package net.kuroppi.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.kuroppi.HttpHeader;
import net.kuroppi.HttpHeaderParser;
import net.kuroppi.HttpRequest;
import net.kuroppi.impl.HttpHeaderImpl;

public class HttpHeaderParserImpl implements HttpHeaderParser {

    @Override
    public HttpRequest parse(InputStream in) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String requestLine = reader.readLine();
        if(requestLine == null || requestLine.length() == 0){
            throw new IOException("Bad Request Parameter");
        }

        String[] parts = requestLine.split(" ");
        if(parts.length != 3){
            throw new IOException("Bad Request Parameter");
        }
        boolean existMethod = false;
        for(HttpRequest.HttpMethod methodtype : HttpRequest.HttpMethod.values() ){
            if(methodtype.toString().equals(parts[0])){
                existMethod = true;
                break;
            }
        }
        if(!existMethod){
            throw new IOException("Not Supported Method");
        }
        HttpRequest.HttpMethod method = HttpRequest.HttpMethod.valueOf(parts[0]);
        String path = parts[1];

        System.out.println(requestLine);

        List<HttpHeader> headers = new ArrayList<>();
        while(true){

            String line = reader.readLine();
            if(line == null || line.length() == 0){
                break;
            }

            String[] splitsLine = line.split(":", 2);
            if(splitsLine.length != 2){
                throw new IOException("Bad Request Parameter");
            }

            headers.add(new HttpHeaderImpl(splitsLine[0].trim(), splitsLine[1].trim()));
        }

        return new HttpRequestImpl(method, path, headers);
    }
    
}