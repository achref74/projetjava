package edu.esprit.tests;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import edu.esprit.entities.Formation;
import edu.esprit.services.ServiceFormation;

public class Main {
    public static void main(String[] args) {
        ServiceFormation sp = new ServiceFormation();

        //ajouter
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

        //supp
        sp.supprimer(1);

        //afficher
        System.out.println(sp.getAll());
    }

}
