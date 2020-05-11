import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(53809);
        Socket s = ss.accept();
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        String str1 = "Hello From server",str2 = "";
        str2 = din.readUTF();
        dout.writeUTF(str1);
        System.out.println("Client Says " + str2);
        dout.flush();
        din.close();
        s.close();
        ss.close();
    }
}
