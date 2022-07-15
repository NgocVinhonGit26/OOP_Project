/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

import java.awt.*;
import java.awt.Color;
import javax.swing.*;

import com.mycompany.connectDB.connectDB;

import java.awt.event.*;
import java.rmi.server.ObjID;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class test extends JPanel {
    private JTable table;
    connectDB conn;

    public test() {
        setLayout(null);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(594, 10, 62, 21);
        JButton btnBuyCart = new JButton("Mua sản phẩm trong giỏ hàng");
        btnBuyCart.setBounds(681, 10, 189, 21);

        JPanel btnsPanel = new JPanel();
        btnsPanel.setBounds(0, 0, 1920, 41);
        btnsPanel.setLayout(null);
        btnsPanel.add(btnBack);
        btnsPanel.add(btnBuyCart);

        add(btnsPanel);

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

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(241, 56, 1096, 631);
        add(scrollPane);

        try {
            Connection connect = conn.getConnection();
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("select * from chitiethd");
            ResultSet search;
            Object columnNames[] = { "Tên sản phẩm", "Số lượng", "Đơn giá", "Giá trị" };
            Object rowData[][] = new Object[50][4];

            int i = 0;
            while (rs.next()) {
                if (rs.getInt(2) > 0 && rs.getInt(2) < 31) {
                    String sql = "select `tensanpham`, `giaban` from diaphim where `masanpham` = ?";
                    PreparedStatement s = connect.prepareStatement(sql);
                    s.setInt(1, rs.getInt(2));
                    search = s.executeQuery();

                    while (search.next()) {

                        Object tmp[][] = { { search.getString(1), rs.getInt(3), search.getFloat(2),
                                rs.getInt(3) * search.getFloat(2) } };
                        System.arraycopy(tmp, 0, rowData, i, tmp.length);
                        i = i + 1;
                    }
                } else {
                    if (rs.getInt(2) > 30 && rs.getInt(2) < 61) {
                        String sql = "select `tensanpham`, `giaban` from sach where `masanpham` = ?";
                        PreparedStatement s = connect.prepareStatement(sql);
                        s.setInt(1, rs.getInt(2));
                        search = s.executeQuery();

                        while (search.next()) {

                            Object tmp[][] = { { search.getString(1), rs.getInt(3), search.getFloat(2),
                                    rs.getInt(3) * search.getFloat(2) } };
                            System.arraycopy(tmp, 0, rowData, i, tmp.length);
                            i = i + 1;
                        }
                    } else {
                        if (rs.getInt(2) > 60) {
                            String sql = "select `tensanpham`, `giaban` from dianhac where `masanpham` = ?";
                            PreparedStatement s = connect.prepareStatement(sql);
                            s.setInt(1, rs.getInt(2));
                            search = s.executeQuery();

                            while (search.next()) {

                                Object tmp[][] = { { search.getString(1), rs.getInt(3), search.getFloat(2),
                                        rs.getInt(3) * search.getFloat(2) } };
                                System.arraycopy(tmp, 0, rowData, i, tmp.length);
                                i = i + 1;
                            }
                        }
                    }
                }
                table = new JTable(rowData, columnNames);
                scrollPane.setViewportView(table);
            }

        } catch (Exception ex) {
            // TODO: handle exception
        }

        // showCart();

        JLabel lblNewLabel = new JLabel("Ngày tháng năm: ");
        lblNewLabel.setBounds(213, 697, 193, 68);
        add(lblNewLabel);

        JLabel lblChitKhu = new JLabel("Chiết khấu: \r\n");
        lblChitKhu.setBounds(1195, 744, 193, 21);
        add(lblChitKhu);

        JLabel lblTngTin = new JLabel("Tổng tiền:");
        lblTngTin.setBounds(1195, 776, 193, 13);
        add(lblTngTin);
        
        JLabel lblTngGiTr = new JLabel("Tổng giá trị: ");
        lblTngGiTr.setBounds(1195, 709, 193, 21);
        add(lblTngGiTr);

    }

    private void showCart() {

        for (Item item : OnlineSelectionScrollPane.CART) {
            JPanel subPanel = new JPanel();
            subPanel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray,
                    Color.black));
            JLabel label = new JLabel(item.getName() + " (Giá: " + item.getCost() + ")",
                    new ImageIcon(item.getImageAddress()), JLabel.CENTER);

            subPanel.add(label);
            subPanel.setMaximumSize(new Dimension(1000, 75));
            cartPanel.add(subPanel);
            subPanel.setToolTipText("Nhấn vào đây để loại bỏ khỏi giỏ hàng");

            subPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int choice = JOptionPane.showConfirmDialog(null, "Lấy ra khỏi giỏ hàng?",
                            "Confirmation",
                            JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        OnlineSelectionScrollPane.CART.remove(item);
                        // cartPanel.remove(subPanel);
                        // cartPanel.validate();
                        // cartPanel.repaint();
                    }

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    subPanel.setBackground(Color.lightGray);
                    subPanel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray,
                            Color.red));

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    subPanel.setBackground(null);
                    subPanel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray,
                            Color.black));
                }

            });
        }
    }
}
