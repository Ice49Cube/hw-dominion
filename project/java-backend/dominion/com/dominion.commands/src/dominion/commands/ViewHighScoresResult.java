package dominion.commands;

import dominion.routing.*;

public class ViewHighScoresResult extends ResultBase 
{
    private HighScore [] scores;
    
    public ViewHighScoresResult()
    {
	super("viewHighScores");
    }
    
    public HighScore[] getScores()
    {
        return this.scores;
    }
    
    public void setScores(HighScore[] value)
    {
        this.scores = value;
    }
}