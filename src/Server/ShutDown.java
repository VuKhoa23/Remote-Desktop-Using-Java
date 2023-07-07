package Server;
import java.io.*;
public class ShutDown {
    public static void Do() throws IOException{
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec("shutdown -s -t 0");
        System.exit(0);
    }

}
