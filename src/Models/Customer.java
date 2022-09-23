package Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Logic and data for the customer model
 *
 */
public class Customer {

    private final SimpleIntegerProperty customerId = new SimpleIntegerProperty();

    private final SimpleStringProperty customerName = new SimpleStringProperty();
    private final SimpleStringProperty customerAddress = new SimpleStringProperty();
    private final SimpleStringProperty customerDivision = new SimpleStringProperty();
    private final SimpleStringProperty customerCountry = new SimpleStringProperty();
    private final SimpleStringProperty customerZip = new SimpleStringProperty();
    private final SimpleStringProperty customerPhone = new SimpleStringProperty();


    public Customer(int id, String name, String address, String city, String country, String phone, String zip) {
        setCustomerID(id);
        setCustomerName(name);
        setCustomerAddress(address);
        setCustomerDivision(city);
        setCustomerCountry(country);
        setCustomerPhone(phone);
        setCustomerZip(zip);
    }


    /**
     * Getter for customer ID
     * @return
     */

    public int getCustomerID() {

        return customerId.intValue();

    }

    /**
     * Setter for customer ID
     *
     */

    public void setCustomerID(int id) {

        customerId.set(id);

    }

    /**
     * Getter for customer name
     * @return
     */

    public String getName() {

        return customerName.get();

    }

    /**
     * Setter for customer name
     */

    public void setCustomerName(String name) {

        customerName.set(name);

    }

    /**
     * Getter for customer address
     * @return
     */

    public String getCustomerAddress() {

        return customerAddress.get();

    }

    /**
     * Setter for customer address
     *
     */

    public void setCustomerAddress(String address) {

        customerAddress.set(address);

    }

    /**
     * Getter for customer city
     * @return
     */

    public String getCustomerDivision() {

        return customerDivision.get();

    }

    /**
     * Setter for customer city
     *
     */

    public void setCustomerDivision(String city) {

        customerDivision.set(city);

    }

    /**
     * Getter for customer country
     * @return
     */

    public String getCustomerCountry() {

        return customerCountry.get();

    }

    /**
     * Setter for customer country
     * @return
     */

    public void setCustomerCountry(String country) {

        customerCountry.set(country);

    }

    /**
     * Getter for customer zip code
     * @return
     */

    public String getCustomerZip() {

        return customerZip.get();

    }

    /**
     * Setter for customer zip code
     *
     */

    public void setCustomerZip(String zip) {

        customerZip.set(zip);

    }

    /**
     * Getter for customer phone number
     * @return
     */
    public String getCustomerPhone() {

        return customerPhone.get();

    }

    /**
     * Setter for customer phone number
     *
     */
    public void setCustomerPhone(String phone) {

        customerPhone.set(phone);

    }


}
