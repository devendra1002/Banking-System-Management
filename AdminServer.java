package bankingSystem;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class AdminServer {
    String databasePassword = "";
    String accNoClient = "", passwordClient = "";
    String databasePort = "51020";
    int serverPort = 52270;
    String hostAddress = "localhost";//can mention foreign address as well //mentioned address is local address i.e. 127.0.0.1

    public void admin() throws IOException {
        //receiving account no and password from client
        ServerSocket ss = new ServerSocket(serverPort);
        Socket s = ss.accept();
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        accNoClient = din.readUTF();
        passwordClient = din.readUTF();
        try{
            //getting password for verification from customers table in database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://"+hostAddress+":"+databasePort+"/bank","root","root");
            System.out.println("Database connection established");
            String query = "select Password from customers where Account_number ="+accNoClient;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                databasePassword = rs.getString("Password");
            }
            stmt.close();
            conn.close();
        }catch (Exception ex){}

        //providing access to the client after verifying the password
        try {
            if (databasePassword.equals(passwordClient)) {
                String str1 = "AcCEsS GrANtED tO tHE UsER";
                dout.writeUTF(str1);
                dout.writeUTF(accNoClient);
                dout.flush();
                din.close();
                s.close();
                ss.close();
            }
        }catch(Exception ex1){
            System.out.println(ex1);
        }
    }
    public static void main(String[] args) throws IOException {
        AdminServer obj1 = new AdminServer();
        obj1.admin();
    }
}
