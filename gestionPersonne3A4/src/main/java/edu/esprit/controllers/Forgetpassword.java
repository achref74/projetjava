package edu.esprit.controllers;

import edu.esprit.entities.User;
import edu.esprit.service.TwilloService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Forgetpassword implements Initializable {
    @javafx.fxml.FXML
    private TextField numtel;
    int code;
    User u = new User();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @javafx.fxml.FXML
    public void sendcode(ActionEvent actionEvent) throws IOException {
        try {
            // Attempt to convert the string to an integer
            int number = Integer.parseInt(numtel.getText());
            // If successful, print the converted integer
            System.out.println("The string is convertible to an integer: " + number);
            if(this.code==number)
            {
                FXMLLoader load = new FXMLLoader(getClass().getResource("/enternewpassword.fxml"));
                Parent root =load.load();
                Enternewpassword c2=  load.getController();
                c2.getuser(u);
                Scene ss= new Scene(root);
                Stage s= new Stage();
                s=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                s.setScene(ss);
                s.show();


            }
            else {
                System.out.println("wrong code");
            }
        } catch (NumberFormatException e) {
            // If conversion fails, catch the exception and print a message
            System.out.println("The numtel is not valid");
            Alert a = new Alert(Alert.AlertType.ERROR,"The numtel is not valid");
            a.show();
        }

    }

    public  void getcode (int code,User e)
    {
        this.code=code;
        this.u=e;
    }
}
