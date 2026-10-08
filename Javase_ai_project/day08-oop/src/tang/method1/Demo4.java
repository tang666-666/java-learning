package tang.method1;

public class Demo4 {
    public static void main(String[] args) {

//        CarFactory cf=new CarFactory() {
//            @Override
//            public Car getCar(String name) {
//                return new Car(name);
//            }
//        };

//        CarFactory cf=name-> new Car(name);

        CarFactory cf=Car::new;

        Car c1=cf.getCar("奔驰");
        System.out.println(c1.getName());
    }
}

class Car{
    private String name;

    public Car(){
    }

    public Car(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

@FunctionalInterface
interface CarFactory{
    Car getCar(String name);
}
