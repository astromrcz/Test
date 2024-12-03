package model;
import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Doctor {
    public void access() { 
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n   Welcome to the Doc Portal.   ");
            System.out.println("1. View Profile");
            System.out.println("2. Add Medicine");
            System.out.println("3. Remove Medicine");
            System.out.println("4. View Medicine Storage");
            System.out.println("5. Schedule a Patient's Medication");
            System.out.println("6. Log Out");
            int choice = sc.nextInt();
            sc.nextLine();  

            switch(choice){
                case 1:
                    new viewProfile(sc);
                    break;
                case 2:
                    Pharmacy m = new Pharmacy();
                    m.addMedicine();
                    break;
                case 3:
                    Pharmacy n = new Pharmacy();
                    n.removeMedicine(null);
                    break;
                case 4:
                    Pharmacy a = new Pharmacy();
                    a.viewMedicine();
                    break;
                case 5:
                    medScheduler q = new medScheduler();
                    q.scheduleMedication();
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
    public class viewProfile {
        public viewProfile(Scanner sc) {
            System.out.print("Enter your username: ");
            String username = sc.nextLine();
            boolean found = false;

            try (BufferedReader reader = new BufferedReader(new FileReader("doctor.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] details = line.split(",");
                    if (details[0].equals(username)) {
                        System.out.println("Welcome to Your Profile.");
                        System.out.println("Name: " + details[3]);
                        System.out.println("Age: " + details[4]);
                        System.out.println("Position: " + details[2]);
                        found = true;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!found) {
                System.out.println("Profile not found.");
            }
            System.out.println("1. Return to Doc Portal");
            System.out.println("2. Log out");
            int choice = sc.nextInt();
            sc.nextLine();  

            switch(choice){
                case 1:
                    Doctor p = new Doctor();
                    p.access();
                    break;
                case 2:
                    System.out.println("Successfully Logged out! ");
                    return;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    public class medScheduler {
        public void scheduleMedication() {
            Scanner sc = new Scanner(System.in);

            while(true){
                System.out.println("\nEnter the Name of Recipient: ");
                String recipient = sc.nextLine();
                if (!isRecipientValid(recipient)) {
                    System.out.println("Recipient not found. Please try again.");
                    continue;
                }

                
                List<String> medicines = getAvailableMedicines();
                if (medicines.isEmpty()) {
                    System.out.println("No medicines available in storage.");
                    return;
                }

                System.out.println("Available Medicines(Medicine, Quantity, Expiration Date):");
                for (int i = 0; i < medicines.size(); i++) {
                    System.out.println((i + 1) + ". " + medicines.get(i));
                }

                System.out.print("Enter the number of the medicine to choose: ");
                int medicineIndex = sc.nextInt() - 1;
                sc.nextLine();  

                if (medicineIndex < 0 || medicineIndex >= medicines.size()) {
                    System.out.println("Invalid selection.");
                    continue;
                }

                String chosenMedicine = medicines.get(medicineIndex).split(",")[0];
                System.out.print("Enter the quantity: ");
                int quantity = sc.nextInt();
                sc.nextLine();  

                if (!updateMedicineStorage(chosenMedicine, quantity)) {
                    System.out.println("\nInsufficient quantity or medicine not found. Creating a request.");
                    createMedicineRequest(chosenMedicine, quantity);
                    continue;
                }

                System.out.print("Enter dosage: ");
                String dosage = sc.nextLine();
                System.out.print("Enter frequency (e.g., once a day, twice a day): ");
                String frequency = sc.nextLine();
                System.out.print("Enter start date (dd-MM-yyyy): ");
                String startDateString = sc.nextLine();
                System.out.print("Enter end date (dd-MM-yyyy): ");
                String endDateString = sc.nextLine();

                // Parse the dates
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate startDate = LocalDate.parse(startDateString, dateFormatter);
                LocalDate endDate = LocalDate.parse(endDateString, dateFormatter);

                // Display the date
                System.out.println("\nMedication Schedule:");
                System.out.println("Medication: " + chosenMedicine);
                System.out.println("Dosage: " + dosage);
                System.out.println("Frequency: " + frequency);
                System.out.println("Start Date: " + startDate);
                System.out.println("End Date: " + endDate);

                // Write details to MedicationSched.txt
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("MedicationSched.txt", true))) {
                    writer.write("Recipient: " + recipient);
                    writer.newLine();
                    writer.write("Medication: " + chosenMedicine);
                    writer.newLine();
                    writer.write("Dosage: " + dosage);
                    writer.newLine();
                    writer.write("Frequency: " + frequency);
                    writer.newLine();
                    writer.write("Start Date: " + startDate);
                    writer.newLine();
                    writer.write("End Date: " + endDate);
                    writer.newLine();
                    writer.write("------------------------------");
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("\nMedication schedule saved successfully!");
                System.out.println("1. Return to Doc Portal");
                System.out.println("2. Log out");
                int choice = sc.nextInt();
                sc.nextLine();  

                switch(choice){
                    case 1:
                        Doctor p = new Doctor();
                        p.access();
                        return;
                    case 2:
                        System.out.println("Successfully Logged out");
                        return;
                    default:
                        System.out.println("Invalid Input.");
                        break;
                }
            }
        }

        private boolean isRecipientValid(String recipient) {
            String fileName = "patient.txt";
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.split(",")[0].equalsIgnoreCase(recipient)) {
                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        private List<String> getAvailableMedicines() {
            List<String> medicines = new ArrayList<>();
            String fileName = "MedicineStorage.txt";
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    medicines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return medicines;
        }

        private boolean updateMedicineStorage(String medicine, int quantity) {
            List<String> medicines = new ArrayList<>();
            String fileName = "MedicineStorage.txt";
            boolean updated = false;

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] details = line.split(",");
                    if (details[0].equalsIgnoreCase(medicine)) {
                        int currentQuantity = Integer.parseInt(details[1]);
                        if (currentQuantity >= quantity) {
                            details[1] = String.valueOf(currentQuantity - quantity);
                            updated = true;
                        }
                    }
                    medicines.add(String.join(",", details));
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            if (updated) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                    for (String med : medicines) {
                        writer.write(med);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            return updated;
        }

        private void createMedicineRequest(String medicine, int quantity) {
            String fileName = "requestMedicine.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                writer.write("Medicine: " + medicine + ", Quantity: " + quantity);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
