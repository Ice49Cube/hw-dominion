/**
 * @author Michael
 */

/*
 * The libraries used are:
 *   - https://github.com/FasterXML/jackson-databind/wiki
 *   - https://github.com/FasterXML/jackson-annotations/wiki
 *   - https://github.com/FasterXML/jackson-core/wiki
 *
 * The basics: 
 *  - http://www.mkyong.com/java/jackson-2-convert-java-object-to-from-json/
 * Dive deep:
 *  - http://www.baeldung.com/jackson-annotations
 *  - https://github.com/FasterXML/jackson-docs/wiki/JacksonPolymorphicDeserialization
 *  - http://programmerbruce.blogspot.be/2011/05/deserialize-json-with-jackson-into.html
 */

package serializingtest;

public class SerializingTest 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        System.out.println("This is a prototype project. See dominion.routing for the implementation.");
        String json = "{\"method\":\"startNewGame\",\"players\":[\"player 1\", \"player 2\", \"player 3\"]}";
        Game game = new Game();
        Routing routing = new Routing(game);
        StartNewGameResult result = (StartNewGameResult)routing.invoke(json);
 
    }    
}

