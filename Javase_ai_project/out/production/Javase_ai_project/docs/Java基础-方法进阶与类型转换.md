# Java 基础：方法进阶与类型转换

> 对应模块：`day01-helloworld`
> 学习日期：2026-07-21

---

## 目录

1. [方法的完整定义格式](#1-方法的完整定义格式)
2. [方法重载（Overloading）](#2-方法重载overloading)
3. [return 的另一种用法：提前结束方法](#3-return-的另一种用法提前结束方法)
4. [自动类型转换](#4-自动类型转换)
5. [强制类型转换](#5-强制类型转换)
6. [表达式的自动类型提升](#6-表达式的自动类型提升)
7. [完整的代码示例](#7-完整的代码示例)

---

## 1. 方法的完整定义格式

### 语法

```java
修饰符 返回类型 方法名(形参列表) {
    方法体代码
    return 返回值;
}
```

| 组成部分 | 说明 | 示例 |
|----------|------|------|
| **修饰符** | 访问权限等修饰 | `public` `static` |
| **返回类型** | 方法执行后返回的数据类型 | `void` `int` `String` |
| **方法名** | 遵循小驼峰命名 | `getSum` `printInfo` |
| **形参列表** | 调用时传入的参数（可多个，可空） | `(int a, int b)` |
| **方法体** | 方法执行的代码块 | `{ ... }` |
| **return** | 返回结果并结束方法 | `return a + b;` |

### 注意事项

- **返回类型为 `void`**：方法不需要返回值，可以省略 `return`，或使用 `return;` 提前结束
- **返回类型为非 `void`**：**必须**有 `return 值;`，且返回值类型必须匹配声明
- **方法必须定义在类中**，不能嵌套定义（方法里不能再定义方法）

---

## 2. 方法重载（Overloading）

### 定义

**方法重载**是指在同一个类中，方法名相同但**形参列表不同**的多个方法。

### 重载的判定条件（满足其一即可）

| 条件 | 示例 |
|------|------|
| **参数个数不同** | `sum(int a)` vs `sum(int a, int b)` |
| **参数类型不同** | `sum(int a)` vs `sum(double a)` |
| **参数顺序不同** | `sum(int a, double b)` vs `sum(double a, int b)` |

### 与重载无关的因素（不关心）

| 无关因素 | 说明 |
|----------|------|
| ✅ **形参名称** | `sum(int x)` 和 `sum(int y)` 不算重载——参数名不同不算 |
| ❌ **返回值类型** | 仅返回值不同不能构成重载，编译报错 |
| ❌ **修饰符** | 仅修饰符不同不能构成重载 |

### 示例

```java
public static int add(int a, int b) {
    return a + b;
}

public static double add(double a, double b) {
    return a + b;          // 参数类型不同，构成重载
}

public static int add(int a, int b, int c) {
    return a + b + c;      // 参数个数不同，构成重载
}
```

### 重载的好处

- **提高代码的可读性**：同样的功能用同一个方法名，不用另想名字
- **调用更灵活**：调用时传入不同参数，自动匹配对应版本

---

## 3. return 的另一种用法：提前结束方法

在返回类型为 `void` 的方法中，也可以使用 `return;` 来**提前结束**方法的执行。

### 示例

```java
public static void checkAge(int age) {
    if (age < 0 || age > 150) {
        System.out.println("年龄不合法！");
        return;   // 立刻结束方法，后面的代码不执行
    }
    System.out.println("年龄是：" + age);
}
```

调用 `checkAge(200)` 时，只会输出 `"年龄不合法！"`，然后方法直接结束，不会执行最后的输出语句。

> **要点：** `return;`（不带返回值）只能在 `void` 方法中使用，作用是"提前退场"。

---

## 4. 自动类型转换

### 定义

**自动类型转换（隐式转换）**：当把一个**小范围**的数据类型赋值给**大范围**的数据类型时，Java 自动完成转换，不需要写额外代码。

### 转换方向

```
byte  →  short  →  int  →  long  →  float  →  double
                      ↑
                    char
```

> **注意箭头方向：** `int` → `long` → `float` → `double`，`float` 比 `long` 范围更大（浮点数在内存中的表示范围更大）。

### 示例

```java
int num = 100;
long bigNum = num;       // int → long，自动转换 ✅
double d = bigNum;       // long → double，自动转换 ✅

char ch = 'A';
int code = ch;           // char → int，自动转换 ✅（'A' 的 Unicode 是 65）
```

### 自动转换规则

| 源类型 | 目标类型 | 是否自动转换 |
|--------|---------|-------------|
| `byte` | `int`/`long`/`float`/`double` | ✅ |
| `short` | `int`/`long`/`float`/`double` | ✅ |
| `char` | `int`/`long`/`float`/`double` | ✅ |
| `int` | `long`/`float`/`double` | ✅ |
| `long` | `float`/`double` | ✅ |
| `float` | `double` | ✅ |
| `double` | 任何更小的类型 | ❌ 必须强制转换 |

---

## 5. 强制类型转换

### 定义

**强制类型转换（显式转换）**：当把**大范围**的数据类型赋值给**小范围**的数据类型时，需要手动添加转换语法。

### 语法

```java
(目标类型) 表达式
```

### 示例

```java
double pi = 3.14159;
int intPi = (int) pi;         // double → int，小数部分被直接丢弃
System.out.println(intPi);    // 输出 3

long big = 100000L;
int small = (int) big;        // long → int
```

### 风险：数据溢出

强制转换可能导致数据丢失：

```java
int bigNum = 300;          // 300 的二进制：00000000 00000000 00000001 00101100
byte b = (byte) bigNum;    // int → byte（4字节截断为1字节）
System.out.println(b);     // 输出 44 ❌（数据丢失了高位部分）
```

### 风险：精度损失

```java
double d = 3.1415926;
int i = (int) d;           // 小数部分被直接丢弃
System.out.println(i);     // 输出 3
```

> **建议：** 小数转整数时使用 `Math.round()`、`Math.floor()`、`Math.ceil()` 等方法实现四舍五入或取整，而非直接强转。

---

## 6. 表达式的自动类型提升

### 规则

**表达式的最终结果类型由表达式中的"最高"类型决定**；

- 所有参与的变量会**自动提升**到表达式中的最高类型
- `byte`、`short`、`char` 在参与运算时会**自动提升为 `int`**（即使没有 int 类型参与）

### 示例一：不同类型混合运算

```java
int a = 10;
double b = 2.5;
// 结果类型由 double 决定（double 是最高类型）
double result = a + b;    // int → double 自动提升
System.out.println(result); // 12.5
```

### 示例二：byte/short/char 自动提升为 int

```java
byte b1 = 10;
byte b2 = 20;
// byte + byte → int！
int sum = b1 + b2;        // ✅ 正确，结果已经是 int
// byte sum2 = b1 + b2;   // ❌ 编译报错：byte 运算结果自动提升为 int
```

### 示例三：混合类型链式运算

```java
byte b = 1;
short s = 2;
char c = 'A';       // 'A' = 65
int i = 10;
long l = 100L;
float f = 2.0F;
double d = 3.0;

// 整个表达式的结果类型是 double（最高类型）
double result = b + s + c + i + l + f + d;
```

### 类型提升规则汇总

```
byte/short/char  ──→  int  ──→  long  ──→  float  ──→  double
（自动提升为int）         ↑ 表达式中出现哪种类型，整体就提升到哪种
```

> **特别注意：** `byte` + `byte` = `int`，哪怕两个都是 byte，结果也是 int！必须用 `int` 变量接收，或强转回 `byte`。

---

## 7. 完整的代码示例

见 `day01-helloworld/src/tang/basic/MethodAndTypeCast.java`，该文件整合了方法重载、类型转换、类型提升的全部示例代码。

---

*本笔记基于 2026-07-21 的学习内容整理，对应 `day01-helloworld` 模块。*
