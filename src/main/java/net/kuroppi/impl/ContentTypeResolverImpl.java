package net.kuroppi.impl;

import java.io.IOException;

public class ContentTypeResolverImpl {
    /**
     * ファイル名からコンテンツタイプを返す
     * 
     * @return
     * @throws IOException
     */
    public static String getContentType(String fileName) throws IOException {
        
        if(fileName.length() == 0){
            throw new IOException("File Name is empty");
        }

        int extPos = fileName.lastIndexOf(".");
        if(extPos < 0){
            throw new IOException("Not Supported Extension");
        }

        String extension = fileName.substring(extPos);
        if(extension.length() < 3 || extension.length() > 5){
            throw new IOException("Uncorrect Extension Length");
        }

        switch(extension.toLowerCase()){
            case ".htm":
            case ".html":
                return "text/html";
            case ".css":
                return "text/css";
            case ".js":
                return "text/javascript";
            case ".jpeg":
            case ".jpg":
                return "image/jpeg";
            case ".png":
                return "image/png";
            case ".gif":
                return "image/gif";
            case ".txt":
                return "text/plain";
            default:
                return "application/octet-stream";
        }
    }
}