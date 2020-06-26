package net.kuroppi;

import java.util.List;

public interface HttpRequest {
    enum HttpMethod{
        GET, HEAD, POST, OPTIONS, PUT, DELETE, TRACE, PATCH, LINK, UNLINK
    }

    HttpMethod getMethod();
    String getPath();
    List<HttpHeader> getHeaders();
}
