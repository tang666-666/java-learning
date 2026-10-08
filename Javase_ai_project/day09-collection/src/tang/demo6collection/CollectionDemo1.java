package tang.demo6collection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionDemo1 {
    public static void main(String[] args) {
        //1.List家族的集合,添加的元素是有序、可重复、有索引
        List<String> list=new ArrayList<>();
        list.add("Java");
        list.add("Python");
        list.add("C++");
        list.add("Java");
        System.out.println(list);
        String s=list.get(0);
        System.out.println(s);

        //2.Set家族的集合,添加的元素是无需、不重复、无索引
        Set<String> set=new HashSet<>();
        set.add("C#");
        set.add("Java");
        set.add("Python");
        set.add("C++");
        set.add("Java");
        System.out.println(set);
    }
}
