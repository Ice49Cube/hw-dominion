

package jdbc_module;

public class Words 
{
    private int wordnr;
    private String word;
    private boolean disabled;
    
    public void setWordList(int wordnr, String word, boolean disabled)
    {
        this.wordnr = wordnr;
        this.word = word;
        this.disabled = disabled;
    }
    
    public int getWordnr()
    {
        return wordnr;
    }
    
    public String getWord()
    {
        return word;
    }
    
    public boolean getDisabled()
    {
        return disabled;
    }
}
