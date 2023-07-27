import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss ");
    public static boolean debugMode = true; // DebugMode activates the debug msg's for console output

    public static void v(String tag, String msg){
        printText(addTime() + addTag(tag) + msg);
    }

    public static void i(String tag, String msg){
        printText(addTime() + useYellow() + addTag(tag) + useNoColor() + msg);
    }

    public static void d(String tag, String msg){
        if(debugMode) printText(addTime() + useBlue() + addTag(tag) + useNoColor() + msg);
    }

    public static void e(String tag, String msg){
        printText(addTime() + useRed() + addTag(tag) + msg);
    }

    public static void accept(String tag, String msg){
        printText(addTime() + useGreen() + addTag(tag) + msg);
    }

    private static String addTag(String tag){
        return "["+tag+"] ";
    }

    private static String addTime(){
        return formatter.format(new Date());
    }

    private static void printText(String msg){
        System.out.println(msg + useNoColor());
    }

    private static String useRed(){
        return "\033[31;1m";
    }

    private static String useGreen(){
        return "\033[32m";
    }

    private static String useYellow(){
        return "\033[33m";
    }

    private static String useBlue(){
        return "\033[34m";
    }

    private static String useNoColor(){
        return "\033[0m";
    }
}
