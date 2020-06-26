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

            res.addHeader(new HttpHeaderImpl("Content-Length", String.valueOf(ContentLength)));

            res.OutputHeader();
            
            FileContent.OutputFile(out, requestPath);

        }
    }
    
}