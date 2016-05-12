/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dominion.test.serialize.card;

public class CardList 
{
    // Just for this test case, lol...
    
    public ICard getCardByName(String name)
    {
        switch(name) {
        case "throne_room":
            return new ThroneRoom();
        case "market":
            return new Market();
        default:
            throw new IllegalStateException();
        }
    }
}
