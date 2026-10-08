package tang.demo2genericity;

import java.util.ArrayList;

public class GenericDemo1 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Java");
        list.add("Python");
//        list.add(23);
//        list.add(true);
//        list.add(new Object());

        for(int i=0;i<list.size();i++){
//            Object rs=list.get(i);
//            String s=(String)rs;
//            System.out.println(s);
            String s = list.get(i);
            System.out.println(s);
        }
    }
}
