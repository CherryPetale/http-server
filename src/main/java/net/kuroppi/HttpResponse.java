package net.kuroppi;

import java.io.IOException;
import java.io.OutputStream;

public interface HttpResponse {
    void setStatusCode(int statuscode);

    void addHeader(HttpHeader header);

    OutputStream OutputStream() throws IOException;
}