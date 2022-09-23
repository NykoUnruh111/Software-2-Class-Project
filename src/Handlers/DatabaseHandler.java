package Handlers;

import MainPackage.Main;
import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.Instant;

/**
 * This class handles all of the database related calls
 */
public class DatabaseHandler {

    /**
     * Static strings that hold database connection information
     */
    private static final String DBNAME = "WJ08EQo";
    private static final String DBUSER = "U08EQo";
    private static final String DBPASS = "53689264807";
    //private static final String DBSERVERNAME = "wgudb.ucertify.com";
    private static final String DBURL = "jdbc:mysql://wgudb.ucertify.com/" + DBNAME;
    private static final String DBDRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;
    public static String currentUser;

    public static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    public static ObservableList<Country> countryList = FXCollections.observableArrayList();
    public static ObservableList<Division> divisionList = FXCollections.observableArrayList();
    public static ObservableList<Contact> contactList = FXCollections.observableArrayList();
    public static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    public static ObservableList<Appointment> sortedAppointmentList = FXCollections.observableArrayList();
    public static ObservableList<String> typeList = FXCollections.observableArrayList();

    /**
     * Function to log user in
     * @param username
     * @param password
     */
    public static Boolean login(String username, String password) {

        System.out.println("Attempting login");

        try {

            Statement statement = connection.createStatement();
            String dbquery = "SELECT * FROM users WHERE User_Name = '" + username + "' AND Password = '" + password + "'";
            ResultSet result = statement.executeQuery(dbquery);

            if (result.next()) {
                //System.out.println("User successfully logged in!");
                return true;
            } else {
                //System.out.println("Username or password is incorrect");
                return false;
            }


        } catch (SQLException throwables) {

            throwables.printStackTrace();
            return false;
        }



    }

    /**
     * Get customer from database
     */

    public static Customer getCustomer(int customerID) {

        try {

            Statement statement = connection.createStatement();
            String dbquery = "SELECT * FROM customers "
                    + "INNER JOIN first_level_divisions "
                    + "INNER JOIN countries ON customers.Division_ID = first_level_divisions.Division_ID" + " AND first_level_divisions.COUNTRY_ID = countries.Country_ID" + " AND  Customer_ID = '" + customerID + "'";
            ResultSet results = statement.executeQuery(dbquery);
            if (results.next()) {
                Customer customer = new Customer(results.getInt("Customer_ID"),
                        results.getString("Customer_Name"),
                        results.getString("Address"),
                        results.getString("Division"),
                        results.getString("Country"),
                        results.getString("Phone"),
                        results.getString("Postal_Code"));

                //customer.setCustomerName(results.getString("Customer_Name"));
                statement.close();
                return customer;
            }

        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }
        return null;
    }

    /**
     * Update customer in database
     */

    public static void updateCustomer(int customerID, String customerName, String customerAddress, String customerPostal, String customerPhone, int divisionID) {

        try {

            Statement statement = connection.createStatement();
            String dbquery = "UPDATE customers SET Customer_Name = '" + customerName
                    + "', Address = '" + customerAddress
                    + "', Postal_Code = '" + customerPostal
                    + "', Phone = '" + customerPhone
                    + "', Last_Update = '" + java.sql.Timestamp.from(Instant.now())
                    + "', Last_Updated_By = '" + currentUser
                    + "', Division_ID = " + divisionID
                    + " WHERE Customer_ID = " + customerID;


            int result = statement.executeUpdate(dbquery);

            System.out.println(result);


        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }

    }

    /**
     * Add customer into database
     * @param customerName
     * @param customerAddress
     * @param customerPostal
     * @param customerPhone
     * @param divisionID
     */

    public static void addCustomer(String customerName, String customerAddress, String customerPostal, String customerPhone, int divisionID) {

        try {

            Statement statement = connection.createStatement();
            String dbquery = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) "
                    + "VALUES ('" + customerName + "', '"
                    +  customerAddress + "', '"
                    + customerPostal + "', '"
                    + customerPhone + "', '"
                    + java.sql.Timestamp.from(Instant.now())
                    + "', '" + currentUser + "', '"
                    + java.sql.Timestamp.from(Instant.now())
                    + "', '" + currentUser + "', "
                    + divisionID + ")";

            /*String dbquery = "INSERT INTO customers SET Customer_Name = '" + customerName
                    + "', Address = '" + customerAddress
                    + "', Postal_Code = '" + customerPostal
                    + "', Phone = " + customerPhone
                    + "', Create_Date = " + java.sql.Timestamp.from(Instant.now())
                    + ", Created_By ='" + currentUser
                    + "', Last_Update = " + java.sql.Timestamp.from(Instant.now())
                    + ", Last_Updated_By ='" + currentUser
                    + ", Division_ID = " + divisionID;*/

            statement.executeUpdate(dbquery);


        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }

    }

    public static void deleteCustomer(int customerID) {

        try {

            Statement statement = connection.createStatement();
            String dbquery = "DELETE FROM  customers WHERE Customer_ID = " + customerID;

            /*String dbquery = "INSERT INTO customers SET Customer_Name = '" + customerName
                    + "', Address = '" + customerAddress
                    + "', Postal_Code = '" + customerPostal
                    + "', Phone = " + customerPhone
                    + "', Create_Date = " + java.sql.Timestamp.from(Instant.now())
                    + ", Created_By ='" + currentUser
                    + "', Last_Update = " + java.sql.Timestamp.from(Instant.now())
                    + ", Last_Updated_By ='" + currentUser
                    + ", Division_ID = " + divisionID;*/

            statement.executeUpdate(dbquery);

            Main.createAlert("Success!", "Successfully deleted customer");


        } catch (SQLException throwables) {

            Main.createAlert("Error!", "Cannot delete customer, please delete all of this customers appointments first!");

        }

    }

    /**
     * Get list of all customers in database
     * @return
     */

    public static ObservableList<Customer> getAllCustomers() {

        customerList.clear();

        try {
        Statement statement = connection.createStatement();
        String dbquery = "SELECT * FROM customers "
                + "INNER JOIN first_level_divisions "
                + "INNER JOIN countries "
                + "ON customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.COUNTRY_ID = countries.Country_ID";
        ResultSet results = statement.executeQuery(dbquery);
        while (results.next()) {
            Customer customer = new Customer(results.getInt("Customer_ID"),
                    results.getString("Customer_Name"),
                    results.getString("Address"),
                    results.getString("Division"),
                    results.getString("Country"),
                    results.getString("Phone"),
                    results.getString("Postal_Code"));

            //customer.setCustomerName(results.getString("Customer_Name"));
            customerList.add(customer);
        }

            statement.close();

    } catch (SQLException throwables) {

        throwables.printStackTrace();

    }
        return customerList;

    }

    /**
     * Get list of countries from database
     * @return
     */
    public static ObservableList<Country> getCountryList() {

        countryList.clear();

        try {
            Statement statement = connection.createStatement();
            String dbquery = "SELECT * FROM countries";

            ResultSet results = statement.executeQuery(dbquery);
            while (results.next()) {
                Country country = new Country(results.getInt("Country_ID"),
                        results.getString("Country"));

                System.out.println(results.getString("Country"));
                //customer.setCustomerName(results.getString("Customer_Name"));
                countryList.add(country);
            }

            statement.close();

        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }

        return countryList;

    }

    /**
     * Get division list based on countryID
     * @param countryID
     * @return
     */
    public static ObservableList<Country> getDivisionList(int countryID) {

        divisionList.clear();

        try {
            Statement statement = connection.createStatement();
            String dbquery = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = " + countryID;

            ResultSet results = statement.executeQuery(dbquery);
            while (results.next()) {
                Division division = new Division(results.getInt("Division_ID"),
                        results.getString("Division"));

                System.out.println(results.getString("Division"));
                //customer.setCustomerName(results.getString("Customer_Name"));
                divisionList.add(division);
            }

            statement.close();

        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }

        return countryList;

    }

    /**
     * Get list of contacts from database
     * @return
     */
    public static ObservableList<Contact> getContactList() {

        contactList.clear();

        try {
            Statement statement = connection.createStatement();
            String dbquery = "SELECT * FROM contacts";

            ResultSet results = statement.executeQuery(dbquery);
            while (results.next()) {
                Contact contact = new Contact(results.getInt("Contact_ID"),
                        results.getString("Contact_Name"), results.getString("Email"));

                System.out.println(results.getString("Contact_Name"));
                //customer.setCustomerName(results.getString("Customer_Name"));
                if (contact != null)
                    contactList.add(contact);
            }

            statement.close();

        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }

        return contactList;

    }

    /**
     * Get appointment list based on customerID
     * @param customerID
     * @return
     */
    public static ObservableList<Appointment> getAppointmentList(int customerID) {

        appointmentList.clear();

        try {
            Statement statement = connection.createStatement();
            String dbquery = "SELECT * FROM appointments WHERE Customer_ID = " + customerID;

            ResultSet results = statement.executeQuery(dbquery);
            while (results.next()) {
                Appointment appointment = new Appointment(results.getInt("Appointment_ID"),
                        results.getString("Title"), results.getString("Description"),
                        results.getString("Location"), results.getString("Type"),
                        results.getString("Start"), results.getString("End"),
                        results.getInt("Customer_ID"), results.getInt("Contact_ID"), results.getInt("User_ID"));

                System.out.println(results.getString("Title"));
                //customer.setCustomerName(results.getString("Customer_Name"));
                appointmentList.add(appointment);
            }

            statement.close();

        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }

        return appointmentList;

    }

    /**
     * Gets all appointments from database
     * @return
     */
    public static ObservableList<Appointment> getAllAppointmentList() {

        appointmentList.clear();

        try {
            Statement statement = connection.createStatement();
            String dbquery = "SELECT * FROM appointments";

            ResultSet results = statement.executeQuery(dbquery);
            while (results.next()) {
                Appointment appointment = new Appointment(results.getInt("Appointment_ID"),
                        results.getString("Title"), results.getString("Description"),
                        results.getString("Location"), results.getString("Type"),
                        results.getString("Start"), results.getString("End"),
                        results.getInt("Customer_ID"), results.getInt("Contact_ID"), results.getInt("User_ID"));

                System.out.println(results.getString("Title"));
                //customer.setCustomerName(results.getString("Customer_Name"));
                appointmentList.add(appointment);
            }

            statement.close();

        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }

        return appointmentList;

    }

    /**
     * Add appointment into database
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param userID
     * @param customerID
     * @param contactID
     */
    public static void addAppointment(String title, String description, String location, String type, String start, String end, int userID, int customerID, int contactID) {

        try {

            Statement statement = connection.createStatement();
            String dbquery = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) "
                    + "VALUES ('" + title + "', '"
                    +  description + "', '"
                    + location + "', '"
                    + type + "', '"
                    + start + "', '"
                    + end + "', '"
                    + java.sql.Timestamp.from(Instant.now())
                    + "', '" + currentUser + "', '"
                    + java.sql.Timestamp.from(Instant.now())
                    + "', '" + currentUser + "', "
                    + customerID + ", "
                    + userID + ", "
                    + contactID + ")";

            /*String dbquery = "INSERT INTO customers SET Customer_Name = '" + customerName
                    + "', Address = '" + customerAddress
                    + "', Postal_Code = '" + customerPostal
                    + "', Phone = " + customerPhone
                    + "', Create_Date = " + java.sql.Timestamp.from(Instant.now())
                    + ", Created_By ='" + currentUser
                    + "', Last_Update = " + java.sql.Timestamp.from(Instant.now())
                    + ", Last_Updated_By ='" + currentUser
                    + ", Division_ID = " + divisionID;*/

            statement.executeUpdate(dbquery);


        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }

    }

    /**
     * Update appointment in database
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param userID
     * @param customerID
     * @param contactID
     */
    public static void updateAppointment(int appointmentID, String title, String description, String location, String type, String start, String end, int userID, int customerID, int contactID) {

        try {

            Statement statement = connection.createStatement();
            String dbquery = "UPDATE appointments SET Title = '" + title
                    + "', Description = '" + description
                    + "', Location = '" + location
                    + "', Type = '" + type
                    + "', Start = '" + start
                    + "', End = '" + end
                    + "', Last_Update = '" + java.sql.Timestamp.from(Instant.now())
                    + "', Last_Updated_By = '" + currentUser
                    + "', Customer_ID = " + customerID
                    + ", User_ID = " + userID
                    + ", Contact_ID = " + contactID
                    + " WHERE Appointment_ID = " + appointmentID;


            statement.executeUpdate(dbquery);


        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }

    }

    /**
     * Delete appointment from database
     * @param appointmentID
     */
    public static void deleteAppointment(int appointmentID) {

        try {

            Statement statement = connection.createStatement();
            String dbquery = "DELETE FROM  appointments WHERE Appointment_ID = " + appointmentID;

            /*String dbquery = "INSERT INTO customers SET Customer_Name = '" + customerName
                    + "', Address = '" + customerAddress
                    + "', Postal_Code = '" + customerPostal
                    + "', Phone = " + customerPhone
                    + "', Create_Date = " + java.sql.Timestamp.from(Instant.now())
                    + ", Created_By ='" + currentUser
                    + "', Last_Update = " + java.sql.Timestamp.from(Instant.now())
                    + ", Last_Updated_By ='" + currentUser
                    + ", Division_ID = " + divisionID;*/

            statement.executeUpdate(dbquery);

            //Main.createAlert("Success!", "Successfully deleted appointment");


        } catch (SQLException throwables) {

            Main.createAlert("Error!", "Cannot delete appointment!");

        }

    }

    /**
     * Function for connecting to database
     */

    public static void dbConnect() {

        try {
            Class.forName(DBDRIVER);

            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            System.out.println("Connected");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            System.out.println("Not connected");
        }

    }

    /**
     * Function for disconnecting database.
     */

    public static void dbDisconnect() {

        try {
            connection.close();
            System.out.println("DB Disconnect success.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("DB Disconnect failed.");
        }

    }

}
