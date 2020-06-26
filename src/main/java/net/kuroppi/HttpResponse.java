package net.kuroppi;

import java.io.IOException;

public interface HttpResponse {
    void setStatusCode(int statuscode);

    void addHeader(HttpHeader header);

    public void OutputHeader() throws IOException ;
}