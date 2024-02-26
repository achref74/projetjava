package edu.esprit.controllers;

import edu.esprit.entities.Client;
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

public class ClientCard {
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
    private Label fxnivsco;

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
        Client c = (Client)user;
        fxnom.setText(c.getNom()+" "+c.getPrenom());
        fxmail.setText(c.getEmail());
        fxdate.setText(String.valueOf(c.getDateNaissance()));
        fxadr.setText(c.getAdresse());
        fxnum.setText(String.valueOf(c.getNumtel()));
        fxtype.setText("Client");
        fxnivsco.setText(c.getNiveau_scolaire());
        fxgenre.setText(c.getGenre());

        String url = c.getImg();
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
    public void navc(ActionEvent actionEvent) {
        ProfilClient.natureaffichage=1;
        storeMail();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profil2.fxml"));
            fxnum.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();

        }
    }
}
