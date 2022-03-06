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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import jkesle.crm.api.appointment.AppointmentApi;
import jkesle.crm.api.customer.CustomerApi;
import jkesle.crm.dto.AppointmentDTO;
import jkesle.crm.dto.CustomerDTO;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Controller for the MainMenu form
 *
 * @author Joshua Kesler
 */
public class MainMenuController {

    private final CustomerApi custApi = new CustomerApi();

    private final AppointmentApi apptApi = new AppointmentApi();

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
    private TableView appointmentTable;

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

//     /**
//      * {@return selected <code>Appointment</code>} Used by modify pages
//      * and delete functions.
//      */
//     public static Appointment getSelectedAppointment() { return  selectedAppointment; }

//     /**
//      * Changes scene from main menu to the AddCustomer page.
//      *
//      * @param event   <code>ActionEvent</code> triggered by "add customer" button.
//      * @throws IOException  if an error occurs during input or output.
//      */
//     public void addCustomerForm(ActionEvent event) throws IOException {
//         Parent root = FXMLLoader.load(getClass().getResource("../view/AddCustomer.fxml"));
//         Stage addCustomerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//         addCustomerStage.setScene(new Scene(root));
//         addCustomerStage.show();
//     }
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

//     /**
//      * Removes an <code>Appointment</code> from the database
//      * and the <code>allAppointments</code> array list.
//      *
//      * @throws SQLException  if error occurs with database connection or query execution.
//      */
//     public void deleteAppointment() throws SQLException {
//         selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
//         if (selectedAppointment == null) {
//             Alert alert = new Alert(Alert.AlertType.ERROR);
//             alert.setHeaderText("Select an appointment to delete first");
//             alert.showAndWait();
//             return;
//         }
//         Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
//         confirm.setHeaderText("Are you sure you'd like to delete this appointment?");
//         Optional<ButtonType> res = confirm.showAndWait();
//         if (res.get() == ButtonType.OK) {
//             Statement statement = JDBC.getConnection().createStatement();
//             String query = "DELETE FROM appointments where Appointment_ID = '" + selectedAppointment.getAppointmentId() + "';";
//             statement.executeUpdate(query);
//             Alert info = new Alert(Alert.AlertType.INFORMATION);
//             info.setHeaderText("Appointment " + selectedAppointment.getAppointmentId() + " has been deleted");
//             allAppointments.remove(selectedAppointment);
//             info.showAndWait();
//             filterByAll();
//         } else return;
//     }

//     /**
//      * Removes a <code>Customer</code> from the database
//      * and the <code>allCustomers</code> array list.
//      *
//      * @throws SQLException  if error occurs with database connection or query execution.
//      */
//     public void deleteCustomer() throws SQLException {
//         selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
//         if(selectedCustomer == null) {
//             Alert alert = new Alert(Alert.AlertType.ERROR);
//             alert.setHeaderText("Select a customer to delete first");
//             alert.showAndWait();
//             return;
//         }
//         if (selectedCustomer.checkAppointments()) {
//             Alert warning = new Alert(Alert.AlertType.ERROR);
//             warning.setHeaderText("This customer has 1 or more appointments associated with it. Update them before deletion");
//             warning.showAndWait();
//             return;
//         } else {
//             Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
//             confirm.setHeaderText("Are you sure you'd like to delete this customer?");
//             Optional<ButtonType> res = confirm.showAndWait();
//             if (res.get() == ButtonType.OK) {
//                 Statement statement = JDBC.getConnection().createStatement();
//                 String query = "DELETE FROM customers where Customer_ID = '" + selectedCustomer.getCustomerId() + "'; " +
//                         "ALTER TABLE customers AUTO_INCREMENT = 1;";
//                 statement.executeUpdate(query);
//                 Alert info = new Alert(Alert.AlertType.INFORMATION);
//                 info.setHeaderText("Customer " + selectedCustomer.getName() + " has been deleted");
//                 allCustomers.remove(selectedCustomer);
//                 info.showAndWait();
//                 customerTable.setItems(allCustomers);
//             } else return;
//         }
//     }

//     /**
//      * Constructs the <code>allCustomers</code> array and
//      * populates the <code>customerTable</code> tableview.
//      * @throws Exception
//      *
//      * @throws SQLException if error occurs during database connection or query execution.
//      */
    public void constructCustomerTable() throws Exception {
        ObservableList<CustomerDTO> customers = FXCollections.observableArrayList();
        customers.addAll(custApi.doGETCustomers());
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
        appointments.addAll(apptApi.doGETAppointments());
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

//     /**
//      * initializes the report table that is used to display multiple reports.
//      *
//      * @throws SQLException if error occurs during database connection or query execution.
//      */
//     public void initReportTable() throws SQLException {
//         reportValueLabel.setText("");
//         reportDescLabel.setText("");
//         reportsTable.setItems(null);
//         if (appointmentsRadio.isSelected()) {
//             hiddenColumnsController(false);
//             hiddenBoxController();
//             populateMonthBox();
//             populateTypeBox();
//             monthBox.getSelectionModel().select(0);
//             typeBox.getSelectionModel().select(0);
//             reportCol1.setText("Type");
//             reportCol2.setText("Time");
//             reportCol1.setCellValueFactory(new PropertyValueFactory("type"));
//             reportCol2.setCellValueFactory(new PropertyValueFactory("startTimeAdjusted"));
//         } else if (customersRadio.isSelected()) {
//             hiddenColumnsController(false);
//             hiddenBoxController();
//             populateDivisionBox();
//             divBox.getSelectionModel().select(0);
//             reportCol1.setText("Customer ID");
//             reportCol2.setText("Division");
//             reportCol1.setCellValueFactory(new PropertyValueFactory("customerId"));
//             reportCol2.setCellValueFactory(new PropertyValueFactory("divisionName"));
//         } else if (contactRadio.isSelected()) {
//             hiddenColumnsController(true);
//             hiddenBoxController();
//             populateContactBox();
//             contactBox.getSelectionModel().select(0);
//             reportCol1.setText("Appointment ID");
//             reportCol2.setText("Title");
//             reportCol1.setCellValueFactory(new PropertyValueFactory("appointmentId"));
//             reportCol2.setCellValueFactory(new PropertyValueFactory("title"));
//             reportCol3.setCellValueFactory(new PropertyValueFactory("type"));
//             reportCol4.setCellValueFactory(new PropertyValueFactory("description"));
//             reportCol5.setCellValueFactory(new PropertyValueFactory("startTimeAdjusted"));
//             reportCol6.setCellValueFactory(new PropertyValueFactory("endTimeAdjusted"));
//             reportCol7.setCellValueFactory(new PropertyValueFactory("customerId"));
//         }
//     }

//     /**
//      * generates a report based on "month" and "type" chosen by the user
//      */
//     public void typeAndMonthReport() {
//         reportsTypeAndMonth.clear();
//         for (Appointment appt : allAppointments) {
//             if (appt.getType().equals(typeBox.getSelectionModel().getSelectedItem().toString())) {
//                 if (appt.getStartTimeAdjusted().toLocalDateTime().toLocalDate().getMonth().toString()
//                         .equals(monthBox.getSelectionModel().getSelectedItem().toString().toUpperCase())) {

//                     reportsTypeAndMonth.add(appt);
//                 }
//             }
//         }
//         reportsTable.setItems(reportsTypeAndMonth);
//         reportDescLabel.setText("Total Appointments:");
//         reportValueLabel.setText(String.valueOf(reportsTypeAndMonth.size()));

//     }

//     /**
//      * generates a report based on division selected by the user
//      */
//     public void customerDivisionReport() {
//         customerDivisionReport.clear();
//         for (Customer cust : allCustomers) {
//             if(cust.getDivisionName().equals(divBox.getSelectionModel().getSelectedItem().toString() )) {
//                 customerDivisionReport.add(cust);
//             }
//         }
//         reportsTable.setItems(customerDivisionReport);
//         reportDescLabel.setText("Total Customers:");
//         reportValueLabel.setText(String.valueOf(customerDivisionReport.size()));
//     }

//     /**
//      * generates a schedule for a contact selected by the user
//      */
//     public void contactScheduleReport() {
//         contactScheduleReport.clear();
//         for (Appointment appt : allAppointments) {
//             if (appt.getContactName().equals(contactBox.getSelectionModel().getSelectedItem().toString())) {
//                 contactScheduleReport.add(appt);
//             }
//         }
//         reportsTable.setItems(contactScheduleReport);
//         reportDescLabel.setText("Total Appointments");
//         reportValueLabel.setText(String.valueOf(contactScheduleReport.size()));
//     }

//     /**
//      * selects the report to generate based on the radio button selected
//      */
//     public void generateReport() {
//         if(appointmentsRadio.isSelected()) {
//             typeAndMonthReport();
//             return;
//         } else if (customersRadio.isSelected()) {
//             customerDivisionReport();
//             return;
//         } else if (contactRadio.isSelected()) {
//             contactScheduleReport();
//             return;
//         }
//     }

//     /**
//      * controls the visibility of the columns used
//      * only by the contact schedule report
//      *
//      * @param option <code>true</code> for visibility, <code>false</code>
//      *               for hidden
//      */
//     public void hiddenColumnsController(boolean option) {
//         reportCol3.setVisible(option);
//         reportCol4.setVisible(option);
//         reportCol5.setVisible(option);
//         reportCol6.setVisible(option);
//         reportCol7.setVisible(option);

//     }

//     /**
//      * controls the visibility of ComboBoxes associated with different reports
//      */
//     public void hiddenBoxController() {
//         boolean appt = false;
//         boolean division = false;
//         boolean contact = false;
//         if (appointmentsRadio.isSelected()) appt = true;
//         if (customersRadio.isSelected()) division = true;
//         if (contactRadio.isSelected()) contact = true;
//         monthBox.setVisible(appt);
//         typeBox.setVisible(appt);
//         contactBox.setVisible(contact);
//         divBox.setVisible(division);
//     }

//     /**
//      * fills <code>divBox</code> with a list of all available divisions from database
//      *
//      * @throws SQLException if error occurs during database connection or query execution
//      */
//     public void populateDivisionBox() throws SQLException {
//         ObservableList<String> items = FXCollections.observableArrayList();
//         Statement statement = JDBC.getConnection().createStatement();
//         String query = "SELECT Division from first_level_divisions;";
//         ResultSet rs = statement.executeQuery(query);
//         while (rs.next()) {
//             items.add(rs.getString("Division"));
//         }
//         divBox.setItems(items);
//     }

//     /**
//      * fills <code>monthBox</code> with the months of the year
//      */
//     public void populateMonthBox() {
//         ObservableList<String> months = FXCollections.observableArrayList();
//         months.add("January");
//         months.add("February");
//         months.add("March");
//         months.add("April");
//         months.add("May");
//         months.add("June");
//         months.add("July");
//         months.add("August");
//         months.add("September");
//         months.add("October");
//         months.add("November");
//         months.add("December");
//         monthBox.setItems(months);
//     }

//     /**
//      * fills <code>typeBox</code> with all distinct "type" values from the
//      * appointments table in the database
//      *
//      * @throws SQLException if error occurs during database connection or
//      * query execution
//      */
//     public void populateTypeBox() throws SQLException {
//         ObservableList<String> types = FXCollections.observableArrayList();
//         Statement statement = JDBC.getConnection().createStatement();
//         String query = "SELECT DISTINCT Type AS Type from appointments;";
//         ResultSet rs = statement.executeQuery(query);
//         while (rs.next()) {
//             types.add(rs.getString("Type"));
//         }
//         typeBox.setItems(types);
//     }

//     /**
//      * fills <code>contactBox</code> with all distinct "contact" values from the
//      * appointments table in the database
//      *
//      * @throws SQLException if error occurs during database connection or
//      * query execution
//      */
//     public void populateContactBox() throws SQLException {
//         ObservableList<String> contacts = FXCollections.observableArrayList();
//         Statement statement = JDBC.getConnection().createStatement();
//         String query = "SELECT DISTINCT Contact_Name AS Contact from contacts;";
//         ResultSet rs = statement.executeQuery(query);
//         while (rs.next()) {
//             contacts.add(rs.getString("Contact"));
//         }
//         contactBox.setItems(contacts);
//     }

//     /**
//      * initializes the customers and appointments tables
//      * @throws SQLException
//      */
    public void initialize() throws Exception {
        custIdCol.setCellValueFactory(new PropertyValueFactory("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory("customerAddress"));
        phoneCol.setCellValueFactory(new PropertyValueFactory("customerPhone"));
        postalCol.setCellValueFactory(new PropertyValueFactory("postal"));
        divisionCol.setCellValueFactory(new PropertyValueFactory("division"));
        apptIdCol.setCellValueFactory(new PropertyValueFactory("appointmentId"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory("appointmentTitle"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory("appointmentDescription"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory("appointmentLocation"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory("contactName"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory("appointmentType"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory("startTimeAdjusted"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory("endTimeAdjusted"));
        apptCustIdCol.setCellValueFactory(new PropertyValueFactory("customerId"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory("userId"));
        constructCustomerTable();
        constructAppointmentTable();
    }
}
