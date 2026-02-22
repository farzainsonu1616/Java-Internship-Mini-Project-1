package com.quiz.app;

import com.quiz.app.util.DBConnection;
import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection con = DBConnection.getConnection();
    }
}