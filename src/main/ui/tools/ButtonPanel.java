package ui.tools;

import ui.GuiManager;

import javax.swing.*;
import java.awt.*;

//The button panel for the GUI, it controls all aspects of the encryption process
public class ButtonPanel extends JPanel {
    GuiManager manager;

    JButton add;
    JButton delete;
    JButton edit;
    JButton encrypt;
    JButton clearInvalid;

    //MODIFIES: this
    //EFFECTS: constructor, initializes all buttons and adds them to the pane, sets preferred size
    public ButtonPanel(GuiManager guiManager) {
        setLayout(new GridBagLayout());
        manager = guiManager;

        initButtons();
        addButtons();
        setPreferredSize(getPreferredSize());
    }

    //MODIFIES: this
    //EFFECTS: initializes all buttons with required action listeners and size constraints
    private void initButtons() {
        delete = new JButton("Delete Selected");
        delete.setPreferredSize(delete.getPreferredSize());
        delete.addActionListener((event) -> deletePress());

        add = new JButton("Add Rotor");
        add.setPreferredSize(delete.getPreferredSize());
        add.addActionListener((event) -> addPress());

        edit = new JButton("Edit Setting");
        edit.setPreferredSize(delete.getPreferredSize());
        edit.addActionListener((event) -> editPress());

        encrypt = new JButton("Encrypt Text");
        encrypt.setPreferredSize(encrypt.getPreferredSize());
        encrypt.addActionListener((event) -> encryptPress());

        clearInvalid = new JButton("Clean Up Text");
        clearInvalid.setPreferredSize(encrypt.getPreferredSize());
        clearInvalid.addActionListener((event) -> clearPress());
    }

    //MODIFIES: this
    //EFFECTS: adds buttons in the correct positions to the ButtonPanel
    private void addButtons() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0,5,2,5);
        c.weightx = 1;
        c.weighty = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        setGridPos(c,1,0);
        add(add, c);
        setGridPos(c,1,1);
        add(edit, c);
        setGridPos(c,1,2);
        add(delete, c);
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

    //MODIFIES: this
    //EFFECTS: general handler method for compiler check
    private void clearPress() {
        manager.triggerTextClean();
    }

    //EFFECTS: passes event to manager when the add button is pressed
    private void addPress() {
        manager.triggerAddPop();
    }

    //EFFECTS: passes event to manager when the delete button is pressed
    private void deletePress() {
        manager.deleteRotor();
    }

    //EFFECTS: passes event to manager when the edit button is pressed
    private void editPress() {
        manager.triggerEditPop();
    }

    //EFFECTS: passes event to manager when the encrypt button is pressed
    private void encryptPress() {
        manager.triggerEncrypt();
    }

    //MODIFIES: this
    //EFFECTS: locks all buttons to ensure no additional inputs disrupt the settings while other events are handled
    public void lockForPopUp() {
        add.setEnabled(false);
        delete.setEnabled(false);
        edit.setEnabled(false);
        encrypt.setEnabled(false);
        clearInvalid.setEnabled(false);
    }

    //MODIFIES: this
    //EFFECTS: unlocks all buttons to allow inputs
    public void unlockForPopUp() {
        add.setEnabled(true);
        delete.setEnabled(true);
        edit.setEnabled(true);
        encrypt.setEnabled(true);
        clearInvalid.setEnabled(true);
    }
}
