package com.dominion.test.serialize.card;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ThroneRoom implements ICard
{
    private final ObjectMapper mapper;
    private ThroneRoomState state;
    private String name;
    private String response;
    
    public ThroneRoom() 
    {
        this.mapper = new ObjectMapper();
        this.name = "throne_room";
    }
    
    public String getName()
    {
        return this.name;
    }
    
    @Override
    public String getState() throws Exception
    {
        return this.mapper.writeValueAsString(this.state);
    }

    @Override
    public void setState(String state) throws Exception
    {
        if ( state != null)
            this.state = this.mapper.readValue(state, ThroneRoomState.class);
    }
    
    @Override
    public boolean play(ICard card)  throws Exception
    {
        if (card != null)
        {
            switch(this.state.count) {
            case 2: 
                playOtherCardFirstTime(card);
                return false;
            case 1:
                playOtherCardSecondTime(card);
                return true;
            default: 
                throw new IllegalStateException();
            }
        }
        else if(this.state != null)
            throw new IllegalStateException();
        this.state = new ThroneRoomState();
        this.state.count = 2;
        return this.state.count == 0;
    }
    
    
    private void playOtherCardFirstTime(ICard card) throws Exception
    {
        card.setState(this.state.state);
        if(card.play(null)) {
            this.state.count -= 1;
            this.state.card = card.getName();
            this.state.state = null;
        } else {
            this.state.state = card.getState();
            this.response = card.getResponse();
        }
    }
    
    private void playOtherCardSecondTime(ICard card)
    {
        if(card.getName().equals(this.state.card)) {
            this.state.count -= 1;
        } else throw new IllegalStateException();
    }
    
    @Override
    public String getResponse()
    {
        switch(this.state.count) {
        case 2:
            return "Play a card twice...";
        case 1:
            return "Play the card '" + this.state.card + "' once more...";
        default:
            throw new IllegalStateException();
        }
    }

}
