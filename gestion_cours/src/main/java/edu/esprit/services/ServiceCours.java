package edu.esprit.services;

import edu.esprit.entities.Cours;

import edu.esprit.entities.Evaluation;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceCours implements IService<Cours>{

    static Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Cours cours) {

/* nharet ely bch   namlou integ netfehmou al modif fl classe formation */
        String req = "INSERT INTO `cours`(`nom`, `description`,`date`,`duree`,`prerequis`,`ressource`,`idFormation`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,cours.getNom());
            ps.setString(2, cours.getDescrption());
            ps.setDate(3, cours.getDate());
            ps.setInt(4, cours.getDuree());
            ps.setString(5, cours.getPrerequis());
            ps.setString(6, cours.getRessource());
            ps.setInt(7,cours.getIdFormation());

            ps.executeUpdate();
            System.out.println("new course added added !");
        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void modifier(Cours cours) {
        String req =" UPDATE `cours` SET `nom`=?, `description`=?,`date`=?,`duree`=?,`prerequis`=?,`ressource`=?,`idFormation`=? WHERE `id_cours`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,cours.getNom());
            ps.setString(2, cours.getDescrption());
            ps.setDate(3, cours.getDate());
            ps.setInt(4, cours.getDuree());
            ps.setString(5, cours.getPrerequis());
            ps.setString(6, cours.getRessource());
            ps.setInt(7,cours.getIdFormation());
            ps.setInt(8,cours.getId_cours());
            ps.executeUpdate();
            System.out.println("Cours modified  !");

        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }



    }

    @Override
    public void supprimer(int id) {
        String req ="DELETE FROM `cours` WHERE id_cours=?" ;
        try {ServiceEvaluation s =new ServiceEvaluation();
            s.supprimerEvaluationById(id);
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Cours supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Cours getOneById(int id) {
        return null;
    }

    @Override
  public Set<Cours> getAll() {
          Set<Cours> set_cours = new HashSet<>();

        String req = "Select * from cours";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt("id_cours");
                String nom = res.getString("nom");
                String description = res.getString("description");
                Date date = res.getDate("date");
                int duree = res.getInt("duree");
                String prerequis = res.getString("prerequis");
                String ressource = res.getString("ressource");
               ServiceEvaluation s =new ServiceEvaluation() ;
               Evaluation evaluation =s.getOneById(id) ;
                Cours c = new Cours(id,nom, description,prerequis,ressource,date,duree,evaluation);
                set_cours.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return set_cours;
  }
    public static boolean existe(int i)
    {
        String req = "Select * from cours WHERE id_cours="+i;
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            if  (res.next()){
                return true ;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return false;
    }}
