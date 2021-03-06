package model;

import persistence.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

//This class represents all the necessary data for the encryption process, each array contains the associated
// shift for each letter of the alphabet which can be accessed by calling the data in the 0 based index of the
// letters (A-Z: 0-25). The reflector is kept separate as it has a different style of encrypting which requires it to
// be used differently
public class RotorCase {

    private static final ArrayList<Integer> rotor1Out = new ArrayList(Arrays.asList(4, 9, 10, 2, 7, 1, -3, 9, 13, 16,
            3, 8, 2, 9, 10, -8, 7, 3, 0, -4, -20, -13, -21, -6, -22, -16));
    private static final ArrayList<Integer> rotor1Return = new ArrayList(Arrays.asList(20, 21, 22, 3, -4, -2, -1, 8,
            13, 16, -9, -7, -10, -3, -2, 4, -9, 6, 0, -8, -3, -13, -9, -7, -10, -16));
    private static final ArrayList<Integer> rotor2Out = new ArrayList(Arrays.asList(0, 8, 1, 7, 14, 3, 11, 13, 15, -8,
            1, -4, 10, 6, -2, -13, 0, -11, 7, -6, -5, 3, -17, -2, -10, -21));
    private static final ArrayList<Integer> rotor2Return = new ArrayList(Arrays.asList(0, 8, 13, -1, 21, 17, 11, 4, -3,
            -8, -7, -1, 2, 6, 10, 5, 0, -11, -14, -6, -13, 2, -10, -15, -3, -7));
    private static final ArrayList<Integer> rotor3Out = new ArrayList(Arrays.asList(1, 2, 3, 4, 5, 6, -4, 8, 9, 10, 13,
            10, 13, 0, 10, -11, -8, 5, -12, -19, -10, -9, -2, -5, -8, -11));
    private static final ArrayList<Integer> rotor3Return = new ArrayList(Arrays.asList(19, -1, 4, -2, 11, -3, 12, -4,
            8, -5, 10, -6, 9, 0, 11, -8, 8, -9, 5, -10, 2, -10, -5, -13, -10, -13));

    private static final ArrayList<Integer> staticReflector = new ArrayList(Arrays.asList(4, 8, 10, 22, -4, 6, 18, 16,
            13, -8, 12, -6, -10, 4, 2, 5, -2, -4, 1, -1, -5, -13, -12, -16, -18, -22));

    private ArrayList<ArrayList<Integer>> rotorBox;

    //MODIFIES: this
    //EFFECTS: Constructor, reads rotors from file and loads them to rotorBox,
    //          if file is not found then only 3 are loaded instead using static class data
    public RotorCase(String destination) {
        JsonReader jsonReader = new JsonReader(destination);
        rotorBox = new ArrayList<>();
        try {
            jsonReader.addRotorDataFromFile(rotorBox);
        } catch (IOException e) {
            loadArraysDirectlyFromClass();
        }
    }

    //Modifies: this
    //Effects: adds a reduced version of the necessary arrays for the encryption process, to the rotorBox array.
    // In numerical order to facilitate getting of the needed arrays of data
    public void loadArraysDirectlyFromClass() {
        rotorBox.add(rotor1Out);
        rotorBox.add(rotor1Return);
        rotorBox.add(rotor2Out);
        rotorBox.add(rotor2Return);
        rotorBox.add(rotor3Out);
        rotorBox.add(rotor3Return);

    }

    //Requires: an integer between 0 and #ofAvailableArrays - 1
    //Effects: returns the requested array of integers based on the input request
    public ArrayList<Integer> getRotorArray(Integer i) {
        return rotorBox.get(i);
    }

    //Effects: returns the designated static reflector array, this array has different behaviour and as such is stored
    // in a different way from the main encryption arrays
    public ArrayList<Integer> getReflector() {
        return staticReflector;
    }

    //EFFECTS: returns the number of available rotors, rotorBox contains two arrays per available
    // rotor (out and return)
    public Integer availableArrays() {
        return rotorBox.size() / 2;
    }

}