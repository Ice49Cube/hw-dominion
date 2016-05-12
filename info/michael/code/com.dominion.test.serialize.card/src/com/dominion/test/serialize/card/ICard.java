package com.dominion.test.serialize.card;

public interface ICard 
{
    String getName();
    String getState() throws Exception;
    void setState(String value) throws Exception;
    boolean play(ICard card) throws Exception;    
    
    String getResponse();
}
