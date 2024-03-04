package edu.esprit.controllers;

import edu.esprit.entities.User;
import edu.esprit.service.ServiceUser;
import edu.esprit.service.TwilloService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class Sendtothisnumber
{
    @javafx.fxml.FXML
    private TextField numtel;
    ServiceUser u = new ServiceUser();
    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void sendcode(ActionEvent actionEvent) throws IOException {
        try {
            // Attempt to convert the string to an integer
            int number = Integer.parseInt(numtel.getText());
            // If successful, print the converted integer
            System.out.println("The string is convertible to an integer: " + number);
            if (u.verifyphonenumver(number)==null)
            {
                System.out.println("empty");

            }
            else
            {
                Random rand = new Random();
                int code = rand.nextInt((9999 - 1000) + 1) + 1000;
                TwilloService.sendSms("+21624954442", "votre code de recuperer votre mot de passe  est :"+code);

                User e = u.verifyphonenumver(number);
                FXMLLoader load = new FXMLLoader(getClass().getResource("/forgetpassword.fxml"));
                Parent root =load.load();
                Forgetpassword c2=  load.getController();
                c2.getcode(code,e);
                Scene ss= new Scene(root);
                Stage s= new Stage();
                s=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                s.setScene(ss);
                s.show();

            }

        } catch (NumberFormatException e) {
            // If conversion fails, catch the exception and print a message
            System.out.println("The numtel is not valid");
            Alert a = new Alert(Alert.AlertType.ERROR,"The numtel is not valid");
            a.show();
        }
    }
}