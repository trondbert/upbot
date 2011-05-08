package com.trondvalen.upbot;

import java.awt.Point;

/**
 * Hello world!
 *
 */
public class Bot 
{
    private final Orientation orientation;
    
    private Point position;
    
    private Point goal = new Point(0,0);

    public Bot(Orientation orientation, int x, int y) {
        this.orientation = orientation;
        position = new Point(x,y);
    }
    
    public Orientation getOrientation() {
        return orientation;
    }

    public Point getPosition() {
        return position;
    }
    
    public Point getGoal() {
        return goal;
    }
    
    public void setPosition(Point position) {
        this.position = position;
    }

    public void setGoal(Point goal) {
        this.goal = goal;
    }

    public boolean sameOrientationAs(Bot blockingBot) {
        return blockingBot != null && blockingBot.getOrientation().equals(orientation);
    }
}
