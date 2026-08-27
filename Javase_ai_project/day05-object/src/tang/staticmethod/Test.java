package tang.staticmethod;

public class Test {
    public static void main(String[] args){
//        1.类名.静态方法
        Student.printHelloWorld();

//        2.对象名.实例方法
        Student s1=new Student();
        s1.setScore(90);
        s1.printPass();

//        规范：如果这个方法只是为了做一个功能且不需要直接访问对象的数据，直接定义成静态方法
//        如果这个方法是对象的行为，需访问对象的数据，直接定义成实例方法
    }
}
