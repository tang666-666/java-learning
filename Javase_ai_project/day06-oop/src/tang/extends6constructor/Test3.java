package tang.extends6constructor;

public class Test3 {
    public static void main(String[] args) {
        Student s1 = new Student("张三",18,'男',"清华大学");
        System.out.println(s1);
        Student s2 = new Student("李四",20,'男');
        System.out.println(s2);
    }
}
