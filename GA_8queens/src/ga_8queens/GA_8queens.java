/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//hasan shamohamadi 9513170008
package ga_8queens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JOptionPane;

/**
 *
 * @author hasan
 */
public class GA_8queens {

    
    ArrayList<Chromosome> Pop;
    int OverallFitness;
    int MAX_FIT = 28;
    double CrossoverP;
    Random random;
    boolean emergencystop =false;
    

    private ArrayList<Chromosome> GetPopulation(int size) {
        
       ArrayList<Integer> Genes = new ArrayList<>();
       ArrayList<Chromosome> p = new ArrayList<>();
     
       for (int i=0; i<8; i++)  Genes.add(i);
           
       for (int k = 0; k < size; k++) {
            
           Collections.shuffle(Genes);
           Chromosome c = new Chromosome();
           c.Genes=new int[8];
           
            for (int i = 0; i < 8; i++) {
                c.Genes[i] =Genes.get(i);
            }p.add(c);
     }
        return p;
    }

    private int GetFitness(ArrayList<Chromosome> Pop) {
       int collisions = 0;
       int Fitness =0;
            for (int k = 0; k < Pop.size(); k++)
            {
                for (int i = 0; i <8; i++)
                {
                    int column = i;
                    int row = Pop.get(k).Genes[i];
                    for (int j = i + 1; j <8; j++)
                    {
                        if (Math.abs(j - column) == Math.abs(Pop.get(k).Genes[j] - row)) 
                            collisions++;                        
                    }
                }
              
                Pop.get(k).Fitness = MAX_FIT - collisions;
                Fitness += Pop.get(k).Fitness;
                collisions = 0;
            } return Fitness;
        }

    private void PrepareRoulette(int OverallFitness, ArrayList<Chromosome> Pop) {
        
        for (int i = 0; i < Pop.size(); i++) {
           Pop.get(i).rouletteProbability = Pop.get(i).Fitness/(double)OverallFitness;
        }
    }

    private ArrayList<Chromosome> Crossover(ArrayList<Chromosome> Pop, double CrossoverP) {
        
        ArrayList<Chromosome> Offsprings = new ArrayList<>();
        Chromosome offspring = new Chromosome();
        
        for (int p = 0; p < Pop.size(); p++) {
            
             if (Lucky(CrossoverP)){// if there is a chance to cross over ?
            
            Chromosome parentX = TurnRuletteWheel(Pop);
            Chromosome parentY = TurnRuletteWheel(Pop);
            
            ArrayList<Integer> child  = new ArrayList<>();
            
            for (int r = 0; r < 8; r++) {// we are gonna add 8 genes to a child
                
           if(Lucky(.5)){// chance to get genes from Parentx
                for (int i = 0; i < 8; i++) {
                    if(!child.contains(parentX.Genes[i])){
                      child.add(parentX.Genes[i]);
                      break;
                    }
                       
                }// end of for
            }else {// chance to get genes from parenty
                
              for (int i = 0; i < 8; i++) {
                    if(!child.contains(parentY.Genes[i])){
                      child.add(parentY.Genes[i]);
                      break;
                    }
                }// end of for  
            }
        }// end of second for , now our child is filled with genes from its parents 
            offspring.Genes = new int[8];
            for (int i = 0; i < 8; i++) {
                offspring.Genes[i] = child.get(i);
            }Offsprings.add(offspring);
        }else {// there is not a chance to cross over 
            Offsprings.add(TurnRuletteWheel(Pop));
        }
             
    }// end of first for 
       return Offsprings;
       
    }

    private boolean Lucky(double CrossoverP) {
        return ThreadLocalRandom.current().nextDouble() < CrossoverP;
    }

    private Chromosome TurnRuletteWheel(ArrayList<Chromosome> Pop) {
        for (int i = 0; i < Pop.size(); i++) {
            if(Pop.get(i).rouletteProbability>ThreadLocalRandom.current().nextDouble()){
                return Pop.get(i);
            }
        }return Pop.get(ThreadLocalRandom.current().nextInt(0, Pop.size()));
    }



    Chromosome Start(String Cp, String Mp, String Ng, String Np, boolean Toggle) {
            double counter=0;
            emergencystop=false;
            Chromosome min;
            CrossoverP =Double.parseDouble(Cp);
      do {
        Pop= GetPopulation(Integer.parseInt(Np));// initiate first population 
        min = Pop.get(0);
        int count =0;
        while(count++ < Integer.parseInt(Ng)){ // produce N generations
           OverallFitness = GetFitness(Pop); // specified fitness for each chromosome
           PrepareRoulette(OverallFitness,Pop);// prepare roulette wheel
           Pop= Crossover(Pop, CrossoverP);    // turn the wheel and cross over
           Mutate(Mp,Pop);
            for (int i = 0; i < Pop.size(); i++) {
              if(Pop.get(i).Fitness>min.Fitness)min= Pop.get(i); // get the best answer 
            }
            if(counter++>5000){emergencystop=true;}
            
        }
      }while(min.Fitness!=28 && Toggle && !emergencystop );
      if(emergencystop){JOptionPane.showMessageDialog(null, "Sorry we could not find the answer");}
      return min;
    }

    
    private void Mutate(String Mp, ArrayList<Chromosome> Pop) {
        
          ArrayList<Integer> random = new ArrayList<>();
                for (int j = 0; j < 8; j++) {
                    random.add(j);
                }
                
        for (int i = 0; i < Pop.size(); i++) {
            Collections.shuffle(Pop);
            if(Lucky(Double.parseDouble(Mp))){
               
                Collections.shuffle(random);
                int r=ThreadLocalRandom.current().nextInt(0, 8);
                int r2;
                if (r>4) r2=r-1;else r2=r+1;
                int temp = Pop.get(i).Genes[r];
                Pop.get(i).Genes[r]=Pop.get(i).Genes[r2];
                Pop.get(i).Genes[r2]=temp;
            }
        }
    }
  }

 class Chromosome {
    int Fitness;
    int[] Genes;
    double rouletteProbability;
}

 
    



