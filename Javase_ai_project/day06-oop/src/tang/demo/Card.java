package tang.demo;

public class Card {
    private String carId;
    private String name;
    private String phone;
    private double money;

    public Card(){
    }

    public Card(String carId, String name, String phone, double money) {
        this.carId = carId;
        this.name = name;
        this.phone = phone;
        this.money = money;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    //预存金额
    public void deposit(double money){
        this.money+=money;
    }

    //消费金额
    public void consume(double money){
        this.money-=money;
    }
}
