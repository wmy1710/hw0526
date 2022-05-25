package server;

import gui.JavaFrame;

import java.io.IOException;

public class ServerReceiveMain implements Runnable{
  private JavaFrame frame;
  public static boolean connect = true;
  public static int port= 5501;
  public ServerReceiveMain(JavaFrame javaFrame){
    this.frame = javaFrame;
  }
  @Override
  public void run() {
    while(true) {
      System.out.println("ReceiveMain");
      TCPServer server = null;
      try {
        server = new TCPServer(port);
        frame.ipSet.setText(server.clientIp.toString());
      } catch (IOException e) {
        e.printStackTrace();
      }
      connect = true;
      System.out.println("wait to read");
      while (connect) {
        StringBuffer msg = null;
        try {
          msg = server.read();
          if (msg.toString().equals("stopend!!!")) {
            port += 10;
            connect = false;
            frame.portSet.setText(port+"");
            System.out.println("now port: " + port);
            frame.frame.revalidate();
            break;
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        frame.chat("Client(" + server.clientIp + ")", msg.toString());
        System.out.println("Client: " + msg);
        frame.frame.revalidate();
      }

    }


  }
}
