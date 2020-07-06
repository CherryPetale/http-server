package net.kuroppi.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.kuroppi.HttpRequest;

public class HttpModified {

    /**
     * Modifiedとして処理すべきか
     * @param req
     * @return
     */
    public static boolean IsModifiedMode(HttpRequest req){
        String ModifiedSince = req.getValue("If-Modified-Since");
        return  (req.getMethod().name().equals("GET")  || 
                 req.getMethod().name().equals("HEAD"))&&
                 ModifiedSince.length() > 0 &&
                 DateStringToMilliTime(ModifiedSince) >= 0;
    }

    /**
     * ミリ秒からModified用タイムスタンプを作成する
     * @param milliTime
     * @return
     */
    public static String milliTimeToDateString(long milliTime){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH);
        try{
            return sdf.format(new Date(milliTime));
        }catch(Exception e){
            return "";
        }
    }

    /**
     * Modified文字列からミリ秒に変換
     * @param dateString
     * @return
     */
    public static long DateStringToMilliTime(String dateString){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH);
        try{
            Date format = sdf.parse(dateString);
            return format.getTime();
        }catch(Exception e){
            return -1L;
        }
    }

    /**
     * クライアントとサーバーの時間を比較し、304を行うべきかを返す
     * @param ClientModifiedTime
     * @param ServerModifiedTime
     * @return True: 304を行うべきである
     */
    public static boolean ComparisonTime(long ClientModifiedTime, long ServerModifiedTime){
        return ClientModifiedTime == ServerModifiedTime;
    }

}