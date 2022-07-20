import java.io.*; 
import java.net.*; 
class Client { 
    public static int[] stringToInt(String s) {
        int[] result = new int[16]; // 128
        
    
        for (int i = 0; i < s.length(); i++) {
            result[i] = (int) s.charAt(i);
        }
        for (int i = s.length(); i < 16; i++){// 128
            result[i] = 0;
        }
    
        return result;
    }
    public native int[] Enc(int[] tmp);
    public native int[] Dec(int[] tmp);
    public static void main(String argv[]) throws Exception { 

        // Client는 10000 포트(Server)로 연결 요청
        Socket clientSocket = new Socket("localhost", 10000);

        // Client/Server 연결 완료
        System.out.println("[client-server connected]\n");

        // Client는 사용자로부터 메시지를 입력받음
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        int[] PT = stringToInt(inFromUser.readLine());
        Client h = new Client();

        // 입력받은 메시지를 int 배열로 변경하고 JNI를 이용하여 블록암호 AES로 암호화
        int[] out = h.Enc(PT);


        // 암호화된 배열을 String으로 변경
        StringBuilder inttoSB = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            inttoSB.append(String.valueOf(out[i]));
            if (i < 15)
                inttoSB.append(" ");
        }
        String clientSentence = inttoSB.toString();

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream()); 

        // 암호화된 메시지를 Server로 전송
        outToServer.writeBytes(clientSentence + "\n");      

        System.out.println("[Client] : Sending an encrypted message to server.\n");

        // Server로부터 암호화된 메시지를 받음
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String receivedSentence = inFromServer.readLine();

        String[] CT = receivedSentence.split(" ");   
        int[] CT2 = new int[CT.length];                     
        for (int i = 0; i < CT.length; i++) {
            CT2[i] = Integer.parseInt(CT[i]);  
        }


        // Server로부터 받은 메시지를 블록암호 AES를 이용하여 복호화
        Client j = new Client();
        int[] out2 = j.Dec(CT2);

        StringBuilder inttoStr = new StringBuilder();
        for (int i = 0; i < PT.length; i++) {
            inttoStr.append((char) out2[i]);                // [char] [char] [char] [char]
        }
        String tmp = inttoStr.toString();

        // Client는 복호화된 메시지를 출력
        System.out.println("[Client] : Decrypted Server message. \n" + tmp);

        // 소켓 닫기
        clientSocket.close(); 
    } 

    static {
        // ===========================================================================================================================
        // (Ver <= 1.2.2) : 간단한 블록암호 --> https://github.com/YongBhin-Kim/SpringWebServer/blob/master/README.md 에서 자세한 설정방법 확인
        
        // [macOS]
        // System.loadLibrary("BlockCipher");

        // [Windows]
        // System.load("~SpringWebServer/Client/libBlockCipher.jnilib");
        // ===========================================================================================================================
        

        // ===========================================================================================================================
        // (Ver 1.2.3) : 블록암호 AES --> https://github.com/YongBhin-Kim/SpringWebServer/blob/master/README.md 에서 자세한 설정방법 확인
        
        // [macOS]
        System.loadLibrary("BlockCipher_AES");

        // [Windows]
        // System.load("~SpringWebServer/Client/libBlockCipher_AES.jnilib"); <--- 이 부분 수정 필요 
        // ===========================================================================================================================
    }
} 