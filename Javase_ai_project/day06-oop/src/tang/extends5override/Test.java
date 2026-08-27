package tang.extends5override;

public class Test {
    public static void main(String[] args) {
        Cat cat=new Cat();
        cat.cry();
    }
}

class Cat extends Animal{
//    方法重写：方法名称、形参列表必须一样
//    重写的规范：声明不变，重新实现
    @Override//方法重写的校验注解：要求方法名称和形参列表必须与被重写方法一致，否则报错
    public void cry(){
        System.out.println("喵喵喵");
    }
}

class Animal{
    public void cry(){
        System.out.println("动物会叫");
    }
}
