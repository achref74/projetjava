package edu.esprit.services;

import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;
import edu.esprit.entities.Formateur;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public  class ServiceUser implements IServiceUser<User>{
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(User user) {
        if (user instanceof Client c ){
            String req = "INSERT INTO `User`(`nom`,`prenom`,`email`,`dateNaissance`,`adresse`,`numtel`,`mdp`,`role`,`niveau_scolaire`) VALUES (?,?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);

                ps.setString(1,c.getNom());
                ps.setString(2,c.getPrenom());
                ps.setString(3,c.getEmail());
                ps.setDate(4,c.getDateNaissance());
                ps.setString(5,c.getAdresse());
                ps.setInt(6,c.getNumtel());
                ps.setString(7,c.getMdp());
                ps.setInt(8,0);
                ps.setString(9,c.getNiveau_scolaire());

                ps.executeUpdate();
                System.out.println("Client added !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }else  if (user instanceof Formateur f){
            String req = "INSERT INTO `User`(`nom`,`prenom`,`email`,`dateNaissance`,`adresse`,`numtel`,`mdp`,`role`,`specialite`,`niveauAcademique`,`disponibilite`,`cv`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);

                ps.setString(1,f.getNom());
                ps.setString(2,f.getPrenom());
                ps.setString(3,f.getEmail());
                ps.setDate(4,f.getDateNaissance());
                ps.setString(5,f.getAdresse());
                ps.setInt(6,f.getNumtel());
                ps.setString(7,f.getMdp());
                ps.setInt(8,1);
                ps.setString(9,f.getSpecialite());
                ps.setString(10,f.getNiveauAcademique());
                ps.setInt(11,f.getDisponibilite());
                ps.setString(12,f.getCv());

                ps.executeUpdate();
                System.out.println("formateur added !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        else if(user instanceof Admin a){
            String req = "INSERT INTO `User`(`nom`,`prenom`,`email`,`dateNaissance`,`adresse`,`numtel`,`mdp`,`role`) VALUES (?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);

                ps.setString(1,a.getNom());
                ps.setString(2,a.getPrenom());
                ps.setString(3,a.getEmail());
                ps.setDate(4,a.getDateNaissance());
                ps.setString(5,a.getAdresse());
                ps.setInt(6,a.getNumtel());
                ps.setString(7,a.getMdp());
                ps.setInt(8,2);


                ps.executeUpdate();
                System.out.println("admin added !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    @Override
    public void modifier(User user) {
        if(user instanceof Client c){
            String req = "UPDATE  `User`set nom=?,prenom=?,email=?,dateNaissance=?,adresse=?,numtel=?,mdp=?,niveau_scolaire=? where idUser=?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);

                ps.setString(1,c.getNom());
                ps.setString(2,c.getPrenom());
                ps.setString(3,c.getEmail());
                ps.setDate(4,c.getDateNaissance());
                ps.setString(5,c.getAdresse());
                ps.setInt(6,c.getNumtel());
                ps.setString(7,c.getMdp());
                ps.setString(8,c.getNiveau_scolaire());
                ps.setInt(9,c.getId());
                ps.executeUpdate();
                System.out.println("Client updated !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }else  if (user instanceof Formateur f){
            String req = "UPDATE  `User`set nom=?,prenom=?,email=?,dateNaissance=?,adresse=?,numtel=?,mdp=?,specialite=?,niveauAcademique=?,disponibilite=?,cv=? where idUser=?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);

                ps.setString(1,f.getNom());
                ps.setString(2,f.getPrenom());
                ps.setString(3,f.getEmail());
                ps.setDate(4,f.getDateNaissance());
                ps.setString(5,f.getAdresse());
                ps.setInt(6,f.getNumtel());
                ps.setString(7,f.getMdp());
                ps.setString(8,f.getSpecialite());
                ps.setString(9,f.getNiveauAcademique());
                ps.setInt(10,f.getDisponibilite());
                ps.setString(11,f.getCv());
                ps.setInt(12,f.getId());
                ps.executeUpdate();
                System.out.println("formateur updated !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }  else if(user instanceof Admin a){

            String req = "UPDATE  `User`set nom=?,prenom=?,email=?,dateNaissance=?,adresse=?,numtel=?,mdp=? where idUser=?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);

                ps.setString(1,a.getNom());
                ps.setString(2,a.getPrenom());
                ps.setString(3,a.getEmail());
                ps.setDate(4,a.getDateNaissance());
                ps.setString(5,a.getAdresse());
                ps.setInt(6,a.getNumtel());
                ps.setString(7,a.getMdp());
                ps.setInt(8,a.getId());
                ps.executeUpdate();
                System.out.println("admin updated !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }
    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `User` WHERE idUser=?";
        String req1 = "SELECT * FROM User WHERE idUser="+id;
        try {


            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req1);
            if (res.next()) {
                int role = res.getInt("role");
                if (role == 0)
                    System.out.println("Client with id= " + id + " deleted !");
                else if (role == 1)
                    System.out.println("Formateur with id= " + id + " deleted !");
                else if (role == 2)
                    System.out.println("Admin with id= " + id + " deleted !");
            } else {
                System.out.println("No user found with id= " + id);
            }
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    @Override
    public User getOneById(int id) {

        String req = "SELECT *  from `User` where idUser="+id;
        Client c=null;
        Formateur f=null;
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
                int role=res.getInt("role");
                if (role==1){
                    String specialite = res.getString("specialite");
                    String niveau_Academique = res.getString("niveauAcademique");
                    int disonibilite = res.getInt("disponibilite");
                    String cv = res.getString("cv");
                    f = new Formateur(idUser, nom, prenom, email, dateNaissance, adresse, numtel, mdp, specialite,niveau_Academique,disonibilite,cv);
                    return f;}
               else if (role==0){ String niveau_scolaire = res.getString("niveau_scolaire");
                    c = new Client(idUser, nom, prenom, email, dateNaissance, adresse, numtel, mdp, niveau_scolaire);
                    return c;}
                else if (role==2){a =new Admin(idUser, nom, prenom, email, dateNaissance, adresse, numtel, mdp);
                    return a;}
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return f;
    }
    public List<User> getAll() {



        List<User> users =  new ArrayList<>();

        String req = "Select * from User ";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int role = res.getInt("role");

                int idUser = res.getInt("idUser");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String email  = res.getString("email");
                Date dateNaissance = res.getDate("dateNaissance");
                String adresse = res.getString("adresse");
                int numtel = res.getInt("numtel");
                String mdp = res.getString("mdp");
                User u=null;
                if (role==0) {
                    String niveau_scolaire = res.getString("niveau_scolaire");

                    u = new Client(idUser, nom, prenom, email, dateNaissance, adresse, numtel, mdp, niveau_scolaire);
                }else if (role ==1){
                    String specialite = res.getString("specialite");
                    String niveauAcademique = res.getString("niveauAcademique");
                    int disponibilite = res.getInt("disponibilite");
                    String cv = res.getString("cv");
                    u = new Formateur(idUser, nom, prenom, email, dateNaissance, adresse, numtel, mdp, specialite,niveauAcademique,disponibilite,cv);
                }
                else if(role==2)
                {
                    u = new Admin(idUser, nom, prenom, email, dateNaissance, adresse, numtel, mdp);
                }
                users.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }



}
