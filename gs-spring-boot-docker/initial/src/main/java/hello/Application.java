package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.*; 
import java.net.*;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws Exception{
		SpringApplication.run(Application.class, args);
		System.out.println("[Server Start]\n");
		String clientSentence, serverSentence; 

		// 10000 포트로 서버 오픈
		ServerSocket welcomeSocket = new ServerSocket(10000);

		while(true){
			// Server/Client 연결
			Socket connectionSocket = welcomeSocket.accept(); 
			System.out.println("[client-server connected]\n"); 

			// Client로부터 암호화된 메시지를 받음
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); 
			DataOutputStream  outToClient = 
			new DataOutputStream(connectionSocket.getOutputStream()); 
			clientSentence = inFromClient.readLine();

			// Client로부터 받은 메시지를 JNI를 호출하여 블록암호 AES로 복호화
			String receivedSentence = Server.JNIFunct(clientSentence);

			// 복호화된 메시지를 블록암호 AES로 암호화
			serverSentence = Server.JNIFunct_Enc(receivedSentence);
			
			// 암호화된 메시지를 Client에게 보냄
			outToClient.writeBytes(serverSentence + '\n'); 
			System.out.println("[Server] : Sending an encrypted message to client.");
		}
	}


}