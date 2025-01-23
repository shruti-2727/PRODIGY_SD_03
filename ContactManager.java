import java.io.*;
import java.util.*;

public class ContactManager {

    private static List<Contact> contacts = new ArrayList<>();

    public static void main(String[] args) {
        loadContactsFromFile();

        Scanner scanner = new Scanner(System.in);
        int choice = 0; // Initialize choice with a default value

        do {
            System.out.println("\nContact Manager Menu:");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                choice = 0; // Reset choice to an invalid value
            }

            switch (choice) {
                case 1:
                    addContact(scanner);
                    break;
                case 2:
                    viewContacts();
                    break;
                case 3:
                    editContact(scanner);
                    break;
                case 4:
                    deleteContact(scanner);
                    break;
                case 5:
                    System.out.println("Exiting Contact Manager.");
                    saveContactsToFile();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        scanner.close();
    }
    private static void addContact(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter email address: ");
        String email = scanner.nextLine();

        contacts.add(new Contact(name, phoneNumber, email));
        System.out.println("Contact added successfully!");
    }

    private static void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            System.out.println("Contact List:");
            for (int i = 0; i < contacts.size(); i++) {
                System.out.println((i + 1) + ". " + contacts.get(i)); 
            }
        }
    }

    private static void editContact(Scanner scanner) {
        System.out.print("Enter the index of the contact to edit: ");
        try {
            int index = scanner.nextInt() - 1; // Adjust for zero-based indexing
            scanner.nextLine(); // Consume the newline character

            if (index < 0 || index >= contacts.size()) {
                System.out.println("Invalid index.");
            } else {
                Contact contact = contacts.get(index);

                System.out.print("Enter new name (or press Enter to keep current): ");
                String name = scanner.nextLine();
                if (!name.isEmpty()) {
                    contact.setName(name); 
                }

                System.out.print("Enter new phone number (or press Enter to keep current): ");
                String phoneNumber = scanner.nextLine();
                if (!phoneNumber.isEmpty()) {
                    contact.setPhoneNumber(phoneNumber); 
                }

                System.out.print("Enter new email address (or press Enter to keep current): ");
                String email = scanner.nextLine();
                if (!email.isEmpty()) {
                    contact.setEmail(email); 
                }

                System.out.println("Contact updated successfully!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid index.");
            scanner.nextLine(); // Consume the invalid input
        }
    }

    private static void deleteContact(Scanner scanner) {
        System.out.print("Enter the index of the contact to delete: ");
        try {
            int index = scanner.nextInt() - 1; // Adjust for zero-based indexing
            scanner.nextLine(); // Consume the newline character

            if (index < 0 || index >= contacts.size()) {
                System.out.println("Invalid index.");
            } else {
                contacts.remove(index);
                System.out.println("Contact deleted successfully!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid index.");
            scanner.nextLine(); // Consume the invalid input
        }
    }

    private static void saveContactsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("contacts.ser"))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            System.err.println("Error saving contacts to file: " + e.getMessage());
        }
    }

    private static void loadContactsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("contacts.ser"))) {
            contacts = (List<Contact>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // If the file doesn't exist or there's an error loading, ignore it
        }
    }
}

class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + email;
    }
}