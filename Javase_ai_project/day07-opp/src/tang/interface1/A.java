package tang.interface1;
//接口：使用interface关键字定义的
public interface A {
    //jdk8之前只能写成员变量（常量）+成员方法（抽象方法）
    //1.常量：接口中定义可以省略public static final
    public static final String NAME="汤宇轩";

    //2.抽象方法：接口中定义抽象方法可以省略public abstract
    public abstract void run();
}
