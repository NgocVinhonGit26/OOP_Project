package com.mycompany.storegui;

import javax.swing.*;

import com.mycompany.connectDB.connectDB;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

public class addCategoryCD extends JScrollPane {

    private static connectDB conn;

    public static void addCategory(String categoryName, String imageAddress,
            HashMap<JPanel, JLabel> UNDER_EDITING_PANELS, HashMap<String, JPanel> CATEGORIES) {
        if (!CATEGORIES.containsKey(categoryName)) { // if category name is not already used
            final JPanel panel = new JPanel();
            BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
            panel.setLayout(layout);

            JLabel tempLabel;
            try {
                URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource(imageAddress);
                tempLabel = new JLabel(categoryName, new ImageIcon(url), SwingConstants.LEADING);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Không thấy ảnh", "ERROR", JOptionPane.ERROR_MESSAGE);
                URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("close-icon.png");
                tempLabel = new JLabel(categoryName, new ImageIcon(url), JLabel.CENTER);
            }

            JLabel label = tempLabel; // will use it later in anonymous class
            label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

            panel.addMouseListener(new MouseAdapter() {
                private boolean show = true; // show extra buttons in admin access
                JPanel subPanel = new JPanel();

                @Override
                public void mouseClicked(MouseEvent e) {

                    if (OnlineLoginPanel.isAdminAccess()) {
                        if (show) {
                            show = false; // so if clicked again hide these
                            UNDER_EDITING_PANELS.put(panel, label);
                            JButton btnAddProduct = new JButton("Thêm sản phẩm mới");
                            JButton btnEditCategory = new JButton("Cập nhật Danh mục");
                            JButton btnDelete = new JButton("Xóa Danh mục");

                            subPanel.setBackground(Color.WHITE);

                            subPanel.add(label);
                            Dimension dimension = new Dimension(5, 0);
                            subPanel.add(Box.createRigidArea(dimension));
                            subPanel.add(btnAddProduct);
                            subPanel.add(Box.createRigidArea(dimension));
                            subPanel.add(btnEditCategory);
                            subPanel.add(Box.createRigidArea(dimension));
                            subPanel.add(btnDelete);

                            panel.remove(label);
                            panel.add(subPanel, 0);
                            panel.revalidate();
                            panel.repaint();

                            btnAddProduct.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        // Connection connect = conn.getConnection();
                                        // Statement stmt = connect.createStatement();
                                        // ResultSet rs = stmt.executeQuery("select * from user");
                                        // dang do
                                        String productName = JOptionPane.showInputDialog(null, "Nhập tên sản phẩm");
                                        if (!productName.isBlank()) {
                                            String producerName = JOptionPane.showInputDialog(null,
                                                    "Nhập tên nhà sản xuất");
                                            if (!producerName.isBlank()) {
                                                String directorName = JOptionPane.showInputDialog(null,
                                                        "Nhập tên nghệ sĩ");
                                                if (!directorName.isBlank()) {
                                                    String lengthText = JOptionPane.showInputDialog(null,
                                                            "Nhập thời lượng đĩa nhạc");
                                                    if (lengthText.isBlank()) {
                                                        JOptionPane.showMessageDialog(null, "Thời lượng không hợp lệ",
                                                                "ERROR", JOptionPane.ERROR_MESSAGE);
                                                    }
                                                    for (char c : lengthText.toCharArray()) { // ktra co phai la so hay
                                                                                              // khong
                                                        if (!Character.isDigit(c)) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "Thời lượng không hợp lệ",
                                                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                                                            break;
                                                        }
                                                    }
                                                    int length = Integer.parseInt(lengthText);
                                                    String category = JOptionPane.showInputDialog(null,
                                                            "Nhập thể loại nhạc");
                                                    if (!category.isBlank()) {
                                                        String quantityText = JOptionPane.showInputDialog(null,
                                                                "Nhập số lượng");
                                                        if (quantityText.isBlank()) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "Số lượng không hợp lệ",
                                                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                                                        }
                                                        for (char c : quantityText.toCharArray()) {
                                                            if ((!Character.isDigit(c))) {
                                                                JOptionPane.showMessageDialog(null,
                                                                        "Số lượng không hợp lệ", "ERROR",
                                                                        JOptionPane.ERROR_MESSAGE);
                                                                break;
                                                            }
                                                        }
                                                        int quantity = Integer.parseInt(quantityText);

                                                        String imageAddress = JOptionPane.showInputDialog(null,
                                                                "Nhập địa chỉ hình ảnh");
                                                        if (!imageAddress.isBlank()) {
                                                            String fundsText = JOptionPane.showInputDialog(null,
                                                                    "Nhập giá mua vào");
                                                            if (fundsText.isBlank()) {
                                                                JOptionPane.showMessageDialog(null, "Giá hợp lệ",
                                                                        "ERROR", JOptionPane.ERROR_MESSAGE);
                                                            }
                                                            for (char c : fundsText.toCharArray()) {
                                                                if (!Character.isDigit(c)) {
                                                                    JOptionPane.showMessageDialog(null, "Giá hợp lệ",
                                                                            "ERROR", JOptionPane.ERROR_MESSAGE);
                                                                    break;
                                                                }
                                                            }
                                                            Float funds = Float.parseFloat(fundsText);

                                                            String costText = JOptionPane.showInputDialog(null,
                                                                    "Nhập giá bán ra");
                                                            if (costText.isBlank()) {
                                                                JOptionPane.showMessageDialog(null, "Giá không hợp lệ",
                                                                        "ERROR", JOptionPane.ERROR_MESSAGE);
                                                            }
                                                            for (char c : costText.toCharArray()) {
                                                                if (!Character.isDigit(c)) {
                                                                    JOptionPane.showMessageDialog(null,
                                                                            "Giá không hợp lệ",
                                                                            "ERROR", JOptionPane.ERROR_MESSAGE);
                                                                    break;
                                                                }
                                                            }
                                                            Float cost = Float.parseFloat(costText);
                                                            List<Track> trackList = new ArrayList<Track>();
                                                            try {
                                                                Connection connection = conn.getConnection();
                                                                String sql = "insert into dianhac (`tensanpham`,`nhasanxuat`,`nghesi`,`thoiluong`,`theloai`, `soluong`,`giamua`,`giaban`,`image`) values(?,?,?,?,?,?,?,?,?)";
                                                                PreparedStatement ps = connection.prepareStatement(sql);

                                                                ps.setString(1, productName);
                                                                ps.setString(2, producerName);
                                                                ps.setString(3, directorName);
                                                                ps.setInt(4, length);
                                                                ps.setString(5, category);
                                                                ps.setInt(6, quantity);
                                                                ps.setFloat(7, funds);
                                                                ps.setFloat(8, cost);
                                                                ps.setString(9, imageAddress);
                                                                ps.executeUpdate();
                                                                ps.close();

                                                                sql = "select `masanpham` from dianhac where `tensanpham` = ?";
                                                                ps = connection.prepareStatement(sql);
                                                                ps.setString(1, productName);
                                                                ResultSet rs = ps.executeQuery();

                                                                while (rs.next()) {
                                                                    int id = rs.getInt(1);
                                                                    System.out.println("check id dia nhac: " + id);
                                                                    JPanel subPanel = (JPanel) panel.getComponent(1);
                                                                    JPanel subSubPanel = OnlineSelectionScrollPane
                                                                            .addProductCD(id, productName,
                                                                                    imageAddress, producerName, length,
                                                                                    cost, quantity, directorName,
                                                                                    trackList);
                                                                    subPanel.add(subSubPanel);
                                                                    subPanel.validate();
                                                                    subPanel.repaint();
                                                                }

                                                            } catch (ArrayIndexOutOfBoundsException ex) {
                                                                Connection connection = conn.getConnection();
                                                                String sql = "insert into dianhac (`tensanpham`,`nhasanxuat`,`nghesi`,`thoiluong`,`theloai`, `soluong`,`giamua`,`giaban`,`image`) values(?,?,?,?,?,?,?,?,?)";
                                                                PreparedStatement ps = connection.prepareStatement(sql);

                                                                ps.setString(1, productName);
                                                                ps.setString(2, producerName);
                                                                ps.setString(3, directorName);
                                                                ps.setInt(4, length);
                                                                ps.setString(5, category);
                                                                ps.setInt(6, quantity);
                                                                ps.setFloat(7, funds);
                                                                ps.setFloat(8, cost);
                                                                ps.setString(9, imageAddress);
                                                                ps.executeUpdate();
                                                                ps.close();

                                                                sql = "select `masanpham` from dianhac where `tensanpham` = ?";
                                                                ps = connection.prepareStatement(sql);
                                                                ps.setString(1, productName);
                                                                ResultSet rs = ps.executeQuery();
                                                                while (rs.next()) {
                                                                    int id = rs.getInt(1);
                                                                    JPanel subPanel = new JPanel();
                                                                    JPanel subSubPanel = OnlineSelectionScrollPane
                                                                            .addProductCD(id, productName,
                                                                                    imageAddress, producerName, length,
                                                                                    cost, quantity, directorName,
                                                                                    trackList);
                                                                    subPanel.add(subSubPanel);
                                                                    subPanel.validate();
                                                                    subPanel.repaint();
                                                                    panel.add(subPanel);
                                                                    panel.validate();
                                                                    panel.repaint();
                                                                }
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "Ảnh trống", "ERROR",
                                                                    JOptionPane.ERROR_MESSAGE);
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Chưa nhập thể loại phim", "ERROR",
                                                                JOptionPane.ERROR_MESSAGE);
                                                    }
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Chưa nhập tên đạo diễn",
                                                            "ERROR", JOptionPane.ERROR_MESSAGE);
                                                }
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Chưa nhập tên nhà sản xuất",
                                                        "ERROR",
                                                        JOptionPane.ERROR_MESSAGE);
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Chưa nhập tên phim", "ERROR",
                                                    JOptionPane.ERROR_MESSAGE);
                                        }

                                        if (productName == null || imageAddress == null)
                                            throw new NullPointerException();
                                        // if(productName == null){
                                        // JOptionPane.showMessageDialog(null, "Tên không hợp lệ", "ERROR",
                                        // JOptionPane.ERROR_MESSAGE);
                                        // }
                                        // System.out.println("1" + productName + "1");

                                    } catch (Exception ex) {

                                    }
                                    // catch (NumberFormatException ex) {
                                    // JOptionPane.showMessageDialog(null, "Chi phí không hợp lệ", "ERROR",
                                    // JOptionPane.ERROR_MESSAGE);
                                    // } catch (NullPointerException ex) {
                                    // JOptionPane.showMessageDialog(null, "Thao tác đã bị hủy bỏ");
                                    // }

                                }
                            });

                            btnEditCategory.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    int choice = JOptionPane.showConfirmDialog(null, "Cập nhật Danh mục ?");
                                    if (choice == JOptionPane.YES_OPTION) {
                                        try {
                                            String name = JOptionPane.showInputDialog(null, "Nhập tên Danh mục");
                                            if (name == null)
                                                throw new NullPointerException();

                                            String imageAddress = JOptionPane.showInputDialog(null,
                                                    "Nhập địa chỉ hình ảnh của Danh mục");
                                            if (imageAddress == null)
                                                throw new NullPointerException();

                                            CATEGORIES.remove(label.getText());
                                            label.setText(name);
                                            CATEGORIES.put(name, panel);

                                            URL url = OnlineSelectionScrollPane.class.getClassLoader()
                                                    .getResource(imageAddress);
                                            label.setIcon(new ImageIcon(ImageIO.read(url)));
                                        } catch (IOException ex) {
                                            JOptionPane.showMessageDialog(null, "Không thấy ảnh", "ERROR",
                                                    JOptionPane.ERROR_MESSAGE);
                                        } catch (NullPointerException ex) {
                                            JOptionPane.showMessageDialog(null, "Operation Cancelled");
                                        } catch (Exception ex) {
                                            JOptionPane.showMessageDialog(null, "Không thấy ảnh", "ERROR",
                                                    JOptionPane.ERROR_MESSAGE);
                                        }
                                    }

                                }
                            });

                            btnDelete.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    int choice = JOptionPane.showConfirmDialog(null, "Xóa vĩnh viễn?",
                                            "Confirmation", JOptionPane.YES_NO_OPTION);
                                    if (choice == JOptionPane.YES_OPTION) {
                                        CATEGORIES.remove(label.getText(), panel);
                                        JPanel parentPanel = (JPanel) panel.getParent();
                                        parentPanel.remove(panel);
                                        parentPanel.validate();
                                        parentPanel.repaint();
                                    }
                                }
                            });

                        } else {
                            subPanel.removeAll(); // remove everything(those extra buttons in admin access) from extra
                                                  // panel
                            panel.remove(subPanel); // remove that panel too
                            panel.setBackground(Color.WHITE);
                            panel.add(label, 0);
                            panel.revalidate();
                            panel.repaint();
                            show = true; // if clicked again show those buttons
                            UNDER_EDITING_PANELS.remove(panel);
                        }

                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {

                    if (OnlineLoginPanel.isAdminAccess())
                        panel.setToolTipText("Bấm vào để chỉnh sửa");

                    else
                        panel.setToolTipText(null);
                }

            });

            if (!OnlineLoginPanel.isAdminAccess() || !CATEGORIES.containsKey(categoryName)) {
                panel.setBackground(Color.WHITE);
                panel.add(label);
            }

            CATEGORIES.put(categoryName, panel); // put in categories(hash map)
        } else
            JOptionPane.showMessageDialog(null, "Danh mục này đã có", "ERROR", JOptionPane.ERROR_MESSAGE);
    }
}
