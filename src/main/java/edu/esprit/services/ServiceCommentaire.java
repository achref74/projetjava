package edu.esprit.services;

import edu.esprit.entities.Commentaire;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceCommentaire implements IService<Commentaire>{
    Connection cnx = DataSource.getInstance().getCnx();
    public void ajouter(Commentaire commentaire) {
        String req = "INSERT INTO `commentaire`(`dateCreation`, `contenu`, `idUser`, `idP`, `rating`) VALUES (?, ?, ?, ?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            String formattedDate = commentaire.getDateCreation().format(formatter);
            ps.setObject(1, formattedDate);
            ps.setString(2, commentaire.getContenu());
            ps.setInt(3, commentaire.getIdUser());
            ps.setInt(4, commentaire.getIdP());
            ps.setInt(5, commentaire.getRating());
            ps.executeUpdate();
            System.out.println("Commentaire ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifier(Commentaire commentaire) throws SQLException {
        String req = "UPDATE commentaire SET  contenu = ?, idUser = ?, idP = ?, rating = ? WHERE idCommentaire = ?";
        PreparedStatement  prepardstatement = cnx.prepareStatement(req);
        prepardstatement.setString(1, commentaire.getContenu());
        prepardstatement.setInt(2, commentaire.getIdUser());
        prepardstatement.setInt(3, commentaire.getIdP());
        prepardstatement.setInt(4, commentaire.getRating());
        prepardstatement.setInt(5, commentaire.getIdCommentaire());
        prepardstatement.executeUpdate();
        System.out.println("commentaire modifié");
    }
    public void supprimer(int id){
        String req = "DELETE FROM commentaire WHERE idCommentaire = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Commentaire supprimé !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public Commentaire getOneById(int id){
        Commentaire commentaire = null;
        String req = "SELECT * FROM commentaire WHERE idCommentaire = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                java.sql.Timestamp timestamp = res.getTimestamp("dateCreation");
                LocalDateTime dateCreation = timestamp.toLocalDateTime();
                String contenu = res.getString("contenu");
                int idUser = res.getInt("idUser");
                int idP = res.getInt("idP");
                int rating = res.getInt("rating");
                commentaire = new Commentaire(dateCreation,contenu,idUser,idP,rating);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return commentaire;
    }
    public List<Commentaire> getAll() throws SQLException {
        String req = "select * from commentaire";
        Statement statement = cnx.createStatement();

        ResultSet cs = statement.executeQuery(req);
        List<Commentaire> list = new ArrayList<>();
        while (cs.next()) {
            Commentaire c = new Commentaire();
            java.sql.Timestamp timestamp = cs.getTimestamp("dateCreation");
            LocalDateTime dateCreation = timestamp.toLocalDateTime();
            c.setDateCreation(dateCreation);
            c.setContenu(cs.getString("Contenu"));
            c.setIdUser(cs.getInt("idUser"));
            c.setIdP(cs.getInt("idP"));
            c.setRating(cs.getInt("rating"));
            list.add(c);
        }
        return list;
    }
}
