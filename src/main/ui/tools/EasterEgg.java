package ui.tools;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


//A popup of a specific picture that is launched when one of a few inputs is provided to encrypt
public class EasterEgg extends JPanel {
    JFrame frame;
    Image image;

    //MODIFIES: this
    //EFFECTS: initializes all aspects of the easter egg popup and then makes it visible to the user
    public EasterEgg(String input) {
        frame = new JFrame("You found me!");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);

        initPicture(input);
        repaint();

        frame.add(this);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: paints the graphics on the panel
    public void paintComponent(Graphics g) {
        if (image != null) {
            g.drawImage(image,0,0,null);
        } else {
            g.drawString("I promise this was going to be funny", 10,20);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads the picture to be used for the easter egg popup, or a basic rectangle otherwise
    private void initPicture(String str) {
        try {
            image = ImageIO.read(new File(str));
            frame.setSize(image.getWidth(null),image.getHeight(null) + 25);
        } catch (IOException e) {
            image = null;
            frame.setSize(500,500);
        }
    }
}
