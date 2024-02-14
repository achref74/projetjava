package edu.esprit.services;

import edu.esprit.entites.Reclamation;
import edu.esprit.entites.Reponse;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ServiceReclamation implements IService<Reclamation> {
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Reclamation reclamation) {
        String req = "INSERT INTO `reclamation`( `id_user`, `id_outil`, `id_formation`, `description`, `id_reponse`, `date`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,reclamation.getId_user());
            ps.setInt(2,reclamation.getId_outil());
            ps.setInt(3,reclamation.getId_formation());
            ps.setString(4,reclamation.getDescription());
            ps.setInt(5,reclamation.getReponse());
            ps.setDate(6, Date.valueOf(reclamation.getDate_reclamation()));
            ps.executeUpdate();
            System.out.println("reclamation added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(int id, Reclamation reclamation) {
        String req = "UPDATE `reclamation` SET `id_user`=?,`id_outil`=?,`id_formation`=?,`description`=?,`id_reponse`=? WHERE id_reclamation=?";

        try {
            // Using PreparedStatement to prevent SQL injection
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1,reclamation.getId_user());
            ps.setInt(2,reclamation.getId_outil());
            ps.setInt(3,reclamation.getId_formation());
            ps.setString(4,reclamation.getDescription());
            ps.setInt(5,reclamation.getReponse());


            ps.setInt(6, id);

            int rowCount = ps.executeUpdate();

            if (rowCount > 0) {
                System.out.println("Reclamation with id " + id + " has been updated successfully.");
            } else {
                System.out.println("No Reclamation found with id " + id + ". Nothing updated.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating Reclamation with id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `reclamation` WHERE  id_reclamation = ?";

        try {
            // Using PreparedStatement to prevent SQL injection
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);

            int rowCount = ps.executeUpdate();

            if (rowCount > 0) {
                System.out.println("reclamation with id " + id + " has been deleted successfully.");
            } else {
                System.out.println("No reclamation found with id " + id + ". Nothing deleted.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting reclamation with id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public Reclamation getOneById(int id) {
        String req = "SELECT `id_reclamation`, `id_user`, `id_outil`, `id_formation`, `description`, `id_reponse`, `date` FROM `reclamation` WHERE id_reclamation = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
           // Set the parameter value
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                int user = res.getInt("id_user");
                int outil =res.getInt("id_outil");
                int formation = res.getInt("id_formation");
                String description = res.getString("description");
                int reponse =res.getInt("id_reponse");
                LocalDate date = res.getDate("date").toLocalDate();
                return new Reclamation(id,user,outil,formation,description,reponse,date);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Reclamation by id: " + e.getMessage());
        }
        return null; // Achat not found
    }

    @Override
    public Set<Reclamation> getAll() {
        Set<Reclamation> Reclamations = new HashSet<>();

        String req = "SELECT * FROM `reclamation` WHERE 1";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt("id_reclamation");
                int user = res.getInt("id_user");
                int outil =res.getInt("id_outil");
                int formation = res.getInt("id_formation");
                String description = res.getString("description");
                int reponse =res.getInt("id_reponse");
                LocalDate date = res.getDate("date").toLocalDate();
                Reclamation r = new Reclamation(id,user,outil,formation,description,reponse,date);
                Reclamations.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Reclamations;
    }
}
