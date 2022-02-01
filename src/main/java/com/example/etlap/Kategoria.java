package com.example.etlap;

public class Kategoria {
    private int id;
    private String nev;

    public int getId() {
        return id;
    }

    public String getNev() {
        return nev;
    }

    public Kategoria(int id, String nev) {
        this.id = id;
        this.nev = nev;
    }

    @Override
    public String toString() {
        return nev;
    }
}
