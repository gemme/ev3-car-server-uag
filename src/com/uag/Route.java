package com.uag;

import com.uag.Color;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Route {
    private int signLeftAngle = 1;
    private int signRightAngle = 1;
    public long timestamp = 0L;
    public Route(String path) {
        this.setPath(path);
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String chooseRouteByColor(int color){
        String turnTo = "rotate,0,0";
        if(color == Color.BLUE) {
            turnTo = this.turnToLeft();
        }
        if(color == Color.RED) {
            turnTo = this.turnToRight();
        }
        return turnTo;
    }

    private String rotateTo(int to){
        // rotate
        int leftAngle = to * this.signLeftAngle;
        int rightAngle = to * this.signRightAngle;
        String template = "rotate,%s,%s";
        return String.format(template, leftAngle, rightAngle);
    }

    private String turnToRight(){
        this.setPath("right");
        return this.rotateTo(180);
    }

    private String turnToLeft(){
        this.setPath("left");
        return this.rotateTo(180);
    }

    public void setPath(String path) {
        if(path.equals("right")) {
            this.signLeftAngle = 1;
            this.signRightAngle = -1;
        } else if(path.equals("left")){
            this.signLeftAngle = -1;
            this.signRightAngle = 1;
        }
    }
}
