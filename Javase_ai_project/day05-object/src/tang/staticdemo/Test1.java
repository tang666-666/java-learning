package tang.staticdemo;

public class Test1 {
    public static void main(String[] args){

//        1.类名.静态变量
        Student.name="张三";
        System.out.println(Student.name);

//        2.对象名.静态变量（不推荐）
        Student s1 =new Student();
        s1.name="李四";


        Student s2 =new Student();
        s2.name="王二";

        System.out.println(s1.name);
        System.out.println(Student.name);

//        3.对象名.实例变量
        s1.age=23;
        s2.age=18;
        System.out.println(s1.age);
    }
}
