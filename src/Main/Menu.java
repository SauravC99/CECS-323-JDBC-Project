package Main;

import java.util.Scanner;
import java.sql.*;

public class Menu {
    public static String dbURL = "jdbc:derby://localhost:1527/JDBC Project";
    //private static String tableName = "";
    // jdbc Connection
    private static Connection conn = null;
    private static Statement stmt = null;
    private static PreparedStatement pstmt;
    
    public static void main(String[] args) {
        //Start the connection to database
        try {
            conn = DriverManager.getConnection(dbURL);
            //stmt = conn.createStatement();
        }
        catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
        
        int choice;
        Scanner scan = new Scanner(System.in);

        System.out.println("What would you like to do?");
        System.out.println("1. List all writing groups");
        System.out.println("2. Specify a writing group and list all the data");
        System.out.println("3. List all publishers");
        System.out.println("4. Specify a publisher and list all the data");
        System.out.println("5. List all book titles");
        System.out.println("6. Specify a book and list all the data");
        System.out.println("7. Insert a new book");
        System.out.println("8. Insert a new publisher and update books");
        System.out.println("9. Remove a book");

        choice = scan.nextInt();
        
        if (choice == 1) {
            try {
                String st = "select GROUPNAME, HEADWRITER, YEARFORMED, SUBJECT from WRITINGGROUP";
                pstmt = conn.prepareStatement(st);
                ResultSet results = pstmt.executeQuery();
                ResultSetMetaData rsmd = results.getMetaData();
                
                printTable(results, rsmd);
                
                results.close();
                pstmt.close();
            }
            catch (SQLException sqlExcept) {
                sqlExcept.printStackTrace();
            }
        }
        if (choice == 2) {
            try {
                System.out.println("Which Writing Group would you like to see?");
                String input = scan.next();
                
                String st = "SELECT GroupName, HeadWriter, YearFormed, Subject FROM WRITINGGROUP WHERE GroupName = ?";
                pstmt = conn.prepareStatement(st);
                pstmt.setString(1, input);
                
                ResultSet results = pstmt.executeQuery();
                ResultSetMetaData rsmd = results.getMetaData();
                
                printTable(results, rsmd);
                results.close();
                pstmt.close();
            }
            catch (SQLException sqlExcept) {
                sqlExcept.printStackTrace();
            }
        }
    }
    public static void printTable(ResultSet results, ResultSetMetaData rsmd) {
        try {
            int col = rsmd.getColumnCount();
            for (int i = 1; i <= col; i++) {
                //print Column Names
                System.out.print(rsmd.getColumnLabel(i)+"\t\t");
            }
            System.out.println("\n--------------------------------------------------------------------------------");
            while (results.next()) {
                String groupName = results.getString(1);
                String headWriter = results.getString(2);
                String yearFormed = results.getString(3);
                String subject = results.getString(4);
                System.out.println(groupName + "\t\t" + headWriter + "\t\t" + yearFormed + "\t\t" + subject);
            }
        }
        catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }
}