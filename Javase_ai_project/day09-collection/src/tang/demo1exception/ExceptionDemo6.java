package tang.demo1exception;

import java.util.Scanner;

public class ExceptionDemo6 {
    public static void main(String[] args) {
        //2.捕获异常对象，尝试重新修复
        //接收用户的定价
        System.out.println("程序开始");
        double price= 0;
        while (true) {
            try {
                price = userInputPrice();
                break;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("输入的数据有误");
            }
        }
        System.out.println("商品定价：" + price);
        System.out.println("程序结束");
    }

    public static double userInputPrice(){
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入商品定价：");
        double price=sc.nextDouble();
        return price;
    }
}
