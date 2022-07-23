package com.mycompany.storegui;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

import com.mycompany.connectDB.connectDB;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.util.List;

public class CostsIncurred {
    connectDB conn;
    private JTable table;

    public CostsIncurred(Date dateFrom, Date dateTo) {

        java.sql.Date dateNow = new java.sql.Date(System.currentTimeMillis());
        JFrame detailFrame = new JFrame();
        detailFrame.setSize(500, 500);
        detailFrame.getContentPane().setLayout(null);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 44, 486, 419);
        detailFrame.getContentPane().add(scrollPane);

        try {
            Connection connect = conn.getConnection();
            String sql = "select * from chiphikhac where `thoigiantao` between ? and ?";
            PreparedStatement pr = connect.prepareStatement(sql);
            pr.setString(1, String.valueOf(dateFrom));
            pr.setString(2, String.valueOf(dateTo));
            ResultSet rs = pr.executeQuery();
            Object columnNames[] = { "Số thứ tự", "Tên chi phí", "Chi phí", "Ngày phát sinh" };
            Object rowData[][] = new Object[50][4];
            int i = 0;
            while (rs.next()) {
                Object tmp[][] = { { i + 1, rs.getString(2), rs.getFloat(3), rs.getDate(4) } };
                System.arraycopy(tmp, 0, rowData, i, tmp.length);
                i++;
            }

            table = new JTable(rowData, columnNames);
            scrollPane.setViewportView(table);

            JButton btnNewButton = new JButton("Thêm chi phí khác");
            btnNewButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String costName = JOptionPane.showInputDialog(null, "Nhập tên chi phí phát sinh");
                    if (!costName.isBlank()) {
                        String costText = JOptionPane.showInputDialog(null, "Nhập chi phí");
                        if (costText.isBlank()) {
                            JOptionPane.showMessageDialog(null, "Chi phí không hợp lệ", "ERROR",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        for (char c : costText.toCharArray()) {
                            if (!Character.isDigit(c)) {
                                JOptionPane.showMessageDialog(null,
                                        "Chi phí không hợp lệ",
                                        "ERROR", JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                        }
                        float cost = Float.valueOf(costText);
                        try {
                            String addCostIncurred = "insert into chiphikhac (`tenchiphi`,`chiphi`,`thoigiantao`) values (?,?,?)";
                            PreparedStatement ps = connect.prepareStatement(addCostIncurred);
                            ps.setString(1, costName);
                            ps.setFloat(2, cost);
                            ps.setDate(3, dateNow);
                            ps.executeUpdate();
                            ps.close();
                        } catch (Exception ex) {
                            // TODO: handle exception
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Chưa nhập tên chi phí", "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            btnNewButton.setBounds(170, 10, 153, 21);
            detailFrame.getContentPane().add(btnNewButton);
        } catch (Exception e) {
            // TODO: handle exception
        }

        // table = new JTable();

        Dimension dimension = new Dimension(5, 0);
        detailFrame.setVisible(true);
    }
}
