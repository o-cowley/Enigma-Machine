package ui;

import model.InUseRotors;

import java.util.List;
import java.util.Scanner;

public class UserSystem {
    private Scanner scanner;
    private InUseRotors encryptionBox;

    public UserSystem() {
        scanner = new Scanner(System.in);
        encryptionBox = new InUseRotors();
        installRotors();
        getAndEncryptInput();
        returnSettingsToUser();
    }

    //the use of scanner.nextLine() to clear the carriage return to allow scanner.nextLine() to read the
    // next input comes from the simple-calculator lecture lab
    private void installRotors() {
        int rotorChoice;
        int settingChoice;
        String input;
        while (true) {
            System.out.println("Please let me know which number rotor you would like to add first (1-3):");
            rotorChoice = scanIntFromRange(1, 3);
            System.out.println("And what would you like the setting to be? (0-25)");
            settingChoice = scanIntFromRange(0, 25);
            encryptionBox.addRotor(rotorChoice, settingChoice);
            System.out.println("Would you like to add more rotors? Reply 'done' if complete, or any input to "
                    + "continue adding rotors.");
            input = scanner.next();
            scanner.nextLine();
            if ((input.equalsIgnoreCase("done")) || (encryptionBox.getRotorCount() > 8)) {
                break;
            }
        }
    }

    private Integer scanIntFromRange(int min, int max) {
        int output = scanner.nextInt();
        while ((output > max) || (output < min)) {
            System.out.println("Sorry, please make sure to pick a number from " + min + " to " + max + ":");
            output = scanner.nextInt();
        }
        return output;
    }

    private void getAndEncryptInput() {
        System.out.println("Please enter the text that you would like to encode/decode: "
                + "(only letters and spaces are valid inputs)");
        String input = scanner.nextLine();
        String returnedString = encryptionBox.encooooode(input);
        System.out.println("The following is your encrypted message. Spaces have been removed for added security.");
        System.out.println(returnedString);
    }

    private void returnSettingsToUser() {
        System.out.println("Would you like to know the settings you used? Type 'yes' or 'no'");
        String input = scanner.next();
        if (input.equals("yes")) {
            printRotorsAndStarts();
        } else {
            System.out.println("Ok! Thanks for encrypting.");
        }
    }

    private void printRotorsAndStarts() {
        List<Integer> names = encryptionBox.returnRotorNames();
        List<Integer> starts = encryptionBox.returnStartPoints();
        for (int i = 0; i < starts.size(); i++) {
            System.out.println("Rotor #" + (i + 1) + " was: " + names.get(i) + " with setting: " + starts.get(i));
        }
    }

    public static void main(String[] args) {
        new UserSystem();
    }
}
