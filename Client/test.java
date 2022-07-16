class test {


    public static void main(String argv[]) {
        System.out.println(Integer.toHexString(61));
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
}