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
import java.util.List;

public class DetailBook {
    connectDB conn;

    public DetailBook(int id, String title, String imageAddress, Float cost, int quantity, List<String> authors,
            String publisher, String category) {

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
        String authorText = "Tác giả:";
        String nsxTest = "Nhà xuất bản: " + publisher;
        String theloaiText = "Thể loại: " + category;
        for (String author : authors) {
            authorText += (" " + author);
        }
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
        quantityLabel.setBounds(157, 283, 99, 13);
        panel.add(quantityLabel);

        JLabel authorLabel = new JLabel(authorText);
        authorLabel.setBounds(157, 202, 265, 13);
        panel.add(authorLabel);

        JLabel lblNhSnXut = new JLabel(nsxTest);
        lblNhSnXut.setBounds(157, 225, 190, 13);
        panel.add(lblNhSnXut);

        JLabel lblThLoi = new JLabel(theloaiText);
        lblThLoi.setBounds(157, 248, 161, 13);
        panel.add(lblThLoi);

        JLabel costLabel = new JLabel(costText);
        costLabel.setBounds(157, 306, 130, 20);
        costLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

        panel.add(costLabel);

        JButton btnBuy = new JButton("Mua");
        btnBuy.setBounds(220, 348, 70, 21);

        btnBuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connection = conn.getConnection();
                    String sql = "UPDATE sach set soluong=? WHERE masanpham=?";
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
