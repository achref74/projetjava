package edu.esprit.services;

import edu.esprit.entities.Cours;
import edu.esprit.entities.Evaluation;
import edu.esprit.entities.Question;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceEvaluation implements IService <Evaluation>{
    static Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Evaluation evaluation) {

    }

    @Override
    public void modifier(Evaluation evaluation) {

    }

    @Override
    public void supprimer(int id) {
        String req ="DELETE FROM `evaluation` WHERE id_e=?" ;
        try {ServiceQuestion s =new ServiceQuestion();
            s.supprimerQuestionsById(id);
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);

            ps.executeUpdate();
            System.out.println("Evaluationnnnnnn supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    @Override
    public Evaluation getOneById(int id) {
        return null;
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
                int duree = res.getInt("duree");
                String nom = res.getString("nom");
                int note = res.getInt("note");
                ServiceQuestion s =new ServiceQuestion();
                Set<Question> questions = s.getQuestionsById(id);
                Evaluation e = new Evaluation(id,duree,nom,note,questions);
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



return false ; }}
