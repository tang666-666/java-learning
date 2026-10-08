package tang.demo6collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CollectionTraversalDemo3 {
    public static void main(String[] args) {
        Collection<String> names=new ArrayList<>();
        names.add("汤宇轩");
        names.add("张三");
        names.add("李四");
        names.add("王二");
        System.out.println(names);

        //1.得到这个集合的迭代器对象
        Iterator<String> it=names.iterator();
//        System.out.println(it.next());
//        System.out.println(it.next());
//        System.out.println(it.next());
//        System.out.println(it.next());

        //2.使用一个while循环来遍历
        while(it.hasNext()){
            String name=it.next();
            System.out.println(name);
        }
    }
}
