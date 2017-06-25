package newpackege;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Algo extends Visualisator {

    public Algo() throws IOException {
    }

    public void dfs(int v) {
        used[v] = true; 
        listDepht.add(v + 1);
        for (int i = 0; i < adj.get(v).size(); ++i) {
            int w = adj.get(v).get(i);
            if (!used[w]) {
                listEdges.add(new Pair(v, w));
                dfs(w); 
            }
        }
        result.add(v+1); 
    }

    public String printresult()
    {
         int k=0;
        String str_res = "Результат: ";
        for(int i = 0; i < n; i++)
        {
            if(i==k*21)
            {
                str_res+="\n";
                k++;
            }
            if (i==(n-1))
            {
                str_res = str_res + result.get(i);
            }
            else{
            str_res = str_res + result.get(i) + "->" ;
            }
        }
        return str_res;
    }
    
    
    public void run()
    {
        //File file = new File ("z://", "data.txt");
       // readData(file);
        for (int v = 0; v < n; ++v) 
        {
            if (!used[v]) 
            {
                dfs(v);
            }
        }
    }

   public void changeDataEdge(int v, int w)
   {
       adj.get(v).add(w);
   }
   public void changeData_adj()
    {
          adj.add(new ArrayList<Integer>());
    }     
        
    public void readData()
    {
        File file = new File ("z://", "data.txt");
        listDepht = new ArrayList<>();
        listEdges = new ArrayList<>();
        try {
            BufferedReader fin = new BufferedReader(new FileReader(file));
            n = Integer.parseInt(fin.readLine());
            m = Integer.parseInt(fin.readLine());

            adj = new ArrayList<ArrayList<Integer>>(n);
            

            for (int i = 0; i < n; i++) {
                adj.add(new ArrayList<Integer>());
            }
            for (int i = 0; i < m; i++) {
                String[] s = fin.readLine().split(" ");
                int v = Integer.parseInt(s[0]);
                int w = Integer.parseInt(s[1]);
                v--;
                w--;
                adj.get(v).add(w);
               // adj.get(w).add(v);
            }
        } catch (IOException ex) 
        {
        }
    }
    
    public int getN()
    {
        return this.listDepht.size();
    }
    
    /**
     *
     * @param i
     * @return
     */
    public String print_start_data(int i)
    {
        String str_data = "";
        if(adj.get(i).size()!=0)
        {
            str_data = (i+1)+"-ая вершина соединена с:  ";
            for (int j = 0; j < adj.get(i).size(); j++)
            {
                str_data = str_data + (adj.get(i).get(j)+1) + " ";           
            }
        }
        else
        {
            str_data = (i+1)+"-ая вершина не соединена ни с одной вершиной:  ";
        }
        return (str_data);
    }   
}