package com.trondvalen.upbot;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class BotPanel extends JPanel{

    private static final long serialVersionUID = -7222525190431964196L;
    
    private Board board;

    public BotPanel(final Board board) {
        this.board = board;
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.clearRect(-100, -100, getWidth()+100, getHeight()+100);
        if (board == null) return;
                
        for (Bot bot : board.getBots()) {
            g2d.drawString(board.getSymbol(bot), bot.getPosition().x*38, -bot.getPosition().y*38 + 400);
            g2d.drawString("G"+board.getSymbol(bot), bot.getGoal().x*38, -bot.getGoal().y*38 + 400);
        }
    }
    
    public void setBoard(Board board) {
        this.board = board;
    }
}
