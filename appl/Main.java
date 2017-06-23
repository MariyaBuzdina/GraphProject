package newpackege;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.io.*;


public class Main {

    static int step = 0;

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Обход оргрфа в глубину");
        Visualisator adj = new Visualisator();
        frame.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        frame.setLayout(new FlowLayout());

        Algo panel = new Algo();

        JButton btnNext = new JButton("Следующий шаг");
        JButton btnResult = new JButton("Результат");
        JButton btnEdge = new JButton("Добавить ребро");
        JButton btnDepht = new JButton("Добавить вершину");
        JButton btnStart = new JButton("Старт");
        JButton btnClear_fields = new JButton("Очистить поля");
        
        JTextArea textresult = new JTextArea();
        textresult.setPreferredSize(new Dimension(200, 100));
        JTextArea out = new JTextArea();  //промежуточные результаты      
        out.setPreferredSize(new Dimension(200, 100));
        JTextArea start_data = new JTextArea();    //начальная матрица    
        start_data.setPreferredSize(new Dimension(200, 100));
        
        frame.add(btnNext);
        frame.add(btnResult);
        frame.add(btnStart);
        frame.add(btnClear_fields);
        frame.add(btnDepht);
        frame.add(btnEdge);
        frame.add(panel);
        frame.add(out);
        frame.add(start_data);
        frame.add(textresult);
        frame.setVisible(true);

        btnStart.setEnabled(false);
        btnClear_fields.setEnabled(false);
        btnDepht.setEnabled(false);
        btnEdge.setEnabled(false);
        btnNext.setEnabled(true);
        btnResult.setEnabled(false);
        
        File file = new File ("D://", "data.txt");
        panel.run(file);
        panel.functionVisual();
        
        start_data.append("Начальные данные: "+"\n"); 
                for(int i = 0; i < adj.get_N(); i++){
                   start_data.append(panel.print_start_data(i)+"\n");    
                }
        
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                panel.dfsVisual(step);
                step++;
                if (step == panel.getN())
                {
                    btnNext.setEnabled(false);
                    btnResult.setEnabled(true);  
                }
            }
        });
         
        btnResult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {                
                textresult.setText(panel.printresult());
                btnNext.setEnabled(false);
            }
        });
        
        /*btnData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int ret = fileChooser.showDialog(null, "Open file");
                if (ret == JFileChooser.APPROVE_OPTION) {

                    try {
                        panel.run(fileChooser.getSelectedFile());
                        panel.functionVisual();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "input incorrect!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } catch (NullPointerException ex) {
                        JOptionPane.showMessageDialog(null, "ArrayList incorrect!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } catch (IndexOutOfBoundsException ex) {
                        JOptionPane.showMessageDialog(null, "ArrayList incorrect!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    btnNext.setEnabled(true);

                }
                
//                adj.listDepht.clear();
//                adj.listEdges.clear();
            }
        });*/
    }
}
