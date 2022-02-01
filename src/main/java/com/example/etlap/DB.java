package com.example.etlap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

public class DB {
    Connection conn;

    public DB() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/etlap", "root", "");
    }

    public List<Etel> getEtlap() throws SQLException {
        List<Etel> etelList = new ArrayList<>();

        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT etlap.id, etlap.nev, etlap.leiras, etlap.ar, kategoria.nev FROM etlap INNER JOIN kategoria ON etlap.kategoria_id = kategoria.id");
        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("nev");
            String category = result.getString("kategoria.nev");
            String desc = result.getString("leiras");
            int price = result.getInt("ar");
            Etel e = new Etel(id, name, category, desc, price);
            etelList.add(e);
        }

        return etelList;
    }

    public List<Kategoria> getKategoria() throws SQLException {
        List<Kategoria> kategoriak = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM kategoria");
        while (result.next()) {
            int id = result.getInt("id");
            String nev = result.getString("nev");
            kategoriak.add(new Kategoria(id, nev));
        }
        return kategoriak;
    }

    public int addEtel(String nev, int kategoria, String leiras, int ar) throws SQLException {
        String sql = "INSERT INTO etlap (nev, kategoria_id, leiras, ar) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nev);
        stmt.setInt(2, kategoria);
        stmt.setString(3, leiras);
        stmt.setInt(4, ar);

        return stmt.executeUpdate();
    }

    public boolean deleteEtel(int id) throws SQLException {
        String sql = "DELETE FROM etlap WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int affectedLines = stmt.executeUpdate();
        return affectedLines == 1;
    }

    public boolean percentageChange(int precentage) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar * ((100 + ?) / 100)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, precentage);
        int affectedLines = stmt.executeUpdate();
        return affectedLines != 0;
    }

    public boolean percentageChange(int precentage, int id) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar * ((100 + ?) / 100) WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, precentage);
        stmt.setInt(2, id);
        int affectedLines = stmt.executeUpdate();
        return affectedLines != 0;
    }

    public boolean hufChange(int huf) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar + ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, huf);
        int affectedLines = stmt.executeUpdate();
        return affectedLines != 0;
    }

    public boolean hufChange(int huf, int id) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar + ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, huf);
        stmt.setInt(2, id);
        int affectedLines = stmt.executeUpdate();
        return affectedLines != 0;
    }

    /*public boolean movieEdit(Movie newMovie) throws SQLException {
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
