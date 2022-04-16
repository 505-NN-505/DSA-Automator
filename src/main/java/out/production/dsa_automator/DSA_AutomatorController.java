package out.production.dsa_automator;

import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DSA_AutomatorController implements Initializable {
    Dragger dragger = new Dragger();

    boolean loggedIn = false;
    protected String currentUser;

    @FXML
    private AnchorPane DebugBoard;

    @FXML
    protected VBox dsMenu;

    @FXML
    private MenuItem buttonSave;

    // 1D List
    Integer countList = -1;
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
    @FXML
    protected HBox hBoxList2;
    @FXML
    protected HBox hBoxList3;
    @FXML
    protected HBox hBoxList4;
    @FXML
    protected HBox hBoxList5;
    @FXML
    private Group listGroup;
    private List<Integer> list1;

    // Math
    @FXML
    protected Button buttonMath;

    // Graph
    Integer countNode = -1;
    Integer countEdge = 0;
    Integer selectedNodesForEdge = 0;
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
    private Group edgeGroup;

    @FXML
    private Button buttonSignIn;
    @FXML
    private Button buttonSignUp;

    @FXML
    private Button buttonHome;
    @FXML
    private Button buttonSearchRepo;
    // tree
    Integer countTreeNode = -1;
    Integer countTreeEdge = 0;
    Integer selectedNodesForTreeEdge = 0;
    Integer v1=-1,v2=-1;
    @FXML
    private Button buttonTree;
    @FXML
    private TitledPane InputToolBarTree;
    @FXML
    private TitledPane TreeAlgorithms;
    @FXML
    private RadioButton CreateVertex;
    @FXML
    private RadioButton CreateEdge;
    @FXML
    private RadioButton LowestCommonAncestor;
    @FXML
    private RadioButton FindCentroid;
    @FXML
    private Label tNode1;
    @FXML
    private Label tNode2;
    @FXML
    private Label tNode3;
    @FXML
    private Label tNode4;
    @FXML
    private Label tNode5;
    @FXML
    private Label tNode6;
    @FXML
    private Label tNode7;
    @FXML
    private Label tNode8;
    @FXML
    private Label tNode9;
    @FXML
    private Label tNode10;
    ArrayList<Pair<Pair<Integer, Integer>, Line>> edgeList = new ArrayList<Pair<Pair<Integer, Integer>, Line>>();
    ArrayList<Pair<Pair<Integer, Integer>, Line>> edgeListTree = new ArrayList<Pair<Pair<Integer, Integer>, Line>>();
    ArrayList<Pair<Integer, Integer>> TreeEdges = new ArrayList<Pair<Integer, Integer>>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridListInput.setVisible(false);
        dragger.setDragger(dsMenu, true);
        buttonList.setOnAction(eventList -> takeListInput());
        buttonMath.setOnAction(eventMath -> generateCalculator());
        buttonGraph.setOnAction(eventMath -> generateGraph());
        buttonTree.setOnAction(eventMath-> generateTree());
        dragger.setDragger(hBoxList1, true);
        dragger.setDragger(hBoxList2, true);
        dragger.setDragger(hBoxList3, true);
        dragger.setDragger(hBoxList4, true);
        dragger.setDragger(hBoxList5, true);

        buttonSignIn.setOnAction(eventSignIn -> startAuthentication(true));
        buttonSignUp.setOnAction(eventSignUp -> startAuthentication(false));
    }

    @FXML
    public void switchToHome(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void saveTheWork(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Save.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Save your work");
            stage.setScene(scene);

            SaveController saveController = fxmlLoader.getController();

            saveController.buttonSave.setOnAction(e -> {
                String fileName = saveController.getTaskTitle();
                Stage sStage = (Stage)saveController.savePane.getScene().getWindow();
                sStage.close();
                takeScreenShot(fileName);
                try {
                    saveController.insertToTaskTable(currentUser, saveController.getTaskTitle(), address);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

                // populating the category tables
                for (int i = 0; i < saveController.selectedTag.size(); i++) {
                    try {
                        saveController.populateCatTable(currentUser, fileName, saveController.selectedTag.get(i));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String address;

    void takeScreenShot(String fileName) {
        WritableImage image = DebugBoard.snapshot(new SnapshotParameters(), null);
        try {
            address = "src\\main\\myDocuments\\" + currentUser + "\\" + fileName + ".png";
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File(address));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startAuthentication(boolean isLogInActive) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Authentication.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            AuthenticationController atc = fxmlLoader.getController();
            atc.initSignIn = isLogInActive;
            atc.isSignInActive = true;
            atc.isSignUpActive = false;
            atc.showAuthUi(isLogInActive);

            atc.buttonSignIn.setOnAction(e -> {
                try {
                    loggedIn = atc.verifyLogIn();
                    if (loggedIn) {
                        currentUser = atc.signInHandle.getText();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            });

            atc.buttonSignUp.setOnAction(e -> {
                try {
                    atc.insertInfo();
                    String path = "src\\main\\myDocuments\\" + atc.signUpHandle.getText();
                    File f = new File(path);
                    boolean created = f.mkdir();
                    if (!created) {
                        System.out.println("Error!!! can't make new folder\n");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            });
            Stage stage = new Stage();
            stage.setTitle("Authentication");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        HBox[] listContainer = {hBoxList1, hBoxList2, hBoxList3, hBoxList4, hBoxList5};
        try {
            countList++;
            for (int i = 0; i < list1.size(); i++) {
                FXMLLoader fxmlloader = new FXMLLoader();
                fxmlloader.setLocation(getClass().getResource("List.fxml"));
                HBox newBox = fxmlloader.load();
                ListController listController = fxmlloader.getController();
                listController.setData(list1.get(i));
                listContainer[countList].getChildren().add(newBox);
            }
            listGroup.getChildren().add(listContainer[countList]);
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
                    } else {
                        nodes[countNode].setVisible(false);
                        countNode--;
                    }
                }
            });
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
                            Line line = new Line();
                            line.setStroke(Color.WHITE);
                            line.setLayoutX(nodes[u[0]].getLayoutX() + nodes[u[0]].getWidth() / 2);
                            line.setLayoutY(nodes[u[0]].getLayoutY() + nodes[u[0]].getHeight() / 2);
                            line.setEndX(nodes[v[0]].getLayoutX() - line.getLayoutX() + nodes[v[0]].getWidth() / 2);
                            line.setEndY(nodes[v[0]].getLayoutY() - line.getLayoutY() + nodes[v[0]].getHeight() / 2);

                            edgeGroup.getChildren().add(line);

                            Pair<Integer, Integer> uv = new Pair(u[0], v[0]);
                            Pair<Pair<Integer, Integer>, Line> p = new Pair(uv, line);
                            edgeList.add(p);
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
   //Tree Starts from here
    public void constructTree(ActionEvent event) {
        Label[] nodes = {tNode1, tNode2, tNode3, tNode4, tNode5, tNode6, tNode7, tNode8, tNode9, tNode10};
        int[] u = new int[1];
        int[] v = new int[1];
        if (CreateVertex.isSelected()) {
            DebugBoard.setOnMousePressed(nodeC -> {
                if (CreateVertex.isSelected() && countTreeNode <= 9) {
                    if (nodeC.getButton() == MouseButton.PRIMARY) {
                        countTreeNode++;
                        nodes[countTreeNode].setVisible(true);
                        nodes[countTreeNode].setLayoutX(nodeC.getX() - nodes[countTreeNode].getWidth() / 2);
                        nodes[countTreeNode].setLayoutY(nodeC.getY() - nodes[countTreeNode].getHeight() / 2);
                    } else {
                        nodes[countTreeNode].setVisible(false);
                        countTreeNode--;
                    }
                }
            });
        }
        else if (CreateEdge.isSelected()) {
            for (int i = 0; i <= countTreeNode; i++) {
                dragger.setDragger(nodes[i], false);
            }
            for (int i = 0; i <= countTreeNode; i++) {
                int finalI = i;
                nodes[i].setOnMousePressed(mouseOnNode -> {
                    if (mouseOnNode.getButton() == MouseButton.PRIMARY) {
                        selectedNodesForTreeEdge++;
                        if (selectedNodesForTreeEdge % 2 == 1) {
                            u[0] = finalI;
                        }
                        else {
                            v[0] = finalI;
                            Line line = new Line();
                            line.setStroke(Color.WHITE);
                            line.setLayoutX(nodes[u[0]].getLayoutX() + nodes[u[0]].getWidth() / 2);
                            line.setLayoutY(nodes[u[0]].getLayoutY() + nodes[u[0]].getHeight() / 2);
                            line.setEndX(nodes[v[0]].getLayoutX() - line.getLayoutX() + nodes[v[0]].getWidth() / 2);
                            line.setEndY(nodes[v[0]].getLayoutY() - line.getLayoutY() + nodes[v[0]].getHeight() / 2);

                            edgeGroup.getChildren().add(line);

                            Pair<Integer, Integer> uv = new Pair(u[0], v[0]);
                            Pair<Pair<Integer, Integer>, Line> p = new Pair(uv, line);
                            edgeListTree.add(p);
                            TreeEdges.add(uv);
                            //System.out.println(u[0]);
                            //System.out.println(v[0]);
                            countTreeEdge++;
                        }
                    }
                });
            }
        }
    }

    public void generateTree() {
        InputToolBarTree.setVisible(true);
        TreeAlgorithms.setVisible(true);
    }
    public void ImplementTreeAlgorithms(ActionEvent event){
        if(LowestCommonAncestor.isSelected()){
            Label[] nodes = {tNode1, tNode2, tNode3, tNode4, tNode5, tNode6, tNode7, tNode8, tNode9, tNode10};
            for(int i=0;i<=countTreeNode;i++){
                int finalI = i;
                nodes[i].setOnMousePressed(mouseOnNode ->{
                    if(mouseOnNode.getButton()== MouseButton.PRIMARY){
                        if(v1==-1){
                            v1=finalI;
                            nodes[v1].setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(40.0), Insets.EMPTY)));
                        }
                        else if(v2==-1){
                            v2=finalI;
                            nodes[v2].setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(40.0), Insets.EMPTY)));
                        }
                        if(v1>=0  && v2>=0){
                            out.production.dsa_automator.LowestCommonAncestor L=new LowestCommonAncestor(countTreeNode);
                            Integer lca=L.FindLCA(TreeEdges,countTreeNode,v1,v2);
                            //System.out.println(lca);
                            nodes[lca].setBackground(new Background(new BackgroundFill(Color.SKYBLUE, new CornerRadii(40.0), Insets.EMPTY)));
                            v1=-1;
                            v2=-1;
                        }
                    }
                });
            }
        }
        if(FindCentroid.isSelected()){
            Label[] nodes = {tNode1, tNode2, tNode3, tNode4, tNode5, tNode6, tNode7, tNode8, tNode9, tNode10};
            out.production.dsa_automator.CentroidFinder CF=new CentroidFinder(countTreeNode);
            Integer centroid= CF.FindCentroid(TreeEdges, countTreeNode);
            nodes[centroid].setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, new CornerRadii(40.0), Insets.EMPTY)));
        }
    }
}