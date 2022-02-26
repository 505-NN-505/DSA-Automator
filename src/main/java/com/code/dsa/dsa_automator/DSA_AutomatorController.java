package com.code.dsa.dsa_automator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DSA_AutomatorController implements Initializable {
    Dragger dragger = new Dragger();

    @FXML
    protected VBox dsMenu;

    // 1D List
    @FXML
    protected Button buttonList;
    @FXML
    protected GridPane gridListInput;
    @FXML
    protected TextField inputSizeList;
    @FXML
    protected TextField inputList;
    @FXML
    protected Button buttonListInputEnter;
    @FXML
    protected Button buttonListInputRetry;
    @FXML
    protected HBox hBoxList1;
    private List<Integer> list1;

    // Math
    @FXML
    protected Button buttonMath;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridListInput.setVisible(false);
        dragger.setDraggerOn(dsMenu);
        buttonList.setOnAction(eventList -> takeListInput());
        buttonMath.setOnAction(eventMath -> generateCalculator());
        dragger.setDraggerOn(hBoxList1);
    }

    public void toggleDS() {
        if (dsMenu.isVisible())
            dsMenu.setVisible(false);
        else
            dsMenu.setVisible(true);
    }

    // List1D
    public void takeListInput() {
        gridListInput.setVisible(true);
        buttonListInputEnter.setVisible(true);
        buttonListInputRetry.setVisible(false);

        buttonListInputEnter.setOnAction(eventListInputEnter -> {
            try {
                list1 = new ArrayList<>();
                String[] str1 = inputList.getText().trim().split("\\s+");
                for (int i = 0; i < str1.length; i++) {
                    list1.add(Integer.parseInt(str1[i]));
                }

                placeList();

                hBoxList1.setVisible(true);

                inputSizeList.clear();
                inputList.clear();
                gridListInput.setVisible(false);
            } catch (NumberFormatException listEx) {
                String prev = inputList.getText();
                inputList.setText(prev + " (Error: Format integers only)");

                buttonListInputEnter.setVisible(false);
                buttonListInputRetry.setVisible(true);

                buttonListInputRetry.setOnAction(eventListInputRetry -> {
                    inputSizeList.clear();
                    inputList.clear();
                    gridListInput.setVisible(false);
                    buttonListInputRetry.setVisible(false);
                });
            }
        });
    }
    public void placeList() {
        try {
            for (int i = 0; i < list1.size(); i++) {
                FXMLLoader fxmlloader = new FXMLLoader();
                fxmlloader.setLocation(getClass().getResource("List.fxml"));
                HBox newBox = fxmlloader.load();
                ListController listController = fxmlloader.getController();
                listController.setData(list1.get(i));
                hBoxList1.getChildren().add(newBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateCalculator() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Calculator.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Calculator");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}