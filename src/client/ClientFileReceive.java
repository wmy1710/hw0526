package client;

import gui.JavaFrame;

import javax.swing.*;
import java.io.IOException;

public class ClientFileReceive implements Runnable{
  private JavaFrame frame;
  private String ip;
  public TCPClient client;

  public ClientFileReceive(JavaFrame javaFrame, String ip) throws IOException {
    this.frame = javaFrame;
    this.ip = ip;
    client = new TCPClient(ip,ClientReceiveMain.port+2);
  }

  @Override
  public void run() {
    StringBuffer msg = null;
    while(true){
      try {
        msg = client.read();
        System.out.println(msg);
        client.receiveFile(msg.toString());
        System.out.println("Server: " + msg);
        frame.chat("Server("+client.hostIp + ")",new ImageIcon(msg.toString()));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}