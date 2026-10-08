package tang.demo2genericity;

public class GenericDemo2 {
    public static void main(String[] args) {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("hello");
        list.add("world");
//        list.add(500)
        list.add("Java");
        list.remove("hello");
        System.out.println(list);
    }
}
