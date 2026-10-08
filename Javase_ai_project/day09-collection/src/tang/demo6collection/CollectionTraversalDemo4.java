package tang.demo6collection;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionTraversalDemo4 {
    public static void main(String[] args) {
        Collection<String> names = new ArrayList<>();
        names.add("汤宇轩");
        names.add("张三");
        names.add("李四");
        names.add("王二");

        for(String name : names){
            System.out.println(name);
        }

        String[] arr={"汤宇轩","张三","李四","王二"};

        for(String name : arr){
            System.out.println(name);
        }
    }
}
