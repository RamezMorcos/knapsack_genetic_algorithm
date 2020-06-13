import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class chromosome {

    ArrayList<Double>value=new ArrayList<>();
    ArrayList<Double>weight=new ArrayList<>();
    ArrayList<Integer>chromosome=new ArrayList<>();
    double fitness_function=0;
    double lower_prob;
    double higher_prob;


    chromosome(int items ,ArrayList<Double>w,ArrayList<Double>v) {
        for (int i = 0; i < items; i++) {
            weight.add(w.get(i));
            value.add(v.get(i));
            Random r=new Random();
            int x=r.nextInt(2);
            chromosome.add(x);
        }
    }



    }

