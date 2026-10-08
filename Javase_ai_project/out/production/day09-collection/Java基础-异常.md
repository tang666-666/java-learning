# Java 基础：异常（Exception）

> 对应模块：`day09-collection` | 包：`tang.demo1exception`
> 学习日期：2026-08-01

---

## 目录

1. [异常概述](#1-异常概述)
2. [异常的分类](#2-异常的分类)
3. [异常的基本处理](#3-异常的基本处理)
4. [异常的作用](#4-异常的作用)
5. [自定义异常](#5-自定义异常)
6. [异常处理方案](#6-异常处理方案)
7. [finally 代码块](#7-finally-代码块)
8. [完整代码示例](#8-完整代码示例)

---

## 1. 异常概述

**异常（Exception）** 代表程序出现的问题。

### 异常体系结构

```
                Throwable（顶层父类）
                     │
        ┌────────────┴────────────┐
     Error                    Exception
   （系统错误）                    │
                    ┌────────────┴────────────┐
              RuntimeException           其他异常
              （运行时异常）           （编译时异常）
```

| 类型 | 说明 |
|------|------|
| **Error** | 系统级错误（如内存溢出），程序无法处理 |
| **Exception** | 程序可以处理的异常（我们关注的重点）|

---

## 2. 异常的分类

### 运行时异常 vs 编译时异常

| 类型 | 特点 | 举例 |
|------|------|------|
| **运行时异常** | **编译阶段不报错**，运行时才出现的异常 | 数组越界、除零、空指针 |
| **编译时异常** | **编译阶段就报错**，编译不通过 | 日期解析、文件读取 |

### 常见运行时异常

```java
// 1. 数组索引越界异常
int[] arr = {1, 2, 3};
System.out.println(arr[3]);       // ArrayIndexOutOfBoundsException

// 2. 算术异常（除零）
System.out.println(10 / 0);       // ArithmeticException

// 3. 空指针异常
String str = null;
System.out.println(str.length()); // NullPointerException
```

### 常见编译时异常

```java
// 日期解析异常
String str = "2026-09-22 11:12:13";
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date date = sdf.parse(str);       // ParseException（编译时异常）

// 文件输入流
InputStream is = new FileInputStream("D:/meinv.png");
// FileNotFoundException（编译时异常）
```

> **区别：** 编译时异常**必须处理**（throws 或 try-catch），否则代码无法编译通过；运行时异常不强制处理。

---

## 3. 异常的基本处理

在 IDEA 中，编译时异常可以用 **`Alt + Enter`** 快速生成处理代码。

### 方式一：抛出异常（throws）

> 在方法上使用 `throws` 关键字，将方法内部出现的异常**抛出去给调用者**处理。

```java
// 方法上声明 throws，把异常抛给调用者
public static void show() throws Exception {
    String str = "2026-09-22 11:12:13";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = sdf.parse(str);   // 编译时异常，抛给调用者
    System.out.println(date);
}

// 调用者继续抛，或者捕获
public static void main(String[] args) throws Exception {
    show();   // 继续往上抛
}
```

### 方式二：捕获异常（try-catch）

> 直接捕获程序出现的异常，防止程序崩溃。

```java
try {
    // 监视代码：出现异常会被 catch 拦截住
    show();
} catch (Exception e) {
    e.printStackTrace();   // 打印异常信息
}
```

### try-catch 语法

```java
try {
    // 可能出现异常的代码
} catch (异常类型 变量名) {
    // 出现该异常时的处理代码
}
```

### 两种方式对比

| 方式 | 语法 | 特点 |
|------|------|------|
| **抛出** | 方法上 `throws 异常类型` | 把问题交给上层，自己不处理 |
| **捕获** | `try { } catch { }` | 自己处理，程序继续往下执行 |

### 关键体会：捕获后程序继续执行

```java
public static void main(String[] args) {
    System.out.println("程序开始执行");
    try {
        System.out.println(div(10, 0));   // 抛异常
        System.out.println("执行成功");    // 不执行
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("执行失败");    // 执行
    }
    System.out.println("程序结束");        // ✅ 仍然执行
}
```

---

## 4. 异常的作用

### 作用一：定位程序 bug 的关键信息

`e.printStackTrace()` 会打印异常的**完整调用栈**，快速定位问题出在哪一行。

```
java.lang.ArithmeticException: / by zero
    at tang.demo1exception.ExceptionDemo2.div(ExceptionDemo2.java:22)
    at tang.demo1exception.ExceptionDemo2.main(ExceptionDemo2.java:7)
```

### 作用二：作为方法内部的一种特殊返回值

> 通过抛出异常，通知**上层调用者**方法执行失败。

```java
public static int div(int a, int b) throws Exception {
    if (b == 0) {
        // 返回一个异常给上层调用者
        // 返回的异常能告知上层：底层执行是成功还是失败
        throw new Exception("除数不能为零");
    }
    return a / b;
}
```

> **对比返回值的优势：** 如果返回 `-1` 表示失败，调用者可能不知道；但抛出异常是**强制性的通知**，调用者必须处理。

---

## 5. 自定义异常

当 Java 内置的异常类型不能满足需求时，可以自定义异常。

### 5.1 自定义运行时异常（用得更多）

| 步骤 | 说明 |
|------|------|
| **① 继承 RuntimeException** | 做"爸爸" |
| **② 重写构造器** | 无参 + 带 message 的 |
| **③ throw 抛出** | 哪里需要就 `throw new 异常类()` |

```java
// ① 继承 RuntimeException
public class AgeIllegalRuntimeException extends RuntimeException {
    // ② 重写构造器
    public AgeIllegalRuntimeException() {
    }

    public AgeIllegalRuntimeException(String message) {
        super(message);
    }
}
```

```java
// ③ 使用
public static void save(int age) {
    if (age < 1 || age > 200) {
        throw new AgeIllegalRuntimeException("年龄非法");
    }
    System.out.println("保存年龄：" + age);
}
```

**特点**：编译阶段**不报错**，运行时才可能出现，提醒**不属于激进型**。

### 5.2 自定义编译时异常

| 步骤 | 说明 |
|------|------|
| **① 继承 Exception** | 做"爸爸" |
| **② 重写构造器** | 无参 + 带 message 的 |
| **③ throw 抛出** | 方法上要 `throws` 声明 |

```java
// ① 继承 Exception
public class AgeIllegalException extends Exception {
    public AgeIllegalException() {
    }

    public AgeIllegalException(String message) {
        super(message);
    }
}
```

```java
// ③ 使用（方法上必须声明 throws）
public static void save(int age) throws AgeIllegalException {
    if (age < 1 || age > 200) {
        throw new AgeIllegalException("年龄非法");
    }
    System.out.println("保存年龄：" + age);
}
```

**特点**：编译阶段**就报错**，提醒**比较激进**（强制调用者处理）。

### 5.3 两种自定义异常对比

| 对比 | 继承 RuntimeException | 继承 Exception |
|------|----------------------|---------------|
| 类型 | 运行时异常 | 编译时异常 |
| 编译阶段 | ✅ 不报错 | ❌ 报错，必须处理 |
| 方法声明 | 不需要 `throws` | 必须 `throws` |
| 提醒强度 | 温和（不激进）| 激进（强制处理）|
| 使用频率 | **更多** | 较少 |

---

## 6. 异常处理方案

### 方案一：层层上抛，最外层统一捕获

> **底层异常层层往上抛出，最外层捕获异常**，记录下异常信息，并响应适合用户观看的信息进行提示。

```java
public static void main(String[] args) {
    System.out.println("程序开始");
    try {
        show1();                    // 底层方法抛出的异常在这里被捕获
        System.out.println("操作成功");
    } catch (Exception e) {
        e.printStackTrace();        // 记录异常信息（给程序员看）
        System.out.println("操作失败");  // 响应合适的信息（给用户看）
    }
    System.out.println("程序结束");
}

public static void show1() throws Exception {
    // 底层可能抛出多种异常，统一 throws
    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
    InputStream is = new FileInputStream("D:/meinv.png");
}
```

**为什么这样设计：**
- 底层方法专注业务逻辑，不关心如何处理错误
- 最外层统一处理，可以决定给用户展示什么信息（不让用户看到堆栈信息）

### 方案二：捕获异常后，尝试重新修复

> 最外层捕获异常后，**尝试重新修复**（比如让用户重新输入）。

```java
public static void main(String[] args) {
    System.out.println("程序开始");
    double price = 0;

    while (true) {
        try {
            price = userInputPrice();   // 尝试获取输入
            break;                      // 成功则跳出循环
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("输入的数据有误，请重新输入");  // 修复：重新输入
        }
    }

    System.out.println("商品定价：" + price);
    System.out.println("程序结束");
}
```

> **要点：** 用**死循环 + try-catch** 实现"出错就重试"的效果。

---

## 7. finally 代码块

### 定义

> **无论是否发生异常，finally 中的代码都会执行。**

### 语法

```java
try {
    // 可能出异常的代码
} catch (Exception e) {
    // 异常处理
} finally {
    // 无论是否出异常都会执行
}
```

### 执行顺序

```
try 中的代码
    ↓
出异常？ ── 是 ──→ catch 处理 ──→ finally
    │
    └── 否 ────────────────────→ finally
```

### 典型用途

**释放资源**（关闭文件流、数据库连接等）：

```java
InputStream is = null;
try {
    is = new FileInputStream("D:/meinv.png");
    // 使用流读取数据
} catch (Exception e) {
    e.printStackTrace();
} finally {
    // 无论成功失败，都要关闭资源
    if (is != null) {
        try {
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### try-catch-finally 组合形式

| 组合 | 说明 |
|------|------|
| `try-catch` | 捕获并处理异常 |
| `try-finally` | 不处理异常，但保证 finally 执行 |
| `try-catch-finally` | 完整形式 |

> **注意：** `try` 可以单独和 `finally` 搭配，但 `try` 不能单独使用（必须跟 catch 或 finally）。

---

## 8. 完整代码示例

| 文件 | 说明 |
|------|------|
| `tang/demo1exception/ExceptionDemo1.java` | 运行时异常 vs 编译时异常 |
| `tang/demo1exception/ExceptionDemo2.java` | 异常作为特殊返回值（throw）|
| `tang/demo1exception/ExceptionDemo3.java` | 自定义编译时异常（继承 Exception）|
| `tang/demo1exception/ExceptionDemo4.java` | 自定义运行时异常（继承 RuntimeException）|
| `tang/demo1exception/ExceptionDemo5.java` | 异常处理方案一：层层上抛 + 最外层捕获 |
| `tang/demo1exception/ExceptionDemo6.java` | 异常处理方案二：捕获后重新修复 |
| `tang/demo1exception/ExceptionDemo7.java` | **综合演示**：finally、异常体系、完整案例 |

---

*本笔记基于 2026-08-01 的学习内容整理，对应 `day09-collection` 模块。*
