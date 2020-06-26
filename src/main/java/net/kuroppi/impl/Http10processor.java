package net.kuroppi.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.kuroppi.HttpHeaderParser;
import net.kuroppi.HttpProcessor;
import net.kuroppi.HttpRequest;
import net.kuroppi.HttpResponse;

public class Http10processor implements HttpProcessor {

    @Override
    public void runHttp(InputStream in, OutputStream out) throws IOException {
        HttpHeaderParser parser = new HttpHeaderParserImpl();
        HttpRequest req = parser.parse(in);
        HttpResponse res = new HttpResponseImpl(out);

        String requestPath = "";
        String ContentType = "";

        try{
            requestPath = PathParserImpl.parse(req.getPath());
        }catch(IOException e){
            res.setStatusCode(404);
            res.OutputHeader();
            return;
        }

        if(requestPath.length() == 0){
            res.setStatusCode(404);
            res.OutputHeader();
            return;
        }

        try{
            ContentType = ContentTypeResolverImpl.getContentType(requestPath);
        }catch(IOException e){
            res.setStatusCode(404);
            res.OutputHeader();
            return;
        }

        if(ContentType.length() == 0){
            res.setStatusCode(404);
            res.OutputHeader();
            return;
        }


		res.addHeader(new HttpHeaderImpl("Server", "kaida-server"));
        res.addHeader(new HttpHeaderImpl("Content-Type", ContentType));

        res.OutputHeader();
        
        FileContent.OutputFile(out, requestPath);

    }
    
}