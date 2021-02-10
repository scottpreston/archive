package com.scottpreston.javarobot.chapter7;

import java.util.ArrayList;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.WebSerialClient;

public class IndoorNavigation extends ObstacleNavigation {

    private Room currentRoom;
    private Region currentRegion;
    
    public IndoorNavigation(JSerialPort serialPort, Room room) throws Exception {
        super(serialPort);
        currentRoom = room;
    }
    
    public void move(String end) throws Exception{
        ArrayList path = new ArrayList();
        getBestRegion();
        NavPoint start = currentRegion.getPointByName(NavPoint.START_POINT);
        NavPoint startCenter = currentRegion.getPointByName(NavPoint.CENTER_POINT);
        // start vector will be in virtual points 100x100
        DistanceVector startVector = getDistanceVector(start,startCenter);
        // convert from 100x100 to scaled version
        startVector.magintude = currentRegion.getScaledMagnitude(startVector.magintude);
        path.add(startVector);
        // middle vectors
        ArrayList regions  = currentRoom.getRegions();
        Region endRegion = null;
        NavPoint endPoint = null;
        for (int r=0;r<regions.size();r++) {
            endRegion = (Region)regions.get(r);
            if (endRegion.getPointByName(end) != null){
                endPoint = endRegion.getPointByName(end);
                break;
            }
        }
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.setVertices(regions);
        dijkstra.setEdges(currentRoom.getEdges());
        path.addAll(dijkstra.getShortestPath(currentRegion,endRegion));
        // end vector
        NavPoint endCenterPoint = currentRegion.getPointByName(NavPoint.CENTER_POINT);
        DistanceVector endVector = getDistanceVector(endCenterPoint,endPoint);
        endVector.magintude = endRegion.getScaledMagnitude(endVector.magintude);
        path.add(endVector);
        DistanceVector[] path2 = (DistanceVector[]) path.toArray();
        // conversion will be made to seconds from Navigation
        move(path2);
    }
    
    
    private void getBestRegion() throws Exception{
        // get 4 coordinate measures
        int[] nesw = getFourCoordinates();
        // get regions
        ArrayList regions  = currentRoom.getRegions();
        Region bestRegion = null;
        int maxVote=0;
        // iterate through all regions
        for (int r=0;r<regions.size();r++) {
            Region tmpRegion = (Region)regions.get(r);
            int longDist = tmpRegion.getSize()*2;
            int[] rChar = tmpRegion.getCharacteristic();
            int vote = 0;
            // vote on if measurements match readings
            for (int v=0;v<4;v++) {
                if (rChar[v] == 0 && nesw[v] > longDist) {
                    vote++;
                }
                if (rChar[v] == 1 && nesw[v] < longDist) {
                    vote++;
                }
            }
            if (vote > maxVote) {
                bestRegion = tmpRegion;
            }            
        }
        int [] bestChar = bestRegion.getCharacteristic();
        int x=0,y=0;
        if (bestChar[2] == 1) {
            y=nesw[2];
        }
        if (bestChar[0] == 1 && bestChar[2] == 0) {
            y= 100 - nesw[0];
        }
        if (bestChar[3] == 1) {
            y=nesw[3];
        }
        if (bestChar[1] == 1 && bestChar[3] == 0) {
            y= 100 - nesw[1];
        }
        bestRegion.addWayPoint(new NavPoint(NavPoint.START_POINT,bestRegion.getScaledPoint(x,y)));
        currentRegion = bestRegion;
    }
    

    public static void main(String[] args) {
        
        Room basement = Room.getBasement();
        try {
            WebSerialClient sPort = new WebSerialClient("10.10.10.99", "8080", "1");
            IndoorNavigation nav = new IndoorNavigation(sPort,basement);
            nav.move("fridge");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        
    }
}
