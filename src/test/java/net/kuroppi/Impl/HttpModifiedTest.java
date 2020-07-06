package net.kuroppi.Impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import net.kuroppi.impl.HttpModified;

public class HttpModifiedTest {
    @Test
    public void test(){
        assertEquals("Fri, 03 Jul 2020 01:59:13 GMT",HttpModified.milliTimeToDateString(HttpModified.DateStringToMilliTime("Fri, 03 Jul 2020 01:59:13 GMT")));
        assertNotEquals(HttpModified.DateStringToMilliTime("Fri, 03 Jul 2020 01:59:14 GMT"), HttpModified.DateStringToMilliTime("Fri, 03 Jul 2020 01:59:13 GMT"));
    }
}