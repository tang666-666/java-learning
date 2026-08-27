package tang.innerclass3;

public class Test {
    public static void main(String[] args) {
        //匿名内部类实际上是有名字：外部类名$编号.class
        Animal a=new Animal() {
            @Override
            public void cry() {
                System.out.println("喵喵喵");
            }
        };
        a.cry();
    }
}

class Cat extends Animal{

    @Override
     public void cry() {
        System.out.println("喵喵喵");
    }
}
