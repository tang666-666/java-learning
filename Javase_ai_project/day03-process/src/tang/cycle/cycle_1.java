package tang.cycle;

import java.util.Scanner;

public class cycle_1 {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int a=sc.nextInt();
        int b=sc.nextInt();
        specialNum(a,b);
    }

    public static void specialNum(int a,int b){
        int i;
        for(i=a;i<=b;i++){
            if(judge(i)) System.out.printf("%d ",i);
        }
    }

    public static boolean judge(int num){
        int a=num/100;
        int b=num/10%10;
        int c=num%10;
        if(a*a*a+b*b*b+c*c*c==num){
            return true;
        }
        return false;
    }
}
