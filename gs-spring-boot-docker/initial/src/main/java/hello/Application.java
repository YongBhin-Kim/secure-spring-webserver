package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.*; 
import java.net.*;
// import Server.java;
// import Server.*;

@SpringBootApplication
// @RestController
public class Application {
	// static {
		// System.loadLibrary("BlockCipher");
	// }

	public static void main(String[] args) throws Exception{
		SpringApplication.run(Application.class, args);
		System.out.println("Hello World!\n");
		String clientSentence, serverSentence; 
		ServerSocket welcomeSocket = new ServerSocket(10000);

		while(true){
			Socket connectionSocket = welcomeSocket.accept(); 
			System.out.println("client-server 연결완료\n");
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); 
			DataOutputStream  outToClient = 
			new DataOutputStream(connectionSocket.getOutputStream()); 
			clientSentence = inFromClient.readLine();
			//===============================================   
			String receivedSentence = Server.JNIFunct(clientSentence);
			serverSentence = Server.JNIFunct_Enc(receivedSentence);
			//===============================================
			//===============================================
			// 받은 clientSentence를 복호화 (clientSentence를 blockCipherLib.c 로 넘겨서 복호화한 후 print)
			// 보낼 serverSentence를 암호화 (serverSentence를 blockCipherLib.c 로 넘겨서 암호화한 후 Client로 보낸다.)
			//===============================================
			outToClient.writeBytes(serverSentence + '\n'); 
		}
	}


}