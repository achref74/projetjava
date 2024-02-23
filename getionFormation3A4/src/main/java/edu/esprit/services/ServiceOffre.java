package edu.esprit.services;

import edu.esprit.entities.Formation;
import edu.esprit.entities.Offre;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceOffre implements IService<Offre> {

    private Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Offre offre) throws SQLException{
        String req = "INSERT INTO `offre`(`prixOffre`, `description`, `dateD`, `dateF`,`idFormation`) VALUES (?,?,?,?,?)";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setDouble(1, offre.getPrixOffre());
        ps.setString(2, offre.getDescription());
        ps.setDate(3, new java.sql.Date(offre.getDateD().getTime()));
        ps.setDate(4, new java.sql.Date(offre.getDateF().getTime()));
        ps.setInt(5, offre.getIdFormation());
        ps.executeUpdate();
        System.out.println("offre ajouté !");
    }

    public void ajouter(Offre offre, int idFormation) throws SQLException{

             String req = "INSERT INTO `offre`(`prixOffre`, `description`, `dateD`, `dateF`,`idFormation`) VALUES (?,?,?,?,?)";

                 PreparedStatement ps = cnx.prepareStatement(req);
                 ps.setDouble(1, offre.getPrixOffre());
                 ps.setString(2, offre.getDescription());
                 ps.setDate(3, new java.sql.Date(offre.getDateD().getTime()));
                 ps.setDate(4, new java.sql.Date(offre.getDateF().getTime()));
                 ps.setInt(5, offre.getIdFormation());
                 ps.executeUpdate();
                 System.out.println("offre ajouté !");


    }
    @Override
    public void modifier(Offre offre) throws SQLException{
        // Assurez-vous que la connexion à la base de données (cnx) est correctement établie

        String req = "UPDATE offre SET prixOffre = ?, description = ?, dateD = ?, dateF = ?  WHERE idOffre = ?";

            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setDouble(1, offre.getPrixOffre());
            ps.setString(2, offre.getDescription());
            ps.setDate(3, new java.sql.Date(offre.getDateD().getTime())); // Conversion de java.util.Date à java.sql.Date
            ps.setDate(4, new java.sql.Date(offre.getDateF().getTime()));
            ps.setInt(5, offre.getIdOffre());
            ps.executeUpdate();
            System.out.println("Offre modifié !");


    }

    @Override
    public void supprimer(int idOffre) throws SQLException{
        String req = "DELETE FROM offre WHERE idOffre = ?";

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idOffre);
            ps.executeUpdate();
            System.out.println("Offre supprimé !");

    }

    @Override
    public Offre getOneById(int idOffre) throws SQLException{
        Offre offre=null;
        String req = "SELECT o.*, f.nom FROM offre o INNER JOIN formation f ON o.idFormation = f.idFormation WHERE o.idOffre = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idOffre);
            ResultSet res = ps.executeQuery();
        Formation formation=new Formation();
            if (res.next()) {
                double prixOffre = res.getDouble("prixOffre");
                String description = res.getString("description");
                Date dateD = res.getDate("dateD");
                Date dateF = res.getDate("dateF");
formation.setNom(res.getString("nom"));
                 offre = new Offre(idOffre, prixOffre, description, dateD, dateF,formation);

                offre.setPrixOffre(prixOffre);
                offre.setDescription(description);
                offre.setDateD(dateD);
                offre.setDateF(dateF);
                offre.setFormation(formation);

                return offre;
            }


        return null;
    }


    @Override
    public List<Offre> getAll() throws SQLException{
        List<Offre> offres = new ArrayList<>();


        String req = "SELECT o.*, f.nom FROM offre o INNER JOIN formation f ON o.idFormation = f.idFormation";

            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int idOffre = res.getInt("idOffre");
                double prixOffre = res.getDouble("prixOffre");
                String description = res.getString("description");
                Date dateD = res.getDate("dateD");
                Date dateF = res.getDate("dateF");
                Formation formation =new Formation();
                formation.setNom(res.getString("nom"));
                Offre offre = new Offre(prixOffre, description, dateD, dateF,formation);
                offres.add(offre);
            }


        return offres;
    }


    public void supprimeroffreById(int id) throws SQLException{


            String req = "DELETE FROM `offre` WHERE idFormation = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " offre supprimée !");

    }
}

