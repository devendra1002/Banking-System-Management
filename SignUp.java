package bankingSystem;
import bankingSystem.LogInClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SignUp extends LogInClient {

    public static void signUp(){
        String databasePort = "51020";
        String hostAddress = "localhost";

        JFrame signUpFrame = new JFrame("Sign Up Window");
        signUpFrame.setSize(400,500);
        signUpFrame.setLayout(new FlowLayout(1,40,30));

        JLabel nameLabel,emailLabel,addressLabel,passwordLabel,passwordLabel2,accTypeLabel;
        JTextField nameTextField,emailTextField,addressField,accTypeField;
        JPasswordField passwordField,passwordField2;
        JButton signUpButton;

        nameLabel = new JLabel("Name:                       ");
        nameTextField = new JTextField(18);
        signUpFrame.add(nameLabel);
        signUpFrame.add(nameTextField);

        emailLabel = new JLabel("Email Id:                  ");
        emailTextField = new JTextField(18);
        signUpFrame.add(emailLabel);
        signUpFrame.add(emailTextField);

        addressLabel = new JLabel("Address:                 ");
        addressField = new JTextField(18);
        signUpFrame.add(addressLabel);
        signUpFrame.add(addressField);

        accTypeLabel = new JLabel("Account Type        ");
        accTypeField = new JTextField(18);
        signUpFrame.add(accTypeLabel);
        signUpFrame.add(accTypeField);

        passwordLabel = new JLabel("Password:              ");
        passwordField = new JPasswordField(18);
        signUpFrame.add(passwordLabel);
        signUpFrame.add(passwordField);

        passwordLabel2 = new JLabel("Correct Password:");
        passwordField2 = new JPasswordField(18);
        signUpFrame.add(passwordLabel2);
        signUpFrame.add(passwordField2);

        signUpButton = new JButton("SIGN UP");
        signUpFrame.add(signUpButton);
        signUpButton.addActionListener(new ActionListener() {
            String name = "", email = "", password = "",checkPass = "",address = "",accType="";
            String dateTimeNow = "";
            public void actionPerformed(ActionEvent e) {
                name = nameTextField.getText().toString();
                email = emailTextField.getText().toString();
                password = passwordField.getText().toString();
                checkPass = passwordField2.getText().toString();
                address = addressField.getText().toString();
                accType = accTypeField.getText().toString();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                dateTimeNow = dtf.format((now));
                System.out.println(dateTimeNow);

                //Generating account number
                int maxAccNo = 0,latestAccNo = 0;
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://"+hostAddress+":"+databasePort+"/bank","root","root");
                    System.out.println("Database connection established");
                    String query = "select Account_number from customers where Account_number in (select(max(Account_number))from customers)";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while(rs.next()) {
                        maxAccNo = rs.getInt("Account_number");
                        latestAccNo = maxAccNo + 1;
                    }
                    conn.close();
                }catch (Exception ex){}
                System.out.println(maxAccNo);
                System.out.println(latestAccNo);
                if(password.equals(checkPass)){
                    //updating the details in database
                    try {
                        //depositing the amount to mentioned account by customers
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conn = DriverManager.getConnection("jdbc:mysql://"+hostAddress+":"+databasePort+"/bank", "root", "root");

                        String query1 = "insert into customers (Account_number,Balance, Transaction_History,Customer_Address,Account_Type,Email_id" +
                                ",Password,Name) values(?,?,?,?,?,?,?,?)";
                        PreparedStatement ps = conn.prepareStatement(query1);
                        ps.setInt(1, latestAccNo);
                        ps.setInt(2,0);
                        ps.setString(3,dateTimeNow);
                        ps.setString(4,address);
                        ps.setString(5,accType);
                        ps.setString(6,email);
                        ps.setString(7,password);
                        ps.setString(8,name);
                        ps.execute();
                        conn.close();
                    } catch (Exception ex) {}
                    logIn();
                }
            }
        });
        signUpFrame.setVisible(true);

    }

    public static void main(String[] args) {
        signUp();
    }
}
