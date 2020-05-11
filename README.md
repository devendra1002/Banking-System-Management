# Online-Banking-System
# Description
Online banking system is java based Software built to maintain all transactions which carried out in banks such as balance enquiry, withdrawal of amount and deposit money. Users can maintain their accounts by themselves after getting verified by server of bank. All records will be updated in the database of the bank as well afetr each transaction. 
# Implementation
- Implemented using Java Programming language only.
- Below steps can be followed for implentation.

1. Maintain a database where all records of the customers like Account number, Balance, transaction history, Address, Account type, Name, Email id etc. can be maintained (for this purpose MySql server can be installed which will store all the records in the form of table).
2. After creating database, it has to be connect with Java using java database connectivity "file: JDBC.java" contains code to link bank server to Java Runtime Environment.
3. To establish connection between user and server (Dialogue Client-server protocol can be used) "file: Client.java and file: Server.java" contains code to achieve this task. 
Note: Server file has to be run first and then client file.

