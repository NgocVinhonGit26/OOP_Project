/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Arrays;

/**
 *
 * @author Bassam Muhammad
 */
public class OnlineLoginPanel extends JPanel {
    private static boolean adminAccess = false;

    public OnlineLoginPanel() {
        super();
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);

        Dimension size = new Dimension(1000, 35);

        JTextField emailField = new JTextField(20);
        JPanel emailPanel = new JPanel();
        emailPanel.add(new JLabel("Email Address: "));
        emailPanel.add(emailField);
        emailPanel.setPreferredSize(size);
        emailPanel.setMaximumSize(size);

        JPasswordField passwordField = new JPasswordField(20);
        JPanel passwordPanel = new JPanel();
        passwordPanel.add(new JLabel("Password:"));
        passwordPanel.add(passwordField);
        passwordPanel.setPreferredSize(size);
        passwordPanel.setMaximumSize(size);

        JButton btnforgotPassword = new JButton("Forgot Password");
        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("Register");
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
                String email = JOptionPane.showInputDialog(null, "Enter email address");
                if (OnlineRegistrationPanel.CUSTOMERS.containsKey(email))
                    JOptionPane.showMessageDialog(null, "Message sent to your email and mobile", "Information",
                            JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, "Incorrect Email", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (OnlineRegistrationPanel.CUSTOMERS.containsKey(emailField.getText())) {
                    if (OnlineRegistrationPanel.CUSTOMERS.get(emailField.getText()).getPassword()
                            .equals(Arrays.toString(passwordField.getPassword()))) { // if entered password matches with
                                                                                     // password in system
                        OnlineRegistrationPanel.setLogin(true);
                        OnlineBuyCartPanel.setLoginedEmail(emailField.getText());
                        if (emailField.getText().equals("anonymousAdmin@amail.com")
                                || emailField.getText().equals("Vinh.pn@mail.com")) {
                            JOptionPane.showMessageDialog(null, "Admin Access");
                            adminAccess = true;
                            MainPanel.setSubContainer(new OnlineSelectionScrollPane());
                        } else {
                            JOptionPane.showMessageDialog(null, "Successfully Login");
                            MainPanel.setSubContainer(new OnlineBuyCartPanel());
                        }

                    } else
                        JOptionPane.showMessageDialog(null, "Incorrect Password", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else
                    JOptionPane.showMessageDialog(null, "Incorrect Email", "ERROR", JOptionPane.ERROR_MESSAGE);

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