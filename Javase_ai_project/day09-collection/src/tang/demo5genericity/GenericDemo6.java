package tang.demo5genericity;

import java.util.ArrayList;

public class GenericDemo6 {
    public static void main(String[] args) {
//        ArrayList<int> list=new ArrayList<>();
        //泛型擦除：泛型工作在编译阶段，等编译后泛型就没用了，所以泛型在编译后都会被擦除。所有类型会恢复成Object类型

        //手动包装
        Integer it1=Integer.valueOf(100);
        Integer it2=Integer.valueOf(100);
        System.out.println(it1==it2);

        //自动装箱
        Integer it11=100;
        Integer it22=100;
        System.out.println(it11==it22);

        //自动拆箱
        int i = it11;
        System.out.println(i);

        ArrayList<Integer> list=new ArrayList<>();
        list.add(123);//自动装箱
        list.add(120);//自动装箱

        int rs= list.get(1);//自动拆箱

        System.out.println("=========================================");
        //1.把基本类型的数据转换成字符串类型
        int j=23;
        String rs1=Integer.toString(j);//"23"
        System.out.println(rs1+1);

        Integer i2=j;
        String rs2=i2.toString();
        System.out.println(rs2+1);

        String rs3=j+"";
        System.out.println(rs3+1);

        System.out.println("=========================================");
        //2.把字符串类型的数值转换成数值本身对应的真实数据类型
        String str="98";
//        int i1=Integer.parseInt(str);
        int i1=Integer.valueOf(str);
        System.out.println(i1+2);

        String str2="98.8";
//        double d1=Double.parseDouble(str2);
        double d1=Double.valueOf(str2);
        System.out.println(d1+2);
    }
}
