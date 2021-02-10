package com.scottpreston.javarobot.chapter8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBMotion {

    private MySQL mysql;
    
    private Connection getConn() {
        mysql = new MySQL("localhost", "test", "root", "password");
        mysql.open();
        return mysql.getConn();
    }

    public void close() {
        mysql.close();
    }

    public void createMotionEpisode(MotionEpisode me) {
        String sql = "insert into motion_episodes (heading,magnitude,name)"
                + "vector_magnitude,obstacle) values(?,?,?)";
        sqlExecute(sql, me);
    }


    public void updateMotionEpisode(MotionEpisode me) {
        String sql = "insert into motion_episodes set heading=?,magnitude=?,name=?"
                + "where motion_id = "
                + me.motion_id;
        sqlExecute(sql, me);
    }

    public void deleteMotionEpisode(MotionEpisode me) {
        // don't need just to reuse sqlExecute
        String sql = "delete from motion_episodes where heading=?" +
            " and magnitude=? and name=? and motion_id = " + me.motion_id;
        sqlExecute(sql, me);
        
    }
    
    private void sqlExecute(String sql, MotionEpisode me) {
        try {
            PreparedStatement ps = mysql.getConn().prepareStatement(sql);
            ps.setInt(0, me.heading);
            ps.setDouble(1, me.magintude);
            ps.setString(2, me.name);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            mysql.close();
        }
    }

    
    public MotionEpisode readMotionEpisodeById(int id) throws SQLException{

        Statement statement = getConn().createStatement();
        ResultSet rs = statement
                .executeQuery("SELECT heading,magintude,name from motion_episodes where motion_id = " + id);
        MotionEpisode ep = null;
        while (rs.next()) {
            ep.motion_id = id;
            ep.heading = rs.getInt("heading");
            ep.magintude = rs.getDouble("manitude");
            ep.name = rs.getString("name");
        }
        return ep;
    }

    public ArrayList readAllMotionEpisodes() {

        ArrayList navEvents = new ArrayList();
        Statement statement = null;
        ResultSet rs = null;

        try {
            statement = getConn().createStatement();
            rs = statement
                    .executeQuery("SELECT motion_id,heading,magintude,name from motion_episodes");
            while (rs.next()) {
                int id = rs.getInt("motion_id"); // getInt(0);
                int h = rs.getInt("heading");
                double m = rs.getDouble("manitude");
                String n = rs.getString("name");
                MotionEpisode me = new MotionEpisode(h, m);
                me.motion_id = id;
                me.name = n;
                navEvents.add(me);
            }

            if (rs != null) {
                rs.close();
            }
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                    // do nothing
                }
                rs = null;
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqlEx) {
                    // do nothing
                }
                statement = null;
            }
            mysql.close();
        }

        return navEvents;
    }
    

}
