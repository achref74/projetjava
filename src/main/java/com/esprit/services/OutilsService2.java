package com.esprit.services;

import com.esprit.models.EtatOutils;
import com.esprit.models.Outils;

import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OutilsService2 implements IService<Outils> {

    private Connection connection;

    public OutilsService2() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Outils outils) {
        String req = "INSERT into outils(nom, description,prix,ressources,stock,etat) values (?, ?,?,?,?,?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, outils.getNom());
            pst.setString(2, outils.getDescription());
            pst.setDouble(3, outils.getPrix());
            pst.setString(4, outils.getRessources());
            pst.setString(5, outils.getStock());
            pst.setString(6, outils.getEtat().name());
            pst.executeUpdate();
            System.out.println("Outils ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Outils outils) {
        String req = "UPDATE outils set nom = ?, description = ?,prix = ?,ressources = ?,stock = ?,etat = ? where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(7, outils.getId());
            pst.setString(1, outils.getNom());
            pst.setString(2, outils.getDescription());
            pst.setDouble(3, outils.getPrix());
            pst.setString(4, outils.getRessources());
            pst.setString(5, outils.getStock());
            pst.setString(6, outils.getEtat().name());
            pst.executeUpdate();
            System.out.println("Outils modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Outils outils) {
        String req = "DELETE from outils where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, outils.getId());
            pst.executeUpdate();
            System.out.println("Outils supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Outils> afficher() {
        List<Outils> outilss = new ArrayList<>();

        String req = "SELECT * FROM Outils";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id =rs.getInt("id");
                String nom = rs.getString("nom");
                String description = rs.getString("description");
                double prix = rs.getDouble("prix");
                String ressources = rs.getString("ressources");
                String stock = rs.getString("stock");

                EtatOutils etat = EtatOutils.valueOf(rs.getString("etat"));


                outilss.add(new Outils(id, nom, description, prix, ressources, stock,etat));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return outilss;
    }

}
