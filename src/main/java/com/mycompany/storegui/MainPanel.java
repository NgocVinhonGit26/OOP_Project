/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

import java.awt.*;
import javax.swing.JPanel;


public final class MainPanel {
    public static final JPanel MAIN_PANEL = new JPanel(new BorderLayout());
    
    private MainPanel(){
        
    }
        
    public static void setSubContainer(Container container){
        MAIN_PANEL.removeAll();
        MAIN_PANEL.add(container);
        MAIN_PANEL.validate();
        MAIN_PANEL.repaint();
    }
}
