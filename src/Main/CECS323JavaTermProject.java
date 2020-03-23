package Main;

/*
* CECS 323: JDBC Project
* Rachel Pai (015555603)
* Saurav Chhapawala (016859360)
* Marjorie Baloro ()
* Due date: March 23, 2020
* */

import java.util.Scanner;
import java.sql.*;

public class CECS323JavaTermProject {
    //  Database credentials
    static String USER;
    static String PASS;
    static String DBNAME;
    //This is the specification for the printout that I'm doing:
    //each % denotes the start of a new field.
    //The - denotes left justification.
    //The number indicates how wide to make the field.
    //The "s" denotes that it's a string.  All of our output in this test are
    //strings, but that won't always be the case.
    //static final String displayFormat="%-5s%-15s%-15s%-15s\n";
    // JDBC driver name and database URL
    
    public static Scanner scan = new Scanner(System.in);
    // jdbc Connection
    public static String dbURL = "jdbc:derby://localhost:1527/JDBC Project";
    //Step 1: Start the connection
    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
    
    public static void main(String[] args) {
        //Start the connection to database
        try {
            //STEP 3: Open the connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(dbURL);
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        
        int choice;
        printMenu();
        choice = scan.nextInt();
        
        if (choice == 1) {
            listAllWritingGroups();
        }
        if (choice == 2) {
            listSpecificWritingGroups();
        }
        if (choice == 3) {
            listAllPublishers();
        }
        if (choice == 4) {
            listSpecificPublishers();
        }
        if (choice == 5) {
            listAllBookTitles();
        }
        if (choice == 6) {
            listSpecifiedBook();
        }
        if (choice == 7) {
            addANewBook();
        }
    }
    
    public static void printMenu() {
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
    }
    
    public static void printTable(ResultSet results, ResultSetMetaData rsmd, boolean check) {
        try {
            int col = rsmd.getColumnCount();
            for (int i = 1; i <= col; i++) {
                //print Column Names
                System.out.print(rsmd.getColumnLabel(i)+"\t\t");
            }
            //STEP 5: Extract data from result set
            System.out.println("\n--------------------------------------------------------------------------------");
            while (results.next()) {
                String item1 = results.getString(1);
                String item2 = results.getString(2);
                String item3 = results.getString(3);
                String item4 = results.getString(4);
                if (check) {
                    String item5 = results.getString(5);
                    System.out.println(item1 + "\t\t" + item2 + "\t\t" + item3 + "\t\t" + item4 + "\t\t" + item5);
                }
                else {
                    System.out.println(item1 + "\t\t" + item2 + "\t\t" + item3 + "\t\t" + item4);
                }
                
            }
        }
        catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }
    
    //Not used yet
    public static int getSize(ResultSet results) {
        try {
            ResultSet temp = results;
            int count = 0;
            while (temp.next()) {
                count += 1;
            }
            return count;
        }
        catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
        return 0;
    }
    
    public static void listAllWritingGroups() {
        System.out.println("You've selected: List all Writing Groups. \n");
        try {
            String st = "select GroupName, HeadWriter, YearFormed, Subject from WRITINGGROUP";
            //STEP 4: Execute a query
            pstmt = conn.prepareStatement(st);
            ResultSet results = pstmt.executeQuery();
            ResultSetMetaData rsmd = results.getMetaData();

            printTable(results, rsmd, false);
            
            //STEP 6: Clean-up environment
            results.close();
            pstmt.close();
        }
        catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }
    
    public static void listSpecificWritingGroups() {
        System.out.println("You've selected: List a specific Writing Group. \n");
        try {
            System.out.println("Which Writing Group would you like to see?");
            String input = scan.next();

            String st = "SELECT GroupName, HeadWriter, YearFormed, Subject FROM WRITINGGROUP WHERE GroupName = ?";
            //STEP 4: Execute a query
            pstmt = conn.prepareStatement(st);
            pstmt.setString(1, input);

            ResultSet results = pstmt.executeQuery();
            ResultSetMetaData rsmd = results.getMetaData();

            printTable(results, rsmd, false);
            
            //STEP 6: Clean-up environment
            results.close();
            pstmt.close();
        }
        catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }
    
    public static void listAllPublishers() {
        System.out.println("You've selected: List all Publishers. \n");
        try {
            String st = "select PublisherName, PublisherAddress, PublisherPhone, PublisherEmail from PUBLISHER";
            //STEP 4: Execute a query
            pstmt = conn.prepareStatement(st);
            ResultSet results = pstmt.executeQuery();
            ResultSetMetaData rsmd = results.getMetaData();

            printTable(results, rsmd, false);

            //STEP 6: Clean-up environment
            results.close();
            pstmt.close();
        }
        catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }
    
    public static void listSpecificPublishers() {
        System.out.println("You've selected: List a specific publisher. \n");
        try {
            System.out.println("Which Publisher would you like to see?");
            String input = scan.next();

            String st = "SELECT PublisherName, PublisherAddress, PublisherPhone, PublisherEmail FROM PUBLISHER WHERE PublisherName = ?";
            //STEP 4: Execute a query
            pstmt = conn.prepareStatement(st);
            pstmt.setString(1, input);

            ResultSet results = pstmt.executeQuery();
            ResultSetMetaData rsmd = results.getMetaData();

            printTable(results, rsmd, false);
            
            //STEP 6: Clean-up environment
            results.close();
            pstmt.close();
        }
        catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }
    public static void listAllBookTitles() {
        System.out.println("You've selected: List all Book Titles. \n");
        try {
            String st = "select GroupName, BookTitle, YearPublished, NumberPages, PublisherName from BOOK";
            //STEP 4: Execute a query
            pstmt = conn.prepareStatement(st);
            ResultSet results = pstmt.executeQuery();
            ResultSetMetaData rsmd = results.getMetaData();

            printTable(results, rsmd, true);
            
            //STEP 6: Clean-up environment
            results.close();
            pstmt.close();
        }
        catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }
    
    public static void listSpecifiedBook() {
        System.out.println("You've selected: List all a specific Book Title. \n");
        try {
            System.out.println("Which book would you like to see?");
            String input = scan.next();

            String st = "SELECT groupName, bookTitle, yearPublished, numberPages, publisherName FROM Book WHERE BookTitle = ?";

            pstmt = conn.prepareStatement(st);
            pstmt.setString(1, input);

            ResultSet results = pstmt.executeQuery();
            ResultSetMetaData rsmd = results.getMetaData();

            printTable(results, rsmd, true);

            results.close();
            pstmt.close();
        }
        catch (SQLException sqlExcept) {
        sqlExcept.printStackTrace();
        }
    }


    public static void addANewBook() {
        System.out.println("You've selected: Add a new Book. \n");
        try {
            Scanner in = new Scanner(System.in);
            
            System.out.println("You've chosen: Add a new book.");
            System.out.println("Enter group name: ");
            String groupName = in.nextLine();
            
            System.out.println("Enter book title: ");
            String bookTitle = in.nextLine();
            
            System.out.println("Enter year published: ");
            String year = in.nextLine();
            
            System.out.println("Enter day published: ");
            String day = in.nextLine();
            
            System.out.println("Enter month published: ");
            String month = in.nextLine();
            
            String yearPublished = year + "-" + month + "-" + day;
            
            System.out.println("Enter publisher name: ");
            String publisherName = in.nextLine();
            
            System.out.println("Enter number of pages: ");
            Integer numberPages = in.nextInt();
            
            String st = "Insert into Book (groupName, bookTitle, yearPublished, numberPages, publisherName) values (?, ?, ?, ?, ?)";
            
            pstmt = conn.prepareStatement(st);
            
            pstmt.setString(1, groupName);
            pstmt.setString(2, bookTitle);
            pstmt.setDate(3, java.sql.Date.valueOf(yearPublished));
            pstmt.setInt(4, numberPages);
            pstmt.setString(5, publisherName);
            
            pstmt.executeUpdate();
        
            pstmt.close();
        }
        catch (SQLException sqlExcept) {
        sqlExcept.printStackTrace();
        }
    }
}    