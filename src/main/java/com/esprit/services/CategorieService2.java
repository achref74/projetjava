package com.esprit.services;

import com.esprit.models.Categorie;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        String req = "UPDATE categorie SET nom = ?, description = ? WHERE id = ?;";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setString(1, categorie.getNom());
            pst.setString(2, categorie.getDescription());
            pst.setInt(3, categorie.getId());
            pst.executeUpdate();
            System.out.println("Categorie modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Categorie categorie) {
        String req = "DELETE FROM categorie WHERE id = ?;";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, categorie.getId());
            pst.executeUpdate();
            System.out.println("Categorie supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Categorie> afficher() {
        List<Categorie> categories = new ArrayList<>();
        String req = "SELECT * FROM categorie;";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    String description = rs.getString("description");
                    categories.add(new Categorie(id, nom, description));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }
}