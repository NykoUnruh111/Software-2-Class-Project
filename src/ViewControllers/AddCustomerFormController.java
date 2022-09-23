package ViewControllers;

import Handlers.DatabaseHandler;
import MainPackage.Main;
import Models.Country;
import Models.Customer;
import Models.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import javax.xml.crypto.Data;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class handles all the logic for adding a new customer
 */
public class AddCustomerFormController implements Initializable {

    public Button addCustomerSaveButton;
    public Button addCustomerCancelButton;

    public ComboBox<Country> addCustomerCountryComboBox;
    public ComboBox<Division> addCustomerDivisionComboBox;

    public TextField addCustomerIDTextField;
    public TextField addCustomerNameTextField;
    public TextField addCustomerAddressTextField;
    public TextField addCustomerPostalTextField;
    public TextField addCustomerPhoneTextField;


    private ObservableList<String> errorList = FXCollections.observableArrayList();

    /**
     * Function called when save button is pressed and adds new customer
     * @param event
     */
    public void handleAddCustomerSave(ActionEvent event) {

        if (validateName(addCustomerNameTextField.getText()) && validateCountry()
                && validateDivision() && validateAddress(addCustomerAddressTextField.getText())
                    && validatePostal(addCustomerPostalTextField.getText()) && validatePhone(addCustomerPhoneTextField.getText())) {

            DatabaseHandler.addCustomer(addCustomerNameTextField.getText(), addCustomerAddressTextField.getText(), addCustomerPostalTextField.getText(), addCustomerPhoneTextField.getText(), addCustomerDivisionComboBox.getSelectionModel().getSelectedItem().getDivisionID());
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
    public void handleAddCustomerCancel(ActionEvent event) {

        closeWindow();

    }

    /**
     * Function to close current window
     */
    private void closeWindow() {


        Stage stage = (Stage) addCustomerCancelButton.getScene().getWindow();

        stage.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Selected country: " + addCustomerCountryComboBox.getSelectionModel().getSelectedIndex());

        addCustomerCountryComboBox.getItems().clear();

        addCustomerCountryComboBox.setItems(DatabaseHandler.countryList);

        addCustomerCountryComboBox.setConverter(new StringConverter<Country>() {
            @Override
            public String toString(Country country) {
                return country.getCountryName();
            }

            @Override
            public Country fromString(String s) {
                return null;
            }
        });

    }

    /**
     * Called any time the country selection is changed to update the available states/provinces
     * @param event
     */
    public void handleCountryChange(ActionEvent event) {

       /* addCustomerDivisionComboBox.getItems().clear();

        Country country = (Country) addCustomerCountryComboBox.getSelectionModel().getSelectedItem();
        System.out.println(country.getCountryName());
        addCustomerDivisionComboBox.getItems().addAll(DatabaseHandler.getDivisionList(country.getCountryID()));*/

        addCustomerDivisionComboBox.getItems().clear();

        if (!DatabaseHandler.countryList.isEmpty())
            DatabaseHandler.getDivisionList(addCustomerCountryComboBox.getSelectionModel().getSelectedItem().getCountryID());

        addCustomerDivisionComboBox.setItems(DatabaseHandler.divisionList);

        addCustomerDivisionComboBox.setConverter(new StringConverter<Division>() {
            @Override
            public String toString(Division division) {
                return division.getDivisionName();
            }

            @Override
            public Division fromString(String s) {
                return null;
            }
        });

        System.out.println("Selected country: " + addCustomerCountryComboBox.getSelectionModel().getSelectedIndex());



    }

    /**
     * Validate that there is a valid name
     * @param name
     * @return
     */
    private boolean validateName(String name) {

        if (name.isEmpty()) {

            errorList.add("Please enter a name!");
            return false;

        } else {

            return true;

        }

    }

    /**
     * Validate a country has been selected
     * @return
     */
    private boolean validateCountry() {

        if (addCustomerCountryComboBox.getSelectionModel().getSelectedIndex() == -1) {

            errorList.add("Please select a country!");
            return false;

        } else {

            return true;

        }



    }

    /**
     * Validate a divison has been selected
     * @return
     */
    private boolean validateDivision() {

        if (addCustomerDivisionComboBox.getSelectionModel().getSelectedIndex() == -1) {

            errorList.add("Please select a state/province!");
            return false;

        } else {

            return true;

        }

    }

    /**
     * Validate an address has been entered
     * @param address
     * @return
     */
    private boolean validateAddress(String address) {

        if (address.isEmpty()) {

            errorList.add("Please enter an address!");
            return false;

        } else {

            return true;

        }

    }

    /**
     * Validate a postal code has been entered
     * @param postal
     * @return
     */
    private boolean validatePostal(String postal) {

        if (postal.isEmpty()) {

            errorList.add("Please enter your postal code!");
            return false;

        } else {

            return true;

        }


    }

    /**
     * Validate a phone number has been entered
     * @param phone
     * @return
     */
    private boolean validatePhone(String phone) {

        if (phone.isEmpty()) {

            errorList.add("Please enter a phone number!");
            return false;

        } else {

            return true;

        }

    }

}
