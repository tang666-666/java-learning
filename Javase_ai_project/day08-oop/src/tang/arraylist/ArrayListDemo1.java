package tang.arraylist;

import java.util.ArrayList;

public class ArrayListDemo1 {
    public static void main(String[] args) {
        //1.创建arraylist对象
        //增
        ArrayList <String> list=new ArrayList<>();
        list.add("java");
        list.add("python");
        list.add("C");
        list.add("C++");
        list.add("汤宇轩");
        System.out.println(list);
        System.out.println(list.get(0));
        System.out.println(list.get(1));

        //查找
        for(int i=0;i<list.size();i++){
            String s=list.get(i);
            System.out.println(s);
        }

        //删除
        list.remove(3);
        System.out.println(list);
        list.remove("C");
        System.out.println(list);

        //修改
        list.set(0,"汤宇轩");
        System.out.println(list);
    }
}
