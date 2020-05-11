# Online-Banking-System
# Description
Online banking system is java based Software built to maintain all transactions which carried out in banks such as balance enquiry, withdrawal of amount and deposit money. Users can maintain their accounts by themselves after getting verified by server of bank. All records will be updated in the database of the bank afetr each transaction. 
# Implementation
1. Implemented using Java Programming language only.
2. Below steps can be followed for implentation.

- Maintain a database where all records of the customers like Account number, Balance, transaction history, Address, Account type, Name, Email id etc. can be stored. (for this purpose MySql server can be installed which will store all the records in the form of table).
- After creating database, it must connect with Java using java database connectivity. ("file: JDBC.java" contains code to link bank server to Java Runtime Environment).
- To establish connection between user and server dialogue Client-Server protocol can be used. ("file: Client.java and file: Server.java" contains code to achieve this task). 
Note: Server file has to be run first and then client file.

- File: (AdminServer.java) file can be used to interact with the database and will provide access of database to the user. only when it find the record of that user in the database who logged in with the server otherwise it will deny the access of the database.
- File: (LogInClient.java) file contains code for implementation of user panel which send request to the server to get access of the account for all banking transactions. After verification user can maintain all transactions.
- File: (SignUp.java) file contains code to insert new record in the database. After inserting record in bank database server will provide access of account to the user redirecting through login panel.
Note: Above three files are essential to run the project (AdminServer, LogInClient and SignUp). Other files are sample files to achieve the tasks such as database connectivity and Client-Server communication. 

# To run
Following steps can be performed.
- First change the ports in all these three files. Specify your PC's port number.
- serverPort and databasePort variables in these three files needs to updated for changing the port number.
- Run AdminServer.java file first.
- Then, run LogInClient.java file.
- Run SignUp.java file only when user wants to create account in the bank.

# Requirements 
- Multithreading in java
- GUI programming in java
- Networking in java
- JDBC 
