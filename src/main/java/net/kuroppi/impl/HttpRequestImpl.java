package net.kuroppi.impl;

import java.util.List;

import net.kuroppi.HttpHeader;
import net.kuroppi.HttpRequest;

public class HttpRequestImpl implements HttpRequest {
    
    private final HttpMethod method;

    private final String path;

    private final String httpVersion;

    private final List<HttpHeader> headers;

    public HttpRequestImpl(HttpMethod method, String path, String httpVersion ,List<HttpHeader> headers){
        if(headers == null){
            throw new NullPointerException();
        }
        this.method = method;
        this.path = path;
        this.httpVersion = httpVersion;
        this.headers = headers;
    }


    @Override
    public HttpMethod getMethod() {
        return this.method;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public String getHttpVersion() {
        return this.httpVersion;
    }

    @Override
    public List<HttpHeader> getHeaders() {
        return this.headers;
    }

    @Override
    public String getValue(String key) {
        for(HttpHeader header : this.headers){
            if(header.getKey().equals(key)){
                return header.getValue();
            }
        }
        return "";
    }
    
}