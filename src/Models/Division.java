package Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Logic and data for the division model
 *
 */
public class Division {
    private final SimpleIntegerProperty divisionID = new SimpleIntegerProperty();
    private final SimpleStringProperty divisionName = new SimpleStringProperty();

    public Division(int divisionID, String divisionName) {

        setDivisionID(divisionID);
        setDivisionName(divisionName);

    }

    /**
     * Getter for divisionID
     */
    public int getDivisionID() {

        return divisionID.intValue();

    }

    /**
     * Setter for divisionID
     */
    public void setDivisionID(int ID) {

        divisionID.set(ID);

    }

    /**
     * Getter for division name
     */
    public String getDivisionName() {

        return divisionName.get();

    }

    /**
     * Setter for division name
     */
    public void setDivisionName(String name) {

        divisionName.set(name);

    }

}
