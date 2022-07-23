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
import java.io.DataOutput;
import java.net.URL;
import java.rmi.server.ObjID;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComponent;

public class Dashboard extends JPanel {
    private JTable table;
    private static connectDB conn;
    LocalDate now = LocalDate.now();
    private String date;
    private float totalValue = 0;
    private float totalCost = 0;
    public static float chietkhau;
    public static Statistical statistical = new Statistical();
    MouseAdapter mouseLocal1;
    MouseAdapter mouseLocal2;

    public Dashboard() {
        setLayout(null);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(594, 10, 62, 21);
        JButton btnBuyCart = new JButton("Đăng xuất\r\n");
        btnBuyCart.setBounds(681, 10, 151, 21);

        JPanel btnsPanel = new JPanel();
        btnsPanel.setBounds(0, 0, 1920, 41);
        btnsPanel.setLayout(null);
        btnsPanel.add(btnBack);
        btnsPanel.add(btnBuyCart);

        add(btnsPanel);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(173, 255, 47));
        panel.setBounds(91, 258, 310, 248);
        add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel_2 = new JLabel("Tổng đơn hàng");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel_2.setBounds(30, 183, 154, 25);
        panel.add(lblNewLabel_2);

        JLabel lblNewLabel_2_4 = new JLabel(String.valueOf(statistical.getTotalOrder()));
        lblNewLabel_2_4.setForeground(new Color(255, 255, 255));
        lblNewLabel_2_4.setFont(new Font("Tahoma", Font.PLAIN, 60));
        lblNewLabel_2_4.setBounds(30, 57, 154, 95);
        panel.add(lblNewLabel_2_4);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(255, 99, 71));
        panel_1.setBounds(429, 258, 310, 248);
        add(panel_1);
        panel_1.setLayout(null);

        JLabel lblNewLabel_2_1 = new JLabel("Tổng mặt hàng");
        lblNewLabel_2_1.setBounds(30, 183, 136, 25);
        lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panel_1.add(lblNewLabel_2_1);

        JLabel lblNewLabel_2_4_1 = new JLabel(String.valueOf(statistical.getTotalProduct()));
        lblNewLabel_2_4_1.setForeground(Color.WHITE);
        lblNewLabel_2_4_1.setFont(new Font("Tahoma", Font.PLAIN, 60));
        lblNewLabel_2_4_1.setBounds(30, 54, 154, 95);
        panel_1.add(lblNewLabel_2_4_1);

        JPanel panel_2 = new JPanel();
        panel_2.setBackground(new Color(147, 112, 219));
        panel_2.setBounds(777, 258, 351, 248);
        add(panel_2);
        panel_2.setLayout(null);

        JLabel lblNewLabel_2_2 = new JLabel("Tổng doanh thu");
        lblNewLabel_2_2.setBounds(28, 184, 185, 25);
        lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panel_2.add(lblNewLabel_2_2);

        JLabel lblNewLabel_2_4_2 = new JLabel(String.valueOf(statistical.getTotalCost()));
        lblNewLabel_2_4_2.setForeground(Color.WHITE);
        lblNewLabel_2_4_2.setFont(new Font("Tahoma", Font.PLAIN, 60));
        lblNewLabel_2_4_2.setBounds(28, 50, 313, 95);
        panel_2.add(lblNewLabel_2_4_2);

        JPanel panel_3 = new JPanel();
        panel_3.setBackground(Color.ORANGE);
        panel_3.setBounds(1159, 258, 345, 248);
        add(panel_3);
        panel_3.setLayout(null);

        JLabel lblNewLabel_2_3 = new JLabel("Lợi nhuận");
        lblNewLabel_2_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel_2_3.setBounds(24, 183, 154, 25);
        panel_3.add(lblNewLabel_2_3);

        JLabel lblNewLabel_2_4_3 = new JLabel(String.valueOf(statistical.getTotalProfit()));
        lblNewLabel_2_4_3.setForeground(Color.WHITE);
        lblNewLabel_2_4_3.setFont(new Font("Tahoma", Font.PLAIN, 60));
        lblNewLabel_2_4_3.setBounds(24, 46, 311, 95);
        panel_3.add(lblNewLabel_2_4_3);

        JLabel lblNewLabel_1 = new JLabel("Dashboard\r\n");
        lblNewLabel_1.setFont(new Font("Calibri Light", Font.BOLD | Font.ITALIC, 60));
        lblNewLabel_1.setBounds(579, 99, 392, 118);
        add(lblNewLabel_1);

        JPanel panel_4 = new JPanel();
        panel_4.setBackground(new Color(199, 21, 133));
        panel_4.setBounds(91, 534, 1413, 4);
        add(panel_4);

        JLabel lblNewLabel_3 = new JLabel("Chào mừng admin đến với giao diện quản lý cửa hàng Media.");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel_3.setBounds(101, 548, 1090, 26);
        add(lblNewLabel_3);

        JPanel panel_5 = new JPanel();
        panel_5.addMouseListener(new MouseAdapter() {
            // @Override
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // TODO Auto-generated method stub
                super.mouseClicked(e);
                System.out.println("vinh dep trai");
            }

        });
        panel_5.setBackground(Color.WHITE);
        panel_5.setBounds(253, 584, 498, 95);
        add(panel_5);
        panel_5.setLayout(null);

        JPanel panel_7 = new JPanel();
        panel_7.setBackground(Color.LIGHT_GRAY);
        panel_7.setBounds(145, 10, 3, 75);
        panel_5.add(panel_7);

        JPanel panel_6_1 = new JPanel();
        panel_6_1.setBackground(Color.WHITE);
        panel_6_1.setBounds(181, 10, 284, 75);
        panel_5.add(panel_6_1);
        panel_6_1.setLayout(null);

        JLabel lblNewLabel_6 = new JLabel("Danh sách khách hàng");
        lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel_6.setBounds(40, 24, 204, 30);
        panel_6_1.add(lblNewLabel_6);

        JLabel lblNewLabel = new JLabel("fdsfsd");
        try {
            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("icon_kh.png");
            lblNewLabel = new JLabel(new ImageIcon(url), JLabel.CENTER);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Image not found", "ERROR", JOptionPane.ERROR_MESSAGE);
            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("close-icon.png");
            lblNewLabel = new JLabel("", new ImageIcon(url), JLabel.CENTER);
        }
        lblNewLabel.setBackground(Color.GREEN);
        lblNewLabel.setBounds(27, 10, 97, 75);
        panel_5.add(lblNewLabel);

        JPanel panel_5_2 = new JPanel();
        panel_5_2.setBackground(Color.WHITE);
        panel_5_2.setBounds(950, 584, 399, 217);
        add(panel_5_2);
        panel_5_2.setLayout(null);

        JPanel panel_6 = new JPanel();
        panel_6.setBounds(10, 10, 379, 92);
        panel_5_2.add(panel_6);
        panel_6.setLayout(null);

        JLabel lblNewLabel_7 = new JLabel("Sản phẩm bán chạy nhất");
        lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_7.setBounds(104, 24, 211, 48);
        panel_6.add(lblNewLabel_7);

        JLabel lblNewLabel_7_2 = new JLabel();
        try {
            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("hot_deal.png");
            lblNewLabel_7_2 = new JLabel(new ImageIcon(url), JLabel.CENTER);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Image not found", "ERROR", JOptionPane.ERROR_MESSAGE);
            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("close-icon.png");
            lblNewLabel_7_2 = new JLabel("", new ImageIcon(url), JLabel.CENTER);
        }
        lblNewLabel_7_2.setBounds(10, 10, 84, 62);
        panel_6.add(lblNewLabel_7_2);

        JLabel lblNewLabel_7_3 = new JLabel("0");
        lblNewLabel_7_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_7_3.setBounds(325, 24, 44, 48);
        panel_6.add(lblNewLabel_7_3);

        JPanel panel_8 = new JPanel();
        panel_8.setBackground(Color.LIGHT_GRAY);
        panel_8.setBounds(22, 112, 355, 3);
        panel_5_2.add(panel_8);

        JPanel panel_6_2 = new JPanel();
        panel_6_2.setLayout(null);
        panel_6_2.setBounds(10, 125, 379, 82);
        panel_5_2.add(panel_6_2);

        JLabel lblNewLabel_7_1 = new JLabel("Chi phí phát sinh");
        lblNewLabel_7_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_7_1.setBounds(104, 17, 212, 48);
        panel_6_2.add(lblNewLabel_7_1);

        JLabel lblNewLabel_7_2_1 = new JLabel();
        try {
            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("cpps.png");
            lblNewLabel_7_2_1 = new JLabel(new ImageIcon(url), JLabel.CENTER);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Image not found", "ERROR", JOptionPane.ERROR_MESSAGE);
            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("close-icon.png");
            lblNewLabel_7_2_1 = new JLabel("", new ImageIcon(url), JLabel.CENTER);
        }
        lblNewLabel_7_2_1.setBounds(10, 10, 84, 62);
        panel_6_2.add(lblNewLabel_7_2_1);

        JLabel lblNewLabel_7_3_1 = new JLabel("0");
        lblNewLabel_7_3_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_7_3_1.setBounds(326, 17, 43, 48);
        panel_6_2.add(lblNewLabel_7_3_1);

        JPanel panel_5_1 = new JPanel();
        panel_5_1.setLayout(null);
        panel_5_1.setBackground(Color.WHITE);
        panel_5_1.setBounds(253, 706, 498, 95);
        add(panel_5_1);

        JPanel panel_7_1 = new JPanel();
        panel_7_1.setBackground(Color.LIGHT_GRAY);
        panel_7_1.setBounds(145, 10, 3, 75);
        panel_5_1.add(panel_7_1);

        JPanel panel_6_1_1 = new JPanel();
        panel_6_1_1.setLayout(null);
        panel_6_1_1.setBackground(Color.WHITE);
        panel_6_1_1.setBounds(181, 10, 284, 75);
        panel_5_1.add(panel_6_1_1);

        JLabel lblNewLabel_6_1 = new JLabel("Danh sách sản phẩm");
        lblNewLabel_6_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel_6_1.setBounds(41, 21, 204, 30);
        panel_6_1_1.add(lblNewLabel_6_1);

        JLabel lblNewLabel_4 = new JLabel((Icon) null, SwingConstants.CENTER);
        try {
            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("list.png");
            lblNewLabel_4 = new JLabel(new ImageIcon(url), JLabel.CENTER);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Image not found", "ERROR", JOptionPane.ERROR_MESSAGE);
            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("close-icon.png");
            lblNewLabel_4 = new JLabel("", new ImageIcon(url), JLabel.CENTER);
        }
        lblNewLabel_4.setBackground(Color.GREEN);
        lblNewLabel_4.setBounds(27, 10, 97, 75);
        panel_5_1.add(lblNewLabel_4);

        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setBounds(168, 182, 132, 19);
        add(dateChooser);

        JLabel lblNewLabel_5 = new JLabel("Từ ngày:");
        lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_5.setBounds(91, 175, 65, 26);
        add(lblNewLabel_5);

        JLabel lblNewLabel_5_1 = new JLabel("Đến ngày:");
        lblNewLabel_5_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_5_1.setBounds(91, 211, 70, 26);
        add(lblNewLabel_5_1);

        JDateChooser dateChooser_1 = new JDateChooser();
        dateChooser_1.setBounds(168, 218, 132, 19);
        add(dateChooser_1);

        JButton btnNewButton = new JButton("Thống kê");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dateChooser.getDate() != null && dateChooser_1.getDate() != null) {
                    java.sql.Date dateFrom = new java.sql.Date(dateChooser.getDate().getTime());
                    java.sql.Date dateTo = new java.sql.Date(dateChooser_1.getDate().getTime());
                    java.sql.Date dateNow = new java.sql.Date(System.currentTimeMillis());
                    if (dateFrom.before(dateTo) && dateTo.before(dateNow) && dateFrom.before(dateNow)) {
                        System.out.println("he lo");

                        try {
                            panel_6.removeMouseListener(mouseLocal1);
                            panel_6_2.removeMouseListener(mouseLocal2);
                            MouseAdapter mouse1 = new MouseAdapter() {
                                // @Override
                                @Override
                                public void mouseClicked(java.awt.event.MouseEvent e) {
                                    // TODO Auto-generated method stub
                                    super.mouseClicked(e);

                                    System.out.println("panel6");
                                    DetailHotdeal hotdeal = new DetailHotdeal(dateFrom, dateTo);

                                }
                            };
                            panel_6.addMouseListener(mouse1);
                            mouseLocal1 = mouse1;

                            MouseAdapter mouse2 = new MouseAdapter() {
                                // @Override
                                @Override
                                public void mouseClicked(java.awt.event.MouseEvent e) {
                                    // TODO Auto-generated method stub
                                    super.mouseClicked(e);
                                    System.out.println("panel6_2");
                                    CostsIncurred costsIncurred = new CostsIncurred(dateFrom, dateTo);
                                }

                            };
                            panel_6_2.addMouseListener(mouse2);
                            mouseLocal2 = mouse2;

                            Connection connect = conn.getConnection();
                            Statement stmt = connect.createStatement();
                            String sql = "select COUNT(`idHD`) from hoadon where `thanhtien` > 0 and `ngaytaodon` between ? and ?";
                            PreparedStatement pr = connect.prepareStatement(sql);
                            pr.setString(1, String.valueOf(dateFrom));
                            pr.setString(2, String.valueOf(dateTo));
                            ResultSet rsCount = pr.executeQuery();
                            while (rsCount.next()) {
                                Dashboard.statistical.setTotalOrder(rsCount.getInt(1));

                            }
                            lblNewLabel_2_4.setText(String.valueOf(statistical.getTotalOrder()));
                            // lblNewLabel_2_4.setText("0");

                            // Statement stmtSum = connect.createStatement();
                            sql = "select SUM(`thanhtien`) from hoadon where `thanhtien` > 0 and `ngaytaodon` between ? and ?";
                            pr = connect.prepareStatement(sql);
                            pr.setString(1, String.valueOf(dateFrom));
                            pr.setString(2, String.valueOf(dateTo));
                            ResultSet rsSum = pr.executeQuery();
                            while (rsSum.next()) {
                                Dashboard.statistical.setTotalCost(rsSum.getFloat(1));
                            }
                            lblNewLabel_2_4_2.setText(String.valueOf(statistical.getTotalCost()));

                            sql = "select COUNT(DISTINCT(masanpham)) from chitiethd inner join hoadon on chitiethd.idHD  = hoadon.idHD where hoadon.ngaytaodon between ? and ?";
                            pr = connect.prepareStatement(sql);
                            pr.setString(1, String.valueOf(dateFrom));
                            pr.setString(2, String.valueOf(dateTo));
                            ResultSet rsCountPro = pr.executeQuery();
                            while (rsCountPro.next()) {
                                Dashboard.statistical.setTotalProduct(rsCountPro.getInt(1));
                            }
                            lblNewLabel_2_4_1.setText(String.valueOf(statistical.getTotalProduct()));

                            Float fakeProfit = 0f;
                            sql = "select `masanpham`, `soluong` from chitiethd inner join hoadon on chitiethd.idHD  = hoadon.idHD where hoadon.ngaytaodon between ? and ?";
                            pr = connect.prepareStatement(sql);
                            pr.setString(1, String.valueOf(dateFrom));
                            pr.setString(2, String.valueOf(dateTo));
                            ResultSet rsPro = pr.executeQuery();
                            while (rsPro.next()) {
                                if (rsPro.getInt(1) > 0 && rsPro.getInt(1) < 31) {
                                    System.out.println("rsPro:" + rsPro.getInt(1));
                                    for (DigitalVideoDisc dvd : OnlineSelectionScrollPane.DVDList) {
                                        if (rsPro.getInt(1) == dvd.getId()) {
                                            fakeProfit += rsPro.getInt(2) * dvd.getFuns();
                                            System.out.println(rsPro.getInt(2));
                                        }
                                    }
                                } else {
                                    if (rsPro.getInt(1) > 30 && rsPro.getInt(1) < 61) {

                                        for (Book book : OnlineSelectionScrollPane.bookList) {
                                            if (rsPro.getInt(1) == book.getId()) {
                                                fakeProfit += rsPro.getInt(2) * book.getFuns();
                                                System.out.println(rsPro.getInt(2));
                                            }
                                        }
                                    } else {
                                        if (rsPro.getInt(1) > 60) {

                                            for (CompactDisc cd : OnlineSelectionScrollPane.CDList) {
                                                if (rsPro.getInt(1) == cd.getId()) {
                                                    fakeProfit += rsPro.getInt(2) * cd.getFuns();
                                                    System.out.println(rsPro.getInt(2));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            System.out.println("fakeProfit" + fakeProfit + " " + "getTotalCost"
                                    + Dashboard.statistical.getTotalCost());
                            Double doubleProfit = (Math
                                    .round((Dashboard.statistical.getTotalCost() - fakeProfit) * 100.0) / 100.0);
                            Dashboard.statistical.setTotalProfit(doubleProfit.floatValue());
                            lblNewLabel_2_4_3.setText(String.valueOf(statistical.getTotalProfit()));
                            // lblNewLabel_2_4_3.setText("0");

                            sql = "select  masanpham, SUM(soluong) from chitiethd  inner join hoadon on chitiethd.idHD  = hoadon.idHD where hoadon.ngaytaodon between ? and ? GROUP BY masanpham ORDER by SUM(soluong) DESC;";
                            pr = connect.prepareStatement(sql);
                            pr.setString(1, String.valueOf(dateFrom));
                            pr.setString(2, String.valueOf(dateTo));
                            ResultSet rsBestseller = pr.executeQuery();
                            int maxQuantity = 0;
                            int maxIdProduct = 0;
                            while (rsBestseller.next()) {
                                if (maxQuantity < rsBestseller.getInt(2)) {
                                    maxQuantity = rsBestseller.getInt(2);
                                    maxIdProduct = rsBestseller.getInt(1);
                                }
                            }
                            lblNewLabel_7_3.setText(String.valueOf(maxQuantity));
                            if (maxIdProduct > 0 && maxIdProduct < 31) {
                                for (DigitalVideoDisc dvd : OnlineSelectionScrollPane.DVDList) {
                                    if (dvd.getId() == maxIdProduct) {
                                        lblNewLabel_7.setText(String.valueOf(dvd.getTitle()));
                                    }
                                }
                            } else {
                                if (maxIdProduct > 30 && maxIdProduct < 61) {
                                    for (Book book : OnlineSelectionScrollPane.bookList) {
                                        if (book.getId() == maxIdProduct) {
                                            lblNewLabel_7.setText(String.valueOf(book.getTitle()));
                                        }
                                    }
                                } else {
                                    if (maxIdProduct > 60) {
                                        for (CompactDisc cd : OnlineSelectionScrollPane.CDList) {
                                            if (cd.getId() == maxIdProduct) {
                                                lblNewLabel_7.setText(String.valueOf(cd.getTitle()));
                                            }
                                        }
                                    }
                                }
                            }

                            sql = "select SUM(`chiphi`) from chiphikhac where `thoigiantao` between ? and ?";
                            pr = connect.prepareStatement(sql);
                            pr.setString(1, String.valueOf(dateFrom));
                            pr.setString(2, String.valueOf(dateTo));
                            ResultSet rsSumcost = pr.executeQuery();
                            while (rsSumcost.next()) {
                                lblNewLabel_7_3_1.setText(String.valueOf(rsSumcost.getFloat(1)));
                            }
                        } catch (Exception ex) {
                            // TODO: handle exception
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Thông tin ngày tháng không hợp lệ !",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Thông tin ngày tháng chưa điền đầy đủ !",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnNewButton.setBounds(316, 216, 95, 21);

        add(btnNewButton);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPanel.setSubContainer(new OnlineSelectionScrollPane());
            }
        });

        btnBuyCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OnlineRegistrationPanel.setLogin(false);
                OnlineLoginPanel.setAdminAccess(false);
                MainPanel.setSubContainer(new OnlineSelectionScrollPane());
            }
        });
    }
}
