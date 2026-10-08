package tang.day08demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * day08 综合演示
 *
 * 本节内容：
 * 1. 代码块——静态代码块、实例代码块
 * 2. 内部类——成员内部类、静态内部类、局部内部类、匿名内部类
 * 3. Lambda 表达式——替代匿名内部类
 * 4. 方法引用——静态方法引用、实例方法引用、特定类型方法引用、构造器引用
 * 5. String——创建方式、常量池、常用方法
 * 6. ArrayList——泛型、增删改查
 *
 * @author tang
 * @version 1.0
 */
public class Day08Demo {

    public static void main(String[] args) {
        // ========== 1. 代码块 ==========
        System.out.println("========== 1. 代码块 ==========");

        System.out.println("第一次创建 BlockDemo 对象：");
        BlockDemo b1 = new BlockDemo();   // 实例代码块 → 构造器

        System.out.println("第二次创建 BlockDemo 对象：");
        BlockDemo b2 = new BlockDemo();   // 实例代码块再次执行

        // 静态代码块在类加载时只执行一次（上面的输出已演示）
        System.out.println("静态代码块只执行一次，实例代码块每次 new 都执行");
        System.out.println("  静态变量共享：" + b1.count + "，" + b2.count);

        System.out.println();


        // ========== 2. 成员内部类 ==========
        System.out.println("========== 2. 成员内部类 ==========");

        // 创建格式：new 外部类().new 内部类()
        Outer1.Inner1 inner1 = new Outer1().new Inner1();
        inner1.show();
        System.out.println("  成员内部类属于外部类对象持有");

        System.out.println();


        // ========== 3. 静态内部类 ==========
        System.out.println("========== 3. 静态内部类 ==========");

        // 创建格式：new 外部类.内部类()
        Outer2.Inner2 inner2 = new Outer2.Inner2();
        inner2.show();
        System.out.println("  静态内部类属于外部类本身持有，不需要外部类对象");

        System.out.println();


        // ========== 4. 局部内部类 ==========
        System.out.println("========== 4. 局部内部类 ==========");

        Outer3 o3 = new Outer3();
        o3.testLocalClass();

        System.out.println();


        // ========== 5. 匿名内部类 ==========
        System.out.println("========== 5. 匿名内部类 ==========");

        // 匿名内部类：本质就是一个子类，并立即创建子类对象
        Swim s = new Swim() {
            @Override
            public void swimming() {
                System.out.println("  匿名内部类：学生在游泳");
            }
        };
        s.swimming();

        // 匿名内部类作为方法参数
        testSwim(new Swim() {
            @Override
            public void swimming() {
                System.out.println("  匿名内部类作为参数传递：老师游泳");
            }
        });

        System.out.println();


        // ========== 6. Lambda 表达式 ==========
        System.out.println("========== 6. Lambda 表达式 ==========");

        // 替代匿名内部类
        Swim s2 = () -> {
            System.out.println("  Lambda：学生在游泳");
        };
        s2.swimming();

        // 带参数的 Lambda
        MathOperation add = (a, b) -> a + b;
        MathOperation multiply = (a, b) -> a * b;
        System.out.println("  Lambda 加法：" + add.operate(5, 3));
        System.out.println("  Lambda 乘法：" + multiply.operate(5, 3));

        System.out.println();


        // ========== 7. Lambda 省略规则 ==========
        System.out.println("========== 7. Lambda 省略规则 ==========");

        // 完整写法
        Comparator<String> c1 = (String a, String b) -> {
            return a.compareTo(b);
        };

        // 省略参数类型
        Comparator<String> c2 = (a, b) -> {
            return a.compareTo(b);
        };

        // 单行省略大括号 + 分号 + return
        Comparator<String> c3 = (a, b) -> a.compareTo(b);

        // 单参数省略括号
        Printer p = msg -> System.out.println("  打印：" + msg);
        p.print("Hello Lambda");

        System.out.println();


        // ========== 8. 方法引用 ==========
        System.out.println("========== 8. 方法引用 ==========");

        // 创建学生数组
        Student[] students = {
                new Student("张三", 24, 178),
                new Student("李四", 38, 170),
                new Student("王二", 19, 150),
                new Student("赵五", 56, 170),
                new Student("李六", 20, 179)
        };

        // 静态方法引用：类名::静态方法
        System.out.print("按年龄排序（静态方法引用）：");
        Arrays.sort(students, Student::compareByAge);
        for (Student s3 : students) {
            System.out.print(s3.getName() + "(" + s3.getAge() + ") ");
        }
        System.out.println();

        // 实例方法引用：对象名::实例方法
        Student helper = new Student();
        System.out.print("按身高排序（实例方法引用）：");
        Arrays.sort(students, helper::compareByHeight);
        for (Student s3 : students) {
            System.out.print(s3.getName() + "(" + s3.getHeight() + ") ");
        }
        System.out.println();

        // 特定类型方法引用：类名::实例方法
        String[] names = {"Tom", "Jerry", "mike", "angele"};
        System.out.print("忽略大小写排序（特定类型方法引用）：");
        Arrays.sort(names, String::compareToIgnoreCase);
        System.out.println(Arrays.toString(names));

        // 构造器引用：类名::new
        System.out.print("构造器引用创建对象：");
        CarFactory cf = Car::new;
        Car car = cf.create("奔驰");
        System.out.println(car.getName());

        System.out.println();


        // ========== 9. String ==========
        System.out.println("========== 9. String ==========");

        // 创建方式对比
        String sA = "abc";
        String sB = "abc";
        System.out.println("直接\"abc\"，sA == sB：" + (sA == sB) + "（常量池复用）");

        String sC = new String("abc");
        String sD = new String("abc");
        System.out.println("new String，sC == sD：" + (sC == sD) + "（堆中不同对象）");

        // 内容比较用 equals
        System.out.println("sA.equals(sC)：" + sA.equals(sC) + "（内容相同）");

        // 常用方法
        String str = "Hello, Java 世界";
        System.out.println("length()：" + str.length());
        System.out.println("substring(7, 11)：" + str.substring(7, 11));
        System.out.println("contains(\"Java\")：" + str.contains("Java"));
        System.out.println("startsWith(\"Hello\")：" + str.startsWith("Hello"));
        System.out.println("toUpperCase()：" + str.toUpperCase());

        // 手机号脱敏
        String phone = "13812345678";
        String masked = phone.substring(0, 3) + "****" + phone.substring(7);
        System.out.println("手机号脱敏：" + masked);

        System.out.println();


        // ========== 10. ArrayList ==========
        System.out.println("========== 10. ArrayList ==========");

        // 创建 ArrayList（泛型约束 String）
        ArrayList<String> list = new ArrayList<>();

        // 增
        list.add("java");
        list.add("python");
        list.add("C++");
        list.add("C");
        System.out.println("添加后：" + list);

        // 查
        System.out.println("get(0)：" + list.get(0));
        System.out.println("size()：" + list.size());

        // 遍历
        System.out.print("遍历：");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();

        // 改
        list.set(0, "JavaSE");
        System.out.println("set(0, JavaSE) 后：" + list);

        // 删
        list.remove(3);          // 按索引删
        System.out.println("remove(3) 后：" + list);
        list.remove("python");   // 按内容删
        System.out.println("remove(python) 后：" + list);

        // contains / isEmpty
        System.out.println("contains(JavaSE)：" + list.contains("JavaSE"));
        System.out.println("isEmpty()：" + list.isEmpty());
    }

    /**
     * 演示匿名内部类作为方法参数传递
     */
    public static void testSwim(Swim s) {
        System.out.println("  --- testSwim 方法调用 ---");
        s.swimming();
    }
}


// ==================== 代码块 ====================

/**
 * 代码块演示类
 * 静态代码块：类加载时执行一次
 * 实例代码块：每次 new 时执行，在构造器前
 */
class BlockDemo {
    static int count = 0;

    // 静态代码块：类加载时自动执行一次
    static {
        System.out.println("  [静态代码块] 类加载时执行");
    }

    // 实例代码块：每次创建对象时，在构造器前执行
    {
        count++;
        System.out.println("  [实例代码块] 创建对象时执行");
    }

    public BlockDemo() {
        System.out.println("  [构造器] 执行");
    }
}


// ==================== 成员内部类 ====================

/**
 * 外部类 Outer1，包含成员内部类 Inner1
 */
class Outer1 {
    private int age = 18;
    static String name = "汤宇轩";

    // 成员内部类：无 static，属于外部类对象
    public class Inner1 {
        public void show() {
            System.out.println("  成员内部类：访问外部类成员 age=" + age + "，name=" + name);
            System.out.println("  this = " + this);
            System.out.println("  Outer1.this = " + Outer1.this);
        }
    }
}


// ==================== 静态内部类 ====================

/**
 * 外部类 Outer2，包含静态内部类 Inner2
 */
class Outer2 {
    static String name = "汤宇轩";

    // 静态内部类：有 static，属于外部类本身
    public static class Inner2 {
        public void show() {
            System.out.println("  静态内部类：访问外部类静态成员 name=" + name);
        }
    }
}


// ==================== 局部内部类 ====================

/**
 * 演示局部内部类（定义在方法中）
 */
class Outer3 {
    public void testLocalClass() {
        // 局部内部类：定义在方法中
        class LocalInner {
            public void show() {
                System.out.println("  局部内部类：定义在方法中");
            }
        }

        LocalInner local = new LocalInner();
        local.show();
    }
}


// ==================== 匿名内部类 & Lambda ====================

/**
 * 函数式接口：有且仅有一个抽象方法
 */
@FunctionalInterface
interface Swim {
    void swimming();
}

/**
 * 函数式接口：带参数和返回值的数学运算
 */
@FunctionalInterface
interface MathOperation {
    int operate(int a, int b);
}

/**
 * 函数式接口：单参数，无返回值
 */
@FunctionalInterface
interface Printer {
    void print(String msg);
}


// ==================== 方法引用 ====================

/**
 * 学生类——用于方法引用演示
 */
class Student {
    private String name;
    private int age;
    private int height;

    public Student() {
    }

    public Student(String name, int age, int height) {
        this.name = name;
        this.age = age;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getHeight() {
        return height;
    }

    /**
     * 静态方法：按年龄比较（用于静态方法引用）
     */
    public static int compareByAge(Student o1, Student o2) {
        return o1.getAge() - o2.getAge();
    }

    /**
     * 实例方法：按身高比较（用于实例方法引用）
     */
    public int compareByHeight(Student o1, Student o2) {
        return o1.getHeight() - o2.getHeight();
    }
}

/**
 * 汽车类——用于构造器引用
 */
class Car {
    private String name;

    public Car() {
    }

    public Car(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

/**
 * 汽车工厂接口——用于构造器引用
 */
@FunctionalInterface
interface CarFactory {
    Car create(String name);
}
