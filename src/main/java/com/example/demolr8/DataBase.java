package com.example.demolr8;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class DataBase {

    public static String DBname;
    public static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    private final String url;


    public DataBase(String DBname) {
        this.DBname = DBname;
        url = "jdbc:mysql://localhost:3306/" + DBname;
        con = null;
    }

    public static String getDBname() {
        return DBname;
    }

    public boolean Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            Properties properties = new Properties();
            properties.setProperty("user", "slave");
            properties.setProperty("password", "slave");
            properties.setProperty("useSSL", "false");
            properties.setProperty("autoReconnect", "true");
            con = getConnection(url, properties);
            //System.out.println("\n"+con+ "\n");
            this.executeQuery("use " + DBname + ";");

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean executeQuery(String query) throws SQLException {
        Statement st = con.createStatement();
        return st.execute(query);
    }

    public boolean insert(String query) throws SQLException {
        executeQuery(query);

        return true;
    }

    public boolean delete(String query) throws SQLException {
        executeQuery(query);

        return true;
    }

    public boolean update(String query) throws SQLException {
        executeQuery(query);

        return true;
    }


    public ArrayList executeQueryWithResult(String query) {
        ArrayList<String[]> rows = new ArrayList<>();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                String[] columns = new String[rsmd.getColumnCount()];
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    columns[i] = rs.getString(i + 1);
                }
                rows.add(columns);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
    }


    public ArrayList search(String text) throws SQLException {
        String query = "SELECT * FROM `currency` WHERE txt LIKE ? AND r030 > ?;";
        PreparedStatement st = this.con.prepareStatement(query);

        st.setString(1, "%" + text + "%");
        st.setString(2, "0");

        ArrayList<String[]> rows = new ArrayList<>();
        ResultSet rs = st.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        while (rs.next()) {
            String[] columns = new String[rsmd.getColumnCount()];
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                columns[i] = rs.getString(i + 1);
            }
            rows.add(columns);
        }
        return rows;
    }

}