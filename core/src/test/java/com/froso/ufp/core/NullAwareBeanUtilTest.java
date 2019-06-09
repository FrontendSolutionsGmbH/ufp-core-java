package com.froso.ufp.core;

import com.froso.ufp.core.domain.documents.simple.plain.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.client.model.*;
import org.junit.*;

import java.lang.reflect.*;

/**
 * Created with IntelliJ IDEA.
 * Christian Kleinhuis (ck@froso.de)leinhuix
 * Date: 30.11.13
 * Time: 00:26
 * To change this template use File | Settings | File Templates.
 */
public class NullAwareBeanUtilTest {

    /**
     * Test if no null values are copied from bean util.
     *
     * @throws IllegalAccessException    the illegal access exception
     * @throws InvocationTargetException the invocation target exception
     */
    @Test
    public void TestIfNoNullValuesAreCopiedFromBeanUtil() throws IllegalAccessException, InvocationTargetException {
        // Take arbitrary class
        Client client = new Client();
        Client clientWithNullValues = new Client();
        DesignSettings designSettings = new DesignSettings();

        /*
        clientWithNullValues.setDesignSettings(null);
        client.setDesignSettings(designSettings);
         */
        NullAwareBeanUtils nullAwareBeanUtils = new NullAwareBeanUtils();
        // Copy Properties from null object to object with setted designsettings
        nullAwareBeanUtils.copyProperties(client, clientWithNullValues);
        // Now check if the designsettings of client that had designsettings before are still intact!
        Assert.assertNotNull(client.getName());
    }
}
