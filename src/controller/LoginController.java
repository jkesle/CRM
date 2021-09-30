package controller;

import helper.JDBC;
import helper.Logger;
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
import model.User;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;

/**
 * Controller for the login page.   Collects
 * user location/time information for use throughout
 * the application.
 *
 * @author Joshua Kesler
 */
public class LoginController {

    @FXML
    private Button loginBtn;

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

    public void login(ActionEvent event) throws SQLException, IOException {
        Statement statement = JDBC.getConnection().createStatement();
        String query = ("SELECT User_Name, Password, User_ID FROM users WHERE User_Name= '" + userNameTF.getText() + "'" +
                " AND Password= '" + passwordTF.getText() + "';");
        ResultSet res = statement.executeQuery(query);
        if (res.next()) {
            User.setUsername(userNameTF.getText());
            User.setUserId(res.getInt("User_ID"));
            Logger.log(userNameTF.getText(), true);
            statement.close();
            res.close();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/MainMenu.fxml")));
            Stage mainForm = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainForm.setScene(new Scene(root));
            mainForm.show();
        } else {
            Logger.log(userNameTF.getText(), false);
            ResourceBundle lang = ResourceBundle.getBundle("resource/login" , locale);
            errorLabel.setText(lang.getString("error"));
            statement.close();
            res.close();
        }
    }


    /**
     * initializes the login screen
     */
    public void initialize() {
        ResourceBundle lang = ResourceBundle.getBundle("resource/login", locale);
        userNameTF.setPromptText(lang.getString("username"));
        passwordTF.setPromptText(lang.getString("password"));
        loginLabel.setText(lang.getString("title"));
        locationLabel1.setText(lang.getString("location"));
        locationLabel2.setText(zoneId.normalized().getDisplayName(TextStyle.FULL, locale));
        loginBtn.setText(lang.getString("button"));

    }
}
