package edu.esprit.services;

import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Publication;
import edu.esprit.entities.User;
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
            ps.setInt(3, commentaire.getUser().getIdUser());
            ps.setInt(4, commentaire.getPublication().getIdP());
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
        prepardstatement.setInt(2, commentaire.getUser().getIdUser());
        prepardstatement.setInt(3, commentaire.getPublication().getIdP());
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
        String req = "SELECT a.*, u.nom,p.contenuP FROM commentaire a INNER JOIN user u ON a.idUser = u.idUser INNER JOIN publication p ON a.idP = p.idP WHERE a.idCommentaire = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            User user=new User();
            Publication publication=new Publication();
            if (res.next()) {
                java.sql.Timestamp timestamp = res.getTimestamp("dateCreation");
                LocalDateTime dateCreation = timestamp.toLocalDateTime();
                String contenu = res.getString("contenu");
                user.setNom(res.getString("nom"));
                publication.setContenu(res.getString("contenuP"));
                int rating = res.getInt("rating");
                commentaire = new Commentaire(dateCreation,contenu,user,publication,rating);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return commentaire;
    }
    public List<Commentaire> getAll() throws SQLException {
        String req = "SELECT a.*, u.nom,p.contenuP FROM commentaire a INNER JOIN user u ON a.idUser = u.idUser INNER JOIN publication p ON a.idP = p.idP";
        Statement statement = cnx.createStatement();

        ResultSet cs = statement.executeQuery(req);
        List<Commentaire> list = new ArrayList<>();

        while (cs.next()) {
            Commentaire c = new Commentaire();
            java.sql.Timestamp timestamp = cs.getTimestamp("dateCreation");
            LocalDateTime dateCreation = timestamp.toLocalDateTime();
            c.setDateCreation(dateCreation);
            c.setContenu(cs.getString("Contenu"));
            User user=new User();
            user.setNom(cs.getString("nom"));
            c.setUser(user);
            Publication publication=new Publication();
            publication.setContenu(cs.getString("contenuP"));
            c.setPublication(publication);
            c.setRating(cs.getInt("rating"));
            list.add(c);
        }
        return list;
    }
}
