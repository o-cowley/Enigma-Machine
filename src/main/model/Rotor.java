package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

//This represents a single rotor with the required methods to pass a letter in and receive the encrypted/decrypted
// version back. The field data includes its rotation point so that the necessary shifting of the rotor can be
// simulated in the encryption process
public class Rotor {
    private static final String destination = "./data/rotorArrays.json";
    private static final RotorCase rotorCase = new RotorCase(destination);
    private static final int availableRotors = rotorCase.availableArrays();

    private int label;
    private int steps;
    private int startPoint;
    private ArrayList<Integer> rotorOutData; //rotor pass before reflection
    private ArrayList<Integer> rotorReturnData; //rotor pass after reflection


    //REQUIRES: Integer between 0 and # of valid data arrays (each diff. rotor has two sets of data in RotorCase)
    //EFFECTS: Constructs a Rotor with the required Out and Return data arrays, sets steps, start point to 0 as
    // default and sets the label indicating which rotor was requested
    public Rotor(Integer i) {
        this.label = i;
        this.steps = 0;
        this.startPoint = 0;
        this.rotorOutData = rotorCase.getRotorArray((2 * i - 2));
        this.rotorReturnData = rotorCase.getRotorArray((2 * i - 1));
    }

    //REQUIRES: json is a JSONObject that contains the required data for a rotor, as established by jsonWriter
    //MODIFIES: this
    //EFFECTS: constructs a new rotor from a JSON object containing the data
    public Rotor(JSONObject json) {
        this.label = (int) json.get("label");
        setRotor((int) json.get("start point"));

        JSONArray outArray = (JSONArray) json.get("out array");
        this.rotorOutData = jsonToArray(outArray);

        JSONArray inArray = (JSONArray) json.get("return array");
        this.rotorReturnData = jsonToArray(inArray);
    }

    //REQUIRES: called only to initialize the reflector rotor
    //MODIFIES: this
    //EFFECTS: empty constructor only used for initializing the static reflector, label is unnecessary
    public Rotor() {
        setRotor(0);
    }

    //REQUIRES: i is a number % 26 and >=0
    //MODIFIES: this
    //EFFECTS: sets the step and startPoint fields of the rotor
    public void setRotor(Integer i) {
        this.steps = i;
        this.startPoint = i;
    }

    //MODIFIES: this
    //EFFECTS: Sets the rotor as the Reflector array, for this rotor to be used as the reflector which has slightly
    // different functionality from a normal rotor
    public void setReflector() {
        this.rotorOutData = rotorCase.getReflector();
        this.rotorReturnData = rotorCase.getReflector();
    }

    //REQUIRES: i is between 0 and 25 inclusive
    //EFFECTS: returns the given integer shifted (+/-) according to the data array and the number of steps
    //taking into account the number of steps the array has gone through up to this point and the direction
    //through the machine (passes through rotorOutData first, rotorBackData after reflector
    public int shiftLetter(Integer i, Boolean direction) {
        int newSetting = (i + this.steps) % 26;
        int letterOut;
        if (!direction) {
            letterOut = (((i + getRotorOutData(newSetting) % 26) + 26) % 26);
        } else {
            letterOut = (((i + getRotorReturnData(newSetting) % 26) + 26) % 26);
        }
        return letterOut;
    }

    //MODIFIES: this
    //EFFECTS: increases the rotor steps field by 1, modulo 26
    public void stepRotor() {
        this.steps = (this.steps + 1) % 26;
    }

    //REQUIRES: JSONArray of Integers
    //EFFECTS: receives a JSON Array and converts it to an ArrayList<Integer> to be used as rotor data
    private ArrayList<Integer> jsonToArray(JSONArray jsonArray) {
        ArrayList<Integer> returnArray = new ArrayList<>();
        for (Object o: jsonArray) {
            int toAdd = (int) o;
            returnArray.add(toAdd);
        }

        return returnArray;
    }

    //EFFECTS: Produces a JSONObject that represents a complete rotor--includes array data for out and return arrays
    // everything is stored individually--current setting not necessary for how this will be used
    public JSONObject toJson() {
        JSONObject jsonRotor = new JSONObject();

        jsonRotor.put("label", label);
        jsonRotor.put("start point", startPoint);
        jsonRotor.put("out array", intArrayToJson(rotorOutData));
        jsonRotor.put("return array", intArrayToJson(rotorReturnData));

        return jsonRotor;
    }

    //EFFECTS: Returns JSONArray containing all data from a single ArrayList<Integer> used to store a full rotor
    // of data in a JSON Array to be stored in the JSON Object that will be written for each rotor
    private JSONArray intArrayToJson(ArrayList<Integer> array) {
        JSONArray jsonArray = new JSONArray();
        for (Integer i: array) {
            jsonArray.put(i);
        }

        return jsonArray;
    }

    //Getters:
    public int getSteps() {
        return this.steps;
    }

    public int getStartPoint() {
        return this.startPoint;
    }

    public int getLabel() {
        return label;
    }

    public int getRotorOutData(int i) {
        return rotorOutData.get(i);
    }

    public int getRotorReturnData(int i) {
        return rotorReturnData.get(i);
    }

    public int getAvailableRotors() {
        return availableRotors;
    }
}
