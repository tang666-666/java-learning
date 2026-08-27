package tang.inheritancedemo;

/**
 * 继承综合演示
 *
 * 本节内容：
 * 1. 继承语法（extends）——子类继承父类非私有成员
 * 2. 权限修饰符——private/缺省/protected/public 的访问范围
 * 3. 继承特点——单继承、多层继承、Object 祖宗类
 * 4. 就近原则——局部 → 子类成员 → 父类成员
 * 5. super 关键字——访问父类成员
 * 6. 方法重写（@Override）——覆盖父类方法
 * 7. 子类构造器——super() 调用父类构造器
 * 8. this() 与 super()——第一行限制，不能同时出现
 *
 * @author tang
 * @version 1.0
 */
public class InheritanceDemo {

    public static void main(String[] args) {
        // ========== 1. 继承基本用法 ==========
        System.out.println("========== 1. 继承基本用法 ==========");

        Teacher t = new Teacher("张老师", '男', "Java编程");
        System.out.println("老师信息：");
        System.out.println("  姓名：" + t.getName() + "（继承自父类）");
        System.out.println("  性别：" + t.getSex() + "（继承自父类）");
        System.out.println("  技能：" + t.getSkill() + "（子类自己）");

        // 子类对象由父类和子类共同组成
        System.out.println("  → 子类对象 = 父类部分(name, sex) + 子类部分(skill)");

        System.out.println();


        // ========== 2. 方法重写 ==========
        System.out.println("========== 2. 方法重写 ==========");

        Animal animal = new Animal();
        animal.cry();           // 父类原版

        Cat cat = new Cat();
        cat.cry();              // 子类重写后

        Dog dog = new Dog();
        dog.cry();              // 子类重写后

        // 重写 toString()
        Teacher t2 = new Teacher("李老师", '女', "数学");
        System.out.println("t2 未重写 toString：" + t2);   // 默认输出地址
        System.out.println("cat 重写 toString：" + cat);   // 输出内容

        System.out.println();


        // ========== 3. super 关键字 + 就近原则 ==========
        System.out.println("========== 3. super 关键字与就近原则 ==========");

        Son son = new Son();
        son.show();
        // 输出结果演示：
        //   num = 30（先找局部变量）
        //   this.num = 20（再找子类成员）
        //   super.num = 10（指定父类成员）

        System.out.println();


        // ========== 4. 子类构造器调用链 ==========
        System.out.println("========== 4. 子类构造器调用链 ==========");

        System.out.println("创建 Student 对象：");
        Student stu1 = new Student();               // 无参构造器
        System.out.println("  学生姓名：" + stu1.getName());

        System.out.println("创建 Student(带参) 对象：");
        Student stu2 = new Student("小明", 18);     // 有参构造器
        System.out.println("  学生姓名：" + stu2.getName() + "，年龄 " + stu2.getAge());

        System.out.println();


        // ========== 5. 继承链演示（多层继承） ==========
        System.out.println("========== 5. 多层继承 ==========");

        // Object → GrandFather → Father → Son2
        Son2 son2 = new Son2();
        son2.showFamily();
        // 输出：
        //   GrandFather 的成员
        //   Father 的成员
        //   Son2 的成员

        System.out.println("继承链：Object → GrandFather → Father → Son2");
        System.out.println("每一层的成员变量都会分配内存");

        System.out.println();


        // ========== 6. 就近原则与 super 混用 ==========
        System.out.println("========== 6. 就近原则与 super ==========");

        GrandSon grandSon = new GrandSon();
        grandSon.showAll();
    }
}


// ==================== 继承基本用法 ====================

/**
 * 父类 People
 * 私有成员变量 + public getter/setter
 */
class People {
    private String name;    // 私有成员——子类不能直接访问，通过 getter/setter
    private char sex;

    public People() {
    }

    public People(String name, char sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }
}

/**
 * 子类 Teacher 继承 People
 * 继承父类的 name、sex（非私有成员）
 * 自己新增 skill
 */
class Teacher extends People {
    private String skill;

    public Teacher() {
    }

    public Teacher(String name, char sex, String skill) {
        super(name, sex);   // 调用父类有参构造器
        this.skill = skill;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {
        return "Teacher{name='" + getName() + "', sex=" + getSex() + ", skill='" + skill + "'}";
    }
}


// ==================== 方法重写 ====================

/**
 * 父类 Animal
 */
class Animal {
    public void cry() {
        System.out.println("动物会叫");
    }
}

/**
 * 子类 Cat 重写 cry()
 */
class Cat extends Animal {
    @Override
    public void cry() {
        System.out.println("喵喵喵");
    }

    @Override
    public String toString() {
        return "这是一只猫";
    }
}

/**
 * 子类 Dog 重写 cry()
 */
class Dog extends Animal {
    @Override
    public void cry() {
        System.out.println("汪汪汪");
    }
}


// ==================== super 与就近原则 ====================

/**
 * 父类 Father2
 */
class Father2 {
    int num = 10;   // 父类成员变量
}

/**
 * 子类 Son——演示就近原则和 super
 */
class Son extends Father2 {
    int num = 20;   // 子类成员变量

    public void show() {
        int num = 30;   // 局部变量

        System.out.println("  num = " + num);           // 30（局部）
        System.out.println("  this.num = " + this.num); // 20（子类成员）
        System.out.println("  super.num = " + super.num); // 10（父类成员）
    }
}


// ==================== 子类构造器 ====================

/**
 * 父类 Person2——有参构造器 + 无参构造器
 */
class Person2 {
    private String name;
    private int age;

    public Person2() {
        System.out.println("  → Person2 无参构造器执行");
    }

    public Person2(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("  → Person2 有参构造器执行");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

/**
 * 子类 Student——演示子类构造器调用父类构造器
 * 子类全部构造器都会先调用父类构造器
 */
class Student extends Person2 {
    public Student() {
        // super();   // 默认存在（第一行），调用父类无参构造器
        System.out.println("  → Student 无参构造器执行");
    }

    public Student(String name, int age) {
        super(name, age);   // 手写调用父类有参构造器
        System.out.println("  → Student 有参构造器执行");
    }
}


// ==================== 多层继承 ====================

/**
 * 爷爷类
 */
class GrandFather {
    String grandName = "爷爷的成员";
}

/**
 * 爸爸类继承爷爷
 */
class Father extends GrandFather {
    String fatherName = "爸爸的成员";
}

/**
 * 儿子类继承爸爸——多层继承链
 */
class Son2 extends Father {
    String sonName = "儿子的成员";

    public void showFamily() {
        System.out.println("  " + grandName);
        System.out.println("  " + fatherName);
        System.out.println("  " + sonName);
    }
}


// ==================== 就近原则 + super 混用（多层） ====================

/**
 * 爷爷类
 */
class GrandFather2 {
    String name = "爷爷的name";
}

/**
 * 爸爸类
 */
class Father3 extends GrandFather2 {
    String name = "爸爸的name";
}

/**
 * 孙子类——三层就近查找
 */
class GrandSon extends Father3 {
    String name = "孙子的name";

    public void showAll() {
        System.out.println("  name = " + name);               // 孙子的
        System.out.println("  this.name = " + this.name);     // 孙子的
        System.out.println("  super.name = " + super.name);   // 爸爸的（super 直接父类）
        // System.out.println("super.super.name");  // ❌ 不能 super.super
    }
}
