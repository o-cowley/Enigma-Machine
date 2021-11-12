package ui.Tools;

import ui.GuiManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// A PopUp window that prompts a user to select the type of rotor to add to their current inUseRotors
//  the selected rotor is then added to the system
public class AddPopUp extends JFrame {
    GuiManager manager;

    JPanel pane;
    JComboBox dropDown;
    JButton done;
    JButton close;
    JLabel label;

    //EFFECTS: Constructs a PopUp that requests user input to select a rotor to add to the rotorBox
    public AddPopUp(Point p, int rotorRange, GuiManager manager) {
        this.setLayout(new GridBagLayout());
        this.setTitle("Add Rotor");
        this.manager = manager;

        pane = new JPanel(new GridBagLayout());
        initializeButtons(rotorRange);
        addButtons();
        setUpClose();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(pane.getPreferredSize());
        pack();
        setResizable(false);
        setLocation(p);
        setVisible(true);

    }

    //MODIFIES: this
    //EFFECTS: sets up the buttons and dropdown menu for user selection of a rotor to add
    private void initializeButtons(int range) {
        DefaultComboBoxModel<Integer> intList = new DefaultComboBoxModel<>();
        for (int i = 1; i <= range; i++) {
            intList.addElement(i);
        }

        dropDown = new JComboBox(intList);
        dropDown.setPrototypeDisplayValue("XX");
        dropDown.setPreferredSize(dropDown.getPreferredSize());

        label = new JLabel("Please select a Rotor type to add:");
        label.setPreferredSize(label.getPreferredSize());

        done = new JButton("Done");
        done.setPreferredSize(done.getPreferredSize());
        done.addActionListener((event) -> {
            manager.reactToAddPop(intList.getElementAt(dropDown.getSelectedIndex()));
            this.dispose();
        });

        close = new JButton("Close");
        close.addActionListener((event) -> {
            manager.unlockForPopUps();
            this.dispose();
        });
    }

    //MODIFIES: this
    //EFFECTS: Positions the buttons on the panel and then puts panel on to the JFrame
    private void addButtons() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.weightx = 1;
        setGridPos(c,0,1);
        c.gridwidth = 2;
        pane.add(dropDown, c);
        c.gridwidth = 1;
        setGridPos(c,2,1);
        pane.add(close, c);
        setGridPos(c,3,1);
        pane.add(done, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        setGridPos(c, 0, 0);
        c.gridwidth = 4;
        pane.add(label, c);

        setGridPos(c,0,0);
        add(pane, c);
    }

    //MODIFIES: this, c
    //EFFECTS: sets the constraints of x and y position to use the GridBagConstraints object to add a component to
    // the main panel
    private void setGridPos(GridBagConstraints c, int x, int y) {
        c.gridx = x;
        c.gridy = y;
    }

    //MODIFIES: this
    //EFFECTS: sets the behaviour for when the window is closed, should unlock the various components that were locked
    // upon triggering
    private void setUpClose() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                manager.unlockForPopUps();
            }
        });
    }



//    public static void main(String[] args) {
//        JFrame mainFrame = new JFrame();
//        mainFrame.setLocationRelativeTo(null);
//
//        JButton but = new JButton("pop");
//        but.setPreferredSize(new Dimension(100,100));
//        //but.addActionListener((event) -> new AddPopUp(new Point(mainFrame.getX() + 100, mainFrame.getY()),
//         //       6));
//
//        mainFrame.add(but);
//
//        mainFrame.setSize(mainFrame.getPreferredSize());
//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainFrame.setVisible(true);
//
//
//    }


}




