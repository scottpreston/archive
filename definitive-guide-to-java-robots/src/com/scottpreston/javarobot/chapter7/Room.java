package com.scottpreston.javarobot.chapter7;

import java.util.ArrayList;

public class Room extends Vertex {
    
    private ArrayList regions = new ArrayList();
    private ArrayList edges = new ArrayList();

    public Room(String name) {
        super(name);
    }
    
    public void addRegion(Region r) {
        regions.add(r);
    }
    
    public void addEdge(Region r1, Region r2, DistanceVector vect) {
        vect.v1 = r1;
        vect.v2 = r2;
        edges.add(vect);
    }
    
    public static Room getBasement() {
        // 1st create regions
        Region a = new Region("home",36);
        a.setCharacteristic(new int[]{0,0,1,1});
        // add specific location of the trash can
        Region b = new Region("trash",36);
        b.setCharacteristic(new int[]{1,0,0,1});
        b.addWayPoint("can",80,20);
        Region c = new Region("desk",24);
        c.setCharacteristic(new int[]{1,1,0,0});
        Region d = new Region("exit",24);
        d.setCharacteristic(new int[]{0,1,0,1});
        Region e = new Region("treadmill",48);
        c.setCharacteristic(new int[]{0,1,1,0});
        Region f = new Region("fridge",36);
        c.setCharacteristic(new int[]{1,0,0,0});
        Region g = new Region("sofa",24);
        c.setCharacteristic(new int[]{0,0,0,1});
        // create room by linking regions
        Room basement = new Room("shop");
        basement.addEdge(a,b,new DistanceVector(190,260));
        basement.addEdge(b,d,new DistanceVector(290,288));
        basement.addEdge(b,c,new DistanceVector(260,216));
        basement.addEdge(c,d,new DistanceVector(315,60));
        basement.addEdge(d,e,new DistanceVector(280,72));
        basement.addEdge(e,f,new DistanceVector(345,260));
        basement.addEdge(e,g,new DistanceVector(325,200));
        basement.addEdge(g,f,new DistanceVector(210,72));
        return basement;
        
    }
    
    public ArrayList getRegions() {
        return regions;
    }
    public ArrayList getEdges() {
        return edges;
    }
    public void setEdges(ArrayList edges) {
        this.edges = edges;
    }
    public void setRegions(ArrayList regions) {
        this.regions = regions;
    }

}
