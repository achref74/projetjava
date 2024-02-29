package edu.esprit.controllers;

import edu.esprit.entities.Client;
import edu.esprit.entities.Formateur;
import edu.esprit.service.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

public class ProfilClient implements Initializable {
    @FXML
    private Label fxadr;

    @FXML
    private Label fxdate;



    @FXML
    private Label fxgenre;

    @FXML
    private Label fxmail;

    @FXML
    private Label fxnivsco;

    @FXML
    private Label fxnum;



    @FXML
    private Label fxnom;

    @FXML
    private ImageView fximg;

    private final ServiceUser su = new ServiceUser();
    public static int natureaffichage=0;
     @Override
     public void initialize(URL url, ResourceBundle resourceBundle) {

         int id=0;
         Client c;
         if (natureaffichage==0)
         {  id = Integer.parseInt(getUserId());
             c = (Client) su.getOneById(id);}
         else{
             id = su.getIdByEmail(getselecteduserUserId());
             c= (Client) su.getOneById(id);}
        fxnom.setText(c.getNom()+" "+c.getPrenom());
        fxmail.setText(c.getEmail());
        fxdate.setText(String.valueOf(c.getDateNaissance()));
        fxadr.setText(c.getAdresse());
        fxnivsco.setText(c.getNiveau_scolaire());
        fxnum.setText(String.valueOf(c.getNumtel()));


        fxgenre.setText(c.getGenre());

        String url1 = c.getImg();
        System.out.println("jjj : "+url1);
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
         natureaffichage=0;

    }

    public void navigateToHome(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/menuApplication.fxml"));
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
            Parent root = FXMLLoader.load(getClass().getResource("/modifierClient.fxml"));
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
            Parent root = FXMLLoader.load(getClass().getResource("/modifierpwdclient.fxml"));
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
    public String getselecteduserUserId(){
        String data = null;

        try {
            File file = new File("src/main/resources/fichiers/cache1.txt");
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

    public void navigateToadmin(ActionEvent actionEvent) {
         try {
        Parent root = FXMLLoader.load(getClass().getResource("/menuAdmin.fxml"));
        fximg.getScene().setRoot(root);
    } catch (IOException e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Sorry");
        alert.setTitle("Error");
        alert.show();
    }

    }
}
