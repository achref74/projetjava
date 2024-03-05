package edu.esprit.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.controlsfx.control.Notifications;

import javax.management.Notification;

public class Test {
    @FXML
    private WebView webView;

    public void initialize() {

       WebEngine webEngine = webView.getEngine();
        webEngine.loadContent(
                "<html>\n" +
                        "  <head>\n" +
                        "    <title>reCAPTCHA demo: Simple page</title>\n" +
                        "    <script src=\"https://www.google.com/recaptcha/enterprise.js\" async defer></script>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <form action=\"\" method=\"POST\">\n" +
                        "      <div class=\"g-recaptcha\" data-sitekey=\"6LeQ84kpAAAAAARSSyqwXB0Tzc4PdKitZ_sOeZME\" data-action=\"LOGIN\"></div>\n" +
                        "      <br/>\n" +
                        "      <input type=\"submit\" value=\"Submit\">\n" +
                        "    </form>\n" +
                        "  </body>\n" +
                        "</html>"
        );
    }
}
