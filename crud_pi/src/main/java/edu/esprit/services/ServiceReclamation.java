package edu.esprit.services;

import edu.esprit.entites.*;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class ServiceReclamation implements IService<Reclamation> {
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Reclamation reclamation) {
        String req = "INSERT INTO `reclamation`( `id_user`, `id_outil`, `id_formation`, `description`, `date`) VALUES (?,?,?,?,?)";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            String formattedDate = reclamation.getDate_reclamation().format(formatter);
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,reclamation.getUser().getId_user());
            ps.setInt(2,reclamation.getOutil().getId_outil());
            ps.setInt(3,reclamation.getFormation().getId_formation());
            ps.setString(4,reclamation.getDescription());
            ps.setObject(5, formattedDate);
            ps.executeUpdate();
            System.out.println("reclamation added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier( Reclamation reclamation) {
        String req = "UPDATE `reclamation` SET `id_user`=?,`id_outil`=?,`id_formation`=?,`description`=? WHERE id_reclamation=?";

        try {
            // Using PreparedStatement to prevent SQL injection
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1,reclamation.getUser().getId_user());
            ps.setInt(2,reclamation.getOutil().getId_outil());
            ps.setInt(3,reclamation.getFormation().getId_formation());
            ps.setString(4,reclamation.getDescription());
            ps.setInt(5, reclamation.getId_reclamation());

            int rowCount = ps.executeUpdate();

            if (rowCount > 0) {
                System.out.println("Reclamation with id " + reclamation.getId_reclamation() + " has been updated successfully.");
            } else {
                System.out.println("No Reclamation found with id " + reclamation.getId_reclamation() + ". Nothing updated.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating Reclamation with id " + reclamation.getId_reclamation() + ": " + e.getMessage());
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
        String req = "SELECT `id_reclamation`, `id_user`, `id_outil`, `id_formation`, `description`,  `date` FROM `reclamation` WHERE id_reclamation = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
           // Set the parameter value
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                //int user = res.getInt("id_user");
               // int outil =res.getInt("id_outil");
               // int formation = res.getInt("id_formation");
                String description = res.getString("description");
                java.sql.Timestamp timestamp = res.getTimestamp("date");
                LocalDateTime date = timestamp.toLocalDateTime();
                Formation formation = new Formation();
                formation.setId_formation(res.getInt("id_formation"));
                User user = new User();
                user.setId_user(res.getInt("id_user"));
                Outil outil = new Outil();
                outil.setId_outil(res.getInt("id_outil"));
                return new Reclamation(id,user,outil,formation,description,date);
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
                Formation formation = new Formation();
                formation.setId_formation(res.getInt("id_formation"));
                User user = new User();
                user.setId_user(res.getInt("id_user"));
                Outil outil = new Outil();
                outil.setId_outil(res.getInt("id_outil"));
                String description = res.getString("description");
                java.sql.Timestamp timestamp = res.getTimestamp("date");
                LocalDateTime date = timestamp.toLocalDateTime();
                Reclamation r = new Reclamation(id,user,outil,formation,description,date);
                Reclamations.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Reclamations;
    }
}
