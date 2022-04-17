package out.production.dsa_automator;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class ArticulationPoint_and_Bridge {
    ArticulationPoint_and_Bridge(Integer countNodes){
    }
    class Graph {
        private LinkedList<Integer> adjLists[];
        private boolean visited[];
        private int par[];
        private int timer;
        private int n;
        private int start[];
        private int low[];
        private boolean AP[];
        // Graph creation
        Graph(int vertices) {
            adjLists = new LinkedList[vertices];
            visited = new boolean[vertices];
            par = new int[vertices];
            start = new int[vertices];
            low = new int[vertices];
            AP = new boolean[vertices+1];
            n=vertices;
            for (int i = 0; i < vertices; i++) {
                adjLists[i] = new LinkedList<Integer>();
                par[i] = -1;
                start[i]=0;
                low[i]=0;
                //AP[i]=false;
            }
            timer=0;

        }

        // Add edges
        void addEdge(int src, int dest) {
            adjLists[src].add(dest);
        }

        // DFS algorithm
        Integer child=0;
        void DFS(int vertex, int p) {
            visited[vertex] = true;
            //System.out.print(vertex + " "+ p);
            par[vertex] = p;
            timer++;
            low[vertex]=timer;
            start[vertex]=timer;
            child=0;
            Iterator<Integer> ite = adjLists[vertex].listIterator();
            while (ite.hasNext()) {
                Integer adj = ite.next();
                int v=adj;
                if(v==p) continue;
                if(visited[v]==false){

                    DFS(v,vertex);
                    if(low[v]<low[vertex]) low[vertex]=low[v];
                }
                else if(visited[v]==true){
                    if(start[v]<low[vertex]) low[vertex]=start[v];
                    continue;
                }
                //System.out.println(vertex);
                //System.out.println(child);
                if(p==-1){
                    int cnt=0;
                    for(int j=0;j<n;j++){
                        if(par[j]==vertex) cnt++;
                    }
                    if(cnt>1) AP[vertex]=true;
                }
                else if(low[v]>=start[vertex] && p!=-1) AP[vertex]=true;
            }
        }
    }
    public void FindArticulationPoint(ArrayList<Pair<Integer, Integer>> GraphEdges, Integer countNodes,Label nodes[]) {
        ArticulationPoint_and_Bridge.Graph g = new ArticulationPoint_and_Bridge.Graph(countNodes + 1);

        for (int i = 0; i < GraphEdges.size(); i++) {
            Pair<Integer, Integer> uv = GraphEdges.get(i);
            Integer u = uv.getKey();
            Integer v = uv.getValue();
            //System.out.println(u);
            //System.out.println(v);
            g.addEdge(u, v);
            g.addEdge(v, u);
        }
        g.par[0] = -1;
        Boolean[] mark = new Boolean[countNodes + 1];
        for (int i = 0; i <= countNodes; i++) mark[i] = false;
        g.DFS(0, -1);
        ArrayList<Integer>ArticulationPoints = new ArrayList<Integer>();
        for(int i=0;i<=countNodes;i++){
            if(g.AP[i]==true){
                System.out.println(i);
                nodes[i].setBackground(new Background(new BackgroundFill(Color.SKYBLUE, new CornerRadii(40.0), Insets.EMPTY)));
                ArticulationPoints.add(i);
            }
        }
        //return ArticulationPoints;

    }
}
