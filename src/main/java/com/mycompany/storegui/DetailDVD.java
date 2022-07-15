package com.mycompany.storegui;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

import com.mycompany.connectDB.connectDB;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;

public class DetailDVD {

    connectDB conn;

    public DetailDVD(int id, String title, String imageAddress, Float cost, int quantity, int length, String director,
            String producer) {

        JFrame detailFrame = new JFrame(title);
        detailFrame.setSize(500, 500);
        detailFrame.setBackground(Color.red);

        JPanel panel = new JPanel();
        panel.setSize(500, 500);

        // panel.setBackground(Color.red);
        panel.setVisible(true);

        JLabel temJLabel;
        try {
            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource(imageAddress);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Không thấy ảnh", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("close-icon.png");
            temJLabel = new JLabel(title, new ImageIcon(url), JLabel.CENTER);
        }

        String costText = "Giá: " + cost;
        String quantityText = "Số lượng: " + quantity;
        String lengthText = "Độ dài: " + length;
        String directorText = "Đạo diễn: " + director;
        String producerText = "Nhà sản xuất: " + producer;
        panel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));

        panel.validate();
        panel.repaint();
        Dimension dimension = new Dimension(5, 0);
        detailFrame.getContentPane().add(Box.createRigidArea(dimension));
        detailFrame.getContentPane().add(panel);
        panel.setLayout(null);
        URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource(imageAddress);
        temJLabel = new JLabel(title, new ImageIcon(url), JLabel.CENTER);
        temJLabel.setBounds(157, 107, 144, 70);

        JLabel imageNameLabel1 = temJLabel;
        imageNameLabel1.setVerticalTextPosition(SwingConstants.BOTTOM);
        imageNameLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
        imageNameLabel1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        panel.add(imageNameLabel1);
        JLabel quantityLabel = new JLabel(quantityText);
        quantityLabel.setBounds(160, 202, 99, 13);
        panel.add(quantityLabel);
        JLabel lengthLabel = new JLabel(lengthText);
        lengthLabel.setBounds(160, 225, 99, 13);
        panel.add(lengthLabel);
        JLabel directorLabel = new JLabel(directorText);
        directorLabel.setBounds(160, 248, 127, 13);
        panel.add(directorLabel);
        JLabel lblNhSnXut = new JLabel(producerText);
        lblNhSnXut.setBounds(160, 276, 199, 13);
        panel.add(lblNhSnXut);
        JLabel costLabel = new JLabel(costText);
        costLabel.setBounds(157, 297, 130, 20);
        costLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

        panel.add(costLabel);

        JButton btnBuy = new JButton("Mua");
        btnBuy.setBounds(220, 348, 70, 21);

        btnBuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connection = conn.getConnection();
                    String sql = "UPDATE diaphim set soluong=? WHERE masanpham=?";
                    PreparedStatement stmt = null;
                    int choice = JOptionPane.showConfirmDialog(null, "Thêm vào giỏ hàng?",
                            "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        Statement stmtMax = connection.createStatement();
                        ResultSet rs = stmtMax.executeQuery("select max(idHD) from hoadon");
                        while (rs.next()) {
                            System.out.println(rs.getInt(1));
                            String qttBuy = JOptionPane.showInputDialog(null,
                                    "Quý khách muốn mua bao nhiêu ?");
                            int qtt = Integer.parseInt(qttBuy);
                            if (qtt > quantity) {
                                JOptionPane.showMessageDialog(null, "Rất tiếc ! Số lượng mua vượt quá giới hạn",
                                        "ERROR", JOptionPane.ERROR_MESSAGE);
                            } else {
                                String toHD = "insert into `chitiethd` (`idHD`,`masanpham`,`soluong`,`giatri`) values (?,?,?,?)";
                                stmt = connection.prepareStatement(toHD);

                                stmt.setInt(1, rs.getInt(1));
                                stmt.setInt(2, id);
                                stmt.setInt(3, qtt);
                                stmt.setFloat(4, qtt * cost);
                                stmt.executeUpdate();

                                stmt = connection.prepareStatement(sql);
                                stmt.setInt(1, quantity - qtt);
                                stmt.setInt(2, id);
                                stmt.executeUpdate();
                                stmt.close();

                                // Item tempItem = new Item();
                                // tempItem.setQuantity(quantity - qtt);

                                OnlineSelectionScrollPane.CART.add(new Cart(title, qtt, cost, qtt * cost, 0.7f));
                                for (Cart cart : OnlineSelectionScrollPane.CART) {
                                    System.out.println(cart.getNameProduct());
                                }

                                MainPanel.setSubContainer(new OnlineSelectionScrollPane());

                                System.out.println("datialcd");
                            }
                        }
                    }
                } catch (Exception ex) {
                    // TODO: handle exception
                }
            }
        });
        panel.add(btnBuy);

        detailFrame.setVisible(true);

    }
}
