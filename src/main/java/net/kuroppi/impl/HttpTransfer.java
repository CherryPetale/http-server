package net.kuroppi.impl;

import net.kuroppi.HttpRequest;
import net.kuroppi.HttpResponse;

public class HttpTransfer {

    public enum SendingMode{
        T_ENCODING,
        CONTENT_LEN
    }

    private SendingMode TransferMode;

    public HttpTransfer(HttpRequest req, long ContentLength){
        if(req.getHttpVersion().toLowerCase().equals("http/1.1") &&
        ContentLength > FileContent.ONE_SEND_SIZE){
            this.TransferMode = SendingMode.T_ENCODING;
        }else{
            this.TransferMode = SendingMode.CONTENT_LEN;
        }
    }

    /**
     * 送信モードの取得
     * @return
     */
    public SendingMode getTransMode(){
        return this.TransferMode;
    }

    /**
     * ヘッダーに送信モードを追記
     * @param res
     * @param ContentLength
     */
    public void AddTransModeToHeader(HttpResponse res, long ContentLength){
        if( this.TransferMode.equals(SendingMode.CONTENT_LEN) ){
            res.addHeader(new HttpHeaderImpl("Content-Length", String.valueOf(ContentLength)));
        }else{
            res.addHeader(new HttpHeaderImpl("Transfer-Encoding", "chunked"));
        }
    }
}