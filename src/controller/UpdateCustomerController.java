package controller;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import model.Customer;
import model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;

/**
 * Controller for UpdateCustomer form
 *
 * @author Joshua Kesler
 */
public class UpdateCustomerController {

    private Customer selectedCustomer = MainMenuController.getSelectedCustomer();

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField postalField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField idField;

    @FXML
    private ComboBox countryBox;

    @FXML
    private ComboBox divisionBox;

    @FXML
    private Label errorLabel;


    @FXML
    private void returnToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        Stage mainForm = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainForm.setScene(new Scene(root));
        mainForm.show();
    }




    @FXML
    private void formatName() {
        UnaryOperator< TextFormatter.Change > filter = change -> {
            String text = change.getText();

            if (text.matches("[A-Za-z ]*")) {
                return change;
            }

            return null;
        };
        nameField.setTextFormatter(new TextFormatter < String > (filter));
    }

    /**
     * checks if TextField is empty
     *
     * @param tf TextField to be checked
     * @return <code>true</code> if empty, <code>false</code> if not empty
     */
    public boolean checkForBlank(TextField tf) {
        return tf.getText().trim() == null || tf.getText().trim().isBlank();
    }

    /**
     *{@return <code>true</code> if any of the TextFields are empty} Sets the <code>errorLabel</code>
     * accordingly
     */
    public boolean checkEmptyFields() {
        errorLabel.setText("");
        TextField[] fields = {nameField, addressField, postalField, phoneField};
        ComboBox[] combos = {countryBox, divisionBox};
        String[] errors = {"Name", "Address", "Postal", "Phone", "Country", "Division"};
        boolean foundError = false;
        for (int k = 0; k < 6; k++) {
            if(k < 4 && checkForBlank(fields[k])) {
                errorLabel.setText(errorLabel.getText() + errors[k] + ", ");
                foundError = true;
            } else if(k == 4 && countryBox.getSelectionModel().isEmpty()) {
                errorLabel.setText(errorLabel.getText() + errors[k] + ", ");
                foundError = true;
            } else if(k==5 && divisionBox.getSelectionModel().isEmpty()) {
                errorLabel.setText(errorLabel.getText() + errors[k] + ", ");
                foundError = true;
            }
        }
        if(foundError) {
            errorLabel.setText(errorLabel.getText() + "field(s) were left blank." );
        }
        return foundError;
    }


    /**
     * fills the <code>divisionBox</code> according to the country selected in
     * the <code>countryBox</code>
     *
     * @throws SQLException if error occurs during database connection or query execution
     */
    public void populateDivisionBox() throws SQLException {
        divisionBox.setValue(null);
        int countryId = -1;
        ObservableList<String> items = FXCollections.observableArrayList();
        Statement statement = JDBC.getConnection().createStatement();
        String query;
        ResultSet rs;
        try {
            if (countryBox.getValue() == null) query = "SELECT Division from first_level_divisions;";
            else {
                switch (countryBox.getValue().toString()) {
                    case "United States":
                        countryId = 1;
                        break;
                    case "United Kingdom":
                        countryId = 2;
                        break;
                    case "Canada":
                        countryId = 3;
                        break;
                }
                query = "SELECT Division from first_level_divisions WHERE Country_ID = " + countryId + ";";
            }
            rs = statement.executeQuery(query);
            while (rs.next()) {
                items.add(rs.getString("Division"));
            }
            divisionBox.setItems(items);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * fills the <code>countryBox</code> with the three available countries
     * in the database
     */
    public void populateCountryBox() {
        ObservableList<String> countries = FXCollections.observableArrayList();
        countries.add("United States");
        countries.add("United Kingdom");
        countries.add("Canada");
        countryBox.setItems(countries);
    }

    /**
     * updates customer in the database and moves application
     * stage to the main menu
     *
     * @param event ActionEvent triggered when "Add New Customer" button is pressed
     * @throws SQLException if error occurs during database connection or query execution.
     * @throws IOException if error occurs during input or output
     */
    public void updateCustomer(ActionEvent event) throws SQLException, IOException {
        if (checkEmptyFields()) {
            return;
        }
        Statement statement = JDBC.getConnection().createStatement();
        String query = "UPDATE customers SET Customer_Name = '" + nameField.getText().trim() + "', Address = '" + addressField.getText().trim() + "', Postal_Code ='" + postalField.getText() +
                "', Phone = '" + phoneField.getText() + "', Last_Update = '" + formatTimeUTC() + "', Last_Updated_By = '" + User.getUsername() +
                "', Division_ID = '" + getDivisionIdFromName() + "' WHERE Customer_ID = '" + selectedCustomer.getCustomerId() + "';";
        statement.executeUpdate(query);
        statement.close();
        returnToMain(event);
    }

    public String formatTimeUTC() {
        Instant instant = Instant.now();
        LocalDateTime datetime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        String formatted = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(datetime);
        return formatted;
    }

    /**
     * {@return the <code>divisionId</code>} based upon the division name
     * in the <code>divisionBox</code>
     *
     * @throws SQLException if error occurs during database connection or query execution.
     */
    public int getDivisionIdFromName() throws SQLException {
        Statement divStatement = JDBC.getConnection().createStatement();
        String divQuery = "SELECT Division_ID FROM first_level_divisions WHERE Division = '" + divisionBox.getValue() + "';";
        ResultSet divID = divStatement.executeQuery(divQuery);
        divID.next();
        int divisionId = divID.getInt("Division_ID");
        divID.close();
        return divisionId;
    }

    /**
     * initializes the ComboBoxes in the AddCustomer form and
     * sets TextFields to selected customer's values
     *
     * @throws SQLException if error occurs during database connection or query execution.
     */
    public void initialize() throws SQLException {
        populateCountryBox();
        idField.setPromptText(String.valueOf(selectedCustomer.getCustomerId()));
        nameField.setText(selectedCustomer.getName());
        addressField.setText(selectedCustomer.getAddress());
        postalField.setText(selectedCustomer.getPostal());
        phoneField.setText(selectedCustomer.getPhone());
        if(selectedCustomer.getCountryId() == 1) {
            countryBox.getSelectionModel().select("United States");
        } else if(selectedCustomer.getCountryId() == 2) {
            countryBox.getSelectionModel().select("United Kingdom");
        } else {
            countryBox.getSelectionModel().select("Canada");
        }
        populateDivisionBox();
        divisionBox.getSelectionModel().select(selectedCustomer.getDivisionName());
        formatName();
    }
}

