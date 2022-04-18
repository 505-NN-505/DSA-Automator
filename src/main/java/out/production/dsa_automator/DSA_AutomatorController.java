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
import java.sql.SQLException;
import java.util.*;

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
    private List<Integer> list2;

    // Math
    @FXML
    protected Button buttonMath;
    // Graph
    private List<Integer> bfsoutput;
    private Group graphGroup;
    Integer countbfs = -1;

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
    //tree

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridListInput.setVisible(false);
        dragger.setDragger(dsMenu, true);
        buttonList.setOnAction(eventList -> takeListInput());
        buttonMath.setOnAction(eventMath -> generateCalculator());
        buttonGraph.setOnAction(eventGraph -> generateGraph());
        buttonTree.setOnAction(eventTree-> generateTree());
        dragger.setDragger(hBoxList1, true);
        dragger.setDragger(hBoxList2, true);
        dragger.setDragger(hBoxList3, true);
        dragger.setDragger(hBoxList4, true);
        dragger.setDragger(hBoxList5, true);

        buttonSignIn.setOnAction(eventSignIn -> startAuthentication(true));
        buttonSignUp.setOnAction(eventSignUp -> startAuthentication(false));
        buttonHome.setOnAction(eventHome -> switchToHome());
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

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public void assignWeights(ActionEvent event) {
        if (isWeighted.isSelected()) {
            for (int i = 0; i < graphedgeList.size(); i++) {
                int finalI = i;
                int finalI1 = i;
                int finalI2 = i;
                graphedgeList.get(i).getValue().setOnMouseClicked(mouseOnLine -> {
                    if (mouseOnLine.getButton() == MouseButton.PRIMARY) {
                        System.out.println(graphedgeList.get(finalI2).getKey().getKey() + " " + graphedgeList.get(finalI2).getKey().getValue());
                        System.out.println(mouseOnLine.getX() + " " + mouseOnLine.getY());
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
                                lb.setText(str1[0]);
                                WeightLabels.add(lb);

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
                            line.setStrokeWidth(10.0);
                            line.setStartX(nodes[u[0]].getLayoutX() + nodes[u[0]].getWidth() / 2);
                            line.setStartY(nodes[u[0]].getLayoutY() + nodes[u[0]].getHeight() / 2);
//                            line.setLayoutX(nodes[u[0]].getLayoutX() + nodes[u[0]].getWidth() / 2);
//                            line.setLayoutY(nodes[u[0]].getLayoutY() + nodes[u[0]].getHeight() / 2);
                            line.setEndX(nodes[v[0]].getLayoutX() - line.getLayoutX() + nodes[v[0]].getWidth() / 2);
                            line.setEndY(nodes[v[0]].getLayoutY() - line.getLayoutY() + nodes[v[0]].getHeight() / 2);

                            edgeGroup.getChildren().add(line);

                            Pair<Integer, Integer> uv = new Pair(u[0]+1, v[0]+1);
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
        else if (isSource.isSelected()) {
            for (int i = 0; i <= countNode; i++) {
                int finalI = i;
                final boolean[] sel = {false};
                nodes[i].setOnMousePressed(mouseOnNode -> {
                    if (mouseOnNode.getButton() == MouseButton.PRIMARY) {
                        source = finalI+1;
                        nodes[source-1].setBackground(new Background(new BackgroundFill(Color.SKYBLUE, new CornerRadii(40.0),Insets.EMPTY)));
                        sel[0] = true;
                        System.out.println("thth");
                    }
                });
                if (sel[0]) break;
            }
        }

        else if (isDestination.isSelected()) {
            for (int i = 0; i <= countNode; i++) {
                int finalI = i;
                final boolean[] sel = {false};
                nodes[i].setOnMousePressed(mouseOnNode -> {
                    if (mouseOnNode.getButton() == MouseButton.PRIMARY) {
                        destination = finalI+1;
                        nodes[destination-1].setBackground(new Background(new BackgroundFill(Color.LIMEGREEN , new CornerRadii(40.0),Insets.EMPTY)));
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
//        for (int i = 0; i < edgesBellman.size(); i++) {
//            System.out.println(edgesBellman.get(i).getKey() + " " + edgesBellman.get(i).getValue());
//        }

//        System.out.println();
//        System.out.println(V + " " + countEdge);

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
                    System.out.println(cnt);
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
                System.out.println("Graph contains negative weight cycle");
                return;
            }
        }
        printArr(dist);
    }

    void printArr(int dist[])
    {
        list1 = new ArrayList<>();
        System.out.println("Vertex Distance from Source");
        for (int i = 1; i < V; ++i) {
            System.out.println(i + "\t\t" + dist[i]);
            if(dist[i] != Integer.MAX_VALUE) list1.add(dist[i]);
            else list1.add(-1);
        }
    }

    void DFSUtil(int v, boolean visited[],int cnt,List<Integer>list2)
    {
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
                DFSUtil(n, visited, cnt, list2);
            }

        }
    }

    void DFS(int v)
    {
        list1 = new ArrayList<>();
        boolean visited[] = new boolean[V];
        DFSUtil(v, visited,0,list2);
        placeList();
    }

    public void BFS(int s) throws IOException {
        list1 = new ArrayList<>();
        // Mark all the vertices as not visited(By default
        // set as false)

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
    }

    public void graphAlgo(ActionEvent event) throws IOException {
        createAdjacencyList();
        if (isBFS.isSelected()){
//            System.out.println(countNode);
//            System.out.println(countEdge);
            BFS(source);
            placeList();
        }
        if (isDFS.isSelected()){
//            System.out.println("source: "+source);
            DFS(source);
            //placeList();
        }
        if(isAP.isSelected()){
            System.out.println("AP");
            Label[] nodes = {gNode1, gNode2, gNode3, gNode4, gNode5, gNode6, gNode7, gNode8, gNode9, gNode10};
            out.production.dsa_automator.ArticulationPoint_and_Bridge AP=new ArticulationPoint_and_Bridge(countNode);
            AP.FindArticulationPoint(GraphEdges,countNode,nodes);
            /*ArrayList<Integer>ArticulationPoints = APB.FindArticulationPoint(GraphEdges,countNode);
            for(int i=0;i<ArticulationPoints.size();i++){
                int v=ArticulationPoints.get(i);
                nodes[v].setBackground(new Background(new BackgroundFill(Color.SKYBLUE, new CornerRadii(40.0), Insets.EMPTY)));
            }
             */
        }
        if(isBridge.isSelected()){
            out.production.dsa_automator.ArticulationPoint_and_Bridge B=new ArticulationPoint_and_Bridge(countNode);
            B.FindBridge(countNode,graphedgeList);
        }

        if (isshortestpath.isSelected()){

            BellmanFord(source);
            System.out.println("source: "+source);
            placeList();
        }
        if(isMST.isSelected()){
            out.production.dsa_automator.MinimumSpanningTree MST= new MinimumSpanningTree(countNode);
            ArrayList<Pair<Integer,Integer>> edgesMST= MST.FindMST(MSTEdges,countNode);
            for(int j=0;j<graphedgeList.size();j++){
                int p=graphedgeList.get(j).getKey().getKey();
                int q=graphedgeList.get(j).getKey().getValue();
                for(int i=0;i<edgesMST.size();i++){
                    int u=edgesMST.get(i).getKey();
                    int v=edgesMST.get(i).getValue();
                    if((p-1==u && q-1==v) || (p-1==v && q-1==u)){
                        graphedgeList.get(j).getValue().setStroke(Color.LIMEGREEN);
                    }
                }

            }

        }
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
            nodes[centroid].setBackground(new Background(new BackgroundFill(Color.YELLOW, new CornerRadii(40.0), Insets.EMPTY)));
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