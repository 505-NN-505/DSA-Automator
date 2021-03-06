package out.production.dsa_automator;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
import javafx.stage.Window;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DSA_AutomatorController implements Initializable {
    Dragger dragger = new Dragger();

    boolean loggedIn = false;
    protected String currentUser;

    @FXML
    private AnchorPane DebugBoard;

    @FXML
    protected VBox dsMenu;

    // 1D List
    Integer countList = -1;
    @FXML
    protected GridPane gridListInput;
    @FXML
    protected TextField inputSizeList;
    @FXML
    protected TextField inputList;
    @FXML
    protected HBox hBoxList1;
    @FXML
    private Group listGroup;
    private List<Integer> list1;
    private List<Integer> list2;

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
    private TitledPane inputToolBarGraphAlgo;
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
    private RadioButton isBFS;
    @FXML
    private RadioButton isDFS;
    @FXML
    private RadioButton isAP;
    @FXML
    private RadioButton isshortestpath;

    @FXML
    private Button buttonSignIn;
    @FXML
    private Button buttonSignUp;

    @FXML
    private Button buttonHome;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonResetBoard;

    @FXML
    public Button buttonSignout;

    @FXML
    public Button buttonClose;

    @FXML
    private Button buttonTutorial;

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

    @FXML
    private Label warning;
    @FXML
    private Label explanation;

    //graph
    ArrayList<Pair<Pair<Integer, Integer>, Line>> graphedgeList = new ArrayList<Pair<Pair<Integer, Integer>, Line>>();
    ArrayList<Pair<Pair<Integer, Integer>, Integer>> edgesBellman = new ArrayList<Pair<Pair<Integer, Integer>, Integer>>();
    ArrayList<Pair<Integer, Integer>> GraphEdges = new ArrayList<Pair<Integer, Integer>>();
    private int V;   // No. of vertices
    private LinkedList<Integer> adj[];
    private LinkedList<Pair<Integer,Integer>> adjBellman[];//Adjacency Lists
    ArrayList<Integer> weights = new ArrayList();
    int source,destination;
    //graph

    @FXML
    private TitledPane inputGraphType;

    @FXML
    private RadioButton isUnweighted;

    @FXML
    private RadioButton isWeighted;

    //tree
    ArrayList<Pair<Pair<Integer, Integer>, Line>> edgeList = new ArrayList<Pair<Pair<Integer, Integer>, Line>>();
    ArrayList<Pair<Pair<Integer, Integer>, Line>> edgeListTree = new ArrayList<Pair<Pair<Integer, Integer>, Line>>();
    ArrayList<Pair<Integer, Integer>> TreeEdges = new ArrayList<Pair<Integer, Integer>>();

    public DSA_AutomatorController() {
    }
    //tree

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridListInput.setVisible(false);
        dragger.setDragger(dsMenu, true);
        buttonMath.setOnAction(eventMath -> generateCalculator());
        buttonGraph.setOnAction(eventGraph -> generateGraph());
        buttonTree.setOnAction(eventTree-> generateTree());
        dragger.setDragger(hBoxList1, true);
        dragger.setDragger(warning, true);

        buttonSignIn.setOnAction(eventSignIn -> startAuthentication(true));
        buttonSignUp.setOnAction(eventSignUp -> startAuthentication(false));
        buttonHome.setOnAction(eventHome -> switchToHome());
        buttonResetBoard.setOnAction(eventReset -> reset());
        buttonTutorial.setOnAction(eventReset -> switchToTutorial());

        warning.setOnMousePressed(eventWarningOff -> {
            if (eventWarningOff.getButton() == MouseButton.SECONDARY) {
                warning.setVisible(false);
            }
        });

        buttonSignout.setOnAction(eventSignOut -> {
            loggedIn = false;
            reset();
        });

//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
//        System.out.println(timeStamp);




//        long u = (System.currentTimeMillis());
//        System.out.println(u);
//        System.out.println(u+5000);
        warning.setVisible(true);

        warning.setText("sahhh");
        warning.setLayoutX(900);
        warning.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0.0), Insets.EMPTY)));
        warning.setVisible(false);

        Label lb = new Label();

        weightsGroup.getChildren().add(lb);
        lb.setVisible(false);

        lb.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0.0), Insets.EMPTY)));
        lb.setMinWidth(115);
        lb.setMinHeight(25);
        lb.setAlignment(Pos.CENTER);

        buttonMath.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                lb.setLayoutX(50);
                lb.setLayoutY(251);
                lb.setText("Math Algorithms");
                lb.setVisible(true);
            } else {
                lb.setVisible(false);
            }
        });

        buttonTree.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                lb.setLayoutX(50);
                lb.setLayoutY(253+34);
                lb.setText("Tree Algorithms");
                lb.setVisible(true);
            } else {
                lb.setVisible(false);
            }
        });

        buttonGraph.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                lb.setLayoutX(50);
                lb.setLayoutY(253+34+34+5);
                lb.setText("Graph Algorithms");
                lb.setVisible(true);
            } else {
                lb.setVisible(false);
            }
        });
    }

    public void switchToHome() {
        if (loggedIn) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Home.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                HomeController homeController = fxmlLoader.getController();
                homeController.setActiveHandle(currentUser);
                homeController.currentUserLabel.setText(currentUser);
                homeController.buttonDashboard.setOnAction(eDashboard -> homeController.loadDashboard());
                homeController.buttonRepository.setOnAction(eRepoTable -> homeController.genRepoTable(true));
                homeController.buttonMyWorks.setOnAction(eRepoTable -> homeController.genRepoTable(false));


                try {
                    homeController.inPieChart();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

                Stage stage = new Stage();
                stage.setTitle("Home");
                stage.setScene(scene);

                homeController.buttonContinueToDSA.setOnAction(eventContinue -> {
                    stage.close();
                });
                homeController.buttonSignOut.setOnAction(eventContinue -> {
                    loggedIn = false;
                    stage.close();
                });

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            warning.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0.0), Insets.EMPTY)));
            warning.setText("Log in to access\nthe repository");
            warning.setVisible(true);
        }
    }

    public void switchToTutorial() {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Tutorial.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Tutorial");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @FXML
    void saveTheWork(ActionEvent event) {
        if (loggedIn) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Save.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Save your work");
                stage.setScene(scene);

                SaveController saveController = fxmlLoader.getController();

                saveController.buttonSave.setOnAction(e -> {
                    String fileName = saveController.getTaskTitle();
                    Stage sStage = (Stage) saveController.savePane.getScene().getWindow();
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            warning.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0.0), Insets.EMPTY)));
            warning.setText("Log in to access\nthe repository");
            warning.setVisible(true);
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
            warning.setVisible(false);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Authentication.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            AuthenticationController atc = fxmlLoader.getController();
            atc.initSignIn = isLogInActive;
            atc.isSignInActive = true;
            atc.isSignUpActive = false;
            atc.showAuthUi(isLogInActive);

            atc.buttonSignIn.setOnAction(e -> {
                try {
                    System.out.println(loggedIn);
                    loggedIn = atc.verifyLogIn();
                    if (loggedIn) {
                        currentUser = atc.signInHandle.getText();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }
            });

            atc.buttonSignUp.setOnAction(e -> {
                try {
                    boolean reg = atc.insertInfo();
                    if (reg) {
                        String path = "src\\main\\myDocuments\\" + atc.signUpHandle.getText();
                        File f = new File(path);
                        boolean created = f.mkdir();
                        if (!created) {
                            System.out.println("Error!!! can't make new folder\n");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
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

    public void reset() {
        if (!edgeGroup.getChildren().isEmpty()) edgeGroup.getChildren().clear();
        if (!weightsGroup.getChildren().isEmpty()) weightsGroup.getChildren().clear();
        if (!listGroup.getChildren().isEmpty()) listGroup.getChildren().clear();

        inputGraphType.setVisible(false);
        inputToolBarGraph.setVisible(false);
        inputToolBarGraphAlgo.setVisible(false);
        InputToolBarTree.setVisible(false);
        TreeAlgorithms.setVisible(false);
        gridListInput.setVisible(false);

        edgeList.clear();
        edgesBellman.clear();
        graphedgeList.clear();
        edgeListTree.clear();
        TreeEdges.clear();
        weights.clear();
        MSTEdges.clear();

        GraphEdges.clear();

        V = 0;
        countNode = -1;
        countEdge = 0;
        countTreeNode = -1;
        countTreeEdge = 0;
        countList = -1;
        v1 = -1;
        v2 = -1;
        selectedNodesForEdge = 0;
        selectedNodesForTreeEdge = 0;
       // System.out.println("Reset " + countNode);


        if(list1 != null) list1.clear();
        if (!hBoxList1.getChildren().isEmpty()) hBoxList1.getChildren().clear();

        LowestCommonAncestor.setSelected(false);
        FindCentroid.setSelected(false);
        isVertexCreation.setSelected(false);
        isEdgeCreation.setSelected(false);
        isSource.setSelected(false);
        isBFS.setSelected(false);
        isDFS.setSelected(false);
        isshortestpath.setSelected(false);
        isAP.setSelected(false);
        isMST.setSelected(false);
        isBridge.setSelected(false);
        isUnweighted.setSelected(true);
        isWeighted.setSelected(false);
        CreateVertex.setSelected(false);
        CreateEdge.setSelected(false);

        check = 0;
        countweights = 0;
        connectedcomponent = 1;
        source = 0;
        warning.setVisible(false);
        checker = 0;
        apdone = 0;
        apdone1 = 0;

    }

    // List1D

    public void placeList() {
        try {
            HBox[] listContainer = {hBoxList1};
            countList++;
           // System.out.println(countList + " " + list1);
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

    int countweights = 0;
    public void assignWeights(ActionEvent event) {
        if (isWeighted.isSelected()) {
            for (int i = 0; i < graphedgeList.size(); i++) {
                int finalI = i;
                int finalI1 = i;
                int finalI2 = i;
                graphedgeList.get(i).getValue().setOnMouseClicked(mouseOnLine -> {
                    if (mouseOnLine.getButton() == MouseButton.PRIMARY) {
                       // System.out.println(graphedgeList.get(finalI2).getKey().getKey() + " " + graphedgeList.get(finalI2).getKey().getValue());
                       // System.out.println(mouseOnLine.getX() + " " + mouseOnLine.getY());
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GraphInput.fxml"));
                            Scene weightScene = new Scene(fxmlLoader.load());
                            Stage weightStage = new Stage();
                            weightStage.setTitle("Graph Weight Input");
                            weightStage.setScene(weightScene);

                            GraphInputController GIController = fxmlLoader.getController();

                            GIController.buttonEnter.setOnAction(eWeightInput -> {
                                String[] str1 = GIController.WeightValue.getText().trim().split("\\s+");

                                Label lb = new Label();
                                lb.toFront();
                                lb.setText(str1[0]);

                                dragger.setDragger(lb, true);
                                WeightLabels.add(lb);

                                countweights++;


                                double coX = mouseOnLine.getX();
                                double coY = mouseOnLine.getY();

                                lb.setLayoutX(coX);
                                lb.setLayoutY(coY - 50);

                                weightsGroup.getChildren().add(lb);

                                lb.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, new CornerRadii(0.0), Insets.EMPTY)));
                                lb.setMinWidth(25);
                                lb.setMinHeight(25);
                                lb.setAlignment(Pos.CENTER);

                                weights.add(Integer.parseInt(str1[0]));

                                Pair<Integer, Integer> uv = new Pair(graphedgeList.get(finalI1).getKey().getKey(), graphedgeList.get(finalI1).getKey().getValue());
                                Pair<Integer, Integer> vu = new Pair(graphedgeList.get(finalI1).getKey().getValue(),graphedgeList.get(finalI1).getKey().getKey());
                                Pair<Pair<Integer, Integer>, Integer> p1 = new Pair(uv, Integer.parseInt(str1[0]));
                                Pair<Pair<Integer, Integer>, Integer> p2 = new Pair(vu, Integer.parseInt(str1[0]));
                                edgesBellman.add(p1);
                                edgesBellman.add(p2);

                                Pair<Integer, Integer> uvMST = new Pair(graphedgeList.get(finalI1).getKey().getKey()-1, graphedgeList.get(finalI1).getKey().getValue()-1);
                                Pair<Pair<Integer, Integer>, Integer> MSTp1 = new Pair(uvMST, Integer.parseInt(str1[0]));
                                MSTEdges.add(MSTp1);


                                weightStage.close();
                            });

                            weightStage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public void constructGraph(ActionEvent event) {

        warning.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0.0), Insets.EMPTY)));

        Label[] nodes = {gNode1, gNode2, gNode3, gNode4, gNode5, gNode6, gNode7, gNode8, gNode9, gNode10};

        buttonResetBoard.setOnAction(eventReset -> {
            for (int i = 0; i < 10; i++) {
                nodes[i].setVisible(false);
                nodes[i].setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(40.0), Insets.EMPTY)));
            }
            reset();
        });

        buttonSignout.setOnAction(eventSignOut -> {
            loggedIn = false;
            for (int i = 0; i < 10; i++) {
                nodes[i].setVisible(false);
                nodes[i].setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(40.0), Insets.EMPTY)));
            }
            reset();
        });

        int[] u = new int[1];
        int[] v = new int[1];
        if (isVertexCreation.isSelected()) {
            DebugBoard.setOnMousePressed(nodeC -> {
                if (isVertexCreation.isSelected() && countNode <= 9) {
                    if (nodeC.getButton() == MouseButton.PRIMARY) {
                        countNode++;
                       // System.out.println("Construct " + countNode);
                        if (countNode > 9) {
                            countNode--;
                            warning.setText("Node limit exceeded");
                            warning.setVisible(true);
                        }
                        else {
                            nodes[countNode].setVisible(true);
                            nodes[countNode].setLayoutX(nodeC.getX() - nodes[countNode].getWidth() / 2);
                            nodes[countNode].setLayoutY(nodeC.getY() - nodes[countNode].getHeight() / 2);

                            warning.setVisible(false);
                        }

                    } else {
                        nodes[countNode].setVisible(false);
                        countNode--;
                    }
                }
            });
        }
        else if (isEdgeCreation.isSelected()) {

            if(countNode == -1) {
              //  System.out.println("choose source");
                warning.setText("No vertex available\nCreate Vertex first");
                warning.setVisible(true);
                isEdgeCreation.setSelected(false);
                return;
            }

            else{
                warning.setVisible(false);
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
                            } else {
                                v[0] = finalI;
                                Line line = new Line();
                                line.toBack();
                                line.setStroke(Color.WHITE);
                                line.setStrokeWidth(10.0);
                                line.setStartX(nodes[u[0]].getLayoutX() + nodes[u[0]].getWidth() / 2);
                                line.setStartY(nodes[u[0]].getLayoutY() + nodes[u[0]].getHeight() / 2);
//                            line.setLayoutX(nodes[u[0]].getLayoutX() + nodes[u[0]].getWidth() / 2);
//                            line.setLayoutY(nodes[u[0]].getLayoutY() + nodes[u[0]].getHeight() / 2);
                                line.setEndX(nodes[v[0]].getLayoutX() - line.getLayoutX() + nodes[v[0]].getWidth() / 2);
                                line.setEndY(nodes[v[0]].getLayoutY() - line.getLayoutY() + nodes[v[0]].getHeight() / 2);

                                edgeGroup.getChildren().add(line);

                                Pair<Integer, Integer> uv = new Pair(u[0] + 1, v[0] + 1);
                                Pair<Integer, Integer> puv = new Pair(u[0], v[0]);
                                Pair<Pair<Integer, Integer>, Line> p = new Pair(uv, line);
                                graphedgeList.add(p);
                                GraphEdges.add(puv);
                                countEdge++;
                            }
                        }
                    });
                }
            }
        }
        else if (isSource.isSelected()) {
            for (int i = 0; i <= countNode; i++) {
                int finalI = i;
                final boolean[] sel = {false};
                nodes[i].setOnMousePressed(mouseOnNode -> {
                    if (mouseOnNode.getButton() == MouseButton.PRIMARY) {
                        source = finalI+1;
                        nodes[source-1].setBackground(new Background(new BackgroundFill(Color.SKYBLUE, new CornerRadii(40.0),Insets.EMPTY)));
                        sel[0] = true;
                       // System.out.println("thth");
                    }
                });
                if (sel[0]) break;
            }
        }


    }
    // Graph
    public void generateGraph() {
        inputGraphType.setVisible(true);
        inputToolBarGraph.setVisible(true);
        inputToolBarGraphAlgo.setVisible(true);
        InputToolBarTree.setVisible(false);
        TreeAlgorithms.setVisible(false);
        gridListInput.setVisible(false);
    }


    public void createAdjacencyList(){
        V = countNode+2;
        adj = new LinkedList[V];
       // adjBellman = new LinkedList[V];

        for (int i=0; i<V; ++i) {
            adj[i] = new LinkedList();
           // adjBellman[i] = new LinkedList();
        }

//        void addEdge(int v,int w)
//        {
//            adj[v].add(w);
//        }
        for(int i=0; i<countEdge; i++){
            int v = graphedgeList.get(i).getKey().getKey();
            int w = graphedgeList.get(i).getKey().getValue();
            adj[v].add(w);
            adj[w].add(v);

           // int weight = weights.get(i);
           // Pair<Integer,Integer> p1 = new Pair(w,weight);
           // Pair<Integer,Integer> p2 = new Pair(v,weight);
            //adjBellman[v].add(p1);
           // adjBellman[w].add(p2);
        }

    }

    void BellmanFord(int src) {


        int dist[] = new int[V];

        // Step 1: Initialize distances from src to all other
        // vertices as INFINITE
        for (int i = 0; i < V; ++i)
            dist[i] = Integer.MAX_VALUE;
        dist[src] = 0;
        int cnt = 0;
        // Step 2: Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other vertex can
        // have at-most |V| - 1 edges
        for (int i = 0; i < V; ++i) {
            for (int j = 0; j < 2 * countEdge; ++j) {
                int u = edgesBellman.get(j).getKey().getKey();
                int v = edgesBellman.get(j).getKey().getValue();
                int weight = edgesBellman.get(j).getValue();;
                if (dist[u] < Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    cnt++;
                  //  System.out.println(cnt);
                    for(int k=0; k<countEdge; k++){
                        if(graphedgeList.get(k).getKey().getKey() == u && graphedgeList.get(k).getKey().getValue()==v
                                || graphedgeList.get(k).getKey().getKey() == v && graphedgeList.get(k).getKey().getValue()==u){
                            if(cnt % 5 == 0)  graphedgeList.get(k).getValue().setStroke(Color.LIMEGREEN);
                            if(cnt % 5 == 1)  graphedgeList.get(k).getValue().setStroke(Color.YELLOW);
                            if(cnt % 5 == 2)  graphedgeList.get(k).getValue().setStroke(Color.BLUE);
                            if(cnt % 5 == 3)  graphedgeList.get(k).getValue().setStroke(Color.ORANGE);
                            if(cnt % 5 == 4)  graphedgeList.get(k).getValue().setStroke(Color.SKYBLUE);
                        }
                    }
                }
            }
        }

        // Step 3: check for negative-weight cycles. The above
        // step guarantees shortest distances if graph doesn't
        // contain negative weight cycle. If we get a shorter
        // path, then there is a cycle.
        for (int j = 0; j < 2 * countEdge; ++j) {
            int u = edgesBellman.get(j).getKey().getKey();
            int v = edgesBellman.get(j).getKey().getValue();
            int weight = edgesBellman.get(j).getValue();
            if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
              //  System.out.println("Graph contains negative weight cycle");
                return;
            }
        }
        printArr(dist);
        warning.setText("Source : Blue Node \nBlue Labels: weights\nUsed edges: colored\nUnused edges: white");
        warning.setVisible(true);
    }

    void printArr(int dist[])
    {
        list1 = new ArrayList<>();
       // System.out.println("Vertex Distance from Source");
        for (int i = 1; i < V; ++i) {
          //  System.out.println(i + "\t\t" + dist[i]);
            if(dist[i] != Integer.MAX_VALUE) list1.add(dist[i]);
            else list1.add(-1);
        }
        placeList();
    }

    Integer check = 0;
    void DFSUtil(int v, boolean visited[],int cnt,List<Integer>list1)
    {


        check++;
        cnt++;
        visited[v-1] = true;
        System.out.print(v + " ");
        list1.add(v);

        Iterator<Integer> i = adj[v].listIterator();
        while (i.hasNext()) {
            int n = i.next();
            System.out.print(visited[n-1]);
            if (!visited[n-1]) {
                for (int j = 0; j < countEdge; j++) {
                    if (graphedgeList.get(j).getKey().getKey() == v && graphedgeList.get(j).getKey().getValue() == n
                            || graphedgeList.get(j).getKey().getKey() == n && graphedgeList.get(j).getKey().getValue() == v) {
                        if (cnt %5 == 1) graphedgeList.get(j).getValue().setStroke(Color.LIMEGREEN);
                        if (cnt %5 == 2) graphedgeList.get(j).getValue().setStroke(Color.YELLOW);
                        if (cnt %5 == 3) graphedgeList.get(j).getValue().setStroke(Color.BLUE);
                        if (cnt %5 == 4) graphedgeList.get(j).getValue().setStroke(Color.ORANGE);
                        if (cnt %5 == 0) graphedgeList.get(j).getValue().setStroke(Color.SKYBLUE);
                    }
                }
                DFSUtil(n, visited, cnt, list1);
            }

        }
        warning.setText("Source: Blue Node \nUsed edges: colored\nUnused edges: white");
        warning.setVisible(true);

    }

    void DFS(int v)
    {
        list1 = new ArrayList<>();
        boolean visited[] = new boolean[V];
        DFSUtil(v, visited,0,list1);
        if(check != 0)placeList();

    }


    public void BFS(int s) throws IOException {

        list1 = new ArrayList<>();
        // Mark all the vertices as not visited(By default
        // set as false)
        //System.out.println(s);
        if(s == 0){
           // System.out.println("choose source");
            warning.setText("Bfs cannot run \nwithout a source!\nChoose Source");
            warning.setVisible(true);
            isBFS.setSelected(false);
            return;
        }
        warning.setVisible(false);
        boolean visited[] = new boolean[V];
        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // Mark the current node as visited and enqueue it
        visited[s]=true;
        queue.add(s);
        int cnt = 0;
        while (queue.size() != 0)
        {
            cnt++;
            // Dequeue a vertex from queue and print it
            s = queue.poll();
//            System.out.print(s+" "+cnt+"\n");
            // bfsoutput.add(s);
            list1.add(s);

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<Integer> i = adj[s].listIterator();
            while (i.hasNext())
            {
                int n = i.next();

                if (!visited[n])
                {
                    visited[n] = true;
                    queue.add(n);
                    for(int j=0; j<countEdge; j++){
                        if(graphedgeList.get(j).getKey().getKey() == s && graphedgeList.get(j).getKey().getValue()==n
                                || graphedgeList.get(j).getKey().getKey() == n && graphedgeList.get(j).getKey().getValue()==s){
                            if(cnt %5 == 1)  graphedgeList.get(j).getValue().setStroke(Color.LIMEGREEN);
                            if(cnt %5 == 2)  graphedgeList.get(j).getValue().setStroke(Color.YELLOW);
                            if(cnt %5 == 3)  graphedgeList.get(j).getValue().setStroke(Color.BLUE);
                            if(cnt %5 == 4)  graphedgeList.get(j).getValue().setStroke(Color.ORANGE);
                            if(cnt %5 == 0)  graphedgeList.get(j).getValue().setStroke(Color.SKYBLUE);
                        }
                    }
                }
            }
        }

        if(list1.size() != countNode)
            connectedcomponent++;
        //System.out.println("cc" + connectedcomponent);
        placeList();
        warning.setText("Source: Blue Node\nUsed edges: colored\nUnused edges: white");
        warning.setVisible(true);
    }

    int connectedcomponent = 1;
    public void hudaiBFS(int s) throws IOException {

        int cnt = 0;
        boolean visited[] = new boolean[V];
        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // Mark the current node as visited and enqueue it
        visited[s]=true;
        queue.add(s);

        while (queue.size() != 0)
        {

            s = queue.poll();

            Iterator<Integer> i = adj[s].listIterator();
            while (i.hasNext())
            {
                int n = i.next();

                if (!visited[n])
                {
                    visited[n] = true;
                    cnt++;
                    queue.add(n);

                }
            }
        }

        if(cnt != countNode) {
            connectedcomponent++;
            //System.out.println(cnt+ " "+ countNode);
        }
        else connectedcomponent=1;
       // System.out.println("cc "+connectedcomponent);

    }

    int checker = 0, apdone = 0,apdone1=0;
    public void graphAlgo(ActionEvent event) throws IOException {
        createAdjacencyList();
        if (isBFS.isSelected()){
            if(source == 0){
                // System.out.println("choose source");
                warning.setText("Bfs cannot run \nwithout a source!\nChoose Source");
                warning.setVisible(true);
                isBFS.setSelected(false);
                return;
            }

            else {
                warning.setVisible(false);
                if (checker == 0) {
//            System.out.println(countNode);
//            System.out.println(countEdge);
                    BFS(source);
                    checker = 1;
                } else {
                    warning.setText("reset to run\nanother algorithm");
                    warning.setVisible(true);
                    isBFS.setSelected(false);
                }
            }

        }
        if (isDFS.isSelected()){

            if(source == 0){
                // System.out.println("choose source");
                warning.setText("No source selected\nChoose Source first");
                warning.setVisible(true);
                isDFS.setSelected(false);
                check = 0;
                return;
            }
            warning.setVisible(false);

            if(countNode == -1){
                // System.out.println("choose source");
                warning.setText("No vertex created\nCreate vertex first");
                warning.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0.0), Insets.EMPTY)));
                warning.setVisible(true);
                isDFS.setSelected(false);
                check = 0;
                // System.out.println("rgrgreg");
                return;
            }

            else{
                warning.setVisible(false);
                if (checker == 0) {
                    //System.out.println("source: "+source);
                    DFS(source);
                    //placeList();
                    checker = 1;
                } else {
                    warning.setText("reset to run\nanother algorithm");
                    warning.setVisible(true);
                    isDFS.setSelected(false);
                }
            }
        }
        if(isAP.isSelected()) {

            if(countNode!=-1)hudaiBFS(1);
            if (connectedcomponent > 1 ) {
                // System.out.println("choose source");

                warning.setText("graph is disconnected");
                warning.setVisible(true);
                isAP.setSelected(false);
            }
            else if (countNode == -1 ) {
                // System.out.println("choose source");

                warning.setText("no vertex created!");
                warning.setVisible(true);
                isAP.setSelected(false);
            }
            else {
                if (checker == 0 || (checker==1 && apdone == 1)) {
                    warning.setVisible(false);
                    // System.out.println("AP");
                    Label[] nodes = {gNode1, gNode2, gNode3, gNode4, gNode5, gNode6, gNode7, gNode8, gNode9, gNode10};
                    buttonResetBoard.setOnAction(eventReset -> {
                        for (int i = 0; i < 10; i++) {
                            nodes[i].setVisible(false);
                            nodes[i].setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(40.0), Insets.EMPTY)));

                        }
                        reset();

                    });
                    buttonSignout.setOnAction(eventSignOut -> {
                        loggedIn = false;
                        for (int i = 0; i < 10; i++) {
                            nodes[i].setVisible(false);
                            nodes[i].setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(40.0), Insets.EMPTY)));
                        }
                        reset();
                    });
                    out.production.dsa_automator.ArticulationPoint_and_Bridge AP = new ArticulationPoint_and_Bridge(countNode);
                    AP.FindArticulationPoint(GraphEdges, countNode, nodes);
            /*ArrayList<Integer>ArticulationPoints = APB.FindArticulationPoint(GraphEdges,countNode);
            for(int i=0;i<ArticulationPoints.size();i++){
                int v=ArticulationPoints.get(i);
                nodes[v].setBackground(new Background(new BackgroundFill(Color.SKYBLUE, new CornerRadii(40.0), Insets.EMPTY)));
            }
             */
                    warning.setText("Red node:\nArticulation point\nUncolored node: No\narticulation point");
                    warning.setVisible(true);
                    checker = 1;
                    apdone = 1;
                }
                else{
                    warning.setText("reset to run\nanother algorithm");
                    warning.setVisible(true);
                    isAP.setSelected(false);
                }

            }
        }
        if(isBridge.isSelected()){
            if(countNode != -1) hudaiBFS(1);
            if (connectedcomponent > 1 ) {
                // System.out.println("choose source");
                warning.setText("graph is disconnected");
                warning.setVisible(true);
                isBridge.setSelected(false);
            }
            else if (countNode == -1 ) {
                // System.out.println("choose source");

                warning.setText("no vertex created!");
                warning.setVisible(true);
                isBridge.setSelected(false);
            }
            else {
                if(checker == 0 || (checker==1 && apdone == 1)) {
                    out.production.dsa_automator.ArticulationPoint_and_Bridge B = new ArticulationPoint_and_Bridge(countNode);
                    B.FindBridge(countNode, graphedgeList);
                    warning.setText("Articulation edges:\ncolored");
                    warning.setVisible(true);
                    checker = 1;
                    apdone1 = 1;
                }
                else{
                    warning.setText("reset to run\nanother algorithm");
                    warning.setVisible(true);
                    isBridge.setSelected(false);
                }
            }

        }

        if (isshortestpath.isSelected()){

            if(source == 0){
                // System.out.println("choose source");
                warning.setText("No source selected\nChoose Source first");
                warning.setVisible(true);
                isshortestpath.setSelected(false);
                //return;
            }
            //warning.setVisible(false);
            if(countweights != countEdge){
                // System.out.println("give weights");
                warning.setText("No weights given\nGive weights first");
                warning.setVisible(true);
                isshortestpath.setSelected(false);
               // return;
            }
            if (checker == 1) {
                warning.setText("reset to run\nanother algorithm");
                warning.setVisible(true);
                isshortestpath.setSelected(false);
            }
            if(checker!=1 && countweights == countEdge && source!=0){
                warning.setVisible(false);
                BellmanFord(source);
                // System.out.println("source: "+source);
                checker = 1;
            }

        }
        if(isMST.isSelected()){
            if(countNode != -1)hudaiBFS(1);
            if(countweights != countEdge){
               // System.out.println("give weights");
                warning.setText("No weights given\nGive weights first");
                warning.setVisible(true);
                isMST.setSelected(false);
            }
            if (countNode == -1 ) {
                // System.out.println("choose source");

                warning.setText("no vertex created!");
                warning.setVisible(true);
                isMST.setSelected(false);
            }
            if (connectedcomponent > 1 ) {
                // System.out.println("choose source");
                warning.setText("graph is disconnected");
                warning.setVisible(true);
                isMST.setSelected(false);
            }
            if (checker == 1) {
                warning.setText("reset to run\nanother algorithm");
                warning.setVisible(true);
                isMST.setSelected(false);
            }
            if(checker!=1 && connectedcomponent<=1 && countNode!=-1 && countweights == countEdge) {
                    warning.setVisible(false);
                    out.production.dsa_automator.MinimumSpanningTree MST = new MinimumSpanningTree(countNode);
                    ArrayList<Pair<Integer, Integer>> edgesMST = MST.FindMST(MSTEdges, countNode);
                    for (int j = 0; j < graphedgeList.size(); j++) {
                        int p = graphedgeList.get(j).getKey().getKey();
                        int q = graphedgeList.get(j).getKey().getValue();
                        for (int i = 0; i < edgesMST.size(); i++) {
                            int u = edgesMST.get(i).getKey();
                            int v = edgesMST.get(i).getValue();
                            if ((p - 1 == u && q - 1 == v) || (p - 1 == v && q - 1 == u)) {
                                graphedgeList.get(j).getValue().setStroke(Color.LIMEGREEN);
                            }
                        }

                    }
                    warning.setText("Source: Blue Node\nBlue Labels: weights\ntree edges: colored\nUnused edges: white");
                    warning.setVisible(true);
                    checker = 1;

            }

        }
    }

    //Tree Starts from here
    public void constructTree(ActionEvent event) {

        warning.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0.0), Insets.EMPTY)));

        Label[] tnodes = {tNode1, tNode2, tNode3, tNode4, tNode5, tNode6, tNode7, tNode8, tNode9, tNode10};
        int[] u = new int[1];
        int[] v = new int[1];

        for(int i=0; i<10; i++)
            tnodes[i].setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(40.0), Insets.EMPTY)));


        buttonResetBoard.setOnAction(eventReset -> {
            for (int i = 0; i < 10; i++) {
                tnodes[i].setVisible(false);
                tnodes[i].setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(40.0), Insets.EMPTY)));

            }
            reset();

        });

        buttonSignout.setOnAction(eventSignOut -> {
            loggedIn = false;
            for (int i = 0; i < 10; i++) {
                tnodes[i].setVisible(false);
                tnodes[i].setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(40.0), Insets.EMPTY)));
            }
            reset();
        });

        if (CreateVertex.isSelected()) {
            DebugBoard.setOnMousePressed(nodeC -> {
                if (CreateVertex.isSelected() && countTreeNode <= 9) {
                    if (nodeC.getButton() == MouseButton.PRIMARY) {
                        countTreeNode++;
                        if (countTreeNode > 9) {
                            warning.setText("Node limit exceeded");
                            warning.setVisible(true);
                        } else {
                            warning.setVisible(false);
                            tnodes[countTreeNode].setVisible(true);
                            tnodes[countTreeNode].setLayoutX(nodeC.getX() - tnodes[countTreeNode].getWidth() / 2);
                            tnodes[countTreeNode].setLayoutY(nodeC.getY() - tnodes[countTreeNode].getHeight() / 2);
                        }
                    }
                    else {


                        tnodes[countTreeNode].setVisible(false);
                        countTreeNode--;
                    }
                }
            });
        }
        else if (CreateEdge.isSelected()) {
            if(countTreeNode == -1){
               // System.out.println("choose source");
                warning.setText("No vertex available\ncreate Vertex first");
                warning.setVisible(true);
                CreateEdge.setSelected(false);
            }
            else {
                warning.setVisible(false);
                for (int i = 0; i <= countTreeNode; i++) {
                    dragger.setDragger(tnodes[i], false);
                }
                for (int i = 0; i <= countTreeNode; i++) {
                    int finalI = i;
                    tnodes[i].setOnMousePressed(mouseOnNode -> {
                        if (mouseOnNode.getButton() == MouseButton.PRIMARY) {
                            selectedNodesForTreeEdge++;
                            if (selectedNodesForTreeEdge % 2 == 1) {
                                u[0] = finalI;
                            } else {
                                v[0] = finalI;
                                Line line = new Line();
                                line.setStroke(Color.WHITE);
                                line.setLayoutX(tnodes[u[0]].getLayoutX() + tnodes[u[0]].getWidth() / 2);
                                line.setLayoutY(tnodes[u[0]].getLayoutY() + tnodes[u[0]].getHeight() / 2);
                                line.setEndX(tnodes[v[0]].getLayoutX() - line.getLayoutX() + tnodes[v[0]].getWidth() / 2);
                                line.setEndY(tnodes[v[0]].getLayoutY() - line.getLayoutY() + tnodes[v[0]].getHeight() / 2);

                                edgeGroup.getChildren().add(line);

                                Pair<Integer, Integer> uv = new Pair(u[0], v[0]);
                                Pair<Pair<Integer, Integer>, Line> p = new Pair(uv, line);
                                p.getValue().setStrokeWidth(10);
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
    }

    public void generateTree() {
        inputGraphType.setVisible(false);
        inputToolBarGraph.setVisible(false);
        inputToolBarGraphAlgo.setVisible(false);
        InputToolBarTree.setVisible(true);
        TreeAlgorithms.setVisible(true);
        gridListInput.setVisible(false);
    }
    public void ImplementTreeAlgorithms(ActionEvent event){
        if(LowestCommonAncestor.isSelected()){
           // System.out.println(countTreeNode + " " + countTreeEdge);
            if(countTreeEdge != countTreeNode){
               // System.out.println("choose source");
                warning.setText("Not a tree,try again!");
                warning.setVisible(true);
                LowestCommonAncestor.setSelected(false);
            }

            else {

                warning.setVisible(false);
                Label[] tnodes = {tNode1, tNode2, tNode3, tNode4, tNode5, tNode6, tNode7, tNode8, tNode9, tNode10};

                buttonResetBoard.setOnAction(eventReset -> {
                    for (int i = 0; i < 10; i++) {
                        tnodes[i].setVisible(false);
                        tnodes[i].setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(40.0), Insets.EMPTY)));

                    }
                    reset();
                });
                buttonSignout.setOnAction(eventSignOut -> {
                    loggedIn = false;
                    for (int i = 0; i < 10; i++) {
                        tnodes[i].setVisible(false);
                        tnodes[i].setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(40.0), Insets.EMPTY)));
                    }
                    reset();
                });

                for (int i = 0; i <= countTreeNode; i++) {
                    int finalI = i;
                    tnodes[i].setOnMousePressed(mouseOnNode -> {
                        if (mouseOnNode.getButton() == MouseButton.PRIMARY) {
                            if (v1 == -1) {
                                v1 = finalI;
                                tnodes[v1].setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(40.0), Insets.EMPTY)));
                            } else if (v2 == -1) {
                                v2 = finalI;
                                tnodes[v2].setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(40.0), Insets.EMPTY)));
                            }
                            if (v1 >= 0 && v2 >= 0) {
                                out.production.dsa_automator.LowestCommonAncestor L = new LowestCommonAncestor(countTreeNode);
                                Integer lca = L.FindLCA(TreeEdges, countTreeNode, v1, v2);
                                //System.out.println(lca);
                                tnodes[lca].setBackground(new Background(new BackgroundFill(Color.SKYBLUE, new CornerRadii(40.0), Insets.EMPTY)));
                                v1 = -1;
                                v2 = -1;
                                warning.setText("green nodes: inputs \nBlue node: LCA");
                                warning.setVisible(true);
                            }
                        }
                    });
                }

            }
        }
        if(FindCentroid.isSelected()) {

            if (countTreeEdge != countTreeNode) {
                // System.out.println("choose source");
                warning.setText("Not a tree,try again!");
                warning.setVisible(true);
                FindCentroid.setSelected(false);
            } else {
                Label[] tnodes = {tNode1, tNode2, tNode3, tNode4, tNode5, tNode6, tNode7, tNode8, tNode9, tNode10};
                buttonResetBoard.setOnAction(eventReset -> {
                    for (int i = 0; i < 10; i++) {
                        tnodes[i].setVisible(false);
                        tnodes[i].setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(40.0), Insets.EMPTY)));
                    }
                    reset();
                });
                buttonSignout.setOnAction(eventSignOut -> {
                    loggedIn = false;
                    for (int i = 0; i < 10; i++) {
                        tnodes[i].setVisible(false);
                        tnodes[i].setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(40.0), Insets.EMPTY)));
                    }
                    reset();
                });
                out.production.dsa_automator.CentroidFinder CF = new CentroidFinder(countTreeNode);
                Integer centroid = CF.FindCentroid(TreeEdges, countTreeNode);
                tnodes[centroid].setBackground(new Background(new BackgroundFill(Color.YELLOW, new CornerRadii(40.0), Insets.EMPTY)));

                warning.setText("yellow node: centroid");
                warning.setVisible(true);
            }

        }
    }

    @FXML
    private Group weightsGroup;
    @FXML
    protected TextField tf;
    // For MST
    @FXML
    private RadioButton isMST;
    @FXML
    private RadioButton isBridge;
    ArrayList<Pair<Pair<Integer, Integer>, Integer>> MSTEdges=new ArrayList<>();
    ArrayList<Label> WeightLabels=new ArrayList<>();
}