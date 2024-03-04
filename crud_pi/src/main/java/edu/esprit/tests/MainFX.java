package edu.esprit.tests;

import edu.esprit.utils.DataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamation.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Gestion Reclamation");
            stage.show();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        if (DataSource.getInstance().getCnx() != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" base de données !");
            alert.setContentText("GG tu est connecter a la base de donner !");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("base de données !");
            alert.setContentText("Échec de la connexion à la base de données");
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
