package net.kuroppi.impl;

import java.io.File;
import java.io.IOException;

public class PathParserImpl {

    private static final String DocumentRoot = System.getProperty("user.dir") + "/public/";

    public static String parse(String path) throws IOException {
        
        String ReqPath = DocumentRoot + path;

        ReqPath = new File(ReqPath).getCanonicalPath();
        
        if(!new File(ReqPath).exists() || !ReqPath.startsWith(DocumentRoot)){
            throw new IOException("Not Found File");
        }

        return ReqPath;
    }
}