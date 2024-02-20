package com.esprit.services;
import com.esprit.models.outil;

import com.esprit.utils.DataSource;
import java.sql.*;
import java.util.*;

public class OutilsService2 implements IService<outil> {

    private Connection connection;

    public OutilsService2() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(outil outils) {
        String req = "INSERT into `outil` (nom, description,prix,ressources,stock,etat,idCategorie) values (?,?,?,?,?,?,?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, outils.getNom());
            pst.setString(2, outils.getDescription());
            pst.setDouble(3, outils.getPrix());
            pst.setString(4, outils.getRessources());
            pst.setString(5, outils.getStock());
            pst.setString(6, outils.getEtat());
            pst.setInt(7,outils.getCategorie().getIdcategorie());
            pst.executeUpdate();
            System.out.println("Outils ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(outil outils) {
        String req = "UPDATE outil set nom = ?, description = ?,prix = ?,ressources = ?,stock = ?,etat = ?,idCategorie=? where idoutils = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(8, outils.getIdoutils());
            pst.setString(1, outils.getNom());
            pst.setString(2, outils.getDescription());
            pst.setDouble(3, outils.getPrix());
            pst.setString(4, outils.getRessources());
            pst.setString(5, outils.getStock());
            pst.setString(6, outils.getEtat());
            pst.setInt(7, outils.getCategorie().getIdcategorie());
            pst.executeUpdate();
            System.out.println("Outils modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE from outil where idoutils = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Outils supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public outil getOneById(int id) {
        String req = "SELECT  `nom`, `description`, `prix`, `ressources`,`stock`,`etat` FROM outil WHERE idoutils = ?";

        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("nom");
                String description = rs.getString("description");
                double prix = rs.getDouble("prix");
                String ressources = rs.getString("ressources");
                String stock = rs.getString("stock");
                String etat = rs.getString("etat");

                return new outil(id, nom, description, prix, ressources, stock, etat);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null; // Return null if no matching record found
    }

    @Override
    public Set<outil> getAll() {
        Set<outil> outilss = new HashSet<>();

        String req = "SELECT * FROM outil";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int idoutils =rs.getInt("idoutils");
                String nom = rs.getString("nom");
                String description = rs.getString("description");
                double prix = rs.getDouble("prix");
                String ressources = rs.getString("ressources");
                String stock = rs.getString("stock");
                String etat = rs.getString("etat");




                outilss.add(new outil(idoutils, nom, description, prix, ressources, stock,etat));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return outilss;
    }



}
