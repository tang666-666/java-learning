package tang.keyworddemo;

/**
 * final、单例、枚举、抽象类与接口综合演示
 *
 * 本节内容：
 * 1. final 关键字——修饰类/方法/变量，基本类型 vs 引用类型
 * 2. 常量——static final，宏替换
 * 3. 单例设计模式——饿汉式、懒汉式
 * 4. 枚举类——enum，本质是常量，构造器私有
 * 5. 抽象类——abstract，不能创建对象，子类必须重写抽象方法
 * 6. 模板方法设计模式——共同步骤封装，抽象方法交给子类
 * 7. 接口——interface，多实现，解耦合
 * 8. JDK8 接口扩展——默认方法、私有方法、静态方法
 *
 * @author tang
 * @version 1.0
 */
public class KeywordDemo {

    // 常量：static final，命名全大写
    public static final String SCHOOL_NAME = "清华大学";
    public static final int MAX_STUDENT = 100;

    public static void main(String[] args) {
        // ========== 1. final 关键字 ==========
        System.out.println("========== 1. final 关键字 ==========");

        // final 修饰基本类型：值不能变
        final int num = 10;
        System.out.println("final int num = " + num);
        // num = 20;   // ❌ 编译错误：final 变量只能赋值一次
        System.out.println("  num 不能再赋值");

        // final 修饰引用类型：地址不能变，内容可以变
        final int[] arr = {1, 2, 3};
        System.out.println("final int[] arr = " + java.util.Arrays.toString(arr));
        // arr = new int[]{4,5,6};   // ❌ 编译错误：不能改地址
        arr[0] = 99;                    // ✅ 可以：改内容
        System.out.println("  修改 arr[0] = 99 后：" + java.util.Arrays.toString(arr));
        System.out.println("  → 引用类型：地址不变，内容可变");

        // final 修饰方法（在下面的 Father/Son 中演示）
        System.out.println("  final 方法不能被重写、final 类不能被继承");

        System.out.println();


        // ========== 2. 常量 ==========
        System.out.println("========== 2. 常量 ==========");

        System.out.println("学校名称：" + SCHOOL_NAME);
        System.out.println("最大学生数：" + MAX_STUDENT);
        System.out.println("  → 常量编译后会宏替换为字面量，性能与直接写字面量一样");

        System.out.println();


        // ========== 3. 单例设计模式 ==========
        System.out.println("========== 3. 单例设计模式 ==========");

        // 饿汉式单例：拿对象时对象早就创建好了
        SingletonA a1 = SingletonA.getInstance();
        SingletonA a2 = SingletonA.getInstance();
        System.out.println("饿汉式：a1 == a2 → " + (a1 == a2));
        System.out.println("  类加载时就创建好对象，每次获取都是同一个");

        // 懒汉式单例：拿对象时才开始创建
        SingletonB b1 = SingletonB.getInstance();
        SingletonB b2 = SingletonB.getInstance();
        System.out.println("懒汉式：b1 == b2 → " + (b1 == b2));
        System.out.println("  第一次调用 getInstance() 时才创建对象");

        System.out.println();


        // ========== 4. 枚举类 ==========
        System.out.println("========== 4. 枚举类 ==========");

        // 枚举本质是常量，每个常量记住一个枚举对象
        Direction dir = Direction.UP;
        System.out.println("方向：" + dir);
        System.out.println("  枚举本质：UP/DOWN/LEFT/RIGHT 都是 Direction 的对象");

        // 遍历枚举
        System.out.print("  所有方向：");
        for (Direction d : Direction.values()) {
            System.out.print(d + " ");
        }
        System.out.println();

        // 应用：季节信息分类
        Season season = Season.SUMMER;
        System.out.println("  当前季节：" + season);

        System.out.println();


        // ========== 5. 抽象类 ==========
        System.out.println("========== 5. 抽象类 ==========");

        // 抽象类不能创建对象
        // Animal animal = new Animal();   // ❌ 编译错误

        // 子类继承抽象类，必须重写完所有抽象方法
        Animal dog = new Dog();
        dog.cry();
        dog.sleep();    // 抽象类中的普通方法可以直接用

        Animal cat = new Cat();
        cat.cry();

        System.out.println("  抽象类不能 new，只能被继承，子类必须重写抽象方法");

        System.out.println();


        // ========== 6. 模板方法设计模式 ==========
        System.out.println("========== 6. 模板方法设计模式 ==========");

        // 模板方法封装共同步骤，抽象方法交给子类
        People student = new Student("小明");
        System.out.println("学生写作文：");
        student.write();

        System.out.println();

        People teacher = new Teacher("张老师");
        System.out.println("老师写作文：");
        teacher.write();

        System.out.println();


        // ========== 7. 接口 ==========
        System.out.println("========== 7. 接口 ==========");

        // 接口不能创建对象，只能被实现
        // Flyable f = new Flyable();   // ❌ 编译错误

        // 一个类可以实现多个接口
        Bird bird = new Bird();
        bird.fly();     // Flyable 接口的方法
        bird.swim();    // Swimmable 接口的方法
        System.out.println("  鸟同时实现了 Flyable 和 Swimmable 两个接口");

        System.out.println();


        // ========== 8. 面向接口编程（解耦合） ==========
        System.out.println("========== 8. 面向接口编程 ==========");

        // 面向接口编程：变量用接口类型，可灵活切换实现
        USB usb1 = new Mouse();     // 鼠标实现 USB
        usb1.connect();             // 多态

        USB usb2 = new Keyboard();  // 键盘实现 USB
        usb2.connect();             // 多态

        // 好处：以后新增 U 盘、摄像头，都不用改电脑代码
        System.out.println("  面向接口编程：USB 接口接受一切设备（鼠标/键盘/U盘...）");

        System.out.println();


        // ========== 9. JDK8 接口扩展 ==========
        System.out.println("========== 9. JDK8 接口扩展 ==========");

        // 默认方法：实现类对象调用
        MyPrinter printer = new MyPrinter();
        printer.printDefault();     // 默认方法
        printer.printAbstract();    // 抽象方法

        // 静态方法：接口名调用
        Printer.printStatic();      // 静态方法

        System.out.println("  默认方法（default）、私有方法（private）、静态方法（static）");
    }
}


// ==================== 单例模式 ====================

/**
 * 饿汉式单例
 * 拿对象时，对象早就创建好了（类加载时就 new）
 */
class SingletonA {
    // ② 静态变量记住唯一对象（类加载时就创建）
    private static SingletonA a = new SingletonA();

    // ① 私有化构造器
    private SingletonA() {
    }

    // ③ 提供静态方法返回对象
    public static SingletonA getInstance() {
        return a;
    }
}

/**
 * 懒汉式单例
 * 拿对象时，才开始创建对象（第一次调用才 new）
 */
class SingletonB {
    // ② 静态变量（先不 new）
    private static SingletonB b;

    // ① 私有化构造器
    private SingletonB() {
    }

    // ③ 提供静态方法，保证返回同一个对象
    public static SingletonB getInstance() {
        if (b == null) {
            b = new SingletonB();   // 第一次调用才创建
        }
        return b;
    }
}


// ==================== 枚举类 ====================

/**
 * 枚举：方向
 * 第一行只能写对象名称，逗号隔开
 */
enum Direction {
    UP, DOWN, LEFT, RIGHT;
}

/**
 * 枚举：季节（信息分类）
 */
enum Season {
    SPRING, SUMMER, AUTUMN, WINTER;
}


// ==================== 抽象类 ====================

/**
 * 抽象类 Animal
 * 抽象方法 cry()，普通方法 sleep()
 */
abstract class Animal {
    // 抽象方法：没有方法体
    public abstract void cry();

    // 普通方法：有方法体
    public void sleep() {
        System.out.println("动物会睡觉");
    }
}

class Dog extends Animal {
    @Override
    public void cry() {
        System.out.println("狗：汪汪汪");
    }
}

class Cat extends Animal {
    @Override
    public void cry() {
        System.out.println("猫：喵喵喵");
    }
}


// ==================== 模板方法设计模式 ====================

/**
 * 抽象类 People——模板方法
 * 模板方法 write() 封装共同步骤，抽象方法 writeMain() 交给子类
 */
abstract class People {
    private String name;

    public People(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // 模板方法：final 防止子类修改模板结构
    public final void write() {
        System.out.println("\t《我的爸爸》");
        System.out.println("\t我的爸爸是个好人");
        // 抽象方法：不同子类实现不同
        writeMain();
        System.out.println("\t我的爸爸很好");
    }

    // 抽象方法：交给子类实现
    public abstract void writeMain();
}

class Student extends People {
    public Student(String name) {
        super(name);
    }

    @Override
    public void writeMain() {
        System.out.println("\t我的爸爸会辅导" + getName() + "写作业");
    }
}

class Teacher extends People {
    public Teacher(String name) {
        super(name);
    }

    @Override
    public void writeMain() {
        System.out.println("\t我的爸爸会教" + getName() + "知识");
    }
}


// ==================== 接口 ====================

/**
 * 接口 Flyable
 */
interface Flyable {
    void fly();   // 抽象方法（可省略 public abstract）
}

/**
 * 接口 Swimmable
 */
interface Swimmable {
    void swim();
}

/**
 * 一个类实现多个接口
 */
class Bird implements Flyable, Swimmable {
    @Override
    public void fly() {
        System.out.println("鸟会飞");
    }

    @Override
    public void swim() {
        System.out.println("鸟也会游泳");
    }
}


// ==================== 面向接口编程 ====================

/**
 * USB 接口
 */
interface USB {
    void connect();
}

/**
 * 鼠标实现 USB 接口
 */
class Mouse implements USB {
    @Override
    public void connect() {
        System.out.println("鼠标连接电脑");
    }
}

/**
 * 键盘实现 USB 接口
 */
class Keyboard implements USB {
    @Override
    public void connect() {
        System.out.println("键盘连接电脑");
    }
}


// ==================== JDK8 接口扩展 ====================

/**
 * 接口 Printer——演示 JDK8 的默认方法、私有方法、静态方法
 */
interface Printer {
    // 抽象方法
    void printAbstract();

    // 默认方法（实例方法）：default 修饰，实现类对象调用
    default void printDefault() {
        System.out.println("默认方法：打印默认文档");
        // 调用私有方法
        checkInk();
    }

    // 私有方法：private 修饰，供接口内其他实例方法调用
    private void checkInk() {
        System.out.println("  私有方法：检查墨水量");
    }

    // 静态方法（类方法）：static 修饰，接口名调用
    static void printStatic() {
        System.out.println("静态方法：打印静态信息（接口名直接调用）");
    }
}

/**
 * 实现类
 */
class MyPrinter implements Printer {
    @Override
    public void printAbstract() {
        System.out.println("实现类重写的抽象方法：打印内容");
    }
}
