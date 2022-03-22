package out.production.dsa_automator;

import javafx.scene.shape.Line;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class LowestCommonAncestor {
    LowestCommonAncestor(Integer countNodes){
    }
    class Graph {
        private LinkedList<Integer> adjLists[];
        private boolean visited[];
        private int par[];
        // Graph creation
        Graph(int vertices) {
            adjLists = new LinkedList[vertices];
            visited = new boolean[vertices];
            par=new int[vertices];
            for (int i = 0; i <vertices; i++){
                adjLists[i] = new LinkedList<Integer>();
                par[i]=-1;
            }

        }

        // Add edges
        void addEdge(int src, int dest) {
            adjLists[src].add(dest);
        }

        // DFS algorithm
        void DFS(int vertex,int p) {
            visited[vertex] = true;
            //System.out.print(vertex + " "+ p);
            par[vertex]=p;
            Iterator<Integer> ite = adjLists[vertex].listIterator();
            while (ite.hasNext()) {
                Integer adj = ite.next();
                if (!visited[adj])

                    DFS(adj,vertex);
            }
        }
    }
    public Integer FindLCA(ArrayList<Pair<Integer, Integer>> TreeEdges,Integer countNodes,Integer a,Integer b) {
        Graph g = new Graph(countNodes+1);

        for(int i=0;i<TreeEdges.size();i++){
             Pair<Integer,Integer> uv=TreeEdges.get(i);
             Integer u=uv.getKey();
             Integer v=uv.getValue();
             //System.out.println(u);
             //System.out.println(v);
             g.addEdge(u,v);
             g.addEdge(v,u);
        }
        g.par[0]=-1;
        Boolean[] mark = new Boolean[countNodes+1];
        for(int i=0;i<=countNodes;i++) mark[i]=false;
        g.DFS(0,-1);
        Integer lca=-1;
        while(true){
            mark[a]=true;
            //System.out.println(a);
            a=g.par[a];
            if(a==-1) break;
        }
        while(true) {
            if (mark[b] == true) {
                lca = b;
                break;
            }
            //System.out.println(b);
            b = g.par[b];
            if (b == -1) break;
            mark[b] = true;
        }


        return lca;
    }
}
