package com.code.dsa.dsa_automator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DSA_AutomatorController implements Initializable {
    Dragger dragger = new Dragger();

    @FXML
    private AnchorPane DebugBoard;

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

    // Graph
    int countNode = -1;
    int countEdge = 0;
    int selectedNodesForEdge = 0;
    @FXML
    private Button buttonGraph;
    @FXML
    private TitledPane inputToolBarGraph;
    @FXML
    private RadioButton isDestination;
    @FXML
    private RadioButton isEdgeCreation;
    @FXML
    private RadioButton isSource;
    @FXML
    private RadioButton isVertexCreation;
    @FXML
    private Label gNode1;
    @FXML
    private Label gNode2;
    @FXML
    private Label gNode3;
    @FXML
    private Label gNode4;
    @FXML
    private Label gNode5;
    @FXML
    private Label gNode6;
    @FXML
    private Label gNode7;
    @FXML
    private Label gNode8;
    @FXML
    private Label gNode9;
    @FXML
    private Label gNode10;
    @FXML
    private Line line;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridListInput.setVisible(false);
        dragger.setDragger(dsMenu, true);
        buttonList.setOnAction(eventList -> takeListInput());
        buttonMath.setOnAction(eventMath -> generateCalculator());
        buttonGraph.setOnAction(eventMath -> generateGraph());
        dragger.setDragger(hBoxList1, true);
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

    // Math
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

    public void constructGraph(ActionEvent event) {
        Label[] nodes = {gNode1, gNode2, gNode3, gNode4, gNode5, gNode6, gNode7, gNode8, gNode9, gNode10};
        int[] u = new int[1];
        int[] v = new int[1];
        if (isVertexCreation.isSelected()) {
            DebugBoard.setOnMousePressed(nodeC -> {
                if (isVertexCreation.isSelected() && countNode <= 9) {
                    if (nodeC.getButton() == MouseButton.PRIMARY) {
                        countNode++;
                        nodes[countNode].setVisible(true);
                        nodes[countNode].setLayoutX(nodeC.getX() - nodes[countNode].getWidth() / 2);
                        nodes[countNode].setLayoutY(nodeC.getY() - nodes[countNode].getHeight() / 2);
                        dragger.setDragger(nodes[countNode], true);
                    } else {
                        nodes[countNode].setVisible(false);
                        countNode--;
                    }
                }
            });
            System.out.println(countNode);
        }
        else if (isEdgeCreation.isSelected()) {
            for (int i = 0; i <= countNode; i++) {
                dragger.setDragger(nodes[i], false);
            }
            for (int i = 0; i <= countNode; i++) {
                int finalI = i;
                nodes[i].setOnMousePressed(mouseOnNode -> {
                    if (mouseOnNode.getButton() == MouseButton.PRIMARY) {
                        selectedNodesForEdge++;
                        if (selectedNodesForEdge % 2 == 1) {
                            u[0] = finalI;
                        }
                        else {
                            v[0] = finalI;
                            line.setLayoutX(nodes[u[0]].getLayoutX() + nodes[u[0]].getWidth() / 2);
                            line.setLayoutY(nodes[u[0]].getLayoutY() + nodes[u[0]].getHeight() / 2);
                            line.setEndX(nodes[v[0]].getLayoutX() - line.getLayoutX() + nodes[v[0]].getWidth() / 2);
                            line.setEndY(nodes[v[0]].getLayoutY() - line.getLayoutY() + nodes[v[0]].getHeight() / 2);
                            line.setVisible(true);
                            countEdge++;
                        }
                    }
                });
            }
        }
    }
    // Graph
    public void generateGraph() {
        inputToolBarGraph.setVisible(true);
    }
}