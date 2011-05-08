package com.trondvalen.upbot;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Board {
    
    private List<Bot> bots;
    private int maxPos;
    
    private static int pushes = 0;    
    private static List<Orientation> solution = new ArrayList<Orientation>();
    private int slideShowStep = 0;
    
    public Board(List<Bot> bots, int maxPos) {
        this.bots = bots;
        this.maxPos = maxPos;
    }
    
    public static void main(String[] args) {
        Board board = null;
        BotFrame frame = new BotFrame("UpBot goes up", null);
        frame.setVisible(true);
        pushes = 0;
        solution = new ArrayList<Orientation>();
        do {
            board = createBoardInGoalState();
            frame.setBoard(board);
            board.makeRandomGame();
        } while (pushes < 3);
        
        System.out.println(solution.size());
                
        for (Bot bot : board.getBots()) {
            System.out.println(String.format(
                    "%s: (%d, %d)", bot.getOrientation().name(), bot.getPosition().x , bot.getPosition().y));
        }        
    }
    
    public void makeRandomGame() {        
        int noMoves = 0;
        do {
            noMoves = doRandomPrevStep() ? 0 : ++noMoves;           
        } while (noMoves < 20);
    }

    private static Board createBoardInGoalState() {
        int max = 10;
        List<Bot> bots = new ArrayList<Bot>();
        for (int i = 0; i < 6; i++) {
            Bot bot = new Bot(Orientation.random(), random(max), random(max));
            bot.setGoal(bot.getPosition());
            bots.add(bot);
        }
        
        Board board = new Board(bots, max);
        return board;
    }
    
    private synchronized boolean doRandomPrevStep() {        
        Bot randomBot = bots.get((int)(Math.random() * 6));
        if (movePrev(randomBot)) {
            solution.add(0, randomBot.getOrientation());
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean movePrev(Bot bot) {
        if (canMovePrev(bot)) {
            Bot botBeingPushed = getBotInPos(next(bot));
            if (botBeingPushed != null) {
                botBeingPushed.setPosition(bot.getPosition());
                pushes++;
            }
            bot.setPosition(prev(bot));
            return true;
        }
        return false;
    }
    
    public boolean moveNext(Bot bot) {
        if (!canMoveNextSingle(bot)) {
            return false;
        }
        Bot botBlocking = getBotInPos(next(bot));
        bot.setPosition(next(bot));
        if (botBlocking != null) {
            botBlocking.setPosition(next(bot));
        }
        return true;
    }
    
    public static int random(int upTo) {
        return (int)(Math.random() * upTo);
    }
    
    public boolean canMovePrev(Bot theBot) {
        for (Bot bot : bots) {
            if (!bot.sameOrientationAs(theBot)) continue;
            if (!canMovePrevSingle(bot))
                return false;            
        }
        return true;
    }
    
    private boolean canMovePrevSingle(Bot bot) {
        Point prev = prev(bot);
        boolean canMoveIfAlone = isOnField(prev);
        if (!canMoveIfAlone)
            return false;
        
        Bot blockingBot = getBotInPos(prev);
        return blockingBot == null || (bot.sameOrientationAs(blockingBot) && canMovePrevSingle(blockingBot));
    }
    
    public boolean canMoveNextSingle(Bot bot) {
        Point next = next(bot);
        boolean canMoveIfAlone = isOnField(next);
        if (!canMoveIfAlone)
            return false;
                
        Bot blockingBot = getBotInPos(next);
        return blockingBot == null || (bot.sameOrientationAs(blockingBot) && canMovePrev(blockingBot));
    }

    private Bot getBotInPos(Point position) {
        for (Bot bot : bots) {
            if (bot.getPosition().equals(position))
                return bot;            
        }
        return null;
    }

    private boolean isOnField(Point prev) {
        return (prev.y != -1 && prev.y != maxPos+1) &&
                (prev.x != -1 && prev.x != maxPos+1);            
    }
    
    public Point next(Bot bot) {
        return next(bot.getPosition(), bot.getOrientation());
    }
    
    public Point prev(Bot bot) {
        return next(bot.getPosition(), bot.getOrientation().opposite());
    }

    private Point next(Point position, Orientation orientation) {
        switch (orientation) {
            case UP: return new Point(position.x, position.y + 1);
            case DOWN: return new Point(position.x, position.y - 1);
            case LEFT: return new Point(position.x-1, position.y);
            case RIGHT: return new Point(position.x+1, position.y);
        }
        return null;
    }
    
    public List<Bot> getBots() {
        return bots;
    }

    public void newStep() {
        Orientation orientation = solution.get(slideShowStep);
        
        System.out.println("Executing: " + orientation.name());
        
        for (Bot bot : bots) {
            if (bot.getOrientation().equals(orientation)) {
                moveNext(bot);
            }            
        }
        slideShowStep++;
    }

    public String getSymbol(Bot bot) {
        switch (bot.getOrientation()) {
            case UP: return "^";
            case DOWN: return "v";
            case LEFT: return "<";
            case RIGHT: return ">";
        }
        return null;
    }
}
