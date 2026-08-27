package tang.polymophsm2;

public class Test {
    public static void main(String[] args) {
//      1.多态的好处：右边对象是解耦合的，更便于拓展维护
        Animal a1 = new Tortoise();
        a1.run();
        //a1.shrinkHead;报错，多态下不能调用子类的独有功能
//      2.父类类型的变量作为参数，可以接受一个子类对象
        Wolf w=new Wolf();
        go(w);

        Tortoise t=new Tortoise();
        go(t);
    }

    public static void go(Animal a){
        System.out.println("开始");
        a.run();
        //a1.shrinkHead;报错，多态下不能调用子类的独有功能
    }
}
