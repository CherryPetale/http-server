package net.kuroppi.Impl;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import net.kuroppi.impl.PathParserImpl;

public class PathParseTest {

    @Test
    public void test() throws IOException {
        try{
            PathParserImpl.parse("/unkown");
            Assert.fail();
        }catch(IOException e){
        }
    }
}