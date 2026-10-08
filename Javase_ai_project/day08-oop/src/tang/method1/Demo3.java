package tang.method1;

import java.util.Arrays;
import java.util.Comparator;

public class Demo3 {
    public static void main(String[] args) {
        String[] names={"Tom","Jerry","Mike","angele","Jack","Rose","Alice"};

        //忽略首字母的大小进行排序
//        Arrays.sort(names, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o1.compareToIgnoreCase(o2);
//            }
//        });

//        Arrays.sort(names, (o1,o2) ->o1.compareToIgnoreCase(o2));
        Arrays.sort(names,String::compareToIgnoreCase);

        System.out.println(Arrays.toString(names));
    }
}
