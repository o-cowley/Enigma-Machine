package persistence;

import model.InUseRotors;
import org.json.JSONObject;

import java.io.*;

public class JsonWriter {
    private PrintWriter writer;
    private String destination;

    //MODIFIES: this
    //EFFECTS: creates new JsonWriter with the file destination
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //MODIFIES: this
    //EFFECTS: opens a new writer, throws FileNotFoundException if the destination can not be found for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter((new File(destination)));
    }

    //MODIFIES: this
    //EFFECTS: writes the JSON representation of the InUseRotors to file
    // relies on toJson() that is a method in InUseRotors, to put things in to the right format to
    // be written (to be read)
    public void write(InUseRotors iur) {
        JSONObject json = iur.toJson();
        writeToFile(json.toString(4));
    }

    //MODIFIES: this
    //EFFECTS: writes a string to file
    private void writeToFile(String json) {
        writer.print(json);
    }

    //MODIFIES: this
    //EFFECTS: closes the writer
    public void close() {
        writer.close();
    }
}
