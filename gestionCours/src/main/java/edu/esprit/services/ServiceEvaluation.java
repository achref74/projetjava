package edu.esprit.services;

import edu.esprit.entities.Evaluation;
import edu.esprit.entities.Question;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceEvaluation implements IService <Evaluation>{
    static Connection cnx = DataSource.getInstance().getCnx();
    public void ajouter(Evaluation evaluation){}

    public void ajouter(Evaluation evaluation,int id_cours) {


        if (ServiceCours.existe(id_cours)) {


            String req = "INSERT INTO `evaluation`( `note`,`nom`,`id_cours`) VALUES (?,?,?)";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);


                ps.setInt(1,evaluation.getNote());
                ps.setString(2,evaluation.getNom());
                ps.setInt(3, id_cours);
                ps.executeUpdate();

                System.out.println("Evaluation  added !");
            } catch (
                    SQLException e) {
                System.out.println(e.getMessage());
            }
        } else System.out.println("On ne peut pas ajouter l'évaluation' ");

    }

    @Override
    public void modifier(Evaluation evaluation) {
        String req =" UPDATE `evaluation` SET `note`=?,`nom`=? WHERE `id_e`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, evaluation.getNote());
            ps.setString(2, evaluation.getNom());

            ps.setInt(3, evaluation.getId_e());
            ps.executeUpdate();
            System.out.println("Ev modified  !");

        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }



    }

    @Override
    public void supprimer(int id) {
        String req ="DELETE FROM `evaluation` WHERE id_e=?" ;
        try {ServiceQuestion s =new ServiceQuestion();
            s.supprimerQuestionsById(id);
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Evaluation supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }}



    public void supprimerEvaluationById(int id) {
        try {
            ServiceQuestion s = new ServiceQuestion();

            s.supprimerQuestionsById(id);
            String req = "DELETE FROM `evaluation` WHERE id_e = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " évaluation supprimée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression des évaluations : " + e.getMessage());
        }
    }

    public void supprimerEvaluationByIdCours(int id) {
        try {
            ServiceQuestion s = new ServiceQuestion();
            // Récupérer l'évaluation
            Evaluation e = this.getEvaluationByIdCours(id);
            // Vérifier si l'évaluation récupérée n'est pas nulle
            if (e != null) {
                // Supprimer les questions liées à cette évaluation
                s.supprimerQuestionsById(e.getId_e());
                // Supprimer l'évaluation elle-même
                String req = "DELETE FROM `evaluation` WHERE id_cours = ?";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, id);
                int rowsAffected = ps.executeUpdate();
                System.out.println(rowsAffected + " évaluation supprimée !");
            } else {
                System.out.println("Aucune évaluation trouvée pour l'ID de cours spécifié !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression des évaluations : " + e.getMessage());
        }
    }
    @Override
    public Evaluation getOneById(int id) {

Evaluation ev = null ;
        String req = "Select * from evaluation WHERE id_e ="+id;
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            if (res.next()){
                 int id_e = res.getInt("id_e");

                String nom = res.getString("nom");
                int note = res.getInt("note");
                ServiceQuestion s =new ServiceQuestion();
                Set<Question> questions = s.getQuestionsByIdEvaluation(id);
                ev = new Evaluation(id_e,nom,note,questions);

            }return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return ev;
    }

    @Override
    public Set<Evaluation> getAll() {
        Set<Evaluation> evs = new HashSet<>();

        String req = "Select * from evaluation";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt("id_e");

                String nom = res.getString("nom");
                int note = res.getInt("note");

                ServiceQuestion s =new ServiceQuestion();
                Set<Question> questions = s.getQuestionsByIdEvaluation(id);
                Evaluation e = new Evaluation(id,nom,note,questions);
                evs.add(e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return evs;
    }
    public static boolean existe(int i)
    {
        String req = "Select * from evaluation WHERE id_e="+i;
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            if  (res.next()){
              return true ;
            } return false ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



return false ; }



    public Evaluation getEvaluationByIdCours(int id) {
        Evaluation ev = null;
        String req = "SELECT * FROM evaluation WHERE id_cours = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            try (ResultSet res = ps.executeQuery()) {
                if (res.next()) {
                    int id_e = res.getInt("id_e");
                    String nom = res.getString("nom");
                    int note = res.getInt("note");
                    ServiceQuestion s = new ServiceQuestion();
                    Set<Question> questions = s.getQuestionsByIdEvaluation(id_e);
                    ev = new Evaluation(id_e, nom, note, questions);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'évaluation : " + e.getMessage());
        }
        return ev;
    }

    public boolean isnotevalid(int note){return (note>0)&&(note<=20); }



}
