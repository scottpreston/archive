package com.scottpreston.javarobot.chapter7;

public class Edge {

    public String name;
    public Vertex v1;
    public Vertex v2;
    public int weight;
    
    public Edge() {}

    // constructs with two vertices and a weight
    public Edge(Vertex v1, Vertex v2, int w) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = w;
    }

    public String toString() {
        return "{v1=" + v1.name + ",v2=" + v2.name + ",w=" + weight + "}";
    }

}
