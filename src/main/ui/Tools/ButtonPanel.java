package ui.Tools;

import ui.GuiManager;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {
    GuiManager manager;

    JButton add;
    JButton delete;
    JButton edit;
    JButton encrypt;
    JButton clearInvalid;

    public ButtonPanel(GuiManager guiManager) {
        setLayout(new GridBagLayout());
        manager = guiManager;

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
        delete.addActionListener((event) -> deletePress());

        add = new JButton("Add Rotor");
        add.setPreferredSize(delete.getPreferredSize());
        add.addActionListener((event) -> addNewRotor());

        edit = new JButton("Edit Setting");
        edit.setPreferredSize(delete.getPreferredSize());
        edit.addActionListener((event) -> editPop());

        encrypt = new JButton("Encrypt Text");
        encrypt.setPreferredSize(encrypt.getPreferredSize());
        encrypt.addActionListener((event) -> encryptPress());

        clearInvalid = new JButton("Clear Invalid Characters");
        clearInvalid.setPreferredSize(encrypt.getPreferredSize());
        clearInvalid.addActionListener((event) -> doThing());
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
        c.fill = GridBagConstraints.BOTH;
        setGridPos(c,0,0);
        add(clearInvalid, c);
        setGridPos(c,0,1);
        c.gridheight = 2;
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

    private void addNewRotor() {
        manager.triggerAddPop();
    }

    private void deletePress() {
        manager.deleteRotor();
    }

    private void editPop() {
        manager.triggerEditPop();
    }

    private void encryptPress() {
        manager.triggerEncrypt();
    }

//    public static void main(String[] args) {
//        JFrame mainFrame = new JFrame();
//        mainFrame.setLocationRelativeTo(null);
//
//        //ButtonPanel buttonPanel = new ButtonPanel();
//        //mainFrame.add(buttonPanel);
//        mainFrame.setPreferredSize(mainFrame.getPreferredSize());
//
//        mainFrame.setSize(mainFrame.getPreferredSize());
//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainFrame.setVisible(true);
//    }
}
