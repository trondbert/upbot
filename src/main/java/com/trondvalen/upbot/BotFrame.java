package com.trondvalen.upbot;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class BotFrame extends JFrame {
    
    private Board board;
    private BotPanel botPanel;
    
    public BotFrame(String title, Board board) {
        setTitle(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(450, 450);
        
        this.board = board;
        botPanel = new BotPanel(board);
        
        getContentPane().add(botPanel);        
        addKeyListener(new BotListener());
    }
    
    class BotListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == ' ') {
                if (1!=2) {
                    synchronized (board) {
                        board.notify();
                    }
                } else {
                    board.newStep();
                }
                botPanel.repaint();
            }
        }        
    }

    public void setBoard(Board board) {
        this.board = board;
        botPanel.setBoard(board);
    }
}
