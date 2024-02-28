package edu.esprit.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFx extends Application {
    public static final String CURRENCY = "$";
   // public void start(Stage stage) throws Exception {FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCours.fxml"));
    public void start(Stage stage) throws Exception {FXMLLoader loader = new FXMLLoader(getClass().getResource("/Market.fxml"));
       // FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Gestion User");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}