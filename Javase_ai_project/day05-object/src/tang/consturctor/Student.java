package tang.consturctor;

public class Student {
    String name;
    int age;
    String sex;
// 构造器：是一种特殊方法，不能写返回值类型，名称必须是类名，就是构造器
    public Student(){
        System.out.println("无参构造器执行");
    }

    public Student(String name){
        System.out.println("有参构造器执行:" + name);
    }

    public Student(String n,int a,String s){
        name=n;
        age=a;
        sex=s;
    }
}
