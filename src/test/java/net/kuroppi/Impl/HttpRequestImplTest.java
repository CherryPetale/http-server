package net.kuroppi.Impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.kuroppi.HttpHeader;
import net.kuroppi.HttpRequest.HttpMethod;
import net.kuroppi.impl.HttpHeaderImpl;
import net.kuroppi.impl.HttpRequestImpl;

public class HttpRequestImplTest {

    @Test
    public void test(){
        List<HttpHeader> headers = new ArrayList<>();
        headers.add(new HttpHeaderImpl("Hoge", "Fuga"));
        headers.add(new HttpHeaderImpl("Hoge2", "Fuga2"));
        HttpRequestImpl hri = new HttpRequestImpl(HttpMethod.GET, "/hoge.html", "HTTP/1.1", headers);

        assertEquals(HttpMethod.GET, hri.getMethod());
        assertEquals("/hoge.html",  hri.getPath());
        assertEquals("HTTP/1.1",       hri.getHttpVersion());
        assertEquals(headers,       hri.getHeaders());
        assertEquals("Fuga",        hri.getValue("Hoge"));
        assertEquals("Fuga2",       hri.getValue("Hoge2"));
        assertEquals("",            hri.getValue("Hoge3"));

    }
}