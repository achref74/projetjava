package edu.esprit.controllers;

import edu.esprit.entities.Client;
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


public class ModifierClient implements Initializable {
    @FXML
    private ComboBox<String> fxnivsco;
    @FXML
    private ImageView photoprofile;
    @FXML
    private Label fxerrornum;
    @FXML
    private Label fxerrormail;
    @FXML
    private ComboBox<String> fxgenre;
    @FXML
    private TextField numtel1;
    @FXML
    private TextField adresse1;
    @FXML
    private TextField dt1;
    @FXML
    private TextField email1;
    @FXML
    private TextField prenom1;
    @FXML
    private TextField nom1;
    ServiceUser s=new ServiceUser();

    private String selectedgenre;
    private String selectednom;
    private String selectedEmail;
    private String selectedprenom;
    private String selecteddatenaiss;
    private String selectedadresse;
    private String selectednomtel;
    private String selectedphotoprofile;
    private String selectedniveauscolaire;

    User u=s.getOneById(Integer.parseInt(getUserId()));

    public void modofoerformateur(ActionEvent actionEvent) {
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
            Client  c=new Client(u.getId(),nom1.getText(),prenom1.getText(),email1.getText(), Date.valueOf(dt1.getText()),adresse1.getText(),Integer.parseInt(numtel1.getText()),u.getMdp(),selectedphotoprofile,fxgenre.getValue(),selectedniveauscolaire);
            s.modifier(c);
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/profil2.fxml"));
                nom1.getScene().setRoot(root);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Sorry");
                alert.setTitle("Error");
                alert.show();
            }

        }
    }

    public void retournerFormateur(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profil2.fxml"));
            nom1.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    public void choisirphotoprofile(MouseEvent mouseEvent) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (u instanceof Client c){
            fxerrormail.setVisible(false);
            fxerrornum.setVisible(false);
            selectednom = "" + c.getNom();
            selectedprenom = c.getPrenom();
            selectedEmail = c.getEmail();
            selecteddatenaiss = String.valueOf(c.getDateNaissance());
            selectedadresse =c.getAdresse();
            selectedgenre =c.getGenre();
            selectednomtel =String.valueOf(c.getNumtel());
            selectedphotoprofile=c.getImg();
            selectedniveauscolaire=c.getNiveau_scolaire();
            nom1.setText(selectednom);
            prenom1.setText(selectedprenom);
            email1.setText(selectedEmail);
            dt1.setText(selecteddatenaiss);
            adresse1.setText(selectedadresse);
            numtel1.setText(selectednomtel);
            fxgenre.setValue(selectedgenre);
            ObservableList<String> list2= FXCollections.observableArrayList("Homme","Femme","Autre");
            fxgenre.setItems(list2);
            fxnivsco.setValue(selectedniveauscolaire);
            ObservableList<String> list= FXCollections.observableArrayList("Ecole","College","Lycee");
            fxnivsco.setItems(list);
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
