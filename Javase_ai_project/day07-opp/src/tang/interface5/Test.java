package tang.interface5;

public class Test {
    public static void main(String[] args) {
        Dog d=new Dog();
        d.go();
    }
}

//4.一个类实现了多个接口，如果多个接口中存在同名的默认方法，可以不冲突，这个类重新该方法即可
interface A3{
    default void show(){
        System.out.println("接口中的A3的show方法");
    }
}

interface B3{
    default void show(){
        System.out.println("接口中的B3的show方法");
    }
}

class Dog2 implements A3,B3{

    @Override
    public void show() {
        A3.super.show();
        B3.super.show();
    }
}

//3.一个类继承了父类，又同时实现了接口，如果父亲中和接口中有同名的默认方法，实现类会优先用父类的
interface A2{
    default void show(){
        System.out.println("接口的A2");
    }
}

class Animal{
    public void show(){
        System.out.println("父类的show");
    }
}

class Dog extends Animal implements A2{
    public void go(){
        show();
        A2.super.show();
    }
}

//2.一个接口继承多个接口，如果多个接口中存在方法签名冲突，则此时不支持多继承，也不支持多实现
interface A1{
    void show1();
}

interface B1{
    void show1();
}

interface C1 extends A1,B1{
}

class D1 implements A1,B1{
    @Override
    public void show1() {

    }
}

//1.接口与接口可以多继承：一个接口可以同时继承多个接口
//类与类
//类与接口
//接口与接口
interface A{
    void show1();
}

interface B{
    void show2();
}

interface C extends B,A{
    void show3();
}

class D implements C{

    @Override
    public void show3() {

    }

    @Override
    public void show1() {

    }

    @Override
    public void show2() {

    }
}