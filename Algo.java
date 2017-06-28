/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackege;

/**
 *
 * @author Ольга
 */


import javafx.util.Pair;
import java.io.*;
import java.util.ArrayList;

public class Algo extends Visualisator {

    protected ArrayList<String> output;//массив для хранения промежуточных выводов
   
    
    public Algo() throws IOException {//конструктор
        this.output = new ArrayList<String>();
        
    }

    public void dfs(int v) {//обход в глуюину с записью порядка обхода и промежуточных шагов
        used[v] = true;//отмечаем, что вершина просмотрена
        output.add("Рассматриваем вершину " + (v + 1));
        listDepht.add(v + 1);//добавляем в список в вершин
        for (int i = 0; i < adj.get(v).size(); ++i) {//для всех смежных вершин
            int w = adj.get(v).get(i);
            if (!used[w]) {//если вершина не просмотрена
                listEdges.add(new Pair(v, w));
                dfs(w);//вызываем dfs
            }
            else{
             listEdges_1.add(new Pair(v, w));
                            
            }
        }
        result.add(v + 1);//запись результатов
        output.add("Вершина " + (v+1) + " обработана");//запись промежуточных шагов
        
    }

    public String printresult() {//функция вывода результатов
        int k = 0;
        String str_res = "Результат (порядок использованных вершин): ";
        for (int i = 0; i < n; i++) {
            if (i == k * 22) {
                str_res += "\n";
                k++;
            }
            if (i == (n - 1)) {
                str_res = str_res + result.get(i);
            } else {
                str_res = str_res + result.get(i) + "->";
            }
        }
        return str_res;
    }

    public void run() {//запуск обхода
        for (int v = 0; v < n; ++v) {
            if (!used[v]) {
                dfs(v);
            }
        }
    }

    public boolean search_for(int v, int w) {//функция проверки добавления ребер
        if (w < n && v < n) {
            for (int i = 0; i < adj.get(v).size(); i++) {
                if (adj.get(v).get(i) == w) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public void changeDataEdge(int v, int w) {//добавление ребер
        adj.get(v).add(w);
    }

    public void changeData_adj() {//добавление вершин
        adj.add(new ArrayList<Integer>());
    }

    public void readData(File file) {//считывание с файла
        
        listDepht = new ArrayList<>();
        listEdges = new ArrayList<>();
        try {
            BufferedReader fin = new BufferedReader(new FileReader(file));
            n = Integer.parseInt(fin.readLine());
            if (n > 50) {
                n = 50;
            }
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
                if(search_for(v,w)){
                adj.get(v).add(w);  
                }             
            }
        } catch (IOException ex) {
        }
    }

    public int getN() {//возвращает количество вершин
        return this.listDepht.size();
    }

    public String print_start_data(int i) {//вывод начальных данных(матрицы смежности)
        String str_data = "";
        if (adj.get(i).size() != 0) {
            str_data = (i + 1) + "-ая вершина соединена с:  ";
            for (int j = 0; j < adj.get(i).size(); j++) {
                str_data = str_data + (adj.get(i).get(j) + 1) + " ";
            }
        } else {
            str_data = (i + 1) + "-ая вершина не соединена ни с одной вершиной:  ";
        }
        return (str_data);
    }
}