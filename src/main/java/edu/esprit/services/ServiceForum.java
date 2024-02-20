package edu.esprit.services;

import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Formation;
import edu.esprit.entities.Forum;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceForum implements IService<Forum> {
    Connection cnx = DataSource.getInstance().getCnx();
    public void ajouter(Forum forum){

        String req = "INSERT INTO `forum`(`titre`,`dateCreation`, `description`, `idFormation`) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, forum.getTitre());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            String formattedDate = forum.getDateCreation().format(formatter);
            ps.setObject(2, formattedDate);
            ps.setString(3, forum.getDescription());
            ps.setInt(4, forum.getFormation().getIdFormation());
            ps.executeUpdate();
            System.out.println("forum ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void modifier(Forum forum) throws SQLException {
        String req = "UPDATE forum SET titre = ?, description = ?,idFormation=? WHERE idForum = ?";
        PreparedStatement  prepardstatement = cnx.prepareStatement(req);
        prepardstatement.setString(1, forum.getTitre());
        prepardstatement.setString(2, forum.getDescription());

        prepardstatement.setInt(3, forum.getFormation().getIdFormation());

        prepardstatement.setInt(4, forum.getIdForum());

        // prepardstatement.setInt(5, commentaire.getIdCommentaire());
        prepardstatement.executeUpdate();
        System.out.println("forum modifié");
    }
    public void supprimer(int id){ String req = "DELETE FROM forum WHERE idforum = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("forum supprimé !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }}
    public Forum getOneById(int id){
        Forum forum = null;
        String req = "SELECT a.*,f.nomF FROM forum a  INNER JOIN formation f ON a.idFormation = f.idFormation WHERE a.idForum = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            Formation formation=new Formation();
            if (res.next()) {
                String titre = res.getString("titre");
                String description = res.getString("description");
                formation.setNomF(res.getString("nomF")) ;
                java.sql.Timestamp timestamp = res.getTimestamp("dateCreation");
                LocalDateTime dateCreation = timestamp.toLocalDateTime();

                forum = new Forum(titre, dateCreation,  description,  formation);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return forum;}


    @Override
    public List<Forum> getAll() throws SQLException {
        String req = "SELECT a.*,f.nom FROM forum a  INNER JOIN formation f ON a.idFormation = f.idFormation";
        Statement statement = cnx.createStatement();

        ResultSet cs = statement.executeQuery(req);
        List<Forum> list = new ArrayList<>();
        while (cs.next()) {
            Forum forum = new Forum();
            forum.setTitre(cs.getString("titre"));
            forum.setDescription(cs.getString("description"));
            java.sql.Timestamp timestamp = cs.getTimestamp("dateCreation");
            LocalDateTime dateCreation = timestamp.toLocalDateTime();
            forum.setDateCreation(dateCreation);
            Formation formation=new Formation();
            formation.setNomF(cs.getString("nom"));
            forum.setFormation(formation);




            list.add(forum);
        }
        return list;
    }
}
