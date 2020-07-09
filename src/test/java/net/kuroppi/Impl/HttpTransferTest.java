package net.kuroppi.Impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.kuroppi.HttpHeader;
import net.kuroppi.impl.HttpHeaderImpl;
import net.kuroppi.impl.HttpRequestImpl;
import net.kuroppi.impl.HttpTransfer;
import net.kuroppi.impl.HttpTransfer.SendingMode;
import net.kuroppi.HttpRequest.HttpMethod;

public class HttpTransferTest {
    
    @Test
    public void test(){
        List<HttpHeader> headers = new ArrayList<>();
        headers.add(new HttpHeaderImpl("Hoge", "Fuga"));
        headers.add(new HttpHeaderImpl("Hoge2", "Fuga2"));
        HttpRequestImpl hri = new HttpRequestImpl(HttpMethod.GET, "/hoge.html", "HTTP/1.1", headers);
        HttpTransfer ht = new HttpTransfer(hri, 1000);

        assertEquals(SendingMode.CONTENT_LEN, ht.getTransMode());
    }
}