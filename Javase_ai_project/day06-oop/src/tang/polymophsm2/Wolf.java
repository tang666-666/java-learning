package tang.polymophsm2;

public class Wolf extends Animal {
    String name="狼";
    @Override
    public void run(){
        System.out.println("狼跑的很快");
    }

    public void eatSheep(){
        System.out.println("狼吃羊");
    }
}
