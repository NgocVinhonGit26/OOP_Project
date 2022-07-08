package com.mycompany.connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class connectDB {
    private static String DB_URL = "jdbc:mysql://localhost:3306/onemediapro";
    private static String USER_NAME = "root";
    private static String PASSWORD = "26072001";

    public static void main(String args[]) {
        try {

            Connection conn = getConnection();

            // crate statement
            Statement stmt = conn.createStatement();
            // get data from table 'student'
            ResultSet rs = stmt.executeQuery("select * from user");
            // show data
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2)
                        + " " + rs.getString(3) + " " + rs.getString(4));
            }
            // close connection
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failed!");
            ex.printStackTrace();
        }
        return conn;
    }
}
