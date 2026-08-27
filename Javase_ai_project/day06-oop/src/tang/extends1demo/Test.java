package tang.extends1demo;

public class Test {
    public static void main(String[] args) {
//        子类可以继承父类的非私有成员
//        子类对象其实是由子类和父类多张设计图共同创建出来的对象，所以子类是完整的
        Teacher t=new Teacher();
        t.setName("汤宇轩");
        t.setSkill("java");
        t.setSex('男');

    }
}
