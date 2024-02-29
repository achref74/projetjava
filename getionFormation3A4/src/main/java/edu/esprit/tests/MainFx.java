package edu.esprit.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFx extends Application {

    public void start(Stage stage) throws Exception {
<<<<<<<< Updated upstream:getionFormation3A4/src/main/java/edu/esprit/tests/mainFx.java
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageF_Formateur.fxml"));
========
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageF_Back.fxml"));
>>>>>>>> Stashed changes:getionFormation3A4/src/main/java/edu/esprit/tests/MainFx.java
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
