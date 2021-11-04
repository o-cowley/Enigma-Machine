package ui;

import javax.swing.*;
import java.awt.*;


public class VisualizationDevelopment extends JFrame {
    private JDesktopPane desktop;
    private JInternalFrame controlPanel;
    private JOptionPane testPop;

    private JPanel testPanel;
    private JButton[] keys;

    public VisualizationDevelopment() {
        testPop = new JOptionPane();
        testPop.showConfirmDialog(null, "Hello");
        testPop.setVisible(true);

        desktop = new JDesktopPane();
        setContentPane(desktop);
        setTitle("Hello this is me");
        setSize(400, 400);

        controlPanel = new JInternalFrame("Control", true, true, false);
        controlPanel.setLayout(new BorderLayout());

        testPanel = new JPanel();
        testPanel.setLayout(new GridLayout(3, 4));
        addButtons(testPanel);
        testPanel.setVisible(true);
        controlPanel.add(testPanel, BorderLayout.CENTER);

        controlPanel.pack();
        controlPanel.setVisible(true);
        desktop.add(controlPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
        setVisible(true);
    }

    public VisualizationDevelopment(boolean a) {
        DefaultComboBoxModel<Integer> name = new DefaultComboBoxModel<>();
        name.addElement(1);
        name.addElement(2);
        name.addElement(3);
        name.addElement(4);
        name.addElement(5);
        JComboBox jc = new JComboBox(name);	//initialzing combo box with list of name.
        add(jc);				//adding JComboBox to frame.
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);
    }


    private void addButtons(JPanel p) {
        keys = new JButton[12];

        for (int i = 0; i < 9; i++) {
            keys[i] = new JButton(Integer.toString(i + 1));

            p.add(keys[i]);
        }

        keys[9] = new JButton("Clear");
        p.add(keys[9]);

        keys[10] = new JButton("0");
        p.add(keys[10]);

        keys[11] = new JButton("#");
        p.add(keys[11]);
    }


    private void makeList() {
        DefaultListModel<Integer> cityList = new DefaultListModel();
        cityList.addElement(1);
        cityList.addElement(2);
        cityList.addElement(3);
        cityList.addElement(4);
        cityList.addElement(6);
        JList cities = new JList(cityList);

    }


    public static void main(String[] args) {
        new VisualizationDevelopment(true);
    }



}
