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
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

import com.mycompany.connectDB.connectDB;

import java.util.Arrays;
import java.awt.Label;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class test extends JPanel {
    private static boolean adminAccess = false;
    private static connectDB conn;
    private JTextField textField;

    public test() {
        super();
        setBackground(Color.PINK);

        Dimension size = new Dimension(1000, 35);
        setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBounds(312, 223, 808, 400);
        add(panel);
                        panel.setLayout(null);
                
                        JPasswordField passwordField = new JPasswordField(20);
                        JPanel passwordPanel = new JPanel();
                        passwordPanel.setBounds(301, 218, 500, 35);
                        panel.add(passwordPanel);
                        passwordPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
                        JLabel label_3 = new JLabel("Mật khẩu:");
                        passwordPanel.add(label_3);
                        passwordPanel.add(passwordField);
                        passwordPanel.setPreferredSize(new Dimension(500, 35));
                        passwordPanel.setMaximumSize(new Dimension(500, 35));
        
                JButton btnforgotPassword = new JButton("Quên mật khẩu");
                JButton btnLogin = new JButton("Đăng nhập");
                JButton btnRegister = new JButton("Đăng ký");
                JButton btnBack = new JButton("Back");
                JPanel logPanel = new JPanel();
                logPanel.setBounds(301, 263, 500, 35);
                panel.add(logPanel);
                
                        logPanel.add(btnforgotPassword);
                        logPanel.add(btnLogin);
                        logPanel.add(btnRegister);
                        logPanel.add(btnBack);
                        logPanel.setPreferredSize(new Dimension(500, 35));
                        logPanel.setMaximumSize(new Dimension(500, 35));
                        
                        JLabel label = new JLabel("");
//                        label.setBackground(Color.PINK);
                        try {
                            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("avt_login1.png");
                            label = new JLabel(new ImageIcon(url), JLabel.CENTER);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Image not found", "ERROR", JOptionPane.ERROR_MESSAGE);
                            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("close-icon.png");
                            label = new JLabel("", new ImageIcon(url), JLabel.CENTER);
                        }
                        label.setBounds(10, 90, 282, 241);
                        panel.add(label);
                        
                        JLabel label_1 = new JLabel("");
                        try {
                            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("login_title.png");
                            label_1 = new JLabel(new ImageIcon(url), JLabel.CENTER);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Image not found", "ERROR", JOptionPane.ERROR_MESSAGE);
                            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("close-icon.png");
                            label_1 = new JLabel("", new ImageIcon(url), JLabel.CENTER);
                        }
                        label_1.setBounds(301, 50, 490, 110);
                        panel.add(label_1);
                        
                        JPanel emailPanel = new JPanel();
                        emailPanel.setPreferredSize(new Dimension(500, 35));
                        emailPanel.setMaximumSize(new Dimension(500, 35));
                        emailPanel.setBounds(301, 170, 500, 35);
                        panel.add(emailPanel);
                        
                        JLabel label_2 = new JLabel("Tài khoản: ");
                        emailPanel.add(label_2);
                        
                        textField = new JTextField(20);
                        emailPanel.add(textField);
                        
                       

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