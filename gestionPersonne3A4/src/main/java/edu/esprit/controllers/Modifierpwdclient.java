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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Modifierpwdclient implements Initializable {
    @FXML
    private PasswordField fxancien;

    @FXML
    private PasswordField fxneauvau;

    @FXML
    private PasswordField fxneauvau2;

    @FXML
    private Label fxn;
    @FXML
    private Label fxa;

    ServiceUser s=new ServiceUser();
    Client u= (Client) s.getOneById(Integer.parseInt(getUserId()));

    @FXML
    void retournerprofile(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profil2.fxml"));
            fxancien.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

    @FXML
    void retournerprofile2(ActionEvent event) throws NoSuchAlgorithmException {
        if(!(fxneauvau.getText().equals(fxneauvau2.getText()))&& !(hashof(fxancien.getText()).equals(u.getMdp()))){
            fxn.setVisible(true);
            fxa.setVisible(true);
        } else if (!(fxneauvau.getText().equals(fxneauvau2.getText()))) {
            fxn.setVisible(true);
            fxa.setVisible(false);

        } else if (!(hashof(fxancien.getText()).equals(u.getMdp()))) {
            fxn.setVisible(false);
            fxa.setVisible(true);

        }else{
        Client c=new Client(u.getId(),u.getNom(),u.getPrenom(),u.getEmail(),u.getDateNaissance(),u.getAdresse(),u.getNumtel(),hashof(fxneauvau.getText()),u.getImg(),u.getGenre(),u.getNiveau_scolaire());
        s.modifier(c);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profil2.fxml"));
            fxancien.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }}
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
    private String hashof(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text.getBytes());
        byte[] hash = md.digest();
        return new String(hash);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fxn.setVisible(false);
        fxa.setVisible(false);
    }
}
