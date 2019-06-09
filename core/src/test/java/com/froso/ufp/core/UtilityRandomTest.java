package com.froso.ufp.core;

import com.froso.ufp.core.util.*;
import org.junit.*;

public class UtilityRandomTest {
    private static final int RANDOM_RANGE = 100000;
    private static final int RANDOM_RANGE_FLOAT = 100000;

    @Test
    public void testRandomGenerator() {

        Assert.assertTrue(0 < RandGen.nextFloat());
        Assert.assertTrue((0 < RandGen.nextFloat(RANDOM_RANGE_FLOAT)) && (RANDOM_RANGE_FLOAT >= RandGen.nextFloat(RANDOM_RANGE_FLOAT)));
        Assert.assertTrue((0 < RandGen.nextInt(RANDOM_RANGE)) && (RANDOM_RANGE_FLOAT >= RandGen.nextInt(RANDOM_RANGE)));
        Assert.assertEquals(RandGen.getRandomUUID().length(), 36);
    }
}
