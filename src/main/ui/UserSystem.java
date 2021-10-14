package ui;

import model.InUseRotors;

import java.util.List;
import java.util.Scanner;

//User Interface class that adds rotors, with all required fields set, to the InUseRotors and then converts a string
public class UserSystem {
    private Scanner scanner;
    private InUseRotors encryptionBox;

    //MODIFIES: this
    //EFFECTS: instantiates scanner and encryptionBox, triggers rotor install, encryption, and return settings methods
    public UserSystem() {
        scanner = new Scanner(System.in);
        encryptionBox = new InUseRotors();
        installRotors();
        verifyRotorSettings();
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
            if (encryptionBox.getRotorCount() == 8) {
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

    //EFFECTS: Checks input of a valid integer for program progression, repeats request until valid input is
    // received, valid range is min to max inclusive
    private Integer scanIntFromRange(int min, int max) {
        int output = scanner.nextInt();
        while ((output > max) || (output < min)) {
            System.out.println("Sorry, please make sure to pick a number from " + min + " to " + max + ":");
            output = scanner.nextInt();
        }
        return output;
    }

    //REQUIRES:TODO
    //MODIFIES:
    //EFFECTS:
    private void verifyRotorSettings() {
        System.out.println("Here are your current rotor settings:");
        printRotorsAndStartData();
        changeInstalledRotors();
    }

    //TODO#1: add the int range guard function to the different input things
    //TODO#2: add necessary details so that the user knows what the options are for edit and delete (rotor counts etc)
    //TODO#3: think about helper for printing rotor details to cut down on complexity of this method
    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    private void changeInstalledRotors() {
        while (true) {
            System.out.println("Enter 'edit' to change the setting of a rotor, 'delete' to remove a rotor, 'add' "
                    + "to continue adding rotors, or 'done' to move on to encryption");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("edit")) {
                changeRotorSetting();
            } else if (input.equalsIgnoreCase("delete")) {
                removeRotor();
            } else if (input.equalsIgnoreCase("add")) {
                installRotors();
            } else if (input.equalsIgnoreCase("done")) {
                break;
            } else {
                System.out.println("Make sure to give pick a valid input: edit, delete, add, or done");
            }
            System.out.println("Here are your current rotor settings:");
            printRotorsAndStartData();
        }
    }

    //REQUIRES: TODO
    //MODIFIES:
    //EFFECTS:
    private void changeRotorSetting() {
        if (encryptionBox.getRotorCount() == 0) {
            System.out.println("Sorry, you don't have any rotors to change!");
        } else {
            System.out.println("Which rotor would you like to change? (1 - " + encryptionBox.getRotorCount() + ")");
            int rotorToChange = scanIntFromRange(1, encryptionBox.getRotorCount());
            System.out.println("What would you like the new setting to be? (0-25)");
            int newSetting = scanIntFromRange(0, 25);
            scanner.nextLine();
            encryptionBox.resetRotorDetails(rotorToChange, newSetting);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: TODO
    private void removeRotor() {
        if (encryptionBox.getRotorCount() <= 1) {
            System.out.println("Sorry, you can't delete a rotor or you wont have any to encrypt with!");
        } else {
            System.out.println("Which rotor would you like to delete? (1 - " + encryptionBox.getRotorCount() + ")");
            int rotorToDelete = scanIntFromRange(1, encryptionBox.getRotorCount());
            scanner.nextLine();
            encryptionBox.deleteRotor(rotorToDelete);
        }
    }

    //REQUIRES: input of a string comprised of only of alphabetic characters and spaces
    //MODIFIES: this
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

    //EFFECTS: triggers return of initial rotor labels and settings method if requested by user
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
}
