package edu.esprit.services;

import edu.esprit.entities.Avis;
import edu.esprit.entities.Commentaire;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ServiceAvis implements IService<Avis> {
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Avis avis) {
        String req = "INSERT INTO `avis`( `rate`, `idUser`, `idFormation`) VALUES (?, ?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, avis.getRate());
            ps.setInt(2, avis.getIdUser());
            ps.setInt(3, avis.getIdFormation());
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
        prepardstatement.setInt(2, avis.getIdUser());
        prepardstatement.setInt(3, avis.getIdFormation());
        prepardstatement.setInt(4, avis.getIdAvis());
        prepardstatement.executeUpdate();
        System.out.println("commentaire modifié");

    }

    @Override
    public void supprimer(int id) {
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
    public Avis getOneById(int id) {
        Avis avis = null;
        String req = "SELECT a.*, u.nom,f.nomF FROM avis a INNER JOIN user u ON a.idUser = u.idUser INNER JOIN formation f ON a.idFormation = f.idFormation WHERE a.idAvis = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
               int rate = res.getInt("rate");
               String nomF = res.getString("nomF");
                String nomUser = res.getString("nom");
                 avis = new Avis(rate,nomF,nomUser);
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
            a.setNomF(cs.getString("nomF"));
            a.setNomUser(cs.getString("nom"));
            a.setRate(cs.getInt("rate"));
            list.add(a);
        }
        return list;
    }
}
