package Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 * Logic and data for the country model
 *
 */
public class Country {

    private final SimpleIntegerProperty countryID = new SimpleIntegerProperty();
    private final SimpleStringProperty countryName = new SimpleStringProperty();

    public Country(int countryID, String countryName) {

        setCountryID(countryID);
        setCountryName(countryName);

    }

    /**
     * Getter for countryID
     */
    public int getCountryID() {

        return countryID.intValue();

    }

    /**
     * Setter for countryID
     */
    public void setCountryID(int ID) {

        countryID.set(ID);

    }

    /**
     * Getter for country name
     */
    public String getCountryName() {

        return countryName.get();

    }

    /**
     * Setter for country name
     */
    public void setCountryName(String name) {

        countryName.set(name);

    }
}
