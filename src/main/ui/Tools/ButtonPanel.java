package ui.Tools;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {
    //TODO: add parent frame for functionality

    JButton add;
    JButton delete;
    JButton edit;
    JButton encrypt;

    public ButtonPanel() {
        setLayout(new GridBagLayout());

        initButtons();
        addButtons();

        Dimension bigger = getPreferredSize();
        bigger.height = bigger.height + 10;
        bigger.width = bigger.width + 5;
        setPreferredSize(bigger);

    }

    private void initButtons() {
        delete = new JButton("Delete Selected");
        delete.setPreferredSize(delete.getPreferredSize());
        delete.addActionListener((event) -> doThing());

        add = new JButton("Add Rotor");
        add.setPreferredSize(delete.getPreferredSize());
        add.addActionListener((event) -> doThing());

        edit = new JButton("Edit Setting");
        edit.setPreferredSize(delete.getPreferredSize());
        edit.addActionListener((event) -> doThing());

        encrypt = new JButton("Encrypt Text");
        encrypt.setPreferredSize(encrypt.getPreferredSize());
        encrypt.addActionListener((event) -> doThing());
    }

    private void addButtons() {
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.ipady = 5;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        setGridPos(c,1,0);
        add(add, c);
        setGridPos(c,1,1);
        add(edit, c);
        setGridPos(c,1,2);
        add(delete, c);
        c.fill = GridBagConstraints.NONE;
        setGridPos(c,0,0);
        c.gridheight = 3;
        c.fill = GridBagConstraints.VERTICAL;
        add(encrypt, c);
    }

    //MODIFIES: this, c
    //EFFECTS: sets the constraints of x and y position to use the GridBagConstraints object to add a component to
    // the main panel
    private void setGridPos(GridBagConstraints c, int x, int y) {
        c.gridx = x;
        c.gridy = y;
    }

    //MODIFIES: THEMAINSYSTEM
    //EFFECTS: general handler method for compiler check
    private void doThing() {
        //TODO: set individual action methods
    }


    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setLocationRelativeTo(null);

        ButtonPanel buttonPanel = new ButtonPanel();
        mainFrame.add(buttonPanel);
        mainFrame.setPreferredSize(mainFrame.getPreferredSize());

        mainFrame.setSize(mainFrame.getPreferredSize());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
