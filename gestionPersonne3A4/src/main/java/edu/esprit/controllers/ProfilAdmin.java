package edu.esprit.controllers;

import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;
import edu.esprit.service.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ProfilAdmin implements Initializable {
    @FXML
    private Label fxadr;

    @FXML
    private Label fxdate;



    @FXML
    private Label fxgenre;

    @FXML
    private Label fxmail;


    @FXML
    private Label fxnum;



    @FXML
    private Label fxnom;

    @FXML
    private ImageView fximg;

    private final ServiceUser su = new ServiceUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int id = Integer.parseInt(getUserId());
       Admin a = (Admin) su.getOneById(id);
        fxnom.setText(a.getNom()+" "+a.getPrenom());
        fxmail.setText(a.getEmail());
        fxdate.setText(String.valueOf(a.getDateNaissance()));
        fxadr.setText(a.getAdresse());

        fxnum.setText(String.valueOf(a.getNumtel()));


        fxgenre.setText(a.getGenre());

        String url1 = a.getImg();

        if(url1!=null) {
            File file = new File(url1);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Image image = new Image(fis);
            fximg.setImage(image);
        }

    }

    public void navigateToHome(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/adminDisplayView.fxml"));
            fximg.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();

        }

    }

    public void modifierCompte(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/modifierAdmin.fxml"));
            fximg.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();

        }


    }

    public void changerMdp(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/modifierpwdadmin.fxml"));
            fximg.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
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
