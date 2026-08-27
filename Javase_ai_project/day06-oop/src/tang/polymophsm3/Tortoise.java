package tang.polymophsm3;

public class Tortoise extends Animal {
    String name="乌龟";
    @Override
    public void run(){
        System.out.println("乌龟跑的很慢");
    }

    public void shrinkHead(){
        System.out.println("乌龟缩头了");
    }
}
