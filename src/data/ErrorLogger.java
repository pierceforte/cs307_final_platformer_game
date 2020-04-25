package data;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;

public final class ErrorLogger {

    private static final String fileLoc = "resources/data/ErrorLog.txt";

    private ErrorLogger() {}
    public static void clear() {
        try {
            File temp = new File(fileLoc);
            if (temp.exists()) {
                RandomAccessFile raf = new RandomAccessFile(temp, "rw");
                raf.setLength(0);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void log(Exception e) {
        try {
            String stackTrace = e.toString() + "\n";
            for (StackTraceElement element : e.getStackTrace()) {
                stackTrace = stackTrace + "  " + element.toString() + "\n";
            }
            System.out.println(stackTrace);
            PrintWriter out = new PrintWriter(fileLoc);
            out.print(stackTrace);
            out.close();
        } catch (IOException p) {
            p.printStackTrace();
        }
    }
}
