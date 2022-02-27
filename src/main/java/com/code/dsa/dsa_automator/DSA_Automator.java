package com.code.dsa.dsa_automator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DSA_Automator extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DSA_Automator.class.getResource("DSA_Automator.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("DSA");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}