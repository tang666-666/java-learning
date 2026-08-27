package tang.innerclass3;

public class Test2 {
    public static void main(String[] args) {
        Swim s1=new Swim() {
            @Override
            public void swimming() {
                System.out.println("学生游泳");
            }
        };
        start(s1);

        start(new Swim() {
            @Override
            public void swimming() {
                System.out.println("老师游泳");
            }
        });
    }

    public static void start(Swim s){
        s.swimming();
    }
}

class Teacher implements Swim{

    @Override
    public void swimming() {
        System.out.println("老师可以游泳");
    }
}

class Students implements Swim{

    @Override
    public void swimming() {
        System.out.println("学生可以游泳");
    }
}

interface Swim{
    void swimming();
}
