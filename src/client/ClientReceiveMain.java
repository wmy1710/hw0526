package client;

import gui.JavaFrame;

import java.io.IOException;
import java.util.Scanner;

public class ClientReceiveMain implements Runnable{
  private JavaFrame frame;
  private String ip;
  public TCPClient client;
  public static int port;

  public ClientReceiveMain(JavaFrame javaFrame,String ip) throws IOException {
    this.frame = javaFrame;
    this.ip = ip;
    System.out.println("ip: "+port);
    client = new TCPClient(ip,port);
  }

  @Override
  public void run() { //傳送訊息
    //Scanner scanner = new Scanner(System.in);
    StringBuffer msg = null;
    while(true){
      try {
        msg = client.read();
      } catch (Exception e) {
        e.printStackTrace();
      }
      frame.chat("Server("+client.hostIp+")",msg.toString());
      System.out.println("Server: "+msg);
    }

  }
}