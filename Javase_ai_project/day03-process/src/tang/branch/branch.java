package tang.branch;

import java.util.Scanner;

public class branch {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int num=sc.nextInt();
        System.out.println(judge(num));
    }

    public static boolean judge(int num){
        int a,b,c;
        a=num%10;
        b=num/10%10;
        c=num/100;
        if(a*a*a+b*b*b+c*c*c==num){
            return true;
        }
        else{
            return false;
        }
    }
}
