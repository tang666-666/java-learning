# Java 基础：代码块、内部类、Lambda、方法引用、String、ArrayList

> 对应模块：`day08-oop`
> 学习日期：2026-07-31

---

## 目录

1. [代码块](#1-代码块)
2. [内部类概述](#2-内部类概述)
3. [成员内部类](#3-成员内部类)
4. [静态内部类](#4-静态内部类)
5. [局部内部类](#5-局部内部类)
6. [匿名内部类](#6-匿名内部类)
7. [函数式编程与 Lambda](#7-函数式编程与-lambda)
8. [Lambda 表达式省略规则](#8-lambda-表达式省略规则)
9. [方法引用](#9-方法引用)
10. [API：String](#10-apistring)
11. [ArrayList 集合](#11-arraylist-集合)
12. [GUI 编程](#12-gui-编程)
13. [完整代码示例](#13-完整代码示例)

---

## 1. 代码块

### 1.1 静态代码块

```java
static {
    // 代码
}
```

| 特点 | 说明 |
|------|------|
| **格式** | `static { }` |
| **执行时机** | **类加载时自动执行** |
| **执行次数** | 类只加载一次 → **只执行一次** |
| **作用** | 完成类的初始化（如对静态变量赋值）|

```java
public class CodeDemo {
    public static String schoolName;
    public static String[] cards = new String[54];

    // 静态代码块：类加载时自动执行一次
    static {
        schoolName = "汤宇轩";
        cards[0] = "A";
        cards[1] = "B";
    }
}
```

### 1.2 实例代码块

```java
{
    // 代码
}
```

| 特点 | 说明 |
|------|------|
| **格式** | `{ }` |
| **执行时机** | **每次创建对象时执行**，且在**构造器前执行** |
| **执行次数** | 创建多少次对象，执行多少次 |
| **作用** | 完成对象的初始化（如对实例变量赋值）|

```java
public class Student {
    private String name;

    // 实例代码块：每次 new 对象时，在构造器前执行
    {
        System.out.println("实例代码块执行");
    }

    public Student() {
        System.out.println("构造器执行");
    }
}
// 执行顺序：实例代码块 → 构造器
```

### 代码块执行顺序总结

```
类加载时：静态代码块（一次）
创建对象时：实例代码块（先）→ 构造器（后）
```

---

## 2. 内部类概述

### 什么是内部类

> 一个类定义在**另一个类的内部**，这个类就是内部类。

### 使用场景

> 当一个类的内部，**包含了一个完整的事物**，且这个事物**没有必要单独设计**时，就可以把它设计成内部类。

### 内部类的种类

| 种类 | 说明 |
|------|------|
| **成员内部类** | 类中的一个普通成员 |
| **静态内部类** | 有 `static` 修饰 |
| **局部内部类** | 定义在方法、代码块、构造器等执行体中 |
| **匿名内部类** | 特殊的局部内部类，没有类名 |

---

## 3. 成员内部类

无 `static` 修饰，属于**外部类的对象**持有。

### 创建对象格式

```java
外部类名称.内部类名称 对象名 = new 外部类名称().new 内部类名称();
```

```java
Outer.Inner inner = new Outer().new Inner();
```

### 示例

```java
public class Outer {
    private int age = 18;

    // 成员内部类
    public class Inner {
        public void show() {
            System.out.println(age);          // 可以访问外部类成员
            System.out.println(this);          // 内部类自己的对象
            System.out.println(Outer.this);    // 寄生的外部类对象
        }
    }
}
```

### 访问规则

- 成员内部类可以访问外部类的**所有成员**（包括私有）
- `Outer.this` 表示外部类的当前对象
- 成员内部类依赖外部类对象存在

---

## 4. 静态内部类

有 `static` 修饰，**属于外部类自己持有**。

### 创建对象格式

```java
外部类名.内部类名 对象名 = new 外部类.内部类();
```

```java
Outer.Inner inner = new Outer.Inner();
```

### 与成员内部类的区别

| 对比 | 成员内部类 | 静态内部类 |
|------|-----------|-----------|
| 修饰 | 无 `static` | 有 `static` |
| 归属 | 属于外部类对象 | 属于外部类本身 |
| 创建格式 | `new Outer().new Inner()` | `new Outer.Inner()` |
| 依赖外部类对象 | 是 | 否 |

---

## 5. 局部内部类

> 定义在**方法中、代码块中、构造器等执行体**中的类。

```java
public class Outer {
    public void test() {
        // 局部内部类：定义在方法中
        class Inner {
            public void show() {
                System.out.println("局部内部类");
            }
        }

        Inner inner = new Inner();
        inner.show();
    }
}
```

- 只在所在的方法/代码块内可见
- 方法结束后无法访问

---

## 6. 匿名内部类

> 一种**特殊的局部内部类**，程序员**不需要为这个类声明名字**，默认有个隐藏的名字。

### 本质与特点

| 特点 | 说明 |
|------|------|
| **本质就是一个子类** | 继承了某个类或实现了某个接口 |
| **会立即创建出子类对象** | 边定义边 new |

### 格式

```java
new 类或接口(参数值) {
    类体（重写的方法）;
};
```

### 示例

```java
// 匿名内部类：创建 Swim 接口的一个匿名实现类对象
Swim s = new Swim() {
    @Override
    public void swimming() {
        System.out.println("学生游泳");
    }
};
s.swimming();
```

### 在开发中的常见形式

> 通常作为一个**对象参数传输给方法**。

```java
public static void testSwim(Swim s) {
    s.swimming();
}

// 调用时直接传匿名内部类对象
testSwim(new Swim() {
    @Override
    public void swimming() {
        System.out.println("在测试中游泳");
    }
});
```

### 应用场景

> 调用别人提供的方法需求时，这个方法正好可以让我们**传输一个匿名内部类对象**给其使用。

---

## 7. 函数式编程与 Lambda

### 函数式编程思想

> 类似于数学中的函数，**强调"做什么"**，只要输入的数据一致，返回的结果也是一致的。

### Lambda 表达式

> 用于**替代某些匿名内部类对象**，从而让程序更简洁、可读性更好。

### 格式

```java
(被重写方法的形参列表) -> { 被重写方法的方法体代码 }
```

### 示例：替代匿名内部类

```java
// 匿名内部类写法
Swim s1 = new Swim() {
    @Override
    public void swimming() {
        System.out.println("学生游泳");
    }
};

// Lambda 写法
Swim s2 = () -> {
    System.out.println("学生游泳");
};
```

### 函数式接口

> **Lambda 并不是能简化全部匿名内部类**，只能简化**函数式接口**的匿名内部类。

**函数式接口**：有且仅有一个抽象方法的接口，可加 `@FunctionalInterface` 注解校验。

```java
@FunctionalInterface
interface Swim {
    void swimming();   // 只有一个抽象方法 → 函数式接口
}
```

---

## 8. Lambda 表达式省略规则

| 规则 | 说明 | 示例 |
|------|------|------|
| **① 参数类型省略** | 参数类型全部可以不写 | `(a, b) -> ...` |
| **② 单参数省略括号** | 只有一个参数时，`()` 也可以省略 | `a -> ...` |
| **③ 多参数不能省括号** | 多个参数不能省略 `()` | `(a, b) -> ...` |
| **④ 单行省略大括号** | 只有一行代码时，大括号可省略，同时省掉分号 | `a -> System.out.println(a)` |
| **⑤ return 省略** | 如果这行代码是 `return` 语句，`return` 也要去掉 | `(a, b) -> a + b` |

### 完整省略示例

```java
// 完整写法
Arrays.sort(students, (Student o1, Student o2) -> {
    return o1.getAge() - o2.getAge();
});

// 省略参数类型
Arrays.sort(students, (o1, o2) -> {
    return o1.getAge() - o2.getAge();
});

// 单行 + 省略 return + 省略大括号和分号
Arrays.sort(students, (o1, o2) -> o1.getAge() - o2.getAge());
```

---

## 9. 方法引用

> 方法引用是 Lambda 的**进一步简写**，让代码更简洁。

### 9.1 静态方法引用

```java
// 格式
类名::静态方法
```

**使用场景**：某个 Lambda 表达式里只是**调用一个静态方法**，且 `->` 前后参数形式一致。

```java
// Lambda
Arrays.sort(students, (o1, o2) -> Student.compareByAge(o1, o2));
// 方法引用
Arrays.sort(students, Student::compareByAge);
```

### 9.2 实例方法引用

```java
// 格式
对象名::实例方法
```

**使用场景**：某个 Lambda 表达式里只是**通过对象名调用实例方法**，且 `->` 前后参数形式一致。

```java
Student t = new Student();
// Lambda
Arrays.sort(students, (o1, o2) -> t.compareByHeight(o1, o2));
// 方法引用
Arrays.sort(students, t::compareByHeight);
```

### 9.3 特定类型的方法引用

```java
// 格式
特定类的名称::方法
```

**使用场景**：Lambda 里只是**调用一个特定类型的实例方法**，且前面参数列表中的**第一个参数作为方法的主调**，后面的所有参数都是该实例方法的入参。

```java
String[] names = {"Tom", "Jerry", "Mike"};
// Lambda
Arrays.sort(names, (o1, o2) -> o1.compareToIgnoreCase(o2));
// 方法引用：o1 是主调（compareToIgnoreCase 的调用者），o2 是入参
Arrays.sort(names, String::compareToIgnoreCase);
```

### 9.4 构造器引用

```java
// 格式
类名::new
```

**使用场景**：某个 Lambda 表达式**只是在创建对象**，且 `->` 前后参数情况一致。

```java
// Lambda
CarFactory cf = name -> new Car(name);
// 构造器引用
CarFactory cf = Car::new;

Car c = cf.getCar("奔驰");
```

---

## 10. API：String

### 概述

> `String` 对象可以**封装字符串数据**，并提供了很多方法完成对字符串的处理。

### 一、创建字符串对象

**方式 1：直接写字符串字面量**

```java
String s1 = "abc";   // 存到字符串常量池
```

**方式 2：调用 String 构造器**

```java
String s2 = new String();               // 空字符串
String s3 = new String("abc");          // 传字符串
char[] chars = {'a', 'b', 'c'};
String s4 = new String(chars);          // 传字符数组
byte[] bytes = {97, 98, 99};
String s5 = new String(bytes);          // 传字节数组
```

### 两种方式的区别（重点）

| 对比 | 直接 `"..."` | `new String(...)` |
|------|-------------|------------------|
| 存储位置 | 字符串**常量池** | 堆内存 |
| 相同内容 | **只存一份**（复用）| 每 new 一次就产生一个新对象 |
| 示例 | `"abc"` 和 `"abc"` 是同一个 | `new` 两次是两个不同对象 |

```java
String t1 = "abc";
String t2 = "abc";
System.out.println(t1 == t2);   // true（常量池复用同一份）

String t3 = new String("abc");
String t4 = new String("abc");
System.out.println(t3 == t4);   // false（堆中两个不同对象）
```

### 二、String 常用方法

| 方法 | 作用 | 示例 |
|------|------|------|
| `length()` | 获取长度 | `"abc".length()` → 3 |
| `equals(字符串)` | **比较内容**（不用 `==`）| `loginName.equals(name)` |
| `equalsIgnoreCase()` | 忽略大小写比较 | |
| `substring(begin, end)` | 截取子串 | `"13512345678".substring(0, 3)` |
| `charAt(index)` | 获取指定位置字符 | |
| `indexOf(字符串)` | 查找第一次出现位置 | |
| `toUpperCase()` / `toLowerCase()` | 转大写 / 小写 | |
| `contains(字符串)` | 是否包含 | |
| `startsWith()` / `endsWith()` | 前后缀判断 | |
| `replace(旧, 新)` | 替换 | |
| `split(正则)` | 分割成数组 | |

> **比较字符串内容要用 `equals()`，不要用 `==`！**（`==` 比较的是地址）

### 案例：手机号脱敏

```java
String phone = "13812345678";
String masked = phone.substring(0, 3) + "****" + phone.substring(7);
System.out.println(masked);   // 138****5678
```

---

## 11. ArrayList 集合

### 概述

> `ArrayList` 是**泛型类**，可以约束存储的数据类型，**大小可变**（自动扩容）。

### 与数组的区别

| 对比 | 数组 | ArrayList |
|------|------|-----------|
| 长度 | 固定 | **可变** |
| 存储类型 | 基本类型 / 引用类型 | 只能引用类型（泛型）|
| 类型 | 不用导包 | `java.util.ArrayList` |

### 创建对象

```java
import java.util.ArrayList;

// 泛型约束：只能存 String
ArrayList<String> list = new ArrayList<>();
ArrayList<Integer> list2 = new ArrayList<>();  // 基本类型要用包装类
```

### 常用方法（增删改查）

| 方法 | 作用 |
|------|------|
| `add(元素)` | 添加元素（尾部）|
| `add(索引, 元素)` | 指定位置插入 |
| `get(索引)` | 获取指定位置元素 |
| `set(索引, 新元素)` | 修改指定位置元素 |
| `remove(索引)` | 按索引删除 |
| `remove(元素)` | 按内容删除 |
| `size()` | 获取元素个数 |
| `contains(元素)` | 是否包含 |
| `isEmpty()` | 是否为空 |
| `clear()` | 清空 |

### 示例

```java
ArrayList<String> list = new ArrayList<>();

// 增
list.add("java");
list.add("python");
list.add("C++");

// 查
System.out.println(list.get(0));    // java
System.out.println(list.size());    // 3

// 遍历
for (int i = 0; i < list.size(); i++) {
    System.out.println(list.get(i));
}

// 删
list.remove(1);          // 按索引删
list.remove("java");     // 按内容删

// 改
list.set(0, "JavaSE");
```

---

## 12. GUI 编程

### 概述

GUI（Graphical User Interface）编程用于开发**图形界面**程序，Java 主要通过 **Swing / AWT** 实现。

### 常用组件

| 组件 | 说明 |
|------|------|
| `JFrame` | 窗口 |
| `JPanel` | 面板（容器）|
| `JButton` | 按钮 |
| `JLabel` | 标签（显示文本）|
| `JTextField` | 文本框 |
| `JTextArea` | 文本域 |

### 最小示例

```java
import javax.swing.*;

public class MyWindow extends JFrame {
    public MyWindow() {
        setTitle("第一个窗口");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MyWindow();
    }
}
```

---

## 13. 完整代码示例

| 包 | 说明 |
|----|------|
| `tang.code` | 静态代码块、实例代码块 |
| `tang.innerclass` | 成员内部类 |
| `tang.innerclass2` | 静态内部类 |
| `tang.innerclass3` | 匿名内部类 |
| `tang.lambda` | Lambda 表达式 |
| `tang.method1` | 方法引用（4 种）|
| `tang.stringdemo` | String 字符串 |
| `tang.arraylist` | ArrayList 集合 |
| `tang.day08demo` | **综合演示**：全部知识点（推荐运行）|

---

*本笔记基于 2026-07-31 的学习内容整理，对应 `day08-oop` 模块。*
