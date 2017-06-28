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
import javax.swing.*;
import com.mxgraph.util.mxConstants;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javafx.util.Pair;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

public class Visualisator extends JPanel {

    protected static int n;

    public int get_N() {
        return n;
    }

    ;
    public void inc_N() {
        n++;
    }

    ;
    public void inc_M() {
        m++;
    }
    ;
    protected boolean provv; // проверка на наличие ребра
    protected static int m;
    protected ArrayList<ArrayList<Integer>> adj; // список смежности
    protected boolean used[]; // массив использованных вершин
    protected ArrayList<Integer> listDepht; // список вершин в порядке прямого обхода
    protected ArrayList<Pair<Integer, Integer>> listEdges; // список рёбер
    protected ArrayList<Pair<Integer, Integer>> listEdges_1; // список неиспользованных рёбер 
    protected ArrayList<Object> points; // вершины графа
    protected mxIGraphModel model;
    protected ArrayList<Integer> result; // список вершин в порядке обратного обхода 
    public PrintWriter cout;

    public void setnull() {
        n = 0;
        m = 0;
    }

    @SuppressWarnings("unused")
    public Visualisator() throws IOException {
        this.setSize(700, 700);
        this.setPreferredSize(new Dimension(700, 700));
        // создание основных списков
        listDepht = new ArrayList<>();
        listEdges = new ArrayList<>();
        listEdges_1 = new ArrayList<>();
        result = new ArrayList<>(n);
        points = new ArrayList<>();
    }

    public boolean dfsVisual(int step, int stepp, int kol) { // функция визуализации пошагового прямого обхода графа
        mxGraph graph = new mxGraph();
        graph.setModel(model);
        model.beginUpdate();
        if (kol == 0) { // если первый шаг
            // покраска вершины
            graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "pink", new Object[]{points.get(listDepht.get(0) - 1)});
            // указание порядкового номера при обходе
            graph.getModel().setValue(points.get(listDepht.get(0) - 1), graph.getModel().getValue(points.get(listDepht.get(0) - 1)).toString() + "\n(1)");
        } else {

            if (step + 1 > listDepht.size()) { // если индекс требуемой вершины находится за пределами списка вершин 
                System.out.println("первый false");
                model.endUpdate();
                this.revalidate();
                return false;
            }
            if (step != 0) {
                // проверка на связность рядом стоящих верин при прямом обходе 
                if ((listDepht.size() == 1) || (!listEdges.contains(new Pair<>(listDepht.get(stepp - 1) - 1, listDepht.get(step) - 1)))) {
                    System.out.println("второй false");
                    model.endUpdate();
                    this.revalidate();
                    return false;
                }
            }
            for (int i = 0; i < listDepht.size(); i++) {
                provv = false;
                // поиск ребра для указанной вершины
                if (listEdges.contains(new Pair<>(listDepht.get(i) - 1, listDepht.get(step) - 1))) {
                    provv = false;
                    //покраска  пройденного ребра
                    graph.getModel().setStyle(graph.getEdgesBetween(points.get(listDepht.get(i) - 1), points.get(listDepht.get(step) - 1))[0], "strokeColor=#FF5C6E");
                    provv = true;
                    break;
                }
            }
            // покраска вершины
            graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "pink", new Object[]{points.get(listDepht.get(step) - 1)});
            // установка порядкового номера
            graph.getModel().setValue(points.get(listDepht.get(step) - 1), graph.getModel().getValue(points.get(listDepht.get(step) - 1)).toString() + "\n(" + (kol + 1) + ")");
        }
        System.out.println("первый true");
        model.endUpdate();
        this.revalidate();
        
        return true;
    }
    

    public int used(int step) { // функция поиска порядкового номера при обратном обходе
        int st = 0;
        for (int i = 0; i < result.size(); i++) {
            if (Objects.equals(listDepht.get(step), result.get(i))) {
                st = i;
            }
        }
        return st;
    }
     public boolean dfsVisual_1(int step, int prov, int kol) { // функция визуализации обратного пошагового обхода графа 
         mxGraph graph = new mxGraph();
         graph.setModel(model);
         model.beginUpdate();
         provv = false;
        if ((listDepht.size() == 1) && (step == -1)) { // если остался один элемен, то необходимо исползовать его( удалить)
            // покраска вершины в использованный цвет
             graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "violet", new Object[]{points.get(listDepht.get(prov) - 1)});
             // назначение номера в порядке обхода(использования)
             graph.getModel().setValue(points.get(listDepht.get(step) - 1), graph.getModel().getValue(points.get(listDepht.get(step) - 1)).toString() + ",(" + (used(step) + 1) + ")");
             listDepht.remove(prov); // удаление вершины из списка
             System.out.println("удаление_1 true");
             model.endUpdate();
             this.revalidate();
             return true;
        }
        for (int i = 0; i < listDepht.size(); i++) { // поиск ребра для вершины с индексом step
            if (listEdges.contains(new Pair<>(listDepht.get(i) - 1, listDepht.get(step) - 1))) {
                  // покраска вершины в использованный цвет
                 graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "violet", new Object[]{points.get(listDepht.get(step) - 1)});
                  // назначение номера в порядке обхода(использования)
                 graph.getModel().setValue(points.get(listDepht.get(step) - 1), graph.getModel().getValue(points.get(listDepht.get(step) - 1)).toString() + ",(" + (used(step) + 1) + ")");
                 // покраска ребра, как пройденного(использованного)
                 graph.getModel().setStyle(graph.getEdgesBetween(points.get(listDepht.get(i) - 1), points.get(listDepht.get(step) - 1))[0], "strokeColor=#00D6AC");
                 listDepht.remove(step);// удаление вершины из списка
                 provv = true;
                 break;
            }
        }
        if (provv == false) { // если ребра для указанной вершины нет, то перекрашиваем только вершину
             graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "violet", new Object[]{points.get(listDepht.get(step) - 1)});
             // назначение номера в порядке обхода(использования)
             graph.getModel().setValue(points.get(listDepht.get(step) - 1), graph.getModel().getValue(points.get(listDepht.get(step) - 1)).toString() + ",(" + (used(step) + 1) + ")");
             listDepht.remove(step);// удаление вершины из списка
        } else { // если вершина имеет ребро  
            System.out.println("удаление false");
             model.endUpdate();
             this.revalidate();
             return false;
        }
         model.endUpdate();
         this.revalidate();
         System.out.println("удаление_2 true");
         return true;
    }

    
    public void functionVisual() { //функция визуализации графа 
        removeAll();
        mxGraph graph = new mxGraph();
        model = graph.getModel();
        Object parent = graph.getDefaultParent();
        model.beginUpdate();
        double phi0 = 0;
        double phi = 2 * Math.PI / n;
        int r = 230;
        for (int i = 0; i < n; i++) {  // создание вершин на поле            
            points.add(i, graph.insertVertex(parent, null, i + 1, 250 + r * Math.cos(phi0), 250 + r * Math.sin(phi0), 40, 40, "shape=ellipse"));
            phi0 += phi;
        }
        for (int i = 0; i < adj.size(); i++) {
            for (int j = 0; j < adj.get(i).size(); j++) { // создание ребер 
                graph.insertEdge(parent, null, null, points.get(i), points.get(adj.get(i).get(j)));
            }
        }
        model.endUpdate();
        graph.setAllowDanglingEdges(false);
        graph.setCellsResizable(false);
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setConnectable(false);
        this.add(graphComponent);
        this.revalidate();
    }
     public int dfsVisual_2(int step, int kol) { // функция визуализации обратного пошагового обхода графа 
         mxGraph graph = new mxGraph();
         graph.setModel(model);
         model.beginUpdate();
         provv = false;
        if ((listDepht.size() == 1) && (step == -1)) { // если остался один элемен, то необходимо исползовать его( удалить)
             step=0;
        }
        int st=-1;
        for (int i = 0; i < n; i++) { // поиск ребра для вершины с индексом step
            if (listEdges_1.contains(new Pair<>( listDepht.get(step) - 1,i))) {
                // покраска ребра, как не пройденного(неиспользованного)
                graph.getModel().setStyle(graph.getEdgesBetween( points.get(listDepht.get(step) - 1),points.get(i))[0], "strokeColor=#0");
                listEdges_1.remove(new Pair<>( listDepht.get(step) - 1,i));
                provv = true;
                st=i;
                break;
             }
        }
         if (provv == false) { // если ребра для указанной вершины нет, то перекрашиваем только вершину
             System.out.println("перекраска false"); 
             model.endUpdate();
             this.revalidate();
             return st;
        } 
        // если вершина имеет ребро  
            System.out.println("перекраска true");
             model.endUpdate();
             this.revalidate();
             return st;
     }

}
