package ViewControllers;

import Handlers.DatabaseHandler;
import MainPackage.Main;
import Models.Appointment;
import Models.Customer;
import Models.TimeKeeper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

/**
 * Main form controller, this is the main menu screen logic
 *
 * In the populatetables() method I use multiple lambda expressions
 *  I use lambda expression here to fill the columns of each table. The first type of lambda expression I use is simply to convert a integers to
 *  a simpleintegerproperty and strings to simplestringproperty to fill my tableview columns. The second lambda expression I use is to process and assign a time
 *  value using a static function from my TimeKeeper class and insert it into the time columns of the tableview.
 */
public class MainFormController implements Initializable {

    public TableView customerTableView;

    public TableColumn<Customer, Integer> customerIDCol;
    public TableColumn<Customer, String> customerNameCol;
    public TableColumn<Customer, String> customerAddressCol;
    public TableColumn<Customer, String> customerPostalCodeCol;
    public TableColumn<Customer, String> customerPhoneNumberCol;
    public Button addCustomerButton;
    public Button modifyCustomerButton;
    public Button removeCustomerButton;
    public AnchorPane anchorPane;

    public TableView appointmentTableView;

    public TableColumn<Appointment, Integer> appointmentIDCol;
    public TableColumn<Appointment, String> appointmentTitleCol;
    public TableColumn<Appointment, String> appointmentDescriptionCol;
    public TableColumn<Appointment, String> appointmentLocationCol;
    public TableColumn<Appointment, String> appointmentContactCol;
    public TableColumn<Appointment, String> appointmentTypeCol;
    public TableColumn<Appointment, String> appointmentStartCol;
    public TableColumn<Appointment, String> appointmentEndCol;
    public TableColumn<Appointment, Integer> appointmentCustomerIDCol;
    public Button addAppointmentButton;
    public Button modifyAppointmentButton;
    public Button removeAppointmentButton;


    public static int currentlySelectedCustomerIndex = -1;
    public static int currentlySelectedAppointmentIndex = -1;
    public RadioButton appointmentWeekRadioButton;
    public RadioButton appointmentMonthRadioButton;
    public RadioButton appointmentAllRadioButton;


    /**
     * Handle when the add customer button is clicked
     * @param event
     */
    public void handleAddCustomer(ActionEvent event) {

        Dialog<ButtonType> dialog = new Dialog();
        dialog.initOwner(anchorPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AddCustomerForm.fxml"));

        try {

            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.showAndWait();
            update();

        } catch(IOException | ParseException e) {

            System.out.println("Error: " + e.getMessage());
        }

    }

    /**
     * Handle when the modify customer button is clicked
     * @param event
     */
    public void handleModifyCustomer(ActionEvent event) {

        if (currentlySelectedCustomerIndex >= 0) {

            Dialog<ButtonType> dialog = new Dialog();
            dialog.initOwner(anchorPane.getScene().getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ModifyCustomerForm.fxml"));
            currentlySelectedCustomerIndex = customerTableView.getSelectionModel().getSelectedIndex();


            try {

                dialog.getDialogPane().setContent(fxmlLoader.load());
                dialog.showAndWait();
                update();

            } catch(IOException | ParseException e) {

                System.out.println("Error: " + e.getMessage());
            }

        } else {

            Main.createAlert("Error!","Please select a customer to modify!");

        }



    }

    /**
     * Handle when the remove customer button is clicked
     * @param event
     */
    public void handleRemoveCustomer(ActionEvent event) throws ParseException {

        if (currentlySelectedCustomerIndex >= 0)
            DatabaseHandler.deleteCustomer(DatabaseHandler.customerList.get(currentlySelectedCustomerIndex).getCustomerID());
        else
            Main.createAlert("Error!","Please select a customer to remove!");



        update();

    }

    /**
     * Handle when add appointment button is clicked
     * @param event
     */
    public void handleAddAppointment(ActionEvent event) {

        Dialog<ButtonType> dialog = new Dialog();
        dialog.initOwner(anchorPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AddAppointmentForm.fxml"));

        try {

            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.showAndWait();
            update();

        } catch(IOException | ParseException e) {

            System.out.println("Error: " + e.getMessage());
        }

    }

    /**
     * Handle when modify appointment button is clicked
     * @param event
     */
    public void handleModifyAppointment(ActionEvent event) {

        if (currentlySelectedAppointmentIndex >= 0) {

            Dialog<ButtonType> dialog = new Dialog();
            dialog.initOwner(anchorPane.getScene().getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ModifyAppointmentForm.fxml"));
            currentlySelectedAppointmentIndex = appointmentTableView.getSelectionModel().getSelectedIndex();


            try {

                dialog.getDialogPane().setContent(fxmlLoader.load());
                dialog.showAndWait();
                update();

            } catch(IOException | ParseException e) {

                System.out.println("Error: " + e.getMessage());
            }

        } else {

            Main.createAlert("Error!","Please select aan appointment to modify!");

        }

    }

    /**
     * Handle when the remove appointment button is clicked
     * @param event
     */
    public void handleRemoveAppointment(ActionEvent event) throws ParseException {

        if (currentlySelectedAppointmentIndex >= 0) {

           DatabaseHandler.deleteAppointment(DatabaseHandler.appointmentList.get(currentlySelectedAppointmentIndex).getAppointmentId());
           Main.createAlert("Success!", "Appointment type " + DatabaseHandler.appointmentList.get(currentlySelectedAppointmentIndex).getAppointmentType() + " with Appointment ID " + DatabaseHandler.appointmentList.get(currentlySelectedAppointmentIndex).getAppointmentId() + " was deleted!");

        }

        else
            Main.createAlert("Error!","Please select an appointment to remove!");

        update();
    }

    /**
     * Updates information
     * @throws ParseException
     */
    public void update() throws ParseException {

        DatabaseHandler.getAllCustomers();
        DatabaseHandler.getCountryList();
        DatabaseHandler.getContactList();
        DatabaseHandler.getAllAppointmentList();

        Date date = Date.from(Instant.now());

        Date dateInOneWeek = Date.from(Instant.now().plusMillis(TimeKeeper.ONE_WEEK_IN_MILLIS));

        if (appointmentAllRadioButton.isSelected()) {
            DatabaseHandler.sortedAppointmentList.clear();

            for (int i = 0; i < DatabaseHandler.appointmentList.size(); i++) {

                DatabaseHandler.sortedAppointmentList.add(DatabaseHandler.appointmentList.get(i));

            }

        } else if (appointmentMonthRadioButton.isSelected()) {

            DatabaseHandler.sortedAppointmentList.clear();

            for (int i = 0; i < DatabaseHandler.appointmentList.size(); i++) {

                if(TimeKeeper.stringToDate(DatabaseHandler.appointmentList.get(i).getAppointmentStart()).getMonth() == date.getMonth()) {

                    DatabaseHandler.sortedAppointmentList.add(DatabaseHandler.appointmentList.get(i));

                }

            }

        } else if (appointmentWeekRadioButton.isSelected()) {

            DatabaseHandler.sortedAppointmentList.clear();

            for (int i = 0; i < DatabaseHandler.appointmentList.size(); i++) {

                if(TimeKeeper.stringToDate(DatabaseHandler.appointmentList.get(i).getAppointmentStart()).getTime() <= dateInOneWeek.getTime()) {

                    System.out.println("Time in one week: " + dateInOneWeek.getTime());
                    DatabaseHandler.sortedAppointmentList.add(DatabaseHandler.appointmentList.get(i));

                }

            }

        }

        currentlySelectedCustomerIndex = -1;
        currentlySelectedAppointmentIndex = -1;

        populateTables();

    }

    /**
     * Called to populate all of the table views
     *
     * I use lambda expression here to fill the columns of each table. The first type of lambda expression I use is simply to convert a integers to
     * a simpleintegerproperty and strings to simplestringproperty to fill my tableview columns. The second lambda expression I use is to process and assign a time
     * value using a static function from my TimeKeeper class and insert it into the time columns of the tableview.
     *
     */
    public void populateTables() {

        //Populate customer table
        customerIDCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCustomerID()).asObject());
        customerNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        customerAddressCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomerAddress()));
        customerPostalCodeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomerZip()));
        customerPhoneNumberCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomerPhone()));

        //Populate appointment table
        appointmentIDCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAppointmentId()).asObject());
        appointmentTitleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAppointmentTitle()));
        appointmentDescriptionCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAppointmentDescription()));
        appointmentLocationCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAppointmentLocation()));
        appointmentContactCol.setCellValueFactory(data -> new SimpleStringProperty(DatabaseHandler.contactList.get(data.getValue().getAppointmentContactId() - 1).getContactName()));
        appointmentTypeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAppointmentType()));

        appointmentStartCol.setCellValueFactory(data -> {
            try {

                return new SimpleStringProperty(TimeKeeper.dateStringFromUTC(data.getValue().getAppointmentStart()));
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        );

        appointmentEndCol.setCellValueFactory(data -> {
            try {

                return new SimpleStringProperty(TimeKeeper.dateStringFromUTC(data.getValue().getAppointmentEnd()));
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        );

        appointmentCustomerIDCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAppointmentCustomerId()).asObject());

        customerTableView.setItems(DatabaseHandler.customerList);
        appointmentTableView.setItems(DatabaseHandler.sortedAppointmentList);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Locale loc = Locale.getDefault();

        resourceBundle = ResourceBundle.getBundle("language/login", loc);

        customerIDCol.setText(resourceBundle.getString("customerIDColText"));
        customerNameCol.setText(resourceBundle.getString("customerNameColText"));
        customerAddressCol.setText(resourceBundle.getString("customerAddressColText"));
        customerPostalCodeCol.setText(resourceBundle.getString("customerPostalCodeColText"));
        customerPhoneNumberCol.setText(resourceBundle.getString("customerPhoneNumberColText"));

        addCustomerButton.setText(resourceBundle.getString("addCustomerButtonText"));
        modifyCustomerButton.setText(resourceBundle.getString("modifyCustomerButtonText"));
        removeCustomerButton.setText(resourceBundle.getString("removeCustomerButtonText"));


        try {
            appointmentAllRadioButton.setSelected(true);
            update();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            upcomingAppointment();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        DatabaseHandler.typeList.clear();

        DatabaseHandler.typeList.add("Consultation Saving");

        DatabaseHandler.typeList.add("Consultation Tax prep");

        DatabaseHandler.typeList.add("Consultation Banking");

    }

    /**
     * Called every time the user clicks the customer table
     * @param mouseEvent
     */
    public void handleCustomerTableClick(MouseEvent mouseEvent) {

        System.out.println(currentlySelectedCustomerIndex = customerTableView.getSelectionModel().getSelectedIndex());

        if (customerTableView.getSelectionModel().getSelectedIndex() != -1) {

            currentlySelectedCustomerIndex = customerTableView.getSelectionModel().getSelectedIndex();
            //DatabaseHandler.getAppointmentList(DatabaseHandler.customerList.get(currentlySelectedCustomerIndex).getCustomerID());

        }

    }

    /**
     * Called every time the user clicks the appointment table
     * @param mouseEvent
     */
    public void handleAppointmentTableClick(MouseEvent mouseEvent) {

        System.out.println(currentlySelectedAppointmentIndex = appointmentTableView.getSelectionModel().getSelectedIndex());

        if (appointmentTableView.getSelectionModel().getSelectedIndex() != -1) {

            currentlySelectedAppointmentIndex = appointmentTableView.getSelectionModel().getSelectedIndex();

        }
    }

    /**
     * Check if there is an upcoming appointment
     */
    public boolean upcomingAppointment() throws ParseException {

        //Check for upcoming appointment

        Date datenow = Date.from(Instant.now());

        Date dateinfifteen = Date.from(Instant.now().plusMillis(15 * TimeKeeper.ONE_MINUTE_IN_MILLIS));

        SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        String outputDateString = formatter.format(dateinfifteen);
        String outputDateNowString = formatter.format(datenow);

        Date outputFutureDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(outputDateString);
        Date outputCurrentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(outputDateNowString);

        for (int i = 0; i < DatabaseHandler.appointmentList.size(); i++) {

            if (outputFutureDate.getTime() >= TimeKeeper.stringToDate(DatabaseHandler.appointmentList.get(i).getAppointmentStart()).getTime() && outputCurrentDate.getTime() <= (TimeKeeper.stringToDate(DatabaseHandler.appointmentList.get(i).getAppointmentStart()).getTime())) {

                Main.createAlert("Appointment!", "You have an appointment at " + TimeKeeper.dateStringFromUTC(DatabaseHandler.appointmentList.get(i).getAppointmentStart()) +
                        " AppointmentID " + DatabaseHandler.appointmentList.get(i).getAppointmentId());
                return true;

            }


        }

        Main.createAlert("Appointment!", "You have no appointments in the next 15 minutes!");
        return false;

    }

    /**
     * Logic for when radio button is clicked
     * @param event
     * @throws ParseException
     */
    public void handleWeekRadioClick(ActionEvent event) throws ParseException {

        appointmentMonthRadioButton.setSelected(false);
        appointmentAllRadioButton.setSelected(false);
        appointmentWeekRadioButton.setSelected(true);
        update();

    }

    /**
     * Logic for when radio button is clicked
     * @param event
     * @throws ParseException
     */
    public void handleMonthRadioClick(ActionEvent event) throws ParseException {

        appointmentWeekRadioButton.setSelected(false);
        appointmentAllRadioButton.setSelected(false);
        appointmentMonthRadioButton.setSelected(true);
        update();

    }

    /**
     * Logic for when radio button is clicked
     * @param event
     * @throws ParseException
     */
    public void handleAllRadioClick(ActionEvent event) throws ParseException {

        appointmentMonthRadioButton.setSelected(false);
        appointmentWeekRadioButton.setSelected(false);
        appointmentAllRadioButton.setSelected(true);
        update();

    }

    /**
     * Generates the first report type when report one button is clicked
     * This report type contains both number of appointments by type and by month
     * @param event
     * @throws ParseException
     */
    public void handleReportOneButton(ActionEvent event) throws ParseException {


        int typeSaving = 0, typeBanking = 0, typeTaxPrep = 0;
        int jan = 0, feb = 0, march = 0, april = 0, may = 0, june = 0, july = 0, aug = 0, sept = 0, oct = 0, nov = 0, dec = 0;

        for (int i = 0; i < DatabaseHandler.appointmentList.size(); i++) {

            if (DatabaseHandler.appointmentList.get(i).getAppointmentType().equals(DatabaseHandler.typeList.get(0))) {

                typeSaving += 1;

            } else if (DatabaseHandler.appointmentList.get(i).getAppointmentType().equals(DatabaseHandler.typeList.get(1))) {

                typeTaxPrep += 1;

            } else if (DatabaseHandler.appointmentList.get(i).getAppointmentType().equals(DatabaseHandler.typeList.get(2))) {

                typeBanking += 1;

            }

            switch(TimeKeeper.dateFromUTC(DatabaseHandler.appointmentList.get(i).getAppointmentStart()).getMonth()) {
                case 0:
                    jan += 1;
                    break;
                case 1:
                    feb += 1;
                    break;
                case 2:
                    march += 1;
                    break;
                case 3:
                    april += 1;
                    break;
                case 4:
                    may += 1;
                    break;
                case 5:
                    june += 1;
                    break;
                case 6:
                    july += 1;
                    break;
                case 7:
                    aug += 1;
                    break;
                case 8:
                    sept += 1;
                    break;
                case 9:
                    oct += 1;
                    break;
                case 10:
                    nov += 1;
                    break;
                case 11:
                    dec += 1;
                    break;
            }


        }

        Main.createAlert("Report", "Report One: "
                + "\n" +"Appointments per type"
                + "\n" + "Saving consultations: " + typeSaving
                + "\n" + "Tax prep consultations: " + typeTaxPrep
                + "\n" + "Banking consultations: " + typeBanking
                + "\n \n \n"
                + "Appointments per month"
                + "\n" + "January: " + jan
                + "\n" + "February: " + feb
                + "\n" + "March: " + march
                + "\n" + "April: " + april
                + "\n" + "May: " + may
                + "\n" + "June: " + june
                + "\n" + "July: " + july
                + "\n" + "August: " + aug
                + "\n" + "September: " + sept
                + "\n" + "October: " + oct
                + "\n" + "November: " + nov
                + "\n" + "December: " + dec);


    }

    /**
     * Logic for report type 2
     * @param event
     * @throws ParseException
     */
    public void handleReportTwoButton(ActionEvent event) throws ParseException {

        ObservableList<String> scheduleList = FXCollections.observableArrayList();

        for (int i = 0; i < DatabaseHandler.contactList.size(); i++) {

            for (int j = 0; j < DatabaseHandler.appointmentList.size(); j++) {

                if (DatabaseHandler.appointmentList.get(j).getAppointmentContactId() == DatabaseHandler.contactList.get(i).getContactID()) {

                    scheduleList.add("Contact: " + DatabaseHandler.contactList.get(i).getContactName() + "     Appointment ID: " + DatabaseHandler.appointmentList.get(j).getAppointmentId()
                            + "     Title: " + DatabaseHandler.appointmentList.get(j).getAppointmentTitle() + "     Type: " + DatabaseHandler.appointmentList.get(j).getAppointmentType()
                            + "     Description: " + DatabaseHandler.appointmentList.get(j).getAppointmentDescription() + "     Start Date and Time: " + TimeKeeper.dateStringFromUTC(DatabaseHandler.appointmentList.get(j).getAppointmentStart())
                            + "     End Date and Time: " + TimeKeeper.dateStringFromUTC(DatabaseHandler.appointmentList.get(j).getAppointmentEnd())
                            + "     Customer ID: " + DatabaseHandler.appointmentList.get(j).getAppointmentCustomerId() + " \n");

                }

            }

        }

        Main.createAlert("Report", scheduleList.toString());

    }

    /**
     * Logic for report type 3.
     * In this report we look at the total number of customers, total number of employees, and the average employee per customer.  This type of report could be useful when attempting to
     * determine if you have enough employees to take care of your current customer base.
     * @param event
     */
    public void handleReportThreeButton(ActionEvent event) {

        Main.createAlert("Report", "Current number of contacts: " + DatabaseHandler.contactList.size() + " \n" + "Current number of customers: " + DatabaseHandler.customerList.size()
                + "\n" + "Average employee per customer: " + (DatabaseHandler.contactList.size() / DatabaseHandler.customerList.size()));


    }

    /**
     * Generates the month report type when monthly report button is clicked
     * Due to vague instructions I have created this extra fourth report that displays appointment types sorted by each month
     * (e.g. january has 3 tax preps and 2 banking consultiations, feb has 0 tax preps and 5 banking consultations, etc)
     * @param event
     * @throws ParseException
     */

    public void handleMonthReport(ActionEvent event) throws ParseException {

        ObservableList<String> monthList = FXCollections.observableArrayList();

        for (int i = 0; i < 11; i++) {

            int typeSaving = 0, typeBanking = 0, typeTaxPrep = 0;

            for (int j = 0; j < DatabaseHandler.appointmentList.size(); j++) {

                if (TimeKeeper.dateFromUTC(DatabaseHandler.appointmentList.get(j).getAppointmentStart()).getMonth() == i) {

                    if (DatabaseHandler.appointmentList.get(j).getAppointmentType().equals(DatabaseHandler.typeList.get(0))) {

                        typeSaving += 1;

                    } else if (DatabaseHandler.appointmentList.get(j).getAppointmentType().equals(DatabaseHandler.typeList.get(1))) {

                        typeTaxPrep += 1;

                    } else if (DatabaseHandler.appointmentList.get(j).getAppointmentType().equals(DatabaseHandler.typeList.get(2))) {

                        typeBanking += 1;

                    }

                }


            }

            switch(i) {
                case 0:
                    monthList.add("January: "
                            + "\n" +"Appointments per type"
                            + "\n" + "Saving consultations: " + typeSaving
                            + "\n" + "Tax prep consultations: " + typeTaxPrep
                            + "\n" + "Banking consultations: " + typeBanking
                            + "\n");
                    break;
                case 1:
                    monthList.add("February: "
                            + "\n" +"Appointments per type"
                            + "\n" + "Saving consultations: " + typeSaving
                            + "\n" + "Tax prep consultations: " + typeTaxPrep
                            + "\n" + "Banking consultations: " + typeBanking
                            + "\n");
                    break;
                case 2:
                    monthList.add("March: "
                            + "\n" +"Appointments per type"
                            + "\n" + "Saving consultations: " + typeSaving
                            + "\n" + "Tax prep consultations: " + typeTaxPrep
                            + "\n" + "Banking consultations: " + typeBanking
                            + "\n");
                    break;
                case 3:
                    monthList.add("April: "
                            + "\n" +"Appointments per type"
                            + "\n" + "Saving consultations: " + typeSaving
                            + "\n" + "Tax prep consultations: " + typeTaxPrep
                            + "\n" + "Banking consultations: " + typeBanking
                            + "\n");
                    break;
                case 4:
                    monthList.add("May: "
                            + "\n" +"Appointments per type"
                            + "\n" + "Saving consultations: " + typeSaving
                            + "\n" + "Tax prep consultations: " + typeTaxPrep
                            + "\n" + "Banking consultations: " + typeBanking
                            + "\n");
                    break;
                case 5:
                    monthList.add("June: "
                            + "\n" +"Appointments per type"
                            + "\n" + "Saving consultations: " + typeSaving
                            + "\n" + "Tax prep consultations: " + typeTaxPrep
                            + "\n" + "Banking consultations: " + typeBanking
                            + "\n");
                    break;
                case 6:
                    monthList.add("July: "
                            + "\n" +"Appointments per type"
                            + "\n" + "Saving consultations: " + typeSaving
                            + "\n" + "Tax prep consultations: " + typeTaxPrep
                            + "\n" + "Banking consultations: " + typeBanking
                            + "\n");
                    break;
                case 7:
                    monthList.add("August: "
                            + "\n" +"Appointments per type"
                            + "\n" + "Saving consultations: " + typeSaving
                            + "\n" + "Tax prep consultations: " + typeTaxPrep
                            + "\n" + "Banking consultations: " + typeBanking
                            + "\n");
                    break;
                case 8:
                    monthList.add("September: "
                            + "\n" +"Appointments per type"
                            + "\n" + "Saving consultations: " + typeSaving
                            + "\n" + "Tax prep consultations: " + typeTaxPrep
                            + "\n" + "Banking consultations: " + typeBanking
                            + "\n");
                    break;
                case 9:
                    monthList.add("October: "
                            + "\n" +"Appointments per type"
                            + "\n" + "Saving consultations: " + typeSaving
                            + "\n" + "Tax prep consultations: " + typeTaxPrep
                            + "\n" + "Banking consultations: " + typeBanking
                            + "\n");
                    break;
                case 10:
                    monthList.add("November: "
                            + "\n" +"Appointments per type"
                            + "\n" + "Saving consultations: " + typeSaving
                            + "\n" + "Tax prep consultations: " + typeTaxPrep
                            + "\n" + "Banking consultations: " + typeBanking
                            + "\n");
                    break;
                case 11:
                    monthList.add("December: "
                            + "\n" +"Appointments per type"
                            + "\n" + "Saving consultations: " + typeSaving
                            + "\n" + "Tax prep consultations: " + typeTaxPrep
                            + "\n" + "Banking consultations: " + typeBanking
                            + "\n");
                    break;
            }

        }

        Main.createAlert("Report", monthList.toString());
    }

}
