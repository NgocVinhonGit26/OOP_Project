/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.beans.JavaBean;
import java.net.URL;

public class MainMenuPanel extends JPanel {
    JButton btnOnline;
    JLabel label;
    JLabel tempLabel;
    GridBagLayout layout;
    GridBagConstraints constraints;

    public MainMenuPanel() {
        super();
        layout = new GridBagLayout();
        constraints = new GridBagConstraints();
        setLayout(new GridBagLayout());

        label = new JLabel("Welcome to Media Shop", SwingConstants.CENTER);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        label.setForeground(Color.cyan);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        addComponent(label, 0, 0, 0, 1);

        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 15);
        btnOnline = new JButton("Click here");
        btnOnline.setFont(font);
        btnOnline.setBackground(null);
        constraints.insets = new Insets(0, 300, 0, 300);
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
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        add(component, constraints);
    }

    private class ActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == btnOnline)
                MainPanel.setSubContainer(new OnlineSelectionScrollPane());
        }
    }
}
