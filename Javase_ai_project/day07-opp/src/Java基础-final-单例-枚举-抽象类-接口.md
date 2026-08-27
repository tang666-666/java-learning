# Java 基础：final、单例、枚举、抽象类与接口

> 对应模块：`day07-opp`
> 学习日期：2026-07-30

---

## 目录

1. [final 关键字](#1-final-关键字)
2. [常量](#2-常量)
3. [设计模式与单例模式](#3-设计模式与单例模式)
4. [枚举类（enum）](#4-枚举类enum)
5. [抽象类（abstract）](#5-抽象类abstract)
6. [模板方法设计模式](#6-模板方法设计模式)
7. [接口（interface）](#7-接口interface)
8. [JDK8 以后接口的扩展](#8-jdk8-以后接口的扩展)
9. [抽象类和接口的区别](#9-抽象类和接口的区别)
10. [完整代码示例](#10-完整代码示例)

---

## 1. final 关键字

`final` 可修饰**类、方法、变量**，表示"最终"。

### 1.1 修饰类——不能被继承

```java
public final class String { ... }   // 最终类

// class MyString extends String {}  // ❌ 编译错误：final 类不能被继承
```

### 1.2 修饰方法——不能被重写

```java
class Father {
    public final void show() { ... }   // 最终方法
}

class Son extends Father {
    // public void show() { ... }      // ❌ 编译错误：final 方法不能被重写
}
```

### 1.3 修饰变量——只能赋值一次

```java
final int a = 10;
// a = 20;        // ❌ 编译错误：final 变量只能赋值一次
```

### 1.4 final 修饰的两种变量类型

| 类型 | 特点 |
|------|------|
| **基本类型变量** | 变量存储的**数据**不能被改变 |
| **引用类型变量** | 变量存储的**地址**不能被改变，但地址指向的**对象内容**可以改变 |

```java
// 基本类型：值不能变
final int num = 10;
// num = 20;                  // ❌ 错误

// 引用类型：地址不能变，但内容可以变
final int[] arr = {1, 2, 3};
// arr = new int[]{4,5,6};    // ❌ 错误：不能改地址
arr[0] = 99;                  // ✅ 可以：修改对象内容
```

---

## 2. 常量

### 定义

> 使用 `static final` 修饰的成员变量被称为**常量**。

```java
public static final String NAME = "汤宇轩";
public static final int MAX_COUNT = 100;
```

### 命名规范

常量名**全大写**，多个单词用下划线分隔：

```java
public static final double PI = 3.14159;
public static final int MAX_VALUE = 999;
```

### 常量的作用

| 作用 | 说明 |
|------|------|
| **记录配置信息** | 如数据库地址、公司名称 |
| **可读性更好** | 见名知意，不用猜 `0` `1` 是什么意思 |
| **可维护性更好** | 改一处，全局生效 |

### 编译后的宏替换（性能）

> 程序编译后，常量会被"**宏替换**"——出现常量的地方全部替换成其记住的字面量。

```java
// 源码
public static final int MAX = 100;
int a = MAX;

// 编译后等价于
int a = 100;   // MAX 被直接替换成 100
```

> 因此使用常量和直接使用字面量的**性能是一样的**，但可读性更好。

---

## 3. 设计模式与单例模式

### 什么是设计模式

> **设计模式** 是解决某类问题的**最优方案**（前人总结的经验）。

学习设计模式要问两个问题：
1. **解决什么问题**（Why）
2. **怎么写**（How）

### 单例设计模式

**作用**：确保每个类**只能创建一个对象**。

### 饿汉式单例

> **拿对象时，对象早就创建好了**（类加载时就创建）。

#### 步骤

| 步骤 | 说明 |
|------|------|
| **① 构造器私有** | 防止外部 new 对象 |
| **② 定义静态变量记住类的一个对象** | 类加载时创建唯一对象 |
| **③ 定义类方法返回对象** | 提供对外获取入口 |

```java
public class A {
    // ② 静态变量记住唯一对象（类加载时就 new）
    private static A a = new A();

    // ① 私有化构造器
    private A() {
    }

    // ③ 提供静态方法返回对象
    public static A getInstance() {
        return a;
    }
}
```

### 懒汉式单例

> **拿对象时，才开始创建对象**（第一次调用时才创建）。

#### 步骤

| 步骤 | 说明 |
|------|------|
| **① 构造器私有** | 防止外部 new 对象 |
| **② 定义静态变量存储对象** | 不立即 new，先声明 |
| **③ 提供静态方法保证返回同一对象** | 第一次调用时才创建 |

```java
public class B {
    // ② 静态变量（不立即 new）
    private static B b;

    // ① 私有化构造器
    private B() {
    }

    // ③ 提供静态方法，保证返回同一个对象
    public static B getInstance() {
        if (b == null) {
            b = new B();    // 第一次调用时才创建
        }
        return b;
    }
}
```

### 饿汉式 vs 懒汉式

| 对比 | 饿汉式 | 懒汉式 |
|------|--------|--------|
| 创建时机 | 类加载时就创建 | 第一次获取时才创建 |
| 特点 | 提前占用内存 | 延迟创建（懒加载）|
| 效率 | 获取快（直接返回）| 首次获取慢（要判断+创建）|
| 线程安全 | 天然安全 | 需注意（多线程下可能创建多个）|

---

## 4. 枚举类（enum）

### 定义

> **枚举类** 是一种特殊类，用 `enum` 关键字定义。

```java
public enum Direction {
    UP, DOWN, LEFT, RIGHT;
}
```

### 特点

| 特点 | 说明 |
|------|------|
| **第一行只能写对象名称** | 且用**逗号**隔开，分号结尾 |
| **本质是常量** | 每个常量都记住枚举类的一个对象 |
| **最终类** | 枚举类都是 final，**不可以被继承** |
| **构造器私有** | 构造器都是私有的（写不写都只能是私有的），对外不能创建对象 |

### 枚举的底层本质

```java
// 上面的枚举类等价于：
public final class Direction {
    public static final Direction UP = new Direction();
    public static final Direction DOWN = new Direction();
    public static final Direction LEFT = new Direction();
    public static final Direction RIGHT = new Direction();

    private Direction() { ... }   // 构造器私有
}
```

### 应用场景

> **信息分类和标志** —— 用枚举表示一组固定的选项。

```java
public enum Season {
    SPRING, SUMMER, AUTUMN, WINTER;
}

// 使用
Season s = Season.SPRING;
```

---

## 5. 抽象类（abstract）

### 定义

`abstract` 可以修饰**类**和**成员方法**。

- **抽象方法**：只有方法签名，没有方法体（不能写 `{}`）
- **抽象类**：用 `abstract` 修饰的类

```java
public abstract class Animal {
    // 抽象方法：只有方法声明，没有方法体
    public abstract void cry();

    // 普通方法（可以有方法体）
    public void sleep() {
        System.out.println("动物会睡觉");
    }
}
```

### 特点

> **抽象类不能创建对象**，仅作为一种特殊的父类，让子类继承并实现。

```java
// Animal a = new Animal();  // ❌ 编译错误：抽象类不能创建对象
```

### 注意事项

| 注意点 | 说明 |
|--------|------|
| **① 抽象类中不一定有抽象方法** | 有抽象方法的类**必须**是抽象类 |
| **② 类有的成员抽象类都可以有** | 成员变量、方法、构造器都可以 |
| **③ 继承必须重写完所有抽象方法** | 否则子类也必须定义为抽象类 |

```java
// 有抽象方法的类必须是抽象类
public abstract class A {
    public abstract void show();   // 抽象方法
}

// 子类必须重写所有抽象方法
public class B extends A {
    @Override
    public void show() { ... }     // 必须重写，否则 B 也要抽象
}
```

---

## 6. 模板方法设计模式

### 什么是模板方法

> 提供一个方法作为完成某类功能的**模板**，模板方法封装了每个实现步骤，但**允许子类提供特定步骤的实现**。

### 作用

- 提高代码的复用
- 简化子类设计

### 写法

| 步骤 | 说明 |
|------|------|
| **① 定义一个抽象类** | 作为模板 |
| **② 定义模板方法** | 把共同的实现步骤放里面（通常用 `final` 修饰防止被改）|
| **③ 定义抽象方法** | 不确定的实现步骤，交给具体子类完成 |

### 示例

```java
// ① 抽象类模板
public abstract class People {
    // ② 模板方法：封装共同步骤（final 防止被子类修改）
    public final void write() {
        System.out.println("\t《我的爸爸》");
        System.out.println("\t我的爸爸是个好人");
        writeMain();              // ③ 调用抽象方法（不同子类不同实现）
        System.out.println("\t我的爸爸很好");
    }

    // ③ 抽象方法：交给子类实现
    public abstract void writeMain();
}

// 子类实现
public class Student extends People {
    @Override
    public void writeMain() {
        System.out.println("\t我的爸爸会写代码");
    }
}
```

---

## 7. 接口（interface）

### 定义

**接口** 用 `interface` 关键字定义。JDK8 之前只能写**成员变量（常量）+ 成员方法（抽象方法）**。

```java
public interface A {
    // 1. 常量：可省略 public static final
    public static final String NAME = "汤宇轩";
    // 等价于：String NAME = "汤宇轩";

    // 2. 抽象方法：可省略 public abstract
    public abstract void run();
    // 等价于：void run();
}
```

### 特点

- 接口**不能创建对象**
- 接口是用来被**实现（implements）** 的
- 实现接口的类称为**实现类**
- **一个类可以实现多个接口**

```java
public class B implements A, C, D {   // 实现多个接口
    @Override
    public void run() { ... }
}
```

### 接口的好处

| 好处 | 说明 |
|------|------|
| **① 弥补类单继承的不足** | 一个类可同时实现多个接口，角色更多、功能更强大 |
| **② 面向接口编程** | 灵活切换各种业务实现，更利于**解耦合** |

---

## 8. JDK8 以后接口的扩展

JDK8 以后接口增强了能力，更便于项目拓展维护。

### ① 默认方法（实例方法）

```java
public interface A {
    // 用 default 修饰，默认会加 public
    default void show() {
        System.out.println("默认方法");
    }
}
```

- 使用 `default` 修饰
- 默认被加上 `public` 修饰
- **只能用接口的实现类对象调用**

### ② 私有方法

```java
public interface A {
    // 用 private 修饰
    private void helper() {
        System.out.println("私有方法");
    }

    default void show() {
        helper();   // 供接口中的其他实例方法调用
    }
}
```

- 必须用 `private` 修饰
- 使用接口中的**其他实例方法**来调用它

### ③ 类方法（静态方法）

```java
public interface A {
    // 用 static 修饰，默认会加 public
    static void method() {
        System.out.println("静态方法");
    }
}
```

- 使用 `static` 修饰
- 默认被加上 `public` 修饰
- **只能用接口名来调用**

### 接口的注意事项

| 注意点 | 说明 |
|--------|------|
| **① 接口与接口可以多继承** | 一个接口可同时继承多个接口 |
| **② 方法签名冲突不支持多继承** | 多个接口存在方法签名冲突时，不支持多继承/多实现 |
| **③ 父类优先** | 类继承父类又实现接口，同名默认方法优先用父类的 |
| **④ 多个接口同名默认方法** | 实现类重新该方法即可（不冲突）|

---

## 9. 抽象类和接口的区别

### 相同点

| 相同点 | 说明 |
|--------|------|
| 都是抽象形式 | 都可有抽象方法，都不能创建对象 |
| 都是派生子类形式 | 抽象类被继承，接口被实现 |
| 都必须重写完抽象方法 | 否则自己成为抽象类或报错 |
| 都支持多态 | 都能实现解耦合 |

### 不同点

| 对比 | 抽象类 | 接口 |
|------|--------|------|
| **成员** | 可定义类的全部普通成员 | 只能定义常量和抽象方法（JDK8 及以前）|
| **继承/实现** | 只能被类**单继承** | 可以被类**多实现** |
| **与其他类/接口组合** | 继承抽象类就不能再继承其他类 | 实现接口后还能继承其他类或实现其他接口 |
| **设计思想** | 体现**模型思想**，利于做父类，实现代码复用 | 更适合做功能**解耦合**，解耦性更强 |

### 一句话总结

> **抽象类**：是"**是什么**"——定义一个事物的抽象模型（如"动物"）。
> **接口**：是"**能干什么**"——定义一个能力（如"会飞"、"会游泳"）。

---

## 10. 完整代码示例

| 包 | 说明 |
|----|------|
| `tang.finalDemo` | final 关键字 + 常量 |
| `tang.singleinstance` | 单例模式（饿汉式 A、懒汉式 B）|
| `tang.enumdemo` | 枚举类 |
| `tang.abstract1` | 抽象类基础 |
| `tang.abstact2` | 抽象类——动物体系 |
| `tang.abstract3` | 模板方法设计模式 |
| `tang.interface1~5` | 接口基础 + 扩展 |
| `tang.demo` | 智能家居综合案例（接口应用）|
| `tang.keyworddemo` | **综合演示**：全部知识点（推荐运行）|

---

*本笔记基于 2026-07-30 的学习内容整理，对应 `day07-opp` 模块。*
