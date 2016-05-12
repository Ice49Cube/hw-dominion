package com.dominion.test.serialize.card;

public class Market implements ICard
{

    private String name;
    
    public Market()
    {
        this.name = "market";
    }
    
    public String getName()
    {
        return this.name;
    }
    
    @Override
    public String getState() throws Exception
    {
        throw new IllegalStateException();
    }
    
    @Override
    public void setState(String value) throws Exception 
    {
        if(value != null)
            throw new IllegalStateException();                    
    }

    @Override
    public boolean play(ICard card) throws Exception 
    {
        return true;
    }
    
    @Override
    public String getResponse()
    {
        return "Extra buy, coin and card...";
    }
}
