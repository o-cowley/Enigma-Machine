package ui.Tools;

import ui.GuiManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class StartProgram extends JPanel {
    private final int ballWidth = 20;
    private final int ballHeight = 20;

    private BufferedImage basket;
    private Image basketResized;

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

    public StartProgram() {
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

    private void initPicture() {
        try {
            basket = ImageIO.read(new File("./data/basket.png"));
            basketResized = basket.getScaledInstance(50,50, 1);
        } catch (IOException e) {
            //
        }
    }

    private void initTimer() {
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeDir();
                changePos();
                checkWinState();
                repaint();
            }
        });
        timer.setRepeats(true);
    }

    private void initMouseMotionListener() {
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
    }

    private void initMouseListener() {
        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

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


    public void paintComponent(Graphics g) {
        g.setColor(new Color(255, 0, 0));
        g.fillOval(oneX, oneY, ballWidth, ballHeight);
        if (catcherVisible) {
            g.drawImage(basketResized, mouseX - 25, mouseY - 25, this);
        }
    }


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

    //TODO  set up proper overlap dimensions
    private void checkWinState() {
        if (catcherVisible) {
            if (((mouseX - 25 >= oneX + ballWidth) && (mouseX + 25 <= oneX)) //TODO fix this
                    && ((mouseY + 25 <= oneY + ballHeight) && (mouseY + 50 >= oneY))) {
                timer.stop();
                JOptionPane.showMessageDialog(frame, "Congrats! You can use the program now.");
                frame.dispose();
                new GuiManager();
            }
        }
    }


    public static void main(String[] args) {
        new StartProgram();
    }
}
