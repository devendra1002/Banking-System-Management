package bankingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogInClient {
    public static void logIn(){
        String hostAddress = "localhost";//can mention foreign address as well
                                        //mentioned address is local address i.e. 127.0.0.1 communicate with same machine
        int serverPort = 52270;

        JFrame logInFrame = new JFrame("Login Window");
        logInFrame.setSize(400,250);
        logInFrame.setLayout(new FlowLayout(1,40,30));

        JLabel accLabel,passwordLabel;
        JTextField accTextField;
        JPasswordField passwordField;
        JButton logInButton;

        accLabel = new JLabel("Account Number:");
        accTextField = new JTextField(18);
        logInFrame.add(accLabel);
        logInFrame.add(accTextField);

        passwordLabel = new JLabel("Password:            ");
        passwordField = new JPasswordField(18);
        logInFrame.add(passwordLabel);
        logInFrame.add(passwordField);

        logInButton = new JButton("Log In");
        logInFrame.add(logInButton);

        logInButton.addActionListener(new ActionListener() {
            String accNo = "",password = "";
            public void actionPerformed(ActionEvent e) {
                accNo = accTextField.getText();
                password = passwordField.getText();
                String serverMsg = "";
                try {
                    Socket s = new Socket(hostAddress,serverPort);
                    DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                    DataInputStream din = new DataInputStream(s.getInputStream());
                    dout.writeUTF(accNo);
                    dout.writeUTF(password);
                    dout.flush();
                    serverMsg = din.readUTF();
                    System.out.println("Server says: " + serverMsg);
                    dout.close();
                    s.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                //Below code will execute only when the server provide access to the user
                if(serverMsg.equals("AcCEsS GrANtED tO tHE UsER")) {
                    String databasePort = "51020";
                    String emailVar = "";int accNoVar = 0;
                    String accTypeVar = "", nameVar = "";
                    try {
                        //getting balance from database for mentioned account by client
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conn = DriverManager.getConnection("jdbc:mysql://"+hostAddress+":"+databasePort+"/bank", "root", "root");
                        System.out.println("Database connection established");
                        String query = "select * from customers where Account_number =" + accNo;
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        while (rs.next()) {
                            emailVar = rs.getString("Email_id");
                            nameVar = rs.getString("Name");
                            accNoVar = rs.getInt("Account_number");
                            accTypeVar = rs.getString("Account_Type");
                            System.out.format("%d\n%s\n%s\n%s\n",accNoVar,emailVar,nameVar,accTypeVar);
                        }
                        conn.close();
                    } catch (Exception ex) {
                        System.out.println(ex);
                        System.out.println("Unable to connect with database");
                    }
                    //Bank services window implementation
                    JFrame bankServiceFrame = new JFrame("Banking Services Window");
                    bankServiceFrame.setSize(500, 600);
                    bankServiceFrame.setBackground(Color.DARK_GRAY );
                    bankServiceFrame.setLayout(new FlowLayout(1,150,30));

                    JLabel label = new JLabel("Online Banking System");
                    label.setFont(new Font("Serif", Font.BOLD, 40));
                    label.setForeground(Color.BLACK);
                    bankServiceFrame.add(label);

                    JLabel label2 = new JLabel("Name - "+nameVar);
                    label2.setFont(new Font("Serif", Font.PLAIN, 20));
                    label2.setForeground(Color.BLACK);
                    bankServiceFrame.add(label2);

                    JLabel label3 = new JLabel("Account Number - "+accNoVar);
                    label3.setFont(new Font("Serif", Font.PLAIN, 20));
                    label3.setForeground(Color.BLACK);
                    bankServiceFrame.add(label3);

                    JLabel label4 = new JLabel("Email ID - "+emailVar);
                    label4.setFont(new Font("Serif", Font.PLAIN, 20));
                    label4.setForeground(Color.BLACK);
                    bankServiceFrame.add(label4);

                    JLabel label5 = new JLabel("Account Type - "+accTypeVar);
                    label5.setFont(new Font("Serif", Font.PLAIN, 20));
                    label5.setForeground(Color.BLACK);
                    bankServiceFrame.add(label5);

                    //Balance enquiry
                    JButton balanceEnquiry;
                    balanceEnquiry = new JButton(" BALANCE  ENQUIRY ");
                    balanceEnquiry.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            int balance  = 0;
                            //balance display window
                            try {
                                //getting balance from database for mentioned account by client
                                Class.forName("com.mysql.cj.jdbc.Driver");
                                Connection conn = DriverManager.getConnection("jdbc:mysql://"+hostAddress+":"+databasePort+"/bank", "root", "root");
                                System.out.println("\nDatabase connection established");
                                String query = "select Balance from customers where Account_number =" + accNo;
                                Statement stmt = conn.createStatement();
                                ResultSet rs = stmt.executeQuery(query);
                                while (rs.next()) {
                                    balance = rs.getInt("Balance");
                                    System.out.format("%d", balance);
                                }
                                conn.close();
                            } catch (Exception ex) {
                                System.out.println(ex);
                                System.out.println("Unable to connect with database");
                            }

                            JFrame balanceFrame = new JFrame("Balance Window");
                            balanceFrame.setSize(300, 200);
                            balanceFrame.setLayout(new FlowLayout(1, 100, 20));

                            JLabel balanceLabel1 = new JLabel("Your Account Balance");
                            balanceLabel1.setFont(new Font("Serif", Font.PLAIN, 25));
                            balanceLabel1.setForeground(Color.BLACK);

                            JLabel balanceLabel2 = new JLabel("is: "+balance+"/-");
                            balanceLabel2.setFont(new Font("Serif", Font.ITALIC, 20));
                            balanceLabel2.setForeground(Color.BLACK);
                            balanceFrame.add(balanceLabel1);
                            balanceFrame.add(balanceLabel2);

                            balanceFrame.setVisible(true);
                        }
                    });
                    bankServiceFrame.add(balanceEnquiry);

                    //withdraw button code here
                    JButton withdraw;
                    withdraw = new JButton(" WITHDRAW  MONEY ");
                    withdraw.addActionListener(new ActionListener() {
                        int balance1 = 0;

                        public void actionPerformed(ActionEvent e) {
                            try {
                                //getting balance from database for mentioned account by client
                                Class.forName("com.mysql.cj.jdbc.Driver");
                                Connection conn = DriverManager.getConnection("jdbc:mysql://"+hostAddress+":"+databasePort+"/bank", "root", "root");
                                System.out.println("\nDatabase connection established");
                                String query = "select Balance from customers where Account_number =" + accNo;
                                Statement stmt = conn.createStatement();
                                ResultSet rs = stmt.executeQuery(query);
                                while (rs.next()) {
                                    balance1 = rs.getInt("Balance");
                                    System.out.format("%s", balance1);
                                }
                                conn.close();
                            } catch (Exception ex) {
                                System.out.println(ex);
                                System.out.println("Unable to connect with database");
                            }

                            //withdraw balance window
                            JFrame withdrawalFrame = new JFrame("Withdrawal Window");
                            withdrawalFrame.setSize(300, 200);
                            withdrawalFrame.setLayout(new FlowLayout(1, 100, 20));

                            JLabel balanceLabel1 = new JLabel("Enter Amount:");
                            withdrawalFrame.add(balanceLabel1);

                            JTextField amtTextField = new JTextField(15);
                            withdrawalFrame.add(amtTextField);

                            JButton ok = new JButton("OK");
                            withdrawalFrame.add(ok);
                            ok.addActionListener(new ActionListener() {

                                String withdrawalBal = "";
                                int remainingBal = 0;
                                int withdrawalAmountInt = 0;

                                public void actionPerformed(ActionEvent e) {
                                    withdrawalBal = amtTextField.getText().toString();
                                    withdrawalAmountInt = Integer.parseInt(withdrawalBal);

                                    //balance in account will be compared with withdrawal account
                                    //below condition is true than only code will execute otherwise directly goes to else part
                                    if (balance1 > withdrawalAmountInt) {
                                        remainingBal = balance1 - withdrawalAmountInt;
                                        try {
                                            //getting current date and time of the PC
                                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                            LocalDateTime now = LocalDateTime.now();
                                            String dateTimeNow = dtf.format((now));
                                            System.out.println("\n"+dateTimeNow);

                                            //getting balance from database for mentioned account by client
                                            Class.forName("com.mysql.cj.jdbc.Driver");
                                            Connection conn = DriverManager.getConnection("jdbc:mysql://"+hostAddress+":"+databasePort+"/bank", "root", "root");
                                            String query1 = "update customers set Balance = ? where Account_number = ?";
                                            PreparedStatement ps = conn.prepareStatement(query1);
                                            ps.setInt(1, remainingBal);
                                            ps.setInt(2, Integer.parseInt(accNo));
                                            ps.execute();
                                            conn.close();
                                        } catch (Exception ex) {
                                            System.out.println(ex);
                                            System.out.println("Unable to connect with database");
                                        }
                                        try {
                                            //getting balance from database for mentioned account by client
                                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                            LocalDateTime now = LocalDateTime.now();
                                            String dateTimeNow = dtf.format((now));
                                            Class.forName("com.mysql.cj.jdbc.Driver");
                                            Connection conn = DriverManager.getConnection("jdbc:mysql://"+hostAddress+":"+databasePort+"/bank", "root", "root");
                                            String query1 = "update customers set Transaction_History = ? where Account_number = ?";
                                            PreparedStatement ps1 = conn.prepareStatement(query1);
                                            ps1.setString(1, dateTimeNow);
                                            ps1.setInt(2, Integer.parseInt(accNo));
                                            ps1.execute();

                                            conn.close();
                                        } catch (Exception ex) {
                                            System.out.println(ex);
                                            System.out.println("Unable to connect with database");
                                        }

                                        //show balance window for withdrawing amount code here
                                        JFrame showBalFrame = new JFrame("Show Balance Window");
                                        showBalFrame.setSize(350, 200);
                                        showBalFrame.setLayout(new FlowLayout(1, 100, 20));

                                        JLabel label1 = new JLabel("Transaction Successful!!");
                                        label1.setFont(new Font("Serif", Font.ITALIC, 30));
                                        label1.setForeground(Color.BLACK);
                                        showBalFrame.add(label1);

                                        JLabel label2 = new JLabel("Remaining Amount:  " + remainingBal+"/-");
                                        label2.setFont(new Font("Serif", Font.PLAIN, 20));
                                        label2.setForeground(Color.BLACK);
                                        showBalFrame.add(label2);
                                        showBalFrame.setVisible(true);
                                    }
                                    else {

                                        //Insufficient Balance window code here
                                        JFrame insufficientFrame = new JFrame("Insufficient Balance Window");
                                        insufficientFrame.setLayout(new FlowLayout(1,100,20));
                                        insufficientFrame.setSize(320,220);

                                        JLabel label1 = new JLabel("Insufficient Balance");
                                        label1.setFont(new Font("Serif", Font.BOLD, 30));
                                        label1.setForeground(Color.RED);
                                        insufficientFrame.add(label1);

                                        JLabel label2 = new JLabel("Please enter amount");
                                        label2.setFont(new Font("Serif", Font.PLAIN, 20));
                                        label2.setForeground(Color.BLACK);
                                        insufficientFrame.add(label2);

                                        JLabel label3 = new JLabel(String.valueOf("less than "+balance1+" Rs."));
                                        label3.setFont(new Font("Serif", Font.TRUETYPE_FONT, 20));
                                        label3.setForeground(Color.BLACK);
                                        insufficientFrame.add(label3);

                                        insufficientFrame.setVisible(true);
                                    }
                                    System.out.println("\n" + remainingBal);
                                }
                            });

                            withdrawalFrame.setVisible(true);
                        }
                    });
                    bankServiceFrame.add(withdraw);

                    //deposit button code here
                    JButton deposit;
                    deposit = new JButton(" DEPOSIT   MONEY ");

                    deposit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            //deposit amount window code here
                            Frame depositBalFrame = new JFrame("Deposit Amount Window");
                            depositBalFrame.setSize(300, 200);
                            depositBalFrame.setLayout(new FlowLayout(1, 100, 20));

                            JLabel label1 = new JLabel("Enter Amount");
                            depositBalFrame.add(label1);

                            JTextField amtTextField = new JTextField(15);
                            depositBalFrame.add(amtTextField);

                            //ok button inside deposit amount window code here
                            JButton okButton = new JButton("OK");
                            okButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    String amt = "";
                                    int amtInt = 0;
                                    amt = amtTextField.getText().toString();
                                    amtInt = Integer.parseInt(amt);
                                    int balance2 = 0;
                                    try {
                                        //depositing the amount to mentioned account by customers
                                        Class.forName("com.mysql.cj.jdbc.Driver");
                                        Connection conn = DriverManager.getConnection("jdbc:mysql://"+hostAddress+":"+databasePort+"/bank", "root", "root");
                                        System.out.println("Database connection established");
                                        String query = "select Balance from customers where Account_number =" + accNo;
                                        Statement stmt = conn.createStatement();
                                        String query1 = "update customers set Transaction_History = ? where Account_number = ?";
                                        PreparedStatement ps1 = conn.prepareStatement(query1);
                                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                        LocalDateTime now = LocalDateTime.now();
                                        String dateTimeNow = dtf.format((now));
                                        ps1.setString(1, dateTimeNow);
                                        ps1.setInt(2, Integer.parseInt(accNo));
                                        ps1.execute();
                                        ResultSet rs = stmt.executeQuery(query);
                                        while (rs.next()) {
                                            balance2 = rs.getInt("Balance");
                                            System.out.format("%s\n", balance2);
                                        }
                                        conn.close();
                                    } catch (Exception ex) {
                                        System.out.println(ex);
                                        System.out.println("Unable to connect with database");
                                    }
                                    try {
                                        //updating transaction history in database for mentioned account by client
                                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                        LocalDateTime now = LocalDateTime.now();
                                        String dateTimeNow = dtf.format((now));
                                        Class.forName("com.mysql.cj.jdbc.Driver");
                                        Connection conn = DriverManager.getConnection("jdbc:mysql://"+hostAddress+":"+databasePort+"/bank", "root", "root");
                                        String query1 = "update customers set Transaction_History = ? where Account_number = ?";
                                        PreparedStatement ps1 = conn.prepareStatement(query1);
                                        ps1.setString(1, dateTimeNow);
                                        ps1.setInt(2, Integer.parseInt(accNo));
                                        ps1.execute();
                                        System.out.println("\n"+dateTimeNow);
                                        conn.close();
                                    } catch (Exception ex) {
                                        System.out.println(ex);
                                        System.out.println("Unable to connect with database");
                                    }
                                    int totalAmt = 0;
                                    totalAmt = amtInt+balance2;
                                    try {
                                        //depositing the amount into mentioned account by customer
                                        Class.forName("com.mysql.cj.jdbc.Driver");
                                        Connection conn = DriverManager.getConnection("jdbc:mysql://"+hostAddress+":"+databasePort+"/bank", "root", "root");

                                        String query1 = "update customers set Balance = ? where Account_number = ?";
                                        PreparedStatement ps = conn.prepareStatement(query1);
                                        ps.setInt(1, totalAmt);
                                        ps.setInt(2, Integer.parseInt(accNo));
                                        ps.execute();
                                        conn.close();
                                        System.out.format("%d\n",totalAmt);
                                    } catch (Exception ex) {}

                                    //show total balance window code here
                                    JFrame showTotalBal = new JFrame("Show Total Balance window");
                                    showTotalBal.setSize(350,200);
                                    showTotalBal.setLayout(new FlowLayout(1,100,20));

                                    JLabel label1 = new JLabel("Transaction Successful!!");
                                    label1.setFont(new Font("Serif", Font.BOLD, 30));
                                    label1.setForeground(Color.BLACK);
                                    showTotalBal.add(label1);

                                    JLabel label2 = new JLabel("Total Balance: "+totalAmt+"Rs");
                                    label2.setFont(new Font("Serif", Font.ITALIC, 20));
                                    label2.setForeground(Color.BLACK);
                                    showTotalBal.add(label2);

                                    showTotalBal.setVisible(true);

                                }
                            });
                            depositBalFrame.add(okButton);

                            depositBalFrame.setVisible(true);
                        }
                    });
                    bankServiceFrame.add(deposit);
                    bankServiceFrame.setVisible(true);
                    System.out.println("Client Says: " + accNo);
                    System.out.println("Client says: " + password);
                }
                else{
                    //wrong details window code here
                    JFrame wrongDetailsFrame = new JFrame("Wrong Details Window");
                    wrongDetailsFrame.setLayout(new FlowLayout(1,100,20));
                    wrongDetailsFrame.setSize(320,220);

                    JLabel label1 = new JLabel("Access Denied");
                    label1.setFont(new Font("Serif", Font.BOLD, 30));
                    label1.setForeground(Color.RED);
                    wrongDetailsFrame.add(label1);

                    JLabel label2 = new JLabel("You entered wrong details");
                    label2.setFont(new Font("Serif", Font.PLAIN, 20));
                    label2.setForeground(Color.BLACK);
                    wrongDetailsFrame.add(label2);

                    JLabel label3 = new JLabel("Please enter correct details");
                    label3.setFont(new Font("Serif", Font.PLAIN, 20));
                    label3.setForeground(Color.BLACK);
                    wrongDetailsFrame.add(label3);

                    wrongDetailsFrame.setVisible(true);
                }
            }
        });
        logInFrame.setVisible(true);
    }
    public static void main(String[] args) {
        logIn();
    }
}
