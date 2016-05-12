package dominion.model;

import java.util.*;

class Util 
{
    public static ArrayList<Integer> getRandomRange(int size)
    {
        return getRandomRange(size, 0, size);
    }

    public static ArrayList<Integer> getRandomRange(int size, int max)
    {
        return getRandomRange(size, 0, max);
    }

    public static ArrayList<Integer> getRandomRange(int size, int min, int max)
    {
        Random random = new Random(new Date().getTime());
        ArrayList<Integer> numbers = new ArrayList();
        if(max - min < size)
            throw new IllegalArgumentException();
        while (numbers.size() < size) 
        {
            int value = random.nextInt(max - min) + min;
            if (!numbers.contains(value)) 
                numbers.add(value);
        }
        return numbers;
    }    
}
