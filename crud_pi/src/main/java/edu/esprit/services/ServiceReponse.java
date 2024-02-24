package edu.esprit.services;

import edu.esprit.entites.Reclamation;
import edu.esprit.entites.Reponse;
import edu.esprit.entites.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class ServiceReponse implements IService<Reponse> {
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Reponse reponse) {
        String req = "INSERT INTO `reponse`(`id_user`, `description`,`id_reclamation`, `date_reponse`) VALUES (?,?,?,?)";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            String formattedDate = reponse.getDate().format(formatter);
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,reponse.getUser().getId_user());
            ps.setString(2,reponse.getDescription());
            ps.setInt(3,reponse.getReclamation().getId_reclamation());
            ps.setObject(4,formattedDate);
            ps.executeUpdate();
            System.out.println("Reponse added !");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifier(Reponse reponse) {
        String req = "UPDATE `reponse` SET `id_user`=?,`description`=? WHERE id_reponse=?";

        try {
            // Using PreparedStatement to prevent SQL injection
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,reponse.getUser().getId_user());
            ps.setString(2,reponse.getDescription());
           // ps.setDate(2,Date.valueOf(reponse.getDate()));
            ps.setInt(3, reponse.getId_reponse());

            int rowCount = ps.executeUpdate();

            if (rowCount > 0) {
                System.out.println("reponse with id " + reponse.getId_reponse()+ " has been updated successfully.");
            } else {
                System.out.println("No reponse found with id " + reponse.getId_reponse() + ". Nothing updated.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating Achat with id " + reponse.getId_reponse() + ": " + e.getMessage());
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
        String req = "SELECT `id_reponse`,`id_user`, `description`,`id_reclamation`, `date_reponse` FROM `reponse` WHERE id_reponse = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id); // Set the parameter value
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String description = res.getString("description");
                java.sql.Timestamp timestamp = res.getTimestamp("date_reponse");
                LocalDateTime date = timestamp.toLocalDateTime();
                Reclamation reclamation=new Reclamation();
                reclamation.setId_reclamation(res.getInt("id_reclamation"));
                User user=new User();
                user.setId_user(res.getInt("id_user"));
                return new Reponse(id,user,description,reclamation,date);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Reponse by id: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Set<Reponse> getAll() {
        Set<Reponse> Reponses = new HashSet<>();

        String req = "SELECT * FROM `reponse` WHERE 1";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                java.sql.Timestamp timestamp = res.getTimestamp("date_reponse");
                LocalDateTime date = timestamp.toLocalDateTime();
                int id = res.getInt("id_reponse");
                String  description = res.getString("description");
                Reclamation reclamation=new Reclamation();
                reclamation.setId_reclamation(res.getInt("id_reclamation"));
                User user=new User();
                user.setId_user(res.getInt("id_user"));
                Reponse r = new Reponse(id,user,description,reclamation,date);
                Reponses.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Reponses;
    }
    public Set<Reponse> getReponseByReclamationId(int id_reclamation) {
        Set<Reponse> Reponses = new HashSet<>();

        String req = "SELECT * FROM `reponse` WHERE id_reclamation = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id_reclamation);
            ResultSet res = pst.executeQuery();

            while (res.next()) {
                java.sql.Timestamp timestamp = res.getTimestamp("date_reponse");
                LocalDateTime date = timestamp.toLocalDateTime();
                int id = res.getInt("id_reponse");
                String description = res.getString("description");
                Reclamation reclamation = new Reclamation();
                reclamation.setId_reclamation(res.getInt("id_reclamation"));
                User user = new User();
                user.setId_user(res.getInt("id_user"));
                Reponse r = new Reponse(id, user, description, reclamation, date);
                Reponses.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Reponses;
    }



}
