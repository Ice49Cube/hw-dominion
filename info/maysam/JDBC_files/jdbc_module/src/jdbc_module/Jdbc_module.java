
package jdbc_module;

/**
 *
 * @author Maysam
 */
public class Jdbc_module 
{
    
    public static void main(String[] args) 
    {
        RandomGenerator randomNbr = new RandomGenerator();
        databaseLink dblink = new databaseLink();
        dblink.databankLink("wordnr, word, disabled", "words", randomNbr.RandonGenerator());
        //dblink.databankLink("word", "words", 19); not working
    }//end main
}//end jdbc_module

