package net.kuroppi;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try{
            new HttpdServer(8080).start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
