package edu.esprit.services;

import edu.esprit.entities.Admin;

import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceAdmin implements IServiceUser<Admin>{




    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Admin admin) {

        String req = "INSERT INTO `User`(`nom`,`prenom`,`email`,`dateNaissance`,`adresse`,`numtel`,`mdp`,`role`) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1,admin.getNom());
            ps.setString(2,admin.getPrenom());
            ps.setString(3,admin.getEmail());
            ps.setDate(4,admin.getDateNaissance());
            ps.setString(5,admin.getAdresse());
            ps.setInt(6,admin.getNumtel());
            ps.setString(7,admin.getMdp());
            ps.setInt(8,2);


            ps.executeUpdate();
            System.out.println("admin added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Admin admin) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Admin getOneById(int id) {
        return null;
    }

    @Override
    public List<Admin> getAll() {
        return null;
    }


}