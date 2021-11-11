package ui;

import exceptions.NoRotorsLoadedException;
import model.InUseRotors;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.GuiExceptions.ContainsNonWordCharactersException;
import ui.Tools.*;

import javax.swing.*;
import java.awt.*;
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initPanels();
        addPanels();

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

    private void startProgram() {
        //TODO WELCOME SCREEN
        new SaveLoadPopUp(true, this);
    }

    //MODIFIES: this, c
    //EFFECTS: sets the constraints of x and y position to use the GridBagConstraints object to add a component to
    // the main panel
    private void setGridPos(GridBagConstraints c, int x, int y) {
        c.gridx = x;
        c.gridy = y;
    }

    public void loadRotors() {
        try {
            readRotorsFromFile();
            rotorManager.updateList(encryptionBox.getRotorCount());
        } catch (Exception e) {
            //TODO: popup to indicate something failed in the reading
        }
    }

    public void deleteRotor() {
        int i = rotorManager.returnSelected();
        encryptionBox.deleteRotor(i);
        rotorManager.removeSelectedElement();
    }

    public void triggerAddPop() {
        new AddPopUp(this.getLocation(), encryptionBox.getAvailableRotorTypes(), this);
    }

    public void reactAddPop(int i) {
        encryptionBox.addRotor(i, 0);
        rotorManager.addNewElement();
    }

    public void triggerEditPop() {
        int i = rotorManager.returnSelected();
        if (i >= 0) {
            new EditPopUp(this.getLocation(), encryptionBox.getRotorType(i),
                    encryptionBox.returnStartPoint(i), this);
        }
    }

    public void reactEditPop(int newSetting) {
        encryptionBox.resetRotorDetails(rotorManager.returnSelected(), newSetting);
    }

    public void triggerEncrypt() {
        try {
            String plainText = textPane.getTextToEncrypt();
            String cypherText = encryptionBox.encodeFullMessage(plainText);
            textPane.printEncryptedString(cypherText);
        } catch (ContainsNonWordCharactersException e) {
            //TODO deal with the bad text exception
        }

    }

    //MODIFIES: this
    //EFFECTS: writes the encryptionBox to file as JSON, to be used next time
    private void writeRotorsToFile() {
        JsonWriter jsonWriter = new JsonWriter(destination);
        try {
            jsonWriter.open();
            jsonWriter.write(encryptionBox);
            jsonWriter.close();
            //System.out.println("Rotors saved to file");
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
