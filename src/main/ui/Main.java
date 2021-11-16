package ui;

import javax.swing.*;

//Main class that is used to start the program
public class Main {

    //EFFECTS: GUI Launcher, also allows the use of different LookAndFeels for the program
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) {
            System.out.println("couldn't load what you wanted");
        }
        new StartGameHandler();
    }

    //javax.swing.plaf.metal.MetalLookAndFeel
    //com.sun.java.swing.plaf.motif.MotifLookAndFeel    great!! but save shortcuts not set up properly for it
    //javax.swing.plaf.nimbus.NimbusLookAndFeel        great!!
    //com.sun.java.swing.plaf.gtk.GTKLookAndFeel   only windows
    //
    //com.sun.java.swing.plaf.windows.WindowsLookAndFeel  windows only
    //com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel

    //com.apple.laf.AquaLookAndFeel  mac only
    // com.sun.java.swing.plaf.gtk.GTKLookAndFeel   not mac i think
}
