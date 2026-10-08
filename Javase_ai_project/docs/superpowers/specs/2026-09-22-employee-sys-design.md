# 人事管理系统（employee-sys）设计文档

- 日期：2026-09-22
- 产出位置：`employee-sys/src/tang/`
- 目标读者：刚学完 Java 基础篇（day01–day08）的学生
- 定位：综合练习项目，用于把已学知识点串成一条完整链路

## 一、背景与约束

### 学习者已掌握

- 面向对象：封装 / JavaBean、继承、多态、向上转型、`instanceof`、`final`、`static`
- 抽象类与模板方法模式、接口（含 JDK8 默认方法）
- 代码块、单例（饿汉 + 懒汉）
- 内部类（成员 / 静态 / 局部 / 匿名）、Lambda、方法引用（四种）
- `String` 常用 API、枚举（含带参枚举）
- `ArrayList` 基础增删改查（`add` / `get` / `set` / `remove` / `size`）
- `Scanner` 键盘输入、`switch`、`do-while`
- `Comparator` 与 `sort`

### 学习者尚未掌握（本项目**不使用**）

- `HashMap` / `HashSet` / `TreeMap` / `Iterator` / 增强 for / `Collections` 工具类
- 异常处理（自定义异常、`throws`、`try-with-resources`）
- IO 流与文件持久化
- 多线程、反射、注解（自定义）、JDBC、数据库
- 日期时间 API（`Date` / `LocalDate`）、`StringBuilder`、包装类系统性用法
- 自定义泛型

### 由此产生的硬约束

1. **纯内存**：数据存放在 `ArrayList<Employee>` 中，程序退出即全部丢失。不做文件读写。
2. **不用异常处理**：错误通过返回值（`boolean` / `null`）表达，由调用方判断后提示。禁止用 `try-catch` 兜底。
3. **不用集合框架新内容**：遍历一律用 `for (int i = 0; ...)` + `get(i)`。禁止 `for-each`、`Iterator`、`Map`。
4. **不用日期时间 API**：入离职日期等字段不纳入本期范围。
5. **不用 `StringBuilder`**：字符串拼接一律 `+`。

## 二、总体架构

采用最简的分层：**实体层（数据长什么样）→ 业务层（怎么管数据）→ 界面层（怎么和用户交互）**。三个层次各自独立，上层依赖下层，下层不知道上层的存在。

```
EmployeeSystem.java    界面层：main() + 菜单 + Scanner + switch
        ↓ 调用
EmployeeService.java   业务层：单例，ArrayList<Employee> 的增删改查与统计
        ↓ 操作
Employee.java          实体层：抽象类，定义共有属性与算薪骨架
        ↑ 继承
FullTimeEmployee / SalesEmployee / InternEmployee
Gender.java            枚举：性别
```

### 文件清单（共 7 个，扁平放在包 `tang` 下）

| 文件 | 类型 | 职责 |
|---|---|---|
| `Gender.java` | `enum` | 性别枚举，把「男 / 女」和显示文本绑在一起 |
| `Employee.java` | `abstract class` | 共有字段、构造器、getter/setter、模板方法 `calculateSalary()`、三个抽象方法、`toString()` |
| `FullTimeEmployee.java` | `class extends Employee` | 正式工：月薪 + 绩效奖金 |
| `SalesEmployee.java` | `class extends Employee` | 销售：底薪 + 销售提成 |
| `InternEmployee.java` | `class extends Employee` | 实习生：日薪 × 出勤天数，无奖金 |
| `EmployeeService.java` | `class`（饿汉单例） | 数据容器与全部业务方法 |
| `EmployeeSystem.java` | `class`（含 `main`） | 菜单循环、输入读取、结果打印 |

> 包名统一为 `tang`，与 `day01`–`day08` 的既有习惯保持一致。包内扁平、不分子包，避免在 7 个类的规模下引入无谓的目录层级。

## 三、实体层设计

### 3.1 `Gender` 枚举

```java
public enum Gender {
    MALE("男"),
    FEMALE("女");

    private final String label;          // 每个枚举项携带的显示文本

    Gender(String label) {               // 枚举构造器恒为 private，可不写
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
```

**为什么用枚举而不是 `String`**：性别只有两个合法取值。用 `String` 时 `"男"` / `"male"` / `"M"` 都能塞进去，编译期发现不了；用枚举则写错就是编译错误。这也复习了 `day07-opp/src/tang/enumdemo/Direction.java` 里学过的「带参枚举」。

### 3.2 `Employee` 抽象类——模板方法模式

```java
public abstract class Employee {
    private String id;        // 工号，唯一标识
    private String name;      // 姓名
    private Gender gender;    // 性别（枚举）
    private int age;          // 年龄
    private String dept;      // 部门

    public Employee(String id, String name, Gender gender, int age, String dept) { ... }

    // ===== getter / setter 省略 =====

    /**
     * 模板方法：定义「算薪」这件事的固定流程。
     * 加 final 是为了锁死流程——子类只能填步骤，不能改流程。
     */
    public final double calculateSalary() {
        return getBaseSalary() + getBonus();
    }

    /** 第一步：基本工资。不同工种算法不同，交给子类。 */
    protected abstract double getBaseSalary();

    /** 第二步：奖金 / 提成。不同工种算法不同，交给子类。 */
    protected abstract double getBonus();

    /** 职位名称。用于报表展示，由子类给出。 */
    public abstract String getPosition();

    /**
     * 统一的表格行输出。子类不需要各自重写。
     * 注意：父类看不到子类独有字段（月薪 / 提成率 / 日薪），
     * 但它不需要看到——职位名和最终工资都通过下面两个抽象方法多态取得。
     */
    @Override
    public String toString() {
        return String.format("%-8s %-6s %-4s %-4d %-8s %-8s %10.2f",
                id, name, gender.getLabel(), age, dept, getPosition(), calculateSalary());
    }
}
```

**设计要点**：

- **为什么 `Employee` 是抽象类而不是接口**：三种员工共享工号、姓名、性别、年龄、部门这一整套**状态**。接口不能存字段，用接口会导致每个实现类重复声明同样的字段和 getter/setter。抽象类表达「是一个」且能复用状态，接口表达「能做什么」——这里显然是前者。
- **为什么 `calculateSalary()` 加 `final`**：这是模板方法模式的关键。流程（基本工资 + 奖金）是公司制度，不允许子类改写；但两个步骤的具体算法因工种而异，用 `protected abstract` 下放。子类只能参与，不能推翻。
- **`toString()` 放在父类**：三种员工的表格行格式一致，写一次即可，子类继承。这是「继承复用」最直观的体现。

### 3.3 三个子类

| 子类 | 独有字段 | `getBaseSalary()` | `getBonus()` |
|---|---|---|---|
| `FullTimeEmployee` 正式工 | `monthlySalary` 月薪、`performanceFactor` 绩效系数 | `monthlySalary` | `monthlySalary * performanceFactor` |
| `SalesEmployee` 销售 | `baseSalary` 底薪、`salesAmount` 销售额、`commissionRate` 提成率 | `baseSalary` | `salesAmount * commissionRate` |
| `InternEmployee` 实习生 | `dailyWage` 日薪、`attendDays` 出勤天数 | `dailyWage * attendDays` | `0` |

每个子类构造器通过 `super(...)` 把共有字段交给父类初始化，这是 `day06-oop/src/tang/extends6constructor/` 学过的写法。

## 四、业务层设计：`EmployeeService`

### 4.1 饿汉式单例

```java
public class EmployeeService {
    /** 类加载时就创建好唯一实例 */
    private static final EmployeeService INSTANCE = new EmployeeService();

    /** 私有构造器：堵死外部 new 的路径 */
    private EmployeeService() { }

    public static EmployeeService getInstance() {
        return INSTANCE;
    }

    private final ArrayList<Employee> employees = new ArrayList<>();
    ...
}
```

**为什么用单例**：全系统只应该有一份员工数据。如果 `EmployeeSystem` 里能随手 `new EmployeeService()`，就可能出现两个互不相干的数据容器，A 里加的员工在 B 里查不到。私有构造器 + 静态方法把这条路堵死。

**为什么选饿汉式而非懒汉式**：饿汉式写法更短、天然没有多线程隐患（懒汉式需要额外判断）。本项目里唯一实例无论如何都会用到，没有「创建开销大到值得延迟」的理由。

### 4.2 对外方法清单

| 方法 | 返回 | 说明 |
|---|---|---|
| `boolean add(Employee e)` | 成功 `true` | 先按工号查重，重复则拒绝并返回 `false` |
| `Employee findById(String id)` | 找到的对象 | 找不到返回 `null`（调用方负责判空） |
| `boolean update(String id, Employee newEmp)` | 成功 `true` | 按工号定位后**整体替换**，工号保持不变 |
| `boolean delete(String id)` | 成功 `true` | 遍历找到后 `remove(i)` |
| `ArrayList<Employee> findAll()` | **副本** | 见下方说明 |
| `ArrayList<Employee> sortBySalary(boolean asc)` | 排好序的副本 | 用 `list.sort(...)` + Lambda |
| `int count()` | 总人数 | 供统计使用 |
| `double averageSalary()` | 平均工资 | 人数为 0 时返回 `0`，不抛异常 |
| `double maxSalary()` / `double minSalary()` | 最高 / 最低工资 | 空集合时返回 `0` |
| `void initSampleData()` | — | 预置几条示例数据，便于一运行就看到效果 |

### 4.3 两个关键决策

**`findAll()` 返回副本而不是内部列表本身。**
`ArrayList` 是引用类型，直接 `return employees` 等于把内部容器的钥匙交出去，调用方一个 `remove` 就能绕过业务层的所有校验。返回 `new ArrayList<>(employees)` 复制一份，调用方随便改都影响不到真实数据。（代价是多一次复制，在这个数据量下可以忽略。）

**`update` 用整体替换，而不是逐字段修改。**
员工的工资相关字段（月薪 / 提成率 / 日薪）属于子类独有。要逐个修改就得先 `instanceof` 判断真实类型、再向下转型——这正是 `day06-oop/src/tang/polymophsmdemo/PolymorphismDemo.java:123` 那个 `ClassCastException` 演示想说明的麻烦事。改成「按原工号整体换一个新对象」，就完全绕开了类型判断，也让界面层能用同一套输入流程同时服务「添加」和「修改」。

### 4.4 错误表达方式

不使用异常，全部靠返回值：

- 工号重复 → `add` 返回 `false`
- 工号不存在 → `findById` 返回 `null`，`update` / `delete` 返回 `false`
- 平均工资分母为 0 → 提前判断 `count() == 0`，返回 `0`

调用方（界面层）负责把这些返回值翻译成用户能看懂的中文提示。

## 五、界面层设计：`EmployeeSystem`

### 5.1 菜单循环

```
========= 人事管理系统 =========
  1. 添加员工
  2. 查询所有员工
  3. 按工号查询
  4. 修改员工
  5. 删除员工
  6. 按工资排序
  7. 统计报表
  0. 退出系统
================================
请输入操作编号：
```

用 `do-while` + `switch` 实现：`switch` 分发到各个私有静态方法，`while (choice != 0)` 保证处理完一次操作后回到菜单，选 0 才跳出。`switch` 的 `default` 分支处理非法编号——这就是不用异常时的「兜底」。

### 5.2 输入读取的两个坑（必须写进注释）

**坑一：`nextInt()` 把回车留在了缓冲区。**
`nextInt()` 只读走数字，不读走你按下的回车。紧接着调 `nextLine()` 会立刻读到一个空字符串。解法是在 `nextInt()` 之后补一句 `sc.nextLine();` 把回车吃掉。这是初学者最高频的 bug，代码里会逐处标注。

**坑二：输入非数字会直接崩。**
`nextInt()` 遇到 `abc` 会抛 `InputMismatchException`。

按约束本项目不用 `try-catch`，因此这个异常**不处理**，程序会直接中断退出。这是一个已知缺陷，不是遗漏——要修它得先学异常处理章节。代码会在每个 `nextInt()` 附近用注释标明这一点，提醒只在菜单处输入数字。

`String` / 数字输入的分工因此固定为：

- 文本字段（姓名、部门、工号）→ `sc.nextLine()`
- 数字字段（年龄、菜单编号、月薪、销售额等）→ `sc.nextInt()` / `sc.nextDouble()`

> 为什么不用 `nextLine()` 读数字再做转换：那需要 `Integer.parseInt` / `Double.parseDouble`，属于尚未系统学习的包装类内容。手写字符串转数字又会把代码带偏成本项目的重点。用 `nextInt()` + 后续 `nextLine()` 是此刻最贴合已有知识的写法。

### 5.3 输入流程复用

「添加员工」和「修改员工」需要采集的字段完全一样，只差一个工号（修改时沿用原工号、不可改）。因此抽出一个私有方法：

```java
private static Employee inputEmployee(Scanner sc, String id)
```

它按顺序询问：职位类型 → 姓名 → 性别 → 年龄 → 部门 → 该职位独有字段，最后 `switch` 职位类型 `new` 出对应的子类对象并 `return`。

返回类型声明为 `Employee`（父类），实际返回的是子类对象——**这就是向上转型**。界面层完全不需要知道具体是哪个子类。

### 5.4 多态在报表里的体现

打印工资表时，循环体里只有一行关键代码：

```java
double salary = employees.get(i).calculateSalary();
```

同一个 `calculateSalary()` 调用，正式工走「月薪 + 月薪×绩效」，销售走「底薪 + 销售额×提成率」，实习生走「日薪×出勤天数」。**编译期看父类，运行期走子类**——这就是多态的价值：界面层不需要写任何 `if (是正式工) ... else if (是销售) ...`。

### 5.5 排序：Lambda / 方法引用

```java
// 写法一：Lambda
list.sort((e1, e2) -> Double.compare(e1.calculateSalary(), e2.calculateSalary()));

// 写法二：方法引用（复习 day08 学过的「特定类型方法引用」）
list.sort(Comparator.comparingDouble(Employee::calculateSalary));
```

`ArrayList` 自带的 `sort` 方法接收一个 `Comparator`，与 `day08-oop/src/tang/method1/Demo2.java` 里 `Arrays.sort` 的用法同源。代码中两种写法都会出现并注明差异。

## 六、已知限制（诚实标注）

1. **数据不持久化**：程序一关，添加的员工全部消失。这是本期「纯内存」约束的直接结果，不是 bug。文件持久化属于 IO 章节。
2. **中文表格对齐不准**：`String.format("%-8s", name)` 按**字符个数**补空格，而一个汉字在终端占**两个字符宽**。所以「张三」和「欧阳娜娜」的列不会严格对齐。要真正对齐需要计算显示宽度，超出本期范围。代码注释中会说明这一点，避免误以为是写错了。
3. **`nextInt()` 输入非数字会崩**：见 5.2，属于异常处理的遗留问题。
4. **年龄、工资等数值不做合法性校验**：输入 `-5` 岁的员工会被接受。校验属于业务规则，本期从简。

## 七、验收标准

1. 7 个 `.java` 文件全部位于 `employee-sys/src/tang/`，包声明为 `package tang;`。
2. 项目能在 IDEA 中编译通过、直接运行 `EmployeeSystem` 的 `main` 方法，无红色报错。
3. 启动后能看到菜单；输入 `1` 能依次添加正式工、销售、实习生各至少 1 名。
4. 输入 `2` 能看到全部员工，**同一张表里三种职位的工资按各自规则算出且互不相同**（证明多态生效）。
5. 输入 `3` 能按工号查到指定员工；查不存在的工号有中文提示且不崩溃。
6. 输入 `4` 能修改员工信息，修改后工号不变、其他字段已更新。
7. 输入 `5` 能删除员工，删除后再查显示不存在。
8. 添加已存在的工号会被拒绝，并有中文提示。
9. 输入 `6` 能按工资升 / 降序打印，顺序正确。
10. 输入 `7` 能打印总人数、平均工资、最高 / 最低工资。
11. 输入 `0` 能正常退出；输入 `9` 等非法编号有提示并回到菜单，不崩溃。
12. **注释覆盖**：每个类有中文 Javadoc 类注释（含 `@author tang`），每个方法有中文说明；涉及继承 / 多态 / 模板方法 / 单例 / Lambda / 枚举 / `nextInt` 回车坑的位置有解释性注释，说明「为什么这么写」而不只是「这行做了什么」。

## 八、明确不做（YAGNI）

以下内容本期**不实现**，留作后续扩展练习：

- 登录与权限（管理员 / 普通用户）
- 部门增删改查、部门与员工的关联关系
- 考勤记录、请假、晋升
- 数据持久化到文件
- 图形界面（Swing）
- 单元测试
