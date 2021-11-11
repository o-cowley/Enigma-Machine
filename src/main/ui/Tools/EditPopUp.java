package ui.Tools;

import ui.GuiManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// A PopUp window that prompts a user to adjust a selected rotor's internal setting before encryption
public class EditPopUp extends JFrame {
    GuiManager manager;

    JSpinner spinner;
    JButton done;
    JButton close;
    JLabel label;

    //EFFECTS: A constructor that initializes an Edit popup to allow a user to set a rotor's internal setting
    public EditPopUp(Point p, int rotor, int initialSetting, GuiManager guiManager) {
        this.setLayout(new GridBagLayout());
        manager = guiManager;

        initializeButtons(rotor, initialSetting);
        addButtons();
        setUpClose();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        pack();
        setPreferredSize(getPreferredSize());
        setResizable(false);

        setLocation(p);
        setVisible(true);

    }

    //how to use a window listener came from code suggestions from this page:
    //https://stackoverflow.com/questions/9093448/how-to-capture-a-jframes-close-button-click-event
    private void setUpClose() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                manager.unlockRotorManager();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: sets up the buttons, text, settings, size, and components to display on the popup
    private void initializeButtons(int rotorLabel, int initialSetting) {
        SpinnerModel spinnerModel = new SpinnerNumberModel(0,0,25,1);
        spinner = new JSpinner(spinnerModel);
        spinner.setPreferredSize(spinner.getPreferredSize());

        JComponent editor = spinner.getEditor();
        JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
        tf.setColumns(4);

        label = new JLabel("<html>This is for rotor type: " + rotorLabel + "<br> the previous setting was: "
                + initialSetting + "</html>");
        label.setPreferredSize(new Dimension(100,50));

        done = new JButton("Done");
        done.setPreferredSize(done.getPreferredSize());
        done.addActionListener((event) -> {
            doneButtonPush((int) spinner.getValue());
            this.dispose();
        });

        close = new JButton("Close");
        close.setPreferredSize(close.getPreferredSize());
        close.addActionListener((event) -> this.dispose());
    }

    //MODIFIES: this
    //EFFECTS: adds the buttons and other Swing components to the panel
    private void addButtons() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.gridwidth = 2;
        setGridPos(c,0,0);
        add(spinner, c);
        c.gridwidth = 1;
        setGridPos(c,2,0);
        add(close, c);
        setGridPos(c,3,0);
        add(done, c);
        setGridPos(c,0,1);
        c.gridwidth = 4;
        c.fill = GridBagConstraints.BOTH;
        add(label, c);

    }


    //MODIFIES: this, c
    //EFFECTS: sets the constraints of x and y position to use the GridBagConstraints object to add a component to
    // the main panel
    private void setGridPos(GridBagConstraints c, int x, int y) {
        c.gridx = x;
        c.gridy = y;
    }

    private void doneButtonPush(int setting) {
        manager.reactToEditPop(setting);
    }



//    public static void main(String[] args) {
//        JFrame mainFrame = new JFrame();
//        mainFrame.setLocationRelativeTo(null);
//
//        JButton but = new JButton("pop");
//        but.setPreferredSize(new Dimension(100,100));
//        but.addActionListener((event) -> new EditPopUp(new Point(mainFrame.getX() + 100, mainFrame.getY()),
//                5, 5));
//
//        mainFrame.add(but);
//
//        mainFrame.setSize(mainFrame.getPreferredSize());
//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainFrame.setVisible(true);
//
//    }



}
