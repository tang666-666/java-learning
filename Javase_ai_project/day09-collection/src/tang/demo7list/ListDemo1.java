package tang.demo7list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListDemo1 {
    public static void main(String[] args) {
        List<String> list=new ArrayList<>();

        list.add("张三");
        list.add("李四");
        list.add("王五");
        list.add("赵六");
        System.out.println(list);

        //插入数据
        list.add(2,"汤宇轩");
        System.out.println(list);

        //删除数据
        list.remove(1);
        System.out.println(list);

        //修改数据
        list.set(2,"轩轩");
        System.out.println(list);

        System.out.println(list.get(1));

        //1.for循环
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        //2.迭代器
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String name=iterator.next();
            System.out.println(iterator.next());
        }

        //3.增强for
        for(String s:list){
            System.out.println(s);
        }

        //4.Lambda
        list.forEach(System.out::println);
    }
}
