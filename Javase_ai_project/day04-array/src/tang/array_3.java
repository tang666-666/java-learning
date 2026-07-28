package tang;

import java.util.Scanner;

public class array_3 {
    public static void main(String[] args){
        findMax();
    }

    public static void findMax(){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[] array=new int[n];
        for(int i=0;i<n;i++){
            array[i]=sc.nextInt();
        }
        int max=array[0];
        int index=0;
        for(int i=1;i<n;i++){
            if(array[i]>max){
                max=array[i];
                index=i;
            }
        }
        System.out.printf("%d %d",max,index);
    }
}