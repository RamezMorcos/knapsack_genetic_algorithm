import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.util.Collections.swap;

public class population {
    ArrayList<chromosome> population = new ArrayList<>();
    ArrayList<chromosome> children = new ArrayList<>();
    int index_first_child=0;
    int index_second_child=0;
    int knapsack_weight;
    population(int num_items, int pop_size,ArrayList<Double>w,ArrayList<Double>v,int knapsack_w) {
        for (int i = 0; i < pop_size; i++) {
            chromosome c = new chromosome(num_items,w,v);
            population.add(c);
        }
      knapsack_weight=knapsack_w;
    }
    public void fitness_calc(){
        for(int i=0;i<population.size();i++){
            int w=0;
            for(int j=0;j<population.get(i).chromosome.size();j++){
                population.get(i).fitness_function+=population.get(i).value.get(j)*population.get(i).chromosome.get(j);
                if(population.get(i).chromosome.get(j)!=0){
                  w+=  population.get(i).weight.get(j);
                }
            }
            if(w>knapsack_weight){
                population.get(i).fitness_function=1/population.get(i).fitness_function;
            }
        }
    }
public void get_secend_child(double lower_prob,double higher_prob){
    Random r=new Random();
    double second_child=r.nextDouble();
    while(second_child>=lower_prob&&second_child<=higher_prob){
        second_child=r.nextDouble();
    }
    for(int i=0;i<population.size();i++) {
        if (second_child >= population.get(i).lower_prob && second_child <= population.get(i).higher_prob) {
            chromosome c = population.get(i);
            children.add(c);
            index_second_child = i;
        }

    }
}
public void selection(){
        double total_population=0;

        for(int i=0;i<population.size();i++){
           total_population+= population.get(i).fitness_function;

        }
    population.get(0).lower_prob=0;
    population.get(0).higher_prob=population.get(0).fitness_function/total_population;
       for(int i=1;i<population.size();i++){
           population.get(i).lower_prob=population.get(i-1).higher_prob;
           population.get(i).higher_prob=population.get(i).lower_prob+population.get(i).fitness_function/total_population;
       }
       Random r=new Random();
        double firstchild=r.nextDouble();
        for(int i=0;i<population.size();i++){
           if(firstchild>=population.get(i).lower_prob&&firstchild<= population.get(i).higher_prob)
           {
                chromosome c=population.get(i);
               children.add(c);
               if(index_first_child==0){index_first_child=i;}

             get_secend_child(population.get(i).lower_prob,population.get(i).higher_prob);
               break;
           }

        }
}
public void crossover(){
    Random r=new Random();
    int len_of_crossover=r.nextInt(children.get(0).chromosome.size());
    double cr=0.5;
    double y=r.nextDouble();
    if(y>cr){
        return ;
    }
    else{
        for(int i=0;i<len_of_crossover;i++){
           int x= children.get(0).chromosome.get(i);
            children.get(0).chromosome.set(i,children.get(1).chromosome.get(i));
            children.get(1).chromosome.set(i,x);
        }
    }

}
public void mutation (){
    Random r=new Random();
    double pm=0.02;
    for(int j=0;j<2;j++){
    for(int i=0;i<children.get(0).chromosome.size();i++){
        double y=r.nextDouble();
        if(y<pm){continue;}
         else if(children.get(j).chromosome.get(i)==0){
            children.get(j).chromosome.set(i,1);
        }
        else {
             children.get(j).chromosome.set(i, 0);
             }
    }
    }
}
public void replacement(){

    for(int i=0;i<children.size();i++) {
        int w = 0;
        children.get(i).fitness_function=0;
        for (int j = 0; j < children.get(i).chromosome.size(); j++) {
            children.get(i).fitness_function += children.get(i).value.get(j) * children.get(i).chromosome.get(j);
            if (children.get(i).chromosome.get(j) != 0) {
                w += children.get(i).weight.get(j);
            }


        }
        if (w > knapsack_weight) {
            children.get(i).fitness_function = 1 / children.get(i).fitness_function;

        }}
        if (population.get(index_first_child).fitness_function <= children.get(0).fitness_function) {
            population.set(index_first_child, children.get(0));
        }
        if (population.get(index_second_child).fitness_function <= children.get(1).fitness_function) {
            population.set(index_second_child, children.get(1));
        }

    children.clear();
}
   public void show_chromosome() {
    int fitness=0;
    int fitness2=0;
       ArrayList<Double>arr1=new ArrayList<>();
       ArrayList<Double>arr2=new ArrayList<>();

       for(int i=0;i<population.get(index_first_child).chromosome.size();i++) {
         fitness += population.get(index_first_child).chromosome.get(i) * population.get(index_first_child).value.get(i);
         if(population.get(index_first_child).chromosome.get(i)==1) {
             arr1.add(population.get(index_first_child).weight.get(i));
             arr1.add(population.get(index_first_child).value.get(i));
         }
         fitness2+=population.get(index_second_child).chromosome.get(i) * population.get(index_second_child).value.get(i);
           if(population.get(index_second_child).chromosome.get(i)==1) {
               arr2.add(population.get(index_second_child).weight.get(i));
               arr2.add(population.get(index_second_child).value.get(i));
           }
       }
       if(fitness>fitness2){

           System.out.println(fitness);

           for(int i =0;i<arr1.size();i+=2){
               System.out.println(arr1.get(i)+"       "+arr1.get(i+1));
           }

       }
       else {
           System.out.println(fitness2);
           for(int i =0;i<arr2.size();i+=2){

               System.out.println(arr2.get(i)+"     "+arr2.get(i+1));
           }

       }



       }


    public static void main(String[] args) {

        int t,noItems,sizeOfKnapsack;
        ArrayList<Double>w= new ArrayList<Double>();
        ArrayList<Double>v=new ArrayList<Double>();
        Scanner s= new Scanner(System.in);
        t= s.nextInt();
        for(int test =0 ; test< t ; test++)
        {
            noItems=s.nextInt();
            sizeOfKnapsack=s.nextInt();
            for (int j=0 ; j<noItems ; j++)
            {
                double weight,value;
                weight=s.nextInt();
                value=s.nextInt();
                w.add(weight);
                v.add(value);

            }
            int popSize=100;
            int max_generation=s.nextInt();
            population pop = new population(noItems,popSize,w,v,sizeOfKnapsack);

            for(int i=0;i<max_generation;i++) {
                pop.fitness_calc();

                pop.selection();
                pop.crossover();
                pop.mutation();
                pop.replacement();
                System.out.print("Case  "+(test+1)+"  :  ");
                pop.show_chromosome();
            }

        }

   }


}
