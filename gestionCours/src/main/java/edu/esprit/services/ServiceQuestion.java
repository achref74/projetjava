package edu.esprit.services;

import edu.esprit.entities.Question;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceQuestion implements IService<Question>{
    Connection cnx = DataSource.getInstance().getCnx();
    public void ajouter(Question q ){}
    public void ajouter(Question question, int id_e) {
        if (ServiceEvaluation.existe(id_e)) {
            String req = "INSERT INTO `question`(`ressource`, `duree`, `point`, `choix1`, `choix2`, `choix3`,  `crx`, `id_e`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1, question.getRessource());
                ps.setInt(2, question.getDuree());
                ps.setInt(3, question.getPoint());
                ps.setString(4, question.getChoix1());
                ps.setString(5, question.getChoix2());
                ps.setString(6, question.getChoix3());

                ps.setString(7, question.getCrx());
                ps.setInt(8, id_e);
                ps.executeUpdate();
                System.out.println("Question ajoutée !");
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout de la question : " + e.getMessage());
            }
        } else {
            System.out.println("L'évaluation avec l'ID spécifié n'existe pas. Impossible d'ajouter la question.");
        }
    }
    @Override
    public void modifier(Question question) {
        String req = "UPDATE `question` SET `ressource`=?, `duree`=?, `point`=?, `choix1`=?, `choix2`=?, `choix3`=?, `reponse`=?, `crx`=? WHERE `id_q`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, question.getRessource());
            ps.setInt(2, question.getDuree());
            ps.setInt(3, question.getPoint());
            ps.setString(4, question.getChoix1());
            ps.setString(5, question.getChoix2());
            ps.setString(6, question.getChoix3());
            ps.setString(7, question.getReponse());
            ps.setString(8, question.getCrx());
            ps.setInt(9, question.getId_q());
            ps.executeUpdate();
            System.out.println("Question modifiée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de la question : " + e.getMessage());
        }
    }

    public void modifierReponse(Question question) {
        String req = "UPDATE question SET reponse = ? WHERE id_q = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, question.getReponse());
            ps.setInt(2, question.getId_q());

            if (ps.executeUpdate() > 0) {
                System.out.println("Réponse mise à jour avec succès");
            } else {
                System.out.println("La mise à jour de la réponse a échoué");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la réponse : " + e.getMessage());
            e.printStackTrace();       }
    }
    public void modifierFormateur(Question question) {
        String req = "UPDATE `question` SET `ressource`=?, `duree`=?, `point`=?, `choix1`=?, `choix2`=?, `choix3`=?, `crx`=? WHERE `id_q`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, question.getRessource());
            ps.setInt(2, question.getDuree());
            ps.setInt(3, question.getPoint());
            ps.setString(4, question.getChoix1());
            ps.setString(5, question.getChoix2());
            ps.setString(6, question.getChoix3());

            ps.setString(7, question.getCrx());
            ps.setInt(8, question.getId_q());
            ps.executeUpdate();
            System.out.println("Question modifiée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de la question : " + e.getMessage());
        }
    }


    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `question` WHERE id_q=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Question supprimée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la question : " + e.getMessage());
        }
    }

    @Override
    public Question getOneById(int id) {
        Question q = null;
        String req = "SELECT * FROM question WHERE id_q=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                int id_q = res.getInt("id_q");
                String ressource = res.getString("ressource");
                int duree = res.getInt("duree");
                int point = res.getInt("point");
                String choix1 = res.getString("choix1");
                String choix2 = res.getString("choix2");
                String choix3 = res.getString("choix3");
                String reponse = res.getString("reponse");
                String crx = res.getString("crx");
                int id_e = res.getInt("id_e");
                q = new Question(id_q, ressource, duree, point, choix1, choix2, choix3, reponse, crx);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la question : " + e.getMessage());
        }
        return q;
    }

    @Override
    public Set<Question> getAll() {
        Set<Question> questions = new HashSet<>();
        String req = "SELECT * FROM question";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int id_q = res.getInt("id_q");
                String ressource = res.getString("ressource");
                int duree = res.getInt("duree");
                int point = res.getInt("point");
                String choix1 = res.getString("choix1");
                String choix2 = res.getString("choix2");
                String choix3 = res.getString("choix3");
                String reponse = res.getString("reponse");
                String crx = res.getString("crx");
                Question q = new Question(id_q, ressource, duree, point, choix1, choix2, choix3, reponse, crx);
                questions.add(q);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des questions : " + e.getMessage());
        }
        return questions;
    }
    public void supprimerQuestionsById(int id_e) {
        String req = "DELETE FROM `question` WHERE id_e = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id_e);
            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " question(s) supprimée(s) !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression des questions : " + e.getMessage());
        }
    }
    public Set<Question> getQuestionsByIdEvaluation(int id){
        Set<Question> questions = new HashSet<>();
        String req = "SELECT * FROM question WHERE id_e = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            while (res.next()){
                int id_q = res.getInt("id_q");
                String ressource = res.getString("ressource");
                int duree = res.getInt("duree");
                int point = res.getInt("point");
                String choix1 = res.getString("choix1");
                String choix2 = res.getString("choix2");
                String choix3 = res.getString("choix3");
                String reponse = res.getString("reponse");
                String crx = res.getString("crx");
                Question q = new Question(id_q, ressource, duree, point, choix1, choix2, choix3, reponse, crx);
                questions.add(q);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des questions : " + e.getMessage());
        }
        return questions;
    }
}
