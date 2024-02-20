package edu.esprit.entites;

public class Formation {
    private int id_formation;

    public Formation() {
    }

    public Formation(int id_formation) {
        this.id_formation = id_formation;
    }

    public int getId_formation() {
        return id_formation;
    }

    public void setId_formation(int id_formation) {
        this.id_formation = id_formation;
    }
    public String toString() {
        return String.valueOf(id_formation);
    }
}
