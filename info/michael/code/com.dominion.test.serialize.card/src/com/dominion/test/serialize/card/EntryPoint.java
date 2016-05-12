package com.dominion.test.serialize.card;

public class EntryPoint 
{
    public static void main(String[] args) 
    {
        try
        {
            testOnce();
            testTwice();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Something went horribly wrong...");
        }
    }
    
    private static void testOnce() throws Exception
    {
        ICard first;
        ICard other;
        String state = null;
                
        first = new ThroneRoom();
        first.setState(state);
        if (!first.play(null)) {
            state = first.getState();
            System.out.println("The card state is: " + state);
            System.out.println("The card response is: " + first.getResponse());
        } else throw new IllegalStateException("Throne room should return false");
                
        first = new ThroneRoom();
        first.setState(state);
        other = new Market();
        if (!first.play(other)) {
            state = first.getState();
            System.out.println("The card state is: " + state);
            System.out.println("The card response is: " + first.getResponse());
        } else throw new IllegalStateException("Throne room should return false");
        
        first = new ThroneRoom();
        first.setState(state);
        other = new Market();
        if (first.play(other)) {
            System.out.println("The card is completed");
        }
    }

    private static void testTwice() throws Exception
    {
        ICard first;
        ICard second;
        ICard other;
        String state = null;
                
        first = new ThroneRoom();
        first.setState(state);
        if (!first.play(null)) {
            state = first.getState();
            System.out.println("The card state is: " + state);
            System.out.println("The card response is: " + first.getResponse());
        } else throw new IllegalStateException("Throne room should return false");
        
        first = new ThroneRoom();
        first.setState(state);
        second = new ThroneRoom();
        if (!first.play(second)) {
            state = first.getState();
            System.out.println("The card state is: " + state);
            System.out.println("The card response is: " + first.getResponse());
        }



        
        first = new ThroneRoom();
        first.setState(state);
        other = new Market();
        if (!first.play(other)) {
            state = first.getState();
            System.out.println("The card state is: " + state);
            System.out.println("The card response is: " + first.getResponse());
        } else throw new IllegalStateException("Throne room should return false");
        
        first = new ThroneRoom();
        first.setState(state);
        other = new Market();
        if (first.play(other)) {
            System.out.println("The card is completed");
        }
    }
    
}
