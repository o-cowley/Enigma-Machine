package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;

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

    public ReflexGame(StartGameHandler startGameHandler) {
        frame = new JFrame();
        rn = new Random();
        startGame = startGameHandler;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        changeCoords();
        setBackground(new Color(152, 253, 225));

        initClose();
        initTimer();
        initMouse();

        frame.add(this);
        frame.setSize(gameWidth, gameHeight);

        timer.start();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    private void initTimer() {
        timer = new Timer(500, e -> {
            changeCoords();
            repaint();
        });
        timer.setRepeats(true);
    }

    public void paintComponent(Graphics g) {
        g.setColor(new Color(0,100,0));
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        g.drawString("Catch the red square!", 125,40);
        g.setColor(new Color(255, 0, 0));
        g.fillRect(oneX, oneY, buttonSide, buttonSide);
    }

    private void changeCoords() {
        oneX = rn.nextInt(gameWidth - buttonSide);
        oneY = rn.nextInt(gameHeight - buttonSide);
    }

    public void initMouse() {
        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (checkWinState(e)) {
                    timer.stop();
                    gameFinished();
                }
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
    }

    private void gameFinished() {
        int result = JOptionPane.showConfirmDialog(frame, "Yay, you won", "Done entry game!",
                        JOptionPane.DEFAULT_OPTION);
        if (result == 0) {
            frame.dispose();
            startGame.doneGame();
        }
    }

    private void initClose() {
        frame.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("triggered the right thing");
            }

            public void windowClosed(WindowEvent e) {
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowActivated(WindowEvent e) {
            }

            public void windowDeactivated(WindowEvent e) {
            }
        });
    }

    public boolean checkWinState(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        return (mouseX >= oneX) && (mouseX <= oneX + buttonSide) && (mouseY >= oneY) && (mouseY <= oneY + buttonSide);
    }
}
