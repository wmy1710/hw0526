package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPServer {
  private ServerSocket ss;
  private Socket sc;
  private int port;
  public InetAddress clientIp;
  public InetAddress serverIp;


  public TCPServer(int port) throws IOException {
    this.port = port;
    this.ss = new ServerSocket(port);     //  建立 TCP 伺服器
    this.sc = this.ss.accept();
    clientIp = sc.getInetAddress();
    serverIp = sc.getLocalAddress();
  }

  public void send(String msg) throws Exception {
      this.sc = this.ss.accept();
      OutputStream os = this.sc.getOutputStream();     //  取得輸出串流
      os.write((msg).getBytes(StandardCharsets.UTF_8));         // 送訊息到 Client 端
      os.close();
      sc.close();
  }

  public StringBuffer read() throws Exception {
    System.out.println("read..");
    this.sc = this.ss.accept();
    InputStream in = this.sc.getInputStream();      // 取得client端輸入訊息的串流
    System.out.println("read..123");
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
    sc.close();
    return buf;
  }

  public void closeServer() throws IOException {
    this.sc.close();                             // 關閉 TCP 伺服器
  }

  public void sendFile(String imagePath) throws IOException {
    this.sc = this.ss.accept();
    System.out.println("準備傳送");
    OutputStream os = sc.getOutputStream();
    FileInputStream fis = new FileInputStream(imagePath);
    byte[] b = new byte[1024];
    int len;
    while((len=fis.read(b))!=-1){
      os.write(b,0,len);
    }
    System.out.println("傳送完畢");
    os.close();
    fis.close();
    sc.close();
  }

  public void receiveFile(String imageName) throws IOException {
    this.sc = this.ss.accept();
    InputStream is = sc.getInputStream();
    FileOutputStream fos = new FileOutputStream(new File(imageName));
    byte[] b = new byte[1024];
    int len;
    while((len = is.read(b))!=-1){
      fos.write(b,0,len);
    }
    System.out.println("收到來自於"+sc.getInetAddress().getHostAddress()+"的檔案");
    //5.關閉相應的流和Socket及ServerSocket的物件
    fos.flush();
    //fos.close();
    is.close();
    sc.close();
  }

  public void setPort(int port){
    this.port = port;
  }
}