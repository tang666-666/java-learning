package tang.pocket;

public class method_2 {
    public static void main(String[] args){
//        掌握方法重载
        print(1);
        print("hello");
        print(3.14);
    }
    public static void print(int a){
        System.out.println(a);
    }
    public static void print(String str){
        System.out.println(str);
    }
    public static void print(double b){
        System.out.println(b);
    }
}
