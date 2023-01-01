package GUI;

import javax.swing.*;
import java.awt.*;

public class CommonGUIPanels {
    public static JPanel getTitlePanel(String title){
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(211, 250, 254));
        // create items of title panel
        JLabel titleLabel1 = new JLabel("Westminster Skin Consultations");
        titleLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel1.setFont(new Font("SansSerif", Font.PLAIN, 50));
        JLabel titleLabel2 = new JLabel(title, JLabel.CENTER);
        titleLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel2.setFont(new Font("SansSerif", Font.PLAIN, 30));
        // add items to title panel
        titlePanel.add(Box.createRigidArea(new Dimension(0,30)));
        titlePanel.add(titleLabel1);
        titlePanel.add(Box.createRigidArea(new Dimension(0,30)));
        titlePanel.add(titleLabel2);
        return titlePanel;
    }
}