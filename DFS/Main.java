package newpackege;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class Main {

    static int step = 0;

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Обход ориентированного графа в глубину");
        Color c1 = new Color(0x6495ED);
        frame.getContentPane().setBackground(c1);
        Visualisator adj = new Visualisator();
        frame.setSize(new Dimension(900, 640));//устанавливаем размер
        frame.setResizable(false);//не двигаем
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//вырубаем все
        frame.setLayout(null);
        //левое поле
        JButton btnDepht = new JButton("Добавить вершину");
        btnDepht.setSize(new Dimension(150, 50));
        btnDepht.setLocation(5,5);
        JButton data=new JButton("Выбрать файл");
        data.setSize(new Dimension(150, 50));
        data.setLocation(170,5);
        JLabel lb1=new JLabel("Добавить ребро");
        lb1.setSize(new Dimension(150, 50));
        lb1.setLocation(29,45);
        JLabel lb2=new JLabel("из");
        lb2.setSize(new Dimension(75, 50));
        lb2.setLocation(35,65);
        JLabel lb3=new JLabel("в");
        lb3.setSize(new Dimension(75, 50));
        lb3.setLocation(125,65);
        SpinnerModel model=new SpinnerNumberModel(1,1,50,1);
        JSpinner from=new JSpinner(model);
        from.setSize(new Dimension(55, 25));
       from.setLocation(10,105);               
       SpinnerModel model2 = new SpinnerNumberModel(1,1,50,1);      
       JSpinner to=new JSpinner(model2);
        to.setSize(new Dimension(55, 25));
       to.setLocation(100,105);
        JButton btnEdge = new JButton("Добавить ребро");
         btnEdge.setSize(new Dimension(150, 50));
         btnEdge.setLocation(5,135);
        JButton btnStart = new JButton("Старт");
       btnStart.setSize(new Dimension(150, 50));
         btnStart.setLocation(5,190);
        JButton btnNext = new JButton("Следующий шаг");
        btnNext.setSize(new Dimension(150, 50));
        btnNext.setLocation(5,245);
        JButton btnResult = new JButton("Результат");
        btnResult.setSize(new Dimension(150, 50));
        btnResult.setLocation(5,300);
        JButton btnClear_fields = new JButton("Очистить поля");
         btnClear_fields.setSize(new Dimension(150, 50));
         btnClear_fields.setLocation(5,355);
        //центральное поле
        
        JTextArea out = new JTextArea();  //промежуточные результаты      
        JScrollPane my_pane2=new JScrollPane(out);
        my_pane2.setBounds(170, 60, 150, 345);
        out.setEditable(false);
        JTextArea start_data = new JTextArea();    //начальная матрица 
        JScrollPane my_pane=new JScrollPane(start_data);
        my_pane.setBounds(5, 410, 316, 200);
              
        start_data.setEditable(false);
        start_data.append("Начальные данные: "+"\n"); 
        
        //правое поле
        Algo panel = new Algo();
        panel.setSize(565,530);
       panel.setLocation(325,0);
        JTextArea textresult = new JTextArea();
        textresult.setSize(565,72);
        textresult.setLocation(325,535);
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

        btnStart.setEnabled(true);
        btnClear_fields.setEnabled(false);
        btnDepht.setEnabled(true);
        btnEdge.setEnabled(true);
        btnNext.setEnabled(false);
        btnResult.setEnabled(false);
        
        //File file = new File ("z://", "data.txt");
        //panel.readData();
       // panel.run();
       // panel.functionVisual();
        
        
               /* for(int i = 0; i < adj.get_N(); i++){
                   start_data.append(panel.print_start_data(i)+"\n");    
                }*/
       
        btnEdge.addActionListener(new ActionListener() {
            
            @Override
            
            public void actionPerformed(ActionEvent e) {
              panel.count_M();
              int v = (Integer) from.getValue();
              int w=(Integer) to.getValue(); 
              panel.changeDataEdge( v-1,  w-1);
              panel.functionVisual(); 
               
            }
        });        
               
               
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.used = new boolean[adj.get_N()];
                panel.run();
                btnStart.setEnabled(false);
                btnNext.setEnabled(true);
                for(int i = 0; i < adj.get_N(); i++){
                   start_data.append(panel.print_start_data(i)+"\n");    
                }
               
            }
        });          
                
        btnDepht.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                panel.count_N();
                panel.changeData_adj();
                panel.functionVisual();
               
            }
        });        
                
                
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
                btnClear_fields.setEnabled(true);
            }
        });
        btnClear_fields.addActionListener(new ActionListener() { 
        @Override 
        public void actionPerformed(ActionEvent e) { 
      //  adj.listDepht.clear();
        //adj.listEdges.clear();
       // panel.functionVisual();
        start_data.selectAll(); 
        start_data.replaceSelection(""); 
        out.selectAll(); 
        out.replaceSelection(""); 
        textresult.selectAll(); 
        textresult.replaceSelection(""); 
        btnClear_fields.setEnabled(false); 
        btnStart.setEnabled(true); 
        btnResult.setEnabled(false); 
        step = 0; 
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
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Некорректный файл!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } catch (NullPointerException ex) {
                        JOptionPane.showMessageDialog(null, "Неверное задание списка!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } catch (IndexOutOfBoundsException ex) {
                        JOptionPane.showMessageDialog(null, "Неверное задание списка!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                  }
                
//                adj.listDepht.clear();
//                adj.listEdges.clear();
            }
        });
    }
}