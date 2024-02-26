package edu.esprit.controllers;

import edu.esprit.entities.Formateur;
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
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.Scanner;


public class ModifierFormateur implements Initializable {
    @FXML
    private TextField adresse1;

    @FXML
    private TextField cv;

    @FXML
    private TextField disponibilite1;

    @FXML
    private TextField dt1;

    @FXML
    private TextField email1;



    @FXML
    private TextField niveauaca;

    @FXML
    private TextField nom1;

    @FXML
    private TextField numtel1;

    @FXML
    private TextField prenom1;



    @FXML
    private TextField specialite;
    @FXML
    private TextField photoprofile;

    @FXML
    private ComboBox<String> fxgenre;
ServiceUser s=new ServiceUser();

    private String selectedgenre;
    private String selectednom;
    private String selectedEmail;
    private String selectedprenom;
    private String selecteddatenaiss;
    private String selectedadresse;
    private String selectednomtel;
    private String selectedniveauacademique;
    private String    selectedspecialite   ;
    private String    selecteddisponibilite  ;
    private String selectedcv;
    private String selectedphotoprofile;
     User u=s.getOneById(Integer.parseInt(getUserId()));
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (u instanceof Formateur f){

            selectednom = "" + f.getNom();
            selectedprenom = f.getPrenom();
            selectedEmail = f.getEmail();
            selecteddatenaiss = String.valueOf(f.getDateNaissance());
            selectedadresse =f.getAdresse();
            selectedgenre =f.getGenre();
            selectednomtel =String.valueOf(f.getNumtel());
            selectedniveauacademique =f.getNiveauAcademique();
            selectedspecialite =f.getSpecialite();
            selecteddisponibilite =String .valueOf(f.getDisponibilite());
            selectedcv =f.getCv();
            selectedphotoprofile=f.getImg();

            nom1.setText(selectednom);
            prenom1.setText(selectedprenom);
            email1.setText(selectedEmail);
            dt1.setText(selecteddatenaiss);
            adresse1.setText(selectedadresse);
            numtel1.setText(selectednomtel);
            fxgenre.setValue(selectedgenre);
            niveauaca.setText(selectedniveauacademique);
            disponibilite1.setText(selecteddisponibilite);
            specialite.setText(selectedspecialite);
            cv.setText(selectedcv);
           photoprofile.setText(selectedphotoprofile);
            ObservableList<String> list2= FXCollections.observableArrayList("Homme","Femme","Autre");
            fxgenre.setItems(list2);


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

    public void modofoerformateur(ActionEvent actionEvent) {
        Formateur f=new Formateur(u.getId(),nom1.getText(),prenom1.getText(),email1.getText(),Date.valueOf(dt1.getText()),adresse1.getText(),Integer.parseInt(numtel1.getText()),u.getMdp(),photoprofile.getText(),fxgenre.getValue(),specialite.getText(),niveauaca.getText(),Integer.parseInt(disponibilite1.getText()),cv.getText());
       s.modifier(f);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profile1.fxml"));
            nom1.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

    public void retournerFormateur(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profile1.fxml"));
            nom1.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }
}
