package edu.esprit.services;

import edu.esprit.entities.Commentaire;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceCommentaire implements IService<Commentaire>{
    Connection cnx = DataSource.getInstance().getCnx();
    public void ajouter(Commentaire commentaire) {
        String req = "INSERT INTO `commentaire`(`dateCreation`, `description`, `contenu`, `idForum`) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setObject(1, Date.valueOf(commentaire.getDateCreation())); // Utilisez setObject pour LocalDate
            ps.setString(2, commentaire.getDescription());
            ps.setString(3, commentaire.getContenu());
            ps.setInt(4, commentaire.getIdForum());
            ps.executeUpdate();
            System.out.println("Commentaire ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifier(Commentaire commentaire) throws SQLException {
        String req = "UPDATE commentaire SET description = ?, contenu = ?, idForum = ? WHERE idCommentaire = ?";
        PreparedStatement  prepardstatement = cnx.prepareStatement(req);
        prepardstatement.setString(1, commentaire.getDescription());
        prepardstatement.setString(2, commentaire.getContenu());
        prepardstatement.setInt(3, commentaire.getIdForum());
        prepardstatement.setInt(4, commentaire.getIdCommentaire());
       // prepardstatement.setInt(5, commentaire.getIdCommentaire());
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
                String description = res.getString("description");
                String contenu = res.getString("contenu");
                LocalDate dateCreation = res.getDate("dateCreation").toLocalDate();
                int idForum = res.getInt("idForum");
                commentaire = new Commentaire(dateCreation, description, idForum, contenu);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return commentaire;
    }
    public List<Commentaire> recuperer() throws SQLException {
        String req = "select * from commentaire";
        Statement statement = cnx.createStatement();

        ResultSet cs = statement.executeQuery(req);
        List<Commentaire> list = new ArrayList<>();
        while (cs.next()) {
            Commentaire c = new Commentaire();
            c.setIdCommentaire(cs.getInt("idCommentaire"));
            c.setContenu(cs.getString("Contenu"));
            c.setDescription(cs.getString("description"));
            c.setDateCreation(cs.getDate("dateCreation").toLocalDate());
            c.setIdForum(cs.getInt("idForum"));


            list.add(c);
        }
        return list;
    }
}
