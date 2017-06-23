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
        String str_res = "Результат: ";
        for(int i = 0; i < n; i++)
        {
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
    
    public void run() throws IOException 
    {
        readData();
        for (int v = 0; v < n; ++v) 
        {
            if (!used[v]) 
            {
                dfs(v);
            }
        }
    }

    public void run(File file)
    {
        readData(file);
        for (int v = 0; v < n; ++v) 
        {
            if (!used[v]) 
            {
                dfs(v);
            }
        }
    }

    /**
     *
     * @throws IOException
     */
    public void readData() throws IOException 
    {
        System.out.print("Enter number of vertices and edges: ");
        Scanner cin = new Scanner(System.in);
        cout = new PrintWriter(System.out);

        n = cin.nextInt(); 
        m = cin.nextInt(); 
        System.out.print("Enter vertices:\n");
        adj = new ArrayList<ArrayList<Integer>>(n);
        used = new boolean[n];

        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < m; i++) {
            int v = cin.nextInt(); //vertex 1
            int w = cin.nextInt(); //vertex 2
            v--;
            w--;
            adj.get(v).add(w);
           // adj.get(w).add(v);
        }
    }

    public void readData(File file)
    {
        listDepht = new ArrayList<>();
        listEdges = new ArrayList<>();
        try {
            BufferedReader fin = new BufferedReader(new FileReader(file));
            n = Integer.parseInt(fin.readLine());
            m = Integer.parseInt(fin.readLine());

            adj = new ArrayList<ArrayList<Integer>>(n);
            used = new boolean[n];

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
            str_data = (i+1)+"-ая вершина соединена с:  ";
            for (int j = 0; j < adj.get(i).size(); j++)
            {
                str_data = str_data + (adj.get(i).get(j)+1) + " ";           
            }
        return (str_data);
    }   
}
