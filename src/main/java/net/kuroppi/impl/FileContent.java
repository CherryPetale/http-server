package net.kuroppi.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileContent {

    public static void OutputFile(OutputStream out, String path) throws IOException {
        try (FileInputStream fis = new FileInputStream(path)){
            byte[] inputBuffer = new byte[1500];
            while(true){
                int n = fis.read(inputBuffer);
                if(n < 0){
                    break;
                }
                out.write(inputBuffer, 0, n);
            }
        }catch(Exception e){
            throw new IOException("Can't Output File");
        }
    }
}