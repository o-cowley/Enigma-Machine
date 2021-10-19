package persistence;

import model.InUseRotors;
import model.Rotor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String sourceFile;

    //EFFECTS: Constructs a reader to read from the sourceFile
    public JsonReader(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    //EFFECTS: reads an InUseRotors from the file and returns it
    // converts string from file in to a JSON Object then hands it off to be turned in to an InUseRotor
    public InUseRotors readFile() throws IOException {
        String jsonData = readFile(sourceFile);
        JSONObject jsonObject = new JSONObject(jsonData);

        return parseInUseRotors(jsonObject);
    }

    //EFFECTS: reads from source file and returns as a single string
    private String readFile(String source) throws IOException {
        StringBuilder jsonString = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> jsonString.append(s));
        }

        return jsonString.toString();
    }

    //EFFECTS: Creates a new InUseRotors from the JSON object and returns it
    // creates new iur and then passes it to addRotors, as well as all the necessary data to add all the rotors
    private InUseRotors parseInUseRotors(JSONObject jsonObject) {
        InUseRotors iur = new InUseRotors();
        addRotors(iur, jsonObject);

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
    // calls on new type of rotor constructor that creates a compelte rotor from a JSON object
    private void addJsonRotor(InUseRotors iur, JSONObject json) {
        Rotor toAdd = new Rotor(json);
        iur.addCompleteRotor(toAdd);
    }
}
