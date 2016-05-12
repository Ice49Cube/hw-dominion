/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardstack;

/**
 *
 * @author Michael
 */
public class CardStack<T> 
{
    T[] items;

    public CardStack()
    {
        this.items = (T[])new Object[]{};
    }
    
    public void push(T item)
    {
        
    }    
    
    /*public void push(T[] items)
    {
        for(T item : items)
            this.push(item);
    } */   
    
    public T pop()
    {
        return this.pop(1)[0];
    }

    public T[] pop(int count)
    {
        return null;
    }
    
    
    public void shift(T item)
    {
        
    }
    
    /*public void shift(T[] items)
    {
        for(T item : items)
            this.shift(item);
    }*/
    
    public T unshift()
    {
        return null;
    }
   
    
}
