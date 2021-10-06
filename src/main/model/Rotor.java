package model;

import java.util.ArrayList;

public class Rotor {
    private final RotorCase rotorCase = new RotorCase();

    private int steps;
    private int startPoint;
    private ArrayList<Integer> rotorOutData; //rotor pass before reflection
    private ArrayList<Integer> rotorReturnData; //rotor pass after reflection


    //Effects: Constructs a Rotor with the required Out and Return arrays for encryption
    public Rotor(Integer i) {
        this.steps = 0;
        this.startPoint = 0;
        this.rotorOutData = rotorCase.getRotorArray((2 * i - 2));
        this.rotorReturnData = rotorCase.getRotorArray((2 * i - 1));
    }

    //Requires: Constructor only called for the reflector rotor
    //Modifies: this
    //Effects: empty constructor only used for initializing the static reflector
    public Rotor() {
        setRotor(0);
    }

    //Requires: i = number % 26 and >=0
    //Modifies: this
    //Effects: sets the step and startPoint fields of the rotor
    public void setRotor(Integer i) {
        this.steps = i;
        this.startPoint = i;
    }

    //Modifies: this
    //Effects: Gets the Reflector data for the given rotor to be used as the reflector in the
    // encryption process
    public void setReflector() {
        this.rotorOutData = rotorCase.getReflector();
        this.rotorReturnData = rotorCase.getReflector();
    }

    //Requires: i is between 0 and 25 inclusive
    //Effects: returns the given integer shifted (+/-) according to the data array and the number of steps
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

    //Modifies: this
    //Effects: steps the rotor once, modulo 26
    public void stepRotor() {
        this.steps = (this.steps + 1) % 26;
    }

    public int getSteps() {
        return this.steps;
    }

    public int getStartPoint() {
        return this.startPoint;
    }

    public int getRotorOutData(int i) {
        return rotorOutData.get(i);
    }

    public int getRotorReturnData(int i) {
        return rotorReturnData.get(i);
    }


}
