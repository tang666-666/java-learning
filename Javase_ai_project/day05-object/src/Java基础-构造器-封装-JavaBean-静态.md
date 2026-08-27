# Java 基础：构造器、封装、JavaBean 与 static

> 对应模块：`day05-object`
> 学习日期：2026-07-27

---

## 目录

1. [构造器（Constructor）](#1-构造器constructor)
2. [this 关键字](#2-this-关键字)
3. [封装的设计要求](#3-封装的设计要求)
4. [JavaBean（实体类）](#4-javabean实体类)
5. [static 修饰成员变量](#5-static-修饰成员变量)
6. [static 修饰成员方法](#6-static-修饰成员方法)
7. [static 访问注意事项](#7-static-访问注意事项)
8. [工具类设计](#8-工具类设计)
9. [完整代码示例](#9-完整代码示例)

---

## 1. 构造器（Constructor）

### 概念

**构造器**是创建对象时自动调用的特殊方法，用于初始化对象。

### 特点

| 特点 | 说明 |
|------|------|
| **无返回值** | 不能写 `void` 或其他返回类型 |
| **名称与类名相同** | 必须完全一致 |
| **创建对象时自动调用** | `new 类名()` 就会调用构造器 |
| **作用** | 初始化对象的成员变量 |

### 语法

```java
public class Student {
    // 无参构造器
    public Student() {
        System.out.println("无参构造器执行");
    }

    // 有参构造器
    public Student(String name, int age) {
        this.name = name;   // 初始化成员变量
        this.age = age;
    }
}
```

### 构造器的分类

| 类型 | 语法 | 用途 |
|------|------|------|
| **无参构造器** | `public Student() {}` | 创建空对象，之后手动赋值 |
| **有参构造器** | `public Student(String n, int a) {}` | 创建对象时直接传参初始化 |

### 默认构造器规则（重点）

> **类默认自带无参构造器**。  
> 但**如果手动定义了有参构造器，默认无参构造器就消失**了，需要自己重新定义。

```java
public class Person {
    // 什么都不写 —— 默认有无参构造器
}

public class Dog {
    // 写了有参构造器
    public Dog(String name) {
        this.name = name;
    }
    // ❌ 此时默认无参构造器消失
    // new Dog();  // 编译错误！必须 new Dog("旺财")
}
```

### 创建对象的过程

```
Student s = new Student("张三", 18);

1. new：在堆内存中分配空间
2. 初始化：成员变量赋默认值（name = null, age = 0）
3. 调用构造器：执行构造器中的代码（给 name、age 赋值）
4. 返回地址：栈中 s 变量指向堆中的对象
```

---

## 2. this 关键字

### 概念

**this** 是一个变量，用在方法中，**代表当前对象**（谁调用这个方法，this 就是谁）。

### 作用：解决变量名冲突

最常见的场景：**成员变量**和**局部变量（参数）**同名时，用 `this` 区分。

```java
public class Student {
    private String name;

    public void setName(String name) {
        // 等号左边 this.name = 成员变量
        // 等号右边 name     = 方法参数
        this.name = name;
    }
}
```

### 内存理解

```
调用：stu.setName("张三")
         ↓
this 指向堆中的 stu 对象
this.name = 对象的 name 成员变量
name      = 方法的参数"张三"
```

### this 的两种用法

| 用法 | 说明 |
|------|------|
| `this.成员变量` | 区分同名变量，访问当前对象的成员变量 |
| `this.方法()` | 调用当前对象的其他方法 |

```java
public void printInfo() {
    this.printName();   // this 调用其他方法（也可省略）
    System.out.println(this.name);
}
```

---

## 3. 封装的设计要求

### 封装的设计要求：合理隐藏，合理暴露

- **合理隐藏**：把不需要外部直接访问的成员变量用 `private` 隐藏起来
- **合理暴露**：通过 `public` 修饰的 get/set 方法暴露访问入口

### 如何隐藏——private

使用 `private` 修饰成员变量后，**只能在本类中直接访问**，其他任何地方都不能直接访问。

```java
public class Student {
    private String name;   // 外部不能直接访问
    private int age;       // 外部不能直接访问
}
```

```java
Student s = new Student();
// s.name = "张三";  // ❌ 编译错误：private 属性外部不能直接访问
```

### 如何暴露——public get/set

用 `public` 修饰的 getter（取值）和 setter（赋值）方法：

```java
public class Student {
    private String name;

    // getter——取值
    public String getName() {
        return name;
    }

    // setter——赋值（可加校验）
    public void setName(String name) {
        this.name = name;
    }
}
```

### 命名规范

| 成员变量 | getter | setter |
|----------|--------|--------|
| `name` | `getName()` | `setName(...)` |
| `age` | `getAge()` | `setAge(...)` |
| `isPass`（boolean） | `isPass()` | `setPass(...)` |

---

## 4. JavaBean（实体类）

### 什么是 JavaBean

**JavaBean（实体类）** 是 Java 中的一种特殊类，专门用来**封装数据**，代表现实世界中的一个实体（学生、商品、订单等）。

### 编写规范（要求）

| 要求 | 说明 |
|------|------|
| **① 成员全部私有** | 用 `private` 修饰所有成员变量 |
| **② 提供 public get/set** | 为每个成员变量提供 getter 和 setter |
| **③ 提供无参构造器** | 必须有（有参构造器可选）|

```java
public class Student {
    // ① 成员变量全部私有
    private String name;
    private double score;

    // ③ 无参构造器（必须）
    public Student() {
    }

    // ③ 有参构造器（可选）
    public Student(String name, double score) {
        this.name = name;
        this.score = score;
    }

    // ② public getter/setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
```

### 实体类的作用

> **创建对象，存取数据（封装数据）**。

- 实体类对象只负责**数据的存取**
- 对数据的**业务处理**（求和、求平均、判断等）交给**其他类的对象**来完成
- 实现 **数据** 与 **业务处理** 相分离

```java
// 实体类：只存数据
Student s = new Student("张三", 90, 80);

// 业务类：专门处理数据
StudentOperator operator = new StudentOperator(s);
operator.printTotalScore();      // 业务处理
operator.printAverageScore();    // 业务处理
```

### 代码分离示意

```
Student（实体类）           StudentOperator（业务类）
├── name: String            ├── printTotalScore()
├── chinese: double         └── printAverageScore()
└── math: double
     ↑ 只负责存取数据            ↑ 只负责处理数据
```

---

## 5. static 修饰成员变量

### 静态变量 vs 实例变量

| 对比 | 静态变量（static） | 实例变量 |
|------|-------------------|---------|
| 修饰 | 有 `static` 修饰 | 无 `static` 修饰 |
| 归属 | **属于类** | 属于每个对象 |
| 内存 | **只有一份** | 每个对象各有一份 |
| 共享 | 类的全部对象**共享** | 各对象独立 |
| 访问 | `类名.静态变量` | `对象名.实例变量` |

```java
public class Student {
    static String school;   // 静态变量——属于类，只有一份
    int age;                // 实例变量——属于每个对象
}
```

### 静态变量的共享特性

```java
Student.school = "清华大学";     // 通过类名访问

Student s1 = new Student();
Student s2 = new Student();

s1.school = "北京大学";          // 修改共享的静态变量
System.out.println(s2.school);   // "北京大学"（s1 的修改影响 s2）

s1.age = 18;                     // 实例变量，只影响 s1
s2.age = 20;                     // 不影响 s1
```

### 内存图解

```
方法区（静态区）
┌─────────────────────┐
│ school = "北京大学"   │ ← 静态变量只有一份，所有对象共享
└─────────────────────┘

堆
┌─────────────┐   ┌─────────────┐
│ s1 对象      │   │ s2 对象      │
│ age = 18    │   │ age = 20    │ ← 实例变量各自独立
└─────────────┘   └─────────────┘
```

### 应用场景

- 所有对象共享的数据（如：学校名称、总人数、配置信息）
- 常量（`public static final`）

---

## 6. static 修饰成员方法

### 静态方法 vs 实例方法

| 对比 | 静态方法（static） | 实例方法 |
|------|-------------------|---------|
| 修饰 | 有 `static` 修饰 | 无 `static` 修饰 |
| 归属 | **属于类** | 属于对象 |
| 访问 | `类名.静态方法()` | `对象名.实例方法()` |
| 直接访问静态成员 | ✅ 可以 | ✅ 可以 |
| 直接访问实例成员 | ❌ 不可以 | ✅ 可以 |
| 使用 this | ❌ 不可以 | ✅ 可以 |

### 示例

```java
public class Student {
    static String school;
    int age;

    // 静态方法
    public static void printSchool() {
        System.out.println(school);   // ✅ 可直接访问静态成员
        // System.out.println(age);   // ❌ 不可访问实例成员
    }

    // 实例方法
    public void printAge() {
        System.out.println(age);       // ✅ 可访问实例成员
        System.out.println(school);    // ✅ 也可访问静态成员
    }
}
```

### 访问方式

```java
// 静态方法——类名调用（推荐）
Student.printSchool();

// 实例方法——对象调用
Student s = new Student();
s.printAge();

// 同一个类中，类名可以省略
public static void methodA() {
    methodB();    // 相当于 ClassName.methodB()
}
```

### 设计原则

> - 如果方法**只是为了实现一个功能**、**不需要访问对象的数据** → 定义成**静态方法**
> - 如果方法是**对象的行为**、**需要访问对象的数据** → 定义成**实例方法**

---

## 7. static 访问注意事项

### 三条核心规则

```java
public class Test {
    static int staticNum = 10;   // 静态变量
    int instanceNum = 20;        // 实例变量

    // 规则 1：静态方法中可以直接访问静态成员，不可以直接访问实例成员
    public static void staticMethod() {
        System.out.println(staticNum);     // ✅ 可以
        // System.out.println(instanceNum); // ❌ 编译错误
    }

    // 规则 2：实例方法中既可以直接访问静态成员，也可以直接访问实例成员
    public void instanceMethod() {
        System.out.println(staticNum);      // ✅ 可以
        System.out.println(instanceNum);    // ✅ 可以
    }

    // 规则 3：实例方法中可以出现 this，静态方法中不可以出现 this
    public void instanceMethod2() {
        System.out.println(this);           // ✅ this 代表当前对象
    }

    public static void staticMethod2() {
        // System.out.println(this);         // ❌ 静态方法中没有 this
    }
}
```

### 原因理解

- 静态成员**属于类**，类加载时就存在，不需要对象就能访问
- 实例成员**属于对象**，必须创建对象后才存在
- **静态方法在类加载时就存在了，此时还没有对象**，所以无法访问实例成员
- `this` 代表当前对象，静态方法没有"当前对象"，所以不能使用

---

## 8. 工具类设计

### 什么是工具类

**工具类**封装了一些通用的静态方法，供其他类直接调用，无需创建对象。

### 设计规范

> 1. 方法全部是**静态方法**
> 2. **不需要创建对象**
> 3. 建议**构造器私有**（防止别人创建对象）

```java
public class VerifyCodeUtil {
    // 私有构造器——防止外部创建对象
    private VerifyCodeUtil() {
    }

    // 静态方法——直接用类名调用
    public static String getCode(int n) {
        String code = "";
        for (int i = 0; i < n; i++) {
            int type = (int) (Math.random() * 3);
            switch (type) {
                case 0: code += (int) (Math.random() * 10); break;
                case 1: code += (char) ('a' + (int) (Math.random() * 26)); break;
                case 2: code += (char) ('A' + (int) (Math.random() * 26)); break;
            }
        }
        return code;
    }
}
```

### 使用

```java
String code = VerifyCodeUtil.getCode(4);  // 直接类名调用，无需 new
```

> **`Math` 类就是典型的工具类**：所有方法都是静态的，构造器私有，直接 `Math.random()` 调用。

---

## 9. 完整代码示例

| 包 | 文件 | 说明 |
|----|------|------|
| `tang.consturctor` | `Student.java` / `Test.java` | 构造器——无参、有参 |
| `tang.thisdemo` | `Student.java` / `Test.java` | this 关键字 |
| `tang.capsulation` | `Student.java` / `Test.java` | 封装——private + get/set |
| `tang.javaBean` | `Student.java` / `Test.java` / `StudentOperator.java` | JavaBean 实体类 + 业务类分离 |
| `tang.staticdemo` | `Student.java` / `User.java` / `Test1.java` / `Test2.java` | static 变量——共享特性 |
| `tang.staticmethod` | `Student.java` / `Test.java` / `VerifyCodeUtil.java` / `Test2.java` / `Test3.java` | static 方法——工具类 |
| `tang.ooptwo` | `OopAdvancedDemo.java` | **综合演示**：全部知识点（推荐运行）|

---

*本笔记基于 2026-07-27 的学习内容整理，对应 `day05-object` 模块。*
