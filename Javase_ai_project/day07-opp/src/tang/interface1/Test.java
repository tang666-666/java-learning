package tang.interface1;

public class Test {
    public static void main(String[] args) {
        System.out.println(A.NAME);
        //接口不能创建对象
        //接口是用来被类实现的
        C c=new C();
        c.play();
        c.run();
    }
}
// 实现类实现多个接口，必须重写完全部接口的全部抽象方法，否则这个类必须定义成抽象类
class C implements B,A{

    @Override
    public void run() {
        System.out.println("跑");
    }

    @Override
    public void play() {
        System.out.println("玩");
    }
}
