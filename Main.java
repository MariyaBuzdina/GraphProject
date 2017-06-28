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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class Main {

    static int step = 0;//счетчики
    static int currVer = 0;//
    static int kol = 0;//счетчик вершин
    static int count = 0;//номер шага
    

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Обход ориентированного графа в глубину");
        Color c1 = new Color(0x6495ED);
        frame.getContentPane().setBackground(c1);
        Visualisator visio = new Visualisator();
        frame.setSize(new Dimension(970, 640));//устанавливаем размер
        frame.setResizable(false);//не двигаем
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//вырубаем все
        frame.setLayout(null);
        //левое поле
        JButton btnDepht = new JButton("Добавить вершину");
        btnDepht.setSize(new Dimension(150, 50));
        btnDepht.setLocation(5, 5);
        JButton data = new JButton("Выбрать файл");
        data.setSize(new Dimension(150, 50));
        data.setLocation(210, 5);
        JLabel lb1 = new JLabel("Добавить ребро");
        lb1.setSize(new Dimension(150, 50));
        lb1.setLocation(29, 45);
        JLabel lb2 = new JLabel("из");
        lb2.setSize(new Dimension(75, 50));
        lb2.setLocation(35, 65);
        JLabel lb3 = new JLabel("в");
        lb3.setSize(new Dimension(75, 50));
        lb3.setLocation(125, 65);
        SpinnerModel model = new SpinnerNumberModel(1, 1, 50, 1);
        JSpinner from = new JSpinner(model);
        from.setSize(new Dimension(55, 25));
        from.setLocation(10, 105);
        SpinnerModel model2 = new SpinnerNumberModel(1, 1, 50, 1);
        JSpinner to = new JSpinner(model2);
        to.setSize(new Dimension(55, 25));
        to.setLocation(100, 105);
        JButton btnEdge = new JButton("Добавить ребро");
        btnEdge.setSize(new Dimension(150, 50));
        btnEdge.setLocation(5, 135);
        JButton btnStart = new JButton("Старт");
        btnStart.setSize(new Dimension(150, 50));
        btnStart.setLocation(5, 190);
        JButton btnNext = new JButton("Следующий шаг");
        btnNext.setSize(new Dimension(150, 50));
        btnNext.setLocation(5, 245);
        JButton btnResult = new JButton("Результат");
        btnResult.setSize(new Dimension(150, 50));
        btnResult.setLocation(5, 300);
        JButton btnClear_fields = new JButton("Очистить поля");
        btnClear_fields.setSize(new Dimension(150, 50));
        btnClear_fields.setLocation(5, 355);
        //центральное поле

        JTextArea out = new JTextArea();  //промежуточные результаты      
        JScrollPane my_pane2 = new JScrollPane(out);
        my_pane2.setBounds(170, 60, 220, 345);
        out.setEditable(false);
        JTextArea start_data = new JTextArea();    //начальная матрица 
        JScrollPane my_pane = new JScrollPane(start_data);
        my_pane.setBounds(5, 410, 385, 200);

        start_data.setEditable(false);

        //правое поле
        Algo panel = new Algo();
        panel.adj = new ArrayList<ArrayList<Integer>>();//создаем пустой список вершин
        panel.setSize(565, 530);
        panel.setLocation(395, 5);
        JTextArea textresult = new JTextArea();
        textresult.setSize(565, 68);
        textresult.setLocation(395, 540);
        textresult.setEditable(false);
        //размещаем
        frame.add(btnDepht);
        frame.add(lb1);
        frame.add(lb2);
        frame.add(lb3);
        frame.add(from);
        frame.add(to);
        frame.add(btnEdge);
        frame.add(btnStart);
        frame.add(btnNext);
        frame.add(data);
        frame.add(panel);
        frame.add(textresult);
        frame.add(btnResult);
        frame.add(btnClear_fields);
        frame.add(my_pane2);
        frame.add(my_pane);
        frame.setVisible(true);

        btnStart.setEnabled(false);
        btnClear_fields.setEnabled(true);
        btnDepht.setEnabled(true);
        btnEdge.setEnabled(true);
        btnNext.setEnabled(false);
        btnResult.setEnabled(false);

        btnEdge.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                panel.inc_M();
                int v = (Integer) from.getValue();
                int w = (Integer) to.getValue();
                if (panel.search_for(v - 1, w - 1)) {
                    panel.changeDataEdge(v - 1, w - 1);
                    panel.functionVisual();
                    panel.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Нельзя соединить данные вершины!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!panel.adj.isEmpty()) {
                    start_data.append("Начальные данные: " + "\n");
                    out.append("Промежуточные выводы: " + "\n");
                    panel.used = new boolean[visio.get_N()];
                    panel.run();
                    data.setEnabled(false);
                    btnDepht.setEnabled(false);
                    btnEdge.setEnabled(false);
                    btnStart.setEnabled(false);
                    btnNext.setEnabled(true);
                    for (int i = 0; i < visio.get_N(); i++) {
                        start_data.append(panel.print_start_data(i) + "\n");
                    }
                } else {
                    btnStart.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "Данные отсутствуют!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        btnDepht.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (panel.get_N() == 50) {
                    JOptionPane.showMessageDialog(null, "Запрещено работать более чем с 50 вершинами!", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    btnStart.setEnabled(true);
                    panel.inc_N();
                    panel.changeData_adj();
                    panel.functionVisual();
                    panel.repaint();
                }

            }
        });

        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(step);
                System.out.println(panel.listEdges_1.size());
                if (panel.dfsVisual(step, currVer, kol)) {
                    kol++;
                    step++;
                    currVer = step;
                    
                    out.append(panel.output.get(count) + "\n");
                    count++;
                    System.out.println("Next");
                } else {
                    int st=panel.dfsVisual_2(step-1, kol);
                    if(st==-1){

                    if (panel.dfsVisual_1(currVer - 1, step, kol)) {

                        currVer = step;
                        System.out.println("Exitttt");
                    } else {
                        System.out.println("Exit");
                        currVer--;
                    }
                    out.append(panel.output.get(count) + "\n");
                    step--;
                    count++;
                    }else{
                    out.append("Вершина " + (st+1) + " уже обработана"+"\n");
                   // coun++;
                    }
                }
                
                if (panel.listDepht.size() == 0) {
                    out.append("Поиск в глубину завершен\n");
                    btnNext.setEnabled(false);
                    btnResult.setEnabled(true);
                }
            }
        });

        btnResult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textresult.setText(panel.printresult());
                btnNext.setEnabled(false);
                btnResult.setEnabled(false);
            }
        });
        btnClear_fields.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setnull();
                panel.adj.clear();
                panel.listDepht.clear();
                panel.listEdges.clear();
                panel.model.remove(this);
                panel.points.clear();
                panel.result.clear();
                panel.output.clear();
                panel.listEdges_1.clear();
                panel.repaint();
                panel.functionVisual();
                start_data.selectAll();
                start_data.replaceSelection("");
                out.selectAll();
                out.replaceSelection("");
                textresult.selectAll();
                textresult.replaceSelection("");
                btnDepht.setEnabled(true);
                btnEdge.setEnabled(true);
                btnResult.setEnabled(false);
                data.setEnabled(true);
                
                step = 0;
                currVer = 0;
                kol = 0;
                count = 0;
            }
        });
        data.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int ret = fileChooser.showDialog(null, "Open file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    try {
                        panel.readData(fileChooser.getSelectedFile());
                        panel.functionVisual();
                        btnStart.setEnabled(true);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Некорректный данные!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } catch (NullPointerException ex) {
                        //число пар меньше чем задано во 2 поле
                        JOptionPane.showMessageDialog(null, "Несоответствие заданного и фактического количества ребер!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } catch (IndexOutOfBoundsException ex) {
                        //связываются не существующие вершины
                        JOptionPane.showMessageDialog(null, "Попытка связать несуществующие вершины!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}