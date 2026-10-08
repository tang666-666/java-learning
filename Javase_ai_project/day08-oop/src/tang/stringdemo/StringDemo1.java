package tang.stringdemo;

import java.util.Scanner;

public class StringDemo1 {
    public static void main(String[] args) {
        //1.直接""创建字符串对象，并进行封装
        String s1="hello,汤宇轩";
        System.out.println(s1);
        System.out.println(s1.length());

        //2.通过构造器初始化对象
        String s2=new String();
        System.out.println(s2);

        String s3=new String("hello，汤宇轩");
        System.out.println(s3);

        char[] chars={'h','e','l','l','o','，','汤','宇','轩'};
        String s4=new String(chars);
        System.out.println(s4);

        byte[] bytes={96,64,54,53,5,3,5,35,3,5};
        String s5=new String(bytes);
        System.out.println(s5);

        System.out.println("=====================");

        //只是以"..."方式写出的字符串对象，会存储到字符串常量池，且相同内容的字符串只存储一份
        String t1="abc";
        String t2="abc";
        System.out.println(t1 == t2);

        String t3=new String("abc");
        String t4=new String("abc");
        System.out.println(t3 == t4);

        System.out.println("============================");
        //调用String提供的操作字符串数据的方法
        //简单版登录
        String loginName="admin";
        System.out.println("请输入您的登录名称");
        Scanner sc=new Scanner(System.in);
        String name=sc.next();

        //字符串对象内容的比较，不要用==进行比较
        if(loginName.equals(name)){
            System.out.println("登录成功");
        }
        else{
            System.out.println("登录失败");
        }

        System.out.println("==========================");
        System.out.println("请你输入手机号登录:");
        String phone=sc.next();
        String newPhone=phone.substring(0,3) + "****" + phone.substring(7);
        System.out.println(newPhone);
    }
}
