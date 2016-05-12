/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc_module;
import java.util.Random;
/**
 *
 * @author Maysam
 */
public class RandomGenerator 
{
    public int RandonGenerator()
    {
        Random number = new Random();
        int low = 1, high = 449015;
        int randomnumber = number.nextInt(high-low) + low;
        //System.out.println(randomnumber);
        return randomnumber;
        
    }
    
    
    
    
    
    
    
    
    
}
