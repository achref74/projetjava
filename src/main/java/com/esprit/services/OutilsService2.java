package com.esprit.services;
import com.esprit.models.Categorie;
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
        String req = "INSERT into `outil` (nom, description,prix,ressources,stock,etat,idCategorie,image) values (?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, outils.getNom());
            pst.setString(2, outils.getDescription());
            pst.setDouble(3, outils.getPrix());
            pst.setString(4, outils.getRessources());
            pst.setString(5, outils.getStock());
            pst.setString(6, outils.getEtat());
            pst.setInt(7,outils.getCategorie().getIdcategorie());
            pst.setString(8, outils.getImage());
            pst.executeUpdate();
            System.out.println("Outils ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(outil outils) {
        String req = "UPDATE outil set nom = ?, description = ?,prix = ?,ressources = ?,stock = ?,etat = ?,idCategorie=?,image =? where idoutils = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(9, outils.getIdoutils());
            pst.setString(1, outils.getNom());
            pst.setString(2, outils.getDescription());
            pst.setDouble(3, outils.getPrix());
            pst.setString(4, outils.getRessources());
            pst.setString(5, outils.getStock());
            pst.setString(6, outils.getEtat());
            pst.setInt(7, outils.getCategorie().getIdcategorie());
            pst.setString(8, outils.getImage());
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
        String req = "SELECT  `nom`, `description`, `prix`, `ressources`,`stock`,`etat`,`image` FROM outil WHERE idoutils = ?";

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
                String image = rs.getString("image");

                return new outil(id, nom, description, prix, ressources, stock, etat,image);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null; // Return null if no matching record found
    }

    @Override
    public List<outil> getAll() {
        List<outil> outilss = new ArrayList<>();

        String req = "SELECT o.*, c.idCategorie as cat_id, c.nom as cat_nom, c.description as cat_description FROM outil o JOIN categorie c ON o.idCategorie = c.idCategorie";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int idoutils = rs.getInt("idoutils");
                String nom = rs.getString("nom");
                String description = rs.getString("description");
                double prix = rs.getDouble("prix");
                String ressources = rs.getString("ressources");
                String stock = rs.getString("stock");
                String etat = rs.getString("etat");
                String image = rs.getString("image");
                int catId = rs.getInt("cat_id");
                String catNom = rs.getString("cat_nom");
                String catDescription = rs.getString("cat_description");
                Categorie categorie = new Categorie(catId, catNom, catDescription);

                outilss.add(new outil(idoutils, nom, description, prix, ressources, stock, etat, categorie, image));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return outilss;
    }




}
