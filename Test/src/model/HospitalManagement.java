package model;
import java.util.*;

public class HospitalManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LoginPage login = new LoginPage();
        
        while (true) {
            System.out.println("\nRadiant Health Sanctuary ");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();  
            
            switch (choice) {
                case 1:
                    if (login.login()) {
                        String role = login.getRole();
                        switch (role) {
                            case "admin":
                                Admin admin = new Admin();
                                admin.manageAccounts();
                                break;
                            case "doctor":
                                Doctor p = new Doctor();
                                p.access();
                                break;
                            case "patient":
                                Patient g = new Patient(login.getUsername());
                                g.access();
                                break;
                            default:
                                System.out.println("Unknown role. Exiting.");
                                break;
                        }
                    }
                    break;
                case 2:
                    System.out.println("System closing. Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
