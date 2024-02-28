package com.esprit.models;

import java.time.LocalDate;
import java.util.Objects;

public class Achat {
private int idAchat;
private Formation formation;
private outil outil;
private double total;
private LocalDate date;
public Achat(){}


    public Achat(int idAchat, Formation formation, outil outil, double total, LocalDate date) {
        this.idAchat = idAchat;
        this.formation = formation;
        this.outil = outil;
        this.total = total;
        this.date = LocalDate.now();    }
    public Achat(int idAchat, Formation formation, outil outil, double total) {
        this.idAchat = idAchat;
        this.formation = formation;
        this.outil = outil;
        this.total = total;
          }

    public Achat(Formation formation, outil outil, double total , LocalDate date) {
        this.formation = formation;
        this.outil = outil;
        this.total = total;
        this.date = date;
    }

    public int getIdAchat() {
        return idAchat;
    }

    public void setIdAchat(int idAchat) {
        this.idAchat = idAchat;
    }

    public Formation getidFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public outil getOutil() {
        return outil;
    }

    public void setOutil(outil outil) {
        this.outil = outil;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate value) {
        this.date = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Achat achat = (Achat) o;
        return idAchat == achat.idAchat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAchat);
    }

    @Override
    public String toString() {
        return "Achat{" +
                "idAchat=" + idAchat +
                ", formation=" + formation +
                ", outil=" + outil +
                ", total=" + total +
                ", date=" + date +
                '}';
    }
}

