package newpackege;

import javax.swing.*;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javafx.util.Pair;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Visualisator extends JPanel {

    protected static int n; 
    public int get_N(){return n;};
    protected static int m; 
    protected ArrayList<ArrayList<Integer>> adj; 
    protected boolean used[]; 
    protected ArrayList<Integer> listDepht;
    protected ArrayList<Pair<Integer, Integer>> listEdges;
    protected Object[] points;
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
    }

    public void functionVisual() {
        removeAll();
        mxGraph graph = new mxGraph();

        points = new Object[n];
        model = graph.getModel();

        Object parent = graph.getDefaultParent();
        model.beginUpdate();

        double phi0 = 0;
        double phi = 2 * Math.PI / n;
        int r = 230; 
        for (int i = 0; i < points.length; i++) {
            points[i] = graph.insertVertex(parent, null, i + 1, 250 + r * Math.cos(phi0), 250 + r * Math.sin(phi0), 30, 30, "shape=triangle");
            phi0 += phi;
        }
        for (int i = 0; i < adj.size(); i++) {
            for (int j = 0; j < adj.get(i).size(); j++) {
                graph.insertEdge(parent, null, null, points[i], points[adj.get(i).get(j)]);
            }
        }

        model.endUpdate();
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        this.add(graphComponent);
        this.revalidate();
    }
    
    public void dfsVisual(int step) {
        mxGraph graph = new mxGraph();
        graph.setModel(model);
        model.beginUpdate();
        if (step == 0) {
            graph.getModel().setValue(points[listDepht.get(0) - 1], graph.getModel().getValue(points[listDepht.get(0) - 1]).toString() + "(1)");
        }
        else {

            for (int i = 0; i < listDepht.size(); i++) {
                if (listEdges.contains(new Pair<>(i, listDepht.get(step) - 1))) {
                    graph.getModel().setStyle(graph.getEdgesBetween(points[i], points[listDepht.get(step) - 1])[0], "strokeColor=#00FF00");
                    //graph.getModel().setStyle(graph.getEdgesBetween(points[i], points[listDepht.get(step) - 1])[1], "strokeColor=#00FF00");
                    break;
                }
            }
            graph.getModel().setValue(points[listDepht.get(step) - 1], graph.getModel().getValue(points[listDepht.get(step) - 1]).toString() + "(" + (step + 1) + ")");
        }
        model.endUpdate();
        this.revalidate();
    }
}
