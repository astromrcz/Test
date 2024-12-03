package model;

import java.io.*;
import java.util.Scanner;

public class Patient {
    private String username;

    public Patient(String username) {
        this.username = username;
    }

    public void access() {

        while(true){
        Scanner sc = new Scanner(System.in);
        System.out.println("\n---Patient Portal---");
        System.out.println("1. View Profile");
        System.out.println("2. See Scheduled Medication");
        System.out.println("3. Log out");
        int choice = sc.nextInt();
        switch(choice){
            case 1:
            viewProfile();
            break;
            case 2:
            printScheduledMedication();
            break;
            case 3:
            System.out.println("Successfully Logged out");
            return;
            default:
            System.out.println("Invalid Input. Returning to Patient Portal.");
            break;
        }
    }
    }
    private void viewProfile() {
        String fileName = "patient.txt";
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details[0].equalsIgnoreCase(username)) {
                    found = true;
                    System.out.println("\nProfile Details:");
                    System.out.println("Username: " + details[0]);
                    System.out.println("Name: " + details[3]);
                    System.out.println("Age: " + details[4]);
                    System.out.println("Contact Number: " + details[6]);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!found) {
            System.out.println("Profile not found for " + username);
        }
    }

    private void printScheduledMedication() {
        String fileName = "MedicationSched.txt";
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Recipient: " + username)) {
                    found = true;
                    System.out.println(line);
                    for (int i = 0; i < 5; i++) {
                        System.out.println(reader.readLine());
                    }
                    System.out.println("------------------------------");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!found) {
            System.out.println("No scheduled medication found for " + username);
        }
    }
}
