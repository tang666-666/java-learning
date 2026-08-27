package tang.innerclass;

import java.time.Period;

public class InnerClassDemo1 {
    public static void main(String[] args) {
        Outer.Inner oi=new Outer().new Inner();
        oi.show();
        //成员内部类访问外部类成员的特点（拓展）
        //1.成员内部类中可以直接访问外部类的静态成员,也可以直接访问外部类的实例成员
        // 2.成员内部类的实例方法中，可以直接拿到当前寄生的外部类对象：外部类名.this
        People.Heart heart=new People().new Heart();
        heart.show();
    }
}

class People{
    private int heartBeat=100;

    public class Heart{
        private int heartBeat=80;

        public void show(){
            int heartBeat=200;
            System.out.println(heartBeat);
            System.out.println(this.heartBeat);
            System.out.println(People.this.heartBeat);
        }
    }
}
