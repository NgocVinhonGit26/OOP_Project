package com.mycompany.storegui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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

        private JPanel contentPane;

        /**
         * Launch the application.
         */
        // public static void main(String[] args) {
        // EventQueue.invokeLater(new Runnable() {
        // public void run() {
        // try {
        // test frame = new test();
        // frame.setVisible(true);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
        // });
        // }

        /**
         * Create the frame.
         */
        public test(String productName, String imageAddress, Float cost, String quantity) {
                JFrame dmm = new JFrame(productName);
                dmm.setSize(500, 500);
                dmm.setBackground(Color.red);

                JPanel panel1 = new JPanel();
                panel1.setSize(500, 500);

                // panel1.setBackground(Color.red);
                panel1.setVisible(true);

                JLabel temJLabel;
                try {
                        URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource(imageAddress);
                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Không thấy ảnh", "ERROR",
                                        JOptionPane.ERROR_MESSAGE);
                        URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("close-icon.png");
                        temJLabel = new JLabel(productName, new ImageIcon(url), JLabel.CENTER);
                }

                String costText = "Rổ Giá: " + cost;

                String quantityText = "per " + quantity;
                panel1.setBorder(BorderFactory.createEtchedBorder(Color.lightGray,
                                Color.black));

                panel1.validate();
                panel1.repaint();
                Dimension dimension = new Dimension(5, 0);
                dmm.getContentPane().add(Box.createRigidArea(dimension));
                dmm.getContentPane().add(panel1);
                panel1.setLayout(null);
                URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource(imageAddress);
                temJLabel = new JLabel(productName, new ImageIcon(url),
                                JLabel.CENTER);
                temJLabel.setBounds(160, 115, 144, 70);

                JLabel imageNameLabel1 = temJLabel;
                imageNameLabel1.setVerticalTextPosition(SwingConstants.BOTTOM);
                imageNameLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
                imageNameLabel1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
                panel1.add(imageNameLabel1);
                JLabel quantityLabel = new JLabel(quantityText);
                quantityLabel.setBounds(160, 202, 99, 13);
                panel1.add(quantityLabel);
                JLabel costLabel = new JLabel(costText);
                costLabel.setBounds(160, 271, 130, 20);
                costLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

                panel1.add(costLabel);

                JButton btnBuy = new JButton("Mua");
                btnBuy.setBounds(220, 348, 70, 21);

                btnBuy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                int choice = JOptionPane.showConfirmDialog(null, "Thêm vào giỏ hàng?",
                                                "Confirmation",
                                                JOptionPane.YES_NO_OPTION);
                                if (choice == JOptionPane.YES_OPTION) {
                                        OnlineSelectionScrollPane.CART.add(new Item(productName, imageAddress, cost));
                                }
                        }
                });
                panel1.add(btnBuy);

                JLabel quantityLabel_1 = new JLabel("123");
                quantityLabel_1.setBounds(160, 225, 99, 13);
                panel1.add(quantityLabel_1);

                JLabel quantityLabel_2 = new JLabel("per <dynamic>");
                quantityLabel_2.setBounds(160, 248, 127, 13);
                panel1.add(quantityLabel_2);
                dmm.setVisible(true);
        }
}
