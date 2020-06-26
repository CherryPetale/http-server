package net.kuroppi;

import java.io.IOException;
import java.io.InputStream;

public interface HttpHeaderParser {

    /**
     * HTTPのリクエストをストリームから読み出しリクエストヘッダー部分のパースを行う
     * @param in
     * @return
     * @throws IOException
     */
    HttpRequest parse(InputStream in) throws IOException;
}