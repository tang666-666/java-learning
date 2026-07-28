# IntelliJ IDEA 开发 Java 程序步骤指南

> 适用版本：IntelliJ IDEA Community / Ultimate
> 对应项目：`Javase_ai_project`（本 JavaSE 学习项目）

---

## 目录

1. [创建 Project（工程）](#1-创建-project工程)
2. [创建 Module（模块）](#2-创建-module模块)
3. [创建 Package（包）](#3-创建-package包)
4. [创建 Class（类）](#4-创建-class类)
5. [完整流程演示：HelloWorld](#5-完整流程演示helloworld)
6. [常用快捷键](#6-常用快捷键)

---

## 1. 创建 Project（工程）

Project 是 IntelliJ IDEA 中**最顶层的组织单位**，代表一个完整的项目。

### 步骤

| 步骤 | 操作 |
|------|------|
| **①** | 打开 IDEA，点击 `File → New → Project` |
| **②** | 左侧选择 **New Project**（不要选 Empty Project） |
| **③** | 填写以下信息： |
|       | - **Name**：`Javase_ai_project`（项目名称） |
|       | - **Location**：`E:\Java_learning\Javase_ai_project`（存放路径） |
|       | - **Language**：`Java` |
|       | - **Build system**：`IntelliJ`（纯 Java 学习用 IDEA 原生构建，暂不需要 Maven/Gradle） |
|       | - **JDK**：选择已安装的 JDK（如 `JDK 17` / `JDK 21`） |
| **④** | 点击 **Create** |

### 完成后结构

```
Javase_ai_project/
├── .idea/               # IDEA 项目配置（自动生成）
├── Javase_ai_project.iml  # 项目模块描述（自动生成）
└── src/                 # 源代码根目录（之后可以手动创建）
```

### 说明

- 一个 Project 可以包含多个 Module（模块）。
- `.idea/` 目录和 `.iml` 文件是 IDEA 的配置，**不要手动修改**，建议加入 `.gitignore`。
- 如果选的是 **Empty Project**，会先创建一个空壳，需要手动添加 Module。

---

## 2. 创建 Module（模块）

Module 是 Project 下的**子项目单元**，每个 Module 有自己独立的源码和依赖。在我们这个项目中，每一"天"的学习内容就是一个 Module。

### 步骤

| 步骤 | 操作 |
|------|------|
| **①** | 菜单 `File → New → Module` |
| **②** | 左侧选择 **Java** |
| **③** | 填写： |
|       | - **Name**：`day01-helloworld`（模块名，见名知意） |
|       | - **Content root**：自动填充为 `.../Javase_ai_project/day01-helloworld` |
|       | - **Module file location**：自动填充 |
| **④** | 点击 **Finish** |

### 完成后结构

```
Javase_ai_project/
├── .idea/
├── day01-helloworld/          # 新建的 Module
│   ├── day01-helloworld.iml   # 模块描述文件
│   └── src/                   # 该模块的源码目录
├── Javase_ai_project.iml
└── src/                       # 此时项目根下的 src 可以删除
```

### 说明

- 创建 Module 后，可以在 `.idea/modules.xml` 中看到所有模块的注册信息。
- 每个 Module 独立编译，输出到 `out/production/<module-name>/`。
- 建议：后续每天的学习内容（day02-basic、day03-array 等）都按此方式创建 Module。

---

## 3. 创建 Package（包）

Package 对应文件系统中的**目录**，用于对类进行命名空间管理。Java **强制要求**包的命名规范。

### 命名规范

```
域名倒序 + 项目名/模块名
示例：tang.helloworld
```

### 步骤

| 方式 | 操作 |
|------|------|
| **方式一：连写法**（推荐） | 在 `src` 目录上右键 → `New → Package` → 输入 `tang.helloworld` ↓ |
|                           | IDEA 自动创建 `tang/helloworld/` 两层目录 |
| **方式二：逐层创建** | 右键 `src` → `New → Directory` → 先建 `tang` → 再在 `tang` 下建 `helloworld` |

### 完成后结构

```
day01-helloworld/
├── day01-helloworld.iml
└── src/
    └── tang/
        └── helloworld/     # 包对应的目录
```

### 规则

1. **包名全小写**，用 `.` 分隔层级。
2. 一个 `.java` 文件的第一行必须是 `package` 声明，且与目录结构一致：

```java
package tang.helloworld;
```

3. 包名一般使用**公司/个人域名倒序**：
   - `com.公司名.项目名.模块名`
   - `org.组织名.项目名`
   - 个人学习可以用 `tang.xxx`、`learn.xxx` 等。

---

## 4. 创建 Class（类）

### 步骤

| 步骤 | 操作 |
|------|------|
| **①** | 在目标包 `tang.helloworld` 上右键 |
| **②** | `New → Java Class` |
| **③** | 输入类名 `HelloWorld` |
| **④** | **Kind** 选择 `Class`，点击 **OK** |

### IDEA 自动生成的内容

```java
package tang.helloworld;

public class HelloWorld {
}
```

### 说明

- **类名首字母大写**，遵循驼峰命名法（PascalCase）。
- `Kind` 选项说明：
  | Kind | 说明 |
  |------|------|
  | `Class` | 普通类 |
  | `Interface` | 接口 |
  | `Enum` | 枚举 |
  | `Record` | 记录类（JDK 14+） |
  | `Annotation` | 注解 |

---

## 5. 完整流程演示：HelloWorld

以本项目的 `HelloWorld.java` 为例，展示从 0 到 1 的完整过程。

### 5.1 创建入口 main 方法

在类中键入 `main` 或 `psvm`，IDEA 自动补全：

```java
public static void main(String[] args) {

}
```

### 5.2 编写输出语句

在 main 方法内键入 `sout`，IDEA 自动补全：

```java
System.out.println("Hello world!");
```

### 5.3 完整代码

```java
package tang.helloworld;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
```

### 5.4 运行程序

| 方式 | 操作 |
|------|------|
| **方式一** | 点击类左侧行号旁的绿色 ▶ 三角按钮 → `Run 'HelloWorld.main()'` |
| **方式二** | 右键代码编辑区 → `Run 'HelloWorld.main()'` |
| **方式三** | 快捷键 `Ctrl + Shift + F10`（运行当前文件） |

### 5.5 查看运行结果

```
Hello world!
```

编译后的 `.class` 文件自动输出到：`out/production/day01-helloworld/tang/helloworld/HelloWorld.class`

---

## 6. 常用快捷键

### 编辑类

| 快捷键 | 功能 |
|--------|------|
| `psvm` + Tab | 生成 `public static void main` |
| `sout` + Tab | 生成 `System.out.println()` |
| `Ctrl + D` | 复制当前行 |
| `Ctrl + Y` | 删除当前行 |
| `Alt + Enter` | 显示意图操作（快速修复） |
| `Ctrl + /` | 单行注释 / 取消注释 |
| `Ctrl + Shift + /` | 多行注释 |

### 运行与导航

| 快捷键 | 功能 |
|--------|------|
| `Shift + F10` | 运行当前项目 |
| `Ctrl + Shift + F10` | 运行当前文件 |
| `Ctrl + N` | 搜索类 |
| `Ctrl + E` | 最近打开的文件 |
| `Alt + 1` | 打开/关闭 Project 面板 |

---

## 附：Project / Module / Package / Class 关系图

```
Project (工程)                        Javase_ai_project
    │
    ├── Module (模块)                 day01-helloworld
    │       │
    │       ├── src/                 源码根目录
    │       │   └── Package (包)     tang.helloworld
    │       │           │
    │       │           └── Class (类)   HelloWorld.java
    │       │
    │       └── out/                 编译输出
    │               └── Package
    │                       └── HelloWorld.class
    │
    ├── Module (模块)                 day02-basic
    └── Module (模块)                 day03-array（后续添加）
```

### 一句话总结

> **Project（工程）** 包含 **Module（模块）**，Module 的 `src` 下建 **Package（包）**，Package 下放 **Class（类）**。

---

*本指南基于 `Javase_ai_project` 项目结构编写，后续可扩展更多 module 对应每一天的学习内容。*
