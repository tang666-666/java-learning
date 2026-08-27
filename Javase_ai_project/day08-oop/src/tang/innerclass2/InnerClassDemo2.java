package tang.innerclass2;

public class InnerClassDemo2 {
    public static void main(String[] args) {
        Outer.Inner oi=new Outer.Inner();
        oi.show();
        //1.静态内部类中可以直接访问外部类的静态成员
        //2.静态内部类中不可以直接访问外部类的实例成员
    }
}
