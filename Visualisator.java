/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helloworld;

/**
 *
 * @author Ольга
 */

import javax.swing.*;
//import javax.swing.Timer;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import javafx.util.Pair;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

public class Visualisator extends JPanel {

    protected static int n; 
    public int get_N(){return n;};
    public void count_N(){n++;};
    public void count_M(){m++;};
    protected static int m; 
    protected ArrayList<ArrayList<Integer>> adj; 
    protected boolean used[]; 
    protected boolean provv; 
    protected Timer timer;
    protected ArrayList<Integer> listDepht;
    protected ArrayList<Integer> listDepht1;
    protected ArrayList<Pair<Integer, Integer>> listEdges;
    protected ArrayList<Object>  points;//вершины графа
    protected mxIGraphModel model;
    protected ArrayList<Integer> result ;
    public PrintWriter cout;

    @SuppressWarnings("unused")
    public Visualisator() throws IOException {
        this.setSize(700, 700);
        this.setPreferredSize(new Dimension(700, 700));
        listDepht = new ArrayList<>();
        listEdges = new ArrayList<>();
        result = new ArrayList<>(n);
        points=new ArrayList<>();
    }

    public void functionVisual() {
        removeAll();
        mxGraph graph = new mxGraph();

        
        model = graph.getModel();

        Object parent = graph.getDefaultParent();
        model.beginUpdate();

        double phi0 = 0;
        double phi = 2 * Math.PI / n;
        int r = 230; 
        for (int i = 0; i < n; i++) {
            points.add(i,graph.insertVertex(parent, null, i + 1, 250 + r * Math.cos(phi0), 250 + r * Math.sin(phi0), 30, 30, "shape=ellipse"));
            phi0 += phi;
        }
        for (int i = 0; i < adj.size(); i++) {
            for (int j = 0; j < adj.get(i).size(); j++) {
                graph.insertEdge(parent, null, null, points.get(i), points.get(adj.get(i).get(j)));
            }
        }

        model.endUpdate();
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        this.add(graphComponent);
        this.revalidate();
    }
    /*
    public void dfsVisual(int step) {
        mxGraph graph = new mxGraph();
        graph.setModel(model);
        model.beginUpdate();
        if (step == 0) {
             graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "green", new Object[]{points.get(listDepht.get(0) - 1)});
             graph.getModel().setValue(points.get(listDepht.get(0) - 1), graph.getModel().getValue(points.get(listDepht.get(0) - 1)).toString() + "(1)");
        }
        else {

            for (int i = 0; i < listDepht.size(); i++) {
                provv=true;
                if (listEdges.contains(new Pair<>(i, listDepht.get(step) - 1))) {
                    provv=false;
                     graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "green", new Object[]{points.get(listDepht.get(step) - 1)});
                     graph.getModel().setStyle(graph.getEdgesBetween(points.get(i), points.get(listDepht.get(step) - 1))[0], "strokeColor=#00FF00");
                    //graph.getModel().setStyle(graph.getEdgesBetween(points[i], points[listDepht.get(step) - 1])[1], "strokeColor=#00FF00");
                    break;
                }
            }
            if(provv==true)graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "green", new Object[]{points.get(listDepht.get(step) - 1)});
                
            graph.getModel().setValue(points.get(listDepht.get(step) - 1), graph.getModel().getValue(points.get(listDepht.get(step) - 1)).toString() + "(" + (step + 1) + ")");
        }
        model.endUpdate();
        this.revalidate();
    }
    */
   public boolean dfsVisual(int step, int stepp, int kol)  {
        mxGraph graph = new mxGraph();
        graph.setModel(model);
        model.beginUpdate();
        
       if (kol == 0) {
            graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "green", new Object[]{points.get(listDepht.get(0) - 1)});
            graph.getModel().setValue(points.get(listDepht.get(0) - 1), graph.getModel().getValue(points.get(listDepht.get(0) - 1)).toString() + "(1)");
        }
        else {
           if(step!=0){
            if ((listDepht.size()==1)||(!listEdges.contains(new Pair<>(listDepht.get(stepp-1) - 1, listDepht.get(step) - 1))) ){
                model.endUpdate();
                this.revalidate();
                return false;
             }
           }
            for (int i = 0; i < listDepht.size(); i++) {
                provv=false;
                if (listEdges.contains(new Pair<>(i, listDepht.get(step) - 1))) {
                     provv=false;
                     graph.getModel().setStyle(graph.getEdgesBetween(points.get(i), points.get(listDepht.get(step) - 1))[0], "strokeColor=#00FF00");
                     provv=true;
                     break;
                }
            }
            graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "green", new Object[]{points.get(listDepht.get(step) - 1)});
            graph.getModel().setValue(points.get(listDepht.get(step) - 1), graph.getModel().getValue(points.get(listDepht.get(step) - 1)).toString() + "(" + (kol + 1) + ")");
           
           }
           
        model.endUpdate();
        this.revalidate();
      return true;
    }
    
    public boolean dfsVisual_1(int step, int prov, int kol)  {
        mxGraph graph = new mxGraph();
        graph.setModel(model);
        model.beginUpdate();
        provv=false;
               if((listDepht.size()==1)&&(step==-1)){
                   graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "red", new Object[]{points.get(listDepht.get(prov) - 1)});
                  listDepht.remove(prov);
                   model.endUpdate();
                    this.revalidate();
                    return true;
               }
                for (int i = 0; i < listDepht.size(); i++) {
                     if (listEdges.contains(new Pair<>(i, listDepht.get(step) - 1 ))) {
                         System.out.println("REDD");
                         graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "red", new Object[]{points.get(listDepht.get(step) - 1)});
                         graph.getModel().setStyle(graph.getEdgesBetween(points.get(i), points.get(listDepht.get(step) - 1))[0], "strokeColor=#A");
                         listDepht.remove(step);
                          provv=true;
                         break;
                        }//else step--;
                    }
                if(provv==false){
                graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "red", new Object[]{points.get(listDepht.get(step) - 1)});
                listDepht.remove(step);
                }else {
                    model.endUpdate();
                    this.revalidate();
                    return false;
                                
                }
        model.endUpdate();
        this.revalidate();
        return true;
    }
}
