package edu.esprit.tests;

import com.github.sarxos.webcam.Webcam;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;

import javax.imageio.ImageIO;

import java.io.File;

import static javafx.application.Application.launch;


public class MainFX extends Application{
    public void start (Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        stage.setTitle("gestion perso");
        stage.setScene(new Scene(root));
        stage.show();

}
    public static void main(String[] args) {
        launch();
    }
}
