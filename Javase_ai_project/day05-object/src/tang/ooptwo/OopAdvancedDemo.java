package tang.ooptwo;

/**
 * 面向对象进阶综合演示
 *
 * 本节内容：
 * 1. 构造器——无参/有参，默认构造器规则
 * 2. this 关键字——解决变量名冲突
 * 3. 封装——private 合理隐藏，public get/set 合理暴露
 * 4. JavaBean 实体类——私有成员 + get/set + 无参构造器
 * 5. static 变量——属于类，只有一份，全部对象共享
 * 6. static 方法——属于类，工具类应用
 * 7. 静态访问规则——静态方法不能访问实例成员，不能使用 this
 *
 * @author tang
 * @version 1.0
 */
public class OopAdvancedDemo {

    public static void main(String[] args) {
        // ========== 1. 构造器演示 ==========
        System.out.println("========== 1. 构造器 ==========");

        // 创建对象时会自动调用构造器
        // new Person() 会调用无参构造器
        Person p1 = new Person();
        System.out.println("p1 默认值：name=" + p1.getName() + ", age=" + p1.getAge());

        // new Person("张三", 18) 会调用有参构造器
        Person p2 = new Person("张三", 18);
        System.out.println("p2 构造后：name=" + p2.getName() + ", age=" + p2.getAge());

        System.out.println();


        // ========== 2. 默认构造器规则演示 ==========
        System.out.println("========== 2. 默认构造器规则 ==========");

        /*
         * 规则：
         * - 类默认自带无参构造器
         * - 一旦手动定义了有参构造器，默认无参构造器就消失
         * - 需要自己重新定义无参构造器
         */
        System.out.println("Person 手动定义了有参构造器，也手动保留了无参构造器");
        System.out.println("  new Person() → " + p1.getName());
        System.out.println("  new Person(\"李四\", 20) → " + new Person("李四", 20).getName());

        System.out.println();


        // ========== 3. this 关键字演示 ==========
        System.out.println("========== 3. this 关键字 ==========");

        // this 解决变量名冲突：成员变量 name vs 参数 name
        p2.setName("李四");    // 传参 name = "李四"，this.name 指向对象的成员变量
        System.out.println("this.name = name 的赋值效果：p2.name = " + p2.getName());

        System.out.println();


        // ========== 4. 封装演示 ==========
        System.out.println("========== 4. 封装 ==========");

        // private 成员变量外部不能直接访问
        Person p3 = new Person("王五", 25);
        System.out.println("通过 getter 取值：" + p3.getName() + "，年龄 " + p3.getAge());

        // 通过 setter 赋值（可加校验）
        p3.setAge(200);     // 非法年龄，被拦截
        p3.setAge(30);      // 合法年龄，赋值成功
        System.out.println("setAge(200) 被拦截后年龄仍为：" + p3.getAge());
        System.out.println("setAge(30) 后年龄变为：" + p3.getAge());

        System.out.println();


        // ========== 5. static 变量演示 ==========
        System.out.println("========== 5. static 变量 ==========");

        // 静态变量：属于类，只有一份，全部对象共享
        // 通过类名访问（推荐）
        Person.country = "中国";
        System.out.println("Person.country = " + Person.country);

        // 创建多个对象
        Person a = new Person("张三", 20);
        Person b = new Person("李四", 22);

        // 静态变量——所有对象共享一份
        System.out.println("a 访问静态变量：" + a.country);   // "中国"
        System.out.println("b 访问静态变量：" + b.country);   // "中国"

        // 任意对象修改静态变量，所有对象都会看到变化
        a.country = "中国（已更新）";
        System.out.println("a 修改静态变量后：");
        System.out.println("  b.country = " + b.country);    // 同步变化
        System.out.println("  Person.country = " + Person.country); // 同步变化

        // 静态变量只有一份
        System.out.println("a.country == b.country：" + (a.country == b.country));
        System.out.println("a 与 b 的 country 指向同一个内存地址");

        // 实例变量——每个对象独立
        a.instanceCounter = 1;
        b.instanceCounter = 2;
        System.out.println("实例变量各自独立：a=" + a.instanceCounter + ", b=" + b.instanceCounter);

        System.out.println();


        // ========== 6. static 方法演示 ==========
        System.out.println("========== 6. static 方法 ==========");

        // 静态方法——用类名调用（推荐）
        Person.printCountry();

        // 实例方法——用对象调用
        a.printPersonInfo();

        // 同一类中访问静态成员，类名可以省略
        System.out.println("同一类中省略类名调用静态方法：");
        sameClassDemo();

        System.out.println();


        // ========== 7. 静态访问规则演示 ==========
        System.out.println("========== 7. 静态访问规则 ==========");

        // 规则 1：静态方法中可以直接访问静态成员，不可以直接访问实例成员
        System.out.println("规则1：静态方法只能访问静态成员");
        System.out.println("  Person.printCountry() 直接访问了静态变量 country ✅");

        // 规则 2：实例方法中既可以直接访问静态成员，也可以直接访问实例成员
        System.out.println("规则2：实例方法可以访问所有成员");
        a.printPersonInfo();   // 同时访问了实例变量 name/age 和静态变量 country ✅

        // 规则 3：实例方法中可以出现 this，静态方法中不可以出现 this
        System.out.println("规则3：实例方法有 this，静态方法没有 this");
        a.printUsingThis();    // 实例方法中使用 this ✅

        System.out.println();


        // ========== 8. 工具类演示（static 方法应用） ==========
        System.out.println("========== 8. 工具类 ==========");

        // 工具类：所有方法静态，构造器私有，直接用类名调用
        System.out.println("Math 工具类：" + Math.random());   // Java 自带的工具类

        System.out.println("4 位验证码：" + VerifyCodeUtil.getCode(4));
        System.out.println("6 位验证码：" + VerifyCodeUtil.getCode(6));

        // 验证工具类构造器私有——不能创建对象
        // new VerifyCodeUtil();  // ❌ 编译错误：构造器私有
        System.out.println("VerifyCodeUtil 构造器私有，无法创建对象，只能直接调用静态方法");

        System.out.println();


        // ========== 9. JavaBean 实体类演示 ==========
        System.out.println("========== 9. JavaBean 实体类 ==========");

        // 实体类：私有成员 + get/set + 无参构造器
        // 实体类只负责存取数据，业务处理交给其他类的对象
        Goods goods = new Goods("Java 编程思想", 88.5, 100);
        System.out.println("商品信息：");
        System.out.println("  名称：" + goods.getName());
        System.out.println("  价格：" + goods.getPrice());
        System.out.println("  库存：" + goods.getStock());

        // 使用业务类处理数据
        GoodsOperator operator = new GoodsOperator(goods);
        operator.printStockStatus();
        operator.printDiscountedPrice(0.8);
    }

    /**
     * 同一类中访问静态成员，类名可以省略的演示
     */
    public static void sameClassDemo() {
        // 直接调用本类的静态方法（省略 OopAdvancedDemo. 前缀）
        printStaticHelper();
        // 也可以省略类名访问静态变量
        System.out.println("省略类名访问静态变量：staticValue = " + staticValue);
    }

    static int staticValue = 42;    // 本类的静态变量

    public static void printStaticHelper() {
        System.out.println("  这是本类的静态方法，直接调用");
    }
}


/**
 * Person 类——演示构造器、this、封装、static
 */
class Person {
    // 静态变量：属于类，只有一份，所有对象共享
    static String country;

    // 实例变量：属于每个对象
    private String name;
    private int age;

    // 实例变量（演示用，正常应为 private）
    int instanceCounter;

    // 无参构造器（手动保留——因为下面定义了有参构造器）
    public Person() {
        // 默认构造器：给成员变量赋初始值（可选）
        this.name = "未命名";
        this.age = 0;
    }

    // 有参构造器
    public Person(String name, int age) {
        this.name = name;   // this 区分：左边是成员变量，右边是参数
        this.age = age;
    }

    // getter/setter——封装的核心

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    /**
     * setter 中加入校验逻辑（封装的好处）
     */
    public void setAge(int age) {
        if (age >= 0 && age <= 150) {
            this.age = age;
        } else {
            System.out.println("  → 年龄不合法（范围 0~150），拒绝赋值");
        }
    }

    // 静态方法：可以直接访问静态成员
    public static void printCountry() {
        System.out.println("静态方法 printCountry() 访问静态变量：国家 = " + country);
    }

    // 实例方法：可以访问静态成员 + 实例成员
    public void printPersonInfo() {
        System.out.println("实例方法 printPersonInfo()：" + name + "，" + age + "岁，" + country);
    }

    // 实例方法中使用 this
    public void printUsingThis() {
        System.out.println("实例方法中 this 代表当前对象：" + this.name + "，" + this.age + "岁");
    }
}


/**
 * Goods 类——JavaBean 实体类标准写法
 *
 * 要求：
 * 1. 成员变量全部私有
 * 2. 提供 public 的 getter/setter
 * 3. 提供无参构造器（有参可选）
 */
class Goods {
    // ① 成员变量全部私有
    private String name;
    private double price;
    private int stock;

    // ③ 无参构造器（必须）
    public Goods() {
    }

    // ③ 有参构造器（可选）
    public Goods(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // ② public getter/setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}


/**
 * GoodsOperator——业务类
 *
 * 实体类只负责存取数据，业务处理交给业务类
 * 实现数据与业务处理相分离
 */
class GoodsOperator {
    private Goods goods;

    // 构造器：接收一个商品实体对象
    public GoodsOperator(Goods goods) {
        this.goods = goods;
    }

    /**
     * 打印库存状态（业务处理）
     */
    public void printStockStatus() {
        if (goods.getStock() > 50) {
            System.out.println("库存充足：" + goods.getStock() + " 件");
        } else if (goods.getStock() > 0) {
            System.out.println("库存不足，仅剩 " + goods.getStock() + " 件，请补货");
        } else {
            System.out.println("已售罄！");
        }
    }

    /**
     * 打印折扣价（业务处理）
     * @param discount 折扣（0.8 表示八折）
     */
    public void printDiscountedPrice(double discount) {
        double price = goods.getPrice() * discount;
        System.out.printf("打 %.1f 折后价格：%.2f 元%n", discount, price);
    }
}


/**
 * VerifyCodeUtil——工具类
 *
 * 设计规范：
 * 1. 所有方法都是静态的
 * 2. 构造器私有，防止创建对象
 * 3. 直接用类名调用
 */
class VerifyCodeUtil {
    // 私有构造器——防止外部创建对象
    private VerifyCodeUtil() {
    }

    /**
     * 生成随机验证码（数字 + 大写字母 + 小写字母）
     * @param n 验证码长度
     * @return 验证码字符串
     */
    public static String getCode(int n) {
        String code = "";
        for (int i = 0; i < n; i++) {
            int type = (int) (Math.random() * 3);   // 0=数字，1=小写，2=大写
            switch (type) {
                case 0:
                    code += (int) (Math.random() * 10);                    // 0~9
                    break;
                case 1:
                    code += (char) ('a' + (int) (Math.random() * 26));     // a~z
                    break;
                case 2:
                    code += (char) ('A' + (int) (Math.random() * 26));     // A~Z
                    break;
            }
        }
        return code;
    }
}
