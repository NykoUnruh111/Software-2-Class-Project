package Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Logic and data for the contact model
 *
 */
public class Contact {

    private final SimpleIntegerProperty contactId = new SimpleIntegerProperty();
    private final SimpleStringProperty contactName = new SimpleStringProperty();
    private final SimpleStringProperty contactEmail = new SimpleStringProperty();

    public Contact(int ID, String name, String email) {

        setContactID(ID);
        setContactName(name);
        setContactemail(email);

    }

    /**
     * Getter for contactID
     */
    public int getContactID() {

        return contactId.intValue();

    }

    /**
     * Setter for contactID
     * @param ID
     */
    public void setContactID(int ID) {

        contactId.set(ID);

    }

    /**
     * Getter for contact name
     * @return
     */
    public String getContactName() {

        return contactName.get();

    }

    /**
     * Setter for contact name
     * @param name
     */
    public void setContactName(String name) {

        contactName.set(name);

    }

    /**
     * Getter for contact email
     * @return
     */
    public String getContactEmail() {

        return contactEmail.get();

    }

    /**
     * Setter for contact email
     * @param email
     */
    public void setContactemail(String email) {

        contactEmail.set(email);

    }

}
