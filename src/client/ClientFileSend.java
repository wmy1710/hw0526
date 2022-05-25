package client;

import gui.JavaFrame;

import javax.swing.*;
import java.io.IOException;

public class ClientFileSend implements Runnable{


    private JavaFrame frame;
    private String ip;
    public static TCPClient client;

    public ClientFileSend(JavaFrame javaFrame, String ip) throws IOException {
        this.frame = javaFrame;
        this.ip = ip;
        client = new TCPClient(ip,ClientReceiveMain.port+1);
    }

    @Override
    public void run() {

        frame.sendButton2.addActionListener(e -> {
            if(!frame.pathLabel.getText().equals("")) {
                try {
                    String path = frame.pathLabel.getText();
                    frame.pathLabel.setText("");
                    client.send(frame.imageName);
                    System.out.println(frame.imageName);
                    frame.chat("Client(" + client.clientIp + ")", new ImageIcon(path));
                    client.sendFile(path);

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