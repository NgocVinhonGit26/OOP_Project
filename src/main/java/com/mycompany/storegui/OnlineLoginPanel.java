/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Thread.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

import com.mycompany.connectDB.connectDB;

import java.util.Arrays;

public class OnlineLoginPanel extends JPanel {
    private static boolean adminAccess = false;
    private static connectDB conn;

    public OnlineLoginPanel() {
        super();
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);

        Dimension size = new Dimension(1000, 35);

        JTextField emailField = new JTextField(20);
        JPanel emailPanel = new JPanel();
        emailPanel.add(new JLabel("Tài khoản: "));
        emailPanel.add(emailField);
        emailPanel.setPreferredSize(size);
        emailPanel.setMaximumSize(size);

        JPasswordField passwordField = new JPasswordField(20);
        JPanel passwordPanel = new JPanel();
        passwordPanel.add(new JLabel("Mật khẩu:"));
        passwordPanel.add(passwordField);
        passwordPanel.setPreferredSize(size);
        passwordPanel.setMaximumSize(size);

        JButton btnforgotPassword = new JButton("Quên mật khẩu");
        JButton btnLogin = new JButton("Đăng nhập");
        JButton btnRegister = new JButton("Đăng ký");
        JButton btnBack = new JButton("Back");
        JPanel logPanel = new JPanel();

        logPanel.add(btnforgotPassword);
        logPanel.add(btnLogin);
        logPanel.add(btnRegister);
        logPanel.add(btnBack);
        logPanel.setPreferredSize(size);
        logPanel.setMaximumSize(size);

        add(Box.createRigidArea(new Dimension(100, 250))); // create vertical space
        add(emailPanel);
        add(passwordPanel);
        add(logPanel);

        btnforgotPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = JOptionPane.showInputDialog(null, "Nhập địa chỉ email");
                try {
                    Connection connect = conn.getConnection();
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery("select * from user");
                    int check = 0;
                    while (rs.next()) {
                        if (rs.getString(2).equals(email)) {
                            JOptionPane.showMessageDialog(null,
                                    "Tin nhắn được gửi đến email và điện thoại di động của bạn",
                                    "Information", JOptionPane.INFORMATION_MESSAGE);
                            check = 1;
                        }
                    }
                    if (check == 0)
                        JOptionPane.showMessageDialog(null, "Incorrect Email", "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    // TODO: handle exception
                }
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connect = conn.getConnection();
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery("select * from user");
                    int check = 0;
                    while (rs.next()) {
                        // System.out.println(rs.getString(2));
                        if (emailField.getText().equals(rs.getString(2))) {
                            check = 1;
                            if (rs.getString(3).equals(String.valueOf(passwordField.getPassword()))) {
                                OnlineRegistrationPanel.setLogin(true);
                                OnlineBuyCartPanel.setLoginedEmail(emailField.getText());
                                if (rs.getBoolean(7)) {
                                    JOptionPane.showMessageDialog(null, "Admin Access");
                                    adminAccess = true;
                                    MainPanel.setSubContainer(new OnlineSelectionScrollPane());
                                    break;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Đăng nhập thành công");
                                    MainPanel.setSubContainer(new OnlineBuyCartPanel());
                                    break;
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Sai mật khẩu", "ERROR",
                                        JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                        }
                    }
                    if (check == 0) {
                        JOptionPane.showMessageDialog(null, "Nhập sai Email", "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {

                }

            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPanel.setSubContainer(new OnlineRegistrationPanel());
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPanel.setSubContainer(new OnlineSelectionScrollPane());
            }
        });

    }

    public static boolean isAdminAccess() {
        return adminAccess;
    }

    public static void setAdminAccess(boolean access) {
        try {
            adminAccess = access;
        } catch (IllegalArgumentException ex) {
            adminAccess = false;
        }
    }
}