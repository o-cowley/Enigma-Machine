package ui.Tools;

import ui.GuiExceptions.ContainsNonWordCharactersException;
import ui.GuiExceptions.NoNewTextException;
import ui.GuiManager;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;

//The text area to add the input to be encrypted, with another pane below to display the output once encrypted
public class TextPane extends JPanel {
    GuiManager manager;

    private final String inputMessage = "Enter your text to encrypt in this area";

    JScrollPane scrollInput;
    JScrollPane scrollOuput;

    JTextArea textAreaInput;
    JTextArea textAreaOutput;

    //EFFECTS: Constructs the text pane with both TextAreas, input area is editable and output is not
    public TextPane(GuiManager manager) {
        setLayout(new GridBagLayout());
        this.manager = manager;


        initTextArea();
        enableActions();
        addToPane();

        setPreferredSize(getPreferredSize());
    }

    //MODIFIES: this
    //EFFECTS: initializes all the textareas and adds them to scroll panes to allow visibility of text
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

        JButton button = new JButton("Disable/Enable");
        button.addActionListener((event) -> {
            if (textAreaInput.isEnabled()) {
                textAreaInput.setEnabled(false);
            } else {
                textAreaInput.setEnabled(true);
            }
        });
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(button, c);
    }

    //EFFECTS: gets the text from the user input to encrypt
    //      Throws NoNewTextException if the input prompt remains unchanged or if the input field is empty
    //      Throws ContainsNonWordCharactersException if the field has characters that are not spaces or alphabetic
    public String getTextToEncrypt() throws ContainsNonWordCharactersException, NoNewTextException {
        String toEncrypt = textAreaInput.getText();
        if (toEncrypt.equalsIgnoreCase(inputMessage) || toEncrypt.length() == 0) {
            throw new NoNewTextException();
        } else if (containsBadCharacters(toEncrypt)) {
            throw new ContainsNonWordCharactersException();
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

    //The design of this code comes from the website
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
    //EFFECTS: sets the output text to be str, intended to be used as
    public void printEncryptedString(String str) {
        textAreaOutput.setText(str);
//        String output = "";
//        String input = textAreaInput.getText();
//        int i = 0;
//        while (i < str.length()) {
//            output = str.substring(0, i) + input.substring(i, str.length());
//            textAreaOutput.setText(output);
//            Thread.sleep(1000);
//            i++;
//        }

    }
}
