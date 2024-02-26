package edu.esprit.services;

import edu.esprit.entities.Certificat;
import edu.esprit.entities.Formation;
import edu.esprit.entities.Offre;
import edu.esprit.utils.DataSource;
import javafx.stage.Stage;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ServiceFormation implements IService<Formation> {


    static Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Formation formation) throws SQLException{
        String req = "INSERT INTO `formation`(`nom`, `description`, `dateD`, `dateF`, `prix`, `nbrCours`) VALUES (?,?,?,?,?,?)";

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, formation.getNom());
            ps.setString(2, formation.getDescription());
            ps.setDate(3, new java.sql.Date(formation.getDateDebut().getTime()));
            ps.setDate(4, new java.sql.Date(formation.getDateFin().getTime()));
            ps.setDouble(5, formation.getPrix());
            ps.setInt(6, formation.getNbrCours());
            /*ps.setInt(7, formation.getIdUser());
            ps.setInt(8, formation.getIdCategorie());*/
            ps.executeUpdate();
            System.out.println("Formation ajoutée !");

    }

    @Override
    public void modifier(Formation formation) throws SQLException{
        // Assurez-vous que la connexion à la base de données (cnx) est correctement établie

        String req = "UPDATE formation SET nom = ?, description = ?, dateD = ?, dateF = ?, prix = ?, nbrCours = ? WHERE idFormation = ?";

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, formation.getNom());
            ps.setString(2, formation.getDescription());
            ps.setDate(3, new java.sql.Date(formation.getDateDebut().getTime())); // Conversion de java.util.Date à java.sql.Date
            ps.setDate(4, new java.sql.Date(formation.getDateFin().getTime()));
            ps.setDouble(5, formation.getPrix());
            ps.setInt(6, formation.getNbrCours());
            ps.setInt(7, formation.getIdFormation());
            System.out.println(ps);
            ps.executeUpdate();
            System.out.println("Formation modifiée !");


    }


    @Override
    public void supprimer(int idFormation) throws SQLException{
        ServiceOffre so=new ServiceOffre();
        so.supprimeroffreById(idFormation);
        ServiceCertificat sc=new ServiceCertificat();
        sc.supprimerCertificatById(idFormation);
        String req = "DELETE FROM formation WHERE idFormation = ?";

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idFormation);
            ps.executeUpdate();
            System.out.println("Formation supprimée !");

    }

    @Override
    public Formation getOneById(int idFormation) throws SQLException{
        String req = "SELECT * FROM formation WHERE idFormation = ?";

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idFormation);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String nom = res.getString("nom");
                String description = res.getString("description");
                Date dateDebut = res.getDate("dateD");
                Date dateFin = res.getDate("dateF");
                double prix = res.getDouble("prix");
                int nbrCours = res.getInt("nbrCours");


                Formation formation = new Formation(idFormation,nom, description, dateDebut, dateFin, prix, nbrCours);
                formation.setNom(nom);
                formation.setDescription(description);
                formation.setDateDebut(dateDebut);
                formation.setDateFin(dateFin);
                formation.setPrix(prix);
                formation.setNbrCours(nbrCours);


                return formation;
            }


        return null;
    }


    @Override
    public List<Formation> getAll() throws SQLException{
        List<Formation> formations = new ArrayList<>();

        String req = "SELECT * FROM formation";

            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int idF=res.getInt("idFormation");
                String nom = res.getString("nom");
                String description = res.getString("description");
                Date dateDebut = res.getDate("dateD");
                Date dateFin = res.getDate("dateF");
                double prix = res.getDouble("prix");
                int nbrCours = res.getInt("nbrCours");
                int idUser = res.getInt("idUser");
                int idCategorie = res.getInt("idCategorie");

                Formation formation = new Formation(idF,nom, description, dateDebut, dateFin, prix, nbrCours,idUser,idCategorie);
                formations.add(formation);
            }


        return formations;
    }

        public Set<Formation> formationsExistForUser(int idUser1) throws SQLException{
            Set<Formation> formations = new HashSet<>();
        String req = "SELECT * FROM formation where idUser=?";

         PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, idUser1);
        ResultSet res = ps.executeQuery();
        while (res.next()) {
            int idF=res.getInt("idFormation");
            String nom = res.getString("nom");
            String description = res.getString("description");
            Date dateDebut = res.getDate("dateD");
            Date dateFin = res.getDate("dateF");
            double prix = res.getDouble("prix");
            int nbrCours = res.getInt("nbrCours");
            int idUser = res.getInt("idUser");
            int idCategorie = res.getInt("idCategorie");

            Formation formation = new Formation(idF,nom, description, dateDebut, dateFin, prix, nbrCours,idUser,idCategorie);
            formations.add(formation);
        }


        return formations;
    }

    public Set<Formation> getAll1() throws SQLException{
        Set<Formation> formations = new HashSet<>();

        String req = "SELECT * FROM formation";

        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req);
        while (res.next()) {
            int idF=res.getInt("idFormation");
            String nom = res.getString("nom");
            String description = res.getString("description");
            Date dateDebut = res.getDate("dateD");
            Date dateFin = res.getDate("dateF");
            double prix = res.getDouble("prix");
            int nbrCours = res.getInt("nbrCours");
            int idUser = res.getInt("idUser");
            int idCategorie = res.getInt("idCategorie");

            Formation formation = new Formation(idF,nom, description, dateDebut, dateFin, prix, nbrCours,idUser,idCategorie);
            formations.add(formation);
        }


        return formations;
    }

}

