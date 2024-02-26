package edu.esprit.controllers;

import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;
import edu.esprit.entities.Formateur;
import edu.esprit.entities.User;
import edu.esprit.service.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Menu {


    @FXML
    private Button fxdeco;

    @FXML
    private Button fxprofile;

    @FXML
    private Label label;

    ServiceUser su = new ServiceUser();

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


}
