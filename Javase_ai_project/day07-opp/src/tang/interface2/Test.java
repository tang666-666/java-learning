package tang.interface2;

public class Test {
    public static void main(String[] args) {
        //弥补了类单继承的不足，一个类同时可以实现多个接口，使类的角色更多，功能更强大
        People p=new Student();
        Driver d=new Student();//多态
        BoyFriend b=new Student();

        //让程序可以面向接口编程，更利于程序的解耦合
        Driver a=new Student();

        BoyFriend c=new Student();
    }
}
interface Driver{}
interface BoyFriend{}

class People{}
class Student extends People implements Driver,BoyFriend{}

class Teacher implements Driver,BoyFriend{}
