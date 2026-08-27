package tang.singleinstance;
//饿汉式单例
public class A {
    //2. 定义一个静态变量，用于基本本类的一个唯一对象
    private static A a=new A();

    //1.私有化构造器：确保单例类对外不能创建太多对象，单例才有可能
    private A(){
    }

    //3.提供一个公开的静态方法，返回这个类的唯一对象
    public static A getInstance() {
        return a;
    }
}
