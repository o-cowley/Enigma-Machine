package ui.Tools;

import model.InUseRotors;

import javax.swing.*;

// A PopUp that prompts a user to either load saved rotor settings, or to save the settings at the end
public class SaveLoadPopUp extends JFrame {
    //TODO: needs to know the main managing frame for functionality access

    JOptionPane buttons;
    int result;

    //EFFECTS: a constructor triggering a popup to ask if the user wants to load saved rotors at the start
    // or save at the end, based on the boolean input save
    public SaveLoadPopUp(boolean save) {
        buttons = new JOptionPane();
        initAction(save);
    }

    //MODIFIES: this, inUseRotors
    //EFFECTS: based on the user input, either loads or saves the rotor settings to/from file, closes/removes after
    public void initAction(boolean save) {
        if (save) {
            result = buttons.showConfirmDialog(this, "Would you like to load your settings?",
                    "", JOptionPane.YES_NO_OPTION);

        } else {
            result = buttons.showConfirmDialog(this, "Would you like to save your settings?",
                    "", JOptionPane.YES_NO_OPTION);
        }

    }

    public static void main(String[] args) {
        new SaveLoadPopUp(true);
    }
}
