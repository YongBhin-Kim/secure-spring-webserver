package hello;
// @component

// @RestController
public class Server {
    public static void main(String[] args) {
        System.out.println("Hello World Server_mainFunct\n");
    }
    public static int[] stringToInt(String s) {
        int[] result = new int[128];
        System.out.println(s.length());
    
        for (int i = 0; i < s.length(); i++) {
            result[i] = (int) s.charAt(i);
        }
        for (int i = s.length(); i < 128; i++){
            result[i] = 0;
        }
    
        return result;
    }
	public native int[] Enc(int[] tmp);
    public native int[] Dec(int[] tmp);

    public static String JNIFunct_Enc(String receivedSentence) throws Exception { // parameter int len 추가

        int[] PT = stringToInt(receivedSentence);
        System.out.println("\n(Server.java) : 암호화\n");
        Server h = new Server();
        int[] out = h.Enc(PT);
        StringBuilder inttoSB = new StringBuilder();
        for (int i=0; i<128; i++) {
            inttoSB.append(String.valueOf(out[i]));
            if (i<127) {
                inttoSB.append(" ");
            }
        }
        // System.out.println("[암호화]\n" + inttoSB.toString());
        return inttoSB.toString();

    }

    public static String JNIFunct(String receivedSentence) throws Exception {

        String[] CT = receivedSentence.split(" ");   // ["val"] ["val"] ["val"] ["val"]
        int[] CT2 = new int[CT.length];                     
        for (int i = 0; i < CT.length; i++) {
            CT2[i] = Integer.parseInt(CT[i]);               // [val] [val] [val] [val]
        }
        for (int i=CT.length; i<128; i++) {
            CT2[i] = 0;
        }


        Server j = new Server();
        int[] out2 = j.Dec(CT2);                            // [val] [val] [val] [val]
        StringBuilder inttoStr = new StringBuilder();
        for (int i = 0; i < CT.length; i++) {
            inttoStr.append((char) out2[i]);                // [char] [char] [char] [char]
        }
        String tmp = inttoStr.toString();                   // "char char char char" 
        System.out.println("(Server.java) : [복호화]\n" + tmp);
        // tmp += " 400 OK";
        return tmp;
    }
    static {
        // System.loadLibrary("BlockCipher");
        // System.loadLibrary("BlockCipher_AES");
        // System.load("/StartDocker/gs-spring-boot-docker/initial/src/main/java/libBlockCipher.jnilib");
        // System.load("/Users/kim-yongbhin/Desktop/Docker/StartDocker2/gs-spring-boot-docker/initial/src/main/java/libBlockCipher.jnilib");
        System.load("/Users/kim-yongbhin/Desktop/Docker/StartDocker2/WebServer/gs-spring-boot-docker/initial/src/main/java/libBlockCipher_AES.jnilib");
    }
}
