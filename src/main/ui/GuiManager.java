package ui;

import exceptions.NoRotorsLoadedException;
import model.InUseRotors;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.GuiExceptions.ContainsNonWordCharactersException;
import ui.Tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GuiManager extends JFrame {
    private static final String destination = "./data/encryptionSettings.json";

    private InUseRotors encryptionBox;
    private ButtonPanel buttons;
    private TextPane textPane;
    private RotorManager rotorManager;

    public GuiManager() {
        encryptionBox = new InUseRotors();

        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        initPanels();
        addPanels();
        setUpClose(this);

        pack();

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        startProgram();

    }

    private void initPanels() {
        buttons = new ButtonPanel(this);
        textPane = new TextPane(this);
        rotorManager = new RotorManager(0, this);
    }

    private void addPanels() {
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        setGridPos(c,0,0);
        add(textPane, c);
        c.weightx = 0.25;
        c.gridwidth = 1;
        c.gridheight = 2;
        setGridPos(c,1,0);
        add(rotorManager, c);
        c.weightx = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        setGridPos(c,0,1);
        add(buttons, c);
    }

    //MODIFIES: this
    //EFFECTS: triggers the popup that prompts a user to load data from a file
    private void startProgram() {
        //TODO WELCOME SCREEN and make sure to lock access until after save and load popup
        new SaveLoadPopUp(true, this);
    }

    //MODIFIES: this, c
    //EFFECTS: sets the constraints of x and y position to use the GridBagConstraints object to add a component to
    // the main panel
    private void setGridPos(GridBagConstraints c, int x, int y) {
        c.gridx = x;
        c.gridy = y;
    }

    //MODIFIES: this
    //EFFECTS: responds to user input to load rotors saved in file at the beginning of encryption process
    public void loadRotors() {
        try {
            readRotorsFromFile();
            rotorManager.updateList(encryptionBox.getRotorCount());
        } catch (Exception e) {
            //TODO: popup to indicate something failed in the reading
        }
    }

    //MODIFIES: this
    //EFFECTS: if a rotor is selected, removes that rotor from the rotorManager pane and removes corresponding
    // rotor from encryptionBox
    public void deleteRotor() {
        int i = rotorManager.returnSelected();
        if (i >= 0) {
            encryptionBox.deleteRotor(i);
        }
        rotorManager.removeSelectedElement();
    }

    public void triggerAddPop() {
        new AddPopUp(this.getLocation(), encryptionBox.getAvailableRotorTypes(), this);
    }

    public void reactToAddPop(int i) {
        encryptionBox.addRotor(i, 0);
        rotorManager.addNewElement();
    }

    //MODIFIES: this
    //EFFECTS: initiates a popup to prompt for a change in user settings, locks rotorManager pane so users can't
    // change highlighted input
    public void triggerEditPop() {
        int i = rotorManager.returnSelected();
        if (i >= 0) {
            rotorManager.lockRotors();
            new EditPopUp(this.getLocation(), encryptionBox.getRotorType(i),
                    encryptionBox.returnStartPoint(i), this);
        }
    }

    //MODIFIES: this
    //EFFECTS: updates a selected rotor to a new setting, reacting to user input from EditPopUp
    public void reactToEditPop(int newSetting) {
        encryptionBox.resetRotorDetails(rotorManager.returnSelected(), newSetting);
        unlockRotorManager();
    }

    //MODIFIES: this
    //EFFECTS: allows user access to the rotorManager pane
    public void unlockRotorManager() {
        rotorManager.unlockRotors();
    }

    //MODIFIES: encryptionBox
    //EFFECTS: responds to user request to encrypt text from textArea, encrypts if possible, triggers warning if
    // un-encryptable characters are in the input field
    public void triggerEncrypt() {
        try {
            String plainText = textPane.getTextToEncrypt();
            String cypherText = encryptionBox.encodeFullMessage(plainText);
            textPane.printEncryptedString(cypherText);
        } catch (ContainsNonWordCharactersException e) {
            //TODO deal with the bad text exception
        }

    }

    //MODIFIES: this, manager
    //EFFECTS: adds a windowClosing listener, triggers the save popup instead of closing
    private void setUpClose(GuiManager manager) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                new SaveLoadPopUp(false, manager);
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: responds to user input, saves system if desired, then exits program
    public void triggerSave(boolean save) {
        if (save) {
            writeRotorsToFile();
        }
        System.exit(0);

    }

    //MODIFIES: this
    //EFFECTS: writes the encryptionBox to file as JSON, to be used next time
    private void writeRotorsToFile() {
        JsonWriter jsonWriter = new JsonWriter(destination);
        try {
            jsonWriter.open();
            jsonWriter.write(encryptionBox);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            //System.out.println("Sorry, the destination file wasn't found");
        }

    }

    //MODIFIES: this
    //EFFECTS: reads rotor settings from file and sets up encryptionBox accordingly
    //         Throws IOException if file not found
    //         Throws NoRotorsLoadedException if save file did not have any rotors loaded
    private void readRotorsFromFile() throws IOException, NoRotorsLoadedException {
        JsonReader jsonReader = new JsonReader(destination);
        encryptionBox = jsonReader.readFile();
    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("couldn't load what you wanted");
        }
        new GuiManager();
    }
}
