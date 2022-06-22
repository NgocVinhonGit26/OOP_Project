/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Bassam Muhammad
 */
public class OnlineBuyCartPanel extends JPanel{
    private final GridBagConstraints gbc = new GridBagConstraints();
    private static String loginedEmail = "";
    
    public OnlineBuyCartPanel(){
        super(new GridBagLayout());
        
        double sum = 0;
        for(Item item : OnlineSelectionScrollPane.CART)
            sum += item.getCost();
        
        gbc.insets = new Insets(0, 0,  15, 5);      //spacings
        
        JLabel totalLabel = new JLabel("Total:  " + sum);
        totalLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        addComponent(totalLabel, 1, 0, 2, 1);
        
        gbc.insets = new Insets(0, 0,  5, 5);
        addComponent(new JLabel("Name: "), 0, 1, 1, 1);
        
        JTextField nameField = new JTextField(OnlineRegistrationPanel.CUSTOMERS.get(loginedEmail).getName(), 20);
        addComponent(nameField, 1, 1, 1, 1);
        
        addComponent(new JLabel("Mobile Number: "), 2, 1, 1, 1);
        
        JTextField mobileField = new JTextField("" + OnlineRegistrationPanel.CUSTOMERS.get(loginedEmail).getPhoneNumber(), 20);
        addComponent(mobileField, 3, 1, 1, 1);
        
        addComponent(new JLabel("Location: "), 0, 2, 1, 1);
                        
        gbc.insets = new Insets(0, 0, 5, 5);
        addComponent(new JLabel("Card Number: "), 0, 3, 1, 1);
       
        JTextField cardField = new JTextField(20);  
        addComponent(cardField, 1, 3, 1, 1);
        
        addComponent(new JLabel("Card PIN: "), 2, 3, 1, 1);
        
        JTextField pinField = new JTextField(20);
        addComponent(pinField, 3, 3, 1, 1);
        
        JButton btnShowCart = new JButton("Show Cart");
        addComponent(btnShowCart, 1, 4, 1, 1);
        
        JButton btnBuy = new JButton("Buy");
        addComponent(btnBuy, 2, 4, 1, 1);
        
        JButton btnBack = new JButton("Back");
        addComponent(btnBack, 3, 4, 1, 1);
        
        gbc.insets = new Insets(0, -50, 5, 0);
        JTextField locationField = new JTextField(OnlineRegistrationPanel.CUSTOMERS.get(loginedEmail).getAddress(), 45);
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
                try{
                    Long.parseLong(cardField.getText());
                    Integer.parseInt(pinField.getText());
                    Long.parseLong(mobileField.getText());
                    
                    if(cardField.getText().length() != 16 || pinField.getText().length() != 4 || 
                            mobileField.getText().length() != 11 || nameField.getText().isBlank())
                        throw new NumberFormatException();
                    
                    JOptionPane.showMessageDialog(null, "Thank You!! Ordered placed successfully\n"
                            + "Order will be delivered within 15 working days");
                    OnlineSelectionScrollPane.CART.clear();
                    MainPanel.setSubContainer(new OnlineSelectionScrollPane());
                } catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "A field is empty or data is not correct!! Try again",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });
    }
    private void addComponent(Component component,int gridx, int gridy, int gridwidth, int gridheight){
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
