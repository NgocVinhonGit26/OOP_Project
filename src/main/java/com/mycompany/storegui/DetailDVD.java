package com.mycompany.storegui;

import java.net.URL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;

public class DetailDVD {
    public DetailDVD(String title, String imageAddress, Float cost, int quantity, int length, String director) {

        JFrame detailFrame = new JFrame(title);
        detailFrame.setSize(500, 500);
        detailFrame.setBackground(Color.red);

        JPanel panel = new JPanel();
        panel.setSize(500, 500);

        // panel.setBackground(Color.red);
        panel.setVisible(true);

        JLabel temJLabel;
        try {
            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource(imageAddress);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Không thấy ảnh", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("close-icon.png");
            temJLabel = new JLabel(title, new ImageIcon(url), JLabel.CENTER);
        }

        String costText = "Giá: " + cost;
        String quantityText = "Số lượng: " + quantity;
        String lengthText = "Độ dài: " + length;
        String directorText = "Đạo diễn: " + director;
        panel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));

        panel.validate();
        panel.repaint();
        Dimension dimension = new Dimension(5, 0);
        detailFrame.getContentPane().add(Box.createRigidArea(dimension));
        detailFrame.getContentPane().add(panel);
        panel.setLayout(null);
        URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource(imageAddress);
        temJLabel = new JLabel(title, new ImageIcon(url), JLabel.CENTER);
        temJLabel.setBounds(157, 107, 144, 70);

        JLabel imageNameLabel1 = temJLabel;
        imageNameLabel1.setVerticalTextPosition(SwingConstants.BOTTOM);
        imageNameLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
        imageNameLabel1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        panel.add(imageNameLabel1);
        JLabel quantityLabel = new JLabel(quantityText);
        quantityLabel.setBounds(160, 202, 99, 13);
        panel.add(quantityLabel);
        if (length != 1) {
            JLabel lengthLabel = new JLabel(lengthText);
            lengthLabel.setBounds(160, 225, 99, 13);
            panel.add(lengthLabel);
        }

        JLabel directorLabel = new JLabel(directorText);
        directorLabel.setBounds(160, 248, 127, 13);
        panel.add(directorLabel);
        JLabel costLabel = new JLabel(costText);
        costLabel.setBounds(160, 271, 130, 20);
        costLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

        panel.add(costLabel);

        JButton btnBuy = new JButton("Mua");
        btnBuy.setBounds(220, 348, 70, 21);

        btnBuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Thêm vào giỏ hàng?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    Item tempItem = new Item();
                    tempItem.setQuantity(quantity - 1);
                    OnlineSelectionScrollPane.CART.add(new Item(title, imageAddress, cost));
                }
            }
        });
        panel.add(btnBuy);
        detailFrame.setVisible(true);

    }
}
