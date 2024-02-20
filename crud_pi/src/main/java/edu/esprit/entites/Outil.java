package edu.esprit.entites;

public class Outil {

    private int id_outil;

    public Outil() {
    }

    public Outil(int id_outil) {
        this.id_outil = id_outil;
    }

    public int getId_outil() {
        return id_outil;
    }

    public void setId_outil(int id_outil) {
        this.id_outil = id_outil;
    }

    @Override
    public String toString() {
        return String.valueOf(id_outil);
    }
}
