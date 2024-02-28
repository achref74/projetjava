package com.esprit.services;


import com.esprit.models.Formation;
import com.esprit.utils.DataSource;
import javafx.stage.Stage;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ServiceFormation implements IService<Formation> {


    private Connection connection;

    public ServiceFormation() {
        connection = DataSource.getInstance().getConnection();
    }


    @Override
    public void ajouter(Formation formation) {

    }

    @Override
    public void modifier(Formation formation) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Formation getOneById(int id) {
        return null;
    }

    @Override
    public List<Formation> getAll()  {
        List<Formation> formations = new ArrayList<>();

        String req = "SELECT * FROM formation";
        try {
        Statement st = connection.prepareStatement(req);
        ResultSet res = st.executeQuery(req);
        while (res.next()) {
            int idF=res.getInt("idFormation");
            String nom = res.getString("nom");
            String description = res.getString("description");
            Date dateDebut = res.getDate("dateD");
            Date dateFin = res.getDate("dateF");
            double prix = res.getDouble("prix");
            int nbrCours = res.getInt("nbrCours");
            int idUser = res.getInt("idUser");
            int idCategorie = res.getInt("idCategorie");

            Formation formation = new Formation(idF,nom, description, dateDebut, dateFin, prix, nbrCours,idUser,idCategorie);
            formations.add(formation);
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

        return formations;
    }

}
