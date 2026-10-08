package tang.demo6collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class CollectionDemo2 {
    public static void main(String[] args) {
        Collection<String> list=new ArrayList<>();

        //添加元素
        list.add("C#");
        list.add("Java");
        list.add("Python");
        System.out.println(list);

        //获取集合元素的个数
        System.out.println(list.size());

        //删除集合元素
        list.remove("Java");
        System.out.println(list);

        //判断集合是否为空
        System.out.println(list.isEmpty());

//        //清空集合
//        list.clear();
//        System.out.println(list);

        //判断集合中是否存在某个数据
        System.out.println(list.contains("Java"));

        //把集合转换成数组
        Object[] arr=list.toArray();
        System.out.println(Arrays.toString(arr));

        //把集合转换成字符串数组
        String[] arr2=list.toArray(String[]::new);
        System.out.println(Arrays.toString(arr2));
    }
}
