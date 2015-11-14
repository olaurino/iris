package cfa.vo.iris.utils;

import org.junit.Assert;
import org.junit.Test;

public class PositionTest {

    @Test
    public void testGetDec() throws Exception {
        Position pos = new Position(111.1, 222.2);
        Assert.assertEquals(222.2, pos.getDec(), 1e-20);
    }

    @Test
    public void testGetRa() throws Exception {
        Position pos = new Position(3.0, 4.0);
        Assert.assertEquals(3.0, pos.getRa(), 1e-20);
    }
}