package edu.esprit.services;

import edu.esprit.entities.Cours;

import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceCours implements IService<Cours>{

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Cours cours) {


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
            System.out.println("Personne added !");
        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void modifier(Cours cours) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Cours getOneById(int id) {
        return null;
    }

    @Override
  public Set<Cours> getAll() {
          Set<Cours> set_cours = new HashSet<>();

       /* String req = "Select * from cours";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt("id_cours");
                String nom = res.getString(2);
                String description = res.getString(3);
                Date date = res.getDate(4);
                int duree = res.getInt(5);
                String prerequis = res.getString(6);
                String ressource = res.getString(7);
                int idFormation = res.getInt(8);
                Cours c = new Cours(id,nom,description,date,duree,prerequis,ressource);
                set_cours.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

     */     return set_cours;
  }




}
