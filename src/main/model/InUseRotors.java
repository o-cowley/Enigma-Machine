package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        for (int y = (inUse.size() - 1); y >= 0; y--) {
            x = inUse.get(y).shiftLetter(x, true);
        }
        return x;
    }

    public String encooooode(String str) {
        List<Integer> convertedForEncoding = getValuesToEncode(str);
        for (int i = 0; i < convertedForEncoding.size(); i++) {
            this.stepRotors();
            int a = this.encode(convertedForEncoding.get(i));
            convertedForEncoding.set(i, a);
        }
        return returnEncodedToString(convertedForEncoding);
    }

    private static List<Integer> getValuesToEncode(String str) {
        String strNoSpaces = str.toUpperCase().replaceAll("\\s", "");
        List<Integer> toConvert = new ArrayList<>();
        for (int i = 0; i < strNoSpaces.length(); i++) {
            int value = strNoSpaces.charAt(i);
            value -= 65;
            toConvert.add(value);
        }
        return toConvert;
    }

    // website used to help for conversion of a list of characters (stored as integer values) back to a single string
    // https://stackoverflow.com/questions/6324826/converting-arraylist-of-characters-to-a-string
    private static String returnEncodedToString(List<Integer> list) {
        StringBuilder builder = new StringBuilder(list.size());
        for (int i: list) {
            int shiftedBack = i + 65;
            char c = (char) shiftedBack;
            builder.append(c);
        }
        return builder.toString();
    }


    //Effects: returns an ordered list of start points for the rotors used in the encryption
    public List<Integer> returnStartPoints() {
        List<Integer> originalSettings = new ArrayList<>();
        for (Rotor r: inUse) {
            originalSettings.add(r.getStartPoint());
        }
        return originalSettings;
    }

    //Effects: returns number of rotors "installed" so far
    public Integer getRotorCount() {
        return inUse.size();
    }

    //Effects: Returns the current step setting of each rotor in the array
    public List<Integer> returnCurrentSettings() {
        List<Integer> originalSettings = new ArrayList<>();
        for (Rotor r: inUse) {
            originalSettings.add(r.getSteps());
        }
        return originalSettings;
    }

    //Effects: Returns the rotor type of each rotor in the array
    public List<Integer> returnRotorNames() {
        List<Integer> rotorLabels = new ArrayList<>();
        for (Rotor r: inUse) {
            rotorLabels.add(r.getLabel());
        }
        return rotorLabels;
    }



//    public static void main(String[] args) {
//        InUseRotors iu = new InUseRotors();
//        List<Integer> list = getValuesToEncode("Ahi  DEFGH IJKLMNOPQR STUVWXYZ");
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
//        String str = returnEncodedToString(list);
//        System.out.println(str);
//        iu.addRotor(1,0);
//        String abc = iu.encooooode("XUVBMKPWDYHEDARKABXLVCKTTW");
//
//        System.out.println(abc);
//
//    }


}