/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynacards;

import java.io.File;
import javax.script.*;

import jdk.nashorn.internal.runtime.ScriptFunction;

/**
 *
 * @author SoftVibe
 */
public class DynaCards {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        engine.put("host", new GameHost());
        engine.eval(new java.io.FileReader(".\\src\\dynacards\\Festival.js"));
        Invocable invocable = (Invocable)engine;
        Object info = invocable.invokeFunction("create");
        ICard card = invocable.getInterface(info, ICard.class);
        String[] specials = card.getSpecials();
        System.out.println("Special.cards: " + card.getCards());
        for(String special : specials)
        {
            invocable.invokeFunction(special);
        }
    }
}


