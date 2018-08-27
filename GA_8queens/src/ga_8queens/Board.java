/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga_8queens;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Board extends JFrame{
    
    JButton[][] queens;
    GA_8queens ga;
    ImageIcon backgroud  = new ImageIcon(getClass().getResource("background.png"));
    Chromosome best;
    JRadioButton toggle;
    JButton result;
    
    private ActionListener getclicks =  new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            
        int[] index = Getindex(e.getSource());
            if(index[0]==1){
                resetboard();
                for (int k = 0; k < 8; k++){ 
                    queens[index[1]][k].setBackground(Color.red);
                    queens[k][index[2]].setBackground(Color.red);
                }
                for (int l = 0; l < 8; l++) {
                     for (int m = 0; m < 8; m++) {
                        if(Math.abs(l-index[1])==Math.abs(m-index[2]))queens[l][m].setBackground(Color.red);
                        }
                    }
            }
        }

        private void resetboard() {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    queens[i][j].setBackground(Color.WHITE);
                    
                }
            }
        }

     

        private int[] Getindex(Object s) {
            int []r={0,0,0};
            for (int i = 0; i < 8; i++) {
                if(queens[i][best.Genes[i]]==s){r[0]=1;r[1]=i;r[2]=best.Genes[i]; return r;}
            }return r;
        }
        
    };
    public Board( ){
     
        ga= new GA_8queens();
        this.setSize(850,605);
        setResizable(false);
        

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        setLayout(null);
        JPanel jp =  new JPanel();
        jp.setBounds(3, 3, 560, 560);
        
       // control.setLayout(null);
        JTextField CP = new JTextField(".3");
        JTextField MP = new JTextField(".01");
        JTextField NG = new JTextField("20");
        JTextField NP = new JTextField("50");
        JLabel l1 =  new JLabel("Cross over Probability ");
        JLabel l2 =  new JLabel("Mutation Probability   ");
        JLabel l3 =  new JLabel("Generation quantity    ");
        JLabel l4 =  new JLabel("Population quantity    ");
        JLabel l5 =  new JLabel("Continue untill an answer is found");
        JLabel l6 =  new JLabel("Best answer ");
        
        result = new JButton();
        CP.setBounds(750,50, 80, 30);
        MP.setBounds(750,90, 80, 30);
        NG.setBounds(750,130, 80, 30);
        NP.setBounds(750,170, 80, 30);
        
        l1.setBounds(600,50, 150, 30);
        l2.setBounds(600,90, 150, 30);
        l3.setBounds(600,130, 150, 30);
        l4.setBounds(600,170, 150, 30);
        l5.setBounds(600,210, 200, 30);
        l6.setBounds(600, 300, 200,40);
        
        toggle = new JRadioButton();
        
        toggle.setBounds(800,210, 150, 30);
        JButton Start =  new JButton("Start The Algorithm");
        Start.setBounds(600, 250, 200,40);
        result.setBounds(600, 350, 200,40);
       
        
        jp.setLayout(new GridLayout(8, 8,3, 3));
        queens =  new JButton[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
             queens[i][j] =  new JButton();
             queens[i][j].setBackground(Color.white);
             jp.add(queens[i][j]);
            }
           
        }
        add(CP);add(MP);add(NG);add(NP);
        add(jp); add(l1);add(Start);add(l6);
        add(l2);add(l3);add(l4);add(l5);add(toggle);
        add(result);
        for (int i = 0; i < 8; i++) {
            queens[0][i].setIcon(backgroud);
        }
       
       Start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(validinput()){
                  clean();
              String s ="";
              best = ga.Start(CP.getText(),MP.getText(),NG.getText(),NP.getText(),toggle.isSelected());
              for (int i = 0; i < 8; i++) {
              
             queens[i][best.Genes[i]].setIcon(backgroud);
             queens[i][best.Genes[i]].addActionListener(getclicks);
             s += ("["+best.Genes[i]+"] ").toString();
             }
              result.setText(s);
              
              if(28-best.Fitness==1)l6.setText("Best answer with "+(28-best.Fitness)+" collision");
              else l6.setText("Best answer with "+(28-best.Fitness)+" collisions");
              
              if(best.Fitness==28)result.setBackground(Color.GREEN);
              else if(best.Fitness>24) result.setBackground(Color.YELLOW);
              else if(best.Fitness<24)result.setBackground(Color.RED);  
              
              }else JOptionPane.showMessageDialog(null, "oops! check your input and try again");
              
              
          }

            private void clean() {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        queens[i][j].setIcon(null);
                        queens[i][j].setBackground(Color.white);
                    }
                }
            }

            private boolean validinput() {
                return !"".equals(CP.getText()) && !"".equals(MP.getText())&& !"".equals(NG.getText())&&!"".equals(NP.getText());
            }
        });
        repaint();
        setLocation(500, 20);
    }
    
 public static void main(String[] args) {
       new Board();
    }

}
