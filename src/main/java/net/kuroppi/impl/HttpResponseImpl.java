package net.kuroppi.impl;

import java.io.IOException;
import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
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
    public void OutputHeader() throws IOException {
        byte[] responseHeader = outputResponseHeader();
        out.write(responseHeader, 0, responseHeader.length);
    }
    
    private byte[] outputResponseHeader(){
        try{
            StringBuilder response = new StringBuilder();

            response.append("HTTP/1.0 ").append(statuscode).append(" ").append(getStatusText()).append("\r\n");
            
            for (HttpHeader header : headers) {
				response.append(header.getKey()).append(": ").append(header.getValue()).append("\r\n");
            }
            response.append("\r\n");

            return response.toString().getBytes();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private String getStatusText(){
        switch(statuscode){
            case 200:
                return "OK";
            case 304:
                return "Not Modified";   
            case 404:
                return "Not Found";
            case 500:
                return "Internal Server Error";
            default:
                return "I'm a teapot";
        }
    }
}