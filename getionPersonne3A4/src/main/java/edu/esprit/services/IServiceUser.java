package edu.esprit.services;

import java.util.Set;

public interface IServiceUser<T> {
    public void ajouter(T t);
    public void modifier(T t);
    public void supprimer(int id);
    public T getOneById(int id);
    public Set<T> getAll();
}
