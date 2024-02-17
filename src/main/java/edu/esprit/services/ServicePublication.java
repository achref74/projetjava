package edu.esprit.services;

import edu.esprit.entities.Forum;
import edu.esprit.entities.Publication;
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
        String req = "INSERT INTO `publication`(`dateCreation`, `contenu`,`image`, `idForum`,`idUser`) VALUES (?, ?, ?, ?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            String formattedDate = publication.getDateCreation().format(formatter);
            ps.setObject(1, formattedDate);// Utilisez setObject pour LocalDate
            ps.setString(2, publication.getContenu());
            ps.setString(3, publication.getImage());
            ps.setInt(4, publication.getIdForum());
            ps.setInt(5, publication.getIdUser());
            ps.executeUpdate();
            System.out.println("publiction ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Publication publication) throws SQLException {
        String req = "UPDATE publication SET contenu = ?, image = ?,nbLike=?,idForum=?,idUser=? WHERE idP = ?";
        PreparedStatement  prepardstatement = cnx.prepareStatement(req);
        prepardstatement.setString(1, publication.getContenu());
        prepardstatement.setString(2, publication.getImage());

        prepardstatement.setInt(3, publication.getNbLike());
        prepardstatement.setInt(4,publication.getIdForum() );
        prepardstatement.setInt(5, publication.getIdUser());

        prepardstatement.setInt(6, publication.getIdP());

        prepardstatement.executeUpdate();
        System.out.println("publiction modifié");

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
        String req = "SELECT * FROM publication WHERE idP = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String contenu = res.getString("contenu");
                String image = res.getString("image");
                int nbLike = res.getInt("nbLike");
                int idForum = res.getInt("idForum");
                int idUser = res.getInt("idUser");
                java.sql.Timestamp timestamp = res.getTimestamp("dateCreation");
                LocalDateTime dateCreation = timestamp.toLocalDateTime();

                publication = new Publication(dateCreation,contenu,image,nbLike,idForum,idUser);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return publication;
    }

    @Override
    public List<Publication> getAll() throws SQLException {
        String req = "select * from publication";
        Statement statement = cnx.createStatement();

        ResultSet cs = statement.executeQuery(req);
        List<Publication> list = new ArrayList<>();
        while (cs.next()) {
            Publication publication = new Publication();
            publication.setContenu(cs.getString("contenu"));
            publication.setImage(cs.getString("image"));
            java.sql.Timestamp timestamp = cs.getTimestamp("dateCreation");
            LocalDateTime dateCreation = timestamp.toLocalDateTime();
            publication.setDateCreation(dateCreation);
            publication.setNbLike(cs.getInt("nbLike"));
            publication.setIdUser(cs.getInt("idUser"));
            publication.setIdForum(cs.getInt("idForum"));




            list.add(publication);
        }
        return list;
    }
}
