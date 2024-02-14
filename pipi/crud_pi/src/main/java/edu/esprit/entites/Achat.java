package edu.esprit.entites;

import java.time.LocalDate;

public class Achat {
    private int id_achat;
    private int user;
    private int formation;
    private int outil;
    private String code_promo;
    private int total;
    private LocalDate date_achat;

    public Achat(int id_achat, int user, int formation, int outil, String code_promo, int total ,LocalDate date) {
        this.id_achat = id_achat;
        this.user = user;
        this.formation = formation;
        this.outil = outil;
        this.code_promo = code_promo;
        this.total = total;
        this.date_achat = date;
    }

    public Achat(int id_achat, int user, int formation, int outil, String code, int total) {
    }

    public Achat(int user, int formation, int outil, String code_promo, int total) {
        this.user = user;
        this.formation = formation;
        this.outil = outil;
        this.code_promo = code_promo;
        this.total = total;
        this.date_achat = LocalDate.now();
    }

    public int getId_achat() {
        return id_achat;
    }

    public void setId_achat(int id_achat) {
        this.id_achat = id_achat;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getFormation() {
        return formation;
    }

    public void setFormation(int formation) {
        this.formation = formation;
    }

    public int getOutil() {
        return outil;
    }

    public void setOutil(int outil) {
        this.outil = outil;
    }

    public String getCode_promo() {
        return code_promo;
    }

    public void setCode_promo(String code_promo) {
        this.code_promo = code_promo;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public LocalDate getDate_achat() {
        return date_achat;
    }

    public void setDate_achat() {
        this.date_achat = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Achat{" +
                "id_achat" + id_achat +
                ", user=" + user +
                ", formation=" + formation +
                ", outil=" + outil +
                ", code_promo='" + code_promo + '\'' +
                ", total=" + total +
                ", date_achat=" + date_achat +
                '}';
    }
}

