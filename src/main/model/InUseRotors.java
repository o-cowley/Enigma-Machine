package model;

import java.util.ArrayList;
import java.util.List;

//this is a representation of the active rotors that are being used for the encryption process. Functioning as if a
// user would pick rotors from the RotorCase and add them to the InUseRotor to be actively used in the letter
// shifting process
public class InUseRotors {

    private ArrayList<Rotor> inUse = new ArrayList<>();
    private Rotor reflector;

    //MODIFIES: this
    //EFFECTS: Initializes and sets the reflector, since it functions differently than the basic rotors
    public InUseRotors() {
        this.reflector = new Rotor();
        reflector.setReflector();
    }

    //REQUIRES: i is between 1 and #ofAvailableRotors inclusive, setting is between 0 and 25 inclusive
    //MODIFIES: this
    //EFFECTS: adds a new Rotor to the end of inUse with setting as initial steps setting
    public void addRotor(Integer rotorLabel, Integer setting) {
        Rotor rotor = new Rotor(rotorLabel);
        rotor.setRotor(setting);
        inUse.add(rotor);
    }

    //REQUIRES: At least one rotor is in the inUse rotor array
    //MODIFIES: this
    //EFFECTS: step all rotors, if a rotor has completes a rotation then the next rotor is also stepped etc.
    public void stepRotors() {
        for (Rotor r: inUse) {
            r.stepRotor();
            if (r.getSteps() != 0) {
                break;
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

    //REQUIRES: rotorNum is accessing a rotor that is already added to inUse
    //MODIFIES: this
    //EFFECTS: sets a rotor's start point and current setting after it has already been set and added
    public void resetRotorDetails(int rotorNum, int newSetting) {
        int i = rotorNum - 1;
        inUse.get(i).setRotor(newSetting);
    }

    //REQUIRES: rotorNum must refer to a rotor that has already been added to inUse
    //MODIFIES: this
    //EFFECTS: removes the given rotor from the inUse list
    public void deleteRotor(int rotorNum) {
        int i = rotorNum - 1;
        inUse.remove(i);
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