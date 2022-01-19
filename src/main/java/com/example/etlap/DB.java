package com.example.etlap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {
    Connection conn;

    public DB() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/etlap", "root", "");
    }

    public List<Etel> getEtlap() throws SQLException {
        List<Etel> etelList = new ArrayList<>();

        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM etlap");
        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("nev");
            String category = result.getString("kategoria");
            String desc = result.getString("leiras");
            int price = result.getInt("ar");
            Etel e = new Etel(id, name, category, desc, price);
            etelList.add(e);
        }

        return etelList;
    }

    public int addEtel(String nev, String kategoria, String leiras, int ar) throws SQLException {
        String sql = "INSERT INTO etlap (nev, kategoria, leiras, ar) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nev);
        stmt.setString(2, kategoria);
        stmt.setString(3, leiras);
        stmt.setInt(4, ar);

        return stmt.executeUpdate();
    }

    /*public boolean movieDelete(int id) throws SQLException {
        String sql = "DELETE FROM filmek WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int affectedLines = stmt.executeUpdate();
        return affectedLines == 1;
    }

    public boolean movieEdit(Movie newMovie) throws SQLException {
        String sql = "UPDATE filmek SET (title = ?, category = ?, length = ?, rating = ?) WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, newMovie.getTitle());
        stmt.setString(2, newMovie.getCategory());
        stmt.setInt(3, newMovie.getLength());
        stmt.setInt(4, newMovie.getRating());
        stmt.setInt(5, newMovie.getId());
        int affectedLines = stmt.executeUpdate();
        return affectedLines == 1;
    }*/
}
