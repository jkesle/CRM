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
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Controller for the UpdateAppointment form
 * //FUTURE ENHANCEMENT// reduce code redundancy by
 * making this class a subclass of AddAppointmentController and
 * overriding the addAppointment method to update the database as
 * opposed to adding to it.
 *
 * @author Joshua Kesler
 */
public class UpdateAppointmentController {

    private final ObservableList<Appointment> allAppointments = MainMenuController.getAllAppointments();

    private Appointment selectedAppointment = MainMenuController.getSelectedAppointment();

    @FXML
    private TextField idTF;

    @FXML
    private TextField titleTF;

    @FXML
    private TextField descTF;

    @FXML
    private TextField locationTF;

    @FXML
    private TextField typeTF;

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private ComboBox custIdBox;

    @FXML
    private ComboBox userIdBox;

    @FXML
    private ComboBox contactBox;

    @FXML
    private Label errorLabel;

    /**
     * updates a selected appointment in the database
     *
     * @param event ActionEvent triggered by pressing "confirm" button
     * @throws ParseException if date time is entered in incorrect format
     * @throws SQLException if error occurs during database connection or query execution
     * @throws IOException if error occurs during input or output
     */
    public void updateAppointment(ActionEvent event) throws ParseException, SQLException, IOException {
        try {
            if (isEmptyFields()) return;
            if (isBusinessHours(startDate, startTime) || isBusinessHours(endDate, endTime)) return;
            if (LocalTime.parse(startTime.getText()).isAfter(LocalTime.parse(endTime.getText()))) {
                errorLabel.setText("End time is before start time");
                return;
            }
            if (isOverlap()) return;
            Timestamp newAppointmentStart = convertStringToTimestampLocalTime(startDate, startTime);
            Timestamp newAppointmentEnd = convertStringToTimestampLocalTime(endDate, endTime);
            Statement statement = JDBC.getConnection().createStatement();
            String update = "UPDATE appointments SET Title = '" + titleTF.getText().trim() + "', Description = '" + descTF.getText().trim() +
                    "', Location = '" + locationTF.getText().trim() + "', Type = '" + typeTF.getText().trim() + "', Start = '" +
                    new Timestamp(newAppointmentStart.getTime() - LoginController.getOffsetLocalTime()) + "', End = '" +
                    new Timestamp(newAppointmentEnd.getTime() - LoginController.getOffsetLocalTime()) +
                    "', Last_Update = '" + Timestamp.from(Instant.now().minusMillis(LoginController.getOffsetLocalTime())) + "', Last_Updated_By = '" +
                    User.getUsername() + "', Customer_ID = '" + custIdBox.getSelectionModel().getSelectedItem().toString() + "', User_ID = '" +
                    userIdBox.getSelectionModel().getSelectedItem().toString() + "', Contact_ID = '" + getContactId(contactBox.getSelectionModel().getSelectedItem().toString()) +
                    "' WHERE Appointment_ID = '" + selectedAppointment.getAppointmentId() + "';";

            statement.executeUpdate(update);
            statement.close();
            allAppointments.clear();
            returnToMain(event);
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Time field must be entered in the format HH:mm EX.) 15:45 or 07:33");
            alert.showAndWait();
        }
    }


    public String getContactId(String contactName) throws SQLException {
        Statement statement = JDBC.getConnection().createStatement();
        String query = "SELECT Contact_ID FROM contacts WHERE Contact_Name = '" + contactName + "';";
        ResultSet rs = statement.executeQuery(query);
        rs.next();
        return rs.getString("Contact_ID");
    }

    /**
     * @see AddAppointmentController#populateUserIdBox()
     * @throws SQLException
     */
    public void populateUserIdBox() throws SQLException {
        ObservableList<String> userIds = FXCollections.observableArrayList();
        Statement statement = JDBC.getConnection().createStatement();
        String query = "SELECT User_ID from users;";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            userIds.add(rs.getString("User_ID"));
        }
        userIdBox.setItems(userIds);
    }

    /**
     * @see AddAppointmentController#populateContactNameBox()
     * @throws SQLException
     */
    public void populateContactNameBox() throws SQLException {
        ObservableList<String> contacts = FXCollections.observableArrayList();
        Statement statement = JDBC.getConnection().createStatement();
        String query = "SELECT Contact_Name from contacts;";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            contacts.add(rs.getString("Contact_Name"));
        }
        contactBox.setItems(contacts);
    }

    /**
     * @see AddAppointmentController#populateCustomerIdBox()
     * @throws SQLException
     */
    public void populateCustomerIdBox() throws SQLException {
        ObservableList<String> customerIds = FXCollections.observableArrayList();
        Statement statement = JDBC.getConnection().createStatement();
        String query = "SELECT Customer_ID from customers;";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            customerIds.add(rs.getString("Customer_ID"));
        }
        custIdBox.setItems(customerIds);
    }


    /**
     * checks if provided date time is inside the business hours of the
     * company EST
     *
     * @param date DatePicker to extract date from
     * @param time TextField to extract time from
     * @return <code>true</code> if outside business hours,
     * <code>false</code> if inside business hours
     * @throws ParseException if date and time are not entered in correct format
     */
    public boolean isBusinessHours(DatePicker date, TextField time) throws ParseException {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date parsedDate = dateFormat.parse(date.getValue().toString() + " " + time.getText().trim());
            Date closedTime = dateFormat.parse(date.getValue().toString() + " " + "22:00");
            Timestamp closed = new Timestamp(closedTime.getTime());
            Date openTime = (dateFormat.parse(date.getValue().toString() + " " + "08:00"));
            Timestamp open = new Timestamp(openTime.getTime());
            Timestamp est = new Timestamp(parsedDate.getTime() - LoginController.getOffsetLocalTime() - 18000000);
            if (est.after(closed) || est.before(open)) {
                errorLabel.setText("Appointment time out of business hours EST");
                return true;
            }
        } catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Time field must be entered in the format HH:mm EX.) 15:45 or 07:33");
            alert.showAndWait();
        }
        return false;
    }


    /**
     * checks for empty fields and sets <code>errorLabel</code> accordingly
     *
     * @return <code>true</code> if field is empty, <code>false</code> if
     * fields are filled
     */
    public boolean isEmptyFields() {
        errorLabel.setText("");
        TextField[] fields = {titleTF, descTF, locationTF, typeTF, startTime, endTime};
        ComboBox[] combos = {custIdBox, userIdBox, contactBox};
        String[] errors = {"Title", "Description", "Location", "Type", "Start Time", "End Time", "Customer ID", "User ID", "Contact Name"};
        boolean foundError = false;
        for (int k = 0; k < 9; k++) {
            if (k < 6 && checkForBlank(fields[k]) || (k == 6 && combos[0].getSelectionModel().isEmpty()) ||
                    (k == 7 && combos[1].getSelectionModel().isEmpty()) || (k == 8 && combos[2].getSelectionModel().isEmpty())) {
                errorLabel.setText(errorLabel.getText() + errors[k] + ", ");
                foundError = true;
            }
        }
        if(foundError) {
            errorLabel.setText(errorLabel.getText() + "field(s) were left empty." );
        }
        return foundError;
    }

    /**
     * determines if a customer is already scheduled for an appointment
     * at the selected times
     *
     * @return <code>true</code> if overlap exists, <code>false</code> if
     * no overlap exists
     * @throws ParseException if date time is not entered in correct format
     */
    public boolean isOverlap() throws ParseException {
        Timestamp start = convertStringToTimestampLocalTime(startDate, startTime);
        Timestamp end = convertStringToTimestampLocalTime(endDate, endTime);
        for (Appointment appt : allAppointments) {
            if(appt.equals(selectedAppointment)) {
                continue;
            }
            if (Integer.parseInt(custIdBox.getSelectionModel().getSelectedItem().toString()) == appt.getCustomerId()) {
                if (start.before(appt.getEndTimeAdjusted()) && start.after(appt.getStartTimeAdjusted()) ||
                        end.after(appt.getStartTimeAdjusted()) && end.before(appt.getEndTimeAdjusted())) {

                    errorLabel.setText("Selected customer already has appointment scheduled at selected times");
                    return true;
                }
            }
        }
        return false;
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

    @FXML
    private void returnToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        Stage mainForm = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainForm.setScene(new Scene(root));
        mainForm.show();
    }

    /**
     * converts string to a timestamp in the user's local timezone
     *
     * @param date DatePicker to extract date from
     * @param time TextField to extract time from
     * @return <code>Timestamp</code> in the local time
     * @throws ParseException if date time are entered in incorrect format
     */
    public Timestamp convertStringToTimestampLocalTime(DatePicker date, TextField time) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date parsedDate = dateFormat.parse(date.getValue().toString() + " " + time.getText().trim());
        Timestamp timestamp = new Timestamp(parsedDate.getTime());
        return timestamp;

    }

    /**
     * initialize ComboBoxes with valid values and TextFields with
     * selected appointment's values
     *
     * @throws SQLException if error occurs during database connection or query execution
     */
    public void initialize() throws SQLException {
        populateUserIdBox();
        populateContactNameBox();
        populateCustomerIdBox();
        idTF.setPromptText(String.valueOf(selectedAppointment.getAppointmentId()));
        titleTF.setText(selectedAppointment.getTitle());
        descTF.setText(selectedAppointment.getDescription());
        typeTF.setText(selectedAppointment.getType());
        locationTF.setText(selectedAppointment.getLocation());
        startDate.setValue(selectedAppointment.getStartTimeAdjusted().toLocalDateTime().toLocalDate());
        endDate.setValue(selectedAppointment.getEndTimeAdjusted().toLocalDateTime().toLocalDate());
        startTime.setText(selectedAppointment.getStartTimeAdjusted().toLocalDateTime().toLocalTime().toString());
        endTime.setText(selectedAppointment.getEndTimeAdjusted().toLocalDateTime().toLocalTime().toString());
        custIdBox.getSelectionModel().select(String.valueOf(selectedAppointment.getCustomerId()));
        userIdBox.getSelectionModel().select(String.valueOf(selectedAppointment.getUserId()));
        contactBox.getSelectionModel().select(selectedAppointment.getContactName());
    }
}
