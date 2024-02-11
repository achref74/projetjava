package edu.esprit.services;

import edu.esprit.entities.Client;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public  class  ServiceClient implements IServiceUser<Client>{




        Connection cnx = DataSource.getInstance().getCnx();
        @Override
        public void ajouter(Client client) {

            String req = "INSERT INTO `User`(`nom`,`prenom`,`email`,`dateNaissance`,`adresse`,`numtel`,`mdp`,`role`,`niveau_scolaire`) VALUES (?,?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);

                ps.setString(1,client.getNom());
                ps.setString(2,client.getPrenom());
                ps.setString(3,client.getEmail());
                ps.setDate(4,client.getDateNaissance());
                ps.setString(5,client.getAdresse());
                ps.setInt(6,client.getNumtel());
                ps.setString(7,client.getMdp());
                ps.setInt(8,0);
                ps.setString(9,client.getNiveau_scolaire());

                ps.executeUpdate();
                System.out.println("Client added !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    @Override
    public void modifier(Client client) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Client getOneById(int id) {
        return null;
    }

    @Override
    public Set<Client> getAll() {
        return null;
    }

}
