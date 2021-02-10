package com.scottpreston.javarobot.chapter8;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQL {

    private Connection conn;
    private String host;
    private String user;
    private String password;
    private String database;

    public MySQL(String h, String d, String u, String p) {
        host = h;
        user = u;
        password = p;
        database  = d;
    }
    
    public void open() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager
                    .getConnection("jdbc:mysql://" + host
                            + "/" + database 
                            + "?user=" + user
                            + "&password=" + password);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void close() {
        conn = null;
    }


    public Connection getConn() {
        return conn;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    public static java.sql.Date toSqlDate(java.util.Date utilDate) {
        return new java.sql.Date(utilDate.getTime());
    }
    
    public static void main(String[] args) {
        MySQL test = new MySQL("localhost", "test" , "root","password");
        test.open();
        test.close();
        System.out.println("done" + new java.util.Date());
    }
}
