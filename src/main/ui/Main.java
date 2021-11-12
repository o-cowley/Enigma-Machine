package ui;

import javax.swing.*;

//Main class that is used to start the program
public class Main {

    //program launcher
//    public static void main(String[] args) {
//        new UserSystem();
//    }

    //GUI Launcher
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("couldn't load what you wanted");
        }
        new GuiManager();
    }

    //javax.swing.plaf.metal.MetalLookAndFeel
    //com.sun.java.swing.plaf.motif.MotifLookAndFeel    great!!
    //javax.swing.plaf.nimbus.NimbusLookAndFeel        great!!
    //com.sun.java.swing.plaf.gtk.GTKLookAndFeel   only windows
    //
    //com.sun.java.swing.plaf.windows.WindowsLookAndFeel  windows only
    //com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel

    //com.apple.laf.AquaLookAndFeel  mac only
    // com.sun.java.swing.plaf.gtk.GTKLookAndFeel   not mac i think
}
