package tang.printout;

import java.util.Scanner;

public class practice {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入年龄：");
        int age=sc.nextInt();
        System.out.println("请输入性别：");
        String sex=sc.next();
        System.out.println("请输入身高：");
        float height=sc.nextFloat();
        System.out.println("请输入体重：");
        float weight=sc.nextFloat();
        System.out.println(BMI(weight,height));
        System.out.println(BMR(age,weight,height,sex));
    }

    public static float BMI(float weight,float height){
        return weight/(height*height);
    }

    public static double BMR(int age,float weight,float height,String sex){
        double BMR;
        if("男".equals(sex)){
            BMR=13.7*weight+5*height-6.8*age+88.6;
        }
        else{
            BMR=9.6*weight+1.8*height-4.7*age+447.6;
        }
        return BMR;
    }

}
