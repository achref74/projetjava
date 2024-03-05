package edu.esprit.services;

import java.sql.SQLException;
import java.util.List;

public interface IService1<T> {
    public void ajouter(T t) throws SQLException;
    public void modifier(T t) throws SQLException;
    public void supprimer(int idFormation) throws SQLException;
    public T getOneById(int idFormation) throws SQLException;
    public List<T> getAll() throws SQLException;
}
