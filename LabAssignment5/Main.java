package LabAssignment5;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner mainScanner = new Scanner(System.in);  // Main scanner, though not directly used here
        
        // Display the customized header
        System.out.println("==========================================");
        System.out.println(" Gonda, Micole Kurt T.");
        System.out.println(" Lab Assignment 5");
        System.out.println("==========================================");
        System.out.println("   Simplified Pag-ibig Queuing System    ");
        System.out.println("                Singleton Pattern         ");
        System.out.println("==========================================");
        
        // Get the singleton instance and start the app
        QueuingSystem app = QueuingSystem.getInstance();
        app.run();
        
        mainScanner.close();  // Close at the end for good measure
    }
}

// Class handling the queuing logic with Singleton for central control
class QueuingSystem {
    private static QueuingSystem singletonInstance;
    private int queueCounter = 0;  // Tracks the next available queue number
    private int servingCounter = 0;  // Tracks the current serving number
    private Scanner userInput;
    
    // Private constructor to enforce Singleton
    private QueuingSystem() {
        userInput = new Scanner(System.in);
    }
    
    // Method to retrieve the single instance
    public static QueuingSystem getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new QueuingSystem();
        }
        return singletonInstance;
    }
    
    // Main loop for the application
    public void run() {
        boolean active = true;
        while (active) {
            System.out.println("\nPag-ibig Office Queuing System Menu"); System.out.println("");
            System.out.println("1. Obtain Queue Number (For Visitors)");
            System.out.println("2. Call Next Customer (For Help Desks)");
            System.out.println("3. Adjust Queue Start (For Help Desks)");
            System.out.println("4. Check Current Service Number (Online View)");
            System.out.println("5. Quit System"); System.out.println("");
            System.out.print("Select your choice: ");
            
            String selection = userInput.nextLine();
            try {
                int option = Integer.parseInt(selection);
                switch (option) {
                    case 1:
                        provideQueueNumber();
                        break;
                    case 2:
                        advanceService();
                        break;
                    case 3:
                        adjustQueue();
                        break;
                    case 4:
                        displayServiceStatus();
                        break;
                    case 5:
                        active = false;
                        System.out.println("System shutting down. Have a great day!");
                        break;
                    default:
                        System.out.println("That's not a valid choice. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Oops, that's not a number. Please input a valid option.");
            }
        }
        // No close here to avoid stream issues; handled in main
    }
    
    // Issues a new queue number to a visitor
    private void provideQueueNumber() {
        queueCounter++;
        System.out.println("Here's your queue number: " + queueCounter);
        System.out.println("Kindly wait until it's called at any of the three help desks.");
    }
    
    // Advances to the next in queue for serving
    private void advanceService() {
        if (servingCounter < queueCounter) {
            servingCounter++;
            System.out.println("Serving now: " + servingCounter + " at one of the help desks.");
        } else {
            System.out.println("Queue is empty right now.");
        }
    }
    
    // Allows resetting the queue with input validation loop
    private void adjustQueue() {
        while (true) {
            System.out.print("Input the new queue starting point: ");
            String entry = userInput.nextLine();
            try {
                int resetValue = Integer.parseInt(entry);
                queueCounter = resetValue - 1;  // Set so next starts correctly
                servingCounter = resetValue - 1;
                System.out.println("Queue adjusted successfully. Next will be " + resetValue + ".");
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid entry. Must be an integer. Try again.");
            }
        }
    }
    
    // Shows the current serving number for monitoring
    private void displayServiceStatus() {
        System.out.println("Real-time serving number: " + servingCounter);
    }
}