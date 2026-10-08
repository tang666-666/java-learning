package tang.demo6collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CollectionTraversalDemo6 {
    public static void main(String[] args) {
        System.out.println("==============================");
        ArrayList<String> list=new ArrayList<>();
        list.add("Java入门");
        list.add("宁夏枸杞");
        list.add("黑枸杞");
        list.add("人字拖");
        list.add("特级枸杞");
        list.add("枸杞子");
        list.add("西洋参");

        for(int i=0;i<list.size();i++){
            String name=list.get(i);
            if(name.contains("枸杞")){
                list.remove(name);
            }
        }
        System.out.println(list);
        System.out.println("==============================");

        ArrayList<String> list2=new ArrayList<>();
        list2.add("Java入门");
        list2.add("宁夏枸杞");
        list2.add("黑枸杞");
        list2.add("人字拖");
        list2.add("特级枸杞");
        list2.add("枸杞子");
        list2.add("西洋参");

        for(int i=0;i<list2.size();i++){
            String name=list2.get(i);
            if(name.contains("枸杞")){
                list2.remove(name);
                i--;//解决方法1：删除数据后做一步i--操作
            }
        }
        System.out.println(list2);
        System.out.println("==============================");

        ArrayList<String> list3=new ArrayList<>();
        list3.add("Java入门");
        list3.add("宁夏枸杞");
        list3.add("黑枸杞");
        list3.add("人字拖");
        list3.add("特级枸杞");
        list3.add("枸杞子");
        list3.add("西洋参");
        
        //解决方案2：倒着遍历并删除（前提是支持索引）
        
        for(int i=list3.size()-1;i>=0;i--){
            String name=list3.get(i);
            if(name.contains("枸杞")){
                list3.remove(name);
            }
        }
        System.out.println(list3);
        System.out.println("==============================");

        ArrayList<String> list4=new ArrayList<>();
        list4.add("Java入门");
        list4.add("宁夏枸杞");
        list4.add("黑枸杞");
        list4.add("人字拖");
        list4.add("特级枸杞");
        list4.add("枸杞子");
        list4.add("西洋参");

        //方案一：迭代器遍历并删除也存在并发修改异常问题
        //可以解决：使用迭代器自己的方法来删除
        Iterator<String> it = list4.iterator();
        while(it.hasNext()){
            String name=it.next();
            if(name.contains("枸杞")){
                it.remove();//用迭代器自己的解决方法
            }
        }
        System.out.println(list4);

        System.out.println("==============================");

        //方案二和三：用增强for还要Lambda（都没有办法解决并发修改异常问题）
        ArrayList<String> list5=new ArrayList<>();
        list5.add("Java入门");
        list5.add("宁夏枸杞");
        list5.add("黑枸杞");
        list5.add("人字拖");
        list5.add("特级枸杞");
        list5.add("枸杞子");
        list5.add("西洋参");

//        for(String s:list5){
//            if(s.contains("枸杞")){
//                list5.remove(s);
//            }
//        }

//        list5.forEach(s->{
//            if(s.contains("枸杞")){
//                list5.remove(s);
//            }
//        });
        System.out.println(list5);
    }
}
