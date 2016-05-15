package dominion.commands;

import dominion.routing.*;

public class ViewHighScoresResult extends ResultBase 
{
    private HighScoreResult [] scores;
    
    public ViewHighScoresResult()
    {
	super("viewHighScores");
    }
    
    public HighScoreResult[] getScores()
    {
        return this.scores;
    }
    
    public void setScores(HighScoreResult[] value)
    {
        this.scores = value;
    }
}