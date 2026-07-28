package tang.pocket;

public class method_3 {
    public static void main(String[] args) {
//        掌握return的运用
        div(10, 10);
        div(10, 0);
    }
    public static void div(int a,int b) {
        if (b == 0) {
            System.out.println("除数不能为0");
            return;//提前结束程序
        }
        System.out.println(a / b);
    }
}
