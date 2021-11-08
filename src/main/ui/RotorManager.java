package ui;

import model.InUseRotors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

// The panel that displays the Rotors that are currently installed in the machine, as well as gives the internal
// settings on the ToolTipText
public class RotorManager extends JPanel {
    //TODO: needs to know the main managing frame for functionality access

    JList list;
    JScrollPane scrollPane;
    JButton delete;
    JButton add;
    DefaultListModel<String> stuff;

    //MODIFIES: this
    //EFFECTS: constructs new RotorManager rotor display pane to be added to the main GUI
    public RotorManager() {
        setLayout(new BorderLayout());
        makeList();

        scrollPane = new JScrollPane(list);
        delete = new JButton("Delete Selected");
        delete.addActionListener((event) -> removeThing(list.getSelectedIndex()));

        add = new JButton("Add Unnecessary");
        add.addActionListener((event) -> addThing());
        add.setPreferredSize(new Dimension(150,30));

        add(scrollPane, BorderLayout.CENTER);
        add(delete,BorderLayout.SOUTH);
        add(add,BorderLayout.EAST);

        setPreferredSize(getPreferredSize());
    }

    // help for the tooltip Text was found on
    // https://www.tutorialspoint.com/how-to-set-a-tooltip-text-for-each-item-of-a-jlist-in-java
    //MODIFIES: this
    //EFFECTS: Fills the visible list according to the specifications provided, will be empty at start if not loading
    // from file
    private void makeList() {
        stuff = new DefaultListModel<>();
        String[] sports1 = new String[]{"Tennis", "Archery", "Football", "Fencing", "Cricket", "Squash", "Hockey",
                "Rugby"};
        for (String s: sports1) {
            stuff.addElement(s);
        }
        list = new JList(stuff) {
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
    public void removeThing(int selected) {
        if (selected < list.getModel().getSize() && selected >= 0) {
            stuff.remove(selected);
            for (int i = selected; i < stuff.size(); i++) {
                stuff.set(i, "Rotor #" + (i + 1));
            }
        }
    }


    //MODIFIES: this
    //EFFECTS: adds a rotor to the end of the visible list
    public void addThing() {
        stuff.addElement("Rotor #" + (stuff.size() + 1));
    }

    //MODIFIES:
    //EFFECTS: returns the index of what is currently selected in the list of rotors
    public int returnSelected() {
        return list.getSelectedIndex();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("JList Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        RotorManager rman = new RotorManager();

        frame.add(rman);
        frame.setSize(300,300);
        frame.setVisible(true);
    }

}
