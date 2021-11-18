package ui.tools;

import ui.GuiManager;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// A PopUp that prompts a user to either load saved rotor settings, or to save the settings at the end
public class SaveLoadPopUp extends JFrame {
    GuiManager manager;

    JOptionPane buttons;
    int result;

    //MODIFIES: this
    //EFFECTS: a constructor triggering a popup to ask if the user wants to load saved rotors at the start
    // or save at the end, based on the boolean input save
    public SaveLoadPopUp(boolean save, GuiManager guiManager) {
        buttons = new JOptionPane();
        manager = guiManager;
        initAction(save);
    }

    //MODIFIES: this
    //EFFECTS: based on the user input, either loads or saves the rotor settings to/from file, closes/removes after
    // unlocks the manager after input is received
    public void initAction(boolean save) {
        if (save) {
            result = buttons.showConfirmDialog(this, "Would you like to load your settings?",
                    "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                manager.loadRotors();
            } else {
                manager.unlockForPopUps();
            }
        } else {
            result = buttons.showConfirmDialog(this, "Would you like to save your settings?",
                    "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            manager.triggerSave(result == JOptionPane.YES_OPTION);
        }
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                manager.unlockForPopUps();
            }
        });
    }
}