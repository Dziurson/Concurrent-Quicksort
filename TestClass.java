package quicksort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit; 

public class TestClass
{
    public static final int LICZBA_ELEMENTOW = 1000000; //Ilość elementów do posortowania
    public static final int LICZBA_PROB = 10;           //Ilość prób do wykonania 
    
    public static void main(String[] args)
    {     
        System.out.println("Tworzenie tablicy zawierającej " + LICZBA_ELEMENTOW + " pomieszanych elementów...");
        
        /*Losowanie tablicy*/
        
        RandomArray r = new RandomArray(LICZBA_ELEMENTOW);
        ArrayList<Integer> tmp = r.ReturnArray();
        int[] tablica = new int[tmp.size()];
        int[] tablica2 = new int[tmp.size()];
        for (int i = 0; i < tmp.size(); i++)
        {
            tablica[i] = tmp.get(i);
            tablica2[i] = tmp.get(i);
        }
        System.out.println("Tablica utworzona.\n");
        
        /*Deklaracja używanych obiektów */
        
        long t_start;
        long t_stop;
        ForkJoinPool t1; 
        Quicksort qs;
        int[] tablica_t ;
        
        /*Pobranie liczby dostępnych procesorów logicznych*/
        
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("Liczba dostępnych procesorów logicznych: " + processors + ".");
        System.out.println("Wyniki uzyskane na podstawie " + LICZBA_PROB + " prób, sortując " + LICZBA_ELEMENTOW + " elementów.\n");
        
        /*Pętla testująca*/
        
        for (int i = 1; i <= processors; i++)
        {
            boolean check = true;
            long suma = 0;
            for (int k = 0; k < LICZBA_PROB; k++)
            {   
                /*Klonowanie tablicy do sortowania*/
                tablica_t = (int[])tablica.clone();
                t1 = null;
                t_start = System.currentTimeMillis();
                t1 = new ForkJoinPool(i);            
                qs = null;
                qs = new Quicksort(tablica_t,LICZBA_ELEMENTOW);
                /*Instrukcja invoke wybudza metodę fork()*/
                t1.invoke(qs);
                t1.shutdown();
                t_stop = System.currentTimeMillis();
                try
                {
                    t1.awaitTermination(1,TimeUnit.SECONDS);
                }
                catch(Exception e)
                {
                    System.err.println(e);
                }
                suma = t_stop - t_start + suma;  
                /*Pętla sprawdzająca czy wynikowa tablica jest posortowana*/
                for (int j = 0; j < tmp.size()-1; j++)
                {
                    if ((tablica_t[j+1] - tablica_t[j]) != 1) check = false & check;
                }                
            }
            if(i == 1) System.out.println("Wywołanie Quicksort zajęło: " + (suma/LICZBA_PROB) + "ms. - " + i + " wątek. Posortowana: " + check + ".");
            else if((i > 1) && (i < 5)) System.out.println("Wywołanie Quicksort zajęło: " + (suma/LICZBA_PROB) + "ms. - " + i + " wątki. Posortowana: " + check + ".");
            else System.out.println("Wywołanie Quicksort zajęło: " + (suma/LICZBA_PROB) + "ms. - " + i + " wątków. Posortowana: " + check + ".");
        }   
        /*Test ParallelSort*/
        long start = System.currentTimeMillis();
        Arrays.parallelSort(tablica2);
        long stop = System.currentTimeMillis();
        boolean check = true;
        System.out.print("\nWywołanie ParallelSort zajęło: " + (stop-start) + "ms. ");
        for (int j = 0; j < tmp.size()-1; j++)
        {
            if ((tablica2[j+1] - tablica2[j]) != 1) check = false;
        }
        System.out.println("Posortowana: " + check);
    }  
}