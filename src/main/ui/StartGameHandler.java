package ui;

import ui.tools.CatchGame;

import javax.swing.*;
import java.awt.*;

public class StartGameHandler extends JPanel {

    JFrame mainFrame;

    JButton buttonCatch;
    JButton buttonReflex;
    JLabel label;

    public StartGameHandler() {
        setLayout(new GridBagLayout());
        mainFrame = new JFrame("Start the show");

        initButtons(this);
        addButtons();

        mainFrame.add(this);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);




    }

    private void initButtons(StartGameHandler main) {
        label = new JLabel("Pick a game! You have to win to get in to the program.");
        label.setPreferredSize(label.getPreferredSize());

        buttonCatch = new JButton("Catch Game");
        buttonCatch.setPreferredSize(buttonCatch.getPreferredSize());
        buttonCatch.addActionListener(e -> new CatchGame(this));

        buttonReflex = new JButton("Reflex Game");
        buttonReflex.setPreferredSize(buttonReflex.getPreferredSize());
        buttonReflex.addActionListener(e -> new ReflexGame(this));
    }

    private void addButtons() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        add(label, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(buttonCatch, c);
        c.gridx = 1;
        c.gridy = 1;
        add(buttonReflex, c);
    }

    public void doneGame() {
        mainFrame.dispose();
        new GuiManager();
    }

    public static void main(String[] args) {
        new StartGameHandler();
    }
}
