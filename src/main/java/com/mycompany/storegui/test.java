package com.mycompany.storegui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mycompany.storegui.MainMenuPanel.ActionHandler;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;

public class test extends JFrame {

	JButton btnOnline;
    JLabel label;
    JLabel tempLabel;
    GridBagLayout layout;
    GridBagConstraints constraints;

    public test() {
        super();
        layout = new GridBagLayout();
        getContentPane().setLayout(new GridBagLayout());

        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 15);
        btnOnline = new JButton("Click here");
        btnOnline.setFont(font);
        btnOnline.setBackground(null);
                                                                        constraints = new GridBagConstraints();
                                                                        constraints.fill = GridBagConstraints.HORIZONTAL;
                                                                        
                                                                                label = new JLabel("Welcome to Media Shop", SwingConstants.CENTER);
                                                                                label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
                                                                                label.setForeground(Color.cyan);
                                                                                addComponent(label, 0, 0, 0, 1);
                                                                                constraints.insets = new Insets(0, 0, 0, 5);
        addComponent(btnOnline, 0, 1, 1, 1);

        ActionHandler handler = new ActionHandler();
        btnOnline.addActionListener(handler);
        
        
        try {
            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("bg1.jpg");
            tempLabel = new JLabel(new ImageIcon(url),JLabel.CENTER);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Image not found", "ERROR", JOptionPane.ERROR_MESSAGE);
            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("close-icon.png");
            tempLabel = new JLabel("", new ImageIcon(url), JLabel.CENTER);
        }
        addComponent(tempLabel, 0, 0, 0, 1);

        
    }

    private void addComponent(Component component, int gridx, int gridy, int gridwidth, int gridheight) {
        constraints.gridx = 0;
        constraints.gridy = 0;
        getContentPane().add(component, constraints);
    }

    private class ActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == btnOnline)
                MainPanel.setSubContainer(new OnlineSelectionScrollPane());
        }
    }
}
