package tang.code;

import java.sql.Array;
import java.util.Arrays;

public class CodeDemo1 {
    public static String schoolName;
    public static String[] cards=new String[54];
    //静态代码块：有static修饰，属于类，与类一起优先加载，自动执行一次
    static {
        System.out.println("静态代码块执行");
        schoolName="汤宇轩";
        cards[0]="A";
        cards[1]="B";
        cards[2]="C";
    }

    public static void main(String[] args) {
        System.out.println("main方法执行了");
        System.out.println(schoolName);
        System.out.println(Arrays.toString(cards));
    }
}
