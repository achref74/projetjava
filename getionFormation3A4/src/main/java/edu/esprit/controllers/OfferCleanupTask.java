package edu.esprit.controllers;

import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public  class OfferCleanupTask  implements Runnable {

    @Override
    public void run() {
        Connection cnx = DataSource.getInstance().getCnx();
        String queryOffre = "DELETE FROM offre WHERE dateF < CURRENT_DATE()";
        String queryFormation = "DELETE FROM formation WHERE dateF < CURRENT_DATE()";
        PreparedStatement ps = null;
        try {
            ps = cnx.prepareStatement(queryOffre);
            int affectedRows_offre = ps.executeUpdate();
            System.out.println( affectedRows_offre + " offres supprimées.");
            ps = cnx.prepareStatement(queryFormation);
            int affectedRows_formation = ps.executeUpdate();
            System.out.println(affectedRows_formation + " formation supprimées.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}