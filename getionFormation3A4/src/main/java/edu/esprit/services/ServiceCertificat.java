package edu.esprit.services;

import edu.esprit.entities.Certificat;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceCertificat implements IService<Certificat>{

    private Connection cnx = DataSource.getInstance().getCnx();

    public void ajouter(Certificat certificat) {
        String req = "INSERT INTO `certificat`(`titre`, `description`, `dateObtention`, `nbrCours`,`idFormation`) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, certificat.getTitre());
            ps.setString(2, certificat.getDescription());
            ps.setDate(3, new java.sql.Date(certificat.getDateObtention().getTime()));
            ps.setInt(4, certificat.getNbrCours());
            //ps.setInt(5, certificat.getIdUser());
            ps.setInt(5, certificat.getIdFormation());
            ps.executeUpdate();
            System.out.println("Certificat ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Certificat certificat) {
        // Assurez-vous que la connexion à la base de données (cnx) est correctement établie

        String req = "UPDATE certificat SET titre = ?, description = ?, dateObtention = ?, nbrCours = ? WHERE idCertificat = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, certificat.getTitre());
            ps.setString(2, certificat.getDescription());
            ps.setDate(3, new java.sql.Date(certificat.getDateObtention().getTime())); // Conversion de java.util.Date à java.sql.Date
            ps.setInt(4, certificat.getNbrCours());
            ps.setInt(5, certificat.getIdCertificat());
            ps.executeUpdate();
            System.out.println("Certificat modifié !");

        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
    }


    @Override
    public void supprimer(int idCertificat) {
        String req = "DELETE FROM certificat WHERE idCertificat = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idCertificat);
            ps.executeUpdate();
            System.out.println("Certificat supprimé !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Certificat getOneById(int idCertificat) {
        String req = "SELECT * FROM certificat WHERE idCertificat = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idCertificat);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String titre = res.getString("titre");
                String description = res.getString("description");
                Date dateObtention = res.getDate("dateObtention");
                int nbrCours = res.getInt("nbrCours");
                int idUser = res.getInt("idUser");
                int idFormation = res.getInt("idFormation");

                Certificat certificat = new Certificat(idCertificat, titre, description, dateObtention, nbrCours);
                certificat.setTitre(titre);
                certificat.setDescription(description);
                certificat.setDateObtention(dateObtention);
                certificat.setNbrCours(nbrCours);
                /*certificat.setIdUser(idUser);
                certificat.setIdFormation(idFormation);*/

                return certificat;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }

        return null;
    }


    @Override
    public List<Certificat> getAll() {
        List<Certificat> certificats = new ArrayList<>();

        String req = "SELECT * FROM certificat";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int idCertificat = res.getInt("idCertificat");
                String titre = res.getString("titre");
                String description = res.getString("description");
                Date dateObtention = res.getDate("dateObtention");
                int nbrCours = res.getInt("nbrCours");
                /*int idUser = res.getInt("idUser");
                int idFormation = res.getInt("idFormation");*/

                Certificat certificat = new Certificat(idCertificat, titre, description, dateObtention, nbrCours);
                certificat.setTitre(titre);
                certificat.setDescription(description);
                certificat.setDateObtention(dateObtention);
                certificat.setNbrCours(nbrCours);
                /*certificat.setIdUser(idUser);
                certificat.setIdFormation(idFormation);*/

                certificats.add(certificat);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }

        return certificats;
    }
    public void supprimerCertificatById(int id) {
        try {

            String req = "DELETE FROM `certificat` WHERE idFormation = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " certificat supprimée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression des évaluations : " + e.getMessage());
        }
    }
}

