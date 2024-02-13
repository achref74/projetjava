package edu.esprit.services;

import edu.esprit.entities.Client;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        String req = "UPDATE  `User`set nom=?,prenom=?,email=?,dateNaissance=?,adresse=?,numtel=?,mdp=?,niveau_scolaire=? where idUser=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1,client.getNom());
            ps.setString(2,client.getPrenom());
            ps.setString(3,client.getEmail());
            ps.setDate(4,client.getDateNaissance());
            ps.setString(5,client.getAdresse());
            ps.setInt(6,client.getNumtel());
            ps.setString(7,client.getMdp());
            ps.setString(8,client.getNiveau_scolaire());
            ps.setInt(9,client.getId());
            ps.executeUpdate();
            System.out.println("Client updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }



    @Override
    public void supprimer(int id) {
        String req = "DELETE  from `User` where idUser=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);


            ps.setInt(1,id);
            ps.executeUpdate();
            System.out.println("Client with id= "+id+" deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }



    @Override
    public Client getOneById(int id) {
        String req = "SELECT *  from `User` where idUser="+id;
        Client c=null;
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            if (res.next())
            {
                int idUser = res.getInt("idUser");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String email  = res.getString("email");
                Date dateNaissance = res.getDate("dateNaissance");
                String adresse = res.getString("adresse");
                int numtel = res.getInt("numtel");
                String mdp = res.getString("mdp");
                String niveau_scolaire = res.getString("niveau_scolaire");
                c = new Client(idUser, nom, prenom, email, dateNaissance, adresse, numtel, mdp, niveau_scolaire);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    @Override
    public List<Client> getAll() {



            List<Client> clients =  new ArrayList<>();

           ServiceUser s=new ServiceUser();
           for (User u:s.getAll())
           { if (u instanceof Client c)
               clients.add(c);
           }

            return clients;
        }


      }
