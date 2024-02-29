package edu.esprit.controllers;

import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;
import edu.esprit.entities.User;
import edu.esprit.service.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ModifierAdmin implements Initializable {
    @FXML
    private TextField adresse1;

    @FXML
    private TextField dt1;

    @FXML
    private TextField email1;

    @FXML
    private Label fxerrormail;

    @FXML
    private Label fxerrornum;

    @FXML
    private ComboBox<String> fxgenre;

    @FXML
    private TextField nom1;

    @FXML
    private TextField numtel1;

    @FXML
    private ImageView photoprofile;

    @FXML
    private TextField prenom1;
    ServiceUser s=new ServiceUser();

    private String selectedgenre;
    private String selectednom;
    private String selectedEmail;
    private String selectedprenom;
    private String selecteddatenaiss;
    private String selectedadresse;
    private String selectednomtel;
    private String selectedphotoprofile;


    User u=s.getOneById(36);




    @FXML
    void choisirphotoprofile(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une photo");
        File selectedFile = fileChooser.showOpenDialog(null);
        String imagepath=null;
        if (selectedFile != null)
            imagepath = selectedFile.getAbsolutePath();

        selectedphotoprofile=imagepath;
        if(selectedphotoprofile!=null) {
            File file = new File(selectedphotoprofile);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Image image = new Image(fis);
            photoprofile.setImage(image);
        }

    }

    @FXML
    void modofoerformateur(ActionEvent event) {
        if (!(s.isValidPhoneNumber(Integer.parseInt(numtel1.getText()))) && !s.isValidEmail(email1.getText())) {

            fxerrornum.setVisible(true);
            fxerrormail.setVisible(true);

        } else if (!s.isValidEmail(email1.getText())) {

            fxerrormail.setVisible(true);
            fxerrornum.setVisible(false);

        } else if (!(s.isValidPhoneNumber(Integer.parseInt(numtel1.getText())))) {
            fxerrornum.setVisible(true);
            fxerrormail.setVisible(false);
        } else {
            Admin  a=new Admin(u.getId(),nom1.getText(),prenom1.getText(),email1.getText(), Date.valueOf(dt1.getText()),adresse1.getText(),Integer.parseInt(numtel1.getText()),u.getMdp(),selectedphotoprofile,fxgenre.getValue());
            s.modifier(a);
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/profil3.fxml"));
                nom1.getScene().setRoot(root);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Sorry");
                alert.setTitle("Error");
                alert.show();
            }

        }

    }

    @FXML
    void retournerFormateur(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profil3.fxml"));
            nom1.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (u instanceof Admin a){
            fxerrormail.setVisible(false);
            fxerrornum.setVisible(false);
            selectednom = "" + a.getNom();
            selectedprenom = a.getPrenom();
            selectedEmail = a.getEmail();
            selecteddatenaiss = String.valueOf(a.getDateNaissance());
            selectedadresse =a.getAdresse();
            selectedgenre =a.getGenre();
            selectednomtel =String.valueOf(a.getNumtel());
            selectedphotoprofile=a.getImg();

            nom1.setText(selectednom);
            prenom1.setText(selectedprenom);
            email1.setText(selectedEmail);
            dt1.setText(selecteddatenaiss);
            adresse1.setText(selectedadresse);
            numtel1.setText(selectednomtel);
            fxgenre.setValue(selectedgenre);
            ObservableList<String> list2= FXCollections.observableArrayList("Homme","Femme","Autre");
            fxgenre.setItems(list2);

            if(selectedphotoprofile!=null) {
                File file = new File(selectedphotoprofile);
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Image image = new Image(fis);
                photoprofile.setImage(image);
            }

        }
    }

}
