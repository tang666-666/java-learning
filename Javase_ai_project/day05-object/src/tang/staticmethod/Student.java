package tang.staticmethod;

public class Student {
    private double score;

//    静态方法：有static修饰，属于类持有
    public static void printHelloWorld(){
        System.out.println("Hello world");
        System.out.println("Hello world");
        System.out.println("Hello world");
    }

//    实例方法：没有static修饰，属于对象持有
    public void printPass(){
        System.out.println(score>=60?"通过" : "挂科");
    }

    public void setScore(double score) {
        this.score = score;
    }
}
