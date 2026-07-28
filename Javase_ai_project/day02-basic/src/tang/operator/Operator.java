package tang.operator;

public class Operator {
    public static void main(String[] args){
        print1(10,2);
        print2();
    }
    public static void print1(int a,int b){
        System.out.println(a+b);
        System.out.println(a-b);
        System.out.println(a*b);
        System.out.println(a/b);
        System.out.println(a%b);
    }
    public static void print2(){
        int a=5;
        System.out.println("abc"+a);
        System.out.println(a+5);
        System.out.println("itheima"+a+'a');
        System.out.println(a+'a'+"itheima");

    }
}
