package edu.esprit.controllers;

import edu.esprit.entities.Client;
import edu.esprit.entities.Formateur;
import edu.esprit.entities.User;
import edu.esprit.service.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;

public class FormateurCard {
    @FXML
    private Label fxadr;

    @FXML
    private Label fxdate;

    @FXML
    private Label fxgenre;

    @FXML
    private ImageView fximg;

    @FXML
    private Label fxmail;

    @FXML
    private Label fxnivaca;
    @FXML
    private Label fxdis;
    @FXML
    private Label fxspec;

    @FXML
    private Label fxnom;

    @FXML
    private Label fxnum;

    @FXML
    private Label fxtype;
    private final ServiceUser su = new ServiceUser();
    @FXML
    void delete(ActionEvent event) {
        su.supprimer(su.getIdByEmail(fxmail.getText()));
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/adminDisplayView.fxml"));
            fxnum.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
            e.printStackTrace();
        }
    }
    public void setData(User user) throws FileNotFoundException {
        Formateur f = (Formateur)user;
        fxnom.setText(f.getNom()+" "+f.getPrenom());
        fxmail.setText(f.getEmail());
        fxdate.setText(String.valueOf(f.getDateNaissance()));
        fxadr.setText(f.getAdresse());
        fxnum.setText(String.valueOf(f.getNumtel()));
        fxtype.setText("Formateur");
        if (f.getDisponibilite() == 1) fxdis.setText("Dispo");
        else fxdis.setText("non Dispo");
        fxnivaca.setText(f.getNiveauAcademique());
        fxspec.setText(f.getSpecialite());
        fxgenre.setText(f.getGenre());

        String url = f.getImg();
        if(url!=null) {
            File file = new File(url);
            FileInputStream fis = new FileInputStream(file);
            Image image = new Image(fis);
            fximg.setImage(image);
        }

    }


    public void storeMail(){
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/fichiers/cache1.txt");
            myWriter.write(fxmail.getText());
            myWriter.close();
            System.out.println("Successfully wrote to the session file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public void navf(ActionEvent actionEvent) {
        ProfilFormateur.natureaffichage=1;
        storeMail();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profile1.fxml"));
            fxnum.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
            e.printStackTrace();
        }

    }
}
