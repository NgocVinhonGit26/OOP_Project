/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

import java.awt.*;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;

public class ShowOnlineCartPanel extends JPanel {
    private final JPanel cartPanel = new JPanel();

    public ShowOnlineCartPanel() {
        super(new BorderLayout());

        BoxLayout layout = new BoxLayout(cartPanel, BoxLayout.Y_AXIS);
        cartPanel.setLayout(layout);

        JButton btnBack = new JButton("Back");
        JButton btnBuyCart = new JButton("Buy products in cart");

        JPanel btnsPanel = new JPanel();
        btnsPanel.add(btnBack);
        btnsPanel.add(btnBuyCart);

        add(btnsPanel, BorderLayout.NORTH);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPanel.setSubContainer(new OnlineSelectionScrollPane());
            }
        });

        btnBuyCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (OnlineRegistrationPanel.isLogin())
                    MainPanel.setSubContainer(new OnlineBuyCartPanel());
                else
                    MainPanel.setSubContainer(new OnlineLoginPanel());
            }
        });

        showCart();

        JScrollPane scrollPane = new JScrollPane(cartPanel);
        add(scrollPane);

    }

    private void showCart() {
        for (Item item : OnlineSelectionScrollPane.CART) {
            JPanel subPanel = new JPanel();
            subPanel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            JLabel label = new JLabel(item.getName() + " (Cost: " + item.getCost() + ")",
                    new ImageIcon(item.getImageAddress()), JLabel.CENTER);

            subPanel.add(label);
            subPanel.setMaximumSize(new Dimension(1000, 75));
            cartPanel.add(subPanel);
            subPanel.setToolTipText("Click to remove from cart");

            subPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int choice = JOptionPane.showConfirmDialog(null, "Remove from cart?", "Confirmation",
                            JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        OnlineSelectionScrollPane.CART.remove(item);
                        cartPanel.remove(subPanel);
                        cartPanel.validate();
                        cartPanel.repaint();
                    }

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    subPanel.setBackground(Color.lightGray);
                    subPanel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    subPanel.setBackground(null);
                    subPanel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                }

            });
        }
    }
}
