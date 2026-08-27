package tang.demo;

public class GoldCard extends Card{

    public GoldCard() {
    }

    public GoldCard(String carId, String name, String phone, double money) {
        super(carId, name, phone, money);
    }

    @Override
    public void consume(double money){
        System.out.println("您当前消费：" + money);
        System.out.println("优惠后的价格：" + money*0.8);
        if(getMoney()<money*0.8){
            System.out.println("您余额是：" + getMoney() + "，当前余额不足");
            return;
        }
        //更新数据
        setMoney(getMoney()-money*0.8);
        //判断消费如果大于200，调用一个独有的功能，打印洗车票
        if(money*0.8>=200){
            printTicker();
        }
        else{
            System.out.println("您当前消费不满200，不能免费洗车");
        }
    }

    //打印洗车票
    public void printTicker(){
        System.out.println("您消费了，请打印洗车票");
    }
}
