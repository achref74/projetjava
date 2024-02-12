package edu.esprit.tests;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.esprit.entities.Formation;
import edu.esprit.entities.Offre;
import edu.esprit.services.ServiceFormation;
import edu.esprit.services.ServiceOffre;

public class Main {
    public static void main(String[] args) {
        ServiceFormation sp = new ServiceFormation();
        ServiceOffre so = new ServiceOffre();

        //ajouter formation
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateDebut = null;
        Date dateFin = null;
        try {
            dateDebut = dateFormat.parse("2024-02-15"); // Remplacez par la date de début souhaitée
            dateFin = dateFormat.parse("2024-03-30"); // Remplacez par la date de fin souhaitée
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Formation nouvelleFormation = new Formation("Formation Java", "Apprendre la programmation Java",dateDebut, dateFin, 100.0, 10);
        Formation nouvelleFormation1 = new Formation("Formation python", "Apprendre python",dateDebut, dateFin, 20, 5);
        //sp.ajouter(nouvelleFormation1);

        //ajouter offre
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date dateD = null;
        Date dateF = null;
        try {
            dateD = dateFormat.parse("2024-02-15"); // Remplacez par la date de début souhaitée
            dateF = dateFormat.parse("2024-03-30"); // Remplacez par la date de fin souhaitée
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Offre nouvelOffre = new Offre("promo123",50,"reduction speciale",dateD,dateF);
        //so.ajouter(nouvelOffre);


        // Modifier une formation existante (par exemple, avec l'ID 1)
        Formation formationAModifier = sp.getOneById(3);
        if (formationAModifier != null) {
            formationAModifier.setNom("Formation Java Avancée");
            formationAModifier.setDescription("Approfondir les connaissances en Java");
            formationAModifier.setPrix(20);
            // Mettez à jour les autres attributs si nécessaire
            sp.modifier(formationAModifier);
            System.out.println(formationAModifier.getIdFormation());
        }

        // Modifier un outil existant (par exemple, avec l'ID 3)
        Offre offreAModifier = so.getOneById(2);
        if (offreAModifier != null) {
            offreAModifier.setCodePromo("NEWCODE");
            offreAModifier.setPrixOffre(30.0);
            // Mettez à jour les autres attributs si nécessaire
            so.modifier(offreAModifier);
            System.out.println(offreAModifier.getIdOffre());
        }

        //supp
        sp.supprimer(1);
        // Supprimer un outil (par exemple, avec l'ID 1)
        so.supprimer(3);

        //afficher
        System.out.println(sp.getAll());
        // Afficher tous les outils
        System.out.println(so.getAll());
    }

}
