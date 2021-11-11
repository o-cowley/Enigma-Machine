package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

// The panel that displays the Rotors that are currently installed in the machine, as well as gives the internal
// settings on the ToolTipText
public class RotorManager extends JPanel {
    GuiManager manager;

    JList list;
    JScrollPane scrollPane;
    JButton delete;
    JButton add;
    DefaultListModel<String> displayedRotorList;

    //MODIFIES: this
    //EFFECTS: constructs new RotorManager rotor display pane to be added to the main GUI
    public RotorManager(int size, GuiManager guiManager) {
        setLayout(new BorderLayout());
        manager = guiManager;
        makeList(size);

        scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(75,300));

        delete = new JButton("Delete Selected");
        delete.addActionListener((event) -> removeSelectedElement());

        add = new JButton("Add Unnecessary");
        add.addActionListener((event) -> addNewElement());
        add.setPreferredSize(new Dimension(150,30));

        add(scrollPane, BorderLayout.CENTER);
        //add(delete,BorderLayout.SOUTH);
        add(add,BorderLayout.SOUTH);

        setPreferredSize(getPreferredSize());
        //setPreferredSize(new Dimension(100,400));
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
                if (index > -1 && selected.contains(point))  {
                    String str = (String) getModel().getElementAt(index);
                    return "Name is " + str;
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

    public void updateList(int size) {
        for (int i = 0; i < size; i++) {
            addNewElement();
        }
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("JList Demo");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        ArrayList sports1 = new ArrayList(Arrays.asList("Tennis", "Archery", "Football", "Fencing", "Cricket",
//                "Squash", "Hockey","Rugby"));
//
//        ArrayList sports2 = new ArrayList(Arrays.asList(1,2,3,4,5,6));
//
//        frame.setSize(300,300);
//        frame.setVisible(true);
//    }

}
