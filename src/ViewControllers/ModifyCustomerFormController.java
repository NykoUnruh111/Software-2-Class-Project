package ViewControllers;

import Handlers.DatabaseHandler;
import MainPackage.Main;
import Models.Country;
import Models.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class handles all the logic for modifying a customer
 */
public class ModifyCustomerFormController implements Initializable {


    public Button modCustomerCancelButton;
    public ComboBox<Country> modCustomerCountryComboBox;
    public ComboBox<Division> modCustomerDivisionComboBox;

    public TextField modCustomerIDTextField;
    public TextField modCustomerNameTextField;
    public TextField modCustomerAddressTextField;
    public TextField modCustomerPostalTextField;
    public TextField modCustomerPhoneTextField;

    private ObservableList<String> errorList = FXCollections.observableArrayList();

    /**
     * Function called when save button is pressed and updates customer
     * @param event
     */
    public void handleModCustomerUpdate(ActionEvent event) {

        if (validateName(modCustomerNameTextField.getText()) && validateCountry()
                && validateDivision() && validateAddress(modCustomerAddressTextField.getText())
                && validatePostal(modCustomerPostalTextField.getText()) && validatePhone(modCustomerPhoneTextField.getText())) {

            DatabaseHandler.updateCustomer(DatabaseHandler.customerList.get(MainFormController.currentlySelectedCustomerIndex).getCustomerID(), modCustomerNameTextField.getText(), modCustomerAddressTextField.getText(), modCustomerPostalTextField.getText(), modCustomerPhoneTextField.getText(), modCustomerDivisionComboBox.getSelectionModel().getSelectedItem().getDivisionID());
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
    public void handleModCustomerCancel(ActionEvent event) {

        closeWindow();

    }

    /**
     * Function to close current window
     */
    private void closeWindow() {

        Stage stage = (Stage) modCustomerCancelButton.getScene().getWindow();

        stage.close();

    }

    /**
     *Initialize
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //DatabaseHandler.customerList.get(MainFormController.currentlySelectedCustomerIndex)

        modCustomerIDTextField.setText(Integer.toString(DatabaseHandler.customerList.get(MainFormController.currentlySelectedCustomerIndex).getCustomerID()));

        modCustomerNameTextField.setText(DatabaseHandler.customerList.get(MainFormController.currentlySelectedCustomerIndex).getName());

        modCustomerAddressTextField.setText(DatabaseHandler.customerList.get(MainFormController.currentlySelectedCustomerIndex).getCustomerAddress());

        modCustomerPostalTextField.setText(DatabaseHandler.customerList.get(MainFormController.currentlySelectedCustomerIndex).getCustomerZip());

        modCustomerPhoneTextField.setText(DatabaseHandler.customerList.get(MainFormController.currentlySelectedCustomerIndex).getCustomerPhone());

        modCustomerCountryComboBox.getItems().clear();

        modCustomerCountryComboBox.setItems(DatabaseHandler.countryList);

        modCustomerCountryComboBox.setConverter(new StringConverter<Country>() {
            @Override
            public String toString(Country country) {
                return country.getCountryName();
            }

            @Override
            public Country fromString(String s) {
                return null;
            }
        });

        //Populate country combobox
        for (int i = 0; i < DatabaseHandler.countryList.size(); i++) {

            if (DatabaseHandler.countryList.get(i).getCountryName().equals(DatabaseHandler.customerList.get(MainFormController.currentlySelectedCustomerIndex).getCustomerCountry())) {

                modCustomerCountryComboBox.setValue(DatabaseHandler.countryList.get(i));
                updateDivisions();

                break;
            }

        }


        //Populate state/province combobox
        for (int i = 0; i < DatabaseHandler.divisionList.size(); i++) {

            if (DatabaseHandler.divisionList.get(i).getDivisionName().equals(DatabaseHandler.customerList.get(MainFormController.currentlySelectedCustomerIndex).getCustomerDivision())) {

                modCustomerDivisionComboBox.setValue(DatabaseHandler.divisionList.get(i));
                System.out.println("Customer division is: " + DatabaseHandler.divisionList.get(i).getDivisionName());
                break;
            }

        }

    }

    /**
     * Called any time the country selection is changed to update the available states/provinces
     * @param event
     */
    public void handleCountryChange(ActionEvent event) {

       updateDivisions();


    }

    /**
     * Updates available divisions
     */
    private void updateDivisions() {

        modCustomerDivisionComboBox.getItems().clear();

        if (!DatabaseHandler.countryList.isEmpty())
            DatabaseHandler.getDivisionList(modCustomerCountryComboBox.getSelectionModel().getSelectedItem().getCountryID());

        modCustomerDivisionComboBox.setItems(DatabaseHandler.divisionList);

        modCustomerDivisionComboBox.setConverter(new StringConverter<Division>() {
            @Override
            public String toString(Division division) {
                return division.getDivisionName();
            }

            @Override
            public Division fromString(String s) {
                return null;
            }
        });

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

        if (modCustomerCountryComboBox.getSelectionModel().getSelectedIndex() == -1) {

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

        if (modCustomerDivisionComboBox.getSelectionModel().getSelectedIndex() == -1) {

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
