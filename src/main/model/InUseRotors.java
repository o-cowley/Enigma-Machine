package model;

import java.util.ArrayList;
import java.util.List;

public class InUseRotors {

    private ArrayList<Rotor> inUse = new ArrayList<>();
    private Rotor reflector;

    //EFFECTS: Constructs an empty inUse ArrayList and initializes its static reflector
    public InUseRotors() {
        this.reflector = new Rotor();
        reflector.setReflector();
    }

    //REQUIRES: i is between 1 and #ofAvailableRotors inclusive, setting is between 0 and 25 inclusive
    //MODIFIES: this
    //EFFECTS: adds a new Rotor to the end of inUse with setting as initial steps setting
    public void addRotor(Integer i, Integer setting) {
        Rotor rotor = new Rotor(i);
        rotor.setRotor(setting);
        inUse.add(rotor);
    }

    //REQUIRES: At least one rotor is in the inUse rotor array
    //MODIFIES: this
    //EFFECTS: step all rotors, if a rotor has completes a rotation then the next rotor is also stepped etc.
    public void stepRotors() {
        inUse.get(0).stepRotor();
        if ((inUse.size() > 1) && (inUse.get(0).getSteps() == 0)) {
            for (int i = 1; i < inUse.size(); i++) {
                inUse.get(i).stepRotor();
                if (inUse.get(i).getSteps() != 0) {
                    break;
                }
            }
        }
    }

    //EFFECTS: passes given Integer through each rotor, then reflector, and back again, shifting value
    // according to each rotor's given pattern
    public int encodeSingle(Integer i) {
        int x = i;
        for (Rotor r: inUse) {
            x = r.shiftLetter(x, false);
        }
        x = reflector.shiftLetter(x, true);
        for (int y = (inUse.size() - 1); y >= 0; y--) {
            x = inUse.get(y).shiftLetter(x, true);
        }
        return x;
    }

    //REQUIRES: at least one rotor in inUse array
    //MODIFIES: this
    //EFFECTS: Encodes the string according to Rotor encryption, stepping rotors before encrypting each letter,
    // returns encrypted string in all caps, with spaces removed
    public String encodeFullMessage(String str) {
        List<Integer> convertedForEncoding = changeStringToIntArray(str);
        for (int i = 0; i < convertedForEncoding.size(); i++) {
            this.stepRotors();
            int a = this.encodeSingle(convertedForEncoding.get(i));
            convertedForEncoding.set(i, a);
        }
        return returnIntArrayToString(convertedForEncoding);
    }

    //REQUIRES: no characters other than alphabetic and spaces
    //EFFECTS: creates a List<Integer> where each entry is the equivalent character value 0-25 (A-Z) of each character
    // in String str, in the order that they appeared, spaces are not included in the List
    private static List<Integer> changeStringToIntArray(String str) {
        String strNoSpaces = str.toUpperCase().replaceAll("\\s", "");
        List<Integer> toConvert = new ArrayList<>();
        for (int i = 0; i < strNoSpaces.length(); i++) {
            int value = strNoSpaces.charAt(i);
            value -= 65;
            toConvert.add(value);
        }
        return toConvert;
    }

    // website used to understand conversion of a list of characters (stored as their integer values) back to
    // a single string:
    // https://stackoverflow.com/questions/6324826/converting-arraylist-of-characters-to-a-string
    //EFFECTS: takes the List<Integer> list, which contains alphabetic values 0-25, shifts back to the required
    // unicode range(Capital letters), then converts back to a character, appends it to builder and converts
    // to a single string
    private static String returnIntArrayToString(List<Integer> list) {
        StringBuilder builder = new StringBuilder(list.size());
        for (int i: list) {
            int shiftedBack = i + 65;
            char c = (char) shiftedBack;
            builder.append(c);
        }
        return builder.toString();
    }


    //EFFECTS: returns an ordered list of start points for the rotors used in the encryption
    public List<Integer> returnStartPoints() {
        List<Integer> originalSettings = new ArrayList<>();
        for (Rotor r: inUse) {
            originalSettings.add(r.getStartPoint());
        }
        return originalSettings;
    }

    //EFFECTS: returns number of rotors in inUse array
    public Integer getRotorCount() {
        return inUse.size();
    }

    //EFFECTS: Returns the current step setting of each rotor in the array as a List<Integer>
    // will always be same length as result of returnRotorNames()
    public List<Integer> returnCurrentSettings() {
        List<Integer> originalSettings = new ArrayList<>();
        for (Rotor r: inUse) {
            originalSettings.add(r.getSteps());
        }
        return originalSettings;
    }

    //EFFECTS: Returns the rotor label of each rotor in the array, as a List<Integer>
    // will always be same length as result of returnCurrentSettings()
    public List<Integer> returnRotorNames() {
        List<Integer> rotorLabels = new ArrayList<>();
        for (Rotor r: inUse) {
            rotorLabels.add(r.getLabel());
        }
        return rotorLabels;
    }
}