package dominion.frontend;

import java.io.*;

/**
 * Java System.console() has bugs in NetBeans, so created a small static class
 * with the basic functions for the console.
 */
public class Console {

    private static final Console instance = new Console();
    private final BufferedReader bufferedReader;

    private Console() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public static String readLine() throws IOException {
        return instance.bufferedReader.readLine();
    }

    public static String readLine(String message) throws IOException {
        System.out.print(message);
        return instance.bufferedReader.readLine();
    }

    /*public static String input(String message) throws IOException
    {
        return readLine(message);
    }*/
    public static void println(String message) {
        System.out.println(message);
    }

    public static void print(String message) {
        System.out.print(message);
    }

    public static BufferedReader getReader() {
        return instance.bufferedReader;
    }
}
