package ui.tools;

import ui.GuiManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

// The panel that displays the Rotors that are currently installed in the machine, as well as gives the internal
// settings on the ToolTipText
public class RotorManager extends JPanel {
    GuiManager manager;

    JList list;
    JScrollPane scrollPane;
    DefaultListModel<String> displayedRotorList;

    //MODIFIES: this
    //EFFECTS: constructs new RotorManager rotor display pane to be added to the main GUI
    public RotorManager(int size, GuiManager guiManager) {
        setLayout(new BorderLayout());
        manager = guiManager;
        makeList(size);

        scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(125,300));

        add(scrollPane, BorderLayout.CENTER);
        setPreferredSize(getPreferredSize());
    }

    // help for the tooltip Text was found on
    // https://www.tutorialspoint.com/how-to-set-a-tooltip-text-for-each-item-of-a-jlist-in-java
    //REQUIRES: amount of loaded number of rotors or -1 if none loaded from save
    //MODIFIES: this
    //EFFECTS: Fills the visible list according to the specifications provided, will be empty at start if none loaded
    // from file
    private void makeList(int size) {
        displayedRotorList = new DefaultListModel<>();
        if (size != -1) {
            for (int i = 1; i <= size; i++) {
                displayedRotorList.addElement("Rotor #" + i);
            }
        }

        list = new JList(displayedRotorList) {
            public String getToolTipText(MouseEvent me) {
                Point point = me.getPoint();
                int index = locationToIndex(point);
                Rectangle selected = list.getCellBounds(index, index);
                if (index >= 0 && selected.contains(point))  {
                    return manager.getRotorString(index);
                }
                return null;
            }
        };
        list.setToolTipText("");
    }

    //MODIFIES: this
    //EFFECTS: removes the selected rotor from the list, renames all following rotors accordingly to maintain
    // continuity of counting
    public void removeSelectedElement() {
        int selected = list.getSelectedIndex();
        if (selected < list.getModel().getSize() && selected != -1) {
            displayedRotorList.remove(selected);
            for (int i = selected; i < displayedRotorList.size(); i++) {
                displayedRotorList.set(i, "Rotor #" + (i + 1));
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: locks rotor display so that nothing can be changed while editing
    public void lockRotors() {
        list.setEnabled(false);
    }

    //MODIFIES: this
    //EFFECTS: unlocks rotor display so that nothing can be changed while editing
    public void unlockRotors() {
        list.setEnabled(true);
    }



    //MODIFIES: this
    //EFFECTS: adds a new rotor to the end of the list
    public void addNewElement() {
        displayedRotorList.addElement("Rotor #" + (displayedRotorList.size() + 1));
    }

    //EFFECTS: returns the index of what is currently selected in the list of rotors
    public int returnSelected() {
        return list.getSelectedIndex();
    }

    //MODIFIES: this
    //EFFECTS: fills the visible list with size number of visible rotors
    public void updateList(int size) {
        for (int i = 0; i < size; i++) {
            addNewElement();
        }
    }
}
