package model;

import java.util.ArrayList;

public class Rotor {
    private final RotorCase rotorCase = new RotorCase();

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
    //EFFECTS: Sets the required Reflector array for this rotor to be used as the reflector
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
    //EFFECTS: increases the rotor steps by 1, modulo 26
    public void stepRotor() {
        this.steps = (this.steps + 1) % 26;
    }

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


}
