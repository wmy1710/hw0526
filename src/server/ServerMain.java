package server;

import gui.JavaFrame;

import java.io.IOException;

public class ServerMain {
  public static void main(String[] args) {
    JavaFrame frame = new JavaFrame(0);
    frame.connectButton.setText("關閉");
    frame.connectButton.addActionListener(e -> {
      if (!frame.isConnect) {
        frame.frame.dispose();
        System.exit(0);
      }
    });
    ServerReceiveMain srm = new ServerReceiveMain(frame);
    Thread t0 = new Thread(srm);
    t0.start();
    ServerFileSend sf = new ServerFileSend(frame);
    Thread t2 = new Thread(sf);
    t2.start();
    ServerFileReceive sfr = new ServerFileReceive(frame);
    Thread t3 = new Thread(sfr);
    t3.start();

    while (true) {
      System.out.println("sendMain");
      TCPServer server = null;
      try {
        server = new TCPServer(ServerReceiveMain.port + 1);
      } catch (IOException e) {
        e.printStackTrace();
      }

      while (ServerReceiveMain.connect) {
        TCPServer finalServer = server;
        frame.sendButton.addActionListener(e -> {
          if (!frame.textArea.getText().equals("")) {
            frame.chat("Server(" + finalServer.serverIp + ")", frame.textArea.getText());
            frame.frame.revalidate();
            try {
              finalServer.send(frame.textArea.getText());
            } catch (Exception ex) {
              ex.printStackTrace();
            }
            frame.textArea.setText("");
          }
        });

      }

    }
  }
}

