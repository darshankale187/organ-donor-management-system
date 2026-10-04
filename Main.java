import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final DonorDAO donorDAO = new DonorDAO();
    private static final RecipientDAO recipientDAO = new RecipientDAO();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");

            try {
                switch (choice) {
                    case 1 -> addDonor();
                    case 2 -> addRecipient();
                    case 3 -> viewAllDonors();
                    case 4 -> viewAllRecipients();
                    case 5 -> findMatchesForRecipient();
                    case 6 -> deleteDonor();
                    case 7 -> deleteRecipient();
                    case 8 -> running = false;
                    default -> System.out.println("Invalid choice, try again.");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }
        System.out.println("Thank you for using the Organ Donor Management System.");
    }

    private static void printMenu() {
        System.out.println("\n===== ORGAN DONOR MANAGEMENT SYSTEM =====");
        System.out.println("1. Register Donor");
        System.out.println("2. Register Recipient");
        System.out.println("3. View All Donors");
        System.out.println("4. View All Recipients (sorted by urgency)");
        System.out.println("5. Find Matching Donors for a Recipient");
        System.out.println("6. Delete Donor");
        System.out.println("7. Delete Recipient");
        System.out.println("8. Exit");
    }

    private static void addDonor() throws SQLException {
        System.out.print("Name: ");
        String name = sc.nextLine();
        int age = readInt("Age: ");
        System.out.print("Blood Group (e.g. O+, A-, AB+): ");
        String bloodGroup = sc.nextLine().toUpperCase();
        System.out.print("Organ to donate (e.g. Kidney, Liver, Cornea): ");
        String organ = sc.nextLine();
        System.out.print("Contact number: ");
        String contact = sc.nextLine();

        Donor donor = new Donor(name, age, bloodGroup, organ, contact);
        int id = donorDAO.addDonor(donor);
        System.out.println("Donor registered successfully! Donor ID: " + id);
    }

    private static void addRecipient() throws SQLException {
        System.out.print("Name: ");
        String name = sc.nextLine();
        int age = readInt("Age: ");
        System.out.print("Blood Group (e.g. O+, A-, AB+): ");
        String bloodGroup = sc.nextLine().toUpperCase();
        System.out.print("Organ required (e.g. Kidney, Liver, Cornea): ");
        String organRequired = sc.nextLine();
        System.out.print("Urgency level (LOW/MEDIUM/HIGH/CRITICAL): ");
        String urgency = sc.nextLine().toUpperCase();
        System.out.print("Contact number: ");
        String contact = sc.nextLine();

        Recipient recipient = new Recipient(name, age, bloodGroup, organRequired, urgency, contact);
        int id = recipientDAO.addRecipient(recipient);
        System.out.println("Recipient registered successfully! Recipient ID: " + id);
    }

    private static void viewAllDonors() throws SQLException {
        List<Donor> donors = donorDAO.getAllDonors();
        if (donors.isEmpty()) {
            System.out.println("No donors registered yet.");
            return;
        }
        System.out.printf("%-5s %-20s %-4s %-6s %-10s %-15s %s%n",
                "ID", "Name", "Age", "Blood", "Organ", "Contact", "Status");
        donors.forEach(System.out::println);
    }

    private static void viewAllRecipients() throws SQLException {
        List<Recipient> recipients = recipientDAO.getAllRecipients();
        if (recipients.isEmpty()) {
            System.out.println("No recipients registered yet.");
            return;
        }
        System.out.printf("%-5s %-20s %-4s %-6s %-10s %-10s %s%n",
                "ID", "Name", "Age", "Blood", "Organ", "Urgency", "Contact");
        recipients.forEach(System.out::println);
    }

    private static void findMatchesForRecipient() throws SQLException {
        int recipientId = readInt("Enter Recipient ID: ");
        Recipient recipient = recipientDAO.getRecipientById(recipientId);

        if (recipient == null) {
            System.out.println("Recipient not found.");
            return;
        }

        // Get all available donors for the required organ, then filter by blood compatibility
        List<Donor> candidateDonors = donorDAO.getAvailableDonorsByOrgan(recipient.getOrganRequired());

        System.out.println("\nSearching matches for " + recipient.getName() +
                " (needs: " + recipient.getOrganRequired() + ", blood group: " + recipient.getBloodGroup() + ")");

        boolean found = false;
        for (Donor donor : candidateDonors) {
            if (BloodCompatibility.isCompatible(donor.getBloodGroup(), recipient.getBloodGroup())) {
                if (!found) {
                    System.out.printf("%-5s %-20s %-4s %-6s %-15s%n", "ID", "Name", "Age", "Blood", "Contact");
                    found = true;
                }
                System.out.printf("%-5d %-20s %-4d %-6s %s%n",
                        donor.getDonorId(), donor.getName(), donor.getAge(),
                        donor.getBloodGroup(), donor.getContact());
            }
        }

        if (!found) {
            System.out.println("No compatible available donors found for this recipient right now.");
        }
    }

    private static void deleteDonor() throws SQLException {
        int id = readInt("Enter Donor ID to delete: ");
        boolean deleted = donorDAO.deleteDonor(id);
        System.out.println(deleted ? "Donor deleted." : "Donor not found.");
    }

    private static void deleteRecipient() throws SQLException {
        int id = readInt("Enter Recipient ID to delete: ");
        boolean deleted = recipientDAO.deleteRecipient(id);
        System.out.println(deleted ? "Recipient deleted." : "Recipient not found.");
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine(); // consume newline
        return val;
    }
}