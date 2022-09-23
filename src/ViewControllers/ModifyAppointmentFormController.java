package ViewControllers;

import Handlers.DatabaseHandler;
import MainPackage.Main;
import Models.Contact;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * This class handles all the logic for adding a new appointment
 *
 */
public class ModifyAppointmentFormController implements Initializable {

    private ObservableList<String> errorList = FXCollections.observableArrayList();

    private ObservableList<TimeSlot> timeSlotList = FXCollections.observableArrayList();

    public TextField modAppointmentIDTextField;
    public TextField modAppointmentTitleTextField;
    public TextField modAppointmentDescriptionTextField;
    public TextField modAppointmentLocationTextField;
    public ComboBox modAppointmentTypeComboBox;
    public TextField modAppointmentCustomerIDTextField;
    public TextField modAppointmentUserIDTextfield;

    public ComboBox<Contact> modAppointmentContactCombobox;
    public DatePicker modAppointmentStartDate;
    public DatePicker modAppointmentEndDate;
    
    public ComboBox<TimeSlot> modAppointmentStartTimeCombobox;
    public ComboBox<TimeSlot> modAppointmentEndTimeCombobox;

    public Button modAppointmentCancelButton;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Handles when the save button is clicked
     * @param event
     * @throws ParseException
     */
    public void handleModAppointmentUpdate(ActionEvent event) throws ParseException {

                if (validateTitle() && validateDescription() && validateLocation() && validateType() && validateStartDate() && validateStartTime()
                        && validateEndDate() && validateEndTime() && validateUserID() && validateCustomerID() && validateContact()) {

                    //Convert current date and time to UTC to add it to the database
                    Date startDate = TimeKeeper.dateToUTC(modAppointmentStartDate.getValue(), modAppointmentStartTimeCombobox.getValue().getMilitaryTime());
                    Date endDate = TimeKeeper.dateToUTC(modAppointmentEndDate.getValue(), modAppointmentEndTimeCombobox.getValue().getMilitaryTime());

                    DatabaseHandler.updateAppointment(DatabaseHandler.appointmentList.get(MainFormController.currentlySelectedAppointmentIndex).getAppointmentId(), modAppointmentTitleTextField.getText().toString(), modAppointmentDescriptionTextField.getText().toString(),
                            modAppointmentLocationTextField.getText().toString(), modAppointmentTypeComboBox.getSelectionModel().getSelectedItem().toString(),
                            sdf.format(startDate), sdf.format(endDate),
                            Integer.parseInt(modAppointmentUserIDTextfield.getText()), Integer.parseInt(modAppointmentCustomerIDTextField.getText()), modAppointmentContactCombobox.getSelectionModel().getSelectedItem().getContactID());

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
    public void handleModAppointmentCancel(ActionEvent event) {

        closeWindow();

    }

    /**
     * Function to close current window
     */
    private void closeWindow() {


        Stage stage = (Stage) modAppointmentCancelButton.getScene().getWindow();

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

        modAppointmentStartTimeCombobox.getItems().clear();

        modAppointmentStartTimeCombobox.setItems(timeSlotList);

        modAppointmentStartTimeCombobox.setConverter(new StringConverter<TimeSlot>() {
            @Override
            public String toString(TimeSlot timeSlot) {

                return timeSlot.getTwelveHourTime();

            }

            @Override
            public TimeSlot fromString(String s) {
                return null;
            }
        });

        modAppointmentEndTimeCombobox.getItems().clear();

        modAppointmentEndTimeCombobox.setItems(timeSlotList);

        modAppointmentEndTimeCombobox.setConverter(new StringConverter<TimeSlot>() {
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
        modAppointmentContactCombobox.getItems().clear();

        modAppointmentContactCombobox.setItems(DatabaseHandler.contactList);

        modAppointmentContactCombobox.setConverter(new StringConverter<Contact>() {
            @Override
            public String toString(Contact contact) {

                return contact.getContactName();

            }

            @Override
            public Contact fromString(String s) {
                return null;
            }
        });

        modAppointmentTypeComboBox.getItems().clear();

        modAppointmentTypeComboBox.setItems(DatabaseHandler.typeList);

        modAppointmentIDTextField.setText("" + DatabaseHandler.appointmentList.get(MainFormController.currentlySelectedAppointmentIndex).getAppointmentId());
        modAppointmentTitleTextField.setText(DatabaseHandler.appointmentList.get(MainFormController.currentlySelectedAppointmentIndex).getAppointmentTitle());
        modAppointmentDescriptionTextField.setText(DatabaseHandler.appointmentList.get(MainFormController.currentlySelectedAppointmentIndex).getAppointmentDescription());
        modAppointmentLocationTextField.setText(DatabaseHandler.appointmentList.get(MainFormController.currentlySelectedAppointmentIndex).getAppointmentLocation());

        //modAppointmentTypeTextField.setText(DatabaseHandler.appointmentList.get(MainFormController.currentlySelectedAppointmentIndex).getAppointmentType());

        modAppointmentCustomerIDTextField.setText("" + DatabaseHandler.appointmentList.get(MainFormController.currentlySelectedAppointmentIndex).getAppointmentCustomerId());
        modAppointmentUserIDTextfield.setText("" + DatabaseHandler.appointmentList.get(MainFormController.currentlySelectedAppointmentIndex).getAppointmentUserId());

        //Set start time
        try {

            //value needed TimeKeeper.stringToDate(DatabaseHandler.appointmentList.get(MainFormController.currentlySelectedAppointmentIndex).getAppointmentStart()).getHours()
            for (int i = 0; i < timeSlotList.size(); i++) {

                if (timeSlotList.get(i).getMilitaryTime() == TimeKeeper.dateFromUTC(DatabaseHandler.appointmentList.get(MainFormController.currentlySelectedAppointmentIndex).getAppointmentStart()).getHours()) {

                    modAppointmentStartTimeCombobox.getSelectionModel().select(i);

                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Set start date
        try {
            modAppointmentStartDate.setValue(TimeKeeper.stringToDate(DatabaseHandler.appointmentList.get(MainFormController.currentlySelectedAppointmentIndex).getAppointmentStart()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Set end time
        try {

            //value needed TimeKeeper.stringToDate(DatabaseHandler.appointmentList.get(MainFormController.currentlySelectedAppointmentIndex).getAppointmentStart()).getHours()
            for (int i = 0; i < timeSlotList.size(); i++) {

                if (timeSlotList.get(i).getMilitaryTime() == TimeKeeper.dateFromUTC(DatabaseHandler.appointmentList.get(MainFormController.currentlySelectedAppointmentIndex).getAppointmentEnd()).getHours()) {

                    modAppointmentEndTimeCombobox.getSelectionModel().select(i);

                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Set end date
        try {
            modAppointmentEndDate.setValue(TimeKeeper.stringToDate(DatabaseHandler.appointmentList.get(MainFormController.currentlySelectedAppointmentIndex).getAppointmentEnd()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Populate contact
        for (int i = 0; i < DatabaseHandler.countryList.size(); i++) {

            if (DatabaseHandler.contactList.get(i).getContactID() == (DatabaseHandler.appointmentList.get(MainFormController.currentlySelectedAppointmentIndex).getAppointmentContactId())) {

                modAppointmentContactCombobox.setValue(DatabaseHandler.contactList.get(i));

                break;
            }

        }

        //Populate type
        for (int i = 0; i < DatabaseHandler.typeList.size(); i++) {

            if (DatabaseHandler.typeList.get(i).equals((DatabaseHandler.appointmentList.get(MainFormController.currentlySelectedAppointmentIndex).getAppointmentType()))) {

                modAppointmentTypeComboBox.getSelectionModel().select(i);
                //modAppointmentContactCombobox.setValue(DatabaseHandler.contactList.get(i));

                break;
            }

        }
        
    }

    /**
     * Validate the appointment title
     * @return
     */
    private boolean validateTitle() {

        if (modAppointmentTitleTextField.getText().isEmpty()) {

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

        if (modAppointmentDescriptionTextField.getText().isEmpty()) {

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

        if (modAppointmentLocationTextField.getText().isEmpty()) {

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

        if(modAppointmentTypeComboBox.getSelectionModel().getSelectedIndex() == -1) {

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

        if (modAppointmentCustomerIDTextField.getText().isEmpty()) {

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

        if (modAppointmentUserIDTextfield.getText().isEmpty()) {

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

        if (modAppointmentContactCombobox.getSelectionModel().getSelectedIndex() == -1) {

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

        if (modAppointmentStartTimeCombobox.getSelectionModel().getSelectedIndex() == -1) {

            errorList.add("Please select a start time!");
            return false;

        } else if (TimeKeeper.dateToEST(modAppointmentStartDate.getValue(), modAppointmentStartTimeCombobox.getValue().getMilitaryTime()).getHours() < 8 || TimeKeeper.dateToEST(modAppointmentStartDate.getValue(), modAppointmentStartTimeCombobox.getValue().getMilitaryTime()).getHours() > 22 ){

            errorList.add("You have picked a time when our office is closed. The hours are between 8AM and 10PM Eastern Standard Time!");
            return false;

        } else {

            //Check for overlap
            for (int i = 0; i < DatabaseHandler.appointmentList.size(); i++) {

                if (TimeKeeper.stringToDate(DatabaseHandler.appointmentList.get(i).getAppointmentStart()).compareTo(TimeKeeper.dateToUTC(modAppointmentStartDate.getValue(), modAppointmentStartTimeCombobox.getValue().getMilitaryTime())) == 0 && DatabaseHandler.appointmentList.get(i).getAppointmentId() != Integer.parseInt(modAppointmentIDTextField.getText())) {

                    System.out.println("Time matches!" + "Checked time: " + TimeKeeper.stringToDate(DatabaseHandler.appointmentList.get(i).getAppointmentStart()) + " New Appontment time: " + TimeKeeper.dateToUTC(modAppointmentStartDate.getValue(), modAppointmentStartTimeCombobox.getValue().getMilitaryTime()));
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

        if (modAppointmentEndTimeCombobox.getSelectionModel().getSelectedIndex() == -1) {

            errorList.add("Please select a end time!");
            return false;

        } else if((modAppointmentStartDate.getValue().isEqual(modAppointmentEndDate.getValue())) && (modAppointmentStartTimeCombobox.getSelectionModel().getSelectedItem().getMilitaryTime() > modAppointmentEndTimeCombobox.getSelectionModel().getSelectedItem().getMilitaryTime())) {

            errorList.add("Appointment cant end before it starts!");
            return false;

        } else if((modAppointmentStartDate.getValue().isEqual(modAppointmentEndDate.getValue())) && (modAppointmentStartTimeCombobox.getSelectionModel().getSelectedItem().getMilitaryTime() == modAppointmentEndTimeCombobox.getSelectionModel().getSelectedItem().getMilitaryTime())) {

            errorList.add("Appointment cant start and end at the same time!");
            return false;

        } else if (TimeKeeper.dateToEST(modAppointmentEndDate.getValue(), modAppointmentEndTimeCombobox.getValue().getMilitaryTime()).getHours() < 8 || TimeKeeper.dateToEST(modAppointmentEndDate.getValue(), modAppointmentEndTimeCombobox.getValue().getMilitaryTime()).getHours() > 22 ){

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

        if (modAppointmentStartDate.getValue() == null){

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

        if (modAppointmentEndDate.getValue() == null) {

            errorList.add("Please select an end date!");
            return false;

        } else if (!modAppointmentEndDate.getValue().isEqual(modAppointmentStartDate.getValue()) && !modAppointmentEndDate.getValue().isAfter(modAppointmentStartDate.getValue())){

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
