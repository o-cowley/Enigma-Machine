package ui.Tools;

import ui.GuiExceptions.ContainsNonWordCharactersException;

import javax.swing.*;
import java.awt.*;

//The text area to add the input to be encrypted, with another pane below to display the output once encrypted
public class TextPane extends JPanel {
    //TODO: add parent frame for functionality

    private final String inputMessage = "Enter your text to encrypt in this area";

    JScrollPane scrollInput;
    JScrollPane scrollOuput;

    JTextArea textAreaInput;
    JTextArea textAreaOutput;

    //EFFECTS: Constructs the text pane with both TextAreas, input area is editable and output is not
    public TextPane() {
        setLayout(new GridBagLayout());

        initTextArea();
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
    //      Throws ContainsNonWordCharactersException if the input prompt remains unchanged or if the input field
    //      is empty
    public String getTextToEncrypt() throws ContainsNonWordCharactersException {
        String toEncrypt = textAreaInput.getText();
        if (toEncrypt.equalsIgnoreCase(inputMessage) || toEncrypt.length() == 0) {
            throw new ContainsNonWordCharactersException();
        } else {
            return textAreaInput.getText();
        }
    }

    //MODIFIES: this
    //EFFECTS: sets the output text to be str, intended to be used as
    public void returnEncryptedMessage(String str) {
        textAreaOutput.setText(str);
    }

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setLocationRelativeTo(null);
        TextPane tp = new TextPane();

        mainFrame.add(tp);

        mainFrame.setSize(mainFrame.getPreferredSize());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

}
