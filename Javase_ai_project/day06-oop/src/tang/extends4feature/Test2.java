package tang.extends4feature;

public class Test2 {
    public static void main(String[] args) {
        Zi zi =new Zi();
        zi.show();
    }
}

class Fu{
    String name = "fu的name";

    public void run(){
        System.out.println("fu类的run方法");
    }
}

class Zi extends Fu{

    String name="zi的name";

    public void show(){
        String name = "show的name";
        System.out.println(name);
        System.out.println(this.name);
        System.out.println(super.name);

        run();
        super.run();
    }
    public void run(){
        System.out.println("zi类的run方法");
    }
}