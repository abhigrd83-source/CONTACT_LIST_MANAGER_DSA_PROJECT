import java.util.*;

// Main class - menu of the Contact List Manager
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ContactList list = new ContactList();   // loads data from contacts.txt

        int choice = 0;
        while (choice != 5) {
            System.out.println();
            System.out.println("===== Contact List Manager =====");
            System.out.println("1-Add Contact");
            System.out.println("2-Display All");
            System.out.println("3-Search by Name");
            System.out.println("4-Delete Contact by ID");
            System.out.println("5-Exit");
            System.out.print("Enter your choice: ");

            String input = sc.nextLine().trim();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                choice = 0;
            }

            if (choice == 1) {
                System.out.print("Name: ");
                String name = sc.nextLine().trim();
                System.out.print("Phone: ");
                String phone = sc.nextLine().trim();
                list.addContact(name, phone);
            } else if (choice == 2) {
                list.displayAll();
            } else if (choice == 3) {
                System.out.print("Enter name to search: ");
                String key = sc.nextLine().trim();
                list.searchByName(key);
            } else if (choice == 4) {
                System.out.print("Enter ID to delete: ");
                String id = sc.nextLine().trim();
                list.deleteById(id);
            } else if (choice == 5) {
                list.saveToFile();
                System.out.println("Data saved. Goodbye!");
            } else {
                System.out.println("Invalid choice. Please enter 1 to 5.");
            }
        }
        sc.close();
    }
}
