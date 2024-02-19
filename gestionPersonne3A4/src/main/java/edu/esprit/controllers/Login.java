package edu.esprit.controllers;

import edu.esprit.entities.User;
import edu.esprit.service.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Login {
    @FXML
    public Label errormsg;
    @FXML
    private TextField f1;

    @FXML
    private TextField f2;


    @FXML
    private void navigateafficher(ActionEvent event) throws IOException {
        String mail = f1.getText();
        String pass = f2.getText();
        if(pass.isEmpty() || mail.isEmpty()) {

            //erreur ou un champ est vide est traitée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR !!");
            alert.setContentText("PLEASE FILL ALL DATA");
            alert.showAndWait();
        }
        else{
            ServiceUser s=new ServiceUser();
            String a=s.login(mail, pass);
            //ici un test qui verifie que le compte existe ou non
            if(a.equals("client")) {
                errormsg.setText("");
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/afficherApplication.fxml"));
                    f1.getScene().setRoot(root);
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Sorry");
                    alert.setTitle("Error");
                    alert.show();
                }
            }
            if(a.equals("Formateur")) {
                errormsg.setText("");
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/afficherApplication.fxml"));
                    f1.getScene().setRoot(root);
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Sorry");
                    alert.setTitle("Error");
                    alert.show();
                }
            }
            else errormsg.setText("User name or password is incorrect");
        }
}


}
