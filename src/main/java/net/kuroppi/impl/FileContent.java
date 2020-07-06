package net.kuroppi.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileContent {

    public final static int ONE_SEND_SIZE = 1500;
    private final static String CRLF = "\r\n";

    public enum SendingMode{
        T_ENCODING,
        CONTENT_LEN
    }

    public static void OutputFile(OutputStream out, String path, SendingMode mode) throws IOException {
        try (FileInputStream fis = new FileInputStream(path)){
            byte[] inputBuffer = new byte[ONE_SEND_SIZE];
            while(true){
                int n = fis.read(inputBuffer);

                if(mode == SendingMode.T_ENCODING){
                    if(n <= 0){
                        out.write(("0"+CRLF+CRLF).getBytes());
                        break;
                    }
                    
                    out.write((Integer.toHexString(n) + CRLF).getBytes());
                    out.write(inputBuffer, 0, n);
                    out.write(CRLF.getBytes());
                    out.flush();
                }else if(mode == SendingMode.CONTENT_LEN){
                    if(n < 0){
                        break;
                    }
                    out.write(inputBuffer, 0, n);
                }
            }
        }catch(Exception e){
            throw new IOException(e.getMessage());
        }
    }

    public static long getFileLength(String path) throws IOException{
        try{
            File f = new File(path);
            return f.length();
        }catch(Exception e){
            throw new IOException("Can't Output File");
        }
    }

    public static long getFileLastUpdateTime(String path) throws IOException{
        try{
            File f = new File(path);
            return f.lastModified();
        }catch(Exception e){
            throw new IOException(e.getMessage());
        }
    }
}