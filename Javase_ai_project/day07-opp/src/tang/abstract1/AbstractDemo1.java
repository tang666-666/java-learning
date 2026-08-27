package tang.abstract1;

public class AbstractDemo1 {
    public static void main(String[] args) {
        //抽象类特点：不能创建对象
        //抽象类的使命就是被子类继承
        B b=new B();
        b.setName("汤宇轩");
        b.setAge(18);
    }
}
