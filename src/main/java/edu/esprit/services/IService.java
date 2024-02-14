package edu.esprit.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IService<T> {
    public void ajouter(T t);
    public void modifier(T t)throws SQLException;
    public void supprimer(int id);
    public T getOneById(int id);
    public List<T> recuperer() throws SQLException;
}
