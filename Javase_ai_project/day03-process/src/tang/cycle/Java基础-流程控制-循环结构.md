# Java 基础：流程控制——循环结构

> 对应模块：`day03-process` | 包：`tang.cycle`
> 学习日期：2026-07-24

---

## 目录

1. [for 循环](#1-for-循环)
2. [while 循环](#2-while-循环)
3. [do-while 循环](#3-do-while-循环)
4. [三种循环的对比与选择](#4-三种循环的对比与选择)
5. [死循环](#5-死循环)
6. [循环嵌套](#6-循环嵌套)
7. [break 与 continue](#7-break-与-continue)
8. [随机数](#8-随机数)
9. [综合案例：验证码生成](#9-综合案例验证码生成)
10. [完整代码示例](#10-完整代码示例)

---

## 1. for 循环

### 语法

```java
for (初始化语句; 循环条件; 迭代语句) {
    // 循环体
}
```

### 执行流程

```
① 初始化语句  →  ② 循环条件（true/false）
                     ↓ true
                  ③ 循环体
                     ↓
                  ④ 迭代语句  →  回到 ②
                     ↓ false
                   退出循环
```

### 示例

```java
// 打印 1~5
for (int i = 1; i <= 5; i++) {
    System.out.println(i);
}

// 求 1~100 的和
int sum = 0;
for (int i = 1; i <= 100; i++) {
    sum += i;
}
System.out.println(sum);  // 5050
```

### 适用场景

- **已知循环次数**时最合适
- 遍历数组、集合的首选

---

## 2. while 循环

### 语法

```java
while (循环条件) {
    // 循环体
}
```

### 示例

```java
// 打印 1~5
int i = 1;
while (i <= 5) {
    System.out.println(i);
    i++;
}

// 求 1~100 的和
int sum = 0, i = 1;
while (i <= 100) {
    sum += i;
    i++;
}
System.out.println(sum);  // 5050
```

### 适用场景

- **未知循环次数，只知道循环条件**时使用
- 例如：持续读取输入直到满足某个条件

---

## 3. do-while 循环

### 语法

```java
do {
    // 循环体
} while (循环条件);
```

### 与 while 的关键区别

| 特点 | `while` | `do-while` |
|------|---------|------------|
| **先判断还是先执行** | 先判断，后执行 | **先执行一次**，再判断 |
| **至少执行次数** | 可能 0 次 | **至少 1 次** |
| **分号结尾** | 不需要 | **需要 `;`** |

### 示例

```java
// 模拟：先做一次，再问是否继续
int i = 1;
do {
    System.out.println("第 " + i + " 次执行");
    i++;
} while (i <= 5);

// 验证"至少执行一次"
int n = 10;
do {
    System.out.println("条件不满足也会执行一次");  // 一定会执行
} while (n < 0);
```

---

## 4. 三种循环的对比与选择

| 维度 | `for` | `while` | `do-while` |
|------|-------|---------|------------|
| **初始化** | 在 `for` 内部 | 在外部 | 在外部 |
| **判断时机** | 先判断 | 先判断 | 后判断 |
| **最少执行** | 0 次 | 0 次 | **1 次** |
| **适用场景** | 已知次数 | 已知条件，未知次数 | 至少需要执行一次 |
| **使用频率** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐ |

### 选择建议

```
已知循环次数？        → for
未知次数，有条件？    → while
至少需要执行一次？    → do-while
```

---

## 5. 死循环

**死循环** 是指循环条件永远为 `true`，循环永远不会停止。

### 三种写法

```java
// for 死循环
for (;;) {
    System.out.println("for 死循环");
}

// while 死循环（最常用）
while (true) {
    System.out.println("while 死循环");
}

// do-while 死循环
do {
    System.out.println("do-while 死循环");
} while (true);
```

### 死循环的用途

死循环并非一定是"错误"，在以下场景很常用：

1. **服务器持续监听请求**
2. **游戏主循环（Game Loop）**
3. **菜单持续显示，直到用户选择退出**
4. **一直读取用户输入，直到遇到特定指令**

```java
// 典型用法：用 break 在内部退出
while (true) {
    int cmd = sc.nextInt();
    if (cmd == 0) {
        break;  // 主动退出
    }
    // 处理其他命令
}
```

---

## 6. 循环嵌套

**循环嵌套** 是指一个循环的循环体内包含另一个循环。

### 示例一：打印直角三角形

```java
for (int i = 1; i <= 5; i++) {        // 外层控制行数
    for (int j = 1; j <= i; j++) {     // 内层控制每行打印个数
        System.out.print("*");
    }
    System.out.println();              // 换行
}
```

输出：
```
*
**
***
****
*****
```

### 示例二：打印九九乘法表

```java
for (int i = 1; i <= 9; i++) {
    for (int j = 1; j <= i; j++) {
        System.out.print(j + "x" + i + "=" + (i * j) + "\t");
    }
    System.out.println();
}
```

### 循环嵌套执行逻辑

```
外层循环第 1 次：
    → 内层循环完整执行一遍
外层循环第 2 次：
    → 内层循环再完整执行一遍
...（以此类推）
```

> **总执行次数 = 外层次数 × 内层次数**

---

## 7. break 与 continue

### break —— 跳出当前循环

```java
for (int i = 1; i <= 5; i++) {
    if (i == 3) {
        break;       // i=3 时跳出整个循环
    }
    System.out.println(i);
}
// 输出：1 2
```

| 位置 | 作用 |
|------|------|
| 循环中 | 跳出当前**整个循环** |
| switch 中 | 跳出 switch，防止穿透 |

### continue —— 跳过本次循环

```java
for (int i = 1; i <= 5; i++) {
    if (i == 3) {
        continue;    // i=3 时跳过本次，进入下一次迭代
    }
    System.out.println(i);
}
// 输出：1 2 4 5
```

### break vs continue

| 关键字 | 作用 | 后续执行 |
|--------|------|---------|
| `break` | **结束**整个循环 | 循环后的第一行代码 |
| `continue` | **跳过**本次循环的剩余部分 | 进入下一次迭代 |

### 嵌套循环中的 break/continue

`break` 和 `continue` 默认只对**当前所在的那一层**循环生效：

```java
for (int i = 1; i <= 3; i++) {          // 外层
    for (int j = 1; j <= 3; j++) {      // 内层
        if (j == 2) {
            break;    // 只跳出内层循环，外层不受影响
        }
        System.out.println(i + "," + j);
    }
}
```

---

## 8. 随机数

Java 生成随机数的两种方式。

### 方式一：Math.random()

```java
// 返回 [0.0, 1.0) 的随机小数
double d = Math.random();           // 如 0.73421...
int num = (int)(Math.random() * 10); // [0, 9] 的整数
int num2 = (int)(Math.random() * 100) + 1; // [1, 100] 的整数
```

| 公式 | 范围 |
|------|------|
| `(int)(Math.random() * n)` | `[0, n-1]` |
| `(int)(Math.random() * n) + m` | `[m, m+n-1]` |
| `(int)(Math.random() * (max - min + 1)) + min` | `[min, max]` |

### 方式二：Random 类

```java
import java.util.Random;

Random r = new Random();
int num = r.nextInt(10);          // [0, 9]
int num2 = r.nextInt(100) + 1;    // [1, 100]
double d = r.nextDouble();        // [0.0, 1.0)
boolean b = r.nextBoolean();      // true / false
```

### 两种方式对比

| 对比 | Math.random() | Random 类 |
|------|---------------|-----------|
| 导入 | 无需导包 | 需要 `import java.util.Random` |
| 创建 | 直接调用静态方法 | `new Random()` 创建对象 |
| 灵活性 | 生成整数需要公式转换 | 直接 `nextInt()`、`nextDouble()` |
| 适用场景 | 简单随机数 | 复杂随机数需求 |

---

## 9. 综合案例：验证码生成

利用 `Math.random()` + `switch` 生成包含数字、大写字母、小写字母的随机验证码。

### 思路

```
1. 确定验证码长度 n
2. 循环 n 次：
    a. 用 Math.random() 随机生成类型（0=数字，1=小写字母，2=大写字母）
    b. 根据类型生成对应的随机字符
    c. 拼接到验证码字符串
3. 返回验证码
```

### 代码实现

```java
public static String getCode(int n) {
    String code = "";
    for (int i = 0; i < n; i++) {
        int type = (int)(Math.random() * 3);   // 0/1/2
        switch (type) {
            case 0:
                int num = (int)(Math.random() * 10);       // 0~9
                code += num;
                break;
            case 1:
                char lower = (char)('a' + (int)(Math.random() * 26)); // a~z
                code += lower;
                break;
            case 2:
                char upper = (char)('A' + (int)(Math.random() * 26)); // A~Z
                code += upper;
                break;
        }
    }
    return code;
}
```

---

## 10. 完整代码示例

| 文件 | 说明 |
|------|------|
| `cycle_1.java` | for 循环——查找水仙花数 |
| `cycle_2.java` | while 循环——数列求和 |
| `cycle_3.java` | for 循环——闰年统计 |
| `cycle_4.java` | 验证码生成（Math.random + switch） |
| `CycleDemo.java` | 综合演示——for/while/do-while/死循环/嵌套/break/continue/Random 类（推荐运行此文件） |

---

*本笔记基于 2026-07-24 的学习内容整理，对应 `day03-process` 模块。*
