package jkesle.crm.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import jkesle.crm.api.appointment.AppointmentApi;
import jkesle.crm.api.appointment.AppointmentApi.AppointmentDELETE;
import jkesle.crm.api.appointment.AppointmentApi.AppointmentGET;
import jkesle.crm.api.customer.CustomerApi;
import jkesle.crm.api.customer.CustomerApi.CustomerDELETE;
import jkesle.crm.api.customer.CustomerApi.CustomerGET;
import jkesle.crm.dto.AppointmentDTO;
import jkesle.crm.dto.CustomerDTO;
import jkesle.crm.parser.appointment.AppointmentResponseParser;
import jkesle.crm.parser.appointment.json.AppointmentJsonParser;
import jkesle.crm.parser.customer.CustomerResponseParser;
import jkesle.crm.parser.customer.json.CustomerJsonParser;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.gson.JsonElement;

/**
 * Controller for the MainMenu form
 *
 * @author Joshua Kesler
 */
public class MainMenuController {

    private CustomerResponseParser<JsonElement> custParser = new CustomerJsonParser();

    private AppointmentResponseParser<JsonElement> apptParser = new AppointmentJsonParser();


    @FXML
    private TableView<CustomerDTO> customerTable;

    @FXML
    private TableColumn custIdCol;

    @FXML
    private TableColumn nameCol;

    @FXML
    private TableColumn addressCol;

    @FXML
    private TableColumn phoneCol;

    @FXML
    private TableColumn postalCol;

    @FXML
    private TableColumn divisionCol;

    @FXML
    private TableView<AppointmentDTO> appointmentTable;

    @FXML
    private TableColumn apptIdCol;

    @FXML
    private TableColumn apptTitleCol;

    @FXML
    private TableColumn apptDescriptionCol;

    @FXML
    private TableColumn apptLocationCol;

    @FXML
    private TableColumn apptContactCol;

    @FXML
    private TableColumn apptTypeCol;

    @FXML
    private TableColumn apptStartCol;

    @FXML
    private TableColumn apptEndCol;

    @FXML
    private TableColumn apptCustIdCol;

    @FXML
    private TableColumn apptUserIdCol;

    @FXML
    private RadioButton monthRadio;

    @FXML
    private TextFlow notificationTextBox;

    @FXML
    private Tab dashboardTab;

    @FXML
    private Tab reportsTab;

    @FXML
    private TableView reportsTable;

    @FXML
    private TableColumn reportCol1;

    @FXML
    private TableColumn reportCol2;

    @FXML
    private TableColumn reportCol3;

    @FXML
    private TableColumn reportCol4;

    @FXML
    private TableColumn reportCol5;

    @FXML
    private TableColumn reportCol6;

    @FXML
    private TableColumn reportCol7;

    @FXML
    private ComboBox monthBox;

    @FXML
    private ComboBox typeBox;

    @FXML
    private RadioButton appointmentsRadio;

    @FXML
    private RadioButton customersRadio;

    @FXML
    private RadioButton contactRadio;

    @FXML
    private ComboBox divBox;

    @FXML
    private ComboBox contactBox;

    @FXML
    private Label reportValueLabel;

    @FXML
    private Label reportDescLabel;

    /**
     * Changes scene from main menu to the AddCustomer page.
     *
     * @param event   <code>ActionEvent</code> triggered by "add customer" button.
     * @throws IOException  if an error occurs during input or output.
     */
    public void addCustomerForm(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("AddCustomer.fxml"));
        Stage addCustomerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addCustomerStage.setScene(new Scene(root));
        addCustomerStage.show();
    }
//     /**
//      * Changes scene from main menu to the UpdateCustomer page.
//      *
//      * @param event   <code>ActionEvent</code> triggered by "update customer" button.
//      * @throws IOException  if an error occurs during input or output.
//      */
//     public void updateCustomerForm(ActionEvent event) throws IOException {
//         selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
//         if(selectedCustomer == null) {
//             Alert alert = new Alert(Alert.AlertType.ERROR);
//             alert.setHeaderText("Select a customer to modify first");
//             alert.showAndWait();
//             return;
//         }
//         Parent root = FXMLLoader.load(getClass().getResource("../view/UpdateCustomer.fxml"));
//         Stage updateCustomerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//         updateCustomerStage.setScene(new Scene(root));
//         updateCustomerStage.show();
//     }
//
//     /**
//      * Changes scene from main menu to the AddAppointment page.
//      *
//      * @param event   <code>ActionEvent</code> triggered by "add appointment" button.
//      * @throws IOException  if an error occurs during input or output.
//      */
//     public void addAppointmentForm(ActionEvent event) throws IOException {
//         Parent root = FXMLLoader.load(getClass().getResource("../view/AddAppointment.fxml"));
//         Stage addAppointmentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//         addAppointmentStage.setScene(new Scene(root));
//         addAppointmentStage.show();
//     }
//     /**
//      * Changes scene from main menu to the UpdateAppointment page.
//      *
//      * @param event   <code>ActionEvent</code> triggered by "update appointment" button.
//      * @throws IOException  if an error occurs during input or output.
//      */
//     public void updateAppointmentForm(ActionEvent event) throws IOException {
//         selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
//         if(selectedAppointment == null) {
//             Alert alert = new Alert(Alert.AlertType.ERROR);
//             alert.setHeaderText("Select an appointment to modify first");
//             alert.showAndWait();
//             return;
//         }
//         Parent root = FXMLLoader.load(getClass().getResource("../view/UpdateAppointment.fxml"));
//         Stage updateAppointmentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//         updateAppointmentStage.setScene(new Scene(root));
//         updateAppointmentStage.show();
//     }

    /**
     * Removes an <code>Appointment</code> from the database
     * and the <code>allAppointments</code> array list.
     * @throws Exception
     */
    public void deleteAppointment() throws Exception {
        AppointmentDTO selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            makeAlert("Select an Appointment to delete first", AlertType.ERROR)
            .showAndWait();
            return;
        }

        Optional<ButtonType> res = makeAlert(String.format("Are you sure you'd like to delete appointment with ID: %s", selectedAppointment.getAppointmentId()), AlertType.INFORMATION)
        .showAndWait();

        if (res.get() == ButtonType.OK) {
            int status = AppointmentDELETE.doAppointmentDELETE(selectedAppointment.getAppointmentId()).join().statusCode();

            if (status != 204) {
                makeAlert("There was an error deleting the appointment from the database", AlertType.ERROR)
                .showAndWait();
                return;
            }

            Alert alert = makeAlert(String.format("Appointment %s was deleted successfully", selectedAppointment.getAppointmentId()), AlertType.INFORMATION);
            constructAppointmentTable();
            alert.showAndWait();
        }
    }

    /**
     * Removes a <code>Customer</code> from the database
     * and the <code>allCustomers</code> array list.
     * @throws Exception
     */
    public void deleteCustomer() throws Exception {
        CustomerDTO selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        List<AppointmentDTO> apptsForCustomer = new ArrayList<>();

        if(selectedCustomer == null) {
            makeAlert("Select a customer to delete first", AlertType.ERROR)
            .showAndWait();
            return;
        }

        AppointmentGET.doGETAppointmentsByCustomer(selectedCustomer.getCustomerId())
        .thenApplyAsync(res -> apptParser.getAppointmentsFromResponse(res.body().toString()))
        .thenAcceptAsync(arr -> arr.getAsJsonArray().forEach(a -> apptsForCustomer.add(apptParser.getAppointmentDTO(a))))
        .join();


        if (!apptsForCustomer.isEmpty()) {
            makeAlert(String.format("Customer with ID: %s has %s appointments still scheduled. Delete them before proceeding",
                selectedCustomer.getCustomerId(), apptsForCustomer.size()), AlertType.ERROR)
                .showAndWait();
            return;
        }

        Optional<ButtonType> res = makeAlert(String.format("Are you sure you'd like to delete customer %s?",
            selectedCustomer.getCustomerId()), AlertType.INFORMATION).showAndWait();

        if (res.get() == ButtonType.OK) {

            int status = CustomerDELETE.doCustomerDELETE(selectedCustomer.getCustomerId()).join().statusCode();

            if (status != 204) {
                makeAlert("There was an error removing the Customer from the database", AlertType.ERROR)
                .showAndWait();
                return;
            }

            Alert alert = makeAlert("Customer has been deleted", AlertType.INFORMATION);
            constructCustomerTable();
            alert.showAndWait();
        }
    }

//     /**
//      * Constructs the <code>allCustomers</code> array and
//      * populates the <code>customerTable</code> tableview.
//      * @throws Exception
//      *
//      * @throws SQLException if error occurs during database connection or query execution.
//      */
    public void constructCustomerTable() throws Exception {
        ObservableList<CustomerDTO> customers = FXCollections.observableArrayList();
        CustomerGET.doGETCustomers()
        .thenApplyAsync(res -> custParser.getCustomersFromResponse(res.body().toString()))
        .thenAcceptAsync(arr -> arr.getAsJsonArray().forEach(c -> customers.add(custParser.getCustomerDTO(c))))
        .join();
        customerTable.setItems(customers);
    }

//     /**
//      * Constructs the <code>allAppointments</code> array and
//      * populates the <code>appointmentTable</code> table.
//      *
//      * @throws SQLException if error occurs during database connection or query execution.
//      */
    public void constructAppointmentTable() throws Exception {
        ObservableList<AppointmentDTO> appointments = FXCollections.observableArrayList();
        AppointmentGET.doGETAppointments()
        .thenApplyAsync(res -> apptParser.getAppointmentsFromResponse(res.body().toString()))
        .thenAcceptAsync(arr -> arr.getAsJsonArray().forEach(a -> appointments.add(apptParser.getAppointmentDTO(a))))
        .join();
        appointmentTable.setItems(appointments);
    }

//     /**
//      * displays all appointments when the "All" radio button is selected
//      */
//     public void filterByAll() {
//         appointmentTable.setItems(allAppointments);
//     }

//     /**
//      * displays either appointments for the 30 days or next 7 days
//      * depending on which radio button is selected in the appointments table
//      * LAMBDA EXPRESSION is used here for increased code readability. Without the lambda expression, the
//      * code would consist of if statements crowding the function, reducing readability.
//      */
//     public void filterAppointments() {
//         filteredAppointments.clear();
//         BigInteger millis = monthRadio.isSelected() ? new BigInteger("2592000000") : new BigInteger("604800000");
//         Instant today = Instant.now();
//         Instant futureTime = Instant.now().plusMillis(millis.longValue());
//         Timestamp now = Timestamp.from(today);
//         Timestamp month = Timestamp.from(futureTime);
//         allAppointments.stream().filter(appt -> appt.getStartDateTime()
//                 .before(month) && appt.getStartDateTime()
//                 .after(now))
//                 .forEach(filteredAppointments::add);

//         appointmentTable.setItems(filteredAppointments);
//     }

//     /**
//      * {@return <code>allAppointments</code> array list} containing all the appointments
//      */
//     public static ObservableList<Appointment> getAllAppointments() {
//         return allAppointments;
//     }

//     /**
//      * Checks if a scheduled appointment is within 15 minutes
//      * of the user's local time upon opening the main menu. Displays
//      * a message to the user in the notification box accordingly. LAMBDA EXPRESSION
//      * is used here for increased code readability. Without the lambda expression, the
//      * code would consist of many bloated, redundant if statements.
//      */
//     public void appointmentWithin15MinutesText() {
//         for (int i = 0; i < notificationTextBox.getChildren().size(); i++) {
//             notificationTextBox.getChildren().remove(i);
//         }
//         Timestamp now = Timestamp.from(Instant.now());
//         Timestamp fifteenMinutesFromNow = Timestamp.from(Instant.now().plusSeconds(900));
//         allAppointments.stream().filter(appt -> User.getUserId() == appt.getUserId())                     // LAMBDA EXPRESSION is used here for increased code readability.
//                 .filter(appt -> appt.getStartTimeAdjusted().before(fifteenMinutesFromNow))// Without it, the code would consist of bloated, redundant if statements
//                 .filter(appt -> appt.getStartTimeAdjusted().after(now))
//                 .forEach(appt -> {
//                     Text notification = new Text();
//                     notification.setFill(Color.RED);
//                     notification.setFont(Font.font("System", FontWeight.BOLD, 13));
//                     notification.setText("\nHEADS UP: Appointment " + String.valueOf(appt.getAppointmentId()) + " starting at " +
//                             appt.getStartTimeAdjusted().toString() + " and ending at " + appt.getEndTimeAdjusted().toString() +
//                             " is within the next fifteen minutes");
//                     notificationTextBox.getChildren().add(notification);
//                 });
//         if (notificationTextBox.getChildren().isEmpty()) {
//             Text noAppointment = new Text("Welcome Back, No appointments within the next fifteen minutes");
//             noAppointment.setFill(Color.GREEN);
//             noAppointment.setFont(Font.font("System", FontWeight.BOLD, 13));
//             notificationTextBox.getChildren().add(noAppointment);
//         }
//     }


        private Alert makeAlert(String message, AlertType type) {
            Alert alert = new Alert(type);
            alert.setHeaderText(message);
            return alert;
        }
//     /**
//      * initializes the customers and appointments tables
//      * @throws SQLException
//      */
        public void initialize() throws Exception {
        custIdCol.setCellValueFactory(new PropertyValueFactory("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory("customerAddress"));
        phoneCol.setCellValueFactory(new PropertyValueFactory("customerPhone"));
        divisionCol.setCellValueFactory(new PropertyValueFactory("division"));
        apptIdCol.setCellValueFactory(new PropertyValueFactory("appointmentId"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory("appointmentTitle"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory("appointmentDescription"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory("appointmentLocation"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory("contactName"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory("appointmentType"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory("appointmentStartDatetime"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory("appointmentEndDatetime"));
        apptCustIdCol.setCellValueFactory(new PropertyValueFactory("customerId"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory("userId"));
        constructCustomerTable();
        constructAppointmentTable();
    }
}
