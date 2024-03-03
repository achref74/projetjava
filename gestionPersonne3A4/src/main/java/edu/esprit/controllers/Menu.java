package edu.esprit.controllers;

import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;
import edu.esprit.entities.Formateur;
import edu.esprit.entities.User;
import edu.esprit.service.ServiceUser;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Menu implements Initializable {

   /* private ScheduledExecutorService executorService;
    private final long timeout = 1; // Temps d'inactivité avant déconnexion, en minutes

    public Menu() {
        startInactivityTimer();
    }

    private void startInactivityTimer() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(this::logout, timeout, TimeUnit.MINUTES);

        // Ajouter un écouteur d'événements à tous les composants interactifs de l'interface utilisateur
        // Pour cet exemple, ajoutons-le simplement au bouton fxprofile
        fxprofile.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Réinitialiser le timer dès qu'il y a une interaction
                if (executorService != null) {
                    executorService.shutdownNow();
                    startInactivityTimer(); // Redémarrer le timer
                }
            }
        });
    }

    private void logout() {
        // Assurez-vous que cette partie s'exécute sur le thread de l'interface utilisateur JavaFX
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
                fxprofile.getScene().setRoot(root);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Impossible de charger l'écran de connexion.");
                alert.setTitle("Erreur");
                alert.show();
            }
        });
    }*/
    @FXML
    private Button fxdeco;

    @FXML
    private Button fxprofile;

    @FXML
    private Label label;

    ServiceUser su = new ServiceUser();
    private Timeline idleTimeline;

    // Définir la durée d'inactivité après laquelle l'utilisateur sera déconnecté (en millisecondes)
    private static final int IDLE_TIME = 120000; // 2 minute

    public void navigatelogin(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
            fxprofile.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

    public void navigateprofile(ActionEvent actionEvent) {
        User session = su.getOneById(Integer.parseInt(getUserId()));
        System.out.println(session);
        if (session instanceof Formateur){
            try {
                System.out.println("hahahah");
                Parent root = FXMLLoader.load(getClass().getResource("/profile1.fxml"));
                fxprofile.getScene().setRoot(root);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Sorry");
                alert.setTitle("Error");
                alert.show();
                e.printStackTrace();
            }
        }
        else if (session instanceof Admin){
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/adminDisplayView.fxml"));
                fxprofile.getScene().setRoot(root);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Sorry");
                alert.setTitle("Error");
                alert.show();
                e.printStackTrace();
            }
        }else if (session instanceof Client){
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/profil2.fxml"));
                fxprofile.getScene().setRoot(root);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Sorry");
                alert.setTitle("Error");
                alert.show();
                e.printStackTrace();
            }
        }
    }

    public String getUserId(){
        String data = null;

        try {
            File file = new File("src/main/resources/fichiers/session.txt");
            Scanner myReader = new Scanner(file);
            if (myReader.hasNextLine()) {
                data = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }
//----------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialiser le timeline pour vérifier l'inactivité de l'utilisateur
        idleTimeline = new Timeline(new KeyFrame(Duration.millis(IDLE_TIME), event -> checkActivity()));
        idleTimeline.setCycleCount(Animation.INDEFINITE);
        startIdleCheck();
    }

    // Méthode appelée lorsqu'un événement de souris est détecté
    @FXML
    private void handleMouseEvent(MouseEvent event) {
        restartIdleCheck();
    }

    // Méthode appelée lorsqu'un événement de clavier est détecté
    @FXML
    private void handleKeyboardEvent() {
        restartIdleCheck();
    }

    // Vérifie s'il y a eu une activité récente et déconnecte l'utilisateur si nécessaire
    private void checkActivity() {
        // Déconnecter l'utilisateur ici
        //disconnect();
        System.out.println("Déconnexion automatique après " + IDLE_TIME / 1000 + " secondes d'inactivité.");
        navigatelogin(null);
    }

    // Redémarrer la vérification d'inactivité
    private void restartIdleCheck() {
        idleTimeline.stop();
        idleTimeline.play();
    }

    // Démarrer la vérification d'inactivité
    private void startIdleCheck() {
        idleTimeline.play();
    }

}
