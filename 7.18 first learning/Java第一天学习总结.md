# Java 第一天学习总结

> 日期：2026-07-18

---

## 一、开发流程总览

编写 Java 程序分为 **三大步骤**：

```
编写源代码 (.java) → 编译 (.class) → 运行
```

每一步对应一个命令行操作：

| 步骤 | 命令 | 产物 |
|------|------|------|
| 编写 | 记事本 / VS Code 等文本编辑器 | `HelloWorld.java` |
| 编译 | `javac HelloWorld.java` | `HelloWorld.class` |
| 运行 | `java HelloWorld` | 控制台输出 |

---

## 二、源代码（编写代码）

### 文件：`HelloWorld.java`

```java
public class HelloWorld{
    public static void main(String[] args){
        System.out.println("Hello World!");
    }
}
```

### 要点说明

| 部分 | 含义 |
|------|------|
| `public class HelloWorld` | 定义一个公开的类，**类名必须与文件名一致**（大小写敏感） |
| `public static void main(String[] args)` | **主方法**——程序的入口，JVM 从这里开始执行 |
| `System.out.println(...)` | 向控制台输出一行文本 |
| `"Hello World!"` | 字符串字面量，用双引号包裹 |

### 编写工具

纯文本编辑器即可，例如：
- **记事本**（Notepad）
- **VS Code**（不带 Java 插件也行，纯文本）
- **Notepad++**
- **Sublime Text**

> 💡 文件名必须是 `HelloWorld.java`，因为类名是 `HelloWorld`，大小写也要一致！

---

## 三、编译代码（javac）

### 命令

```bash
javac HelloWorld.java
```

### 执行过程

1. 打开命令行（cmd / PowerShell / Git Bash / Terminal）
2. `cd` 到 `HelloWorld.java` 所在的目录
3. 运行 `javac HelloWorld.java`

### 编译结果

- 如果代码没有语法错误，**不会有任何输出**（无消息就是好消息 ✅）
- 会在同目录下生成一个 `HelloWorld.class` 文件（**字节码文件**）

```
e:\Java_learning\7.18 first learning\
├── HelloWorld.java      ← 你写的源代码
└── HelloWorld.class     ← 编译后生成的字节码
```

### 关于 javac

- `javac` = **Java Compiler**（Java 编译器）
- 它把 `.java` 源代码翻译成 JVM 能理解的 **字节码**（`.class` 文件）
- 字节码是跨平台的——一份字节码可以在任何装有 JVM 的系统上运行（**"一次编写，到处运行"**）

---

## 四、运行代码（java）

### 命令

```bash
java HelloWorld
```

### ⚠️ 注意

- **不要加 `.class` 后缀！** 是 `java HelloWorld`，不是 `java HelloWorld.class`
- JVM 会自动在当前目录寻找 `HelloWorld.class` 文件

### 运行结果

```
Hello World!
```

### 关于 java

- `java` 命令启动 **JVM（Java Virtual Machine，Java 虚拟机）**
- JVM 加载 `.class` 字节码文件
- 找到 `main` 方法并开始执行里面的代码
- `System.out.println("Hello World!")` 把文本输出到控制台

---

## 五、完整操作流程（命令一览）

```bash
# 1. 查看当前目录
pwd  # 或 cd 命令

# 2. 确认文件存在
ls -l HelloWorld.java   # 或 dir HelloWorld.java

# 3. 编译（生成 .class 文件）
javac HelloWorld.java

# 4. 运行（不加 .class 后缀！）
java HelloWorld

# 预期输出：
# Hello World!
```

---

## 六、常见问题排查

### ❌ `'javac' 不是内部或外部命令`
- **原因**：Java 没有安装，或没有配置环境变量
- **解决**：安装 JDK，并配置 `JAVA_HOME` 和 `PATH`

### ❌ `错误: 找不到符号`
- **原因**：代码中有拼写错误（比如 `String` 写成 `string`，`System` 写成 `system`）
- **解决**：检查大小写和拼写

### ❌ `错误: 找不到或无法加载主类 HelloWorld`
- **原因**：
  - 运行命令写成了 `java HelloWorld.class`（多加了 `.class`）
  - 当前目录不对，JVM 找不到 `.class` 文件
- **解决**：用 `java HelloWorld`（不加后缀），确认在正确目录下运行

### ❌ `类 HelloWorld 是公共的，应在名为 HelloWorld.java 的文件中声明`
- **原因**：文件名和类名不一致
- **解决**：把文件名改成 `HelloWorld.java`（或把类名改成文件名对应的名字）

---

## 七、核心概念速记

| 概念 | 一句话 |
|------|--------|
| **JDK** | Java Development Kit——开发工具包，包含 `javac`、`java` 等 |
| **JVM** | Java Virtual Machine——运行字节码的虚拟机，跨平台的关键 |
| **字节码** | `.class` 文件，JVM 能识别的中间代码 |
| **main 方法** | Java 程序的入口，JVM 从这里开始执行 |
| **编译** | `javac` 把源码 → 字节码，只做一次 |
| **运行** | `java` 启动 JVM 执行字节码，可以做无数次 |

---

## 八、学习路线图（下一步可以学什么）

```
✅ 第一天：Hello World + 编译运行流程
⬜ 第二天：变量、数据类型、基本输入输出
⬜ 第三天：运算符、条件判断（if/else）
⬜ 第四天：循环（for / while）
⬜ 第五天：数组
⬜ 第六天：方法（函数）
⬜ 第七天：面向对象——类与对象
...
```

---

> 🎉 **恭喜！你已成功迈出 Java 学习的第一步！**
> 虽然只是输出了一行 "Hello World!"，但你已经理解了 Java 最核心的 **编写 → 编译 → 运行** 流程。
> 这是所有 Java 程序（从简单小工具到大型企业系统）都遵循的范式。
