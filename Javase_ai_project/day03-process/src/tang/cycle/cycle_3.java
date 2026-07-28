package tang.cycle;

import java.util.Scanner;

public class cycle_3 {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int year=sc.nextInt();
        int count=0;
        if(year<=2000||year>2100){
            System.out.printf("Invalid year!");
        }
        else{
            for(int i=2001;i<=year;i++){
                if((i%4==0&&i%100!=0)||(i%400==0)) {
                    count++;
                    System.out.printf("%d\n", i);
                }
            }
            if(count==0){
                System.out.printf("None");
            }
        }
    }
}
