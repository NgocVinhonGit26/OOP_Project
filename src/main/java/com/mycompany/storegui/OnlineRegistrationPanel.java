/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

import javax.swing.*;

import com.mycompany.connectDB.connectDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;

public class OnlineRegistrationPanel extends JPanel {
    private final GridBagConstraints gbc = new GridBagConstraints();
    public static final HashMap<String, Customer> CUSTOMERS = new HashMap<>(); // keep record of customers
    private static boolean login = false;
    private static String DB_URL = "jdbc:mysql://localhost:3306/onemediapro";
    private static String USER_NAME = "root";
    private static String PASSWORD = "26072001";
    private static connectDB conn;

    public OnlineRegistrationPanel() {
        super(new GridBagLayout());

        gbc.insets = new Insets(0, 0, 5, 5); // spacings

        addComponent(new JLabel("Tài khoản: "), 0, 0, 1, 1);

        JTextField emailField = new JTextField(20);
        addComponent(emailField, 1, 0, 1, 1);

        addComponent(new JLabel("Mật khẩu: "), 2, 0, 1, 1);

        JPasswordField passwordField = new JPasswordField(20);
        addComponent(passwordField, 3, 0, 2, 1);

        addComponent(new JLabel("Họ tên: "), 0, 1, 1, 1);

        JTextField nameField = new JTextField(20);
        addComponent(nameField, 1, 1, 1, 1);

        addComponent(new JLabel("Số điện thoại: "), 2, 1, 1, 1);

        JTextField mobileField = new JTextField(20);
        addComponent(mobileField, 3, 1, 1, 1);

        addComponent(new JLabel("Địa chỉ: "), 0, 2, 1, 1);

        JTextField locationField = new JTextField(45);
        addComponent(locationField, 1, 2, 3, 1);

        JButton btnRegister = new JButton("Đăng kí");
        addComponent(btnRegister, 1, 3, 1, 1);

        JButton btnBack = new JButton("Back");
        addComponent(btnBack, 2, 3, 1, 1);

        gbc.insets = new Insets(0, -50, 5, 0);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connect = conn.getConnection();
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery("select * from user");
                    Integer check = 0;
                    System.out.println("check0");

                    if (emailField.getText().contains("@mail.com")) {
                        System.out.println("check0123");
                        while (rs.next()) {
                            if (emailField.getText().equals(rs.getString(2))) {
                                check = 1;
                                break;
                            }
                        }
                        if (check == 0) {
                            // check = 1;
                            // System.out.println("check: " + emailField.getText() + " " +
                            // (rs.getString(2)));
                            if (passwordField.getPassword().length >= 8) {
                                if (!nameField.getText().isBlank()) {
                                    if (!locationField.getText().isBlank()) {
                                        if (mobileField.getText().length() == 10) {

                                            Connection connection = DriverManager.getConnection(DB_URL, USER_NAME,
                                                    PASSWORD);
                                            String sql = "insert into user (`userName`,`passWord`,`tenkhachhang`,`sodienthoai`,`diachi`,`chucnang`) values(?,?,?,?,?,?)";
                                            PreparedStatement ps = connection.prepareStatement(sql);
                                            System.out.println(emailField.getText());
                                            ps.setInt(1, 500);
                                            ps.setString(1, String.valueOf(emailField.getText()));
                                            ps.setString(2, String.valueOf(passwordField.getPassword()));
                                            ps.setString(3, String.valueOf(nameField.getText()));
                                            ps.setString(4, String.valueOf(mobileField.getText()));
                                            ps.setString(5, String.valueOf(locationField.getText()));
                                            ps.setInt(6, 0);

                                            ps.executeUpdate();
                                            ps.close();

                                            setLogin(true);
                                            OnlineBuyCartPanel.setLoginedEmail(emailField.getText());

                                            JOptionPane.showMessageDialog(null, "Đăng ký thành công",
                                                    "Information",
                                                    JOptionPane.INFORMATION_MESSAGE);

                                            MainPanel.setSubContainer(new OnlineBuyCartPanel());

                                            connect.close();

                                        } else {
                                            JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ",
                                                    "ERROR",
                                                    JOptionPane.ERROR_MESSAGE);

                                        }

                                    } else {
                                        JOptionPane.showMessageDialog(null, "Địa chỉ không hợp lệ", "ERROR",
                                                JOptionPane.ERROR_MESSAGE);

                                    }

                                } else {
                                    JOptionPane.showMessageDialog(null, "Tên không hợp lệ", "ERROR",
                                            JOptionPane.ERROR_MESSAGE);

                                }

                            } else {
                                JOptionPane.showMessageDialog(null, "Mật khẩu phải có ít nhất 8 ký tự",
                                        "ERROR",
                                        JOptionPane.ERROR_MESSAGE);

                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Email đã được đăng ký", "ERROR",
                                    JOptionPane.ERROR_MESSAGE);
                            // break;
                        }
                    } else {
                        // if (check == 0) {
                        // JOptionPane.showMessageDialog(null, "Email đã được đăng ký", "ERROR",
                        // JOptionPane.ERROR_MESSAGE);

                        JOptionPane.showMessageDialog(null, "Địa chỉ email không hợp lệ", "ERROR",
                                JOptionPane.ERROR_MESSAGE);

                    }
                } catch (Exception ex) {

                }
            }
        });

        btnBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainPanel.setSubContainer(new OnlineSelectionScrollPane());
            }

        });

    }

    private void addComponent(Component component, int gridx, int gridy, int gridwidth, int gridheight) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        add(component, gbc);
    }

    public static boolean isLogin() {
        return login;
    }

    public static void setLogin(boolean login) {
        try {
            OnlineRegistrationPanel.login = login;
        } catch (IllegalArgumentException ex) {
            OnlineRegistrationPanel.login = false;
        }

    }

}
