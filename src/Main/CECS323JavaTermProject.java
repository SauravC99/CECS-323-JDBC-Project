/*
* CECS 323: JDBC Project
* Rachel Pai (015555603)
*
* Due date: March 23, 2020
* */

package cecs.pkg323.jdbc.project;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Mimi Opkins with some tweaking from Dave Brown
 */
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
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
//            + "testdb;user=";
    /**
     * Takes the input string and outputs "N/A" if the string is empty or null.
     * @param input The string to be mapped.
     * @return  Either the input string or "N/A" as appropriate.
     */
    public static String dispNull (String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }

    public static int getMenuChoice() {

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

        //Scans for the user input
        Scanner scan = new Scanner(System.in);
        choice = scan.nextInt(); //User input

        if (choice > 10 || choice < 1){
            System.out.println("Please choose again. You did not pick one of the listed choices. \n");
            System.out.println("---------------------------------------------------------------");

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
            System.out.println("10. Exit");

            choice = scan.nextInt();
        }
        return choice;
    }

    public static void listAllWritingGroups(){
        System.out.println("You've selected: List all writing groups. \n");
        String displayFormat = "%-50s %-30s %-10s %-20s\n";

        //Step 1: Start the connection
        Connection conn = null; //Initalization of connection
        Statement stmt = null; //Initalization of the statement that we're using

        try{
            //STEP 2: Register JDBC Driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open the connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "select groupName, headWriter, yearFormed, subject" + "from WritingGroup";
            ResultSet rs = stmt.executeQuery(sql); //Results from the sql statement

            //STEP 5: Extract data from result set
            System.out.printf(displayFormat, "Group Name", "Head Writer", "Year Formed", "Subject");
            while (rs.next()) {
                //Retrieve by column name
                String groupName = rs.getString("groupName");
                String headWriter = rs.getString("headWriter");
                String yearFormed = rs.getString("yearFormed");
                String subject = rs.getString("subject");

                //Display values
                System.out.printf(displayFormat,
                        dispNull(groupName), dispNull(headWriter), dispNull(yearFormed), dispNull(subject));
            }

            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }

        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
            System.out.println("Goodbye!");
    }//end Option 1

    public static void userSpecifiedGroupData(){
        //Prompt user for the specific group name that they want
        Scanner scan = new Scanner(System.in); //Takes in user input
        System.out.println("You've selected: Specify a writing group and list all the data. \n");

        String displayFormat = "%-50s %-30s %-10s %-20s\n";

        //Step 1: Start the connection
        Connection conn = null; //Initalization of connection
        PreparedStatement pstmt = null; //Initialization of the preprared statement

        try{
            //STEP 2: Register JDBC Driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open the connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);

            //STEP 4: Execute a query (Prepared statement)
            System.out.println("Creating PreparedStatement...");
            String sql;

            //Do we need to combine tables?
            sql = "SELECT groupName, headWriter, yearFormed, subject FROM WritingGroup WHERE groupName = ?";
            pstmt = conn.prepareStatement(sql); //Prepared statement

            System.out.println("Which writing group would you like to pick?");
            String specificGroupName = scan.nextLine();


            //STEP 5: Replace user input with ? in the original SQL statement
            pstmt.setString(1, specificGroupName);
            ResultSet rs = pstmt.executeQuery();
            conn.commit();

            //Step 6: Extract data from result set
            System.out.printf(displayFormat, "Group Name", "Head Writer", "Year Formed", "Subject");
            while (rs.next()) {
                //Retrieve by column name
                String groupName = rs.getString("groupName");
                String headWriter = rs.getString("headWriter");
                String yearFormed = rs.getString("yearFormed");
                String subject = rs.getString("subject");

                //Display values
                System.out.printf(displayFormat,
                        dispNull(groupName), dispNull(headWriter), dispNull(yearFormed), dispNull(subject));
            }

            //STEP 6: Clean-up environment
            rs.close();
            pstmt.close();
            conn.close();
        }

        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println(" \n Action completed! \n");
    }

    public static void listAllPublishers() {
        System.out.println("You've selected: List all publishers. \n");
        String displayFormat = "%-50s %-50s %-20s %-50s \n";

        //STEP 1: Start the connection
        Connection conn = null;
        Statement stmt = null;

        try{
            //STEP 2: Register JDBC Driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...'");
            conn = DriverManager.getConnection(DB_URL);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();

            String sql;
            sql = "SELECT publisherName, publisherAddress, publisherPhone, publisherEmail FROM Publisher";

            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            System.out.printf(displayFormat, "Publisher Name", "Publisher Address", "Publisher Phone", "Publisher Email");
            System.out.printf(displayFormat, "-----", "-----", "-----", "------");

            while (rs.next()) {
                String publisherName = rs.getString("publisherName");
                String publisherAddress = rs.getString("publisherAddress");
                String publisherPhone = rs.getString("publisherPhone");
                String publisherEmail = rs.getString("publisherEmail");

                System.out.printf(displayFormat, dispNull(publisherName), dispNull(publisherAddress), dispNull(publisherPhone), dispNull(publisherEmail));
            }

            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }

        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

    public static void userSpecifiedGroupData(){
        //Prompt user for the specific group name that they want
        Scanner scan = new Scanner(System.in); //Takes in user input
        System.out.println("You've selected: Specify a writing group and list all the data. \n");

        String displayFormat = "%-50s %-30s %-10s %-20s\n";

        //Step 1: Start the connection
        Connection conn = null; //Initalization of connection
        PreparedStatement pstmt = null; //Initialization of the preprared statement

        try{
            //STEP 2: Register JDBC Driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open the connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);

            //STEP 4: Execute a query (Prepared statement)
            System.out.println("Creating PreparedStatement...");
            String sql;

            //Do we need to combine tables?
            sql = "SELECT groupName, headWriter, yearFormed, subject FROM WritingGroup WHERE groupName = ?";
            pstmt = conn.prepareStatement(sql); //Prepared statement

            System.out.println("Which writing group would you like to pick?");
            String specificGroupName = scan.nextLine();


            //STEP 5: Replace user input with ? in the original SQL statement
            pstmt.setString(1, specificGroupName);
            ResultSet rs = pstmt.executeQuery();
            conn.commit();

            //Step 6: Extract data from result set
            System.out.printf(displayFormat, "Group Name", "Head Writer", "Year Formed", "Subject");
            while (rs.next()) {
                //Retrieve by column name
                String groupName = rs.getString("groupName");
                String headWriter = rs.getString("headWriter");
                String yearFormed = rs.getString("yearFormed");
                String subject = rs.getString("subject");

                //Display values
                System.out.printf(displayFormat,
                        dispNull(groupName), dispNull(headWriter), dispNull(yearFormed), dispNull(subject));
            }

            //STEP 6: Clean-up environment
            rs.close();
            pstmt.close();
            conn.close();
        }

        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println(" \n Action completed! \n");
    }


    public static void main(String[] args) {
        //Prompt the user for the database name, and the credentials.
        //If your database has no credentials, you can update this code to
        //remove that from the connection string.
        Scanner in = new Scanner(System.in);
        System.out.print("Name of the database (not the user account): ");
        DBNAME = in.nextLine();
        System.out.print("Database user name: ");
        USER = in.nextLine();
        System.out.print("Database password: ");
        PASS = in.nextLine();
        //Constructing the database URL connection string
        DB_URL = DB_URL + DBNAME; //+ ";user="+ USER + ";password=" + PASS;
    }//end main
}//end FirstExample}
