package edu.esprit.controllers;

import edu.esprit.entities.Offre;

import java.util.Timer;
import java.util.TimerTask;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Date;

public class MaillingF {
    static Offre offre;

    // Méthode pour envoyer un e-mail
    public static void envoyerMail(String destinataire, String sujet, String contenu) throws MessagingException {
        // Configuration des propriétés pour l'envoi de l'e-mail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.example.com"); // Remplacez smtp.example.com par votre serveur SMTP
        props.put("mail.smtp.port", "587"); // Utilisez le port SMTP approprié

        // Authentification de l'expéditeur
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("votre_adresse_email@example.com", "votre_mot_de_passe");
            }
        });

        // Création du message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("votre_adresse_email@example.com")); // Remplacez par votre adresse e-mail
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
        message.setSubject(sujet);
        message.setText(contenu);

        // Envoi du message
        Transport.send(message);
    }

    public static void main(String[] args) {
        // Création d'une tâche TimerTask pour vérifier la date de fin de l'offre
        TimerTask tache = new TimerTask() {
            @Override
            public void run() {
                // Obtenez la date actuelle
                Date dateActuelle = new Date();
                // Suppose que offre est votre instance de la classe Offre
                Date dateFinOffre; // Obtenez la date de fin de l'offre
                dateFinOffre = offre.getDateF();

                // Calculer la différence en millisecondes entre les deux dates
                long differenceEnMillis = dateFinOffre.getTime() - dateActuelle.getTime();

                // Convertir la différence en jours
                long differenceEnJours = differenceEnMillis / (1000 * 60 * 60 * 24);

                // Vérifier si la différence est inférieure ou égale à 2 jours
                if (differenceEnJours <= 2) {
                    try {
                        // Si oui, envoyer un e-mail au client
                        envoyerMail("adresse_client@example.com", "Votre offre se termine bientôt",
                                "Votre offre se termine dans 2 jours. Profitez-en dès maintenant !");
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        // Création d'une instance Timer pour planifier la tâche toutes les 24 heures
        Timer timer = new Timer();
        // Planifier la tâche pour commencer immédiatement et se répéter toutes les 24 heures
        timer.schedule(tache, 0, 24 * 60 * 60 * 1000);
    }
}
