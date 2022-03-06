package jkesle.crm.controller;

import jkesle.crm.helper.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;

import com.jfoenix.controls.JFXButton;

import jkesle.crm.api.auth.*;

/**
 * Controller for the login page.   Collects
 * user location/time information for use throughout
 * the application.
 *
 * @author Joshua Kesler
 */
public class LoginController {

    @FXML
    private JFXButton loginBtn;

    @FXML
    private TextField userNameTF;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private Label locationLabel1;

    @FXML
    private Label locationLabel2;

    @FXML
    private Label loginLabel;

    @FXML
    private Label errorLabel;

    public static final Locale locale = Locale.getDefault();

    private static TimeZone timezone = TimeZone.getDefault();

    public static ZoneId zoneId = ZoneId.systemDefault();

    /**
     * gets the time (in milliseconds) of the offset
     * of the user's local timezone.
     *
     * @return offset time in milliseconds
     */
    public static long getOffsetLocalTime() {
        return timezone.getOffset(Calendar.ZONE_OFFSET);
    }

    public void login(ActionEvent event) throws Exception {
        String username = userNameTF.getText();
        String password = passwordTF.getText();
        int response = Auth.doPOST(username, password);
        System.out.println(response);
        if (response == 204) {
            goToMainMenu(event);
        } else {
            errorLabel.setText("Invalid login credentials");
        }
    }

    public void goToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainMenu.fxml"));
        Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        newStage.setScene(new Scene(root));
        newStage.show();
    }


    /**
     * initializes the login screen
     */
    public void initialize() {
        ResourceBundle lang = ResourceBundle.getBundle("login", locale);
        userNameTF.setPromptText(lang.getString("username"));
        passwordTF.setPromptText(lang.getString("password"));
        loginLabel.setText(lang.getString("title"));
        locationLabel1.setText(lang.getString("location"));
        locationLabel2.setText(zoneId.normalized().getDisplayName(TextStyle.FULL, locale));
        loginBtn.setText(lang.getString("button"));

    }
}
