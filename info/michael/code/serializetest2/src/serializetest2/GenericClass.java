/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serializetest2;

import java.lang.reflect.ParameterizedType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael
 */
public class GenericClass<T> 
{
    private Class<T> type;

    //@SuppressWarnings("unchecked")
    public GenericClass(Class<T> clazz) 
    {   
        this.type = clazz;
    }    
    
    public Class<T> getGenericType()
    {
        return this.type;
    }
}