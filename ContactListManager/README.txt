CONTACT LIST MANAGER (Java + DSA)
PS No: 30 | Language: Java
Concepts: Linked List, ArrayList, Searching, Methods, File Handling

1. PROBLEM DEFINITION
Console application to manage a contact list using a singly linked list.
User can add contacts, display all, search by name and delete by ID.
Data is saved in contacts.txt.

2. FILES AND CLASSES
Contact.java      - stores id, name, phone of one contact
Node.java         - linked list node (data = Contact, next)
ContactList.java  - linked list + add, display, search, delete, file handling
Main.java         - menu and user input

3. RULES
- ID is auto generated: C101, C102, ...
- Phone must have exactly 10 digits
- Duplicate phone numbers are not allowed
- Data loads from contacts.txt at start and saves on exit
- Searching logic is written manually (no built-in search or sort)

4. HOW TO RUN
javac *.java
java Main

5. TEST CASES (all pass)
TC1: Add Anita Roy, 9876543210
     -> Contact added. ID: C101 | Anita Roy | 9876543210
TC2: Only C101 exists, delete C101
     -> Contact C101 deleted. Contacts remaining: 0
TC3: Add Ravi Kumar, 12345
     -> Invalid phone number. It must have exactly 10 digits.
TC4: C101 exists. Add Karan Mehta, 9123456780
     -> Contact added. ID: C102 | Karan Mehta | 9123456780
     Search "Mehta" -> Found: C102 | Karan Mehta | 9123456780
     Display All    -> 1. C101 | Anita Roy | 9876543210
                       2. C102 | Karan Mehta | 9123456780

6. TIME COMPLEXITY (n = number of contacts)
Add: O(n)   Display: O(n)   Search: O(n x m)   Delete: O(n)   Space: O(n)

7. ALGORITHM
Add    : validate -> make ID -> create node -> go to last node -> link -> save
Display: start from head, print each node till null
Search : check every node name with key (ignore case) -> print matches
Delete : if head matches, move head; else use prev and curr to unlink node
