package edu.esprit.services;

import edu.esprit.entities.Formation;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ServiceFormation implements IService<Formation> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Formation formation) {
        String req = "INSERT INTO `formation`(`nom`, `description`, `dateD`, `dateF`, `prix`, `nbrCours`) VALUES (?,?,?,?,?,?)";
        try {
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Formation formation) {
        // Assurez-vous que la connexion à la base de données (cnx) est correctement établie

        String req = "UPDATE formation SET nom = ?, description = ?, dateD = ?, dateF = ?, prix = ?, nbrCours = ? WHERE idFormation = ?";
        try {
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

        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
    }


    @Override
    public void supprimer(int idFormation) {
        String req = "DELETE FROM formation WHERE idFormation = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idFormation);
            ps.executeUpdate();
            System.out.println("Formation supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Formation getOneById(int idFormation) {
        String req = "SELECT * FROM formation WHERE idFormation = ?";
        try {
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


    @Override
    public List<Formation> getAll() {
        List<Formation> formations = new ArrayList<>();

        String req = "SELECT * FROM formation";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                String nom = res.getString("nom");
                String description = res.getString("description");
                Date dateDebut = res.getDate("dateD");
                Date dateFin = res.getDate("dateF");
                double prix = res.getDouble("prix");
                int nbrCours = res.getInt("nbrCours");
               /* int idUser = res.getInt("idUser");
                int idCategorie = res.getInt("idCategorie");*/
                Formation formation = new Formation(nom, description, dateDebut, dateFin, prix, nbrCours);
                formations.add(formation);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return formations;
    }
}

