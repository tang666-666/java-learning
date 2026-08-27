package tang.polymophsm3;

public class Test {
    public static void main(String[] args) {
//      1.多态的好处：右边对象是解耦合的，更便于拓展维护
        Animal a1 = new Tortoise();
        a1.run();
        //a1.shrinkHead;报错，多态下不能调用子类的独有功能
//      2.父类类型的变量作为参数，可以接受一个子类对象

//      强制类型转换：
        Tortoise t1 = (Tortoise) a1;
        t1.shrinkHead();
//      有继承关系就可以强制转换，编译阶段不会报错
//      运行时可能会出现类型转换异常

        System.out.println("======================");

        Wolf w=new Wolf();
        go(w);

        Tortoise t=new Tortoise();
        go(t);
    }

    public static void go(Animal a){
        System.out.println("开始");
        a.run();
        //a1.shrinkHead;报错，多态下不能调用子类的独有功能

//java建议：使用instanceof关键字，判断当前对象的真实类型，再进行强转
        if(a instanceof Wolf){
            Wolf w1=(Wolf) a;
            w1.eatSheep();
        } else if (a instanceof Tortoise) {
            Tortoise t1=(Tortoise) a;
            t1.shrinkHead();
        }
    }
}
