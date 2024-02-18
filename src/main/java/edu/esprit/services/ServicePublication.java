package edu.esprit.services;

import edu.esprit.entities.Forum;
import edu.esprit.entities.Publication;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ServicePublication implements IService<Publication> {
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Publication publication) {
        if (publication.getContenu() == null || publication.getContenu().isEmpty()) {
            System.out.println("Le contenu de la publication est obligatoire.");
        }else {
        String req = "INSERT INTO `publication`(`dateCreation`, `contenuP`,`image`, `idForum`,`idUser`) VALUES (?, ?, ?, ?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            String formattedDate = publication.getDateCreation().format(formatter);
            ps.setObject(1, formattedDate);// Utilisez setObject pour LocalDate
            ps.setString(2, publication.getContenu());
            ps.setString(3, publication.getImage());
            ps.setInt(4, publication.getForum().getIdForum());
            ps.setInt(5, publication.getUser().getIdUser());
            ps.executeUpdate();
            System.out.println("publiction ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }}
    }

    @Override
    public void modifier(Publication publication) throws SQLException {
        if (publication.getContenu() == null || publication.getContenu().isEmpty()) {
            System.out.println("Le contenu de la publication est obligatoire.");
        }else {
        String req = "UPDATE publication SET contenuP = ?, image = ?,nbLike=?,idForum=?,idUser=? WHERE idP = ?";
        PreparedStatement  prepardstatement = cnx.prepareStatement(req);
        prepardstatement.setString(1, publication.getContenu());
        prepardstatement.setString(2, publication.getImage());

        prepardstatement.setInt(3, publication.getNbLike());
        prepardstatement.setInt(4,publication.getForum().getIdForum() );
        prepardstatement.setInt(5, publication.getUser().getIdUser());

        prepardstatement.setInt(6, publication.getIdP());

        prepardstatement.executeUpdate();
        System.out.println("publiction modifié");}

    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM publication WHERE idP = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("publication supprimé !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Publication getOneById(int id) {
        Publication publication = null;
        String req = "SELECT a.*, u.nom,f.titre FROM publication a INNER JOIN user u ON a.idUser = u.idUser INNER JOIN forum f " +
                "ON a.idForum = f.idForum WHERE a.idP = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            User user=new User();
            Forum forum=new Forum();
            if (res.next()) {
                String contenu = res.getString("contenuP");
                String image = res.getString("image");
                int nbLike = res.getInt("nbLike");
                user.setNom(res.getString("nom"));
                forum.setTitre(res.getString("titre"));
                java.sql.Timestamp timestamp = res.getTimestamp("dateCreation");
                LocalDateTime dateCreation = timestamp.toLocalDateTime();

                publication = new Publication(dateCreation,contenu,image,nbLike,forum,user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return publication;
    }

    @Override
    public List<Publication> getAll() throws SQLException {
        String req = "SELECT a.*, u.nom,f.titre FROM publication a INNER JOIN user u ON a.idUser = u.idUser INNER JOIN forum f ON a.idForum = f.idForum";
        Statement statement = cnx.createStatement();

        ResultSet cs = statement.executeQuery(req);
        List<Publication> list = new ArrayList<>();
        while (cs.next()) {
            Publication publication = new Publication();
            publication.setContenu(cs.getString("contenuP"));
            publication.setImage(cs.getString("image"));
            java.sql.Timestamp timestamp = cs.getTimestamp("dateCreation");
            LocalDateTime dateCreation = timestamp.toLocalDateTime();
            publication.setDateCreation(dateCreation);
            publication.setNbLike(cs.getInt("nbLike"));
            User user=new User();
            user.setNom(cs.getString("nom"));
            publication.setUser(user);
            Forum forum=new Forum();
            forum.setTitre(cs.getString("titre"));
            publication.setForum(forum);




            list.add(publication);
        }
        return list;
    }
}
