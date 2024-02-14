package edu.esprit.services;

import edu.esprit.entites.Achat;

import java.util.Set;

public interface IService <T> {
    public void ajouter(T t);
    public void modifier(int id,T t);
    public void supprimer(int id);
    public T getOneById(int id);
    public Set<T> getAll();
}
