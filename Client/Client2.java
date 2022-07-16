import java.io.*; 
import java.net.*; 
class Client { 

  public static void main(String argv[]) throws Exception { 

    // Socket clientSocketForSpring = new Socket("localhost", 8080); // Docker Image 다운 시에는 필요 없음.
    String clientSentence, receivedSentence; 

    Socket clientSocket = new Socket("localhost", 10000);
    System.out.println("client-server 연결완료\n");

    // loop:
    // while(true) {
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); 
    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream()); 
    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
    System.out.println("Input : ");
    clientSentence = inFromUser.readLine();

    Client h = new Client(); // h를 c로 넘기자
    // h.print



    /// 서버로 전송
    // if(clientSentence == "Q") {break loop;}
    outToServer.writeBytes(clientSentence + '\n'); 
    receivedSentence = inFromServer.readLine();
    //===============================================
    // 보낼 clientSentence를 암호화
    // 받은 receivedSentence를 복호화
    //===============================================
    System.out.println("From Server: " + receivedSentence);
    // }
    clientSocket.close(); 
    // clientSocketForSpring.close();
  } 
  static {
    System.loadLibrary("BlockCipher");
  }
} 

