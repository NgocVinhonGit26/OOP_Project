package com.mycompany.storegui;

import javax.swing.*;

import com.mycompany.connectDB.connectDB;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public final class OnlineSelectionScrollPane extends JScrollPane {
    // category and products panels will go on it
    private static final JPanel MAIN_PANEL = new JPanel(); // static because scroll pane "add" method not working and so
                                                           // to pass it to its constructor
    private static final HashMap<String, JPanel> CATEGORIES = new HashMap<>(); // keep record of categories
    public static final ArrayList<Cart> CART = new ArrayList<>(); // keep record of items included in cart
    public static final ArrayList<DigitalVideoDisc> DVDList = new ArrayList<>();
    public static final ArrayList<Book> bookList = new ArrayList<>();
    public static final ArrayList<CompactDisc> CDList = new ArrayList<>();
    public static final ArrayList<Track> trackList = new ArrayList<>();
    private static final HashMap<JPanel, JLabel> UNDER_EDITING_PANELS = new HashMap<>();
    private static connectDB conn;

    static {

        // make 3 categories
        addCategoryDVD.addCategory("DVD", "dvd.png", UNDER_EDITING_PANELS, CATEGORIES);
        addCategoryBook.addCategory("Book", "book.png", UNDER_EDITING_PANELS, CATEGORIES);
        addCategoryCD.addCategory("CD", "cd.png", UNDER_EDITING_PANELS, CATEGORIES);
        // addCategory("DVD", "dvd.png");
        // addCategory("Book", "book.png");
        // addCategory("CD", "cd.png");

        try {
            Connection connect = conn.getConnection();
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("select * from diaphim");

            while (rs.next()) {
                DigitalVideoDisc dvd = new DigitalVideoDisc(rs.getInt(1), rs.getString(2),
                        rs.getString(6), rs.getInt(5), rs.getString(4), rs.getFloat(8), rs.getFloat(9), rs.getInt(7),
                        rs.getString(10), rs.getString(3));
                Collections.addAll(DVDList, dvd);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        try {
            Connection connect = conn.getConnection();
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("select * from sach");

            while (rs.next()) {
                Book book = new Book(rs.getInt(1), rs.getString(2), rs.getString(5), rs.getFloat(7),
                        rs.getFloat(8), rs.getString(4), rs.getInt(6), rs.getString(9), rs.getString(3));
                Collections.addAll(bookList, book);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        Track track1 = new Track("Hello", 40);
        Track track2 = new Track("What do you mean", 50);
        Track track3 = new Track("Day by day", 50);
        Track track4 = new Track("Arigatou", 10);
        Collections.addAll(trackList, track1, track2, track3, track4);
        try {
            Connection connect = conn.getConnection();
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("select * from dianhac");

            while (rs.next()) {

                CompactDisc cd = new CompactDisc(rs.getInt(1), rs.getString(2),
                        rs.getString(6), rs.getString(4), rs.getInt(5), rs.getFloat(8), rs.getFloat(9), rs.getInt(7),
                        rs.getString(10), rs.getString(3), trackList);
                Collections.addAll(CDList, cd);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        // add products to first category
        JPanel panel = new JPanel();
        for (DigitalVideoDisc dvd : DVDList) {
            panel.add(addProductDVD(dvd.getId(), dvd.getTitle(), dvd.getImage(), dvd.getCost(), dvd.getQuantity(),
                    dvd.length, dvd.director, dvd.getProducer()));
        }
        CATEGORIES.get("DVD").add(panel);

        // add products to second category
        panel = new JPanel();
        for (Book book : bookList) {
            panel.add(addProductBook(book.getId(), book.getTitle(), book.getImage(), book.getCost(),
                    book.getQuantity(), book.getAuthors(), book.getPublisher(), book.getCategory()));
        }
        CATEGORIES.get("Book").add(panel);

        // add products to third category
        panel = new JPanel();
        for (CompactDisc cd : CDList) {
            panel.add(addProductCD(cd.getId(), cd.getTitle(), cd.getImage(), cd.getArtist(), cd.length,
                    cd.getCost(), cd.getQuantity(), cd.getDirector(), cd.getTrackList()));
        }
        CATEGORIES.get("CD").add(panel);

    }

    public OnlineSelectionScrollPane() {
        super(MAIN_PANEL);

        // remove any previous component (dealing with static nonsense)
        MAIN_PANEL.removeAll();
        MAIN_PANEL.validate();
        MAIN_PANEL.repaint();

        BoxLayout layout = new BoxLayout(MAIN_PANEL, BoxLayout.Y_AXIS);
        MAIN_PANEL.setLayout(layout);

        JButton btnBack = new JButton("Tr??? l???i");
        JButton btnShowCart = new JButton("Hi???n th??? gi??? h??ng");
        JButton btnBuyCart = new JButton("Mua s???n ph???m trong gi??? h??ng");

        JPanel subPanel = new JPanel();
        subPanel.add(btnBack);
        subPanel.add(Box.createRigidArea(new Dimension(20, 0))); // horizontal space
        subPanel.add(btnShowCart);
        subPanel.add(Box.createRigidArea(new Dimension(20, 0))); // horizontal space
        subPanel.add(btnBuyCart);

        if (OnlineLoginPanel.isAdminAccess()) { // if have admin access give these extra things
            JButton btnAddCategory = new JButton("Xem th???ng k??");
            subPanel.add(Box.createRigidArea(new Dimension(30, 0)));
            subPanel.add(btnAddCategory);

            btnAddCategory.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    MainPanel.setSubContainer(new Dashboard());
                }

            });
        }
        // if logined give log out button
        if (OnlineRegistrationPanel.isLogin()) {
            JButton logOut = new JButton("????ng xu???t");
            subPanel.add(Box.createRigidArea(new Dimension(50, 0)));
            subPanel.add(logOut);

            logOut.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    OnlineRegistrationPanel.setLogin(false);
                    OnlineLoginPanel.setAdminAccess(false);

                    for (JLabel label : UNDER_EDITING_PANELS.values()) {
                        JPanel panel = CATEGORIES.get(label.getText());
                        panel.remove(0);
                        panel.add(label, 0);
                        panel.revalidate();
                        panel.repaint();
                    }
                    MainPanel.setSubContainer(new OnlineSelectionScrollPane());
                }

            });

        }

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPanel.setSubContainer(new MainMenuPanel());
            }
        });

        btnShowCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPanel.setSubContainer(new ShowOnlineCartPanel());
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

        MAIN_PANEL.add(subPanel);

        // add categories to main panel
        for (JPanel panel : CATEGORIES.values())
            MAIN_PANEL.add(panel);

    }

    public static void addCategory(String categoryName, String imageAddress) {
        if (!CATEGORIES.containsKey(categoryName)) { // if category name is not already used
            final JPanel panel = new JPanel();
            BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
            panel.setLayout(layout);

            JLabel tempLabel;
            try {
                URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource(imageAddress);
                tempLabel = new JLabel(categoryName, new ImageIcon(url), SwingConstants.LEADING);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Kh??ng th???y ???nh", "ERROR", JOptionPane.ERROR_MESSAGE);
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
                            JButton btnAddProduct = new JButton("Th??m s???n ph???m m???i");
                            JButton btnEditCategory = new JButton("C???p nh???t Danh m???c");
                            JButton btnDelete = new JButton("X??a Danh m???c");

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
                                        String productName = JOptionPane.showInputDialog(null, "Nh???p t??n s???n ph???m");
                                        if (!productName.isBlank()) {
                                            String producerName = JOptionPane.showInputDialog(null,
                                                    "Nh???p t??n nh?? s???n xu???t");
                                            if (!producerName.isBlank()) {
                                                String directorName = JOptionPane.showInputDialog(null,
                                                        "Nh???p t??n ?????o di???n");
                                                if (!directorName.isBlank()) {
                                                    String lengthText = JOptionPane.showInputDialog(null,
                                                            "Nh???p th???i l?????ng phim");
                                                    if (lengthText.isBlank()) {
                                                        JOptionPane.showMessageDialog(null, "Th???i l?????ng kh??ng h???p l???",
                                                                "ERROR", JOptionPane.ERROR_MESSAGE);
                                                    }
                                                    for (char c : lengthText.toCharArray()) { // ktra co phai la so hay
                                                                                              // khong
                                                        if (!Character.isDigit(c)) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "Th???i l?????ng kh??ng h???p l???",
                                                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                                                            break;
                                                        }
                                                    }
                                                    int length = Integer.parseInt(lengthText);
                                                    String category = JOptionPane.showInputDialog(null,
                                                            "Nh???p th??? lo???i phim");
                                                    if (!category.isBlank()) {
                                                        String quantityText = JOptionPane.showInputDialog(null,
                                                                "Nh???p s??? l?????ng");
                                                        if (quantityText.isBlank()) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "S??? l?????ng kh??ng h???p l???",
                                                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                                                        }
                                                        for (char c : quantityText.toCharArray()) {
                                                            if ((!Character.isDigit(c))) {
                                                                JOptionPane.showMessageDialog(null,
                                                                        "S??? l?????ng kh??ng h???p l???", "ERROR",
                                                                        JOptionPane.ERROR_MESSAGE);
                                                                break;
                                                            }
                                                        }
                                                        int quantity = Integer.parseInt(quantityText);

                                                        String imageAddress = JOptionPane.showInputDialog(null,
                                                                "Nh???p ?????a ch??? h??nh ???nh");
                                                        if (!imageAddress.isBlank()) {
                                                            String fundsText = JOptionPane.showInputDialog(null,
                                                                    "Nh???p gi?? mua v??o");
                                                            if (fundsText.isBlank()) {
                                                                JOptionPane.showMessageDialog(null, "Gi?? h???p l???",
                                                                        "ERROR", JOptionPane.ERROR_MESSAGE);
                                                            }
                                                            for (char c : fundsText.toCharArray()) {
                                                                if (!Character.isDigit(c)) {
                                                                    JOptionPane.showMessageDialog(null, "Gi?? h???p l???",
                                                                            "ERROR", JOptionPane.ERROR_MESSAGE);
                                                                    break;
                                                                }
                                                            }
                                                            Float funds = Float.parseFloat(fundsText);

                                                            String costText = JOptionPane.showInputDialog(null,
                                                                    "Nh???p gi?? b??n ra");
                                                            if (costText.isBlank()) {
                                                                JOptionPane.showMessageDialog(null, "Gi?? h???p l???",
                                                                        "ERROR", JOptionPane.ERROR_MESSAGE);
                                                            }
                                                            for (char c : costText.toCharArray()) {
                                                                if (!Character.isDigit(c)) {
                                                                    JOptionPane.showMessageDialog(null, "Gi?? h???p l???",
                                                                            "ERROR", JOptionPane.ERROR_MESSAGE);
                                                                    break;
                                                                }
                                                            }
                                                            Float cost = Float.parseFloat(costText);

                                                            try {

                                                                JPanel subPanel = (JPanel) panel.getComponent(1);
                                                                JPanel subSubPanel = addProduct(productName,
                                                                        imageAddress, cost, quantity);

                                                                Connection connection = conn.getConnection();
                                                                String sql = "insert into diaphim (`tensanpham`,`nhasanxuat`,`daodien`,`thoiluong`,`theloai`, `soluong`,`giamua`,`giaban`,`image`) values(?,?,?,?,?,?,?,?,?)";
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

                                                                subPanel.add(subSubPanel);
                                                                subPanel.validate();
                                                                subPanel.repaint();
                                                            } catch (ArrayIndexOutOfBoundsException ex) {
                                                                JPanel subPanel = new JPanel();
                                                                JPanel subSubPanel = addProduct(productName,
                                                                        imageAddress, cost, quantity);
                                                                subPanel.add(subSubPanel);
                                                                subPanel.validate();
                                                                subPanel.repaint();
                                                                panel.add(subPanel);
                                                                panel.validate();
                                                                panel.repaint();
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "???nh tr???ng", "ERROR",
                                                                    JOptionPane.ERROR_MESSAGE);
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Ch??a nh???p th??? lo???i phim", "ERROR",
                                                                JOptionPane.ERROR_MESSAGE);
                                                    }
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Ch??a nh???p t??n ?????o di???n",
                                                            "ERROR", JOptionPane.ERROR_MESSAGE);
                                                }
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Ch??a nh???p t??n nh?? s???n xu???t",
                                                        "ERROR",
                                                        JOptionPane.ERROR_MESSAGE);
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Ch??a nh???p t??n phim", "ERROR",
                                                    JOptionPane.ERROR_MESSAGE);
                                        }

                                        if (productName == null || imageAddress == null)
                                            throw new NullPointerException();
                                        // if(productName == null){
                                        // JOptionPane.showMessageDialog(null, "T??n kh??ng h???p l???", "ERROR",
                                        // JOptionPane.ERROR_MESSAGE);
                                        // }
                                        // System.out.println("1" + productName + "1");

                                    } catch (Exception ex) {

                                    }
                                    // catch (NumberFormatException ex) {
                                    // JOptionPane.showMessageDialog(null, "Chi ph?? kh??ng h???p l???", "ERROR",
                                    // JOptionPane.ERROR_MESSAGE);
                                    // } catch (NullPointerException ex) {
                                    // JOptionPane.showMessageDialog(null, "Thao t??c ???? b??? h???y b???");
                                    // }

                                }
                            });

                            btnEditCategory.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    int choice = JOptionPane.showConfirmDialog(null, "C???p nh???t Danh m???c ?");
                                    if (choice == JOptionPane.YES_OPTION) {
                                        try {
                                            String name = JOptionPane.showInputDialog(null, "Nh???p t??n Danh m???c");
                                            if (name == null)
                                                throw new NullPointerException();

                                            String imageAddress = JOptionPane.showInputDialog(null,
                                                    "Nh???p ?????a ch??? h??nh ???nh c???a Danh m???c");
                                            if (imageAddress == null)
                                                throw new NullPointerException();

                                            CATEGORIES.remove(label.getText());
                                            label.setText(name);
                                            CATEGORIES.put(name, panel);

                                            URL url = OnlineSelectionScrollPane.class.getClassLoader()
                                                    .getResource(imageAddress);
                                            label.setIcon(new ImageIcon(ImageIO.read(url)));
                                        } catch (IOException ex) {
                                            JOptionPane.showMessageDialog(null, "Kh??ng th???y ???nh", "ERROR",
                                                    JOptionPane.ERROR_MESSAGE);
                                        } catch (NullPointerException ex) {
                                            JOptionPane.showMessageDialog(null, "Operation Cancelled");
                                        } catch (Exception ex) {
                                            JOptionPane.showMessageDialog(null, "Kh??ng th???y ???nh", "ERROR",
                                                    JOptionPane.ERROR_MESSAGE);
                                        }
                                    }

                                }
                            });

                            btnDelete.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    int choice = JOptionPane.showConfirmDialog(null, "X??a v??nh vi???n?",
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
                        panel.setToolTipText("B???m v??o ????? ch???nh s???a");

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
            JOptionPane.showMessageDialog(null, "Danh m???c n??y ???? c??", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    static JPanel addProduct(String productName, String imageAddress, Float cost, int quantity) {
        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        JLabel tempLabel;
        try {
            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource(imageAddress);
            tempLabel = new JLabel(productName, new ImageIcon(ImageIO.read(url)), JLabel.CENTER);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Kh??ng th???y ???nh", "ERROR", JOptionPane.ERROR_MESSAGE);
            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("close-icon.png");
            tempLabel = new JLabel(productName, new ImageIcon(url), JLabel.CENTER);
        }

        JLabel imageNameLabel = tempLabel;
        imageNameLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        imageNameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        imageNameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        panel.add(imageNameLabel);

        String costText = "Gi??: " + cost;
        JLabel costLabel = new JLabel(costText);
        costLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

        String quantityText = "S??? l?????ng: " + quantity;
        JLabel quantityLabel = new JLabel(quantityText);

        panel.add(costLabel);
        panel.add(quantityLabel);
        panel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
        panel.validate();
        panel.repaint();
        boolean status = false;
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (OnlineLoginPanel.isAdminAccess()) {
                    if (!e.isMetaDown()) {// if not right click
                        int choice = JOptionPane.showConfirmDialog(null, "X??a ?", "Confirmation",
                                JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            JPanel parentPanel = (JPanel) panel.getParent();
                            parentPanel.remove(panel);
                            parentPanel.validate();
                            parentPanel.repaint();
                        }
                    }

                    else { // if right click
                        int choice = JOptionPane.showConfirmDialog(null, "C???p nh???t s???n ph???m ?", "Confirmation",
                                JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            try {
                                String productName = JOptionPane.showInputDialog(null, "Nh???p t??n s???n ph???m");
                                if (productName == null)
                                    throw new NullPointerException();

                                String imageAddress = JOptionPane.showInputDialog(null, "Nh???p ?????a ch??? ???nh minh h???a");
                                if (imageAddress == null)
                                    throw new NullPointerException();

                                float cost = Float.parseFloat((String) JOptionPane.showInputDialog(null, "Nh???p gi??"));
                                int quantity = Integer.parseInt((String) JOptionPane.showInputDialog(null,
                                        "Enter quantity(it will be displayed as cost per quantity)"));
                                if (productName == null || imageAddress == null)
                                    throw new NullPointerException();

                                URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource(imageAddress);
                                File file = new File(imageAddress);
                                imageNameLabel.setText(productName);
                                imageNameLabel.setIcon(new ImageIcon(ImageIO.read(url)));

                                costLabel.setText("" + cost);
                                quantityLabel.setText("" + quantity);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Chi ph?? kh??ng h???p l???", "ERROR",
                                        JOptionPane.ERROR_MESSAGE);
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, "Kh??ng th???y ???nh", "ERROR",
                                        JOptionPane.ERROR_MESSAGE);
                            } catch (NullPointerException ex) {
                                JOptionPane.showMessageDialog(null, "Operation Cancelled");
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Kh??ng th???y ???nh", "ERROR",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }

                    }

                } else { // if not admin access

                    // DetailDVD detail = new DetailDVD(1, productName, imageAddress, cost,
                    // quantity, 1, "vinh", "vinh");
                    // test detail = new test(productName, imageAddress, cost, quantity);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(Color.pink);
                panel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));

                if (OnlineLoginPanel.isAdminAccess())
                    panel.setToolTipText("Nh???p chu???t tr??i ????? x??a. Nh???p chu???t ph???i ????? ch???nh s???a");

                else
                    panel.setToolTipText("B???m v??o ????? mua");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(null);
                panel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            }

        });
        return panel;
    }

    protected static JPanel addProductDVD(int id, String title, String imageAddress, Float cost, int quantity,
            int length, String director, String producer) {
        JPanel panel = new JPanel();
        if (quantity != 0) {

            BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
            panel.setLayout(layout);
            JLabel tempLabel;
            try {
                URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource(imageAddress);
                tempLabel = new JLabel(title, new ImageIcon(ImageIO.read(url)), JLabel.CENTER);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Kh??ng th???y ???nh", "ERROR", JOptionPane.ERROR_MESSAGE);
                URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("close-icon.png");
                tempLabel = new JLabel(title, new ImageIcon(url), JLabel.CENTER);
            }

            JLabel imageNameLabel = tempLabel;
            imageNameLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
            imageNameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            imageNameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            panel.add(imageNameLabel);

            String costText = "Gi??: " + cost;
            JLabel costLabel = new JLabel(costText);
            costLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

            String quantityText = "S??? l?????ng: " + quantity;
            JLabel quantityLabel = new JLabel(quantityText);

            String lengthText = "????? d??i: " + length;
            JLabel lengthLabel = new JLabel(lengthText);

            panel.add(costLabel);
            panel.add(quantityLabel);
            panel.add(lengthLabel);
            panel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            panel.validate();
            panel.repaint();

            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (OnlineLoginPanel.isAdminAccess()) {
                        if (!e.isMetaDown()) {// if not right click
                            int choice = JOptionPane.showConfirmDialog(null, "X??a ?", "Confirmation",
                                    JOptionPane.YES_NO_OPTION);
                            if (choice == JOptionPane.YES_OPTION) {
                                try {
                                    Connection connection = conn.getConnection();
                                    String sql = "delete from diaphim where `masanpham` = ?";
                                    PreparedStatement ps = connection.prepareStatement(sql);
                                    ps.setInt(1, id);
                                    ps.executeUpdate();
                                    ps.close();
                                    JPanel parentPanel = (JPanel) panel.getParent();
                                    parentPanel.remove(panel);
                                    parentPanel.validate();
                                    parentPanel.repaint();
                                } catch (Exception ex) {
                                    // TODO: handle exception
                                }

                            }
                        }

                        else { // if right click
                            int choice = JOptionPane.showConfirmDialog(null, "C???p nh???t s???n ph???m ?", "Confirmation",
                                    JOptionPane.YES_NO_OPTION);
                            if (choice == JOptionPane.YES_OPTION) {
                                try {
                                    Connection connection = conn.getConnection();
                                    String sql = "select * from diaphim where `masanpham` = ?";
                                    PreparedStatement ps = connection.prepareStatement(sql);

                                    ps.setInt(1, id);
                                    ResultSet rs = ps.executeQuery();

                                    while (rs.next()) {

                                        // dang do
                                        String productName = JOptionPane.showInputDialog("Nh???p t??n s???n ph???m",
                                                rs.getString(2));
                                        if (!productName.isBlank()) {
                                            String producerName = JOptionPane.showInputDialog("Nh???p t??n nh?? s???n xu???t",
                                                    rs.getString(3));
                                            if (!producerName.isBlank()) {
                                                String directorName = JOptionPane.showInputDialog("Nh???p t??n ?????o di???n",
                                                        rs.getString(4));
                                                if (!directorName.isBlank()) {
                                                    String lengthText = JOptionPane
                                                            .showInputDialog("Nh???p th???i l?????ng phim", rs.getInt(5));
                                                    if (lengthText.isBlank()) {
                                                        JOptionPane.showMessageDialog(null, "Th???i l?????ng kh??ng h???p l???",
                                                                "ERROR", JOptionPane.ERROR_MESSAGE);
                                                    }
                                                    for (char c : lengthText.toCharArray()) { // ktra co phai la so hay
                                                                                              // khong
                                                        if (!Character.isDigit(c)) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "Th???i l?????ng kh??ng h???p l???",
                                                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                                                            break;
                                                        }
                                                    }
                                                    int lengthU = Integer.parseInt(lengthText);
                                                    String category = JOptionPane.showInputDialog("Nh???p th??? lo???i phim",
                                                            rs.getString(6));
                                                    if (!category.isBlank()) {
                                                        String quantityText = JOptionPane
                                                                .showInputDialog("Nh???p s??? l?????ng", rs.getString(7));
                                                        if (quantityText.isBlank()) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "S??? l?????ng kh??ng h???p l???",
                                                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                                                        }
                                                        for (char c : quantityText.toCharArray()) {
                                                            if ((!Character.isDigit(c))) {
                                                                JOptionPane.showMessageDialog(null,
                                                                        "S??? l?????ng kh??ng h???p l???", "ERROR",
                                                                        JOptionPane.ERROR_MESSAGE);
                                                                break;
                                                            }
                                                        }
                                                        int quantityU = Integer.parseInt(quantityText);

                                                        String imageAddress = JOptionPane
                                                                .showInputDialog("Nh???p ?????a ch??? h??nh ???nh",
                                                                        rs.getString(10));
                                                        if (!imageAddress.isBlank()) {
                                                            String fundsText = JOptionPane
                                                                    .showInputDialog("Nh???p gi?? mua v??o", rs.getInt(8));
                                                            if (fundsText.isBlank()) {
                                                                JOptionPane.showMessageDialog(null, "Gi?? h???p l???",
                                                                        "ERROR", JOptionPane.ERROR_MESSAGE);
                                                            }
                                                            for (char c : fundsText.toCharArray()) {
                                                                if (!Character.isDigit(c)) {
                                                                    JOptionPane.showMessageDialog(null, "Gi?? h???p l???",
                                                                            "ERROR", JOptionPane.ERROR_MESSAGE);
                                                                    break;
                                                                }
                                                            }
                                                            Float funds = Float.parseFloat(fundsText);

                                                            String costText = JOptionPane
                                                                    .showInputDialog("Nh???p gi?? b??n ra", rs.getInt(9));
                                                            if (costText.isBlank()) {
                                                                JOptionPane.showMessageDialog(null, "Gi?? kh??ng h???p l???",
                                                                        "ERROR", JOptionPane.ERROR_MESSAGE);
                                                            }
                                                            for (char c : costText.toCharArray()) {
                                                                if (!Character.isDigit(c)) {
                                                                    JOptionPane.showMessageDialog(null,
                                                                            "Gi?? kh??ng h???p l???",
                                                                            "ERROR", JOptionPane.ERROR_MESSAGE);
                                                                    break;
                                                                }
                                                            }
                                                            Float cost = Float.parseFloat(costText);

                                                            try {

                                                                connection = conn.getConnection();
                                                                sql = "update diaphim set `tensanpham` = ?,`nhasanxuat` = ?,`daodien` = ?,`thoiluong` = ?,`theloai` = ?, `soluong` = ?,`giamua` = ?,`giaban` = ?,`image` = ? where `masanpham` = ?";
                                                                ps = connection.prepareStatement(sql);

                                                                ps.setString(1, productName);
                                                                ps.setString(2, producerName);
                                                                ps.setString(3, directorName);
                                                                ps.setInt(4, lengthU);
                                                                ps.setString(5, category);
                                                                ps.setInt(6, quantityU);
                                                                ps.setFloat(7, funds);
                                                                ps.setFloat(8, cost);
                                                                ps.setString(9, imageAddress);
                                                                ps.setInt(10, id);
                                                                ps.executeUpdate();
                                                                ps.close();
                                                                for (DigitalVideoDisc dvd : DVDList) {
                                                                    if (dvd.getId() == id) {
                                                                        dvd.setTitle(producerName);
                                                                        dvd.setProducer(productName);
                                                                        dvd.setDirector(directorName);
                                                                        dvd.setLength(lengthU);
                                                                        dvd.setCategory(category);
                                                                        dvd.setQuantity(quantityU);
                                                                        dvd.setFuns(funds);
                                                                        dvd.setCost(cost);
                                                                        dvd.setImage(imageAddress);
                                                                    }
                                                                }
                                                                JPanel subPanel = (JPanel) panel.getParent();
                                                                JPanel subSubPanel = OnlineSelectionScrollPane
                                                                        .addProductDVD(id, productName,
                                                                                imageAddress,
                                                                                cost, quantityU, lengthU,
                                                                                directorName,
                                                                                producerName);
                                                                subPanel.add(subSubPanel);
                                                                subPanel.validate();
                                                                subPanel.repaint();
                                                                JPanel parentPanel = (JPanel) panel.getParent();
                                                                parentPanel.remove(panel);
                                                                parentPanel.validate();
                                                                parentPanel.repaint();

                                                            } catch (ArrayIndexOutOfBoundsException ex) {

                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "???nh tr???ng", "ERROR",
                                                                    JOptionPane.ERROR_MESSAGE);
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Ch??a nh???p th??? lo???i phim", "ERROR",
                                                                JOptionPane.ERROR_MESSAGE);
                                                    }
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Ch??a nh???p t??n ?????o di???n",
                                                            "ERROR", JOptionPane.ERROR_MESSAGE);
                                                }
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Ch??a nh???p t??n nh?? s???n xu???t",
                                                        "ERROR",
                                                        JOptionPane.ERROR_MESSAGE);
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Ch??a nh???p t??n phim", "ERROR",
                                                    JOptionPane.ERROR_MESSAGE);
                                        }

                                        if (productName == null || imageAddress == null)
                                            throw new NullPointerException();
                                    }

                                    // if(productName == null){
                                    // JOptionPane.showMessageDialog(null, "T??n kh??ng h???p l???", "ERROR",
                                    // JOptionPane.ERROR_MESSAGE);
                                    // }
                                    // System.out.println("1" + productName + "1");

                                } catch (Exception ex) {

                                }
                            }

                        }

                    } else { // if not admin access
                        JPanel subPanel = (JPanel) panel.getParent();
                        DetailDVD detail = new DetailDVD(subPanel, panel, id, title, imageAddress, cost, quantity,
                                length, director, producer);
                        // test detail = new test(productName, imageAddress, cost, quantity);
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    panel.setBackground(Color.pink);
                    panel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));

                    if (OnlineLoginPanel.isAdminAccess())
                        panel.setToolTipText("Nh???p chu???t tr??i ????? x??a. Nh???p chu???t ph???i ????? ch???nh s???a");

                    else
                        panel.setToolTipText("B???m v??o ????? mua");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    panel.setBackground(null);
                    panel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                }

            });

        }
        return panel;

    }

    protected static JPanel addProductBook(int id, String title, String imageAddress, Float cost, int quantity,
            List<String> authors, String publisher, String category) {
        JPanel panel = new JPanel();
        if (quantity != 0) {
            BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
            panel.setLayout(layout);
            JLabel tempLabel;
            try {
                URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource(imageAddress);
                tempLabel = new JLabel(title, new ImageIcon(ImageIO.read(url)), JLabel.CENTER);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Kh??ng th???y ???nh", "ERROR", JOptionPane.ERROR_MESSAGE);
                URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("close-icon.png");
                tempLabel = new JLabel(title, new ImageIcon(url), JLabel.CENTER);
            }

            JLabel imageNameLabel = tempLabel;
            imageNameLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
            imageNameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            imageNameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            panel.add(imageNameLabel);

            String costText = "Gi??: " + cost;
            JLabel costLabel = new JLabel(costText);
            costLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

            String quantityText = "S??? l?????ng: " + quantity;
            JLabel quantityLabel = new JLabel(quantityText);

            panel.add(costLabel);
            panel.add(quantityLabel);
            panel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            panel.validate();
            panel.repaint();

            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (OnlineLoginPanel.isAdminAccess()) {
                        if (!e.isMetaDown()) {// if not right click
                            int choice = JOptionPane.showConfirmDialog(null, "X??a ?", "Confirmation",
                                    JOptionPane.YES_NO_OPTION);
                            if (choice == JOptionPane.YES_OPTION) {
                                try {
                                    Connection connection = conn.getConnection();
                                    String sql = "delete from sach where `masanpham` = ?";
                                    PreparedStatement ps = connection.prepareStatement(sql);
                                    ps.setInt(1, id);
                                    ps.executeUpdate();
                                    ps.close();
                                    JPanel parentPanel = (JPanel) panel.getParent();
                                    parentPanel.remove(panel);
                                    parentPanel.validate();
                                    parentPanel.repaint();
                                } catch (Exception ex) {
                                    // TODO: handle exception
                                }

                            }
                        }

                        else { // if right click
                            int choice = JOptionPane.showConfirmDialog(null, "C???p nh???t s???n ph???m ?", "Confirmation",
                                    JOptionPane.YES_NO_OPTION);
                            if (choice == JOptionPane.YES_OPTION) {
                                try {
                                    Connection connection = conn.getConnection();
                                    String sql = "select * from sach where `masanpham` = ?";
                                    PreparedStatement ps = connection.prepareStatement(sql);

                                    ps.setInt(1, id);
                                    ResultSet rs = ps.executeQuery();

                                    while (rs.next()) {

                                        // dang do
                                        String productName = JOptionPane.showInputDialog("Nh???p t??n s???n ph???m",
                                                rs.getString(2));
                                        if (!productName.isBlank()) {
                                            String producerName = JOptionPane.showInputDialog("Nh???p t??n nh?? xu???t b???n",
                                                    rs.getString(3));
                                            if (!producerName.isBlank()) {
                                                String directorName = JOptionPane.showInputDialog("Nh???p t??n t??c gi???",
                                                        rs.getString(4));
                                                List<String> author = new ArrayList<String>();
                                                author.add(directorName);
                                                if (!directorName.isBlank()) {

                                                    String category = JOptionPane.showInputDialog("Nh???p lo???i s??ch",
                                                            rs.getString(5));
                                                    if (!category.isBlank()) {
                                                        String quantityText = JOptionPane
                                                                .showInputDialog("Nh???p s??? l?????ng", rs.getInt(6));
                                                        if (quantityText.isBlank()) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "S??? l?????ng kh??ng h???p l???",
                                                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                                                        }
                                                        for (char c : quantityText.toCharArray()) {
                                                            if ((!Character.isDigit(c))) {
                                                                JOptionPane.showMessageDialog(null,
                                                                        "S??? l?????ng kh??ng h???p l???", "ERROR",
                                                                        JOptionPane.ERROR_MESSAGE);
                                                                break;
                                                            }
                                                        }
                                                        int quantity = Integer.parseInt(quantityText);

                                                        String imageAddress = JOptionPane
                                                                .showInputDialog("Nh???p ?????a ch??? h??nh ???nh",
                                                                        rs.getString(9));
                                                        if (!imageAddress.isBlank()) {
                                                            String fundsText = JOptionPane
                                                                    .showInputDialog("Nh???p gi?? mua v??o", rs.getInt(7));
                                                            if (fundsText.isBlank()) {
                                                                JOptionPane.showMessageDialog(null, "Gi?? kh??ng h???p l???",
                                                                        "ERROR", JOptionPane.ERROR_MESSAGE);
                                                            }
                                                            for (char c : fundsText.toCharArray()) {
                                                                if (!Character.isDigit(c)) {
                                                                    JOptionPane.showMessageDialog(null,
                                                                            "Gi?? kh??ng h???p l???",
                                                                            "ERROR", JOptionPane.ERROR_MESSAGE);
                                                                    break;
                                                                }
                                                            }
                                                            Float funds = Float.parseFloat(fundsText);

                                                            String costText = JOptionPane
                                                                    .showInputDialog("Nh???p gi?? b??n ra", rs.getInt(8));
                                                            if (costText.isBlank()) {
                                                                JOptionPane.showMessageDialog(null, "Gi?? kh??ng h???p l???",
                                                                        "ERROR", JOptionPane.ERROR_MESSAGE);
                                                            }
                                                            for (char c : costText.toCharArray()) {
                                                                if (!Character.isDigit(c)) {
                                                                    JOptionPane.showMessageDialog(null,
                                                                            "Gi?? kh??ng h???p l???", "ERROR",
                                                                            JOptionPane.ERROR_MESSAGE);
                                                                    break;
                                                                }
                                                            }
                                                            Float cost = Float.parseFloat(costText);

                                                            try {
                                                                connection = conn.getConnection();
                                                                sql = "update sach set `tensanpham` = ?,`nhaxuatban` = ?,`tacgia` = ?,`theloai` = ?,`soluong` = ?,`giamua` = ?,`giaban` = ?,`image` = ? where `masanpham` = ?";
                                                                ps = connection.prepareStatement(sql);

                                                                ps.setString(1, productName);
                                                                ps.setString(2, producerName);
                                                                ps.setString(3, directorName);
                                                                ps.setString(4, category);
                                                                ps.setInt(5, quantity);
                                                                ps.setFloat(6, funds);
                                                                ps.setFloat(7, cost);
                                                                ps.setString(8, imageAddress);
                                                                ps.setInt(9, id);
                                                                ps.executeUpdate();
                                                                ps.close();

                                                                for (Book book : bookList) {
                                                                    if (book.getId() == id) {
                                                                        book.setTitle(productName);
                                                                        book.setPublisher(producerName);
                                                                        book.setAuthors(author);
                                                                        book.setCategory(category);
                                                                        book.setQuantity(quantity);
                                                                        book.setFuns(funds);
                                                                        book.setCost(cost);
                                                                        book.setImage(imageAddress);
                                                                    }
                                                                }
                                                                JPanel subPanel = (JPanel) panel.getParent();
                                                                JPanel subSubPanel = OnlineSelectionScrollPane
                                                                        .addProductBook(id,
                                                                                productName, imageAddress, cost,
                                                                                quantity, author, producerName,
                                                                                category);
                                                                subPanel.add(subSubPanel);
                                                                subPanel.validate();
                                                                subPanel.repaint();
                                                                JPanel parentPanel = (JPanel) panel.getParent();
                                                                parentPanel.remove(panel);
                                                                parentPanel.validate();
                                                                parentPanel.repaint();
                                                            } catch (ArrayIndexOutOfBoundsException ex) {

                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "???nh tr???ng", "ERROR",
                                                                    JOptionPane.ERROR_MESSAGE);
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Ch??a nh???p lo???i s??ch", "ERROR",
                                                                JOptionPane.ERROR_MESSAGE);
                                                    }
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Ch??a nh???p t??n t??c gi???",
                                                            "ERROR", JOptionPane.ERROR_MESSAGE);
                                                }
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Ch??a nh???p t??n nh?? xu???t b???n",
                                                        "ERROR",
                                                        JOptionPane.ERROR_MESSAGE);
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Ch??a nh???p t??n s??ch", "ERROR",
                                                    JOptionPane.ERROR_MESSAGE);
                                        }

                                        if (productName == null || imageAddress == null)
                                            throw new NullPointerException();
                                    }

                                    // if(productName == null){
                                    // JOptionPane.showMessageDialog(null, "T??n kh??ng h???p l???", "ERROR",
                                    // JOptionPane.ERROR_MESSAGE);
                                    // }
                                    // System.out.println("1" + productName + "1");

                                } catch (Exception ex) {

                                }
                            }

                        }

                    } else { // if not admin access
                        JPanel subPanel = (JPanel) panel.getParent();

                        DetailBook detail = new DetailBook(subPanel, panel, id, title, imageAddress, cost, quantity,
                                authors,
                                publisher, category);
                        // test detail = new test(productName, imageAddress, cost, quantity);
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    panel.setBackground(Color.pink);
                    panel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));

                    if (OnlineLoginPanel.isAdminAccess())
                        panel.setToolTipText("Nh???p chu???t tr??i ????? x??a. Nh???p chu???t ph???i ????? ch???nh s???a");

                    else
                        panel.setToolTipText("B???m v??o ????? mua");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    panel.setBackground(null);
                    panel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                }

            });
        }
        return panel;

    }

    protected static JPanel addProductCD(int id, String title, String imageAddress, String director, int length,
            Float cost, int quantity, String artist, List<Track> trackList) {
        JPanel panel = new JPanel();
        if (quantity != 0) {
            BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
            panel.setLayout(layout);
            JLabel tempLabel;
            try {
                URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource(imageAddress);
                tempLabel = new JLabel(title, new ImageIcon(ImageIO.read(url)), JLabel.CENTER);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Kh??ng th???y ???nh", "ERROR", JOptionPane.ERROR_MESSAGE);
                URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("close-icon.png");
                tempLabel = new JLabel(title, new ImageIcon(url), JLabel.CENTER);
            }

            JLabel imageNameLabel = tempLabel;
            imageNameLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
            imageNameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            imageNameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            panel.add(imageNameLabel);

            String costText = "Gi??: " + cost;
            JLabel costLabel = new JLabel(costText);
            costLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

            String quantityText = "S??? l?????ng: " + quantity;
            JLabel quantityLabel = new JLabel(quantityText);

            panel.add(costLabel);
            panel.add(quantityLabel);
            panel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            panel.validate();
            panel.repaint();

            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (OnlineLoginPanel.isAdminAccess()) {
                        if (!e.isMetaDown()) {// if not right click
                            int choice = JOptionPane.showConfirmDialog(null, "X??a ?", "Confirmation",
                                    JOptionPane.YES_NO_OPTION);
                            if (choice == JOptionPane.YES_OPTION) {
                                try {
                                    Connection connection = conn.getConnection();
                                    String sql = "delete from dianhac where `masanpham` = ?";
                                    PreparedStatement ps = connection.prepareStatement(sql);
                                    ps.setInt(1, id);
                                    ps.executeUpdate();
                                    ps.close();
                                    // System.out.println("check delete cd");
                                    JPanel parentPanel = (JPanel) panel.getParent();
                                    parentPanel.remove(panel);
                                    parentPanel.validate();
                                    parentPanel.repaint();
                                } catch (Exception ex) {
                                    // TODO: handle exception
                                }

                            }
                        }

                        else { // if right click
                            int choice = JOptionPane.showConfirmDialog(null, "C???p nh???t s???n ph???m ?", "Confirmation",
                                    JOptionPane.YES_NO_OPTION);
                            if (choice == JOptionPane.YES_OPTION) {
                                try {
                                    Connection connection = conn.getConnection();
                                    String sql = "select *from dianhac where `masanpham` = ?";
                                    PreparedStatement ps = connection.prepareStatement(sql);

                                    ps.setInt(1, id);
                                    ResultSet rs = ps.executeQuery();

                                    while (rs.next()) {

                                        // dang do
                                        String productName = JOptionPane.showInputDialog("Nh???p t??n s???n ph???m",
                                                rs.getString(2));
                                        if (!productName.isBlank()) {
                                            String producerName = JOptionPane.showInputDialog(
                                                    "Nh???p t??n nh?? s???n xu???t", rs.getString(3));
                                            if (!producerName.isBlank()) {
                                                String directorName = JOptionPane.showInputDialog("Nh???p t??n ngh??? s??",
                                                        rs.getString(4));
                                                if (!directorName.isBlank()) {
                                                    String lengthText = JOptionPane
                                                            .showInputDialog("Nh???p th???i l?????ng ????a nh???c", rs.getInt(5));
                                                    if (lengthText.isBlank()) {
                                                        JOptionPane.showMessageDialog(null, "Th???i l?????ng kh??ng h???p l???",
                                                                "ERROR", JOptionPane.ERROR_MESSAGE);
                                                    }
                                                    for (char c : lengthText.toCharArray()) { // ktra co phai la so hay
                                                                                              // khong
                                                        if (!Character.isDigit(c)) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "Th???i l?????ng kh??ng h???p l???",
                                                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                                                            break;
                                                        }
                                                    }
                                                    int length = Integer.parseInt(lengthText);
                                                    String category = JOptionPane.showInputDialog("Nh???p th??? lo???i nh???c",
                                                            rs.getString(6));
                                                    if (!category.isBlank()) {
                                                        String quantityText = JOptionPane
                                                                .showInputDialog("Nh???p s??? l?????ng", rs.getInt(7));
                                                        if (quantityText.isBlank()) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "S??? l?????ng kh??ng h???p l???",
                                                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                                                        }
                                                        for (char c : quantityText.toCharArray()) {
                                                            if ((!Character.isDigit(c))) {
                                                                JOptionPane.showMessageDialog(null,
                                                                        "S??? l?????ng kh??ng h???p l???", "ERROR",
                                                                        JOptionPane.ERROR_MESSAGE);
                                                                break;
                                                            }
                                                        }
                                                        int quantity = Integer.parseInt(quantityText);

                                                        String imageAddress = JOptionPane.showInputDialog(
                                                                "Nh???p ?????a ch??? h??nh ???nh", rs.getString(10));
                                                        if (!imageAddress.isBlank()) {
                                                            String fundsText = JOptionPane
                                                                    .showInputDialog("Nh???p gi?? mua v??o", rs.getInt(8));
                                                            if (fundsText.isBlank()) {
                                                                JOptionPane.showMessageDialog(null, "Gi?? kh??ng h???p l???",
                                                                        "ERROR", JOptionPane.ERROR_MESSAGE);
                                                            }
                                                            for (char c : fundsText.toCharArray()) {
                                                                if (!Character.isDigit(c)) {
                                                                    JOptionPane.showMessageDialog(null,
                                                                            "Gi?? kh??ng h???p l???",
                                                                            "ERROR", JOptionPane.ERROR_MESSAGE);
                                                                    break;
                                                                }
                                                            }
                                                            Float funds = Float.parseFloat(fundsText);

                                                            String costText = JOptionPane
                                                                    .showInputDialog("Nh???p gi?? b??n ra", rs.getInt(9));
                                                            if (costText.isBlank()) {
                                                                JOptionPane.showMessageDialog(null, "Gi?? kh??ng h???p l???",
                                                                        "ERROR", JOptionPane.ERROR_MESSAGE);
                                                            }
                                                            for (char c : costText.toCharArray()) {
                                                                if (!Character.isDigit(c)) {
                                                                    JOptionPane.showMessageDialog(null,
                                                                            "Gi?? kh??ng h???p l???",
                                                                            "ERROR", JOptionPane.ERROR_MESSAGE);
                                                                    break;
                                                                }
                                                            }
                                                            Float cost = Float.parseFloat(costText);
                                                            List<Track> trackList = new ArrayList<Track>();
                                                            try {

                                                                // System.out.println("check input: " + productName);
                                                                connection = conn.getConnection();
                                                                sql = "update dianhac set `tensanpham` = ?,`nhasanxuat` = ?,`nghesi` = ?,`thoiluong` = ?,`theloai` = ?, `soluong` = ?,`giamua` = ?,`giaban` = ?,`image` = ? where `masanpham` = ?";
                                                                ps = connection.prepareStatement(sql);

                                                                ps.setString(1, productName);
                                                                ps.setString(2, producerName);
                                                                ps.setString(3, directorName);
                                                                ps.setInt(4, length);
                                                                ps.setString(5, category);
                                                                ps.setInt(6, quantity);
                                                                ps.setFloat(7, funds);
                                                                ps.setFloat(8, cost);
                                                                ps.setString(9, imageAddress);
                                                                ps.setInt(10, id);
                                                                ps.executeUpdate();
                                                                ps.close();

                                                                for (CompactDisc cd : CDList) {
                                                                    if (cd.getId() == id) {
                                                                        cd.setTitle(productName);
                                                                        cd.setDirector(productName);
                                                                        cd.setArtist(director);
                                                                        cd.setLength(length);
                                                                        cd.setCategory(category);
                                                                        cd.setQuantity(quantity);
                                                                        cd.setFuns(funds);
                                                                        cd.setCost(cost);
                                                                        cd.setImage(imageAddress);
                                                                    }
                                                                }
                                                                JPanel subPanel = (JPanel) panel.getParent();
                                                                JPanel subSubPanel = OnlineSelectionScrollPane
                                                                        .addProductCD(id, productName,
                                                                                imageAddress, producerName, length,
                                                                                cost, quantity, directorName,
                                                                                trackList);
                                                                subPanel.add(subSubPanel);
                                                                subPanel.validate();
                                                                subPanel.repaint();
                                                                JPanel parentPanel = (JPanel) panel.getParent();
                                                                parentPanel.remove(panel);
                                                                parentPanel.validate();
                                                                parentPanel.repaint();

                                                            } catch (ArrayIndexOutOfBoundsException ex) {

                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "???nh tr???ng", "ERROR",
                                                                    JOptionPane.ERROR_MESSAGE);
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Ch??a nh???p th??? lo???i phim", "ERROR",
                                                                JOptionPane.ERROR_MESSAGE);
                                                    }
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Ch??a nh???p t??n ?????o di???n",
                                                            "ERROR", JOptionPane.ERROR_MESSAGE);
                                                }
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Ch??a nh???p t??n nh?? s???n xu???t",
                                                        "ERROR",
                                                        JOptionPane.ERROR_MESSAGE);
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Ch??a nh???p t??n phim", "ERROR",
                                                    JOptionPane.ERROR_MESSAGE);
                                        }
                                        if (productName == null || imageAddress == null)
                                            throw new NullPointerException();
                                    }

                                    // if(productName == null){
                                    // JOptionPane.showMessageDialog(null, "T??n kh??ng h???p l???", "ERROR",
                                    // JOptionPane.ERROR_MESSAGE);
                                    // }
                                    // System.out.println("1" + productName + "1");

                                } catch (Exception ex) {

                                }
                            }

                        }

                    } else { // if not admin access
                        JPanel subPanel = (JPanel) panel.getParent();
                        DetailCD detail = new DetailCD(subPanel, panel, id, title, imageAddress, director, length, cost,
                                quantity, artist, trackList);
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    panel.setBackground(Color.pink);
                    panel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));

                    if (OnlineLoginPanel.isAdminAccess())
                        panel.setToolTipText("Nh???p chu???t tr??i ????? x??a. Nh???p chu???t ph???i ????? ch???nh s???a");

                    else
                        panel.setToolTipText("B???m v??o ????? mua");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    panel.setBackground(null);
                    panel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
                }

            });
        }
        return panel;

    }
}