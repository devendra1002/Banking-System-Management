import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost",53809);
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        DataInputStream din = new DataInputStream(s.getInputStream());
        String str1 = "devendra",str2 = "";
        dout.writeUTF(str1);
        dout.flush();
        str2 = din.readUTF();
        System.out.println("Server says " + str2);
        dout.close();
        s.close();
    }
}
