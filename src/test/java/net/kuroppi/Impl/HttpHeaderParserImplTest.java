package net.kuroppi.Impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.kuroppi.HttpHeader;
import net.kuroppi.HttpRequest;
import net.kuroppi.impl.HttpHeaderParserImpl;

public class HttpHeaderParserImplTest {
    
    private static final String CR = "\r";
    private static final String LF = "\n";
    private static final String CRLF = "\r\n";

    @Test
    public void test() throws IOException{
        HttpHeaderParserImpl parser = new HttpHeaderParserImpl();
        String http = makeTestRequest(CRLF);
        
        ByteArrayInputStream in = new ByteArrayInputStream(http.getBytes("ISO-8859-1"));
        HttpRequest request = parser.parse(in);
        
        Assert.assertEquals(HttpRequest.HttpMethod.GET, request.getMethod());
        Assert.assertEquals("/index.html", request.getPath());
        
        List<HttpHeader> headers = request.getHeaders();
        Assert.assertEquals(3, headers.size());
        
        HttpHeader hostHeader = headers.get(0);
		Assert.assertEquals("Host", hostHeader.getKey());
		Assert.assertEquals("www.dreamarts.co.jp", hostHeader.getValue());

		HttpHeader userAgentHeader = headers.get(1);
		Assert.assertEquals("User-Agent", userAgentHeader.getKey());
		Assert.assertEquals("Mozilla/5.0", userAgentHeader.getValue());

		HttpHeader connectionHeader = headers.get(2);
		Assert.assertEquals("Connection", connectionHeader.getKey());
		Assert.assertEquals("keep-alive", connectionHeader.getValue());
    }

    private String makeTestRequest(String lf) {
		return new StringBuilder().append("GET /index.html HTTP/1.1" + lf).append("Host: www.dreamarts.co.jp" + lf)
				.append("User-Agent: Mozilla/5.0" + lf).append("Connection: keep-alive" + lf).append(lf).toString();
	}
}