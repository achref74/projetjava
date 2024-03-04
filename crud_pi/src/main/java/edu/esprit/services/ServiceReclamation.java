package edu.esprit.services;

import edu.esprit.entites.*;
import edu.esprit.utils.DataSource;

import javax.lang.model.type.NullType;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class ServiceReclamation implements IService<Reclamation> {
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Reclamation reclamation) throws SQLException{
        String req = "INSERT INTO `reclamation`( `id_user`, `id_outil`, `id_formation`, `description`, `date`) VALUES (?,?,?,?,?)";

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            String formattedDate = reclamation.getDate_reclamation().format(formatter);
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,reclamation.getUser().getId_user());
           // ps.setInt(2,reclamation.getOutil().getId_outil());
           // ps.setInt(3,reclamation.getFormation().getId_formation());
            ps.setString(4,reclamation.getDescription());
            ps.setObject(5, formattedDate);
        if (reclamation.getOutil().getId_outil() != -1) {
            ps.setInt(2, reclamation.getOutil().getId_outil());
        } else {
            ps.setInt(2, -1);
        }

// Assuming -1 is a sentinel value indicating an unset state
        if (reclamation.getFormation().getId_formation() != -1) {
            ps.setInt(3, reclamation.getFormation().getId_formation());
        } else {
            ps.setObject(3, -1);
        }
            ps.executeUpdate();
            System.out.println("reclamation added !");
    }

    @Override
    public void modifier( Reclamation reclamation) throws SQLException{
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
                System.out.println(" id " + reclamation.getId_reclamation() + " has been updated successfully.");
            } else {
                System.out.println("No edu.esprit.controllers.Reclamation found with id " + reclamation.getId_reclamation() + ". Nothing updated.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating edu.esprit.controllers.Reclamation with id " + reclamation.getId_reclamation() + ": " + e.getMessage());
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
        String reqq="SELECT \n" +
                "    r.id_reclamation, \n" +
                "    u.nom AS user_nom,\n" +
                "    o.nom AS outil_nom,\n" +
                "    f.nom AS formation_nom,\n" +
                "    r.description,\n" +
                "    r.date\n" +
                "FROM \n" +
                "    reclamation r\n" +
                "JOIN \n" +
                "    user u ON r.id_user = u.idUser\n" +
                "JOIN \n" +
                "    outil o ON r.id_outil = o.id_outil\n" +
                "JOIN \n" +
                "    formation f ON r.id_formation = f.id_formation\n" +
                "WHERE \n" +
                "    r.id_reclamation = ?;";
        try (PreparedStatement ps = cnx.prepareStatement(reqq)) {
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
                formation.setNom(res.getString("nom"));
                // retrieve
                User user = new User();
                user.setNom(res.getString("nom"));
                user.setId_user(res.getInt("id_user"));
                Outil outil = new Outil();
                outil.setId_outil(res.getInt("id_outil"));
                outil.setNom(res.getString("nom"));
                return new Reclamation(id,user,outil,formation,description,date);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Reclamation by id: " + e.getMessage());
        }
        return null; // Achat not found
    }

    @Override
   public Set<Reclamation> getAll() throws SQLException{
        Set<Reclamation> Reclamations = new HashSet<>();

       // String req = "SELECT * FROM `reclamation` WHERE 1";
        String reqq = "SELECT  r.id_reclamation, u.nom AS user_nom,o.nom AS outil_nom,f.nom AS formation_nom, r.description, r.date FROM reclamation r JOIN  user u ON r.id_user = u.idUser JOIN  outil o ON r.id_outil = o.idoutils JOIN  formation f ON r.id_formation = f.idFormation;";
        String rec="SELECT r.id_reclamation, u.nom AS user_nom, u.idUser AS user_id, o.nom AS outil_nom, o.idoutils AS outil_id, f.nom AS formation_nom, f.idFormation AS formation_id, r.description, r.date FROM reclamation r JOIN user u ON r.id_user = u.idUser JOIN outil o ON r.id_outil = o.idoutils JOIN formation f ON r.id_formation = f.idFormation;";
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(rec);
            while (res.next()){
                int id = res.getInt("id_reclamation");
                Formation formation = new Formation();
                formation.setId_formation(res.getInt("formation_id"));
                formation.setNom(res.getString("formation_nom"));
                User user = new User();
                user.setId_user(res.getInt("user_id"));
                user.setNom(res.getString("user_nom"));
                Outil outil = new Outil();
                outil.setId_outil(res.getInt("outil_id"));
                outil.setNom(res.getString("outil_nom"));
                String description = res.getString("description");
                java.sql.Timestamp timestamp = res.getTimestamp("date");
                LocalDateTime date = timestamp.toLocalDateTime();
                Reclamation r = new Reclamation(id,user,outil,formation,description,date);
                Reclamations.add(r);
            }

        return Reclamations;
    }
    /*public Set<Reclamation> getAll() throws SQLException{
        Set<Reclamation> Reclamations = new HashSet<>();

        // String req = "SELECT * FROM `reclamation` WHERE 1";
       // String reqq = "SELECT  r.id_reclamation, u.nom AS user_nom,o.nom AS outil_nom,f.nom AS formation_nom, r.description, r.date FROM reclamation r JOIN  user u ON r.id_user = u.idUser JOIN  outil o ON r.id_outil = o.idoutils JOIN  formation f ON r.id_formation = f.idFormation;";
        String rec="SELECT r.id_reclamation, u.nom AS user_nom, u.idUser AS user_id, o.nom AS outil_nom, o.idoutils AS outil_id, f.nom AS formation_nom, f.idFormation AS formation_id, r.description, r.date FROM reclamation r JOIN user u ON r.id_user = u.idUser JOIN outil o ON r.id_outil = o.idoutils JOIN formation f ON r.id_formation = f.idFormation;";
        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(rec);
        while (res.next()){
            int id = res.getInt("id_reclamation");


            User user = new User();
            user.setId_user(res.getInt("user_id"));
            user.setNom(res.getString("user_nom"));
            Outil outil = new Outil();
            int outilId = res.getInt("outil_id");
            if (res.getInt("outil_id")== -1 ) {
                outil.setId_outil(-1);
                outil.setNom("");  // Set outil to null if the value is NULL in the database
            } else {
                outil.setId_outil(outilId);
                outil.setNom(res.getString("outil_nom"));
            }

            Formation formation = new Formation();
            int formationId = res.getInt("formation_id");
            if (res.getInt("formation_id")==-1) {
                formation.setId_formation(-1);
                formation.setNom("");  // Set formation to null if the value is NULL in the database
            } else {
                formation.setId_formation(formationId);
                formation.setNom(res.getString("formation_nom"));
            }


            String description = res.getString("description");
            java.sql.Timestamp timestamp = res.getTimestamp("date");
            LocalDateTime date = timestamp.toLocalDateTime();
            Reclamation r = new Reclamation(id,user,outil,formation,description,date);
            Reclamations.add(r);
        }

        return Reclamations;
    }*/


}
