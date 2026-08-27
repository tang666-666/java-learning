package tang.finalDemo;

public class finalDemo1 {
//  成员变量
//  final修饰静态变量，这个变量今后被称为常量，可以记住一个固定值，并且程序中不能修改了，通常这个值作为系统的配置信息
//  常量的名称，建议全部大写，多个单词用下划线链接
    private static final String SCHOOL_NAME="汤宇轩";

//  实例变量(一般没有意义）
    private final String name="222";

    public static void main(String[] args) {
//      3.final修饰变量，变量有且只能被赋值一次（成员变量、局部变量）
//      局部变量
        final double rate=3.14;
//      rate=3.15;
        buy(8.0);

//      SCHOOL_NAME="111";

        final int a=20;

        final int [] arr={11,22,34,44};
        arr[2]=99;
    }

    public static void buy(final double a){
//      a=1;
        System.out.println(a);
    }
}

//      1.final修饰类，该类无法被继承
final class A{
    private String name;
}

//class B extends A{}

//      2.final修饰方法，方法不能被重写
class C{
    public final void show(){
        System.out.println("show方法被调用");
    }
}

class D extends C{
//    @Override
//    public void show(){
//        System.out.println("show方法被调用");
//    }
}
