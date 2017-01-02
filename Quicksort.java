package quicksort;

import java.util.concurrent.RecursiveAction;

public class Quicksort extends RecursiveAction
{   
    int[] tablica; //Tablica, w miare wykonywania algorytmu zostaje sortowana
    int lewa;      //Granice przedziałów sortowania
    int prawa;     //--------------//--------------
    
    /*Konstuktor służący wywołaniu sortowania przez użytkownika*/
    public Quicksort(int[] tablica_t, int size)                                 
    {
        tablica = tablica_t;
        lewa = 0;
        prawa = size - 1;
    }
    
    /*Kontruktor używany podczas invoke (wewnątrz funkcji,
      używając fork i join lub invokeAll*/
    private Quicksort(int[] tablica_t, int l, int r)                            
    {
        tablica = tablica_t;
        lewa = l;
        prawa = r;
    }
    
    @Override 
    protected void compute()
    {
        /*Jeśli warunek jest niespełniony, oznacza 
        że aktualna część tablicy jest posortowana*/
        if (lewa < prawa)
        {   
            {
                int el_podzialu = Podziel(tablica,lewa,prawa);                
                //invokeAll(new Quicksort(tablica, lewa, el_podzialu - 1), new Quicksort(tablica, el_podzialu, prawa));
                
                /*Użycie fork() i join() pozwoliło uzyskać czasy wykonania szybsze o okolo 20% od invokeAll*/
                Quicksort t1 = new Quicksort(tablica, lewa, el_podzialu - 1);
                t1.fork();
                Quicksort t2 = new Quicksort(tablica, el_podzialu, prawa);
                t2.compute();
                t1.join();
            }            
        }
    }
    
    /*Podział na dwie podtablice zgodnie z algorytmem Hoare'a*/
    private int Podziel(int[] tab, int l, int r)
    {
        int tmp_l = l;
        int tmp_r = r;
        int tmp;
        int element_srodkowy = tab[(l+r)/2];
        while (tmp_l <= tmp_r)
        {
            while (tab[tmp_l] < element_srodkowy)
            {
                  tmp_l++;                  
            }
            while (tab[tmp_r] > element_srodkowy)
            {
                  tmp_r--;                  
            }
            if (tmp_l <= tmp_r) 
            {
                  tmp = tab[tmp_l];
                  tab[tmp_l] = tab[tmp_r];
                  tab[tmp_r] = tmp;
                  tmp_l++;
                  tmp_r--;
            }
        }     
        return tmp_l;
    }
    
    /*Proste wypisywanie stanu tablicy*/
    public void ShowArray()
    {
        for (int i = 0; i < prawa + 1; i++)
        {
            System.out.println(tablica[i]);
        }
    }    
}
