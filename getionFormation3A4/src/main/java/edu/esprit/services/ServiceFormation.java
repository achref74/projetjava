package edu.esprit.services;

import edu.esprit.entities.Certificat;
import edu.esprit.entities.Formation;
import edu.esprit.entities.Offre;
import edu.esprit.utils.DataSource;
import javafx.stage.Stage;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ServiceFormation implements IService<Formation> {


    static Connection cnx = DataSource.getInstance().getCnx();


    @Override
    public void ajouter(Formation formation) throws SQLException {
        String req = "INSERT INTO `formation`(`nom`, `description`, `dateD`, `dateF`, `prix`, `nbrCours`,`imageUrl`,`idUser`) VALUES (?,?,?,?,?,?,?,?)";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, formation.getNom());
        ps.setString(2, formation.getDescription());
        java.sql.Date a = new java.sql.Date(formation.getDateDebut().getTime());
        java.sql.Date b = new java.sql.Date(formation.getDateFin().getTime());
        ps.setDate(3, a);
        ps.setDate(4, b);
        if (!isValidPrix(formation.getPrix())) {
            System.out.println("Le prix doit etre positive!");

            return;
        } else if (!isValidDate(a, b)) {
            System.out.println("Le prix doit etre positive!");

            return;
        } else if (!isValidDate(a, b) && !isValidPrix(formation.getPrix())) {
            System.out.println("Le prix doit etre positive et la date n'est pas valide!");
            return;


        } else {
            ps.setDouble(5, formation.getPrix());
            ps.setInt(6, formation.getNbrCours());
            ps.setString(7, formation.getImageUrl());

            ps.setInt(8, formation.getIdUser());

            //ps.setInt(8, formation.getIdCategorie());*/
            ps.executeUpdate();
            System.out.println("Formation ajoutée !");
        }

    }

    @Override
    public void modifier(Formation formation) throws SQLException {
        // Assurez-vous que la connexion à la base de données (cnx) est correctement établie

        String req = "UPDATE formation SET nom = ?, description = ?, dateD = ?, dateF = ?, prix = ?, nbrCours = ?, imageUrl= ? WHERE idFormation = ?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, formation.getNom());
        ps.setString(2, formation.getDescription());
        java.sql.Date a = new java.sql.Date(formation.getDateDebut().getTime());
        java.sql.Date b = new java.sql.Date(formation.getDateFin().getTime());
        ps.setDate(3, a);
        ps.setDate(4, b);
        if (!isValidPrix(formation.getPrix())) {
            System.out.println("Le prix doit etre positive!");

            return;
        } else if (!isValidDate(a, b)) {
            System.out.println("Le prix doit etre positive!");

            return;
        } else if (!isValidDate(a, b) && !isValidPrix(formation.getPrix())) {
            System.out.println("Le prix doit etre positive et la date n'est pas valide!");
            return;


        } else {
            ps.setDouble(5, formation.getPrix());
            ps.setInt(6, formation.getNbrCours());
            ps.setString(7, formation.getImageUrl());

            ps.setInt(8, formation.getIdFormation());
            System.out.println(ps);
            ps.executeUpdate();
            System.out.println("Formation modifiée !");
        }


    }


    @Override
    public void supprimer(int idFormation) throws SQLException {
        ServiceOffre so = new ServiceOffre();
        so.supprimeroffreById(idFormation);
        ServiceCertificat sc = new ServiceCertificat();
        sc.supprimerCertificatById(idFormation);
        String req = "DELETE FROM formation WHERE idFormation = ?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, idFormation);
        ps.executeUpdate();
        System.out.println("Formation supprimée !");

    }

    @Override
    public Formation getOneById(int idFormation) throws SQLException {
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


            Formation formation = new Formation(idFormation, nom, description, dateDebut, dateFin, prix, nbrCours);
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
    public List<Formation> getAll() throws SQLException {
        List<Formation> formations = new ArrayList<>();

        String req = "SELECT * FROM formation";

        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req);
        while (res.next()) {
            int idF = res.getInt("idFormation");
            String nom = res.getString("nom");
            String description = res.getString("description");
            Date dateDebut = res.getDate("dateD");
            Date dateFin = res.getDate("dateF");
            double prix = res.getDouble("prix");
            int nbrCours = res.getInt("nbrCours");
            int idUser = res.getInt("idUser");
            int idCategorie = res.getInt("idCategorie");

            Formation formation = new Formation(idF, nom, description, dateDebut, dateFin, prix, nbrCours, idUser, idCategorie);
            formations.add(formation);
        }


        return formations;
    }

    public Set<Formation> formationsExistForUser(int idUser1) throws SQLException {
        Set<Formation> formations = new HashSet<>();
        String req = "SELECT * FROM formation where idUser=?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, idUser1);
        ResultSet res = ps.executeQuery();
        while (res.next()) {
            int idF = res.getInt("idFormation");
            String nom = res.getString("nom");
            String description = res.getString("description");
            Date dateDebut = res.getDate("dateD");
            Date dateFin = res.getDate("dateF");
            double prix = res.getDouble("prix");
            int nbrCours = res.getInt("nbrCours");
            String imageUrl = res.getString("imageUrl");
            Formation formation = new Formation(idF, nom, description, dateDebut, dateFin, prix, nbrCours, imageUrl);
            formations.add(formation);
        }


        return formations;
    }

    public Set<Formation> getAll1() throws SQLException {
        Set<Formation> formations = new HashSet<>();

        String req = "SELECT * FROM formation";

        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req);
        while (res.next()) {
            int idF = res.getInt("idFormation");
            String nom = res.getString("nom");
            String description = res.getString("description");
            Date dateDebut = res.getDate("dateD");
            Date dateFin = res.getDate("dateF");
            double prix = res.getDouble("prix");
            int nbrCours = res.getInt("nbrCours");
            String imageUrl = res.getString("imageUrl");
            int idCategorie = res.getInt("idCategorie");

            Formation formation = new Formation(idF, nom, description, dateDebut, dateFin, prix, nbrCours, imageUrl);
            formations.add(formation);
        }


        return formations;
    }

    public Set<Formation> getAll4() throws SQLException {
        Set<Formation> formations = new HashSet<>();

        String req = "SELECT * FROM formation";

        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req);
        while (res.next()) {
            int idF = res.getInt("idFormation");
            String nom = res.getString("nom");
            String description = res.getString("description");
            Date dateDebut = res.getDate("dateD");
            Date dateFin = res.getDate("dateF");
            double prix = res.getDouble("prix");
            int nbrCours = res.getInt("nbrCours");
            int idCategorie = res.getInt("idCategorie");

            Formation formation = new Formation(idF, nom, description, dateDebut, dateFin, prix, nbrCours);
            formations.add(formation);
        }


        return formations;
    }

    public boolean isValidPrix(Double prix) {
        return prix > 0;
    }

    public boolean isValidDate(Date dt1, Date dt2) {
        return dt1.before(dt2);
    }

    public Set<Formation> rechercherFormationsParNom(String nomF) throws SQLException {
        Set<Formation> formations = new HashSet<>();

        String query = "SELECT * FROM formation WHERE LOWER(nom) LIKE  '"+nomF+"%' ";
        PreparedStatement ps = cnx.prepareStatement(query);

        ResultSet res = ps.executeQuery();

        // Parcourir les résultats de la requête et ajouter les formations à la liste
        while (res.next()) {
            int idF = res.getInt("idFormation");
            String nom = res.getString("nom");
            String description = res.getString("description");
            Date dateDebut = res.getDate("dateD");
            Date dateFin = res.getDate("dateF");
            double prix = res.getDouble("prix");
            int nbrCours = res.getInt("nbrCours");
            String imageUrl = res.getString("imageUrl");
            int idCategorie = res.getInt("idCategorie");
            Formation formation = new Formation(idF, nom, description, dateDebut, dateFin, prix, nbrCours, imageUrl);
            formations.add(formation);
        }


        return formations;
    }
    public Set<Formation> rechercherFormationsParNom1(String nomF) throws SQLException {
        String query = "SELECT * FROM formation WHERE LOWER(nom) LIKE ?";
        Set<Formation> formations = new HashSet<>();

        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, nomF.toLowerCase() + '%');
            try (ResultSet res = ps.executeQuery()) {
                formations = StreamSupport.stream(
                                Spliterators.spliteratorUnknownSize(
                                        new Iterator<Formation>() {
                                            @Override
                                            public boolean hasNext() {
                                                try {
                                                    return res.next();
                                                } catch (SQLException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            }

                                            @Override
                                            public Formation next() {
                                                try {
                                                    int idF = res.getInt("idFormation");
                                                    String nom = res.getString("nom");
                                                    String description = res.getString("description");
                                                    Date dateDebut = res.getDate("dateD");
                                                    Date dateFin = res.getDate("dateF");
                                                    double prix = res.getDouble("prix");
                                                    int nbrCours = res.getInt("nbrCours");
                                                    String imageUrl = res.getString("imageUrl");
                                                    int idCategorie = res.getInt("idCategorie");
                                                    return new Formation(idF, nom, description, dateDebut, dateFin, prix, nbrCours, imageUrl);
                                                } catch (SQLException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            }
                                        },
                                        Spliterator.ORDERED),
                                false)
                        .collect(Collectors.toSet());
            }
        }
        return formations;
    }



}