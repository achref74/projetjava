package com.esprit.services;

import com.esprit.models.Achat;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.esprit.models.Categorie;
import com.esprit.models.Formation;
import com.esprit.models.outil;
import com.esprit.utils.DataSource;
public class AchatService2 implements IService<Achat> {



        Connection cnx = DataSource.getInstance().getConnection();

        @Override
        public void ajouter(Achat achat) {
            String req = "INSERT INTO `Achat`(`idFormation`, `idOutil`, `total`, `date`) VALUES (?,?,?,?)";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);

                ps.setInt(1,achat.getidFormation().getIdFormation());
                ps.setInt(2,achat.getOutil().getIdoutils());
                ps.setDouble(3,achat.getTotal());
                ps.setDate(4, Date.valueOf(achat.getDate()));
                //ps.setInt(5,achat.getC);
                ps.executeUpdate();
                System.out.println("Achat added !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }


        @Override
        public void modifier(Achat achat) {
            String req = "UPDATE `Achat` SET  idFormation=?, idOutil=?, total=? WHERE idAchat=?";

            try {
                // Using PreparedStatement to prevent SQL injection
                PreparedStatement preparedStatement = cnx.prepareStatement(req);

                preparedStatement.setInt(1, achat.getidFormation().getIdFormation());
                preparedStatement.setInt(2,achat.getOutil().getIdoutils());
                preparedStatement.setDouble(3, achat.getTotal());
                preparedStatement.setInt(4, achat.getIdAchat());

                int rowCount = preparedStatement.executeUpdate();

                if (rowCount > 0) {
                    System.out.println("Achat with id " + achat.getIdAchat() + " has been updated successfully.");
                } else {
                    System.out.println("No Achat found with id " + achat.getIdAchat() + ". Nothing updated.");
                }
            } catch (SQLException e) {
                System.out.println("Error updating Achat with id " + achat.getIdAchat() + ": " + e.getMessage());
            }
        }


        @Override
        public void supprimer(int id) {
            String req = "DELETE FROM `Achat` WHERE idAchat = ?";

            try {
                // Using PreparedStatement to prevent SQL injection
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, id);

                int rowCount = ps.executeUpdate();

                if (rowCount > 0) {
                    System.out.println("Achat with id " + id + " has been deleted successfully.");
                } else {
                    System.out.println("No Achat found with id " + id + ". Nothing deleted.");
                }
            } catch (SQLException e) {
                System.out.println("Error deleting Achat with id " + id + ": " + e.getMessage());
            }
        }

        @Override
        public Achat getOneById(int id) {
            String req = "SELECT `idAchat`, `idFormation`, `idoutils`, `total`, `date` FROM `Achat` WHERE idAchat = ?";

            try (PreparedStatement ps = cnx.prepareStatement(req)) {
                ps.setInt(1, id); // Set the parameter value
                ResultSet res = ps.executeQuery();
                Formation formation=new Formation();
                outil outil=new outil();
                if (res.next()) {

                    Double total = res.getDouble("total");
                    LocalDate date = res.getDate("date").toLocalDate();
                    formation.setIdFormation(res.getInt("idFormation"));
                    outil.setIdoutils(res.getInt("idOutil"));
                    return new Achat(id,formation,outil,total, date);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching Achat by id: " + e.getMessage());
            }

            return null; // Achat not found
        }

    @Override
    public List<Achat> getAll() {
        List<Achat> achats = new ArrayList<>();
        String req = "SELECT a.*, f.*, o.*, c.* FROM Achat a " +
                "LEFT JOIN formation f ON a.idFormation = f.idFormation " +
                "LEFT JOIN outil o ON a.idOutil = o.idOutils " +
                "LEFT JOIN categorie c ON o.idCategorie = c.idCategorie";  // Assuming you have a 'categorie' table

        try (Statement st = cnx.createStatement();
             ResultSet res = st.executeQuery(req)) {

            while (res.next()) {
                int idAchat = res.getInt("a.idAchat");

                // Formation fields
                int idFormation = res.getInt("f.idFormation");
                String formationNom = res.getString("f.nom");
                String formationDescription = res.getString("f.description");
                Date formationDateDebut = res.getDate("f.dateD");
                Date formationDateFin = res.getDate("f.dateF");
                double formationPrix = res.getDouble("f.prix");
                int formationNbrCours = res.getInt("f.nbrCours");
                int formationIdUser = res.getInt("f.idUser"); // Handle possible null value
                int formationIdCategorie = res.getInt("f.idCategorie");

                // Outil fields
                int idOutil = res.getInt("o.idoutils");
                String outilNom = res.getString("o.nom");
                String outilDescription = res.getString("o.description");
                double outilPrix = res.getDouble("o.prix");
                String outilRessources = res.getString("o.ressources");
                String outilStock = res.getString("o.stock");
                String outilEtat = res.getString("o.etat");
                String outilImage = res.getString("o.image");

                // Categorie for outil
                int categorieId = res.getInt("c.idCategorie");
                String categorieNom = res.getString("c.nom");
                String categorieDescription = res.getString("c.description");


                Categorie categorie = new Categorie(categorieId, categorieNom,categorieDescription );
                Formation formation = new Formation(idFormation, formationNom, formationDescription, formationDateDebut, formationDateFin, formationPrix, formationNbrCours, formationIdUser, formationIdCategorie);
                outil outil = new outil(idOutil, outilNom, outilDescription, outilPrix, outilRessources, outilStock, outilEtat, categorie, outilImage);

                double total = res.getDouble("a.total");
                LocalDate date = res.getDate("a.date").toLocalDate();

                Achat achat = new Achat(idAchat, formation, outil, total, date);
                achats.add(achat);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return achats;
    }




    private Formation getFormationById(int idFormation) {

        return new Formation();
    }


    private outil getOutilById(int idOutil) {

        return new outil();
    }



    }
