package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPClient {
  private Socket client;
  public String hostIp;
  private int port;
  public InetAddress clientIp;

  public TCPClient(String hostIp,int port) throws IOException {
    this.hostIp = hostIp;
    this.port = port;
    System.out.println(hostIp+" "+port);
    this.client = new Socket(hostIp, port); // 連線本地端主機的 TCP Socket
    this.clientIp = client.getInetAddress();
  }

  public void setHostIp(String ip) throws IOException {
    this.hostIp = ip;
    this.client = new Socket(hostIp, port);
  }

  public void send(String msg) throws Exception {
    this.client = new Socket(hostIp, port);
    OutputStream os = this.client.getOutputStream();     //  取得輸出串流
    os.write((msg).getBytes(StandardCharsets.UTF_8));         // 送訊息到 Server 端
    // 關閉輸出串流
    os.close();
  }

  public StringBuffer read() throws Exception {
    this.client = new Socket(hostIp, port);
    InputStream in = this.client.getInputStream();      // 取得server端輸入訊息的串流
    StringBuffer buf = new StringBuffer();         // 建立讀取字串
    try {
      //System.out.println("開始讀取");
      while (true) {            // 不斷讀取
        int x = in.read();    // 讀取一個 byte(read 傳回-1代表串流結束)
        if (x==-1) break;     // x = -1 代表串流結束，讀取完畢，用break 跳開
        byte b = (byte) x;    // 將 x 轉為 byte，放入變數 b
        buf.append((char) b);    }    // 從buffer中添加字元
    } catch (Exception e) {
      in.close();    }          // 關閉輸入串流
    return buf;
  }

  public void closeClient() throws IOException {
    this.client.close();               // 關閉 TcpSocket
  }

  public void sendFile(String imagePath) throws IOException {
    this.client = new Socket(hostIp, port);
    OutputStream os = client.getOutputStream();
    FileInputStream fis = new FileInputStream(new File(imagePath));
    byte[] b = new byte[1024];
    int len;
    while((len=fis.read(b))!=-1){
      os.write(b,0,len);
    }
    os.close();
    fis.close();
  }

  public void receiveFile(String imageName) throws IOException {
    this.client = new Socket(hostIp, port);
    InputStream is = client.getInputStream();
    FileOutputStream fos = new FileOutputStream(new File(imageName));
    byte[] b = new byte[1024];
    int len;
    while((len = is.read(b))!=-1){
      fos.write(b,0,len);
    }
    System.out.println("收到來自於"+client.getInetAddress().getHostAddress()+"的檔案");
    //5.關閉相應的流和Socket及ServerSocket的物件
    is.close();
    fos.close();
  }


}