package tang.demo4genericity;

import java.util.ArrayList;

public class GenericDemo5 {
    public static void main(String[] args) {
        ArrayList<Xiaomi> xiaomis=new ArrayList<>();
        xiaomis.add(new Xiaomi());
        xiaomis.add(new Xiaomi());
        xiaomis.add(new Xiaomi());
        go(xiaomis);

        ArrayList<BYD> byds=new ArrayList<>();
        byds.add(new BYD());
        byds.add(new BYD());
        byds.add(new BYD());
        go(byds);

//        ArrayList<Dog> dogs=new ArrayList<>();
//        dogs.add(new Dog());
//        dogs.add(new Dog());
//        dogs.add(new Dog());
//        go(dogs);
    }

    //虽然Xiaomi和BYD是Car的子类，但是ArrayList<Xiaomi>、ArrayList<BYD>和ArrayList<Car>没有任何关系
    public static void go(ArrayList<? extends Car> cars){

    }
}
