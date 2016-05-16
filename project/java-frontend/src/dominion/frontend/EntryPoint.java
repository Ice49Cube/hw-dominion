package dominion.frontend;

public class EntryPoint {

    //http://stackoverflow.com/questions/8689780/how-to-clear-the-cache-in-netbeans
    /**
     * The start of the dominion CLI frontend.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        GameEngine engine = new GameEngine("http://localhost:8080/Server");
        engine.run();
    }
}
