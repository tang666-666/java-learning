package tang.enumdemo;

public class Test {
    public static void main(String[] args) {
        A a1=A.X;
        System.out.println(a1);

        A a2=A.Y;
        System.out.println(a2);

        System.out.println(a1.name());
        System.out.println(a2.name());

        //索引
        System.out.println(a1.ordinal());
        System.out.println(a2.ordinal());
    }
}
