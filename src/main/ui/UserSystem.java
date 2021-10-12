package ui;

import model.InUseRotors;

import java.util.List;
import java.util.Scanner;

public class UserSystem {
    private Scanner scanner;
    private InUseRotors encryptionBox;

    //MODIFIES: this
    //EFFECTS: instantiates scanner and encryptionBox, triggers rotor install, encryption, and return settings methods
    public UserSystem() {
        scanner = new Scanner(System.in);
        encryptionBox = new InUseRotors();
        installRotors();
        getAndEncryptInput();
        returnSettingsToUser();
    }

    //the use of scanner.nextLine() to clear the carriage return to allow scanner.nextLine() to read the
    // next input comes from the simple-calculator lecture lab
    //MODIFIES: this
    //EFFECTS: accepts user input to pick rotors and initial settings for encryption in next step
    private void installRotors() {
        int rotorChoice;
        int settingChoice;
        String input;
        while (true) {
            System.out.println("Please let me know which number rotor you would like to add (1-3):");
            rotorChoice = scanIntFromRange(1, 3);
            System.out.println("And what would you like the setting to be? (0-25)");
            settingChoice = scanIntFromRange(0, 25);
            scanner.nextLine();
            encryptionBox.addRotor(rotorChoice, settingChoice);
            if (encryptionBox.getRotorCount() > 8) {
                break;
            }
            System.out.println("Would you like to add more rotors? Reply 'done' if complete, or any input to "
                    + "continue adding rotors, up to 8 rotors is allowed.");
            input = scanner.next();
            scanner.nextLine();
            if (input.equalsIgnoreCase("done")) {
                break;
            }
        }
    }

    //REQUIRES: min and max that correspond to the valid ranges for either rotor choice or setting selection
    //EFFECTS: Requests input of a valid integer for program progression, repeats until valid input is received
    private Integer scanIntFromRange(int min, int max) {
        int output = scanner.nextInt();
        while ((output > max) || (output < min)) {
            System.out.println("Sorry, please make sure to pick a number from " + min + " to " + max + ":");
            output = scanner.nextInt();
        }
        return output;
    }

    //REQUIRES: input of a string comprised of only of alphabetic characters and spaces
    //MODIFIES: this  TODO: CHECK ABOUT THIS BECAUSE IT MODIFIES STEPS BUT IN ANOTHER METHOD
    //EFFECTS: encrypts/decrypts received message and returns the message to user
    private void getAndEncryptInput() {
        System.out.println("Please enter the text that you would like to encode/decode: "
                + "(only letters and spaces are valid inputs)");
        String input = scanner.nextLine();
        String returnedString = encryptionBox.encodeFullMessage(input);
        System.out.println("The following is your encrypted message. Spaces have been removed for protection "
                + "against frequency analysis attacks.");
        System.out.println(returnedString);
    }

    //REQUIRES: user input
    //EFFECTS: triggers return of initial rotor settings method if requested by user
    private void returnSettingsToUser() {
        System.out.println("Would you like to know the settings you used? Type 'yes' or 'no'");
        String input = scanner.next();
        if (input.equals("yes")) {
            printRotorsAndStartData();
        } else {
            System.out.println("Ok! Thanks for encrypting.");
        }
    }

    //EFFECTS: prints rotor label and initial settings to console for user
    private void printRotorsAndStartData() {
        List<Integer> names = encryptionBox.returnRotorNames();
        List<Integer> starts = encryptionBox.returnStartPoints();
        for (int i = 0; i < starts.size(); i++) {
            System.out.println("Rotor #" + (i + 1) + " was: " + names.get(i) + " with setting: " + starts.get(i));
        }
    }

    //MODIFIES: this
    //EFFECTS: Starts program
    public static void main(String[] args) {
        new UserSystem();
    }
}
