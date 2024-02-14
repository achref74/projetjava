package edu.esprit.services;

import edu.esprit.entites.Reclamation;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.util.Set;

public class ServiceReclamation implements IService<Reclamation> {
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Reclamation reclamation) {

    }

    @Override
    public void modifier(int id, Reclamation reclamation) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Reclamation getOneById(int id) {
        return null;
    }

    @Override
    public Set<Reclamation> getAll() {
        return null;
    }
}
