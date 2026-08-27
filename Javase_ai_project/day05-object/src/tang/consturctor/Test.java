package tang.consturctor;

public class Test {
    public static void main(String[] args){
//        创建对象时，对象会立即自动调用构造器

        Student s1 = new Student();
        Student s2=new Student("汤宇轩");

        Student t1=new Student();
        t1.name="汤宇轩";
        t1.age=18;
        t1.sex="男";
        System.out.println(t1.name);
        System.out.println(t1.age);
        System.out.println(t1.sex);

        Student t2=new Student("汤宇轩",18,"男");
        System.out.println(t2.name);
        System.out.println(t2.age);
        System.out.println(t2.sex);
    }
}
