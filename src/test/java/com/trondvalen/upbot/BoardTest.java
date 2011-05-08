package com.trondvalen.upbot;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
    
    private List<Bot> bots;
    private Board board;
    private Bot upBot3_6;
    private Bot downBot7_8;
    private Bot leftBot1_3;
    private Bot rightBot7_2;
    
    @Before
    public void setUp() {
        upBot3_6 = new Bot(Orientation.UP, 3, 6);
        downBot7_8 = new Bot(Orientation.DOWN, 7, 8);
        leftBot1_3 = new Bot(Orientation.LEFT, 1, 3);
        rightBot7_2 = new Bot(Orientation.RIGHT, 7, 2);
        bots = Arrays.asList(upBot3_6, downBot7_8, leftBot1_3, rightBot7_2);
        board = new Board(bots, 10);
    }

    @Test
    public void shouldMoveBotToNextPos() throws Exception {
        assertThat(board.next(bots.get(0))).isEqualTo(new Point(3, 7));
    }
    
    @Test
    public void cannotMovePrevAt0Border() throws Exception {
        upBot3_6.setPosition(new Point(3,0));
        assertThat(board.canMovePrev(upBot3_6)).isFalse();
    }
    
    @Test
    public void cannotMovePrevAtMaxBorder() throws Exception {
        downBot7_8.setPosition(new Point(3,10));
        assertThat(board.canMovePrev(downBot7_8)).isFalse();
    }
    
    @Test
    public void canMovePrevAwayFromBorders() throws Exception {
        upBot3_6.setPosition(new Point(3,1));
        assertThat(board.canMovePrev(upBot3_6)).isTrue();
    }
    
    @Test
    public void cannotMovePrevIfBotBlocking() {
        upBot3_6.setPosition(new Point(2,5));
        downBot7_8.setPosition(new Point(2,4));
        assertThat(board.canMovePrev(upBot3_6)).isFalse();
    }
    
    @Test
    public void testPrev() throws Exception {
        assertThat(board.prev(upBot3_6)).isEqualTo(new Point(3,5));
        assertThat(board.prev(downBot7_8)).isEqualTo(new Point(7,9));
        assertThat(board.prev(leftBot1_3)).isEqualTo(new Point(2,3));
        assertThat(board.prev(rightBot7_2)).isEqualTo(new Point(6,2));
    }
    
    @Test
    public void testMoveNext() throws Exception {
        leftBot1_3.setPosition(new Point(3,7));
        
        board.moveNext(upBot3_6);
        assertThat(upBot3_6.getPosition()).isEqualTo(new Point(3,7));
        assertThat(leftBot1_3.getPosition()).isEqualTo(new Point(3,8));
    }
}
