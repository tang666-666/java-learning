package tang.interface4;

public class Test {
    public static void main(String[] args) {
        B b=new B();
        b.go();
        A.show();
    }
}

class B implements A{

}
