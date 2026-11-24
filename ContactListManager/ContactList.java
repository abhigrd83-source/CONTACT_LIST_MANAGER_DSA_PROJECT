import java.io.*;
import java.util.*;

// ContactList class - singly linked list of contacts
public class ContactList {

    private Node head = null;          // start of linked list
    private int count = 0;             // total contacts
    private int nextId = 101;          // next ID number
    private String fileName = "contacts.txt";

    // constructor - load old data from file when program starts
    public ContactList() {
        loadFromFile();
    }

    // ---------- add contact ----------
    public void addContact(String name, String phone) {
        if (name.length() == 0 || hasBar(name)) {
            System.out.println("Invalid name. Name cannot be empty or contain '|'.");
            return;
        }

        if (!isValidPhone(phone)) {
            System.out.println("Invalid phone number. It must have exactly 10 digits.");
            return;
        }

        if (phoneExists(phone)) {
            System.out.println("Duplicate phone number. This number already exists.");
            return;
        }

        String id = "C" + nextId;
        nextId++;
        Contact c = new Contact(id, name, phone);
        addAtEnd(new Node(c));
        count++;
        saveToFile();

        System.out.println("Contact added. ID: " + c);
    }

    // add a node at the end of the linked list
    private void addAtEnd(Node newNode) {
        if (head == null) {
            head = newNode;
            return;
        }
        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = newNode;
    }

    // check phone has exactly 10 digits
    private boolean isValidPhone(String phone) {
        if (phone.length() != 10) {
            return false;
        }
        for (int i = 0; i < phone.length(); i++) {
            char c = phone.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    // check if phone is already in the list
    private boolean phoneExists(String phone) {
        Node temp = head;
        while (temp != null) {
            if (temp.data.getPhone().equals(phone)) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    // ---------- display all ----------
    public void displayAll() {
        if (head == null) {
            System.out.println("No contacts to display.");
            return;
        }
        Node temp = head;
        int num = 1;
        while (temp != null) {
            System.out.println(num + ". " + temp.data);
            num++;
            temp = temp.next;
        }
    }

    // ---------- search by name ----------
    public void searchByName(String key) {
        if (key.length() == 0) {
            System.out.println("Please enter a name to search.");
            return;
        }

        // ArrayList used to hold the matching contacts
        ArrayList<Contact> results = new ArrayList<Contact>();
        Node temp = head;
        while (temp != null) {
            if (matches(temp.data.getName(), key)) {
                results.add(temp.data);
            }
            temp = temp.next;
        }

        if (results.size() == 0) {
            System.out.println("No contact found with name: " + key);
        } else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println("Found: " + results.get(i));
            }
        }
    }

    // manual partial match (ignore upper/lower case)
    private boolean matches(String fullName, String key) {
        String a = fullName.toLowerCase();
        String b = key.toLowerCase();

        if (b.length() > a.length()) {
            return false;
        }
        for (int i = 0; i <= a.length() - b.length(); i++) {
            int j = 0;
            while (j < b.length() && a.charAt(i + j) == b.charAt(j)) {
                j++;
            }
            if (j == b.length()) {
                return true;
            }
        }
        return false;
    }

    // ---------- delete by ID ----------
    public void deleteById(String id) {
        if (head == null) {
            System.out.println("List is empty. Nothing to delete.");
            return;
        }

        // case 1: first node
        if (head.data.getId().equalsIgnoreCase(id)) {
            String deletedId = head.data.getId();
            head = head.next;
            count--;
            saveToFile();
            System.out.println("Contact " + deletedId + " deleted. Contacts remaining: " + count);
            return;
        }

        // case 2: middle or last node
        Node prev = head;
        Node curr = head.next;
        while (curr != null) {
            if (curr.data.getId().equalsIgnoreCase(id)) {
                prev.next = curr.next;
                count--;
                saveToFile();
                System.out.println("Contact " + curr.data.getId() + " deleted. Contacts remaining: " + count);
                return;
            }
            prev = curr;
            curr = curr.next;
        }

        System.out.println("Contact with ID " + id + " not found.");
    }

    // ---------- helpers ----------
    // check if text has the '|' character (manual loop)
    private boolean hasBar(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '|') {
                return true;
            }
        }
        return false;
    }

    // split "ID|Name|Phone" into 3 parts (manual, no split method)
    private String[] splitLine(String line) {
        String[] parts = new String[3];
        int partNo = 0;
        String current = "";
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '|') {
                if (partNo >= 2) {
                    return null;
                }
                parts[partNo] = current;
                partNo++;
                current = "";
            } else {
                current = current + c;
            }
        }
        if (partNo != 2) {
            return null;
        }
        parts[2] = current;
        return parts;
    }

    // ---------- file handling ----------
    private void loadFromFile() {
        try {
            File f = new File(fileName);
            if (!f.exists()) {
                f.createNewFile();   // create empty file
                return;
            }

            // read all lines into an ArrayList
            ArrayList<String> lines = new ArrayList<String>();
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = br.readLine();
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
            br.close();

            int maxId = 100;
            for (int i = 0; i < lines.size(); i++) {
                String[] parts = splitLine(lines.get(i));
                if (parts != null) {
                    Contact c = new Contact(parts[0], parts[1], parts[2]);
                    addAtEnd(new Node(c));
                    count++;
                    int num = Integer.parseInt(parts[0].substring(1));
                    if (num > maxId) {
                        maxId = num;
                    }
                }
            }
            nextId = maxId + 1;
        } catch (Exception e) {
            System.out.println("Error while loading file: " + e.getMessage());
        }
    }

    public void saveToFile() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(fileName));
            Node temp = head;
            while (temp != null) {
                Contact c = temp.data;
                pw.println(c.getId() + "|" + c.getName() + "|" + c.getPhone());
                temp = temp.next;
            }
            pw.close();
        } catch (Exception e) {
            System.out.println("Error while saving file: " + e.getMessage());
        }
    }
}
