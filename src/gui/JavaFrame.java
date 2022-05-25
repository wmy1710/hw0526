package gui;

import javax.swing.*;
import java.awt.*;

public class JavaFrame extends JFrame{
  public JPanel chatArea;
  public JFrame frame;
  private String[] defaultUserName = {"Server", "Client"};
  private int defaultUserId;
  public JLabel pathLabel;
  public JButton sendButton;
  public JTextArea textArea;
  public JButton connectButton;
  public boolean isConnect = false;
  public JTextArea ipSet;
  public JTextArea portSet;
  public JButton sendButton2;
  public String imageName;


  public JavaFrame(int defaultUserId){
    this.defaultUserId = defaultUserId;
    this.set();
  }

  private void set(){
    this.frame = new JFrame("TCP視窗聊天室"); // 設定佈局
    frame.setLayout(null);
    frame.setSize(350, 600);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // 建立標籤
    JLabel lb1 = new JLabel("聊天室 - "+ defaultUserName[defaultUserId] +"端",JLabel.CENTER);
    lb1.setBounds(0, 3, 350, 30); // 設定絕對座標
    lb1.setBackground(Color.lightGray);
    lb1.setOpaque(true);
    frame.add(lb1); // 將元件加入版面

    //設Server ip
    JLabel ipSetLabel = new JLabel(defaultUserName[1-defaultUserId] + " IP ");
    ipSetLabel.setFont(new Font("name", Font.PLAIN, 11));
    ipSetLabel.setBounds(0, 33, 55, 30); // 設定絕對座標
    ipSetLabel.setBackground(Color.lightGray);
    ipSetLabel.setOpaque(true);
    frame.add(ipSetLabel); // 將元件加入版面

    ipSet = new JTextArea(); //文字輸入框
    ipSet.setFont(ipSet.getFont().deriveFont(15f));
    ipSet.setLineWrap(true);
    ipSet.setWrapStyleWord(true);
    ipSet.setBounds(55, 33, 120, 30); // 設定絕對座標
    frame.add(ipSet);

    JLabel portSetLabel = new JLabel(defaultUserName[1-defaultUserId] + " port ");
    portSetLabel.setFont(new Font("name", Font.PLAIN, 10));
    portSetLabel.setBounds(175, 33, 55, 30); // 設定絕對座標
    portSetLabel.setBackground(Color.lightGray);
    portSetLabel.setOpaque(true);
    frame.add(portSetLabel); // 將元件加入版面

    portSet = new JTextArea("5501"); //文字輸入框
    portSet.setFont(ipSet.getFont().deriveFont(15f));
    portSet.setLineWrap(true);
    portSet.setWrapStyleWord(true);
    portSet.setBounds(230, 33, 60, 30); // 設定絕對座標
    frame.add(portSet);

    connectButton = new JButton("連線"); //傳送按鈕
    connectButton.setBackground(Color.lightGray);
    connectButton.setBounds(290, 33, 60, 30); // 設定絕對座標
    frame.add(connectButton);



    chatArea = new JPanel(new FlowLayout(FlowLayout.LEFT));

    chatArea.setBounds(0,63,350,450);
    frame.add(chatArea);//将panel放入jFrame界面

    textArea = new JTextArea(); //文字輸入框
    textArea.setFont(textArea.getFont().deriveFont(15f));
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setBounds(0, 513, 280, 40); // 設定絕對座標
    frame.add(textArea);

    sendButton = new JButton("send"); //傳送按鈕
    sendButton.setBackground(Color.lightGray);
    sendButton.setBounds(280, 513, 70, 40); // 設定絕對座標
    frame.add(sendButton);

    JButton fileButton = new JButton("file"); //傳送按鈕
    fileButton.setBackground(Color.lightGray);
    fileButton.setBounds(0, 553, 70, 40); // 設定絕對座標
    frame.add(fileButton);

    pathLabel = new JLabel();
    pathLabel.setBounds(70, 553, 210, 40); // 設定絕對座標
    frame.add(pathLabel);

    sendButton2 = new JButton("send"); //傳送按鈕
    sendButton2.setBackground(Color.lightGray);
    sendButton2.setBounds(280, 553, 70, 40); // 設定絕對座標
    frame.add(sendButton2);

    fileButton.addActionListener(e -> {
      JFileChooser chooser = new JFileChooser();             //设置选择器
      chooser.setMultiSelectionEnabled(true);             //设为多选
      int returnVal = chooser.showOpenDialog(fileButton);
      if (returnVal == JFileChooser.APPROVE_OPTION) {          //如果符合文件类型
        pathLabel.setText(chooser.getSelectedFile().getAbsolutePath());
        System.out.println("You chose to open this file: "+ chooser.getSelectedFile().getName());  //输出相对路径
        imageName = chooser.getSelectedFile().getName();
      }
    });

    frame.revalidate();
  }

  public void chat(String user,String text) {
    JPanel chatBubble = new JPanel(new FlowLayout(FlowLayout.LEFT));
    chatBubble.setSize(350, 40); // 設定絕對座標
    JLabel textLabel = new JLabel(user + " :  " + text + "                                                                               ");
    chatBubble.add(textLabel);
    chatArea.add(chatBubble);
    frame.revalidate();
  }

  public void chat(String user,ImageIcon imageIcon) {
    int[] hw = {imageIcon.getIconWidth(),imageIcon.getIconHeight()};
    hw = size(hw);
    //System.out.println(hw[0]+" "+hw[1]);
    imageIcon.setImage(imageIcon.getImage().getScaledInstance(hw[0],hw[1],Image.SCALE_DEFAULT ));
    JPanel chatBubble = new JPanel(new FlowLayout(FlowLayout.LEFT));
    chatBubble.setSize(350, 40);
    JLabel textLabel = new JLabel(user + " :  ");
    JLabel imageLabel = new JLabel(imageIcon);
    chatBubble.add(textLabel);
    chatBubble.add(imageLabel);
    chatBubble.add(new JLabel("                               "));
    chatArea.add(chatBubble);
    frame.revalidate();
  }

  private int[] size(int[] hw){
      System.out.println("ori: "+hw[0]+" "+hw[1]);
      while(hw[0] > 200 && hw[1] > 200) {
        System.out.println("after: "+hw[0]+" "+hw[1]);
        hw[0]/=2;
        hw[1]/=2;
      }

    return hw;
  }

  public String getIpSet(){
    return ipSet.getText();
  }

  public String getPortSet(){
    return portSet.getText();
  }
}
