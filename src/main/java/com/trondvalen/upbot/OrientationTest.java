package com.trondvalen.upbot;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class OrientationTest {
    
    @Test
    public void testEquals() throws Exception {
        assertThat(Orientation.UP).isEqualTo(Orientation.UP);
        assertThat(Orientation.UP).isNotEqualTo(Orientation.DOWN);
        assertThat(Orientation.LEFT).isNotEqualTo(Orientation.RIGHT);
        
        assertThat(Orientation.LEFT == Orientation.LEFT);
        assertThat(Orientation.LEFT != Orientation.RIGHT);
    }
}
