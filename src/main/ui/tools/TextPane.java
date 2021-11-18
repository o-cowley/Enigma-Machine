package ui.tools;

import ui.guiexceptions.ContainsNonWordCharactersException;
import ui.guiexceptions.NoNewTextException;
import ui.GuiManager;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

//The text area to add the input to be encrypted, with another pane below to display the output once encrypted
public class TextPane extends JPanel {
    private final String inputMessage = "Enter your text to encrypt in this area";

    GuiManager manager;

    HashMap<String, String> bonusInputs;

    JScrollPane scrollInput;
    JScrollPane scrollOuput;
    JTextArea textAreaInput;
    JTextArea textAreaOutput;

    //MODIFIES: this
    //EFFECTS: Constructs the text pane with both TextAreas, input area is editable and output is not
    public TextPane(GuiManager manager) {
        setLayout(new GridBagLayout());
        this.manager = manager;

        initTextArea();
        enableActions();
        addToPane();
        addMouse();
        buildMap();

        setPreferredSize(getPreferredSize());
    }

    //MODIFIES: this
    //EFFECTS: initializes all the text areas and adds them to scroll panes to allow visibility of text
    // added beyond window size
    private void initTextArea() {

        textAreaInput = new JTextArea(inputMessage);
        textAreaInput.setLineWrap(true);
        textAreaInput.setWrapStyleWord(true);
        scrollInput = new JScrollPane(textAreaInput);
        scrollInput.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        textAreaOutput = new JTextArea("Once encrypted, your text will appear here");
        textAreaOutput.setLineWrap(true);
        textAreaOutput.setWrapStyleWord(true);
        textAreaOutput.setEditable(false);

        scrollOuput = new JScrollPane(textAreaOutput);
        scrollOuput.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        scrollInput.setPreferredSize(new Dimension(550,200));
        scrollOuput.setPreferredSize(new Dimension(550,100));
    }

    //MODIFIES: this
    //EFFECTS: Adds the text areas to the TextPane
    private void addToPane() {
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;

        c.insets = new Insets(5,5,5,5);
        c.gridy = 0;
        add(scrollInput, c);
        c.weighty = 0.5;
        c.gridy = 1;
        add(scrollOuput, c);
    }

    //MODIFIES: this
    //EFFECTS: clears the initial message text when the input window is initially clicked
    public void addMouse() {
        textAreaInput.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (textAreaInput.contains(e.getPoint())) {
                    if (textAreaInput.getText().equalsIgnoreCase(inputMessage)) {
                        textAreaInput.setText("");
                    }
                }
            }
        });
    }

    //EFFECTS: gets the text from the user input to encrypt
    //      Throws NoNewTextException if the input prompt remains unchanged or if the input field is empty
    //      Throws ContainsNonWordCharactersException if the field has characters that are not spaces or alphabetic
    public String getTextToEncrypt() throws ContainsNonWordCharactersException, NoNewTextException {
        String toEncrypt = textAreaInput.getText();
        String toEncryptLower = toEncrypt.toLowerCase();
        if (toEncrypt.equalsIgnoreCase(inputMessage) || toEncrypt.length() == 0) {
            throw new NoNewTextException();
        } else if (containsBadCharacters(toEncrypt)) {
            throw new ContainsNonWordCharactersException();
        } else if (bonusInputs.containsKey(toEncryptLower)) {
            new EasterEgg(bonusInputs.get(toEncryptLower));
            return toEncrypt;
        } else {
            return toEncrypt;
        }
    }

    //EFFECTS: returns false if there is a character in the string that is not alphabetic or a space
    private boolean containsBadCharacters(String toEncrypt) {
        char[] array = toEncrypt.toCharArray();
        for (char c: array) {
            if (!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                return true;
            }
        }
        return false;
    }

    //MODIFIES: this
    //EFFECTS: removes all non-alphabetic or non-space characters from the text input pane
    public void cleanUpInput() {
        StringBuilder cleanString = new StringBuilder();
        char[] characters = textAreaInput.getText().toCharArray();
        for (char c: characters) {
            if (Character.isLetter(c) || Character.isSpaceChar(c)) {
                cleanString.append(c);
            }
        }
        String cleaned = cleanString.toString();
        textAreaInput.setText(cleaned);
    }

    //The design of this code is influenced by an example from the website
    //https://www.programcreek.com/java-api-examples/?api=javax.swing.text.DefaultEditorKit
    //MODIFIES: this
    //EFFECTS: turns on Cut/Copy/Paste to system clipboard for the text areas--triggered by ctrl-x/c/v
    private void enableActions() {
        Action copyAction = this.textAreaInput.getActionMap().get(DefaultEditorKit.copyAction);
        copyAction.setEnabled(true);
        copyAction = this.textAreaOutput.getActionMap().get(DefaultEditorKit.copyAction);
        copyAction.setEnabled(true);
        Action cutAction = this.textAreaInput.getActionMap().get(DefaultEditorKit.cutAction);
        cutAction.setEnabled(true);
        cutAction = this.textAreaOutput.getActionMap().get(DefaultEditorKit.cutAction);
        cutAction.setEnabled(true);
    }

    //MODIFIES: this
    //EFFECTS: sets the output text field to str
    public void printEncryptedString(String str) {
        textAreaOutput.setText(str);
    }

    //MODIFIES: this
    //EFFECTS: assembles the hashmap of the various inputs that result in an easter egg popup
    private void buildMap() {
        bonusInputs = new HashMap<>();
        bonusInputs.put("what is the meaning of life", "./data/42.png");
        bonusInputs.put("damn daniel", "./data/whiteVans.png");
        bonusInputs.put("hey a dog", "./data/tobs.jpg");
    }
}
