package tang.demo1exception;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExceptionDemo5 {
    public static void main(String[] args) {
        //1.底层异常都抛出去给最外层调用者，最外层捕获异常，记录异常，响应合适信息给用户看
        System.out.println("程序开始");
        try {
            show1();
            System.out.println("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("操作失败");
        }
        System.out.println("程序结束");
    }
    public static void show1() throws Exception {
        String str="2026-09-22 11:12:13";
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=sdf.parse(str);
        System.out.println(date);

        InputStream is=new FileInputStream("D:/meinv.png");
    }
}
