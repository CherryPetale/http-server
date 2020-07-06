package net.kuroppi.Impl;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import net.kuroppi.impl.ContentTypeResolverImpl;

public class ContentTypeImplTest {

    @Test
    public void test() throws IOException {
        Assert.assertEquals("image/png", ContentTypeResolverImpl.getContentType("/tmp/test.png"));
        Assert.assertEquals("image/png", ContentTypeResolverImpl.getContentType("/tmp/test.PNG"));
        Assert.assertEquals("image/png", ContentTypeResolverImpl.getContentType("test.PNG"));
        
        Assert.assertEquals("text/html", ContentTypeResolverImpl.getContentType("/tmp/test.htm"));
        Assert.assertEquals("text/html", ContentTypeResolverImpl.getContentType("/test.htm"));
        Assert.assertEquals("text/html", ContentTypeResolverImpl.getContentType("/test.html"));

        try{
            ContentTypeResolverImpl.getContentType("/test.pngpng");
            Assert.fail();
        }catch(IOException e){
        }


        try{
            ContentTypeResolverImpl.getContentType("/test");
            Assert.fail();
        }catch(IOException e){
        }


        try{
            ContentTypeResolverImpl.getContentType("");
            Assert.fail();
        }catch(IOException e){
        }

        Assert.assertEquals("application/octet-stream", ContentTypeResolverImpl.getContentType("/tmp/test.aaa"));
    }
}