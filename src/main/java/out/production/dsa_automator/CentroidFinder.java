package out.production.dsa_automator;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class CentroidFinder {
    CentroidFinder(Integer countNodes){
    }
    class Graph {
        private LinkedList<Integer> adjLists[];
        private boolean visited[];
        private boolean visited2[];
        private int par[];
        private int sub[];
        private int c;
        private int n,p;
        // Graph creation
        Graph(int vertices) {
            adjLists = new LinkedList[vertices];
            visited = new boolean[vertices];
            visited2= new boolean[vertices];
            par=new int[vertices];
            sub=new int[vertices];
            for (int i = 0; i <vertices; i++){
                adjLists[i] = new LinkedList<Integer>();
                par[i]=-1;
                sub[i]=0;
            }
            n=vertices;

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
            int ret=0;
            Iterator<Integer> ite = adjLists[vertex].listIterator();
            while (ite.hasNext()) {
                Integer adj = ite.next();
                if (!visited[adj])

                    DFS(adj,vertex);
                int v=adj;
                ret+=sub[v];
            }
            sub[vertex]=ret+1;
        }
        void DFS2(int vertex){
            visited2[vertex]=true;
            int ret=0;
            Iterator<Integer> ite = adjLists[vertex].listIterator();
            while (ite.hasNext()) {
                Integer adj = ite.next();
                if (!visited2[adj]){
                    DFS2(adj);
                    int v=adj;
                    if(sub[v]>ret){
                        ret=sub[v];
                    }
                }
            }
            if(n-sub[vertex]>ret){
                ret=n-sub[vertex];
            }


            //System.out.println(vertex +" "+ ret+" "+n);
            if(ret<=n/2){
                c=vertex;
            }
        }
    }
    public Integer FindCentroid(ArrayList<Pair<Integer, Integer>> TreeEdges, Integer countNodes) {
        CentroidFinder.Graph g = new CentroidFinder.Graph(countNodes+1);

        for(int i=0;i<TreeEdges.size();i++){
            Pair<Integer,Integer> uv=TreeEdges.get(i);
            Integer u=uv.getKey();
            Integer v=uv.getValue();
            //System.out.println(u);
            //System.out.println(v);
            g.addEdge(u,v);
            g.addEdge(v,u);
        }
        g.DFS(0,-1);
        g.DFS2(0);

        int v=g.c;
        //for(int i=0;i<=countNodes;i++) System.out.println(g.sub[i]);
        //System.out.println(v);
        return v;
    }

}
