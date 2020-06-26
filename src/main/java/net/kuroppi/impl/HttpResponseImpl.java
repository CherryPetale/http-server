package net.kuroppi.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import net.kuroppi.HttpHeader;
import net.kuroppi.HttpResponse;

public class HttpResponseImpl implements HttpResponse {

    private int statuscode = 200;

    private final OutputStream out;

    private final List<HttpHeader> headers = new ArrayList<>();

    public HttpResponseImpl(OutputStream out){
        this.out = out;
    }

    @Override
    public void setStatusCode(int statuscode) {
        this.statuscode = statuscode;
    }

    @Override
    public void addHeader(HttpHeader header) {
        headers.add(header);
    }

    @Override
    public OutputStream OutputStream() throws IOException {
        return out;
    }
    
}