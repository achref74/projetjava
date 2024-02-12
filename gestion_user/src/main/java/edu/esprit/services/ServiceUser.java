package edu.esprit.services;

import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;
import edu.esprit.entities.User;
import edu.esprit.entities.Formateur;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceUser {
    Connection cnx = DataSource.getInstance().getCnx();
    public List<User> getAll() {



        List<User> users =  new ArrayList<>();

        String req = "Select * from User ";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int role = res.getInt("role");

                int idUser = res.getInt("idUser");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String email  = res.getString("email");
                Date dateNaissance = res.getDate("dateNaissance");
                String adresse = res.getString("adresse");
                int numtel = res.getInt("numtel");
                String mdp = res.getString("mdp");
                User u=null;
                if (role==0) {
                    String niveau_scolaire = res.getString("niveau_scolaire");

                    u = new Client(idUser, nom, prenom, email, dateNaissance, adresse, numtel, mdp, niveau_scolaire);
                }else if (role ==1){
                    String specialite = res.getString("specialite");
                    String niveauAcademique = res.getString("niveauAcademique");
                    int disponibilite = res.getInt("disponibilite");
                    String cv = res.getString("cv");

                    u = new Formateur(idUser, nom, prenom, email, dateNaissance, adresse, numtel, mdp, specialite,niveauAcademique,disponibilite,cv);
                }
                else if(role==2)
                {
                    u = new Admin(idUser, nom, prenom, email, dateNaissance, adresse, numtel, mdp);
                }
                users.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }
}


