# Java 基础：流程控制——分支结构

> 对应模块：`day03-process`
> 学习日期：2026-07-23

---

## 目录

1. [顺序结构](#1-顺序结构)
2. [分支结构概述](#2-分支结构概述)
3. [if 分支的三种形式](#3-if-分支的三种形式)
4. [switch 分支结构](#4-switch-分支结构)
5. [完整代码示例](#5-完整代码示例)

---

## 1. 顺序结构

**顺序结构**是程序最基本的执行方式：代码**从上到下，逐行执行**，没有跳转。

```java
System.out.println("第一步");
System.out.println("第二步");
System.out.println("第三步");
// 输出顺序：第一步 → 第二步 → 第三步
```

如果没有分支、循环等特殊控制，程序就会按照顺序结构依次执行。这是所有程序的基础骨架。

---

## 2. 分支结构概述

**分支结构**让程序可以根据条件选择性地执行某段代码。Java 提供了两套分支机制：

| 分支方式 | 适用场景 |
|----------|---------|
| `if` 系列 | 区间判断、范围判断、复杂的 boolean 逻辑 |
| `switch` | 等值匹配（单个值精确匹配） |

---

## 3. if 分支的三种形式

### 形式一：单路分支 —— if

```java
if (条件) {
    // 条件为 true 时执行的代码
}
```

```java
int age = 20;
if (age >= 18) {
    System.out.println("已成年，可以上网");
}
// 条件成立则执行，不成立则跳过
```

### 形式二：双路分支 —— if-else

```java
if (条件) {
    // 条件为 true 时执行
} else {
    // 条件为 false 时执行
}
```

```java
int score = 55;
if (score >= 60) {
    System.out.println("及格");
} else {
    System.out.println("不及格");
}
// 输出：不及格
```

### 形式三：多路分支 —— if-else if-else

```java
if (条件1) {
    // 条件1为 true 时执行
} else if (条件2) {
    // 条件1为 false 且 条件2为 true 时执行
} else if (条件3) {
    // 条件1、2为 false 且 条件3为 true 时执行
} else {
    // 以上条件全为 false 时执行
}
```

```java
int score = 85;
if (score >= 90) {
    System.out.println("优秀");
} else if (score >= 80) {
    System.out.println("良好");
} else if (score >= 70) {
    System.out.println("中等");
} else if (score >= 60) {
    System.out.println("及格");
} else {
    System.out.println("不及格");
}
// 输出：良好
```

### if 使用注意事项

1. **条件必须是 boolean 表达式**——`if (score = 60)` 是错误写法（C 里合法，Java 不行）
2. **如果 if/else 后只有一条语句，可以省略大括号**，但**强烈建议永远写大括号**，避免歧义
3. **else 和最近的 if 配对**，除非用大括号改变

```java
// ❌ 容易出错的写法
if (score >= 60)
    System.out.println("及格");
    System.out.println("这句不受 if 控制，永远执行");  // 不在 if 代码块内！

// ✅ 推荐：永远写大括号
if (score >= 60) {
    System.out.println("及格");
}
```

---

## 4. switch 分支结构

### 基本语法

```java
switch (表达式) {
    case 值1:
        // 匹配值1时执行的代码
        break;
    case 值2:
        // 匹配值2时执行的代码
        break;
    // ...
    default:
        // 所有 case 都不匹配时执行
        break;
}
```

### switch 支持的数据类型

```java
// 支持的类型（表达式的结果类型）
byte, short, int, char
// JDK 7+ 支持 String
// JDK 14+ 支持 枚举（enum）
```

### switch 核心规则

| 规则 | 说明 |
|------|------|
| **表达式类型** | 只能是 `byte`、`short`、`int`、`char`（及 JDK7+ 的 `String`）|
| **case 值** | **必须是字面量**，不能是变量 |
| **case 不能重复** | 同一个 switch 中，case 值不能重复 |
| **break 不能忘** | 匹配到 case 后，如果没有 break，会**穿透**（继续执行下一个 case）|
| **case 合并** | 多个 case 执行相同代码时，可以合并在一起写 |

### 示例：完整 switch

```java
int weekday = 3;

switch (weekday) {
    case 1:
        System.out.println("星期一");
        break;
    case 2:
        System.out.println("星期二");
        break;
    case 3:
        System.out.println("星期三");
        break;
    case 4:
        System.out.println("星期四");
        break;
    case 5:
        System.out.println("星期五");
        break;
    default:
        System.out.println("周末");
        break;
}
// 输出：星期三
```

### switch 穿透现象

```java
int num = 1;
switch (num) {
    case 1:
        System.out.println("一");
        // 没写 break → 穿透
    case 2:
        System.out.println("二");
        break;
    case 3:
        System.out.println("三");
        break;
}
// 输出：
// 一
// 二
```

> **穿透**：匹配到 case 1 后，执行完 case 1 的代码，因为没有 break，继续执行 case 2，直到遇到 break 或 switch 结束。

### case 合并（利用穿透）

当多个 case 需要执行相同代码时，可以**合并 case**：

```java
int month = 3;
String season;
switch (month) {
    case 3:
    case 4:
    case 5:
        season = "春季";
        break;
    case 6:
    case 7:
    case 8:
        season = "夏季";
        break;
    case 9:
    case 10:
    case 11:
        season = "秋季";
        break;
    case 12:
    case 1:
    case 2:
        season = "冬季";
        break;
    default:
        season = "无效月份";
        break;
}
System.out.println(season); // 春季
```

### if vs switch 的选择

| 场景 | 推荐 |
|------|------|
| 判断范围（如 `score >= 60`） | `if` 系列 |
| 判断复杂 boolean 条件（与、或、非组合） | `if` 系列 |
| 精确等值匹配（星期、月份、菜单选项） | `switch` |
| 匹配项很少（1~3 个） | `if` 或 `switch` 均可 |

---

## 5. 完整代码示例

| 文件 | 包 | 说明 |
|------|-----|------|
| `branch.java` | `tang` | 水仙花数判断（if-else 的应用） |
| `BranchDemo.java` | `tang.branch.branch` | 三种 if 结构 + switch 综合演示（推荐运行此文件） |

---

*本笔记基于 2026-07-23 的学习内容整理，对应 `day03-process` 模块。*
