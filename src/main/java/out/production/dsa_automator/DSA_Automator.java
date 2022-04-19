package out.production.dsa_automator;

import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class DSA_Automator extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DSA_Automator.class.getResource("DSA_Automator.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        DSA_AutomatorController dsaController = fxmlLoader.getController();
        dsaController.buttonClose.setOnAction(eventClose -> {
            stage.close();
        });
        stage.setTitle("DSA_Automator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}