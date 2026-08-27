package tang.demo;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        //加油站支付小程序
        //1.创建卡片类，以便创建金卡或银卡对象，封装车主的数据
        //2.定义一个卡片父类：Card,定义金卡和银卡的共同属性和方法
        //3.定义一个金卡类，继承Card类,金卡要重写方法
        //3.定义一个银卡类，继承Card类,金卡要重写方法
        //4.办一张金卡：创建金卡对象，交给一个独立的业务（支付机）：存款，消费
        GoldCard glodCard=new GoldCard("皖AFL321","汤宇轩","18326695626",5000);
        pay(glodCard);
        //4.办一张银卡：创建金卡对象，交给一个独立的业务（支付机）：存款，消费
        SliverCard sliverCard=new SliverCard("皖BFL321","汤宇轩","13956928802",2000);
        pay(sliverCard);
    }

    //支付机：用一个方法来刷卡：可能接受金卡或银卡
    public static void pay(Card c){
        System.out.println("请刷卡,请你输入当前消费的金额：");
        Scanner sc=new Scanner(System.in);
        double money=sc.nextDouble();
        c.consume(money);
    }
}
