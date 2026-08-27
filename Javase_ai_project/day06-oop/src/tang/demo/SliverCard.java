package tang.demo;

public class SliverCard extends Card{

    public SliverCard() {
    }

    public SliverCard(String carId, String name, String phone, double money) {
        super(carId, name, phone, money);
    }

    @Override
    public void consume(double money){
        System.out.println("您当前消费：" + money);
        System.out.println("优惠后的价格：" + money*0.9);
        if(getMoney()<money*0.9){
            System.out.println("您余额是：" + getMoney() + "，当前余额不足");
            return;
        }
        //更新数据
        setMoney(getMoney()-money*0.9);
    }
}
