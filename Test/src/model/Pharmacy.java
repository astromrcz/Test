package model;

import java.io.*;
import java.util.*;

interface medStorage {
    void addMedicine();
    void removeMedicine(String fileName);
    void viewMedicine();
}

public class Pharmacy implements medStorage {
    @Override
    public void addMedicine() {
        Scanner sc = new Scanner(System.in);
        String fileName = "MedicineStorage.txt";
        while (true) {
            System.out.print("Enter Medicine (Type 'x' to stop): ");
            String medicine = sc.nextLine();
            if (medicine.equalsIgnoreCase("x")) {
                System.out.println("Returning to Homepage");
                break;
            }
            System.out.print("Enter Quantity: ");
            int quantity = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Expiration Date: ");
            String expiration = sc.nextLine();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                writer.write(medicine + "," + quantity + "," + expiration);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(medicine + " added successfully!");

            System.out.println("1. Add Another Medicine");
            System.out.println("2. Go to Medicine Remover");
            System.out.println("3. View all Medicine");
            System.out.println("4. Return to Doctor Portal");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addMedicine();
                    break;
                case 2:
                    removeMedicine(fileName);
                    break;
                case 3:
                    viewMedicine();
                    return;
                case 4:
                    Doctor e = new Doctor();
                    e.access();
                    return;
                default:
                    System.out.println("Invalid choice. Returning to Homepage.");
                    return;
            }
        }
    }

    @Override
    public void removeMedicine(String fileName) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            List<String> medicines = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    medicines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if (medicines.isEmpty()) {
                System.out.println("No medicines found in " + fileName);
                return;
            }

            System.out.println("Medicines in " + fileName + ":");
            for (int i = 0; i < medicines.size(); i++) {
                String[] details = medicines.get(i).split(",");
                if (details.length >= 3) {
                    System.out.println((i + 1) + ". Medicine: " + details[0] + ", Quantity: " + details[1] + ", Expiration Date: " + details[2]);
                } else {
                    System.out.println((i + 1) + ". Invalid entry in file");
                }
            }

            System.out.print("Enter the number of the medicine to remove: ");
            int medicineIndex = sc.nextInt() - 1;
            sc.nextLine();

            if (medicineIndex < 0 || medicineIndex >= medicines.size()) {
                System.out.println("Invalid selection.");
                return;
            }

            System.out.print("Are you sure you want to remove this medicine? (yes/no): ");
            String confirmation = sc.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                medicines.remove(medicineIndex);
                System.out.println("Medicine removed successfully.");

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                    for (String med : medicines) {
                        writer.write(med);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Medicine removal cancelled.");
            }

            System.out.println("1. Remove Another Medicine");
            System.out.println("2. View All Medicines");
            System.out.println("3. Return to Doc Portal");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 3) {
                Doctor e = new Doctor();
                e.access();
                return;
            } else if (choice == 2) {
                System.out.println("Medicines in " + fileName + ":");
                for (int i = 0; i < medicines.size(); i++) {
                    String[] details = medicines.get(i).split(",");
                    if (details.length >= 3) {
                        System.out.println((i + 1) + ". Medicine: " + details[0] + ", Quantity: " + details[1] + ", Expiration Date: " + details[2]);
                    } else {
                        System.out.println((i + 1) + ". Invalid entry in file");
                    }
                }
            } else if (choice != 1) {
                System.out.println("Invalid choice. Returning to Employee Portal.");
                Doctor e = new Doctor();
                e.access();
                return;
            }
        }
    }

    @Override
    public void viewMedicine() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String fileName = "MedicineStorage.txt";
            List<String> medicines = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    medicines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if (medicines.isEmpty()) {
                System.out.println("No medicines found in " + fileName);
            } else {
                System.out.println("Medicines in Storage: ");
                for (int i = 0; i < medicines.size(); i++) {
                    String[] details = medicines.get(i).split(",");
                    System.out.println((i + 1) + "." + details[0] + ", Quantity: " + details[1] + ", Expiration Date: " + details[2]);
                }
            }
            System.out.println("\n_______________________________");
            System.out.println("1. Go back to Pharmacist Portal.");
            System.out.println("2. Log Out");
            int input = sc.nextInt();
            sc.nextLine();  

            switch (input) {
                case 1:
                    Doctor p = new Doctor();
                    p.access();
                    return;
                case 2:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid Input. Exiting.");
                    return;
            }
        }
    }
}
