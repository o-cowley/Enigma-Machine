package ui;

import exceptions.NoRotorsLoadedException;
import model.InUseRotors;
import persistence.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

//User Interface class that adds rotors, with all required fields set, to the InUseRotors and then converts a string
public class ConsoleSystem {
    private static final String destination = "./data/encryptionSettings.json";
    private final int maxRotorCount = 8;

    private Scanner scanner;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private InUseRotors encryptionBox;
    private int availableRotorTypes;


    //MODIFIES: this
    //EFFECTS: instantiates scanner and encryptionBox, triggers rotor install, edit phase, encryption, and return
    // settings methods
    public ConsoleSystem() {
        scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(destination);
        jsonReader = new JsonReader(destination);

        encryptionBox = new InUseRotors();
        availableRotorTypes = encryptionBox.getAvailableRotorTypes();

        runSystem();
    }

    //MODIFIES: this
    //EFFECTS: runs the program
    private void runSystem() {
        loadOrInstallRotors();
        getAndEncryptInput();
        askToSave();
        returnSettingsToUser();
        scanner.close();
    }

    //MODIFIES: this
    //EFFECTS: requests user to choose to load encryptionBox from file or set manually, executes accordingly
    private void loadOrInstallRotors() {
        System.out.println("Would you like to load your rotors from last save? enter 'yes' to load");
        String input = scanner.next();
        scanner.nextLine();
        if (input.equalsIgnoreCase("yes")) {
            try {
                readRotorsFromFile();
                printRotorsAndStartData(true);
            } catch (IOException e) {
                System.out.println("Sorry, I couldn't get your rotor data, you will have to input them yourself!");
                customRotorSetup();
            } catch (NoRotorsLoadedException e) {
                System.out.println("There weren't any rotors saved, you will need to input them.");
                customRotorSetup();
            }
        } else {
            customRotorSetup();
        }
    }

    //MODIFIES: this
    //EFFECTS: allows user to select rotors and settings and add them to encryptionBox
    private void customRotorSetup() {
        installRotors();
        verifyRotorSettings();
    }

    //MODIFIES: this
    //EFFECTS: asks user if they want to save the settings they used for encryption to file, executes accordingly
    private void askToSave() {
        System.out.println("Would you like to save your rotors for next time? enter 'yes' to do so");
        String input = scanner.next();
        scanner.nextLine();
        if (input.equalsIgnoreCase("yes")) {
            writeRotorsToFile();
        }
    }

    //the use of scanner.nextLine() to clear the carriage return to allow scanner.nextLine() to read the
    // next input comes from the simple-calculator lecture lab
    //MODIFIES: this
    //EFFECTS: accepts user input to pick rotors and initial settings for encryption
    private void installRotors() {
        int rotorChoice;
        int settingChoice;
        String input;
        while (true) {
            System.out.println("Please let me know which number rotor you would like to add (1-"
                    + availableRotorTypes + "):");
            rotorChoice = scanIntFromRange(1, availableRotorTypes);
            System.out.println("And what would you like the setting to be? (0-25)");
            settingChoice = scanIntFromRange(0, 25);
            scanner.nextLine();
            encryptionBox.addRotor(rotorChoice, settingChoice);
            if (encryptionBox.getRotorCount() >= maxRotorCount) {
                break;
            }
            System.out.println("Would you like to add more rotors? Reply 'done' if complete, or any input to "
                    + "continue adding rotors, up to " + maxRotorCount + " rotors is allowed.");
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

    //MODIFIES: this
    //EFFECTS: displays current rotors and then gives the user the option to change rotor settings,
    // delete rotors, or add more rotors, unlimited changes allowed before user choice to proceed to encryption phase
    private void verifyRotorSettings() {
        while (true) {
            System.out.println("Here are your current rotor settings:");
            printRotorsAndStartData(true);
            System.out.println("Enter 'edit' to change the setting of a rotor, 'delete' to remove a rotor, 'add' "
                    + "to continue adding rotors, or 'done' to move on to encryption");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("edit")) {
                changeRotorSetting();
            } else if (input.equalsIgnoreCase("delete")) {
                removeRotor();
            } else if (input.equalsIgnoreCase("add")) {
                addRotorsFromEdit();
            } else if (input.equalsIgnoreCase("done")) {
                break;
            } else {
                System.out.println("Make sure to enter a valid input.");
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: changes the start setting of a rotor that has already been picked and added to the encryptionBox,
    // rotor and new setting chosen by user, if no rotors installed, the edit command is rejected
    private void changeRotorSetting() {
        if (encryptionBox.getRotorCount() == 0) {
            System.out.println("Sorry, you don't have any rotors to change!");
        } else {
            System.out.println("Which rotor would you like to change? (1 - " + encryptionBox.getRotorCount() + ")");
            int rotorToChange = scanIntFromRange(1, encryptionBox.getRotorCount());
            System.out.println("What would you like the new setting to be? (0-25)");
            int newSetting = scanIntFromRange(0, 25);
            scanner.nextLine();
            encryptionBox.resetRotorDetails((rotorToChange - 1), newSetting);
        }
    }

    //MODIFIES: this
    //EFFECTS: allows the user to select an already installed rotor to delete, or refuses if there are not enough
    // rotors to have at least one left after deletion
    private void removeRotor() {
        if (encryptionBox.getRotorCount() <= 1) {
            System.out.println("Sorry, you can't delete a rotor or you wont have any to encrypt with!");
        } else {
            System.out.println("Which rotor would you like to delete? (1 - " + encryptionBox.getRotorCount() + ")");
            int rotorToDelete = scanIntFromRange(1, encryptionBox.getRotorCount());
            rotorToDelete--;
            scanner.nextLine();
            encryptionBox.deleteRotor(rotorToDelete);
        }
    }

    //MODIFIES: this
    //EFFECTS: checks if rotor count is at maxRotorCount, if so, does not permit adding more, otherwise calls method
    // to install additional rotors
    private void addRotorsFromEdit() {
        if (encryptionBox.getRotorCount() >= maxRotorCount) {
            System.out.println("You already have " + maxRotorCount + " rotors, you can't add any more than that.");
        } else {
            installRotors();
        }
    }

    //REQUIRES: input of a string comprised of only of alphabetic characters and spaces
    //MODIFIES: this
    //EFFECTS: encrypts/decrypts received message and returns the message as a String to user, rotors are
    // stepped each letter
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
        System.out.println("Would you like to know the settings you picked? Type 'yes' or 'no'");
        String input = scanner.next();
        if (input.equalsIgnoreCase("yes")) {
            printRotorsAndStartData(false);
        } else {
            System.out.println("Ok! Thanks for encrypting.");
        }
    }

    //EFFECTS: prints rotor label and initial settings to console for user with present or past tense
    private void printRotorsAndStartData(boolean presentTense) {
        String tense;
        if (presentTense) {
            tense = " is: ";
        } else {
            tense = " was: ";
        }
        List<Integer> names = encryptionBox.returnRotorNames();
        List<Integer> starts = encryptionBox.returnStartPoints();
        for (int i = 0; i < starts.size(); i++) {
            System.out.println("Rotor #" + (i + 1) + tense + names.get(i) + " with setting: " + starts.get(i));
        }
    }

    //MODIFIES: this
    //EFFECTS: writes the encryptionBox to file as JSON, to be used next time
    private void writeRotorsToFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(encryptionBox);
            jsonWriter.close();
            System.out.println("Rotors saved to file");
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, the destination file wasn't found");
        }

    }

    //MODIFIES: this
    //EFFECTS: reads rotor settings from file and sets up encryptionBox accordingly
    //         Throws IOException if file not found
    //         Throws NoRotorsLoadedException if save file did not have any rotors loaded
    private void readRotorsFromFile() throws IOException, NoRotorsLoadedException {
        encryptionBox = jsonReader.readFile();
    }

}
