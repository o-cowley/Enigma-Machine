package ui.tools;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

//A popup of a specific picture that is launched when a certain input is provided to encrypt
public class EasterEgg extends JPanel {
    JFrame frame;
    Image fortyTwo;

    //MODIFIES: this
    //EFFECTS: initializes all aspects of the easter egg popup and then makes it visible to the user
    public EasterEgg() {
        frame = new JFrame("You found me!");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);

        initPicture();
        repaint();

        frame.add(this);
        frame.setSize(700,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    //MODIFIES: this
    //EFFECTS: paints the graphics on the panel
    public void paintComponent(Graphics g) {
        g.drawImage(fortyTwo,0,0,null);
    }

    //MODIFIES: this
    //EFFECTS: loads the picture to be used for the easter egg popup, or a basic rectangle otherwise
    private void initPicture() {
        try {
            fortyTwo = ImageIO.read(new File("./data/42.png"));
        } catch (IOException e) {
            //TODO...figure out something to do instead
        }
    }

}
