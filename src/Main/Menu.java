import java.util.Scanner;
import java.sql.*;

public class Menu {
    public static String dbURL = "jdbc:derby://localhost:1527/JDBC Project";
    private static String tableName = "WRITINGGROUP";
    // jdbc Connection
    private static Connection conn = null;
    private static Statement stmt = null;

    public static void main(String[] args) {
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
        
        
        try {
            conn = DriverManager.getConnection(dbURL);
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select * from " + tableName);
            ResultSetMetaData rsmd = results.getMetaData();
            int numberCols = rsmd.getColumnCount();
            for (int i=1; i<=numberCols; i++) {
                //print Column Names
                System.out.print(rsmd.getColumnLabel(i)+"\t\t");
            }

            System.out.println("\n-------------------------------------------------");

            while(results.next())
            {
                //int id = results.getInt(1);
                String id = results.getString(2);
                String restName = results.getString(2);
                String cityName = results.getString(3);
                System.out.println(id + "\t\t" + restName + "\t\t" + cityName);
            }
            results.close();
            stmt.close();
        }
        catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }
}