package com.scottpreston.javarobot.chapter7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Dijkstra {

    private ArrayList vertices = new ArrayList();
    private ArrayList edges = new ArrayList();
    private HashMap oldVertex = new HashMap();
    private HashMap distances = new HashMap();
    private HashSet unsettled = new HashSet();
    private HashSet settled = new HashSet();    
    
    public void addEdge(Edge e) {
        edges.add(e);
    }
    
    public void addAllEdges(ArrayList e) {
        edges = e;
    }
    
    public void addVertex(Vertex v) {
        vertices.add(v);
    }
    
    public void addAllVertices(ArrayList v) {
        vertices = v;
    }

    public int getDist(Vertex start, Vertex end) {
        int[][] adj = getAdj();
        int size = vertices.size();
        int w = 0;
        for (int i = 0; i < size; i++) {
            Vertex vi = (Vertex) vertices.get(i);
            for (int j = 0; j < size; j++) {
                Vertex vj = (Vertex) vertices.get(j);
                if (vi.equals(start) && vj.equals(end)) {
                    w = adj[i][j];
                }
            }

        }
        return w;
    }

    public void setShortDistance(Vertex v, int dist) {
        unsettled.remove(v);
        distances.put(v, new Integer(dist));
        unsettled.add(v);
    }
    
    public void setPred(Vertex a, Vertex b ){
        oldVertex.put(a,b);
    }

    public Vertex getPred(Vertex a) {
        return (Vertex)oldVertex.get(a);
    }
    
    public int getShortDistance(Vertex v) {
        Integer d = (Integer) distances.get(v);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d.intValue();
        }
    }

    public Vertex extractMinimum() {
        Iterator i = unsettled.iterator();
        int min = Integer.MAX_VALUE;
        Vertex minV = null;
        while (i.hasNext()) {
            Vertex tmp = (Vertex) i.next();
            if (getShortDistance(tmp) < min) {
                min = getShortDistance(tmp);
                minV = tmp;
            }
        }
        unsettled.remove(minV);
        return minV;
    }
    
    public void relaxNeighbors(Vertex u) {
        int[][] adj = getAdj();
        int size = vertices.size();
        for (int i = 0; i < size; i++) {
            Vertex vi = (Vertex) vertices.get(i);
            if (vi.equals(u)) { // only check this i'th column
                for (int j = 0; j < size; j++) {
                    Vertex v = (Vertex) vertices.get(j);
                    int w2 = adj[i][j];
                    // should give all adjacent vertices not settled
                    if (w2 > 0 && w2 < Integer.MAX_VALUE
                            && (settled.contains(v) == false)) {
                        // sdoes a shorter distance exist?
                        if (getShortDistance(v) > getShortDistance(u)
                                + getDist(u, v)) {
                            int d = getShortDistance(u) + getDist(u, v);
                            setShortDistance(v, d);
                            setPred(v,u);
                        }
                    }

                }
            }

        }
    }


    public ArrayList getShortestPath( Vertex start, Vertex end) {
        unsettled.add(start);
        setShortDistance(start,0);
        while (unsettled.size() > 0) {
            Vertex u = extractMinimum(); // gets shortest Vertext
            settled.add(u);
            relaxNeighbors(u);
        }
        ArrayList l = new ArrayList();
        for (Vertex v = end; v != null; v = getPred(v)) {
            l.add(v);
        }
        Collections.reverse(l);

        System.out.println("--- PRINT ORDER ---");
        for (int d=0;d < l.size();d++) {
            Vertex v = (Vertex) l.get(d);
            System.out.println(v.name);
        }
        return l;
    }

    public Vertex getVertexByName(String n) {
        int size = vertices.size();
        for (int i = 0; i < size; i++) {
            Vertex vi = (Vertex) vertices.get(i);
            if (vi.name.equals(n)) {
                return vi;
            }
        }
        return null;

    }

    private int[][] getAdj() {

        int[][] adjMatrix = new int[vertices.size()][vertices.size()];
        // init all large
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                adjMatrix[i][j] = Integer.MAX_VALUE;
            }
        }
        // set to actual values and selfs to zero
        for (int i = 0; i < vertices.size(); i++) {
            Vertex vi = (Vertex) vertices.get(i);
            for (int j = 0; j < vertices.size(); j++) {
                Vertex vj = (Vertex) vertices.get(j);
                if (i == j) {
                    adjMatrix[i][j] = 0;
                } else {
                    for (int k = 0; k < edges.size(); k++) {
                        Edge e = (Edge) edges.get(k);
                        if (e.v1.equals(vi) && e.v2.equals(vj))
                            adjMatrix[i][j] = e.weight;
                        if (e.v2.equals(vi) && e.v1.equals(vj))
                            adjMatrix[i][j] = e.weight;
                    }
                }
            }
        }

        return adjMatrix;
    }

  
    public static void main(String[] args) {
        Dijkstra dijkstra = new Dijkstra();
        Vertex a = new Vertex("a");
        dijkstra.addVertex(a);
        Vertex b = new Vertex("b");
        dijkstra.addVertex(b);
        Vertex c = new Vertex("c");
        dijkstra.addVertex(c);
        Vertex d = new Vertex("d");
        dijkstra.addVertex(d);
        dijkstra.addEdge(new Edge(a, d, 2));
        dijkstra.addEdge(new Edge(a, b, 2));
        dijkstra.addEdge(new Edge(a, c, 4));
        dijkstra.addEdge(new Edge(b, c, 1));
        dijkstra.getShortestPath(d,c);
        
        //System.out.println(d.adjToString(d.getAdj()));
    }
   
    /**
     * @return Returns the vertices.
     */
    public ArrayList getVertices() {
        return vertices;
    }
    /**
     * @param vertices The vertices to set.
     */
    public void setVertices(ArrayList vertices) {
        this.vertices = vertices;
    }
    /**
     * @return Returns the edges.
     */
    public ArrayList getEdges() {
        return edges;
    }
    /**
     * @param edges The edges to set.
     */
    public void setEdges(ArrayList edges) {
        this.edges = edges;
    }
}
