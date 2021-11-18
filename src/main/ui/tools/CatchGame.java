package ui.tools;

import ui.StartGameHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

// A splash screen ball catching game that the user is required to beat in order to access the encryption tool
public class CatchGame extends JPanel {
    private final int ballWidth = 20;
    private final int ballHeight = 20;

    private BufferedImage basket;
    private Image basketResized;

    StartGameHandler startGame;

    JFrame frame;
    Timer timer;

    private int oneX = 20;
    private int oneY = 20;
    boolean up = false;
    boolean down = true;
    boolean left = false;
    boolean right = true;

    private int mouseX = 0;
    private int mouseY = 0;
    private boolean catcherVisible = false;

    private int randomizer = 0;
    private Random rn;

    //MODIFIES: this
    //EFFECTS: constructor for the CatchGame class, initializes all aspects and launches the game
    public CatchGame(StartGameHandler startGame) {
        this.startGame = startGame;
        setBackground(new Color(112, 245, 205));

        frame = new JFrame();
        frame.setSize(300, 300);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setVisible(true);

        rn = new Random();
        initPicture();
        initMouseListener();
        initMouseMotionListener();
        initTimer();

        timer.start();
    }

    //MODIFIES: this
    //EFFECTS: loads the picture to be used for the game basket
    private void initPicture() {
        try {
            basket = ImageIO.read(new File("./data/basket.png"));
            basketResized = basket.getScaledInstance(50,50, 1);
        } catch (IOException e) {
            basket = null;
        }
    }

    //MODIFIES: this
    //EFFECTS: initializes the timer and loads the event handler that powers the game and checks for winState
    private void initTimer() {
        timer = new Timer(10, e -> {
            changeDir();
            changePos();
            checkWinState();
            repaint();
        });
        timer.setRepeats(true);
    }

    //MODIFIES: this
    //EFFECTS: initializes the mouse motion listener to detect where the mouse is within the bounds of the game
    private void initMouseMotionListener() {
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: initializes mouseAdaptor that checks if the mouse is within the bounds of the game window
    private void initMouseListener() {
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                catcherVisible = true;
                mouseX = e.getX() - 10;
                mouseY = e.getY() - 10;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                catcherVisible = false;
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: creates the graphics of the game
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0,100,0));
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        g.drawString("Catch the ball in the basket!", 10,40);
        g.setColor(new Color(255, 0, 0));
        g.fillOval(oneX, oneY, ballWidth, ballHeight);
        if (catcherVisible) {
            if (basket != null) {
                g.drawImage(basketResized, mouseX - 25, mouseY - 25, this);
            } else {
                g.fillRect(mouseX - 25, mouseY - 25, 50,50);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: changes the ball direction according to the randomizer
    private void changeDir() {
        if (randomizer == 10 || randomizer == 60) {
            left = !left;
            right = !right;
        } else if (randomizer == 40 || randomizer == 90) {
            up = !up;
            down = !down;
        } else {
            moveNormal();
        }
    }

    //MODIFIES: this
    //EFFECTS: adjusts direction of the ball or bounces the ball if it bounces off a boundary
    private void moveNormal() {
        if (oneX >= this.getWidth() - 10) {
            left = true;
            right = false;
        }
        if (oneX <= 0) {
            left = false;
            right = true;
        }
        if (oneY >= this.getHeight() - 10) {
            down = false;
            up = true;
        }
        if (oneY <= 0) {
            down = true;
            up = false;
        }
    }

    //MODIFIES: this
    //EFFECTS: moves the X and Y coordinates of the ball according to the direction fields, then generates the next
    //  randomizer value for direction changes
    private void changePos() {
        if (up) {
            oneY--;
        } else {
            oneY++;
        }
        if (left) {
            oneX--;
        } else {
            oneX++;
        }
        randomizer = rn.nextInt(100);
    }

    //MODIFIES: this
    //EFFECTS: detects if the ball has been caught in the basket. Displays win popup if so, stops timer, closes game
    // panel and then indicates to the manager that the game has been won
    private void checkWinState() {
        if (catcherVisible) {
            if ((oneX >= mouseX - 25) && (oneX + ballWidth <= mouseX + 25)
                    && (oneY + ballHeight >= mouseY + 10) && (oneY + ballHeight <= mouseY + 25) && (down)) {
                timer.stop();
                JOptionPane.showMessageDialog(frame, "Congrats! You can use the program now.");
                frame.dispose();
                startGame.doneGame();
            }
        }
    }
}
