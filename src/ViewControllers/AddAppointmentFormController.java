package ViewControllers;

import Handlers.DatabaseHandler;
import MainPackage.Main;
import Models.Contact;
import Models.Country;
import Models.TimeKeeper;
import Models.TimeSlot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * This class handles all the logic for adding a new appointment
 *
 */
public class AddAppointmentFormController implements Initializable {

    private ObservableList<String> errorList = FXCollections.observableArrayList();

    private ObservableList<TimeSlot> timeSlotList = FXCollections.observableArrayList();

    public TextField addAppointmentIDTextField;
    public TextField addAppointmentTitleTextField;
    public TextField addAppointmentDescriptionTextField;
    public TextField addAppointmentLocationTextField;
    public TextField addAppointmentCustomerIDTextField;
    public TextField addAppointmentUserIDTextfield;

    public ComboBox<Contact> addAppointmentContactCombobox;
    public DatePicker addAppointmentStartDate;
    public DatePicker addAppointmentEndDate;
    
    public ComboBox<TimeSlot> addAppointmentStartTimeCombobox;
    public ComboBox<TimeSlot> addAppointmentEndTimeCombobox;
    public ComboBox addAppointmentTypeComboBox;

    public Button addAppointmentCancelButton;

    SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Handles when the save button is clicked
     * @param event
     * @throws ParseException
     */
    public void handleAddAppointmentSave(ActionEvent event) throws ParseException {

                if (validateTitle() && validateDescription() && validateLocation() && validateType() && validateStartDate() && validateStartTime()
                        && validateEndDate() && validateEndTime() && validateUserID() && validateCustomerID() && validateContact()) {

                    //Convert current date and time to UTC to add it to the database
                    Date startDate = TimeKeeper.dateToUTC(addAppointmentStartDate.getValue(), addAppointmentStartTimeCombobox.getValue().getMilitaryTime());
                    Date endDate = TimeKeeper.dateToUTC(addAppointmentEndDate.getValue(), addAppointmentEndTimeCombobox.getValue().getMilitaryTime());

                    DatabaseHandler.addAppointment(addAppointmentTitleTextField.getText().toString(), addAppointmentDescriptionTextField.getText().toString(),
                            addAppointmentLocationTextField.getText().toString(), addAppointmentTypeComboBox.getSelectionModel().getSelectedItem().toString(),
                            sdf.format(startDate), sdf.format(endDate),
                            Integer.parseInt(addAppointmentUserIDTextfield.getText()), Integer.parseInt(addAppointmentCustomerIDTextField.getText()), addAppointmentContactCombobox.getSelectionModel().getSelectedItem().getContactID());

                    closeWindow();
                } else {

                    Main.createAlert("Error!", errorList.toString());
                    errorList.clear();

                }

        }


    /**
     * Function called when close button is pressed and closes window
     * @param event
     */
    public void handleAddAppointmentCancel(ActionEvent event) {

        closeWindow();

    }

    /**
     * Function to close current window
     */
    private void closeWindow() {


        Stage stage = (Stage) addAppointmentCancelButton.getScene().getWindow();

        stage.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Populate timeslots
        timeSlotList.clear();

        timeSlotList.add(new TimeSlot("8 AM", 8));
        timeSlotList.add(new TimeSlot("9 AM", 9));
        timeSlotList.add(new TimeSlot("10 AM", 10));
        timeSlotList.add(new TimeSlot("11 AM", 11));
        timeSlotList.add(new TimeSlot("12 PM", 12));
        timeSlotList.add(new TimeSlot("1 PM", 13));
        timeSlotList.add(new TimeSlot("2 PM", 14));
        timeSlotList.add(new TimeSlot("3 PM", 15));
        timeSlotList.add(new TimeSlot("4 PM", 16));
        timeSlotList.add(new TimeSlot("5 PM", 17));
        timeSlotList.add(new TimeSlot("6 PM", 18));
        timeSlotList.add(new TimeSlot("7 PM", 19));
        timeSlotList.add(new TimeSlot("8 PM", 20));
        timeSlotList.add(new TimeSlot("9 PM", 21));
        timeSlotList.add(new TimeSlot("10 PM", 22));

        addAppointmentStartTimeCombobox.getItems().clear();

        addAppointmentStartTimeCombobox.setItems(timeSlotList);

        addAppointmentStartTimeCombobox.setConverter(new StringConverter<TimeSlot>() {
            @Override
            public String toString(TimeSlot timeSlot) {

                return timeSlot.getTwelveHourTime();

            }

            @Override
            public TimeSlot fromString(String s) {
                return null;
            }
        });

        addAppointmentEndTimeCombobox.getItems().clear();

        addAppointmentEndTimeCombobox.setItems(timeSlotList);

        addAppointmentEndTimeCombobox.setConverter(new StringConverter<TimeSlot>() {
            @Override
            public String toString(TimeSlot timeSlot) {

                return timeSlot.getTwelveHourTime();

            }

            @Override
            public TimeSlot fromString(String s) {
                return null;
            }
        });


        //Populate contacts
        addAppointmentContactCombobox.getItems().clear();

        addAppointmentContactCombobox.setItems(DatabaseHandler.contactList);

        addAppointmentContactCombobox.setConverter(new StringConverter<Contact>() {
            @Override
            public String toString(Contact contact) {

                return contact.getContactName();

            }

            @Override
            public Contact fromString(String s) {
                return null;
            }
        });

        addAppointmentTypeComboBox.getItems().clear();

        addAppointmentTypeComboBox.setItems(DatabaseHandler.typeList);
        
    }

    /**
     * Validate the appointment title
     * @return
     */
    private boolean validateTitle() {

        if (addAppointmentTitleTextField.getText().isEmpty()) {

            errorList.add("Please add a title!");
            return false;

        } else {
            return true;
        }

    }


    /**
     * Validate the appointment description
     * @return
     */
    private boolean validateDescription() {

        if (addAppointmentDescriptionTextField.getText().isEmpty()) {

            errorList.add("Please add a description!");
            return false;

        } else {

            return true;

        }

    }

    /**
     * Validate appointment location
     * @return
     */
    private boolean validateLocation() {

        if (addAppointmentLocationTextField.getText().isEmpty()) {

            errorList.add("Please add a location!");
            return false;

        } else {

            return true;

        }

    }

    /**
     * Validate appointment type
     * @return
     */
    private boolean validateType() {

        if(addAppointmentTypeComboBox.getSelectionModel().getSelectedIndex() == -1) {

            errorList.add("Please add an appointment type!");
            return false;

        } else {

            return true;

        }

    }

    /**
     * Validate customer ID
     * @return
     */
    private boolean validateCustomerID() {

        if (addAppointmentCustomerIDTextField.getText().isEmpty()) {

            errorList.add("Please add a customerID!");
            return false;

        } else {

            return true;

        }

    }

    /**
     * Validate user ID
     * @return
     */
    private boolean validateUserID() {

        if (addAppointmentUserIDTextfield.getText().isEmpty()) {

            errorList.add("Please add a userID!");
            return false;

        } else {

            return true;

        }

    }

    /**
     * Validate contact has been selected from list
     * @return
     */
    private boolean validateContact() {

        if (addAppointmentContactCombobox.getSelectionModel().getSelectedIndex() == -1) {

            errorList.add("Please select a contact!");
            return false;

        } else {

            return true;

        }

    }

    /**
     * Validate start time has been selected from list, that the office is not closed based on EST, and checks that there are no overlapping appointments
     * @return
     */
    private boolean validateStartTime() throws ParseException {

        if (addAppointmentStartTimeCombobox.getSelectionModel().getSelectedIndex() == -1) {

            errorList.add("Please select a start time!");
            return false;

        } else if (TimeKeeper.dateToEST(addAppointmentStartDate.getValue(), addAppointmentStartTimeCombobox.getValue().getMilitaryTime()).getHours() < 8 || TimeKeeper.dateToEST(addAppointmentStartDate.getValue(), addAppointmentStartTimeCombobox.getValue().getMilitaryTime()).getHours() > 22 ){

            errorList.add("You have picked a time when our office is closed. The hours are between 8AM and 10PM Eastern Standard Time!");
            return false;

        } else {

            //Check for overlap
            for (int i = 0; i < DatabaseHandler.appointmentList.size(); i++) {

                if (TimeKeeper.stringToDate(DatabaseHandler.appointmentList.get(i).getAppointmentStart()).compareTo(TimeKeeper.dateToUTC(addAppointmentStartDate.getValue(), addAppointmentStartTimeCombobox.getValue().getMilitaryTime())) == 0) {

                    System.out.println("Time matches!" + "Checked time: " + TimeKeeper.stringToDate(DatabaseHandler.appointmentList.get(i).getAppointmentStart()) + " New Appontment time: " + TimeKeeper.dateToUTC(addAppointmentStartDate.getValue(), addAppointmentStartTimeCombobox.getValue().getMilitaryTime()));
                    errorList.add("There is an overlapping appointment scheduled at this time!");
                    return false;

                }

            }

            return true;


        }

    }

    /**
     * Validate that end time has been selected from the list
     * @return
     */
    private boolean validateEndTime() throws ParseException {

        if (addAppointmentEndTimeCombobox.getSelectionModel().getSelectedIndex() == -1) {

            errorList.add("Please select a end time!");
            return false;

        } else if((addAppointmentStartDate.getValue().isEqual(addAppointmentEndDate.getValue())) && (addAppointmentStartTimeCombobox.getSelectionModel().getSelectedItem().getMilitaryTime() > addAppointmentEndTimeCombobox.getSelectionModel().getSelectedItem().getMilitaryTime())) {

            errorList.add("Appointment cant end before it starts!");
            return false;

        } else if((addAppointmentStartDate.getValue().isEqual(addAppointmentEndDate.getValue())) && (addAppointmentStartTimeCombobox.getSelectionModel().getSelectedItem().getMilitaryTime() == addAppointmentEndTimeCombobox.getSelectionModel().getSelectedItem().getMilitaryTime())) {

            errorList.add("Appointment cant start and end at the same time!");
            return false;

        } else if (TimeKeeper.dateToEST(addAppointmentEndDate.getValue(), addAppointmentEndTimeCombobox.getValue().getMilitaryTime()).getHours() < 8 || TimeKeeper.dateToEST(addAppointmentEndDate.getValue(), addAppointmentEndTimeCombobox.getValue().getMilitaryTime()).getHours() > 22 ){

            errorList.add("You have picked a time when our office is closed. The hours are between 8AM and 10PM Eastern Standard Time!");
            return false;

        }
        else {

            return true;

        }

    }

    /**
     * Validate that start date has been selected from date picker
     * @return
     */
    private boolean validateStartDate() {

        if (addAppointmentStartDate.getValue() == null){

            errorList.add("Please select a start date!");
            return false;

        } else {

            return true;

        }
    }

    /**
     * Validate that end date has been selected from date picker
     * @return
     */
    private boolean validateEndDate() {

        if (addAppointmentEndDate.getValue() == null) {

            errorList.add("Please select an end date!");
            return false;

        } else if (!addAppointmentEndDate.getValue().isEqual(addAppointmentStartDate.getValue()) && !addAppointmentEndDate.getValue().isAfter(addAppointmentStartDate.getValue())){

            errorList.add("The end date cant be before the start date!");
            return false;

        } else {

            return true;

        }

    }

    /**
     * Convert date in UTC from long
     * @param longDate
     * @return
     */
    public static Date dateFromUTC(Long longDate){

        Date date = new Date();
        date.setTime(longDate);

        return new Date(date.getTime() + Calendar.getInstance().getTimeZone().getOffset(date.getTime()));

    }



}
