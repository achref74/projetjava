package edu.esprit.services;

import edu.esprit.entities.Admin;

import edu.esprit.entities.Client;
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

        String req = "UPDATE  `User`set nom=?,prenom=?,email=?,dateNaissance=?,adresse=?,numtel=?,mdp=? where idUser=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1,admin.getNom());
            ps.setString(2,admin.getPrenom());
            ps.setString(3,admin.getEmail());
            ps.setDate(4,admin.getDateNaissance());
            ps.setString(5,admin.getAdresse());
            ps.setInt(6,admin.getNumtel());
            ps.setString(7,admin.getMdp());
            ps.setInt(8,admin.getId());
            ps.executeUpdate();
            System.out.println("admin updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE  from `User` where idUser=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);


            ps.setInt(1,id);
            ps.executeUpdate();
            System.out.println("Admin with id= "+id+" deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Admin getOneById(int id) {
        String req = "SELECT *  from `User` where idUser="+id;
        Admin a=null;
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            if (res.next())
            {
                int idUser = res.getInt("idUser");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String email  = res.getString("email");
                Date dateNaissance = res.getDate("dateNaissance");
                String adresse = res.getString("adresse");
                int numtel = res.getInt("numtel");
                String mdp = res.getString("mdp");

                a= new Admin(idUser, nom, prenom, email, dateNaissance, adresse, numtel, mdp);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return a;
    }

    @Override
    public List<Admin> getAll() {
        List<Admin> admins =  new ArrayList<>();

        ServiceUser s=new ServiceUser();
        for (User u:s.getAll())
        { if (u instanceof Admin a)
            admins.add(a);
        }

        return admins;
    }
    }


