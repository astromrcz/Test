package model;
import java.io.*;
import java.util.*;

public class LoginPage {
    private String role;
    private String username;

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public boolean login() {
        Scanner sc = new Scanner(System.in);
        String[] files = {"Admin.txt", "doctor.txt", "patient.txt"};
        String[] roles = {"admin", "doctor", "patient"};
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.print("Enter username (or type 'x' to exit): ");
            username = sc.nextLine();
            if (username.equalsIgnoreCase("x")) {
                System.out.println("Exiting login process.");
                break;
            }
            System.out.print("Enter password: ");
            String password = sc.nextLine();
            if (password.equalsIgnoreCase("x")) {
                System.out.println("Exiting login process.");
                break;
            }

            for (int i = 0; i < files.length; i++) {
                String fileName = files[i];
                try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] credentials = line.split(",");
                        if (credentials.length >= 2 && credentials[0].equals(username) && credentials[1].equals(password)) {
                            System.out.println("Logged in successfully as " + roles[i] + "!");
                            role = roles[i];
                            loggedIn = true;
                            break;
                        } 
                    }
                    if (loggedIn) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!loggedIn) {
                System.out.println("Invalid username or password. Please try again.");
            }
        }
        return loggedIn;
    }
}
