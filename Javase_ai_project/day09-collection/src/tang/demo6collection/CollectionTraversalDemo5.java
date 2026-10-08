package tang.demo6collection;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionTraversalDemo5 {
    public static void main(String[] args) {
        Collection<String> names = new ArrayList<>();
        names.add("汤宇轩");
        names.add("张三");
        names.add("李四");
        names.add("王二");

//        names.forEach(new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println(s);
//            }
//        });

//        names.forEach(s -> System.out.println(s));

        names.forEach(System.out::println);
    }
}
