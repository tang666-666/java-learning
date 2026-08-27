package tang.object;

/**
 * 面向对象入门综合演示
 *
 * 本节内容：
 * 1. 类的定义（成员变量 + 成员方法）
 * 2. 对象的创建（new 关键字）
 * 3. 通过对象访问属性和方法
 * 4. 同一个类创建多个对象（各自独立）
 * 5. 封装思想（private + getter/setter）
 * 6. this 关键字的使用
 * 7. 对象在内存中的表现（栈/堆/方法区）
 *
 * @author tang
 * @version 1.0
 */
public class OopDemo {

    public static void main(String[] args) {
        // ========== 1. 创建对象——使用类模板 ==========
        System.out.println("========== 1. 创建对象 ==========");

        // 类名 对象名 = new 类名();
        // 步骤：① 加载类到方法区 → ② new 在堆中分配空间
        //       → ③ 初始化默认值 → ④ 返回地址给栈中的引用变量

        Dog dog1 = new Dog();       // 创建第一个 Dog 对象
        Dog dog2 = new Dog();       // 创建第二个 Dog 对象（独立空间）

        // 给对象赋值
        dog1.name = "旺财";
        dog1.setAge(3);

        dog2.name = "来福";
        dog2.setAge(5);

        // 访问对象的属性和方法
        System.out.println(dog1.name + "，" + dog1.getAge() + "岁");
        dog1.bark();

        System.out.println(dog2.name + "，" + dog2.getAge() + "岁");
        dog2.bark();

        System.out.println();


        // ========== 2. 多个对象独立存储 ==========
        System.out.println("========== 2. 多个对象独立存储 ==========");

        // 每个对象有自己独立的成员变量空间
        Dog dog3 = dog1;    // dog3 和 dog1 指向同一个对象
        System.out.println("dog3 指向 dog1 的同一个对象：");
        dog3.name = "小白";  // 通过 dog3 修改
        System.out.println("dog1.name = " + dog1.name);  // "小白"（同一对象）
        System.out.println("dog3.name = " + dog3.name);  // "小白"

        System.out.println();


        // ========== 3. 封装演示——private + getter/setter ==========
        System.out.println("========== 3. 封装演示 ==========");

        // 演示封装——public 属性可以直接访问，private 属性要通过 setter
        Dog d = new Dog();
        d.name = "小黑";             // ✅ 直接访问 public 属性
        d.setAge(-10);               // ❌ 通过 setter 访问，会被校验拦截

        // 使用封装好的 Student 类
        Student stu = new Student();

        // 通过 setter 赋值（带校验）
        stu.setName("张三");
        stu.setAge(20);
        stu.setScore(95.5);

        // 通过 getter 取值
        System.out.println("学生信息：");
        System.out.println("  姓名：" + stu.getName());
        System.out.println("  年龄：" + stu.getAge());
        System.out.println("  成绩：" + stu.getScore());

        // 尝试设置非法年龄
        stu.setAge(-5);     // 输出"年龄不合法，范围：0~150"
        System.out.println("  设置非法年龄后年龄仍为：" + stu.getAge());  // 还是 20

        System.out.println();


        // ========== 4. this 关键字演示 ==========
        System.out.println("========== 4. this 关键字 ==========");

        /*
         * this 代表当前对象的引用
         * 作用：当成员变量和局部变量同名时，用 this 区分
         *
         * 内存理解：
         *   stu.setName("张三") 调用时，
         *   this 指向堆中的 stu 对象
         *   this.name = 成员变量
         *   name    = 方法参数
         */
        Student stu2 = new Student();
        stu2.setName("李四");   // 调用时，this 就是 stu2 这个对象
        System.out.println("stu2 的名字：" + stu2.getName());

        System.out.println();


        // ========== 5. 对象作为方法的参数 ==========
        System.out.println("========== 5. 对象作为参数 ==========");

        // 对象是引用类型，传的是地址
        Student stu3 = new Student();
        stu3.setName("王五");
        stu3.setScore(88.0);
        System.out.println("修改前：stu3.score = " + stu3.getScore());

        // 传递对象引用，方法内部修改会影响原对象
        updateScore(stu3, 95.0);
        System.out.println("修改后：stu3.score = " + stu3.getScore());

        System.out.println();


        // ========== 6. 综合案例：学生成绩管理系统（展示 OOP 思想） ==========
        System.out.println("========== 6. 综合案例：学生成绩管理 ==========");

        // 创建多个学生对象
        Student s1 = new Student("小明", 18, 92.5);
        Student s2 = new Student("小红", 19, 88.0);
        Student s3 = new Student("小刚", 20, 76.5);

        // 存入数组
        Student[] students = {s1, s2, s3};

        // 统计信息
        double totalScore = 0;
        Student topStudent = students[0];

        System.out.println("=== 学生成绩单 ===");
        System.out.println("姓名\t年龄\t成绩");
        for (Student s : students) {
            s.printInfo();                      // 调用对象的方法
            totalScore += s.getScore();
            if (s.getScore() > topStudent.getScore()) {
                topStudent = s;
            }
        }

        System.out.println("\n班级平均分：" + (totalScore / students.length));
        System.out.println("最高分：" + topStudent.getName() + "（" + topStudent.getScore() + "分）");

        // 展示不及格学生
        System.out.println("\n不及格学生：");
        boolean hasFail = false;
        for (Student s : students) {
            if (!s.isPass()) {
                System.out.println("  " + s.getName() + "（" + s.getScore() + "分）");
                hasFail = true;
            }
        }
        if (!hasFail) {
            System.out.println("  全部及格！");
        }
    }

    /**
     * 演示对象作为方法参数（引用传递）
     * 方法内修改对象属性，会影响原对象
     *
     * 内存理解：
     *   stu 接收到的是对象的地址（引用），
     *   通过地址直接修改堆中的数据
     */
    public static void updateScore(Student stu, double newScore) {
        stu.setScore(newScore);
        System.out.println("  （方法内已修改成绩为 " + newScore + "）");
    }
}


/**
 * Dog 类——演示基本的类定义和对象创建
 *
 * 成员变量：name、age
 * 成员方法：bark()
 */
class Dog {
    // 成员变量（属性）
    String name;        // 默认值 null
    private int age;    // private 封装——外部不能直接访问

    // 成员方法（行为）
    public void bark() {
        System.out.println(name + "：汪汪！");
    }

    // getter/setter（对封装的 age 提供访问接口）
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 0 && age <= 30) {
            this.age = age;
        } else {
            System.out.println("年龄不合法（狗），范围：0~30");
        }
    }
}


/**
 * Student 类——演示完整的封装
 *
 * 封装步骤：
 * 1. private 私有化成员变量
 * 2. public getter/setter 提供访问
 * 3. setter 中可加入校验逻辑
 */
class Student {
    // private 成员变量（外部无法直接访问）
    private String name;
    private int age;
    private double score;

    // 无参构造器
    public Student() {
    }

    // 有参构造器（方便一次性赋值）
    public Student(String name, int age, double score) {
        this.name = name;       // this.name = 成员变量，name = 参数
        this.age = age;
        this.score = score;
    }

    // getter 和 setter——封装的核心

    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     * @param name 姓名
     */
    public void setName(String name) {
        // this 区分成员变量和局部变量
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    /**
     * 设置年龄——带校验逻辑
     * @param age 年龄
     */
    public void setAge(int age) {
        if (age >= 0 && age <= 150) {
            this.age = age;
        } else {
            System.out.println("  → 年龄不合法，范围：0~150");
        }
    }

    public double getScore() {
        return score;
    }

    /**
     * 设置成绩
     * @param score 成绩
     */
    public void setScore(double score) {
        if (score >= 0 && score <= 100) {
            this.score = score;
        } else {
            System.out.println("  → 成绩不合法，范围：0~100");
        }
    }

    /**
     * 判断是否及格
     * @return 成绩 >= 60 返回 true
     */
    public boolean isPass() {
        return score >= 60;
    }

    /**
     * 打印学生信息
     */
    public void printInfo() {
        System.out.println(name + "\t" + age + "\t" + score);
    }
}
