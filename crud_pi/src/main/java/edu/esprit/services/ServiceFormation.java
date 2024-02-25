package edu.esprit.services;

import edu.esprit.entites.Formation;
import edu.esprit.entites.Outil;
import edu.esprit.entites.Reclamation;
import edu.esprit.entites.User;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ServiceFormation implements IService<Formation> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Formation formation) throws SQLException {

    }

    @Override
    public void modifier(Formation formation) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public Formation getOneById(int id) throws SQLException {
        return null;
    }

    @Override
    public Set<Formation> getAll() throws SQLException {

        Set<Formation> Formations = new HashSet<>();

        String req = "SELECT * FROM `formation` WHERE 1";

        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req);
        while (res.next()){
            int id = res.getInt("idFormation");
            Formation f = new Formation(id);
            Formations.add(f);
        }

        return Formations;
    }


}
