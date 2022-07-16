package hello;

import java.io.*;
import java.net.*;

public class Server {
// Application h = new Application(); // JNI 선언	public native int[] Enc(int[] tmp);
    public native int[] Dec(int[] tmp);    
    public native int[] Enc(int[] tmp);
    // public static void main(String argv[]) throws Exception {
    public static void SRV(String clientSentence) throws Exception {
        // String clientSentence = "a a a a a";
        String CT[] = clientSentence.split(" ");
        int[] CT2 = new int[CT.length];
        for (int i = 0; i < CT.length; i++) {
            CT2[i] = Integer.parseInt(CT[i]);
        } // PT2 배열 JNI로 넘기기!!
        Server j = new Server();
        int[] out2 = j.Dec(CT2); // int (사실 hex)
        StringBuilder hexStr = new StringBuilder();
        for (int i = 0; i < 128; i++) {
            hexStr.append(String.valueOf(out2[i]) + " ");
        }
        String tmp = hexStr.toString(); // hex (string)

        StringBuilder realhex = new StringBuilder();
        String[] tmp2 = tmp.split(" "); // hex (string)
        for (int i = 0; i < 128; i++) {
            realhex.append( (char) Integer.parseInt(tmp2[i], 16));
        }
        String decrypted = realhex.toString();
    }	
    static {
		// System.loadLibrary("BlockCipher");
        System.loadLibrary("BlockCipher_AES");
	}
}