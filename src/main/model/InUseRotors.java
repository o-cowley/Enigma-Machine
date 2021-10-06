package model;

import java.util.ArrayList;
import java.util.List;

public class InUseRotors {

    private ArrayList<Rotor> inUse = new ArrayList<>();
    private Rotor reflector;

    //Effects: Constructs an empty inUse ArrayList with the static reflector included separately
    public InUseRotors() {
        this.reflector = new Rotor();
        reflector.setReflector();
    }

    //Requires: i is between 0 and #ofAvailableRotors inclusive, setting is between 0 and 25 inclusive
    //Modifies: this
    //Effects: adds a new Rotor to inUse with the desired start setting
    public void addRotor(Integer i, Integer setting) {
        Rotor rotor = new Rotor(i);
        rotor.setRotor(setting);
        inUse.add(rotor);
    }

    //Requires: At least one rotor is in the inUse rotor array
    //Modifies: this
    //Effects: step all rotors if the preceding rotor has completed a rotation, to be done before every
    //instance of encode
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

    public int encode(Integer i) {
        int x = i;
        for (Rotor r: inUse) {
            x = r.shiftLetter(x, false);
        }
        x = reflector.shiftLetter(x, true);
        for (int y = (inUse.size() - 1); i >= 0; i--) {
            x = inUse.get(y).shiftLetter(x, true);
        }
        return x;
    }

    //Effects: returns an ordered list of start points for the rotors used in the encryption
    public List<Integer> returnStartPoints() {
        List<Integer> originalSettings = new ArrayList<>();
        for (Rotor r: inUse) {
            originalSettings.add(r.getStartPoint());
        }
        return originalSettings;
    }
}