# Java 基础：继承（extends）

> 对应模块：`day06-oop`
> 学习日期：2026-07-28

---

## 目录

1. [继承概述](#1-继承概述)
2. [继承的基本语法](#2-继承的基本语法)
3. [权限修饰符](#3-权限修饰符)
4. [继承的特点](#4-继承的特点)
5. [super 关键字](#5-super-关键字)
6. [方法重写（@Override）](#6-方法重写override)
7. [子类构造器](#7-子类构造器)
8. [this 调用兄弟构造器](#8-this-调用兄弟构造器)
9. [完整代码示例](#9-完整代码示例)

---

## 1. 继承概述

**继承（Inheritance）** 让一个类（子类）获得另一个类（父类）的成员，是面向对象三大特征之一。

### 继承的目的

> **提高代码的重用性**，减少重复代码的书写。

```java
// 不使用继承：Teacher 和 Consultant 有大量重复代码
class Teacher {
    private String name;
    private char sex;
    private String skill;
}

class Consultant {
    private String name;    // 重复！
    private char sex;       // 重复！
    private String advice;
}

// 使用继承：公共部分放到父类
class People {
    private String name;
    private char sex;
}

class Teacher extends People {
    private String skill;
}

class Consultant extends People {
    private String advice;
}
```

### 继承的概念

| 术语 | 说明 |
|------|------|
| **父类**（基类/超类） | 被继承的类 |
| **子类**（派生类） | 继承父类的类 |
| **extends** | 继承关键字 |

### 子类能继承什么

- ✅ 父类的**非私有成员**（成员变量、成员方法）
- ❌ 父类的 `private` 私有成员（不能直接访问）

### 子类对象的组成

> **子类的对象是由子类、父类共同完成的。**

```
子类对象 = 父类部分 + 子类自己部分
```

创建子类对象时，父类和子类的成员变量都会分配内存。

---

## 2. 继承的基本语法

```java
public class 子类 extends 父类 {
    // 子类自己的成员
}
```

### 示例

```java
// 父类
public class People {
    private String name;
    private char sex;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public char getSex() { return sex; }
    public void setSex(char sex) { this.sex = sex; }
}

// 子类继承父类
public class Teacher extends People {
    private String skill;   // 子类自己的成员

    public String getSkill() { return skill; }
    public void setSkill(String skill) { this.skill = skill; }
}
```

### 使用

```java
Teacher t = new Teacher();
t.setName("张老师");    // ✅ 父类的成员，子类对象可以直接用
t.setSex('男');          // ✅ 父类的成员
t.setSkill("Java");      // ✅ 子类自己的成员
```

---

## 3. 权限修饰符

**权限修饰符** 限制类中的成员（成员变量、成员方法、构造器）能够被访问的范围。

### 四种权限修饰符

| 修饰符 | 本类 | 同包类 | 子孙类 | 任意位置 |
|--------|:---:|:---:|:---:|:---:|
| `private` | ✅ | ❌ | ❌ | ❌ |
| 缺省（默认） | ✅ | ✅ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `public` | ✅ | ✅ | ✅ | ✅ |

### 权限大小排序

```
private  <  缺省  <  protected  <  public
```

### 示例

```java
public class Father {
    private void privateMethod() { ... }        // 只能本类
    void defaultMethod() { ... }                // 本类 + 同包
    protected void protectedMethod() { ... }    // 本类 + 同包 + 子类
    public void publicMethod() { ... }          // 任意位置
}
```

> **记忆：** 除了 `private` 和 `protected`，其余和 C++ 的访问控制逻辑类似。

---

## 4. 继承的特点

### 特点 1：单继承

> **一个类只能有一个"爸爸"**——Java 只支持单继承。

```java
class A extends B { }   // ✅
// class C extends B, D { }  // ❌ Java 不支持多继承
```

### 特点 2：多层继承

> 可以有"爷爷"、"太爷"——继承可以有多层。

```java
class A {}            // 爷爷
class B extends A {}  // 爸爸
class C extends B {}  // 儿子
```

### 特点 3：祖宗类 Object

> 每个类都直接或间接继承 `Object` 类。

```
Object（祖宗类）
  ↑
  A
  ↑
  B
  ↑
  C
```

- 一个类要么直接继承 `Object`，要么默认继承 `Object`，要么间接继承 `Object`
- 所有类最终都是 `Object` 的子孙类

### 特点 4：就近原则（变量查找顺序）

> 成员变量（`this.xxx`）查找顺序：**子类局部范围 → 子类成员范围 → 父类成员范围**，父类也没有则报错。

```java
class Father {
    int num = 10;       // 父类成员
}

class Son extends Father {
    int num = 20;       // 子类成员

    public void test() {
        int num = 30;   // 子类局部变量
        System.out.println(num);         // 30（先找局部）
        System.out.println(this.num);    // 20（再找子类成员）
        System.out.println(super.num);   // 10（指定父类成员）
    }
}
```

### 成员方法查找

方法重名时：**子类优先**（子类方法覆盖父类方法，即重写）。

---

## 5. super 关键字

### 作用

当子类和父类中出现了**重名的成员**时，用 `super` 指定访问**父类**的成员。

```java
super.父类成员变量    // 访问父类的成员变量
super.父类方法()      // 调用父类的方法
super()              // 调用父类的构造器
```

### this vs super

| 关键字 | 指向 |
|--------|------|
| `this` | 当前对象的成员 |
| `super` | 父类对象的成员 |

```java
public class Son extends Father {
    int num = 20;

    public void show() {
        System.out.println(num);         // 20（子类）
        System.out.println(this.num);    // 20（子类）
        System.out.println(super.num);   // 10（父类）
    }
}
```

> **记忆：** `this` 找自己，`super` 找爸爸。

---

## 6. 方法重写（@Override）

### 什么是方法重写

当子类觉得父类的某个方法**不好用**或**无法满足自己的需求**时，可以重写一个**方法名称、参数列表一样**的方法，去**覆盖**父类的方法。

### 语法

```java
@Override   // 重写校验注解：要求方法名和形参列表必须与被重写方法一致，否则报错
public void methodName(参数) {
    // 子类自己的实现
}
```

### 示例

```java
class Animal {
    public void cry() {
        System.out.println("动物会叫");
    }
}

class Cat extends Animal {
    @Override
    public void cry() {        // 重写父类的 cry()
        System.out.println("喵喵喵");
    }
}
```

### 重写注意事项

| 注意点 | 说明 |
|--------|------|
| **① 访问权限** | 子类重写方法的访问权限必须**大于等于**父类方法 |
| **② 返回值** | 返回值类型必须一样（或更小的类型）|
| **③ 不可重写** | `private`、`static` 方法不能被重写 |

```java
class Father {
    public void show() { ... }
}

class Son extends Father {
    // ✅ 权限 public >= public
    public void show() { ... }

    // ❌ 权限缩小：protected < public，编译错误
    // protected void show() { ... }
}
```

### 应用场景：重写 toString()

子类可以重写 `Object` 类的 `toString()` 方法，以便返回对象的内容。

```java
public class Student {
    private String name;
    private int age;

    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + "}";
    }
}
```

> IDEA 快捷键：右键 → Generate → toString()，自动生成。

---

## 7. 子类构造器

### 特点

> **子类的全部构造器，都会先调用父类的构造器，再执行自己的构造器。**

```
子类构造器执行过程：
1. 先调用父类构造器（完成父类部分初始化）
2. 再执行子类自己的代码（完成子类部分初始化）
```

### 实现机制

#### 情况一：父类有无参构造器

默认情况下，子类全部构造器的第一行代码都是 `super()`（写不写都有），调用父类的**无参构造器**。

```java
class Father {
    public Father() {
        System.out.println("父类无参构造器");
    }
}

class Son extends Father {
    public Son() {
        // super();  // 默认存在，可省略
        System.out.println("子类构造器");
    }
}

// 创建子类对象输出：
// 父类无参构造器
// 子类构造器
```

#### 情况二：父类没有无参构造器

如果父类只有有参构造器（没有无参），则必须在子类构造器第一行**手写 `super(参数)`**，指定调用父类的有参构造器。

```java
class Father {
    private String name;

    public Father(String name) {   // 只有有参构造器
        this.name = name;
    }
}

class Son extends Father {
    public Son(String name) {
        super(name);   // ❗ 必须手写，调用父类的有参构造器
        // 如果不写，编译错误
    }
}
```

---

## 8. this 调用兄弟构造器

### 概念

`this(参数)` 可以在一个构造器中调用**本类（同一个类）的其他构造器**，称为"兄弟构造器"。

```java
public class Student {
    private String name;
    private int age;

    // 无参构造器调用有参构造器
    public Student() {
        this("未命名", 0);   // this 调用本类的有参构造器
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

### 注意事项（重点）

> 1. `super()` 和 `this()` 都**必须写在构造器的第一行**
> 2. `super()` 和 `this()` **不能同时出现**（都要求第一行，放不下两个）

```java
public class Student {
    public Student() {
        super();       // ✅ 第一行调用父类构造器
        // this("xx"); // ❌ 不能再同时写 this()
    }

    public Student(String name) {
        this();        // ✅ 第一行调用兄弟构造器
        // super();    // ❌ 不能再同时写 super()
    }
}
```

### super() 和 this() 的选择

| 写法 | 调用谁 | 何时使用 |
|------|--------|---------|
| `super(参数)` | 父类构造器 | 需要先初始化父类部分 |
| `this(参数)` | 本类其他构造器 | 复用本类其他构造器的初始化逻辑 |

---

## 9. 完整代码示例

| 包 | 说明 |
|----|------|
| `tang.extends1demo` | 继承基本用法（People/Teacher/Consultant）|
| `tang.extends2modifier` | 权限修饰符（private/缺省/protected/public）|
| `tang.extends3modifier` | 修饰符跨包访问演示 |
| `tang.extends4feature` | 继承特点（单继承/多层继承/Object）|
| `tang.extends5override` | 方法重写（@Override）|
| `tang.extends6constructor` | 子类构造器（super() 调用链）|
| `tang.inheritancedemo` | **综合演示**：全部知识点（推荐运行）|

---

*本笔记基于 2026-07-28 的学习内容整理，对应 `day06-oop` 模块。*
