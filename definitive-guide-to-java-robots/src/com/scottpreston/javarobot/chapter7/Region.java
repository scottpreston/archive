package com.scottpreston.javarobot.chapter7;

import java.awt.Point;
import java.util.ArrayList;

public class Region extends Vertex{

    //list of way points in center
    private ArrayList wayPoints = new ArrayList();
    // start point in region absolute coordinates
    private int size = 0;
    // used to determine position within region N,E,S,W readings
    private int[] characteristic = new int[]{0,0,0,0};

    // constructor
    public Region(String name, int size) {
        super(name);
        this.size = size;
        // just add center point for later use.
        addWayPoint(NavPoint.CENTER_POINT,50,50);
    }

    // navigation points
    public void addWayPoint(NavPoint p) {
        wayPoints.add(p);
    }
    public void addWayPoint(String name,int x, int y) {
        addWayPoint(new NavPoint(name,x,y));
    }

    //  get scaled start point
    //  output will be percentage from measured
    public Point getScaledPoint(int x,int y) {
        double totalSize = size * 2;
        int x2 = (int)(x/totalSize*100);
        int y2 = (int)(y/totalSize*100);
        return new Point(x2,y2);
    }
    
    // returns in actual inches
    public double getScaledMagnitude(double m) {
        double scale = size * 2 / 100.0;
        return m*scale;
    }
    
    //get points by name
    public NavPoint getPointByName(String name) {
        NavPoint pt = null;
        for (int x=0; x<wayPoints.size() ; x++) {
            NavPoint tmp = (NavPoint)wayPoints.get(x);
            if (tmp.name.equalsIgnoreCase(name)){
                pt = tmp;
                break;
            }
        }
        return pt;
    }

    public ArrayList getWayPoints() {
        return wayPoints;
    }
   
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int[] getCharacteristic() {
        return characteristic;
    }
    public void setCharacteristic(int[] characteristic) {
        this.characteristic = characteristic;
    }
}
