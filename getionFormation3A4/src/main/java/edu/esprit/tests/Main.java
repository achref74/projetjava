package edu.esprit.tests;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.esprit.entities.Formation;
import edu.esprit.entities.Offre;
import edu.esprit.entities.Certificat;

import edu.esprit.services.ServiceFormation;
import edu.esprit.services.ServiceOffre;
import edu.esprit.services.ServiceCertificat;


public class Main {
    public static void main(String[] args) {
        ServiceFormation sp = new ServiceFormation();
        ServiceOffre so = new ServiceOffre();
        ServiceCertificat sc = new ServiceCertificat();

        //ajouter offre

            Date dateD = new Date(2024-03-30);// Remplacez par la date de début souhaitée
            Date dateF =new Date(2024-03-30); // Remplacez par la date de fin souhaitée

        Offre nouvelOffre = new Offre("promo8",50,"reduction speciale",dateD,dateF,8);
        so.ajouter(nouvelOffre,8); // Ajouter l'offre après l'association

        // Ajouter un nouveau certificat

        Date dateObtention = new Date(2024-05-21);

        Certificat nouveauCertificat = new Certificat("Certificat Java", "Maîtrise de la programmation Java", dateObtention, 10,9);
        sc.ajouter(nouveauCertificat);
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
        Formation nouvelleFormation = new Formation("Formation c", "Apprendre la programmation c",dateDebut, dateFin, 100.0, 10,nouveauCertificat,nouvelOffre);
        //Formation nouvelleFormation1 = new Formation("Formation python", "Apprendre python",dateDebut, dateFin, 20, 5);
        sp.ajouter(nouvelleFormation);



        // Modifier une formation existante (par exemple, avec l'ID 1)
        Formation formationAModifier = sp.getOneById(18);
        if (formationAModifier != null) {
            formationAModifier.setNom("Formation Java Avancée");
            formationAModifier.setDescription("Approfondir les connaissances en Java");
            formationAModifier.setPrix(20);
            // Mettez à jour les autres attributs si nécessaire
            sp.modifier(formationAModifier);
            //System.out.println(formationAModifier.getIdFormation());
        }

        // Modifier un offre existant (par exemple, avec l'ID 3)
        Offre offreAModifier = so.getOneById(11);
        if (offreAModifier != null) {
            offreAModifier.setCodePromo("NEWCODE");
            offreAModifier.setPrixOffre(30.0);
            // Mettez à jour les autres attributs si nécessaire
            so.modifier(offreAModifier);
            //System.out.println(offreAModifier.getIdOffre());
        }

        // Modifier un certificat existant (par exemple, avec l'ID 3)
        Certificat certificatAModifier = sc.getOneById(12);
        if (certificatAModifier != null) {
            certificatAModifier.setTitre("Certificat Java Avancé");
            certificatAModifier.setDescription("Approfondissement des connaissances en Java");
            certificatAModifier.setNbrCours(20);
            // Mettez à jour les autres attributs si nécessaire
            sc.modifier(certificatAModifier);
            //System.out.println(certificatAModifier.getIdCertificat());
        }

        //supp
       //
        sp.supprimer(1);
        // Supprimer un outil (par exemple, avec l'ID 1)
        so.supprimer(3);
// Supprimer un certificat (par exemple, avec l'ID 1)
        sc.supprimer(2);
        //afficher
        System.out.println(sp.getAll());
        // Afficher tous les outils
        System.out.println(so.getAll());
        // Afficher tous les certificats
        System.out.println(sc.getAll());
    }

}
