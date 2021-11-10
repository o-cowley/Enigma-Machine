package ui.Tools;

import model.InUseRotors;

import javax.swing.*;
import java.lang.reflect.Array;

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
                    "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        } else {
            result = buttons.showConfirmDialog(this, "Would you like to save your settings?",
                    "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        }

    }

    //javax.swing.plaf.metal.MetalLookAndFeel
    //com.sun.java.swing.plaf.motif.MotifLookAndFeel    great!!
    //javax.swing.plaf.nimbus.NimbusLookAndFeel        great!!
    //com.sun.java.swing.plaf.gtk.GTKLookAndFeel   only windows
    //
    //com.sun.java.swing.plaf.windows.WindowsLookAndFeel  windows only
    //com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel

    //com.apple.laf.AquaLookAndFeel  mac only
    // com.sun.java.swing.plaf.gtk.GTKLookAndFeel   note windows i think

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("couldn't load what you wanted");
        }
        new SaveLoadPopUp(true);
        for (UIManager.LookAndFeelInfo e: UIManager.getInstalledLookAndFeels()) {
            System.out.println(e.getName());
        }
    }
}