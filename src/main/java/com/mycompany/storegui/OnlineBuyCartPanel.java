/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

import com.mycompany.connectDB.connectDB;

public class OnlineBuyCartPanel extends JPanel {
    private final GridBagConstraints gbc = new GridBagConstraints();
    private static String loginedEmail = "";
    private static connectDB conn;

    public OnlineBuyCartPanel() {

        super(new GridBagLayout());

        try {

            Connection connect = conn.getConnection();
            Statement stmt = connect.createStatement();
            System.out.println("khong gion1");
            ResultSet rs = stmt.executeQuery("select * from user where userName = '" + loginedEmail + "'");
            while (rs.next()) {
                double sum = 0;
                for (Item item : OnlineSelectionScrollPane.CART)
                    sum += item.getCost();

                gbc.insets = new Insets(0, 0, 15, 5); // spacings

                JLabel totalLabel = new JLabel("Total:  " + sum);
                totalLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                addComponent(totalLabel, 1, 0, 2, 1);

                gbc.insets = new Insets(0, 0, 5, 5);
                addComponent(new JLabel("Name: "), 0, 1, 1, 1);

                // JTextField nameField = new
                // JTextField(OnlineRegistrationPanel.CUSTOMERS.get(loginedEmail).getName(),
                // 20);
                JTextField nameField = new JTextField(rs.getString(4), 20);
                addComponent(nameField, 1, 1, 1, 1);

                addComponent(new JLabel("Số điện thoại: "), 2, 1, 1, 1);

                JTextField mobileField = new JTextField("" + rs.getString(5), 20);
                addComponent(mobileField, 3, 1, 1, 1);

                addComponent(new JLabel("Đại chỉ: "), 0, 2, 1, 1);

                gbc.insets = new Insets(0, 0, 5, 5);
                addComponent(new JLabel("Card Number: "), 0, 3, 1, 1);

                JTextField cardField = new JTextField(20);
                addComponent(cardField, 1, 3, 1, 1);

                addComponent(new JLabel("Card PIN: "), 2, 3, 1, 1);

                JTextField pinField = new JTextField(20);
                addComponent(pinField, 3, 3, 1, 1);

                JButton btnShowCart = new JButton("Giỏ hàng");
                addComponent(btnShowCart, 1, 4, 1, 1);

                JButton btnBuy = new JButton("Thanh toán");
                addComponent(btnBuy, 2, 4, 1, 1);

                JButton btnBack = new JButton("Back");
                addComponent(btnBack, 3, 4, 1, 1);

                gbc.insets = new Insets(0, -50, 5, 0);
                JTextField locationField = new JTextField(rs.getString(6), 45);
                addComponent(locationField, 1, 2, 3, 1);

                btnBack.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MainPanel.setSubContainer(new OnlineSelectionScrollPane());
                    }
                });

                btnShowCart.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MainPanel.setSubContainer(new ShowOnlineCartPanel());
                    }
                });

                btnBuy.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Long.parseLong(cardField.getText());
                            Integer.parseInt(pinField.getText());
                            Long.parseLong(mobileField.getText());

                            if (cardField.getText().length() != 16 || pinField.getText().length() != 4 ||
                                    mobileField.getText().length() != 10 || nameField.getText().isBlank())
                                throw new NumberFormatException();

                            JOptionPane.showMessageDialog(null, "Cảm ơn quý khách!! Đặt hàng thành công\n"
                                    + "Đơn đặt hàng sẽ được giao trong vòng 15 ngày");
                            OnlineSelectionScrollPane.CART.clear();
                            MainPanel.setSubContainer(new OnlineSelectionScrollPane());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null,
                                    "Thông tin bị bỏ trống hoặc không chính xác !! Vui lòng thử lại",
                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void addComponent(Component component, int gridx, int gridy, int gridwidth, int gridheight) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        add(component, gbc);
    }

    public static String getLoginedEmail() {
        return loginedEmail;
    }

    public static void setLoginedEmail(String loginedEmail) {
        OnlineBuyCartPanel.loginedEmail = loginedEmail;
    }

}
