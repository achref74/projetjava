package com.esprit.services;

import com.esprit.models.Achat;
import com.esprit.models.Categorie;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CategorieService2 implements IService<Categorie> {

    private Connection connection;

    public CategorieService2() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Categorie categorie) {
        String req = "INSERT INTO categorie(nom, description) VALUES (?, ?);";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setString(1, categorie.getNom());
            pst.setString(2, categorie.getDescription());
            pst.executeUpdate();
            System.out.println("Categorie ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Categorie categorie) {
        String req = "UPDATE categorie SET nom = ?, description = ? WHERE idcategorie = ?;";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setString(1, categorie.getNom());
            pst.setString(2, categorie.getDescription());
            pst.setInt(3, categorie.getIdcategorie());
            pst.executeUpdate();
            System.out.println("Categorie modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM categorie WHERE idcategorie = ?;";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1,id);
            pst.executeUpdate();
            System.out.println("Categorie supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Categorie getOneById(int id) {
        String req = "SELECT `idcategorie`, `nom`, `description` FROM `Categorie` WHERE idcategorie = ?";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, id); // Set the parameter value
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                int idcategorie = res.getInt("idcategorie");
                String nom = res.getString("nom");
                String description = res.getString("description");
                return new Categorie(idcategorie,nom,description);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Categorie by id: " + e.getMessage());
        }
        return null;
    }

    @Override

    public List<Categorie> getAll() {
        List<Categorie> categories = new ArrayList<>();
        String req = "SELECT * FROM categorie;";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int idcategorie = rs.getInt("idcategorie");
                    String nom = rs.getString("nom");
                    String description = rs.getString("description");
                    categories.add(new Categorie(idcategorie, nom, description));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }
}