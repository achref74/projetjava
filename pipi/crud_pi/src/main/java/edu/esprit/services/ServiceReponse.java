package edu.esprit.services;

import edu.esprit.entites.Achat;
import edu.esprit.entites.Reponse;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ServiceReponse implements IService<Reponse> {
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Reponse reponse) {
        String req = "INSERT INTO `reponse`( `description`, `date`) VALUES (?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1,reponse.getDescription());
            ps.setDate(2,Date.valueOf(reponse.getDate()));

            ps.executeUpdate();
            System.out.println("Reponse added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifier(int id, Reponse reponse) {
        String req = "UPDATE `reponse` SET `description`=?,`date`=? WHERE id_reponse=?";

        try {
            // Using PreparedStatement to prevent SQL injection
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1,reponse.getDescription());
            ps.setDate(2,Date.valueOf(reponse.getDate()));
            ps.setInt(3, id);

            int rowCount = ps.executeUpdate();

            if (rowCount > 0) {
                System.out.println("reponse with id " + id + " has been updated successfully.");
            } else {
                System.out.println("No reponse found with id " + id + ". Nothing updated.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating Achat with id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `reponse` WHERE  id_reponse = ?";

        try {
            // Using PreparedStatement to prevent SQL injection
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);

            int rowCount = ps.executeUpdate();

            if (rowCount > 0) {
                System.out.println("reponse with id " + id + " has been deleted successfully.");
            } else {
                System.out.println("No reponse found with id " + id + ". Nothing deleted.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting reponse with id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public Reponse getOneById(int id) {
        String req = "SELECT `id_reponse`, `description`, `date` FROM `reponse` WHERE id_reponse = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id); // Set the parameter value
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String description = res.getString("description");
                LocalDate date = res.getDate("date").toLocalDate();
                return new Reponse(id,description,date);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Reponse by id: " + e.getMessage());
        }
        return null; // Achat not found
    }

    @Override
    public Set<Reponse> getAll() {
        Set<Reponse> Reponses = new HashSet<>();

        String req = "SELECT * FROM `reponse` WHERE 1";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt("id_reponse");
                String  description = res.getString("description");
                LocalDate date = res.getDate("date").toLocalDate();
                Reponse r = new Reponse(id,description,date);
                Reponses.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Reponses;
    }

}
