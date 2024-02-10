package edu.esprit.services;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import edu.esprit.entities.Personne;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServicePersonne implements IService<Personne> {

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Personne personne) {
        /*String req = "INSERT INTO `personne`(`nom`, `prenom`) VALUES ('"+personne.getNom()+"','"+personne.getPrenom()+"')";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Personne added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        String req = "INSERT INTO `personne`(`nom`,`prenom`) VALUES (?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1,personne.getNom());
            ps.setString(2,personne.getPrenom());
            ps.executeUpdate();
            System.out.println("Personne added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Personne personne) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Personne getOneById(int id) {
        return null;
    }

    @Override
    public Set<Personne> getAll() {
        Set<Personne> personnes = new HashSet<>();

        String req = "Select * from personne";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt("id");
                String nom = res.getString(2);
                String prenom = res.getString("prenom");
                Personne p = new Personne(id,nom,prenom);
                personnes.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return personnes;
    }
}
