package edu.esprit.services;

import edu.esprit.entites.Achat;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ServiceAchat implements IService<Achat>{
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Achat achat) {
        String req = "INSERT INTO `achat`(`id_user`, `id_formation`, `id_outil`, `code_promo`, `total`, `date`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,achat.getUser());
            ps.setInt(2,achat.getFormation());
            ps.setInt(3,achat.getOutil());
            ps.setString(4,achat.getCode_promo());
            ps.setInt(5,achat.getTotal());
            ps.setDate(6, Date.valueOf(achat.getDate_achat()));
            ps.executeUpdate();
            System.out.println("Achat added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void modifier(int id,Achat achat) {
        String req = "UPDATE `achat` SET id_user=?, id_formation=?, id_outil=?, code_promo=?, total=? WHERE id_achat=?";

        try {
            // Using PreparedStatement to prevent SQL injection
            PreparedStatement preparedStatement = cnx.prepareStatement(req);

            preparedStatement.setInt(1, achat.getUser());
            preparedStatement.setInt(2, achat.getFormation());
            preparedStatement.setInt(3, achat.getOutil());
            preparedStatement.setString(4, achat.getCode_promo());
            preparedStatement.setInt(5, achat.getTotal());




            preparedStatement.setInt(6, id);

            int rowCount = preparedStatement.executeUpdate();

            if (rowCount > 0) {
                System.out.println("Achat with id " + id + " has been updated successfully.");
            } else {
                System.out.println("No Achat found with id " + id + ". Nothing updated.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating Achat with id " + id + ": " + e.getMessage());
        }
    }


    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `achat` WHERE id_achat = ?";

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
        String req = "SELECT `id_achat`, `id_user`, `id_formation`, `id_outil`, `code_promo`, `total`, `date` FROM `achat` WHERE id_achat = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id); // Set the parameter value
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                int user = res.getInt("id_user");
                int formation = res.getInt("id_formation");
                int outil = res.getInt("id_outil");
                String code = res.getString("code_promo");
                int total = res.getInt("total");
                LocalDate date = res.getDate("date").toLocalDate();
                return new Achat(id, user, formation, outil, code, total, date);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Achat by id: " + e.getMessage());
        }

        return null; // Achat not found
    }

    @Override
    public Set<Achat> getAll() {
        Set<Achat> Achatss = new HashSet<>();

        String req = "SELECT * FROM `achat` WHERE 1";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt("id_achat");
                int user = res.getInt("id_user");
                int formation = res.getInt("id_formation");
                int outil = res.getInt("id_outil");
                String code = res.getString("code_promo");
                int total = res.getInt("total");
                LocalDate date = res.getDate("date").toLocalDate();
                Achat a = new Achat(id,user,formation,outil,code,total,date);
                Achatss.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Achatss;
    }



}

