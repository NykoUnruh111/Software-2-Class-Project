package Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Logic and data for the appointment model
 *
 */
public class Appointment {

    private final SimpleIntegerProperty appointmentId = new SimpleIntegerProperty();

    private final SimpleStringProperty appointmentTitle = new SimpleStringProperty();
    private final SimpleStringProperty appointmentDescription = new SimpleStringProperty();
    private final SimpleStringProperty appointmentLocation = new SimpleStringProperty();
    private final SimpleStringProperty appointmentType = new SimpleStringProperty();
    private final SimpleStringProperty appointmentStart = new SimpleStringProperty();
    private final SimpleStringProperty appointmentEnd = new SimpleStringProperty();

    private final SimpleIntegerProperty appointmentCustomerId = new SimpleIntegerProperty();
    private final SimpleIntegerProperty appointmentContactId = new SimpleIntegerProperty();
    private final SimpleIntegerProperty appointmentUserId = new SimpleIntegerProperty();



    public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, String appointmentStart, String appointmentEnd, int appointmentCustomerId, int appointmentContactId, int userID) {

        setAppointmentId(appointmentID);
        setAppointmentTitle(appointmentTitle);
        setAppointmentDescription(appointmentDescription);
        setAppointmentLocation(appointmentLocation);
        setAppointmentType(appointmentType);
        setAppointmentStart(appointmentStart);
        setAppointmentEnd(appointmentEnd);

        setAppointmentCustomerId(appointmentCustomerId);
        setAppointmentContactId(appointmentContactId);
        setAppointmentUserId(userID);

    }

    /**
     * Getter for appointment ID
     * @return
     */
    public int getAppointmentId() {

        return appointmentId.get();

    }

    /**
     * Setter for appointment ID
     * @param appointmentId
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId.set(appointmentId);
    }

    /**
     * Getter for appointment title
     * @return
     */
    public String getAppointmentTitle() {
        return appointmentTitle.get();
    }

    /**
     * setter for appointment title
     * @param appointmentTitle
     */
    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle.set(appointmentTitle);
    }

    /**
     * Getter for appointment description
     * @return
     */
    public String getAppointmentDescription() {
        return appointmentDescription.get();
    }

    /**
     * Setter for appointment description
     * @param appointmentDescription
     */
    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription.set(appointmentDescription);
    }

    /**
     * Getter for appointment location
     * @return
     */
    public String getAppointmentLocation() {
        return appointmentLocation.get();
    }

    /**
     * Setter for appointment location
     * @param appointmentLocation
     */
    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation.set(appointmentLocation);
    }

    /**
     * Getter for appointment type
     * @return
     */
    public String getAppointmentType() {
        return appointmentType.get();
    }

    /**
     * Setter for appointment type
     * @param appointmentType
     */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType.set(appointmentType);
    }

    /**
     * Getter for appointment start
     * @return
     */
    public String getAppointmentStart() {
        return appointmentStart.get();
    }

    /**
     * Setter for appointment start
     * @param appointmentStart
     */
    public void setAppointmentStart(String appointmentStart) {
        this.appointmentStart.set(appointmentStart);
    }

    /**
     * Getter for appointment end
     * @return
     */
    public String getAppointmentEnd() {
        return appointmentEnd.get();
    }

    /**
     * Setter for appointment end
     * @param appointmentEnd
     */
    public void setAppointmentEnd(String appointmentEnd) {
        this.appointmentEnd.set(appointmentEnd);
    }

    /**
     * Getter for customer ID
     * @return
     */
    public int getAppointmentCustomerId() {
        return appointmentCustomerId.get();
    }

    /**
     * Setter for customer ID
     * @param appointmentCustomerId
     */
    public void setAppointmentCustomerId(int appointmentCustomerId) {
        this.appointmentCustomerId.set(appointmentCustomerId);
    }

    /**
     * Getter for contact ID
     * @return
     */
    public int getAppointmentContactId() {
        return appointmentContactId.get();
    }

    /**
     * Setter for contact ID
     * @param appointmentContactId
     */
    public void setAppointmentContactId(int appointmentContactId) {
        this.appointmentContactId.set(appointmentContactId);
    }

    /**
     * Getter for user ID
     * @return
     */
    public int getAppointmentUserId() {

        return appointmentUserId.intValue();

    }

    /**
     * Setter for user ID
     * @param userID
     */
    public void setAppointmentUserId(int userID) {

        appointmentUserId.set(userID);

    }


}
