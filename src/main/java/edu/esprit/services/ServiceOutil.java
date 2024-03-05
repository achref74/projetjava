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

public class ServiceOutil implements IService<Outil>{
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Outil outil) throws SQLException {

    }

    @Override
    public void modifier(Outil outil) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public Outil getOneById(int id) throws SQLException {
        return null;
    }

    @Override
    public Set<Outil> getAll() throws SQLException {
        Set<Outil> Outils = new HashSet<>();

        String req = "SELECT * FROM `outil` WHERE 1";

        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req);
        while (res.next()){
            int id = res.getInt("idoutils");
            String nom =res.getString("nom");
            Outil f = new Outil(id,nom);
            Outils.add(f);
        }

        return Outils;
    }
}
