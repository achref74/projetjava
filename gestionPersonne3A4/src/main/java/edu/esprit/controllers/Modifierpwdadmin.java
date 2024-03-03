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
import javafx.scene.control.PasswordField;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Modifierpwdadmin implements Initializable {
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
    int id = Integer.parseInt(getUserId());
    Admin u = (Admin) s.getOneById(id);

    @FXML
    void retournerprofile(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profil3.fxml"));
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
        if (!fxancien.getText().isEmpty() && !fxneauvau.getText().isEmpty() && !fxneauvau2.getText().isEmpty()){
            if(!(fxneauvau.getText().equals(fxneauvau2.getText()))&& !(hashof(fxancien.getText()).equals(u.getMdp()))){
                fxn.setVisible(true);
                fxa.setVisible(true);
            } else if (!(fxneauvau.getText().equals(fxneauvau2.getText()))) {
                fxn.setVisible(true);
                fxa.setVisible(false);

            } else if (!(hashof(fxancien.getText()).equals((u.getMdp())))) {
                fxn.setVisible(false);
                fxa.setVisible(true);


            }else{
                Admin c=new Admin(u.getId(),u.getNom(),u.getPrenom(),u.getEmail(),u.getDateNaissance(),u.getAdresse(),u.getNumtel(),hashof(fxneauvau.getText()),u.getImg(),u.getGenre());
                s.modifier(c);
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/profil3.fxml"));
                    fxancien.getScene().setRoot(root);
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Sorry");
                    alert.setTitle("Error");
                    alert.show();
                }

            }

        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Sorry please fill all data");
            alert.setTitle("ERROR");
            alert.show();
        }

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
