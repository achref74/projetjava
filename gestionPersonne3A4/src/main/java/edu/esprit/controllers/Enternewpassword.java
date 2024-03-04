package edu.esprit.controllers;

import edu.esprit.entities.User;
import edu.esprit.service.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Enternewpassword
{
    @javafx.fxml.FXML
    private TextField p1;
    @javafx.fxml.FXML
    private TextField p2;
    User e = new User();
    ServiceUser u = new ServiceUser();
    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void save(ActionEvent actionEvent) throws IOException, NoSuchAlgorithmException {
        if (p1.getText().equals("") || p2.getText().equals(""))
        {
            System.out.println("empty");
        }

       else if (p1.getText().equals(p2.getText()))
        {
            e.setMdp( hashof( p1.getText()));
            u.changepassword(e);
            FXMLLoader load = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root =load.load();
            Login c2=  load.getController();
            Scene ss= new Scene(root);
            Stage s= new Stage();
            s=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            s.setScene(ss);
            s.show();

        }
        else
        {
            System.out.println("not the samme");
        }
    }

    public  void  getuser (User e )
    {
        this.e = e;
    }
    private String hashof(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text.getBytes());
        byte[] hash = md.digest();
        return new String(hash);
    }

}