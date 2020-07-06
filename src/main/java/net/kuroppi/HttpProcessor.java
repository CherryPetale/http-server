package net.kuroppi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface HttpProcessor {
    void runHttp(InputStream in, OutputStream out) throws IOException;
}