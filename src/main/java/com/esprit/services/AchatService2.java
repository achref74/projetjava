package com.esprit.services;

import com.esprit.models.Achat;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.esprit.models.formation;
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
                formation formation=new formation();
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
            List<Achat> Achats = new ArrayList<>();

            String req = "SELECT * FROM `Achat` WHERE 1";
            try {
                Statement st = cnx.createStatement();
                ResultSet res = st.executeQuery(req);
                formation formation=new formation();
                outil outil=new outil();
                while (res.next()){
                    int idAchat = res.getInt("idAchat");
                    formation.setIdFormation(res.getInt("idFormation"));
                    outil.setIdoutils(res.getInt("idOutil"));
                    int idoutils = res.getInt("idoutils");
                    Double total = res.getDouble("total");
                    LocalDate date = res.getDate("date").toLocalDate();
                    Achat a = new Achat(idAchat,formation,outil,total,date);
                    Achats.add(a);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return Achats;
        }



    }
