package server;

import gui.JavaFrame;

import javax.swing.*;
import java.io.IOException;

public class ServerFileReceive implements Runnable{
  JavaFrame frame;

  public ServerFileReceive(JavaFrame frame){
    this.frame = frame;
  }

  @Override
  public void run() {
    while(true) {
      System.out.println("fileReceive");
      TCPServer server = null;
      try {
        server = new TCPServer(ServerReceiveMain.port+2);
      } catch (IOException e) {
        e.printStackTrace();
      }
      StringBuffer msg = null;
      while (ServerReceiveMain.connect) {
        try {
          msg = server.read();
          if (msg.toString().equals("stopend!!!")) {
            break;
          }
          server.receiveFile(msg.toString());
          frame.chat("Server(" + server.serverIp + ")", new ImageIcon(msg.toString()));
          System.out.println("收到"+msg);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
