/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominion.frontend;


import java.util.*;
import java.io.*;
import java.net.*;

public class EntryPoint 
{    
    private static String[] getPlayers() throws Exception
    {        
        ArrayList<String> players = new ArrayList();
        String player;
        while (players.size() < 4) {
            System.out.print("Enter name for player " + (players.size() + 1));
            if(players.size() > 1)
                System.out.print(" or enter to start");
            System.out.print(": ");            
            player = Console.readLine().trim();
            if (player.equals(""))
            {
                if (players.size() > 1)
                    break;
            }
            else if(!players.contains(player))
                players.add(player);
        }
        return players.toArray(new String[players.size()]);
    }
    
    /**
     * The start of the dominion CLI frontend.
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        Routing routing = new Routing("http://localhost:8080/Server");
        Game game;
        String choice;
        while (true) {
            System.out.println("New Game: N - Continue Game: code - Quit: Q");
            System.out.print("Your choice: ");
            choice = Console.readLine().trim();
            switch(choice) {
            case "q": case "Q":
                return;
            case "n": case "N":
                game = Game.startGame(routing, getPlayers());
                break;
            default:
                if (!choice.equals("")) {
                    game = Game.startGame(routing, choice);
                } else {
                    game = null;
                }
            };            
            if(game != null) game.run();
        }
    }
    
}
