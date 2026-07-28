package tang.printout;
//1.导入包
import java.util.Scanner;

public class out {
    public static void main(String[] args){
        printUserInfo();
    }
    public static void printUserInfo(){
//        2.创建对象
        Scanner sc=new Scanner(System.in);
//        3.获取用户输入
        System.out.println("请输入用户名：");

        //让程序在这一行暂停，等到用户输入一个字符串，按下回车后，把名字交给变量username。
        String username=sc.next();

        System.out.println("请输入年龄：");

        //让程序在这一行暂停，等到用户输入一个整数，按下回车后，把名字交给变量age。
        int age=sc.nextInt();
//        4.打印
        System.out.println();
    }
}
