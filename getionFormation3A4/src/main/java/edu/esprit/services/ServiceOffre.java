package edu.esprit.services;

import edu.esprit.entities.Offre;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceOffre implements IService<Offre> {

    private Connection cnx = DataSource.getInstance().getCnx();
     @Override
    public void ajouter(Offre offre) {
        String req = "INSERT INTO `offre`(`codePromo`, `prixOffre`, `description`, `dateD`, `dateF`) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, offre.getCodePromo());
            ps.setDouble(2, offre.getPrixOffre());
            ps.setString(3, offre.getDescription());
            ps.setDate(4, new java.sql.Date(offre.getDateD().getTime()));
            ps.setDate(5, new java.sql.Date(offre.getDateF().getTime()));
           // ps.setInt(6, outil.getIdFormation());
            ps.executeUpdate();
            System.out.println("Outil ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void modifier(Offre offre) {
        // Assurez-vous que la connexion à la base de données (cnx) est correctement établie

        String req = "UPDATE offre SET codePromo = ?, prixOffre = ?, description = ?, dateD = ?, dateF = ? WHERE idOffre = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, offre.getCodePromo());
            ps.setDouble(2, offre.getPrixOffre());
            ps.setString(3, offre.getDescription());
            ps.setDate(4, new java.sql.Date(offre.getDateD().getTime())); // Conversion de java.util.Date à java.sql.Date
            ps.setDate(5, new java.sql.Date(offre.getDateF().getTime()));
            ps.setInt(6, offre.getIdOffre());
            ps.executeUpdate();
            System.out.println("Outil modifié !");

        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int idOffre) {
        String req = "DELETE FROM offre WHERE idOffre = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idOffre);
            ps.executeUpdate();
            System.out.println("Outil supprimé !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Offre getOneById(int idOffre) {
        String req = "SELECT * FROM offre WHERE idOffre = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idOffre);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String codePromo = res.getString("codePromo");
                double prixOffre = res.getDouble("prixOffre");
                String description = res.getString("description");
                Date dateD = res.getDate("dateD");
                Date dateF = res.getDate("dateF");
                int idFormation = res.getInt("idFormation");

                Offre offre = new Offre(idOffre, codePromo, prixOffre, description, dateD, dateF);
                offre.setCodePromo(codePromo);
                offre.setPrixOffre(prixOffre);
                offre.setDescription(description);
                offre.setDateD(dateD);
                offre.setDateF(dateF);
                offre.setIdFormation(idFormation);

                return offre;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }

        return null;
    }


    @Override
    public List<Offre> getAll() {
        List<Offre> offres = new ArrayList<>();

        String req = "SELECT * FROM offre";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int idOffre = res.getInt("idOffre");
                String codePromo = res.getString("codePromo");
                double prixOffre = res.getDouble("prixOffre");
                String description = res.getString("description");
                Date dateD = res.getDate("dateD");
                Date dateF = res.getDate("dateF");
                int idFormation = res.getInt("idFormation");

                Offre offre = new Offre(codePromo, prixOffre, description, dateD, dateF);
                offres.add(offre);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }

        return offres;
    }



}

