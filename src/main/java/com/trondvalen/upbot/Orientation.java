package com.trondvalen.upbot;


public enum Orientation {
    UP, DOWN, LEFT, RIGHT;
    
    public Orientation opposite() {
        switch (this) {
            case UP: return DOWN;
            case DOWN: return UP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
        }
        return null;        
    }
    
    public static Orientation random() {
        int random = (int)(Math.random() * 4);
        switch (random) {
            case 0: return UP;
            case 1: return DOWN;
            case 2: return RIGHT;
            case 3: return LEFT;
        }
        return null;
    }
}
