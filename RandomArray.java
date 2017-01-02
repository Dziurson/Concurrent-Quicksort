package quicksort;

import java.util.ArrayList;
import java.util.Collections;

public class RandomArray
{
    ArrayList<Integer> tablica;
    
    RandomArray(int size)
    {
        tablica = new ArrayList(size);
        for(int i = 0; i < size; i++)
        {
            tablica.add(i,i+1);
        }
        Collections.shuffle(tablica);
    }
    
    public void PrintArray()
    {
        for (Integer tablica1 : tablica)
        {
            System.out.println(tablica1);
        }
    }
    
    public ArrayList<Integer> ReturnArray()
    {
        return tablica;
    }
    
}
