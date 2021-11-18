package ui.tools;

import ui.StartGameHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

//A simple game of whack-a-mole, this is another option of a game to beat for users to gain access to the program
public class ReflexGame extends JPanel {
    private final int gameWidth = 500;
    private final int gameHeight = 500;
    private final int buttonSide = 50;

    StartGameHandler startGame;

    private JFrame frame;

    private Timer timer;
    private Random rn;
    private int oneX;
    private int oneY;

    //MODIFIES: this
    //EFFECTS: A constructor for the ReflexGame, initializes all fields and visuals and then starts the animation
    public ReflexGame(StartGameHandler startGameHandler) {
        frame = new JFrame();
        rn = new Random();
        startGame = startGameHandler;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        changeCoords();
        setBackground(new Color(152, 253, 225));

        initTimer();
        initMouse();

        frame.add(this);
        frame.setSize(gameWidth, gameHeight);
        frame.setLocationRelativeTo(null);

        timer.start();
        frame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: starts the timer and adds the two key methods to be executed every cycle
    private void initTimer() {
        timer = new Timer(500, e -> {
            changeCoords();
            repaint();
        });
        timer.setRepeats(true);
    }

    //MODIFIES: this
    //EFFECTS: paints the graphics on to the panel
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0,100,0));
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        g.drawString("Catch the red square!", 125,40);
        g.setColor(new Color(255, 0, 0));
        g.fillRect(oneX, oneY, buttonSide, buttonSide);
    }

    //MODIFIES: this
    //EFFECTS: Changes the coordinates of the target box
    private void changeCoords() {
        oneX = rn.nextInt(gameWidth - buttonSide);
        oneY = rn.nextInt(gameHeight - buttonSide);
    }

    //MODIFIES: this
    //EFFECTS: adds the mouse handler to check if the target was caught on a mouse click
    public void initMouse() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (checkWinState(e)) {
                    timer.stop();
                    gameFinished();
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: alerts the user that they successfully got through the game, then disposes of the game frame and
    // alerts the StartGameHandler to start the encryption phase of the program
    private void gameFinished() {
        int result = JOptionPane.showConfirmDialog(frame, "Yay, you won", "Game won!",
                        JOptionPane.DEFAULT_OPTION);
        if (result == 0) {
            frame.dispose();
            startGame.doneGame();
        }
    }

    //EFFECTS: checks if a mouse click was within the target and therefore satisfies the win condition
    public boolean checkWinState(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        return (mouseX >= oneX) && (mouseX <= oneX + buttonSide) && (mouseY >= oneY) && (mouseY <= oneY + buttonSide);
    }
}
