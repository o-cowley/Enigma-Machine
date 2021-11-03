package ui;

import javax.swing.*;
import java.awt.*;

public class RotorManager extends JPanel {
    JList list;
    JScrollPane scrollPane;
    JButton button;
    DefaultListModel<String> sports;


    public RotorManager() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200,200));

        makeList();
        makeButton();

        add(scrollPane);
        add(button);
        setVisible(true);
    }

    public RotorManager(boolean t) {
        JFrame frame = new JFrame("JList Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sports = new DefaultListModel<>();
        String[] sports1 = new String[]{"Tennis", "Archery", "Football", "Fencing", "Cricket", "Squash", "Hockey",
                "Rugby"};
        for (String s: sports1) {
            sports.addElement(s);
        }
        list = new JList(sports);
        JScrollPane scrollPane = new JScrollPane(list);
        button = new JButton("Delete Selected");
        button.addActionListener((event) -> removeThing());

        JButton button2 = new JButton("Add Unnecessary");
        button2.addActionListener((event) -> addThing());

        Container contentPane = frame.getContentPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(button,BorderLayout.SOUTH);
        contentPane.add(button2,BorderLayout.EAST);

        frame.setSize(400,400);
        frame.setVisible(true);
    }

    private void makeList() {

        DefaultListModel listToAdd = new DefaultListModel();

        listToAdd.add(0, "Abc");
        listToAdd.add(1, "def");
        listToAdd.add(2, "ghi");

        list = new JList<String>(listToAdd);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane(list);


    }

    public void makeButton() {
        button = new JButton();
        button.setPreferredSize(new Dimension(20,20));
        button.addActionListener((event) -> {
            removeThing();
        });
    }

    public static void main(String[] args) {
//        JFrame frame = new JFrame("Test scroll");
//        RotorManager rm = new RotorManager();
//        frame.getContentPane().setLayout(new BorderLayout());
//
//        frame.setSize(new Dimension(500,500));
//        frame.setLocationRelativeTo(null);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//
//        frame.getContentPane().add(rm, BorderLayout.NORTH);
//        frame.setVisible(true);
        new RotorManager(true);
    }

    public void removeThing() {
        int selected = list.getSelectedIndex();
        if (selected < list.getModel().getSize() && selected >= 0) {
            sports.remove(selected);
        }
    }

    public void addThing() {
        sports.addElement("Heloooooooo");
    }

}
