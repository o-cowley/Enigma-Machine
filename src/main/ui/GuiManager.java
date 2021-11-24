package ui;

import exceptions.NoRotorsLoadedException;
import model.EventLog;
import model.Event;
import model.InUseRotors;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.guiexceptions.ContainsNonWordCharactersException;
import ui.guiexceptions.NoNewTextException;
import ui.tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

//Main GUI manager component, receives input from the three main components and triggers different popup actions
// as well as reacts to input from the popups. Used to centralize the encryption tools from InUseRotors, as well as
// to keep consistency in even handling
public class GuiManager extends JFrame {
    private static final String destination = "./data/encryptionSettings.json";

    private InUseRotors encryptionBox;
    private ButtonPanel buttons;
    private TextPane textPane;
    private RotorManager rotorManager;

    //MODIFIES: this
    //EFFECTS: constructor that initializes all fields and sets up component placement/settings, triggers start of gui
    // at the end of the setup process
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

        launchLoadOption();
    }

    //MODIFIES: this
    //EFFECTS: sets up the three main component panels, providing them with necessary association to this manager
    private void initPanels() {
        buttons = new ButtonPanel(this);
        textPane = new TextPane(this);
        rotorManager = new RotorManager(0, this);
    }

    //MODIFIES: this
    //EFFECTS: adds the different component panels to this frame to display
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
    //EFFECTS: locks all input buttons and triggers the starting popup that prompts a user to load data from a
    // file on opening
    private void launchLoadOption() {
        lockForPopUps();
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
    //EFFECTS: responds to user input to load rotors saved in file at the beginning of encryption process, or alerts
    // the user that they were unable to be loaded
    public void loadRotors() {
        try {
            readRotorsFromFile();
            rotorManager.updateList(encryptionBox.getRotorCount());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "We couldn't load the rotors from your save. "
                    + "You will have to add them yourself.", "No Save Found!", JOptionPane.ERROR_MESSAGE);
        }
        unlockForPopUps();
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

    //MODIFIES: this
    //EFFECTS: triggers a popup that prompts a user to select the type of rotor to add to their encryption box
    public void triggerAddPop() {
        lockForPopUps();
        new AddPopUp(this.getLocation(), encryptionBox.getAvailableRotorTypes(), this);
    }

    //MODIFIES: this
    //EFFECTS: adds a new rotor to the visible manager pane and the encryption box, reacting to user input
    public void reactToAddPop(int i) {
        encryptionBox.addRotor(i, 0);
        rotorManager.addNewElement();
        unlockForPopUps();
    }

    //MODIFIES: this
    //EFFECTS: initiates a popup to prompt for a change in user settings, locks rotorManager pane so users can't
    // change highlighted input
    public void triggerEditPop() {
        int i = rotorManager.returnSelected();
        if (i >= 0) {
            lockForPopUps();
            new EditPopUp(this.getLocation(), encryptionBox.getRotorType(i),
                    encryptionBox.returnStartPoint(i), this);
        }
    }

    //MODIFIES: this
    //EFFECTS: updates a selected rotor to a new setting, reacting to user input from EditPopUp
    public void reactToEditPop(int newSetting) {
        encryptionBox.resetRotorDetails(rotorManager.returnSelected(), newSetting);
        unlockForPopUps();
    }

    //MODIFIES: this
    //EFFECTS: locks user access to the rotorManager pane and buttons while pop ups are being interacted with
    public void lockForPopUps() {
        rotorManager.lockRotors();
        buttons.lockForPopUp();
    }

    //MODIFIES: this
    //EFFECTS: allows user access to the rotorManager pane and buttons
    public void unlockForPopUps() {
        rotorManager.unlockRotors();
        buttons.unlockForPopUp();
    }

    //MODIFIES: this
    //EFFECTS: responds to user request to encrypt text from textArea, encrypts if possible, triggers warning if
    // un-encryptable characters are in the input field
    public void triggerEncrypt() {
        if (encryptionBox.getRotorCount() == 0) {
            JOptionPane.showMessageDialog(this, "You forgot to add rotors!",
                    "Rotors Missing!", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                String plainText = textPane.getTextToEncrypt();
                String cypherText = encryptionBox.encodeFullMessage(plainText);
                textPane.printEncryptedString(cypherText);
            } catch (ContainsNonWordCharactersException e) {
                JOptionPane.showMessageDialog(this, "<html> You seem to have included "
                                + "some invalid characters, <br>make sure to use the 'Clean up text' button to get rid "
                                + "of them before encrypting</html>",
                        "Un-encryptable characters!", JOptionPane.WARNING_MESSAGE);
            } catch (NoNewTextException e) {
                JOptionPane.showMessageDialog(this, "You haven't entered any new text yet!",
                        "No New Text!", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    //MODIFIES: this, manager
    //EFFECTS: adds a windowClosing listener, triggers the save popup instead of closing, locks panel to avoid
    // additional inputs while executing this popup
    private void setUpClose(GuiManager manager) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                lockForPopUps();
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
        printLog(EventLog.getInstance());
        System.exit(0);
    }

    //MODIFIES: this
    //EFFECTS: writes the encryptionBox to file as JSON, to be used next time, or alerts user if file could not be
    // found
    private void writeRotorsToFile() {
        JsonWriter jsonWriter = new JsonWriter(destination);
        try {
            jsonWriter.open();
            jsonWriter.write(encryptionBox);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to find the save file! Please verify"
                            + " that it exists.",
                    "No Save File!", JOptionPane.WARNING_MESSAGE);
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

    //MODIFIES: this
    //EFFECTS: Triggers the method to remove all un-encryptable characters from the text input pane
    public void triggerTextClean() {
        textPane.cleanUpInput();
    }

    //EFFECTS: returns the String representation of a Rotor to be displayed as toolTipText
    public String getRotorString(int index) {
        return encryptionBox.getRotorName(index);
    }

    private void printLog(EventLog e) {
        for (Event event: e) {
            System.out.println(event.toString());
        }
    }
}
