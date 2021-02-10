package com.scottpreston.javarobot.chapter9;

import java.util.ArrayList;
import java.util.Arrays;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.Utils;
import com.scottpreston.javarobot.chapter2.WebSerialClient;
import com.scottpreston.javarobot.chapter7.DistanceVector;
import com.scottpreston.javarobot.chapter7.Localization;
import com.scottpreston.javarobot.chapter7.Region;
import com.scottpreston.javarobot.chapter7.Room;
import com.scottpreston.javarobot.chapter7.SonarReadings;

public class MazeNavigation extends Localization {
    
    private static final int DEFAULT_REGION_SIZE = (ROBOT_RADIUS * 2) + 12;
    
    // this will be list of all edges
    private ArrayList edges = new ArrayList();
    private ArrayList regions = new ArrayList();
    private Region currentRegion = null;
    private Room currentRoom = null;
    
    public MazeNavigation(JSerialPort serialPort) throws Exception {
        super(serialPort);
        // creates 1st region robot is in
        currentRegion = new Region(regions.size() + "", DEFAULT_REGION_SIZE);
        currentRegion.setCharacteristic(toCharacteristic(getFourCoordinates()));
        // add first region to list of regions
        regions.add(currentRegion);
        // add adjacent regions & edges
        addAdjacentRegions();
    }

    public void findExit() throws Exception {

        // number of inches until out of maze
        int target = 84;
        // current max size of all sonar readings
        int maxSize = 0;
        // loop until robot is out of maze
        while (maxSize < target) {
            // get four corners readings
            int[] nesw = getFourCoordinates();
            // init minimum vector found for all 4 coordinate vectors
            DistanceVector minVector = null;
            // set min weight to high number
            int minWeight = Integer.MAX_VALUE;
            // loop through edges
            for (int i = 0; i < edges.size(); i++) {
                // get temp vector
                DistanceVector tmpVector = (DistanceVector) edges.get(i);
                // only get vectors with first vertex as current region
                if (tmpVector.v1.name.equals(currentRegion + "")) {
                    // get smallest weighted
                    if (tmpVector.weight < minWeight) {
                        minWeight = tmpVector.weight;
                        minVector = tmpVector;
                    }
                }
            }
            // increase size so less likely next time to go through it.
            minVector.magintude = minVector.magintude + 1;
            // create a motion vector of region size
            DistanceVector currentVector = new DistanceVector(minVector.heading,
                    DEFAULT_REGION_SIZE);
            // move
            move(currentVector);
            // sets current name to next vertex
            for (int x = 0; x < regions.size(); x++) {
                Region tmp = (Region) regions.get(x);
                if (tmp.name.equalsIgnoreCase(minVector.v2.name)) {
                    currentRegion = tmp;
                    break;
                }
            }

            // update characteristic of this region since it was not set
            // when adjacent regions were added
            currentRegion.setCharacteristic(toCharacteristic(getFourCoordinates()));
            // sort all values
            Arrays.sort(nesw);
            // set largest sonar to maxSize
            maxSize = nesw[3];
            // now update adjacent regions to this one based on characteristic
            addAdjacentRegions();
        }
        currentRoom = new Room("maze");
        currentRoom.setEdges(edges);
        currentRoom.setRegions(regions);
    }
    // override so don't have to face just north.
    public int[] getFourCoordinates() throws Exception {
        getSonarServos().lookSide();
        Utils.pause(500);
        int heading = getNavStamp().getCompass();
        // straighten robot up
        int newHeading = 0;
        if (heading > 315 && heading < 45) {
            newHeading = 0;
        }
        if (heading > 45 && heading < 135) {
            newHeading = 90;
        }
        if (heading > 135 && heading < 225) {
            newHeading = 180;
        }
        if (heading > 225 && heading < 315) {
            newHeading = 270;
        }
        changeHeading(newHeading);
        Utils.pause(500);
        // take new readings
        SonarReadings sonarReadings = getNavStamp().getSonar();
        int front = sonarReadings.center;
        int left = sonarReadings.left + ROBOT_RADIUS;
        int right = sonarReadings.right - ROBOT_RADIUS;
        getSonarServos().lookAft();
        Utils.pause(500);
        sonarReadings = getNavStamp().getSonar();
        // average of two readings
        int back = (int) ((sonarReadings.left + sonarReadings.right) / 2.0);
        int[] nesw = null;
        // send array based on new Heading
        switch (newHeading) {
        	case 0:  
        	    nesw = new int[] {front,right,back,left};
        	    break;
        	case 90:  
        	    nesw = new int[] {left,front,right,back}; 
        	    break;
        	case 180:  
        	    nesw = new int[] {back,left,front,right}; 
        	    break;
        	case 270:  
        	    nesw = new int[] {right,back,left,front}; 
        	    break;        	    
        }
        return nesw;
    }
    
    private void addAdjacentRegions() {
        //      gets possible regions by looking at edges
        int[] c = currentRegion.getCharacteristic();
        // iterate through four coordinate axies
        for (int i = 0; i < 4; i++) {
            // if c=0, which means greater than default region size"
            if (c[i] == 0) {
                // create the region
                Region nextRegion = new Region(regions.size() + "",
                        DEFAULT_REGION_SIZE);
                // create the DistanceVector / edge
                DistanceVector vect = new DistanceVector(i * 90, 0);
                // set current region as source vertex
                vect.v1 = currentRegion;
                // set next region as end vertex
                vect.v2 = nextRegion;
                // checks to see if already a vertex
                // if false already a vertex so skip.
                if (isValidEdge(vect.heading)) {
                    edges.add(vect);
                    regions.add(nextRegion);
                }
            }
        }
    }

    private boolean isValidEdge(int heading) {

        // get all edges since it contains all regions
        for (int i = 0; i < edges.size(); i++) {
            // look through each edge
            DistanceVector edge = (DistanceVector) edges.get(i);
            // if edge already exist with the same heading then not valid
            if (edge.v1 == currentRegion && edge.heading == heading) {
                return false;
            }

            // adjust heading so that it can see edge from opposite end.
            int tempHeading = edge.heading + 180;
            if (tempHeading > 360) {
                tempHeading = tempHeading - 360;
            }
            // if current region is already the target vertex
            // at angle opposite, then there is already an edge for this direction
            /// with a vertex pointing to current region.
            if (edge.v2 == currentRegion && tempHeading == heading) {
                return false;
            }
        }
        // if did not return by now, heading must be valid from current region
        return true;
    }

    private int[] toCharacteristic(int[] nesw) {
        int[] characteristic = new int[4];
        for (int i = 0; i < 4; i++) {
            // 4 feet determines characteristic
            if (nesw[i] > DEFAULT_REGION_SIZE) {
                // greater
                characteristic[i] = 0;
            } else {
                // less than
                characteristic[i] = 1;
            }
        }
        return characteristic;
    }

    public static void main(String[] args) {

        WebSerialClient com = new WebSerialClient("10.10.10.99", "8080", "1");
        try {
            MazeNavigation nav = new MazeNavigation(com);
            nav.findExit();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("done");
    }

}
