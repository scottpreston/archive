package com.scottpreston.javarobot.chapter8;

import com.scottpreston.javarobot.chapter7.DistanceVector;

public class MotionEpisode extends DistanceVector {

    public int motion_id = 0;
    
    public MotionEpisode() {
        super(0,0);
    }
    public MotionEpisode(int h, double m) {
        super(h, m);
    }
    public MotionEpisode(String h, String m) throws Exception {
        super(h, m);
    }
}
