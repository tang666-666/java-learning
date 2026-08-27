package tang.abstract1;

public abstract class A {
    //抽象方法，没有方法体，只有方法声明
    private String name;
    private int age;

    public A(){
    }

    public A(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public abstract void show();

    public void show1(){
    }

}
