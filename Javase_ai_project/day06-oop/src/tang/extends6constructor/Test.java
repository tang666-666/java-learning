package tang.extends6constructor;

public class Test {
    public static void main(String[] args) {
        Zi zi=new Zi();
    }
}

class Zi extends Fu{
    public Zi(){
        //super();//默认存在，写不写都有
        System.out.println("子类无参构造器");
    }
}

class Fu{
    public Fu(){
        System.out.println("父类无参构造器");
    }

    public Fu(int a){
        System.out.println("父类有参构造器");
    }
}
