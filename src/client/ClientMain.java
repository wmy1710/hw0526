package client;

import gui.JavaFrame;

public class ClientMain {

  public ClientMain() {
  }

  public static void main(String[] args) throws Exception {

    JavaFrame frame = new JavaFrame(1);
    TCPClient client = null;
    frame.connectButton.addActionListener(e -> {
      if(!frame.isConnect) {
        System.out.println(frame.ipSet.getText());
        frame.connectButton.setText("關閉");

        frame.isConnect = true;

      }
    });
    while(!frame.isConnect){
      System.out.println(" ");
    }
    frame.frame.revalidate();
//    String ip = frame.ipSet.getText()
//    System.out.println(ip);
//    System.out.println(frame.getPortSet());
    client = new TCPClient(frame.getIpSet(), Integer.parseInt(frame.getPortSet()));
    ClientReceiveMain.port = Integer.parseInt(frame.getPortSet())+1;
    ClientReceiveMain crm = new ClientReceiveMain(frame,frame.getIpSet());
    ClientFileReceive cf = new ClientFileReceive(frame,frame.getIpSet());
    Thread t = new Thread(crm);
    t.start();

    Thread t2 = new Thread(cf);
    t2.start();
    ClientFileSend cfs = new ClientFileSend(frame,frame.getIpSet());
    Thread t3 = new Thread(cfs);
    t3.start();

    TCPClient finalClient1 = client;
    System.out.println("wait...");
    frame.sendButton.addActionListener(e -> {
      // System.out.println("button");
      if (!frame.textArea.getText().equals("")) {
        frame.chat("Client(" + finalClient1.clientIp + ")", frame.textArea.getText());
        frame.frame.revalidate();
        try {
          finalClient1.send(frame.textArea.getText());
        } catch (Exception ex) {
          ex.printStackTrace();
        }
        frame.textArea.setText("");
      }
    });

    TCPClient finalClient = client;
    frame.connectButton.addActionListener(e -> {
      if(frame.isConnect){
        try {
          System.out.println("end");
          finalClient.send("stopend!!!");
          ClientFileSend.client.send("stopend!!!");
        } catch (Exception ex) {
          ex.printStackTrace();
        }
        frame.isConnect = false;
        frame.frame.dispose();
        System.exit(0);
      }
    });
  }
}