package tang;

import java.util.Scanner;

public class array_2 {
    public static void main(String[] args){
        inputscore();
    }

    public static void inputscore(){
        double [] score=new double[10];
        Scanner sc=new Scanner(System.in);
        for(int i=0;i<10;i++){
            score[i]=sc.nextDouble();
        }
        for(int i=0;i<10;i++){
            System.out.println(score[i]);
        }
    }
}
