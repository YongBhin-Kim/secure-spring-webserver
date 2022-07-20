package hello;
// @component

// @RestController
public class Server {
    public static void main(String[] args) {
        System.out.println("[Server] : success load library \n");
    }
    public static int[] stringToInt(String s) {
        int[] result = new int[16];
    
        for (int i = 0; i < s.length(); i++) {
            result[i] = (int) s.charAt(i);
        }
        for (int i = s.length(); i < 16; i++){
            result[i] = 0;
        }
    
        return result;
    }
	public native int[] Enc(int[] tmp);
    public native int[] Dec(int[] tmp);

    public static String JNIFunct_Enc(String receivedSentence) throws Exception {

        int[] PT = stringToInt(receivedSentence);
        Server h = new Server();

        // Client에게 보낼 메시지를 블록암호 AES를 이용하여 암호화
        int[] out = h.Enc(PT);
        StringBuilder inttoSB = new StringBuilder();
        for (int i=0; i<16; i++) {
            inttoSB.append(String.valueOf(out[i]));
            if (i<15) {
                inttoSB.append(" ");
            }
        }
        return inttoSB.toString();

    }

    public static String JNIFunct(String receivedSentence) throws Exception {

        String[] CT = receivedSentence.split(" ");
        int[] CT2 = new int[CT.length];                     
        for (int i = 0; i < CT.length; i++) {
            CT2[i] = Integer.parseInt(CT[i]);
        }
        for (int i=CT.length; i<16; i++) {
            CT2[i] = 0;
        }

        // Client로부터 받은 메시지를 블록암호 AES를 이용하여 복호화
        Server j = new Server();
        int[] out2 = j.Dec(CT2);                           
        StringBuilder inttoStr = new StringBuilder();
        for (int i = 0; i < CT.length; i++) {
            inttoStr.append((char) out2[i]);               
        }
        String tmp = inttoStr.toString();
        System.out.println("[Server] : Decrypted Client message. \n" + tmp);
        return tmp;
    }

    static {
        // ===========================================================================================================================
        // (Ver <= 1.2.2) : 간단한 블록암호 --> https://github.com/YongBhin-Kim/SpringWebServer/blob/master/README.md 에서 자세한 설정방법 확인

        // [macOS]
        // System.load("/Users/kim-yongbhin/Desktop/Docker/StartDocker2/gs-spring-boot-docker/initial/src/main/java/libBlockCipher.jnilib");

        // [Windows]
        // System.load("~WebServer/gs-spring-docker/initial/src/main/java/libBlockCipher.jnilib");

        // ===========================================================================================================================



        // ===========================================================================================================================
        // (Ver 1.2.3) : 블록암호 AES --> https://github.com/YongBhin-Kim/SpringWebServer/blob/master/README.md 에서 자세한 설정방법 확인

        // [macOS]
        System.load("/Users/kim-yongbhin/Desktop/Docker/StartDocker2/WebServer/gs-spring-boot-docker/initial/src/main/java/libBlockCipher_AES.jnilib");

        // [Windows]
        // System.load("~WebServer/gs-spring-boot-docker/initial/src/main/java/libBlockCipher_AES.jnilib"); <--- 이 부분 수정 필요
        // ===========================================================================================================================
    }
}
