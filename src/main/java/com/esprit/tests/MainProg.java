package com.esprit.tests;

import com.esprit.models.Categorie;
import com.esprit.models.EtatOutils;
import com.esprit.models.Outils;
import com.esprit.services.OutilsService2;
import com.esprit.services.CategorieService2;

public class MainProg {

    public static void main(String[] args) {
        OutilsService2 outilsService = new OutilsService2();
        CategorieService2 categorieService = new CategorieService2();

        // Testing Outils
        outilsService.ajouter(new Outils(1, "omar", "abidi", 10.5, "s", "s", EtatOutils.Attente));

        // Testing Categorie
        categorieService.ajouter(new Categorie(1, "omar", "ss"));
    }
}
