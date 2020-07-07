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
            HttpTransfer trans = new HttpTransfer(req, ContentLength);
            trans.AddTransModeToHeader(res, ContentLength);

            // ヘッダー送信
            res.OutputHeader();
            
            // コンテンツ送信
            FileContent.OutputFile(out, requestPath, trans.getTransMode());

        }
    }
}