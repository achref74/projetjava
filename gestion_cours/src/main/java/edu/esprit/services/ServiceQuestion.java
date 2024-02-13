package edu.esprit.services;

import edu.esprit.entities.Evaluation;
import edu.esprit.entities.Question;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.Set;
import java.util.*;

public class ServiceQuestion implements IService<Question>{
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Question question) {

    }

    public void ajouter(Question question, int id_e) {

        if (ServiceEvaluation.existe(id_e)) {


        String req = "INSERT INTO `question`(`ressource`, `id_e`) VALUES (?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, question.getRessource());
            ps.setInt(2, id_e);

            ps.executeUpdate();
            System.out.println("Question added !");
        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }
    } else System.out.println("On ne peut pas ajouter la question "); }

    @Override
    public void modifier(Question question) {


      String req ="  UPDATE `question` SET `ressource`=? WHERE id_q=?";
        try {
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, question.getRessource());
            ps.setInt(2, question.getId_q());

        ps.executeUpdate();
        System.out.println("Question modified  !");

        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }

        }

    @Override
    public void supprimer(int id) {
        String req ="DELETE FROM `question` WHERE id_q=?" ;
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Question supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Question getOneById(int id) {
        return null;
    }
    public Set<Question> getQuestionsById(int id_e){
        Set<Question> questions = new HashSet<>();

        String req = "Select * from question WHERE id_e ="+id_e;
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt("id_q");
                String ressource = res.getString("ressource");

                Question q = new Question(id,ressource);
                questions.add(q);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return questions;


    }

    public void supprimerQuestionsById(int id) {
        String req = "DELETE FROM `question` WHERE id_e = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " question(s) supprimée(s) !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression des questions : " + e.getMessage());
        }
    }

    @Override
    public Set<Question> getAll() {

        Set<Question> questions = new HashSet<>();

        String req = "Select * from question";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt("id_q");
                String ressource = res.getString("ressource");

                Question q = new Question(id,ressource);
            questions.add(q);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return questions;}
}
