package ui;

import model.InUseRotors;

import java.util.Scanner;

public class UserSystem {
    private Scanner scanner;
    private InUseRotors encryptionBox;

    public UserSystem() {
        scanner = new Scanner(System.in);
        encryptionBox = new InUseRotors();
        installRotors();
    }

    public void installRotors() {
        int rotorChoice;
        int settingChoice;
        String input;
        while (true) {
            System.out.println("Please let me know which number rotor you would like to add first (1-3):");
            rotorChoice = scanIntFromRange(1, 3);
            System.out.println("And what would you like the setting to be? (0-25)");
            settingChoice = scanIntFromRange(0, 25);
            encryptionBox.addRotor(rotorChoice, settingChoice);
            System.out.println("Would you like to add more rotors? Reply 'yes' or 'no':");
            input = scanner.next();
            if ((input.equalsIgnoreCase("no")) || (encryptionBox.getRotorCount() > 8)) {
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




    public static void main(String[] args) {
        UserSystem us = new UserSystem();
        System.out.println(us.encryptionBox.returnCurrentSettings().toString());
        System.out.println(us.encryptionBox.returnRotorNames().toString());

    }
}
