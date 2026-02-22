package com.quiz.app.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/online_quiz";

    private static final String USER = "root";
    private static final String PASSWORD = "root"; // ⚠️ change if your password is different

    public static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database Connected Successfully");
            return con;
        } catch (Exception e) {
            System.out.println("Database Connection Failed");
            e.printStackTrace();
            return null;
        }
    }
}