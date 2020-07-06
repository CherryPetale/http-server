package net.kuroppi.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.kuroppi.HttpProcessor;
import net.kuroppi.HttpRequest;
import net.kuroppi.HttpResponse;

public class Http10processor implements HttpProcessor {

    @Override
    public void runHttp(InputStream in, OutputStream out) throws IOException {
        boolean KeepAlive = true;
        while(KeepAlive){
            HttpRequest req = new HttpHeaderParserImpl().parse(in);
            HttpResponse res = new HttpResponseImpl(out);

            String requestPath = "";
            String ContentType = "";
            long ContentLength = 0;

            // タイムアウトチェック
            if(req == null){
                return;
            }

            // Keep-Aliveチェック
            if(req.getValue("Connection").toLowerCase().equals("close")){
                KeepAlive = false;
            }

            // ファイルパスとサイズの解析と確認
            try{
                requestPath = PathParserImpl.parse(req.getPath());
                ContentLength = FileContent.getFileLength(requestPath);
                if(requestPath.length() == 0){
                    throw new IOException("Blank Path");
                }
            }catch(IOException e){
                res.setStatusCode(404);
                res.OutputHeader();
                return;
            }

            // コンテンツタイプの確認
            try{
                ContentType = ContentTypeResolverImpl.getContentType(requestPath);
                if(ContentType.length() == 0){
                    throw new IOException("Blank Content");
                }
            }catch(IOException e){
                res.setStatusCode(404);
                res.OutputHeader();
                return;
            }

            // 成功レスポンス
            res.addHeader(new HttpHeaderImpl("Server", "kaida-server"));
            res.addHeader(new HttpHeaderImpl("Content-Type", ContentType));
            res.addHeader(new HttpHeaderImpl("Connection", "Keep-Alive"));
            res.addHeader(new HttpHeaderImpl("Cache-Control", "max-age=0"));

            //Modifiedチェック
            if( HttpModified.IsModifiedMode(req) &&
                HttpModified.ComparisonTime(HttpModified.DateStringToMilliTime(req.getValue("If-Modified-Since")), FileContent.getFileLastUpdateTime(requestPath))){
                    res.setStatusCode(304);
                    res.OutputHeader();
                    return;
            }else{
                res.addHeader(new HttpHeaderImpl("Last-Modified", HttpModified.milliTimeToDateString(FileContent.getFileLastUpdateTime(requestPath))));
            }

            // ContentLengthとTransferEncodingの切り分け
            FileContent.SendingMode mode = 
                req.getHttpVersion().toLowerCase().equals("http/1.1") &&
                ContentLength > FileContent.ONE_SEND_SIZE ? 
                FileContent.SendingMode.T_ENCODING : FileContent.SendingMode.CONTENT_LEN;

            if( mode.equals(FileContent.SendingMode.CONTENT_LEN) ){
                res.addHeader(new HttpHeaderImpl("Content-Length", String.valueOf(ContentLength)));
            }else{
                res.addHeader(new HttpHeaderImpl("Transfer-Encoding", "chunked"));
            }

            res.OutputHeader();
            
            FileContent.OutputFile(out, requestPath, mode);

        }
    }
    
}