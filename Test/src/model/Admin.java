package model;
import java.io.*;
import java.util.*;

public class Admin {
    public void manageAccounts() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n   Admin Portal   ");
            System.out.println("1. Create an Account");
            System.out.println("2. Remove an Account");
            System.out.println("3. View all Users");
            System.out.println("4. Log Out");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();  

            switch (choice) {
                case 1:
                    createAccount(sc);
                    break;
                case 2:
                    removeAccount(sc);
                    break;
                case 3:
                    viewAllUsers();
                    break;
                case 4:
                    System.out.println("Successfully Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again");
                    break;
            }
        }
    }

    private void createAccount(Scanner sc) {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        System.err.println("Enter Full Name:");
        String fullName = sc.nextLine();
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Contact Number: ");
        String contactNum = sc.nextLine();
        System.out.print("Enter role (Admin/Doctor/Patient): ");
        String role = sc.nextLine().toLowerCase();
        String fileName;

        switch (role) {
            case "admin":
                fileName = "Admin.txt";
                break;
            case "doctor":
                fileName = "doctor.txt";
                break;
            case "patient":
                fileName = "patient.txt";
                break;
            default:
                System.out.println("Invalid role. Account creation failed.");
                return;
        }

        if (!checkUniqueUsername(username, fileName)) {
            System.out.println("Username already exists. Please choose a different username.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(username + "," + password + "," + role + "," + fullName + "," + age + "," + contactNum);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Account created successfully!");
    }

    private boolean checkUniqueUsername(String username, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.split(",")[0].equals(username)) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void removeAccount(Scanner scanner) {
        while (true) {
            System.out.println("1. Remove an Account");
            System.out.println("2. Go back to Admin Portal");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  

            if (choice == 2) {
                System.out.println("Returning to Admin Homepage.");
                return;
            } else if (choice != 1) {
                System.out.println("Invalid choice. Try again.");
                continue;
            }

            System.out.print("Enter role of the account to remove (Admin/Doctor/Patient): ");
            String role = scanner.nextLine().toLowerCase();

            String fileName;
            switch (role) {
                case "admin":
                    fileName = "Admin.txt";
                    break;
                case "doctor":
                    fileName = "doctor.txt";
                    break;
                case "patient":
                    fileName = "patient.txt";
                    break;
                default:
                    System.out.println("Invalid role. Account removal failed.");
                    continue;
            }

            List<String> accounts = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    accounts.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            if (accounts.isEmpty()) {
                System.out.println("No accounts found in " + fileName);
                continue;
            }

            System.out.println("Accounts in " + fileName + ":");
            for (int i = 0; i < accounts.size(); i++) {
                System.out.println((i + 1) + ". " + accounts.get(i).split(",")[0]);
            }

            System.out.print("Enter the number of the account to remove: ");
            int accountIndex = scanner.nextInt() - 1;
            scanner.nextLine();  

            if (accountIndex < 0 || accountIndex >= accounts.size()) {
                System.out.println("Invalid selection.");
                continue;
            }

            System.out.print("Are you sure you want to remove this account? (yes/no): ");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                accounts.remove(accountIndex);
                System.out.println("Account removed successfully.");
            } else {
                System.out.println("Account removal cancelled.");
                continue;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (String account : accounts) {
                    writer.write(account);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Updated accounts in " + fileName + ":");
            for (int i = 0; i < accounts.size(); i++) {
                System.out.println((i + 1) + ". " + accounts.get(i).split(",")[0]);
                System.out.println("____________________________");
            }
        }
    }

    private void viewAllUsers() {
        String[] files = {"Admin.txt", "doctor.txt", "patient.txt"};
        String[] roles = {"Admins", "Doctors", "Patients"};

        for (int i = 0; i < files.length; i++) {
            System.out.println("\n" + roles[i] + ":");
            List<String> accounts = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(files[i]))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    accounts.add(line.split(",")[0]);
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            if (accounts.isEmpty()) {
                System.out.println("No accounts found.");
            } else {
                for (int j = 0; j < accounts.size(); j++) {
                    System.out.println("----------------------------");
                    System.out.println((j + 1) + ". " + accounts.get(j));
                } 
            }
        }
        System.out.println("____________________________");
    }
}
