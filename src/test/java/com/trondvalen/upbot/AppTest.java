package com.trondvalen.upbot;

import static com.trondvalen.upbot.Orientation.UP;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testNewBot () throws Exception {
        Bot bot = new Bot(UP, 4, 6);
        assertThat(bot.getOrientation()).isEqualTo(UP);
        assertThat(bot.getPosition().getX()).isEqualTo(4);
        assertThat(bot.getPosition().getY()).isEqualTo(6);
    }
}
