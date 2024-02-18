package edu.esprit.services;

import edu.esprit.entities.Avis;
import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Formation;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ServiceAvis implements IService<Avis> {
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Avis avis)throws SQLException {
        String req = "INSERT INTO `avis`( `rate`, `idUser`, `idFormation`) VALUES (?, ?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, avis.getRate());
            ps.setInt(2, avis.getUser().getIdUser());
            ps.setInt(3, avis.getFormation().getIdFormation());
            ps.executeUpdate();
            System.out.println("Avis ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Avis avis) throws SQLException {
        String req = "UPDATE avis SET  rate = ?, idUser = ?, idFormation = ? WHERE idAvis = ?";
        PreparedStatement  prepardstatement = cnx.prepareStatement(req);
        prepardstatement.setInt(1, avis.getRate());
        prepardstatement.setInt(2, avis.getUser().getIdUser());
        prepardstatement.setInt(3, avis.getFormation().getIdFormation());
        prepardstatement.setInt(4, avis.getIdAvis());
        prepardstatement.executeUpdate();
        System.out.println("commentaire modifié");

    }

    @Override
    public void supprimer(int id)throws SQLException {
        String req = "DELETE FROM avis WHERE idAvis = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Avis supprimé !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Avis getOneById(int id)throws SQLException {
        Avis avis = null;
        String req = "SELECT a.*, u.nom,f.nomF FROM avis a INNER JOIN user u ON a.idUser = u.idUser INNER JOIN formation f ON a.idFormation = f.idFormation WHERE a.idAvis = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            User user=new User();
            Formation formation=new Formation();
            if (res.next()) {
               int rate = res.getInt("rate");
               user.setNom(res.getString("nom"));
                formation.setNomF(res.getString("nomF")) ;
                 avis = new Avis(rate,formation,user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return avis;
    }


    @Override
    public List<Avis> getAll() throws SQLException {
        String req = "SELECT a.*, u.nom,f.nomF FROM avis a INNER JOIN user u ON a.idUser = u.idUser INNER JOIN formation f ON a.idFormation = f.idFormation ";
        Statement statement = cnx.createStatement();

        ResultSet cs = statement.executeQuery(req);
        List<Avis> list = new ArrayList<>();
        while (cs.next()) {
            Avis a = new Avis();
            Formation formation=new Formation();
            formation.setNomF(cs.getString("nomF"));
            a.setFormation(formation);
            User user=new User();
            user.setNom(cs.getString("nom"));
            a.setUser(user);

            a.setRate(cs.getInt("rate"));
            list.add(a);
        }
        return list;
    }
}
