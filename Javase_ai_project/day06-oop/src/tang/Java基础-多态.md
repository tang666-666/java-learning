# Java 基础：多态（Polymorphism）

> 对应模块：`day06-oop`
> 学习日期：2026-07-29

---

## 目录

1. [多态概述](#1-多态概述)
2. [多态的前提](#2-多态的前提)
3. [多态的注意事项](#3-多态的注意事项)
4. [多态的好处](#4-多态的好处)
5. [多态存在的问题](#5-多态存在的问题)
6. [多态下的类型转换](#6-多态下的类型转换)
7. [instanceof 关键字](#7-instanceof-关键字)
8. [完整代码示例](#8-完整代码示例)

---

## 1. 多态概述

**多态（Polymorphism）** 是面向对象三大特征之一，指在**继承/实现**情况下的一种现象。

### 两种表现

| 表现 | 说明 |
|------|------|
| **对象多态** | 父类引用可以指向不同的子类对象 |
| **行为多态** | 同一个方法调用，在不同对象上表现出不同行为 |

```java
// 对象多态：Animal 引用指向 Wolf 对象、Tortoise 对象
Animal a1 = new Wolf();       // a1 是狼
Animal a2 = new Tortoise();   // a2 是乌龟

// 行为多态：同一个 run()，表现不同
a1.run();   // 狼跑的很快
a2.run();   // 乌龟跑的很慢
```

---

## 2. 多态的前提

### 三个前提（缺一不可）

| 前提 | 说明 |
|------|------|
| **① 有继承/实现关系** | 子类 extends 父类，或 implements 接口 |
| **② 存在父类引用子类对象** | `父类 变量 = new 子类()` |
| **③ 存在方法重写** | 子类重写父类的方法 |

```java
// ① 继承关系
class Wolf extends Animal { ... }
class Tortoise extends Animal { ... }

// ② 父类引用子类对象 + ③ 方法重写
Animal a = new Wolf();   // Wolf 重写了 run()
a.run();                 // 调用的是 Wolf 重写后的方法
```

### 核心口诀

> **方法：编译看左边（父类），运行看右边（子类）**

```java
Animal a = new Wolf();
a.run();
// 编译阶段：看左边 Animal，确认有 run() 方法 → 通过编译
// 运行阶段：看右边 Wolf，执行 Wolf 重写后的 run() → "狼跑的很快"
```

---

## 3. 多态的注意事项

> **多态是对象、行为的多态，Java 中的属性（成员变量）不谈多态。**

### 成员变量：编译看左边，运行也看左边

```java
class Animal {
    String name = "动物";
    public void run() { ... }
}

class Wolf extends Animal {
    String name = "狼";   // 子类的 name（不是重写，是新建）
    @Override
    public void run() { ... }
}

Animal a = new Wolf();
System.out.println(a.name);   // "动物"（看左边 Animal 的 name）
```

| 成员 | 编译 | 运行 | 结果 |
|------|------|------|------|
| **成员方法** | 看左边（父类） | 看右边（子类） | 多态生效 |
| **成员变量** | 看左边（父类） | 看左边（父类） | 多态不生效 |

---

## 4. 多态的好处

### 好处 1：解耦合，便于扩展维护

> 多态形式下，**右边对象是解耦合的**，换一个子类对象不用改代码。

```java
// 右边换对象，左边代码不用改
Animal a = new Wolf();      // 改成 Tortoise 只需要换右边
a.run();                    // 无需改动这一行
```

### 好处 2：父类类型作形参，接受一切子类对象

> 定义方法时，使用**父类类型的形参**，可以接受**一切子类对象**，拓展性更强。

```java
// 一个方法，接受所有动物
public static void go(Animal a) {
    System.out.println("开始");
    a.run();   // 不同动物表现不同
}

go(new Wolf());      // 传狼
go(new Tortoise());  // 传乌龟
// 以后新增 Dog、Cat，都不用改 go 方法
```

---

## 5. 多态存在的问题

> **多态下不能使用子类的独有功能。**

```java
class Tortoise extends Animal {
    public void run() { ... }
    public void shrinkHead() { ... }   // 乌龟独有功能
}

Animal a = new Tortoise();
a.run();            // ✅ 可以（父类也有 run）
// a.shrinkHead();  // ❌ 编译错误！父类 Animal 没有 shrinkHead()
```

### 原因

编译阶段看的是**左边（父类类型）**，父类没有 `shrinkHead()` 方法，所以编译不过。

### 解决方式

需要调用子类独有功能时，**先强转回子类类型**：

```java
Animal a = new Tortoise();
Tortoise t = (Tortoise) a;   // 强转回乌龟类型
t.shrinkHead();              // ✅ 可以调用子类独有功能
```

---

## 6. 多态下的类型转换

### 自动类型转换（向上转型）

```java
// 父类 变量名 = new 子类();
Animal a = new Wolf();       // 自动向上转型，无需写 (Animal)
```

- 子类对象可以直接赋值给父类引用
- 编译和运行都不报错

### 强制类型转换（向下转型）

```java
// 子类 变量名 = (子类) 父类变量;
Animal a = new Wolf();
Wolf w = (Wolf) a;           // 向下转型，需要写 (Wolf)
```

### 强制类型转换的注意事项（重点）

| 阶段 | 行为 |
|------|------|
| **编译阶段** | 只要存在继承/实现关系就可以强转，**编译不会报错** |
| **运行阶段** | 如果对象的**真实类型**与强转后的类型不同，**报类型转换错误** |

```java
Animal a = new Wolf();       // a 真实类型是 Wolf
Tortoise t = (Tortoise) a;   // 编译通过（有继承关系）
// 运行报错！ClassCastException
// 因为 a 的真实类型是 Wolf，不是 Tortoise
```

### 错误演示

```java
// ❌ 编译通过，运行报 ClassCastException
Animal a = new Wolf();
Tortoise t = (Tortoise) a;   // 狼不能强转成乌龟！
t.shrinkHead();              // 这里运行时报错
```

---

## 7. instanceof 关键字

### 作用

> 强转前建议使用 `instanceof` 关键字，**判断当前对象的真实类型**，再进行强转。

### 语法

```java
对象 instanceof 类型    // 返回 boolean
```

### 示例

```java
public static void go(Animal a) {
    System.out.println("开始");
    a.run();

    // 强转前先判断真实类型
    if (a instanceof Wolf) {
        Wolf w = (Wolf) a;      // 确认是狼才强转
        w.eatSheep();           // 调用狼的独有功能
    } else if (a instanceof Tortoise) {
        Tortoise t = (Tortoise) a;   // 确认是乌龟才强转
        t.shrinkHead();              // 调用乌龟的独有功能
    }
}
```

### 使用建议

- 强转前先用 `instanceof` 判断，避免 `ClassCastException`
- `instanceof` 返回 `true` 时，强转一定安全

---

## 8. 完整代码示例

| 包 | 说明 |
|----|------|
| `tang.polymophsm1` | 对象多态、行为多态（编译看左，运行看右）|
| `tang.polymophsm2` | 多态的好处（解耦合、父类作形参）|
| `tang.polymophsm3` | 类型转换 + instanceof 判断 |
| `tang.demo` | 加油站支付小程序（多态综合应用）|
| `tang.polymophsmdemo` | **综合演示**：全部知识点（推荐运行）|

---

*本笔记基于 2026-07-29 的学习内容整理，对应 `day06-oop` 模块。*
