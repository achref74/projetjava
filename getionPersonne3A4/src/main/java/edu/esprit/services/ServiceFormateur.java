package edu.esprit.services;


import edu.esprit.entities.Formateur;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceFormateur implements IServiceUser<Formateur> {





        Connection cnx = DataSource.getInstance().getCnx();
        @Override
        public void ajouter(Formateur formateur) {

            String req = "INSERT INTO `User`(`nom`,`prenom`,`email`,`dateNaissance`,`adresse`,`numtel`,`mdp`,`role`,`specialite`,`niveauAcademique`,`disponibilite`,`cv`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);

                ps.setString(1,formateur.getNom());
                ps.setString(2,formateur.getPrenom());
                ps.setString(3,formateur.getEmail());
                ps.setDate(4,formateur.getDateNaissance());
                ps.setString(5,formateur.getAdresse());
                ps.setInt(6,formateur.getNumtel());
                ps.setString(7,formateur.getMdp());
                ps.setInt(8,1);
                ps.setString(9,formateur.getSpecialite());
                ps.setString(10,formateur.getNiveauAcademique());
                ps.setInt(11,formateur.getDisponibilite());
                ps.setString(12,formateur.getCv());

                ps.executeUpdate();
                System.out.println("formateur added !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void modifier(Formateur formateur) {
            String req = "UPDATE  `User`set nom=?,prenom=?,email=?,dateNaissance=?,adresse=?,numtel=?,mdp=?,specialite=?,niveauAcademique=?,disponibilite=?,cv=? where idUser=?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);

                ps.setString(1,formateur.getNom());
                ps.setString(2,formateur.getPrenom());
                ps.setString(3,formateur.getEmail());
                ps.setDate(4,formateur.getDateNaissance());
                ps.setString(5,formateur.getAdresse());
                ps.setInt(6,formateur.getNumtel());
                ps.setString(7,formateur.getMdp());
                ps.setString(8,formateur.getSpecialite());
                ps.setString(9,formateur.getNiveauAcademique());
                ps.setInt(10,formateur.getDisponibilite());
                ps.setString(11,formateur.getCv());
                ps.setInt(12,formateur.getId());
                ps.executeUpdate();
                System.out.println("formateur updated !");
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
                System.out.println("formateur with id= "+id+" deleted !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }



        @Override
        public Formateur getOneById(int id) {
            String req = "SELECT *  from `User` where idUser="+id;
            Formateur f=null;
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
                    String specialite = res.getString("specialite");
                    String niveau_Academique = res.getString("niveau_Academique");
                    int disonibilite = res.getInt("disonibilite");
                    String cv = res.getString("cv");
                    f = new Formateur(idUser, nom, prenom, email, dateNaissance, adresse, numtel, mdp, specialite,niveau_Academique,disonibilite,cv);
                }


            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return f;
        }

        @Override
        public List<Formateur> getAll() {



            List<Formateur> formateurs =  new ArrayList<>();

            ServiceUser s=new ServiceUser();
            for (User u:s.getAll())
            { if (u instanceof Formateur f)
                formateurs.add(f);
            }

            return formateurs;
        }


    }
