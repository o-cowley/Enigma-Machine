package persistence;

import exceptions.NoRotorsLoadedException;
import model.InUseRotors;
import model.Rotor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// A class with the tools necessary to get JSON data from file as a string, and provide it to necessary methods
// that can parse it and upload the settings accordingly
// Basic structure for this class was modelled off of JsonSerializationDemo from the project files
public class JsonReader {
    private String sourceFile;

    //MODIFIES: this
    //EFFECTS: Constructs a reader to read from the sourceFile
    public JsonReader(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    //EFFECTS: reads an InUseRotors from the file and returns it
    //         converts string from file in to a JSON Object then hands it off to be turned in to an InUseRotor
    //         Throws IOException if file is not found at sourceFile destination provided
    //         Throws NoRotorsLoadedException if no rotors were loaded from the save file
    public InUseRotors readFile() throws IOException, NoRotorsLoadedException {
        String jsonData = readFile(sourceFile);
        JSONObject jsonObject = new JSONObject(jsonData);

        return parseInUseRotors(jsonObject);
    }

    //EFFECTS: reads JSONObject from source file and returns as a single string
    //         Throws IOException it file is not found according to the path provided
    private String readFile(String source) throws IOException {
        StringBuilder jsonString = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> jsonString.append(s));
        }

        return jsonString.toString();
    }

    //EFFECTS: Creates a new InUseRotors from the JSON object and returns it
    //         creates new iur and then passes it to addRotors with necessary data to add all the rotors
    //         Throws NoRotorsLoadedException if no rotors could be loaded from the file
    private InUseRotors parseInUseRotors(JSONObject jsonObject) throws NoRotorsLoadedException {
        InUseRotors iur = new InUseRotors();
        addRotors(iur, jsonObject);
        if (iur.getRotorCount() == 0) {
            throw new NoRotorsLoadedException();
        }
        return iur;
    }

    //MODIFIES: iur
    //EFFECTS: adds all rotors from the JSON Object to the InUseRotors
    // gets entire array of objects and individually passes to addJsonRotor to add each as a rotor
    private void addRotors(InUseRotors iur, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("rotors");
        for (Object json: jsonArray) {
            JSONObject nextRotor = (JSONObject) json;
            addJsonRotor(iur, nextRotor);
        }
    }

    //MODIFIES: iur
    //EFFECTS: parses a single rotor from a JSON object then adds it to InUseRotors
    // calls on the rotor constructor that creates a complete rotor from a JSON object
    private void addJsonRotor(InUseRotors iur, JSONObject json) {
        Rotor toAdd = new Rotor(json);
        iur.addCompleteRotor(toAdd);
    }

    //MODIFIES: rotorBox
    //EFFECTS: Takes a JSONObject as string and converts JSONArrays inside to right array format then adds to rotorBox
    //         Throws IOException if file is not found
    public void addRotorDataFromFile(ArrayList<ArrayList<Integer>> rotorBox) throws IOException {
        String jsonData = readFile(sourceFile);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArray = (JSONArray) jsonObject.get("rotors");

        ArrayList<Integer> toAdd;

        for (Object json: jsonArray) {
            JSONArray nextRotor = (JSONArray) json;
            toAdd = jsonToArray(nextRotor);
            rotorBox.add(toAdd);
        }
    }

    //EFFECTS: Takes a JSONArray of int and produces a new ArrayList<Integer> of the data
    private ArrayList<Integer> jsonToArray(JSONArray jsonArray) {
        ArrayList<Integer> returnArray = new ArrayList<>();
        for (Object o : jsonArray) {
            int toAdd = (int) o;
            returnArray.add(toAdd);
        }

        return returnArray;
    }
}
