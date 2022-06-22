/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Bassam Muhammad
 */
public class OnlineRegistrationPanel extends JPanel {
    private final GridBagConstraints gbc = new GridBagConstraints();
    public static final HashMap<String, Customer> CUSTOMERS = new HashMap<>(); // keep record of customers
    private static boolean login = false;

    static {
        CUSTOMERS.put("anonymousAdmin@amail.com", new Customer("Admin", "Anonymous",
                Arrays.toString(new char[] { 'a', 'd', 'm', 'i', 'n', '1', '2', '3' }), 12345678910l));
        CUSTOMERS.put("Vinh.pn@mail.com", new Customer("Phùng Ngọc Vinh", "Hà Nội",
                Arrays.toString(new char[] { 'a', 'd', 'm', 'i', 'n', '1', '2', '3' }), 12345678910l)); // add admin
                                                                                                        // information
    }

    public OnlineRegistrationPanel() {
        super(new GridBagLayout());

        gbc.insets = new Insets(0, 0, 5, 5); // spacings

        addComponent(new JLabel("Email Address: "), 0, 0, 1, 1);

        JTextField emailField = new JTextField(20);
        addComponent(emailField, 1, 0, 1, 1);

        addComponent(new JLabel("Password: "), 2, 0, 1, 1);

        JPasswordField passwordField = new JPasswordField(20);
        addComponent(passwordField, 3, 0, 2, 1);

        addComponent(new JLabel("Name: "), 0, 1, 1, 1);

        JTextField nameField = new JTextField(20);
        addComponent(nameField, 1, 1, 1, 1);

        addComponent(new JLabel("Mobile Number: "), 2, 1, 1, 1);

        JTextField mobileField = new JTextField(20);
        addComponent(mobileField, 3, 1, 1, 1);

        addComponent(new JLabel("Location: "), 0, 2, 1, 1);

        JButton btnRegister = new JButton("Register");
        addComponent(btnRegister, 1, 3, 1, 1);

        JButton btnBack = new JButton("Back");
        addComponent(btnBack, 2, 3, 1, 1);

        gbc.insets = new Insets(0, -50, 5, 0);
        JTextField locationField = new JTextField(45);
        addComponent(locationField, 1, 2, 3, 1);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (emailField.getText().contains("@mail.com")) {
                    if (!CUSTOMERS.containsKey(emailField.getText())) {
                        if (passwordField.getPassword().length >= 8) {
                            if (!nameField.getText().isBlank()) {
                                if (!locationField.getText().isBlank()) {
                                    try {
                                        long mobileNumber = Long.parseLong(mobileField.getText());
                                        if (mobileNumber < 0 || mobileField.getText().length() != 11)
                                            throw new NumberFormatException();

                                        CUSTOMERS.put(emailField.getText(),
                                                new Customer(nameField.getText(), locationField.getText(),
                                                        Arrays.toString(passwordField.getPassword()), mobileNumber));

                                        setLogin(true);
                                        OnlineBuyCartPanel.setLoginedEmail(emailField.getText());

                                        JOptionPane.showMessageDialog(null, "Successfully registered", "Information",
                                                JOptionPane.INFORMATION_MESSAGE);

                                        MainPanel.setSubContainer(new OnlineBuyCartPanel());

                                    } catch (NumberFormatException ex) {
                                        JOptionPane.showMessageDialog(null, "Invalid mobile number", "ERROR",
                                                JOptionPane.ERROR_MESSAGE);
                                    }

                                } else
                                    JOptionPane.showMessageDialog(null, "Invalid location", "ERROR",
                                            JOptionPane.ERROR_MESSAGE);

                            } else
                                JOptionPane.showMessageDialog(null, "Invalid name", "ERROR", JOptionPane.ERROR_MESSAGE);

                        } else
                            JOptionPane.showMessageDialog(null, "Password should be have atleast 8 character", "ERROR",
                                    JOptionPane.ERROR_MESSAGE);
                    } else
                        JOptionPane.showMessageDialog(null, "Email already registered", "ERROR",
                                JOptionPane.ERROR_MESSAGE);

                } else
                    JOptionPane.showMessageDialog(null, "Invalid Email Address", "ERROR", JOptionPane.ERROR_MESSAGE);

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
