package com.expence.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static String url = "jdbc:mysql://localhost:3306/java_project";
    private static String user ="root";
    private static String password = "password";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection con = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url,user,password);
        return con;
    }

    public static void closeConnection(Connection con){
        if(con != null){
            con = null;
        }
    }
}
