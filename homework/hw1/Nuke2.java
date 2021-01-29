import java.io.*;

class Nuke2 {
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader keybd;
        String str;
        
        System.out.print("Input your string: ");
        System.out.flush();
        
        keybd = new BufferedReader(new InputStreamReader(System.in));
        str = keybd.readLine();
        
//        StringBuilder strb = new StringBuilder(str);
//        strb = strb.deleteCharAt(1);
//        System.out.println("The final string is:" + strb);
        
        String result = str.substring(0, 1) + str.substring(2, str.length());
        System.out.println("The final string is:" + result);
                          
    }
}




