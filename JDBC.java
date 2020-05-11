import java.sql.*;
import java.util.Scanner;


public class JDBC {
    public static void main(String[] args) {
        //Scanner sc = new Scanner(System.in);
        //int input = sc.nextInt();
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:51020/bank", "root", "root");
            System.out.println("Database connection established");
            Statement stmt = conn.createStatement();
            String query = "select * from customers";
            ResultSet rs = stmt.executeQuery(query);
            //String query1 = "select * from customers";
            //PreparedStatement ps = conn.prepareStatement(query1);
            //ps.setString(1,"Devendra");
            //ps.execute();
            while (rs.next()) {
                int accNo = rs.getInt("Account_number");
                int balance = rs.getInt("Balance");
                String transaction = rs.getString("Transaction_History");
                String address = rs.getString("Customer_Address");
                String accType = rs.getString("Account_Type");
                String name = rs.getString("Name");
                String email = rs.getString("Email_id");
                String pass = rs.getString("Password");
                System.out.format("%d %d %s %s %s %s %s %s\n", accNo, balance, transaction, address, accType, name, email, pass);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
        /*
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:51020/bank","root","root");
            System.out.println("Database connection established");
            //Statement stmt = conn.createStatement();
            //String query = "select * from customers";
            //ResultSet rs = stmt.executeQuery(query);
            String query1 = "update customers set Name = 'Danish Shivvanshi' where Account_number = 897456168";
            PreparedStatement ps = conn.prepareStatement(query1);
            //ps.setString(1,"Devendra");
            ps.execute();
            /*while(rs.next()){
                int accNo = rs.getInt("Account_number");
                int balance = rs.getInt("Balance");
                String transaction = rs.getString("Transaction_History");
                String address = rs.getString("Customer_Address");
                String accType = rs.getString("Account_Type");
                String name = rs.getString("Name");
                String email = rs.getString("Email_id");
                String pass = rs.getString("Password");
                System.out.format("%d %d %s %s %s %s %s %s\n",accNo,balance,transaction,address,accType,name,email,pass);
            }*/