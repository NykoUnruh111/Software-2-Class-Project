package MainPackage;

import Handlers.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

/**
 *
 * @author Nathaniel Unruh
 */
public class Main extends Application {

    /**
     * Starts the first form
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../ViewControllers/LoginForm.fxml"));
        primaryStage.setTitle("WGU Software II");
        primaryStage.setScene(new Scene(root, 640, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void createAlert(String alertHeader, String alertString) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.NONE);
        alert.setTitle(alertHeader);
        alert.setHeaderText(alertString);

        Optional<ButtonType> result = alert.showAndWait();

    }
}
