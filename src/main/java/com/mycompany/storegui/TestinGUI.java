/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

import javax.swing.JFrame;

public class TestinGUI {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Media Shop");
        MainPanel.setSubContainer(new MainMenuPanel());
        frame.add(MainPanel.MAIN_PANEL);
        frame.setSize(1000, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
