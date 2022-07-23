package com.mycompany.storegui;

import java.net.URL;
import java.rmi.server.ObjID;
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

public class DetailHotdeal {
    connectDB conn;
    private JTable table;

    public DetailHotdeal(Date dateFrom, Date dateTo) {

        JFrame detailFrame = new JFrame("Top sản phẩm bán chạy nhất");
        detailFrame.setSize(500, 500);
        JScrollPane scrollPane = new JScrollPane();
        detailFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        try {
            Connection connect = conn.getConnection();
            String sql = "select  masanpham, SUM(soluong) from chitiethd  inner join hoadon on chitiethd.idHD  = hoadon.idHD where hoadon.ngaytaodon between ? and ? GROUP BY masanpham ORDER by SUM(soluong) DESC;";
            PreparedStatement pr = connect.prepareStatement(sql);
            pr.setString(1, String.valueOf(dateFrom));
            pr.setString(2, String.valueOf(dateTo));
            ResultSet rs = pr.executeQuery();
            Object columnNames[] = { "Số thứ tự", "Tên sản phẩm", "Số lượng", "Đơn giá" };
            // Object rowData[][] = { { "1", "das", "45", "25000" }, { "2", "abc", "45",
            // "25000" } };
            Object rowData[][] = new Object[50][4];
            String nameProduct = "";
            float cost = 0;
            int i = 0;
            while (rs.next()) {

                if (rs.getInt(1) > 0 && rs.getInt(1) < 31) {
                    for (DigitalVideoDisc dvd : OnlineSelectionScrollPane.DVDList) {
                        if (rs.getInt(1) == dvd.getId()) {
                            nameProduct = dvd.getTitle();
                            cost = dvd.getCost();
                        }
                    }
                } else {
                    if (rs.getInt(1) > 30 && rs.getInt(1) < 61) {
                        for (Book book : OnlineSelectionScrollPane.bookList) {
                            if (rs.getInt(1) == book.getId()) {
                                nameProduct = book.getTitle();
                                cost = book.getCost();
                            }
                        }
                    } else {
                        if (rs.getInt(1) > 60) {
                            for (CompactDisc cd : OnlineSelectionScrollPane.CDList) {
                                if (rs.getInt(1) == cd.getId()) {
                                    nameProduct = cd.getTitle();
                                    cost = cd.getCost();
                                }
                            }
                        }
                    }
                }

                Object tmp[][] = { { i + 1, nameProduct, rs.getInt(2), cost } };
                System.arraycopy(tmp, 0, rowData, i, tmp.length);
                i++;
            }
            table = new JTable(rowData, columnNames);
            scrollPane.setViewportView(table);
        } catch (Exception e) {
            // TODO: handle exception
        }

        Dimension dimension = new Dimension(5, 0);
        // URL url =
        // OnlineSelectionScrollPane.class.getClassLoader().getResource(imageAddress);
        detailFrame.setVisible(true);
    }
}
