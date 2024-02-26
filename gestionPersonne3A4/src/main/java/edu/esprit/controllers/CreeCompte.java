package edu.esprit.controllers;

import edu.esprit.service.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;

public class CreeCompte {
    @FXML
    private TextField fxadresse;

    @FXML
    private RadioButton fxc;

    @FXML
    private DatePicker fxdate;

    @FXML
    private TextField fxemail;

    @FXML
    private Label fxerrormail;

    @FXML
    private RadioButton fxf;

    @FXML
    private TextField fxnom;

    @FXML
    private TextField fxnumtel;

    @FXML
    private Label fxerrornum;
    @FXML
    private TextField fxprenom;
    private final ServiceUser sp = new ServiceUser();
    String userType = null;

    @FXML
    void clickClient(ActionEvent event) {
        userType = "client";
        fxf.setSelected(false);

    }

    @FXML
    void clickFormateur(ActionEvent event) {
        fxc.setSelected(false);
        userType = "formateur";


    }

    @FXML
    void createAccount(ActionEvent event) {
        try {
            if (!(sp.isValidPhoneNumber(Integer.parseInt(fxnumtel.getText()))) && !sp.isValidEmail(fxemail.getText())) {

                fxerrornum.setVisible(true);
                fxerrormail.setVisible(true);

            } else if (!sp.isValidEmail(fxemail.getText())) {

                fxerrormail.setVisible(true);
                fxerrornum.setVisible(false);

            } else if (!(sp.isValidPhoneNumber(Integer.parseInt(fxnumtel.getText())))) {
                fxerrornum.setVisible(true);
                fxerrormail.setVisible(false);
            } else {
                fxerrornum.setVisible(false);
                fxerrormail.setVisible(false);
                storeData(userType, fxnom.getText(),fxprenom.getText(),fxemail.getText(),
                        Date.valueOf(fxdate.getValue()),fxadresse.getText(),Integer.parseInt(fxnumtel.getText()));
                if (userType.equals("client")){
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/ajoutClient.fxml"));
                        fxnom.getScene().setRoot(root);
                    } catch (IOException e) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Sorry");
                        alert.setTitle("Error");
                        alert.show();

                    }

                }else if (userType.equals("formateur")){
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/ajoutFormateur.fxml"));
                        fxnom.getScene().setRoot(root);
                    } catch (IOException e) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Sorry");
                        alert.setTitle("Error");
                        alert.show();

                    }

                }


            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void storeData(String userType, String nom, String prenom, String email, Date date, String adresse, int numtel) {
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/fichiers/cache.txt");
            myWriter.write(nom+"&&"+prenom+"&&"+email+"&&"+String.valueOf(date)+"&&"+adresse+"&&"+String.valueOf(numtel));
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }



}