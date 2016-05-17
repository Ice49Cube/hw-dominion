package dominion.frontend;

import java.io.*;

/**
 * Java System.console() has bugs in NetBeans, so created a small static class
 * with the basic functions for the console. (Could've used Scanner...)
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

    public static BufferedReader getReader() {
        return instance.bufferedReader;
    }
}
