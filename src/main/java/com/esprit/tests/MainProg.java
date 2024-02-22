package com.esprit.tests;

import com.esprit.models.Categorie;
import com.esprit.models.formation;
import com.esprit.models.outil;
import com.esprit.services.AchatService2;
import com.esprit.services.OutilsService2;
import com.esprit.services.CategorieService2;

public class MainProg {

    public static void main(String[] args) {
        OutilsService2 outilService = new OutilsService2();
        CategorieService2 categorieService = new CategorieService2();
        AchatService2 achatService = new AchatService2();

       // categorieService.ajouter(new Categorie("felhui","mohamedd"));
formation formation = new formation(1);
outil o=new outil();

        // Testing Outils
        Categorie categorie=new Categorie();
        categorie.setIdcategorie(1);
        //outil o=new outil(3, "youssef", "1920", 12.5, "si", "saaaa", "termine");
       // categorie.setIdcategorie(1);
       // Achat achat = new Achat(formation,o,52);
        //achatService.ajouter(new Achat(formation,o,52));
        //achatService.modifier(new Achat(2,formation,o,1920));
        outilService.ajouter(new outil( "youssef", "hhhhhhhhhhh", 12.5, "srrrri", "saaaa", "hahahah",categorie,"ggg"));
        //outilService.modifier(new outil(1, "ggYousseff", "abidi", 10.5, "hhhhhhhh", "s", "termine",categorie));
      //  outil o=new outil(4, "youssef", "1920", 12.5, "si", "saaaa", "termine",categorie);
         //outilService.supprimer(2);

     //   System.out.println(outilService.getOneById(1));

        // Testing Categorie
       // categorieService.ajouter(new Categorie( "youssef", "ccc"));
        //categorieService.modifier(new Categorie(3, "omar", "mm"));
        //categorieService.supprimer(3);
        //System.out.println(categorieService.getAll());




    }
}
