package tang.demo1exception;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExceptionDemo1 {
    public static void main(String[] args) {
        show();
        try {
            //监视代码，出现异常，会被catch拦截住这个异常
            show2();
        } catch (Exception e) {
            e.printStackTrace();//打印异常信息
        }
    }

    //编译时异常
    public static void show2() throws Exception {
        System.out.println("程序开始");
        String str="2026-09-22 11:12:13";
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=sdf.parse(str);
        System.out.println(date);
        System.out.println("程序结束");
    }

    //运行时异常
    public static void show(){
        System.out.println("程序开始");
        int[] arr={1,2,3};
        //System.out.println(arr[3]);
        //System.out.println(10/0);
        //空指针异常
        //String str=null;
        //System.out.println(str);
        //System.out.println(str.length());
        System.out.println("程序结束");
    }
}
