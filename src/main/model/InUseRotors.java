package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//This is a representation of the active rotors that are being used for the encryption process. Functioning as if a
// user would pick rotors from the RotorCase and add them to the InUseRotor to be actively used in the letter
// shifting process
public class InUseRotors {

    private ArrayList<Rotor> inUse = new ArrayList<>();
    private Rotor reflector;
    private int availableRotorTypes;

    //MODIFIES: this
    //EFFECTS: Constructor, initializes and sets the reflector rotor, retrieves number of different rotors
    // available for selection
    public InUseRotors() {
        this.reflector = new Rotor();
        reflector.setReflector();

        availableRotorTypes = reflector.getAvailableRotors();
    }

    //REQUIRES: i is between 1 and #ofAvailableRotors inclusive, setting is between 0 and 25 inclusive
    //MODIFIES: this
    //EFFECTS: adds a new Rotor to the end of inUse with setting as initial steps setting
    public void addRotor(Integer rotorLabel, Integer setting) {
        Rotor rotor = new Rotor(rotorLabel);
        rotor.setRotor(setting);
        inUse.add(rotor);
    }

    //MODIFIES: this
    //EFFECTS: step all rotors, if a rotor has completes a rotation then the next rotor along is also stepped etc.
    public void stepRotors() {
        for (Rotor r: inUse) {
            r.stepRotor();
            if (r.getSteps() != 0) {
                break;
            }
        }
    }

    //REQUIRES: i be between 0 and 25, inclusive
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

    //REQUIRES: rotorNum refers to a rotor that is in inUse
    //MODIFIES: this
    //EFFECTS: sets a rotor's start point and current setting to newSetting after it has already been set and added
    public void resetRotorDetails(int rotorNum, int newSetting) {
        int i = rotorNum - 1;
        inUse.get(i).setRotor(newSetting);
    }

    //REQUIRES: rotorNum refers to a rotor that is in inUse
    //MODIFIES: this
    //EFFECTS: removes the given rotor from the inUse list
    public void deleteRotor(int rotorNum) {
        inUse.remove(rotorNum);
    }

    //EFFECTS: returns a new JSON Object of all rotors in the current inUse, rotors are represented in JSON Array
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("rotors", rotorsToJson());
        return json;
    }


    //EFFECTS: goes through inUse and converts each rotor in to a JSON object and then adds it to a
    // JSON Array, returns array
    private JSONArray rotorsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Rotor r: inUse) {
            jsonArray.put(r.toJson());
        }

        return jsonArray;
    }

    //REQUIRES: r to not be a rotor set as reflector
    //MODIFIES this
    //EFFECTS: adds an already made rotor to inUse, takes advantage of JSON data and new Rotor constructor
    public void addCompleteRotor(Rotor r) {
        inUse.add(r);
    }

    //Getters:

    //EFFECTS: returns an ordered list of start points for the rotors used in the encryption
    public List<Integer> returnStartPoints() {
        List<Integer> originalSettings = new ArrayList<>();
        for (Rotor r: inUse) {
            originalSettings.add(r.getStartPoint());
        }
        return originalSettings;
    }

    //EFFECTS: returns the startpoint of a given rotor in the inUse array
    public int returnStartPoint(int rotorIndex) {
        return inUse.get(rotorIndex).getStartPoint();
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

    //EFFECTS: return the available different rotors from RotorCase
    public int getAvailableRotorTypes() {
        return availableRotorTypes;
    }
}