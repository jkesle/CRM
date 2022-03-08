package jkesle.crm.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jkesle.crm.api.country.CountryApi.CountryGET;
import jkesle.crm.api.division.DivisionApi.DivisionGET;
import jkesle.crm.dto.CountryDTO;
import jkesle.crm.dto.DivisionDTO;
import jkesle.crm.parser.country.CountryResponseParser;
import jkesle.crm.parser.country.json.CountryJsonParser;
import jkesle.crm.parser.division.DivisionResponseParser;
import jkesle.crm.parser.division.json.DivisionJsonParser;
import javafx.event.ActionEvent;
import javafx.event.EventType;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import javax.swing.ComboBoxModel;

import com.google.gson.JsonElement;

/**
 * Controller for the AddCustomer form
 *
 * @author Joshua Kesler
 */
public class AddCustomerController {

    private DivisionResponseParser<JsonElement> divisionParser = new DivisionJsonParser();

    private CountryResponseParser<JsonElement> countryParser = new CountryJsonParser();

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField postalField;

    @FXML
    private TextField phoneField;

    @FXML
    private ComboBox<CountryDTO> countryBox;

    @FXML
    private ComboBox<DivisionDTO> divisionBox;

    @FXML
    private Label errorLabel;


    @FXML
    private void returnToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage mainForm = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainForm.setScene(new Scene(root));
        mainForm.show();
    }




    @FXML
    private void formatName() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[A-Za-z ]*")) {
                return change;
            }

            return null;
        };
        nameField.setTextFormatter(new TextFormatter <String> (filter));
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
            } else if(k == 4 && combos[0].getSelectionModel().isEmpty()) {
                errorLabel.setText(errorLabel.getText() + errors[k] + ", ");
                foundError = true;
            } else if(k==5 && combos[1].getSelectionModel().isEmpty()) {
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
     * fills the <code>countryBox</code> with the three available countries
     * in the database
     * @throws Exception
     */
    public void populateCountryBox() throws Exception {
        ObservableList<CountryDTO> countries = FXCollections.observableArrayList();
        CountryGET.doGETCountries()
            .thenApplyAsync(res -> countryParser.getCountriesFromResponse(res.body()))
            .thenAcceptAsync(arr -> arr.getAsJsonArray().forEach(c -> countries.add(countryParser.getCountryDTO(c))))
            .join();
        countryBox.setItems(countries);
        countryBox.setCellFactory(list -> new ListCell<CountryDTO>() {
            protected void updateItem(CountryDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                } else {
                    setText(item.getCountryName());
                }
            };
        });
    }

//     /**
//      * adds a new customer to the database and moves application
//      * stage to the main menu
//      *
//      * @param event ActionEvent triggered when "Add New Customer" button is pressed
//      * @throws SQLException if error occurs during database connection or query execution.
//      * @throws IOException if error occurs during input or output
//      */
//     public void addCustomer(ActionEvent event) throws SQLException, IOException {
//         if (checkEmptyFields()) {
//             return;
//         }
//         Statement statement = JDBC.getConnection().createStatement();
//         String query = "INSERT INTO customers VALUES (NULL, '" + nameField.getText().trim() + "', '" + addressField.getText().trim() + "', '" + postalField.getText() + "', '" +
//                 phoneField.getText() + "', '" + formatTimeUTC() + "', '" + User.getUsername() + "', '" + formatTimeUTC() +
//                 "', '" + User.getUsername() + "', '" + getDivisionIdFromName() + "');";
//         statement.executeUpdate(query);
//         statement.close();
//         returnToMain(event);
//     }

//     /**
//      * {@return String current time in UTC} for communicating with the database
//      */
//     public String formatTimeUTC() {
//         Instant instant = Instant.now();
//         LocalDateTime datetime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
//         String formatted = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(datetime);
//         return formatted;
//     }

//     /**
//      * {@return the <code>divisionId</code>} based upon the division name
//      * in the <code>divisionBox</code>
//      *
//      * @throws SQLException if error occurs during database connection or query execution.
//      */
//     public int getDivisionIdFromName() throws SQLException {
//         Statement divStatement = JDBC.getConnection().createStatement();
//         String divQuery = "SELECT Division_ID FROM first_level_divisions WHERE Division = '" + divisionBox.getValue() + "';";
//         ResultSet divID = divStatement.executeQuery(divQuery);
//         divID.next();
//         int divisionId = divID.getInt("Division_ID");
//         divID.close();
//         return divisionId;
//     }

    /**
     * initializes the ComboBoxes in the AddCustomer form
     * @throws Exception
     *
     * @throws SQLException if error occurs during database connection or query execution.
     */
    public void initialize() throws Exception {
        formatName();
        populateCountryBox();
        countryBox.setOnAction(event -> {
            try {
                divisionBox.setItems(getDivisionsByCountry());
            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        divisionBox.setCellFactory(list -> new ListCell<DivisionDTO>() {
                protected void updateItem(DivisionDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) {
                        setText(null);
                    } else {
                        setText(item.getDivisionName());
                    }
                };
            });
    }

    private ObservableList<DivisionDTO> getDivisionsByCountry() throws URISyntaxException {
        ObservableList<DivisionDTO> list = FXCollections.observableArrayList();
        DivisionGET.doGETDivisionsByCountry(countryBox.getSelectionModel().getSelectedItem().getCountryId())
        .thenApplyAsync(res -> divisionParser.getDivisionsFromResponse(res.body()).getAsJsonArray())
        .thenAcceptAsync(arr -> arr.forEach(d -> list.add(divisionParser.getDivisionDTO(d))))
        .join();
        return list;
    }
}
