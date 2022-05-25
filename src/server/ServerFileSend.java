package server;

import gui.JavaFrame;

import javax.swing.*;
import java.io.IOException;

public class ServerFileSend implements Runnable{
  JavaFrame frame;

  public ServerFileSend(JavaFrame frame){
    this.frame = frame;
  }

  @Override
  public void run() {

    while (true) {
      System.out.println("fileSend");
      TCPServer server = null;
      try {
        server = new TCPServer(ServerReceiveMain.port+3);
      } catch (IOException e) {
        e.printStackTrace();
      }
      TCPServer finalServer = server;
      while(ServerReceiveMain.connect) {

        frame.sendButton2.addActionListener(e -> {
          if(!frame.pathLabel.getText().equals("")) {
            try {
              String path = frame.pathLabel.getText();
              frame.pathLabel.setText("");
              finalServer.send(frame.imageName);
              System.out.println(frame.imageName);
              frame.chat("Server(" + finalServer.serverIp + ")", new ImageIcon(path));
              finalServer.sendFile(path);

            } catch (Exception exception) {
              JOptionPane.showMessageDialog(null,
                  "No such file",
                  "file error",
                  JOptionPane.WARNING_MESSAGE);
            }
          }
        });
      }
    }
  }
}
