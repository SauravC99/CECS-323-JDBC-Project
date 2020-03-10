import java.util.Scanner;

public class Menu {
    public static void main(String args[]) {
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
    }
}
