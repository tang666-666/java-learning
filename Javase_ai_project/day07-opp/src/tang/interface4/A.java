package tang.interface4;

public interface A {
    //1.默认方法（实例方法）：使用default修饰，默认会被加上public修饰，只能使用接口的实现类对象调用
    default void go(){
        System.out.println("go方法执行");
        run();
    }

    //2.私有方法：必须用private修饰，使用接口中的其他实例方法来调用它
    private void run(){
        System.out.println("run方法执行");
    }

    //3.类方法（静态方法）：使用static修饰，默认会被加上public修饰，只能用接口名来调用
    public static void show(){
        System.out.println("show方法执行");
    }
}
