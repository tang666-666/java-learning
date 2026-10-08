package tang.lambda;

public class LambdaDemo1 {
    public static void main(String[] args) {
//        Swim a1=new Swim() {
//            @Override
//            public void swimming() {
//                System.out.println("学生游泳");
//            }
//        };

        Swim a1=()-> {
            System.out.println("学生游泳");
        };
        a1.swimming();
    }
}

//函数式接口:有且仅有一个抽象方法
@FunctionalInterface
interface Swim{
    void swimming();
}
