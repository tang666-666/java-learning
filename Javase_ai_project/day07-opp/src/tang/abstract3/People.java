package tang.abstract3;

public abstract class People {
    //1.模板方法设计模式
    public final void write(){
        System.out.println("\t\t\t《我的爸爸》");
        System.out.println("\t我的爸爸是个好人");
        //2.模板方法知道子类一定要写这个正文，但是每个子类写的信息是不同的，父类就定义一个抽象方法，具体的方法让子类来重写
        writeMain();
        System.out.println("\t我的爸爸很好");
    }

    public abstract void writeMain();
}
