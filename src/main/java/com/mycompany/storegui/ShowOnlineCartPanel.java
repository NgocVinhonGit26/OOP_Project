/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

import java.awt.*;
import java.awt.Color;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import org.w3c.dom.events.MouseEvent;

import com.mycompany.connectDB.connectDB;

import java.awt.event.*;
import java.rmi.server.ObjID;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class ShowOnlineCartPanel extends JPanel {
    private JTable table;
    connectDB conn;
    LocalDate now = LocalDate.now();
    private String date;
    private float totalValue = 0;
    private float totalCost = 0;
    public static float chietkhau;

    public ShowOnlineCartPanel() {
        setLayout(null);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(594, 10, 62, 21);
        JButton btnBuyCart = new JButton("Mua sản phẩm trong giỏ hàng");
        btnBuyCart.setBounds(681, 10, 200, 21);

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

        showCart(scrollPane);
    }

    private void showCart(JScrollPane scrollPane) {
        int i = 0;
        Object columnNames[] = { "Tên sản phẩm", "Số lượng", "Đơn giá", "Giá trị" };
        Object rowData[][] = new Object[50][4];
        for (Cart cart : OnlineSelectionScrollPane.CART) {

            Object tmp[][] = { { cart.getNameProduct(), cart.getSl(), cart.getCost(), cart.getTotalCost() } };
            System.arraycopy(tmp, 0, rowData, i, tmp.length);

            totalValue += cart.getTotalCost();
            chietkhau = cart.getChietkhau();
            System.out.println("chiet khau: " + chietkhau);
            table = new JTable(rowData, columnNames);
            scrollPane.setViewportView(table);
            i++;
        }

        // try {
        // Connection connect = conn.getConnection();
        // Statement stmt = connect.createStatement();
        // PreparedStatement idCart = connect.prepareStatement("select max(idHD) from
        // `hoadon` where id = ?");
        // idCart.setInt(1, OnlineLoginPanel.idUser);
        // ResultSet idHD = idCart.executeQuery();

        // while (idHD.next()) {
        // PreparedStatement u = connect.prepareStatement("select * from chitiethd where
        // `idHD` = ?");
        // u.setInt(1, idHD.getInt(1));
        // ResultSet rs = u.executeQuery();
        // ResultSet search;
        // Object columnNames[] = { "Tên sản phẩm", "Số lượng", "Đơn giá", "Giá trị" };
        // Object rowData[][] = new Object[50][4];
        // int i = 0;
        // chietkhau = idHD.getFloat(4);
        // date = idHD.getString(3);
        // while (rs.next()) {

        // if (rs.getInt(2) > 0 && rs.getInt(2) < 31) {
        // String sql = "select `tensanpham`, `giaban` from diaphim where `masanpham` =
        // ?";
        // PreparedStatement s = connect.prepareStatement(sql);
        // s.setInt(1, rs.getInt(2));
        // search = s.executeQuery();

        // while (search.next()) {

        // Object tmp[][] = { { search.getString(1), rs.getInt(3), search.getFloat(2),
        // rs.getInt(3) * search.getFloat(2) } };
        // System.arraycopy(tmp, 0, rowData, i, tmp.length);
        // i = i + 1;
        // totalValue += rs.getInt(3) * search.getFloat(2);
        // }
        // } else {
        // if (rs.getInt(2) > 30 && rs.getInt(2) < 61) {
        // String sql = "select `tensanpham`, `giaban` from sach where `masanpham` = ?";
        // PreparedStatement s = connect.prepareStatement(sql);
        // s.setInt(1, rs.getInt(2));
        // search = s.executeQuery();

        // while (search.next()) {

        // Object tmp[][] = { { search.getString(1), rs.getInt(3), search.getFloat(2),
        // rs.getInt(3) * search.getFloat(2) } };
        // System.arraycopy(tmp, 0, rowData, i, tmp.length);
        // i = i + 1;
        // totalValue += rs.getInt(3) * search.getFloat(2);
        // }
        // } else {
        // if (rs.getInt(2) > 60) {
        // String sql = "select `tensanpham`, `giaban` from dianhac where `masanpham` =
        // ?";
        // PreparedStatement s = connect.prepareStatement(sql);
        // s.setInt(1, rs.getInt(2));
        // search = s.executeQuery();

        // while (search.next()) {

        // Object tmp[][] = { { search.getString(1), rs.getInt(3), search.getFloat(2),
        // rs.getInt(3) * search.getFloat(2) } };
        // System.arraycopy(tmp, 0, rowData, i, tmp.length);
        // i = i + 1;
        // totalValue += rs.getInt(3) * search.getFloat(2);
        // }
        // }
        // }
        // }
        // table = new JTable(rowData, columnNames);
        // scrollPane.setViewportView(table);
        // }
        // }

        // } catch (Exception ex) {
        // // TODO: handle exception
        // }

        totalCost = totalValue - totalValue * chietkhau;
        JLabel lblNewLabel = new JLabel("Ngày tháng năm:" + " " + now);
        lblNewLabel.setBounds(213, 697, 193, 68);
        add(lblNewLabel);

        JLabel lblChitKhu = new JLabel("Chiết khấu: " + " " + chietkhau);
        lblChitKhu.setBounds(1195, 744, 193, 21);
        add(lblChitKhu);

        JLabel lblTngTin = new JLabel("Tổng tiền:" + " " + totalCost);
        lblTngTin.setBounds(1195, 776, 193, 13);
        add(lblTngTin);

        JLabel lblTngGiTr = new JLabel("Tổng giá trị:" + " " + totalValue);
        lblTngGiTr.setBounds(1195, 709, 193, 21);
        add(lblTngGiTr);

        // chuc nang remove item khoi gio hang

        // table.addMouseListener(new MouseAdapter() {
        // @Override
        // public void mouseClicked(MouseEvent e) {
        // int choice = JOptionPane.showConfirmDialog(null, "Lấy ra khỏi giỏ hàng?",
        // "Confirmation",
        // JOptionPane.YES_NO_OPTION);
        // }
        // });
    }
}
