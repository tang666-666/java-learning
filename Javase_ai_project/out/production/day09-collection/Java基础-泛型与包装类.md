# Java 基础：泛型与包装类

> 对应模块：`day09-collection` | 包：`tang.demo2genericity` ~ `tang.demo5genericity`
> 学习日期：2026-08-02

---

## 目录

1. [泛型概述](#1-泛型概述)
2. [泛型类](#2-泛型类)
3. [泛型接口](#3-泛型接口)
4. [泛型方法](#4-泛型方法)
5. [通配符](#5-通配符)
6. [泛型的上下限](#6-泛型的上下限)
7. [泛型擦除](#7-泛型擦除)
8. [包装类](#8-包装类)
9. [自动装箱与自动拆箱](#9-自动装箱与自动拆箱)
10. [类型转换（String ↔ 数值）](#10-类型转换string--数值)
11. [完整代码示例](#11-完整代码示例)

---

## 1. 泛型概述

### 定义

> 定义类、接口、方法时，同时声明了一个或多个**类型变量**（如 `<E>`），称为**泛型类、泛型接口、泛型方法**，统称为**泛型**。

### 作用

> **提供在编译阶段约束所能操作的数据类型，并自动进行检查的能力。**

```java
ArrayList<String> list = new ArrayList<>();
list.add("Java");     // ✅
// list.add(23);      // ❌ 编译阶段就报错！只能放 String
```

### 本质

> **把具体的数据类型作为参数传给类型变量。**

```
ArrayList<String>  →  把 String 作为参数传给类型变量 E
ArrayList<Integer> →  把 Integer 作为参数传给类型变量 E
```

### 为什么要用泛型（对比无泛型）

```java
// ❌ 没有泛型：什么都能放，取出来要强转，容易出错
ArrayList list = new ArrayList();
list.add("Java");
list.add(23);
list.add(true);

Object rs = list.get(1);
String s = (String) rs;    // 💥 运行时 ClassCastException！

// ✅ 有泛型：编译阶段就约束，取出来不用强转
ArrayList<String> list2 = new ArrayList<>();
list2.add("Java");
// list2.add(23);          // ❌ 编译就报错，问题提前暴露
String s2 = list2.get(0);  // 不用强转
```

---

## 2. 泛型类

### 语法

```java
修饰符 class 类名<类型变量, 类型变量, ...> {
    // 类体
}
```

> 类型变量建议用**大写的英文字母**，常见的有：`E`、`T`、`K`、`V`。

| 字母 | 含义 |
|------|------|
| `E` | Element（元素）|
| `T` | Type（类型）|
| `K` | Key（键）|
| `V` | Value（值）|

### 示例：自定义泛型类

```java
// 自定义泛型类
public class MyArrayList<E> {
    private ArrayList list = new ArrayList();

    public boolean add(E e) {       // 参数类型由使用者决定
        list.add(e);
        return true;
    }

    public boolean remove(E e) {
        return list.remove(e);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
```

### 使用

```java
// 指定 E 为 String
MyArrayList<String> list = new MyArrayList<>();
list.add("hello");
list.add("world");
// list.add(500);      // ❌ 编译阶段就报错
list.remove("hello");
System.out.println(list);
```

---

## 3. 泛型接口

### 语法

```java
修饰符 interface 接口名<类型变量, 类型变量, ...> {
    // 接口体
}
```

### 示例

```java
// 自定义泛型接口
public interface Data<T> {
    void add(T t);
    void delete(T t);
    void update(T t);
    T query(int id);
}
```

### 实现泛型接口的两种方式

#### 方式一：实现时指定具体类型

```java
public class StudentData implements Data<Student> {
    @Override
    public void add(Student s) { }

    @Override
    public void delete(Student s) { }

    @Override
    public void update(Student s) { }

    @Override
    public Student query(int id) {
        return null;
    }
}
```

#### 方式二：实现时继续保留泛型

```java
public class DataImpl<T> implements Data<T> {
    @Override
    public void add(T t) { }

    @Override
    public T query(int id) {
        return null;
    }
    // ...
}
```

> **注意：** 如果实现接口时**不指定泛型**，类型变量会按 `Object` 处理（相当于泛型被擦除）。

---

## 4. 泛型方法

### 语法

```java
修饰符 <类型变量, 类型变量, ...> 返回值类型 方法名(形参列表) {
    // 方法体
}
```

> **关键区别：** 类型变量写在**返回值类型前面**，这是泛型方法的标志。

### 示例

```java
// 泛型方法：打印任意类型的数组
public static <T> void printArray(T[] arr) {
    for (T t : arr) {
        System.out.print(t + " ");
    }
}

// 泛型方法：返回数组中的最大值
public static <T> T getMax(T[] arr) {
    return arr[0];
}
```

### 使用

```java
String[] names = {"赵敏", "汤宇轩", "张三"};
printArray(names);          // T 被推断为 String

Student[] stus = new Student[3];
printArray(stus);           // T 被推断为 Student

Student max = getMax(stus); // 返回值类型随 T 变化
String max2 = getMax(names);
```

### 泛型方法 vs 泛型类中的方法

| 对比 | 泛型类中的方法 | 泛型方法 |
|------|--------------|---------|
| 类型变量位置 | 声明在类上 `<E>` | 声明在方法上 `<T>` |
| 作用范围 | 整个类 | 仅该方法 |
| 标志 | 类名后 `<E>` | 返回值类型前 `<T>` |

---

## 5. 通配符

### 定义

> **通配符 `?`** 可以在**使用泛型**的时候代表**一切类型**。

> **注意：** `E`、`T`、`K`、`V` 是在**定义泛型**的时候使用；`?` 是在**使用泛型**的时候使用。

### 示例

```java
// 使用通配符：可以接受任意类型的 ArrayList
public static void printAll(ArrayList<?> list) {
    for (Object o : list) {
        System.out.println(o);
    }
}

printAll(new ArrayList<String>());     // ✅
printAll(new ArrayList<Integer>());    // ✅
printAll(new ArrayList<Student>());    // ✅
```

### 为什么需要通配符

```java
// 虽然 Xiaomi 和 BYD 都是 Car 的子类，
// 但 ArrayList<Xiaomi>、ArrayList<BYD> 和 ArrayList<Car> 没有任何关系！

public static void go(ArrayList<Car> cars) { }   // ❌ 只能接受 ArrayList<Car>

// 用通配符解决
public static void go(ArrayList<? extends Car> cars) { }   // ✅ 接受 Car 及其子类
```

---

## 6. 泛型的上下限

### 泛型上限

```java
? extends Car
```

> `?` 能接受的必须是 **Car 或者其子类**。

```java
public static void go(ArrayList<? extends Car> cars) { }

// ✅ 可以传
go(new ArrayList<Car>());
go(new ArrayList<Xiaomi>());     // Xiaomi extends Car
go(new ArrayList<BYD>());        // BYD extends Car

// ❌ 不能传
// go(new ArrayList<Dog>());     // Dog 不是 Car 的子类
```

### 泛型下限

```java
? super Car
```

> `?` 能接收的必须是 **Car 或者其父类**。

```java
public static void go2(ArrayList<? super Car> cars) { }

// ✅ 可以传
go2(new ArrayList<Car>());
go2(new ArrayList<Object>());    // Object 是 Car 的父类

// ❌ 不能传
// go2(new ArrayList<Xiaomi>()); // Xiaomi 是 Car 的子类，不是父类
```

### 上下限对比

| 写法 | 含义 | 记忆 |
|------|------|------|
| `? extends Car` | Car **及其子类** | 上限（封顶）|
| `? super Car` | Car **及其父类** | 下限（兜底）|

---

## 7. 泛型擦除

### 概念

> **泛型工作在编译阶段**，等编译后泛型就没用了，所以**泛型在编译后都会被擦除**，所有类型会恢复成 `Object` 类型。

```
编译前：ArrayList<String>  →  编译后：ArrayList（内部按 Object 处理）
```

### 为什么要了解

- 泛型是**编译期的语法糖**，运行时不存在泛型信息
- 这解释了为什么泛型**不支持基本数据类型**

### 泛型支持的类型

> **泛型不支持基本数据类型，只能支持对象类型（引用数据类型）。**

```java
// ❌ 编译错误：泛型不支持基本类型
// ArrayList<int> list = new ArrayList<>();

// ✅ 必须用包装类
ArrayList<Integer> list = new ArrayList<>();
```

---

## 8. 包装类

### 定义

> **包装类**就是把**基本类型的数据包装成对象的类型**。

### 基本类型 ↔ 包装类对照表

| 基本类型 | 包装类 | 记忆 |
|---------|--------|------|
| `byte` | `Byte` | 首字母大写 |
| `short` | `Short` | 首字母大写 |
| `int` | **`Integer`** | ⚠️ 特殊，不是 `Int` |
| `long` | `Long` | 首字母大写 |
| `float` | `Float` | 首字母大写 |
| `double` | `Double` | 首字母大写 |
| `char` | **`Character`** | ⚠️ 特殊，不是 `Char` |
| `boolean` | `Boolean` | 首字母大写 |

> **记忆口诀：** `int → Integer`、`char → Character`，**其他都是首字母大写**。

### 为什么需要包装类

1. **泛型只支持对象类型**，用 `ArrayList<Integer>` 存整数
2. 包装类提供了很多实用的方法（如 `Integer.parseInt()`）
3. 可以表示 `null`（基本类型不能为 null）

---

## 9. 自动装箱与自动拆箱

### 自动装箱

> **基本数据类型可以自动转换成包装类型。**

```java
// 手动包装
Integer it1 = Integer.valueOf(100);

// 自动装箱（推荐）
Integer it2 = 100;          // 自动把 int 包装成 Integer
```

### 自动拆箱

> **包装类型可以自动转换为基本数据类型。**

```java
Integer it = 100;
int i = it;                 // 自动拆箱
```

### 在集合中的应用

```java
ArrayList<Integer> list = new ArrayList<>();
list.add(123);              // 自动装箱：int → Integer
list.add(120);

int rs = list.get(1);       // 自动拆箱：Integer → int
```

### ⚠️ 包装类的 == 陷阱（重点）

```java
// 手动包装：每次都创建新对象
Integer it1 = Integer.valueOf(100);
Integer it2 = Integer.valueOf(100);
System.out.println(it1 == it2);    // true（100 在缓存范围 -128~127 内）

// 自动装箱
Integer it11 = 100;
Integer it22 = 100;
System.out.println(it11 == it22);  // true（同上，命中了缓存）
```

> **注意：** 包装类的 `==` 比较的是**地址**，不是值！  
> `Integer` 缓存了 `-128 ~ 127` 的值，超出这个范围就会 new 新对象，`==` 就会返回 `false`。
>
> **比较包装类的值，一定要用 `equals()`，不要用 `==`！**

```java
Integer a = 1000;
Integer b = 1000;
System.out.println(a == b);         // false！超出缓存范围
System.out.println(a.equals(b));    // true ✅ 正确做法
```

---

## 10. 类型转换（String ↔ 数值）

### 10.1 把基本类型的数据转换成字符串类型

| 方式 | 示例 |
|------|------|
| **`public static String toString(double d)`** | `Integer.toString(23)` → `"23"` |
| **`public String toString()`** | `i2.toString()` |
| 简便方式（拼接空串）| `j + ""` |

```java
int j = 23;

// 方式一：静态方法
String rs1 = Integer.toString(j);    // "23"
System.out.println(rs1 + 1);         // "231"（字符串拼接）

// 方式二：对象方法
Integer i2 = j;
String rs2 = i2.toString();          // "23"
System.out.println(rs2 + 1);         // "231"

// 方式三：拼接空串（最简便）
String rs3 = j + "";
System.out.println(rs3 + 1);         // "231"
```

### 10.2 把字符串类型的数值转换成数值本身对应的真实数据类型

| 方式 | 示例 |
|------|------|
| **`public static int parseInt(String s)`** | `Integer.parseInt("98")` → `98` |
| **`public static Integer valueOf(String s)`** | `Integer.valueOf("98")` → `98` |

```java
String str = "98";

// 方式一：parseInt
int i1 = Integer.parseInt(str);       // 98
System.out.println(i1 + 2);           // 100（数值运算）

// 方式二：valueOf
int i2 = Integer.valueOf(str);        // 98
System.out.println(i2 + 2);           // 100

// 小数
String str2 = "98.8";
double d1 = Double.parseDouble(str2);
// 或
double d2 = Double.valueOf(str2);
System.out.println(d1 + 2);           // 100.8
```

### 两种方式对比

| 方法 | 返回类型 | 说明 |
|------|---------|------|
| `parseInt(String)` | `int`（基本类型）| 直接返回基本类型 |
| `valueOf(String)` | `Integer`（包装类）| 返回包装类对象，可自动拆箱 |

---

## 11. 完整代码示例

| 包 | 文件 | 说明 |
|----|------|------|
| `tang.demo2genericity` | `GenericDemo1.java` | 泛型的作用（对比无泛型）|
| | `MyArrayList.java` | 自定义泛型类 |
| | `GenericDemo2.java` | 使用自定义泛型类 |
| `tang.demo3genericity` | `Data.java` | 自定义泛型接口 |
| | `StudentData.java` / `TeacherData.java` | 实现泛型接口 |
| `tang.demo4genericity` | `GenericDemo4.java` | 泛型方法 |
| | `GenericDemo5.java` | 通配符 + 泛型上限 |
| `tang.demo5genericity` | `GenericDemo6.java` | 包装类、装箱拆箱、类型转换 |
| `tang.demo6genericity` | `GenericDemo7.java` | **综合演示**（推荐运行）|

---

*本笔记基于 2026-08-02 的学习内容整理，对应 `day09-collection` 模块。*
