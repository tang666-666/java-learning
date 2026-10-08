# 人事管理系统实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 为刚学完 Java 基础篇（day01–day08）的学生构建一个纯内存的控制台人事管理系统，用 7 个类把封装、继承、多态、抽象类、模板方法、单例、枚举、Lambda、方法引用串成一条完整链路。

**Architecture:** 三层最简分层。实体层 `Employee` 抽象类用模板方法模式定义算薪骨架，三个子类实现各自算法；业务层 `EmployeeService` 以饿汉单例持有 `ArrayList<Employee>`，提供全部增删改查与统计；界面层 `EmployeeSystem` 用 `do-while` + `switch` 组织控制台菜单。上层依赖下层，下层不知道上层的存在。

**Tech Stack:** Java 21（`javac 21.0.11`），无构建工具、无第三方依赖、无单元测试框架。IDEA 纯模块（`employee-sys/employee-sys.iml`，`src` 为源码根）。

**Spec:** `docs/superpowers/specs/2026-09-22-employee-sys-design.md`

## Global Constraints

以下约束来自 spec，**每个任务都隐含适用**，不再逐条重复：

- **包名恒为 `package tang;`**，文件平铺在 `employee-sys/src/tang/`，不建子包。共 7 个 `.java` 文件，不多不少。
- **纯内存**：数据只存在 `ArrayList<Employee>` 中，程序退出即丢失。禁止任何文件读写。
- **禁止 `try-catch` / `throws` / 自定义异常**。错误一律用返回值表达：`boolean` 表成败，`null` 表不存在。
- **禁止集合框架新内容**：不得使用 `for-each`、`Iterator`、`Map`、`Set`、`Collections` 工具类。遍历一律 `for (int i = 0; i < list.size(); i++)` + `get(i)`。
- **禁止 `StringBuilder`**，字符串拼接一律用 `+`。
- **禁止日期时间 API**（`Date` / `LocalDate` / `SimpleDateFormat`）。
- **禁止 `Integer.parseInt` / `Double.parseDouble`** 等包装类转换方法。
- **注释一律中文**。每个类有 Javadoc 类注释，含 `@author tang`；每个方法有中文说明；解释「为什么这么写」而非「这行做了什么」。
- **编译命令固定为**：`javac -encoding UTF-8 -d employee-sys/out <源文件列表>`（`-encoding UTF-8` 不可省，源码含中文）。
- **运行命令固定为**：`java -Dstdout.encoding=UTF-8 -cp employee-sys/out tang.<类名>`（`-Dstdout.encoding=UTF-8` 不可省，否则中文输出乱码）。
- **提交纪律**：本仓库有大量与本任务无关的未提交改动（`.claude/sessions/*`、`out/*.class`、`.idea/*` 等）。每次提交**必须逐个列出精确文件路径**，禁止 `git add .` 或 `git add -A`。若你不想提交，跳过提交步骤不影响后续任务。

### 本计划的两类验证手段

因为 spec 明确不做单元测试，且 Java 里抽象类无法单独运行，验证分两类：

1. **探针验证（Task 1–4）**：在项目外的临时目录 `/tmp/esys-probe/tang/` 写一个带 `main` 的探针类，用**同一个包名 `tang`**，与项目源码一起编译。这样项目目录始终只保留 7 个正式文件，探针用完即弃。
2. **管道验证（Task 5–6）**：程序有真实菜单后，用 `printf` 把按键序列喂给 `stdin`，核对输出。

---

## 文件结构

| 文件 | 类型 | 职责 | 由哪个任务创建 |
|---|---|---|---|
| `employee-sys/src/tang/Gender.java` | `enum` | 性别枚举，枚举项携带中文显示文本 | Task 1 |
| `employee-sys/src/tang/Employee.java` | `abstract class` | 5 个共有字段、构造器、getter/setter、模板方法 `calculateSalary()`、3 个抽象方法、`toString()` | Task 1 |
| `employee-sys/src/tang/FullTimeEmployee.java` | `class` | 正式工：月薪 + 月薪×绩效系数 | Task 2 |
| `employee-sys/src/tang/SalesEmployee.java` | `class` | 销售：底薪 + 销售额×提成率 | Task 2 |
| `employee-sys/src/tang/InternEmployee.java` | `class` | 实习生：日薪×出勤天数，无奖金 | Task 2 |
| `employee-sys/src/tang/EmployeeService.java` | `class`（单例） | 数据容器与全部业务方法 | Task 3 + Task 4 |
| `employee-sys/src/tang/EmployeeSystem.java` | `class`（含 `main`） | 菜单循环、输入读取、结果打印 | Task 5 + Task 6 |

---

## Task 1: 性别枚举与员工抽象类

**Files:**
- Create: `employee-sys/src/tang/Gender.java`
- Create: `employee-sys/src/tang/Employee.java`

**Interfaces:**
- Consumes: 无（这是第一个任务）
- Produces:
  - `enum Gender { MALE, FEMALE }`，方法 `String getLabel()`
  - `abstract class Employee`，构造器 `Employee(String id, String name, Gender gender, int age, String dept)`
  - `String getId()` / `void setId(String)`
  - `String getName()` / `void setName(String)`
  - `Gender getGender()` / `void setGender(Gender)`
  - `int getAge()` / `void setAge(int)`
  - `String getDept()` / `void setDept(String)`
  - `public final double calculateSalary()`
  - `protected abstract double getBaseSalary()`
  - `protected abstract double getBonus()`
  - `public abstract String getPosition()`
  - `public String toString()`

---

- [ ] **Step 1: 创建 `Gender.java`**

写入 `employee-sys/src/tang/Gender.java`：

```java
package tang;

/**
 * 性别枚举。
 * <p>
 * 为什么用枚举而不是 String：性别只有「男」「女」两个合法取值。
 * 若用 String，写成 "男"、"male"、"M" 都能编译通过，错误要到运行时才发现；
 * 用枚举则写错就是编译错误，把问题挡在编译期。
 * <p>
 * 为什么枚举项要带参数：把「枚举值」和「界面上显示的文字」绑在一起。
 * 如果只写 MALE/FEMALE，打印时就得在别处写 if 判断转成中文，
 * 那样一旦新增取值，所有 if 都要改。绑在枚举里则只需改这一个文件。
 *
 * @author tang
 * @version 1.0
 */
public enum Gender {

    /** 男性，界面显示为「男」 */
    MALE("男"),

    /** 女性，界面显示为「女」 */
    FEMALE("女");

    /** 该枚举项对应的中文显示文本。final 表示一旦创建就不能改。 */
    private final String label;

    /**
     * 枚举的构造器。
     * 注意：枚举构造器只能是 private，写不写 private 都一样，这里省略不写。
     * 它由 JVM 在加载枚举类时为每一行枚举项各调用一次，程序员无法手动 new。
     *
     * @param label 中文显示文本
     */
    Gender(String label) {
        this.label = label;
    }

    /**
     * 取得该性别的中文显示文本。
     *
     * @return 「男」或「女」
     */
    public String getLabel() {
        return label;
    }
}
```

- [ ] **Step 2: 创建 `Employee.java`**

写入 `employee-sys/src/tang/Employee.java`：

```java
package tang;

/**
 * 员工抽象类，所有工种员工的父类。
 * <p>
 * 为什么用抽象类而不是接口：三种员工共享工号、姓名、性别、年龄、部门这一整套
 * 「状态」。接口不能存字段，用接口会导致每个实现类重复声明同样的字段和
 * getter/setter。抽象类能表达「是一个」并复用状态，接口表达「能做什么」——
 * 这里显然是前者。
 * <p>
 * 为什么用 abstract 修饰类：本类只描述「员工」这个概念，现实中不存在
 * 一个不属于任何具体工种的员工。加 abstract 后编译器会阻止 new Employee(...)，
 * 强制使用者必须 new 某个具体子类。
 *
 * @author tang
 * @version 1.0
 */
public abstract class Employee {

    /** 工号，全局唯一，用作查找依据 */
    private String id;

    /** 姓名 */
    private String name;

    /** 性别，取值来自 Gender 枚举 */
    private Gender gender;

    /** 年龄 */
    private int age;

    /** 所属部门 */
    private String dept;

    /**
     * 构造器。共有字段在此统一初始化，子类通过 super(...) 调用它。
     *
     * @param id     工号
     * @param name   姓名
     * @param gender 性别
     * @param age    年龄
     * @param dept   部门
     */
    public Employee(String id, String name, Gender gender, int age, String dept) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.dept = dept;
    }

    // ==================== getter / setter ====================
    // 私有字段 + 公开的读写方法，这就是「封装」：
    // 外部不能直接改字段，必须走方法，将来要加校验只需改方法内部。

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    // ==================== 模板方法模式 ====================

    /**
     * 计算该员工的实发工资。
     * <p>
     * 这是一个「模板方法」：它定义了算薪的固定流程——基本工资 + 奖金。
     * 流程本身是公司制度，不允许子类改写，所以加 final 锁死。
     * 但两个步骤的具体算法因工种而异，用 protected abstract 下放给子类。
     * 子类只能填空，不能推翻流程——这就是模板方法模式的价值。
     * <p>
     * 注意这里只写了 getBaseSalary() + getBonus()，完全不知道具体是哪种员工。
     * 运行时会自动调用子类的实现，这叫「编译期看父类，运行期走子类」，
     * 也就是多态。
     *
     * @return 实发工资，单位：元
     */
    public final double calculateSalary() {
        return getBaseSalary() + getBonus();
    }

    /**
     * 第一步：基本工资。各工种算法不同，交给子类实现。
     *
     * @return 基本工资，单位：元
     */
    protected abstract double getBaseSalary();

    /**
     * 第二步：奖金或提成。各工种算法不同，交给子类实现。
     * 用 protected 是因为这是内部算薪步骤，不对外暴露。
     *
     * @return 奖金金额，单位：元
     */
    protected abstract double getBonus();

    /**
     * 职位名称，用于报表展示和菜单回显。
     *
     * @return 如「正式工」「销售」「实习生」
     */
    public abstract String getPosition();

    /**
     * 输出为一行表格数据。
     * <p>
     * 为什么写在父类而不是各子类：三种员工的表格行格式完全一致，
     * 写一次子类继承即可，这是继承复用最直观的体现。
     * <p>
     * 父类看不到子类独有字段（月薪 / 提成率 / 日薪），但它不需要看到——
     * 职位名通过 getPosition() 取得，工资通过 calculateSalary() 算出，
     * 两个方法都是多态的。
     * <p>
     * 关于对齐：String.format 的 %-8s 是按「字符个数」补空格，
     * 而一个汉字在终端占「两个字符宽」。所以「张三」和「欧阳娜娜」这两行
     * 不会严格对齐——这不是 bug，是终端显示的固有限制，要真正对齐
     * 需要自己算显示宽度，超出本项目范围。
     *
     * @return 格式化的表格行
     */
    @Override
    public String toString() {
        return String.format("%-8s %-6s %-4s %-4d %-8s %-8s %10.2f",
                id, name, gender.getLabel(), age, dept, getPosition(), calculateSalary());
    }
}
```

- [ ] **Step 3: 编译验证**

运行：

```bash
javac -encoding UTF-8 -d employee-sys/out \
  employee-sys/src/tang/Gender.java \
  employee-sys/src/tang/Employee.java
```

预期：**没有任何输出**，退出码 0。javac 静默即成功——有错才会打印。

这一步能验证什么：`Employee` 是抽象类，无法实例化，所以只能编译。但编译本身就有意义——它证明方法签名一致、抽象方法声明合法、`Gender` 的引用正确、`String.format` 的调用在类型层面成立（参数是 `String.format(String, Object...)` 能接受的类型）。

**不要指望编译能验证格式串**：`String.format` 的格式串是普通字符串，javac 完全不检查它的内容——占位符个数对不上、`%d` 配了个 `String`，编译一律照过，`-Xlint:all` 也不会有任何 warning。这类错误只在运行到那一行时才以 `MissingFormatArgumentException` / `IllegalFormatConversionException` 的形式抛出。所以「格式串与参数匹配」这件事，证据只能来自运行程序（Task 2 Step 6 的探针输出、Task 5 Step 3 起的管道输出）。

若报 `错误: 找不到符号 Gender`，检查 `Gender.java` 是否与 `Employee.java` 在同一目录且同为 `package tang;`。

- [ ] **Step 4: 提交**

```bash
git add employee-sys/src/tang/Gender.java employee-sys/src/tang/Employee.java
git commit -m "feat(employee-sys): 添加性别枚举与员工抽象类"
```

注意：这里逐个列出了文件路径，**没有用 `git add .`**——仓库里有大量无关改动，用 `.` 会把它们一起提交进去。

---

## Task 2: 三个员工子类与多态验证

**Files:**
- Create: `employee-sys/src/tang/FullTimeEmployee.java`
- Create: `employee-sys/src/tang/SalesEmployee.java`
- Create: `employee-sys/src/tang/InternEmployee.java`
- Create（临时，项目外）: `/tmp/esys-probe/tang/SalaryProbe.java`

**Interfaces:**
- Consumes: `Employee` 抽象类（Task 1）—— 构造器、`calculateSalary()`、`getPosition()`、`toString()`
- Produces:
  - `FullTimeEmployee(String id, String name, Gender gender, int age, String dept, double monthlySalary, double performanceFactor)`
  - `SalesEmployee(String id, String name, Gender gender, int age, String dept, double baseSalary, double salesAmount, double commissionRate)`
  - `InternEmployee(String id, String name, Gender gender, int age, String dept, double dailyWage, int attendDays)`

**三种工种的算薪规则：**

| 子类 | `getBaseSalary()` | `getBonus()` |
|---|---|---|
| `FullTimeEmployee` | `monthlySalary` | `monthlySalary * performanceFactor` |
| `SalesEmployee` | `baseSalary` | `salesAmount * commissionRate` |
| `InternEmployee` | `dailyWage * attendDays` | `0` |

---

- [ ] **Step 1: 创建 `FullTimeEmployee.java`**

写入 `employee-sys/src/tang/FullTimeEmployee.java`：

```java
package tang;

/**
 * 正式工：固定月薪 + 绩效奖金。
 * <p>
 * 继承 Employee 后自动拥有工号、姓名、性别、年龄、部门五个字段及其
 * getter/setter，本类只需要声明自己独有的两个字段。
 *
 * @author tang
 * @version 1.0
 */
public class FullTimeEmployee extends Employee {

    /** 月薪，单位：元 */
    private double monthlySalary;

    /** 绩效系数。0.2 表示年终/月度奖金为月薪的 20% */
    private double performanceFactor;

    /**
     * 构造器。
     * <p>
     * super(...) 必须放在第一行——子类对象创建时，一定先初始化父类那部分，
     * 再初始化子类那部分。共有字段交给父类构造器，独有字段自己赋值。
     *
     * @param id                工号
     * @param name              姓名
     * @param gender            性别
     * @param age               年龄
     * @param dept              部门
     * @param monthlySalary     月薪
     * @param performanceFactor 绩效系数
     */
    public FullTimeEmployee(String id, String name, Gender gender, int age, String dept,
                            double monthlySalary, double performanceFactor) {
        super(id, name, gender, age, dept);
        this.monthlySalary = monthlySalary;
        this.performanceFactor = performanceFactor;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public double getPerformanceFactor() {
        return performanceFactor;
    }

    public void setPerformanceFactor(double performanceFactor) {
        this.performanceFactor = performanceFactor;
    }

    /**
     * 正式工的基本工资就是月薪。
     * <p>
     * 注意访问权限是 protected 而不是 public：父类里声明的是
     * protected abstract，子类重写时不能把权限改小（不能写成 private），
     * 但可以放大成 public。这里保持 protected 与父类一致。
     */
    @Override
    protected double getBaseSalary() {
        return monthlySalary;
    }

    /** 绩效奖金 = 月薪 × 绩效系数 */
    @Override
    protected double getBonus() {
        return monthlySalary * performanceFactor;
    }

    /** 职位名称，多态取得 */
    @Override
    public String getPosition() {
        return "正式工";
    }
}
```

- [ ] **Step 2: 创建 `SalesEmployee.java`**

写入 `employee-sys/src/tang/SalesEmployee.java`：

```java
package tang;

/**
 * 销售：底薪 + 销售提成。
 * <p>
 * 与正式工的区别在于奖金算法完全不同——销售的奖金跟业绩挂钩，
 * 这正是需要多态的原因：同样的 calculateSalary() 调用，
 * 不同子类走出完全不同的结果。
 *
 * @author tang
 * @version 1.0
 */
public class SalesEmployee extends Employee {

    /** 底薪，单位：元 */
    private double baseSalary;

    /** 本月销售额，单位：元 */
    private double salesAmount;

    /** 提成率。0.05 表示按销售额的 5% 提成 */
    private double commissionRate;

    /**
     * 构造器。
     *
     * @param id             工号
     * @param name           姓名
     * @param gender         性别
     * @param age            年龄
     * @param dept           部门
     * @param baseSalary     底薪
     * @param salesAmount    本月销售额
     * @param commissionRate 提成率
     */
    public SalesEmployee(String id, String name, Gender gender, int age, String dept,
                         double baseSalary, double salesAmount, double commissionRate) {
        super(id, name, gender, age, dept);
        this.baseSalary = baseSalary;
        this.salesAmount = salesAmount;
        this.commissionRate = commissionRate;
    }

    public double getBaseSalaryValue() {
        return baseSalary;
    }

    public void setBaseSalaryValue(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public double getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(double salesAmount) {
        this.salesAmount = salesAmount;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
    }

    /**
     * 销售的基本工资就是底薪。
     * <p>
     * 注意方法名叫 getBaseSalary，与字段 baseSalary 不冲突——
     * 字段名和方法名可以重名，但如果 getter 直接叫 getBaseSalary()，
     * 方法体内写 return baseSalary; 依然合法（读的是字段）。
     * 这里为了不让读者混淆「字段」和「父类的抽象方法」，
     * 特意把 getter 命名为 getBaseSalaryValue()。
     */
    @Override
    protected double getBaseSalary() {
        return baseSalary;
    }

    /** 销售提成 = 销售额 × 提成率 */
    @Override
    protected double getBonus() {
        return salesAmount * commissionRate;
    }

    /** 职位名称，多态取得 */
    @Override
    public String getPosition() {
        return "销售";
    }
}
```

- [ ] **Step 3: 创建 `InternEmployee.java`**

写入 `employee-sys/src/tang/InternEmployee.java`：

```java
package tang;

/**
 * 实习生：按天计酬，没有奖金。
 * <p>
 * 这个类存在的意义之一是证明「抽象方法可以被实现为返回常量」——
 * getBonus() 直接返回 0 也是合法实现。父类不需要为「没有奖金」
 * 这种情况做任何特殊处理。
 *
 * @author tang
 * @version 1.0
 */
public class InternEmployee extends Employee {

    /** 日薪，单位：元 */
    private double dailyWage;

    /** 本月出勤天数 */
    private int attendDays;

    /**
     * 构造器。
     *
     * @param id         工号
     * @param name       姓名
     * @param gender     性别
     * @param age        年龄
     * @param dept       部门
     * @param dailyWage  日薪
     * @param attendDays 出勤天数
     */
    public InternEmployee(String id, String name, Gender gender, int age, String dept,
                          double dailyWage, int attendDays) {
        super(id, name, gender, age, dept);
        this.dailyWage = dailyWage;
        this.attendDays = attendDays;
    }

    public double getDailyWage() {
        return dailyWage;
    }

    public void setDailyWage(double dailyWage) {
        this.dailyWage = dailyWage;
    }

    public int getAttendDays() {
        return attendDays;
    }

    public void setAttendDays(int attendDays) {
        this.attendDays = attendDays;
    }

    /** 实习生的基本工资 = 日薪 × 出勤天数 */
    @Override
    protected double getBaseSalary() {
        return dailyWage * attendDays;
    }

    /** 实习生没有奖金，返回 0 即可 */
    @Override
    protected double getBonus() {
        return 0;
    }

    /** 职位名称，多态取得 */
    @Override
    public String getPosition() {
        return "实习生";
    }
}
```

- [ ] **Step 4: 创建探针目录并写入 `SalaryProbe.java`**

先建目录：

```bash
mkdir -p /tmp/esys-probe/tang
```

写入 `/tmp/esys-probe/tang/SalaryProbe.java`（注意：这是**项目外**的临时文件，包名同样是 `tang`）：

```java
package tang;

import java.util.ArrayList;

/**
 * 临时探针：验证三个子类的多态算薪是否正确。
 * 这个文件不在项目源码目录里，只用于开发期验证，验证完即可删除。
 */
public class SalaryProbe {

    public static void main(String[] args) {
        // 关键点：集合的泛型写的是父类 Employee，
        // 但实际存进去的是三个不同的子类对象——这叫向上转型。
        ArrayList<Employee> list = new ArrayList<>();

        list.add(new FullTimeEmployee("E001", "张三", Gender.MALE, 30, "研发部", 8000, 0.2));
        list.add(new SalesEmployee("E002", "李四", Gender.FEMALE, 28, "销售部", 3000, 50000, 0.05));
        list.add(new InternEmployee("E003", "王五", Gender.MALE, 22, "研发部", 150, 20));

        for (int i = 0; i < list.size(); i++) {
            Employee e = list.get(i);
            System.out.println(e);
            System.out.println("  -> 职位=" + e.getPosition()
                    + " 基本工资=" + e.getBaseSalary()
                    + " 奖金=" + e.getBonus()
                    + " 合计=" + e.calculateSalary());
        }

        System.out.println("---- 按工资升序排序 ----");
        list.sort(java.util.Comparator.comparingDouble(Employee::calculateSalary));
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getName() + " " + list.get(i).calculateSalary());
        }
    }
}
```

- [ ] **Step 5: 编译项目源码与探针**

```bash
javac -encoding UTF-8 -d /tmp/esys-probe/out \
  employee-sys/src/tang/Gender.java \
  employee-sys/src/tang/Employee.java \
  employee-sys/src/tang/FullTimeEmployee.java \
  employee-sys/src/tang/SalesEmployee.java \
  employee-sys/src/tang/InternEmployee.java \
  /tmp/esys-probe/tang/SalaryProbe.java
```

预期：**没有任何输出**，退出码 0。

- [ ] **Step 6: 运行探针，验证多态生效**

```bash
java -Dstdout.encoding=UTF-8 -cp /tmp/esys-probe/out tang.SalaryProbe
```

预期输出（金额务必逐项核对；以下空格数是从实际运行结果复制的，不要凭感觉数）：

```
E001     张三     男    30   研发部      正式工         9600.00
  -> 职位=正式工 基本工资=8000.0 奖金=1600.0 合计=9600.0
E002     李四     女    28   销售部      销售          5500.00
  -> 职位=销售 基本工资=3000.0 奖金=2500.0 合计=5500.0
E003     王五     男    22   研发部      实习生         3000.00
  -> 职位=实习生 基本工资=3000.0 奖金=0.0 合计=3000.0
---- 按工资升序排序 ----
王五 3000.0
李四 5500.0
张三 9600.0
```

**这一步是整个计划里最关键的验证**：三行数据走的是**同一个** `calculateSalary()` 调用（父类里那行 `getBaseSalary() + getBonus()`），却算出 9600 / 5500 / 3000 三个不同的结果。这就是多态——界面层不需要写任何 `if (是正式工) ... else if (是销售) ...`。

若三行结果相同，说明子类漏写了 `@Override` 或方法名拼错，导致回退到父类实现——但父类是抽象方法，若真没实现会编译失败，所以更可能是 `getBonus()` 里算错。

- [ ] **Step 7: 删除探针编译产物**

```bash
rm -rf /tmp/esys-probe/out
```

探针源码 `/tmp/esys-probe/tang/SalaryProbe.java` 可以保留，后续任务还会用到；`out` 目录每次重新编译覆盖即可，删不删都行。

- [ ] **Step 8: 提交**

```bash
git add employee-sys/src/tang/FullTimeEmployee.java \
        employee-sys/src/tang/SalesEmployee.java \
        employee-sys/src/tang/InternEmployee.java
git commit -m "feat(employee-sys): 添加正式工/销售/实习生三个子类"
```

---

## Task 3: 员工服务单例——容器与查询

**Files:**
- Create: `employee-sys/src/tang/EmployeeService.java`（本任务只写前半部分）
- Create（临时，项目外）: `/tmp/esys-probe/tang/ServiceProbe1.java`

**Interfaces:**
- Consumes: `Employee`（Task 1）、三个子类（Task 2）
- Produces:
  - `static EmployeeService getInstance()`
  - `boolean add(Employee e)`
  - `Employee findById(String id)`
  - `ArrayList<Employee> findAll()`
  - `int count()`

---

- [ ] **Step 1: 创建 `EmployeeService.java`（第一阶段）**

写入 `employee-sys/src/tang/EmployeeService.java`：

```java
package tang;

import java.util.ArrayList;

/**
 * 员工业务类：整个系统里唯一的数据容器。
 * <p>
 * 为什么用单例：全系统只应该有一份员工数据。如果界面层能随手
 * new EmployeeService()，就可能出现两个互不相干的数据容器——
 * 在 A 里添加的员工，在 B 里查不到，而且这种 bug 极难排查。
 * 私有构造器 + 静态方法把「随手 new」这条路堵死。
 * <p>
 * 为什么用饿汉式而不是懒汉式：饿汉式在类加载时就创建好实例，
 * 写法更短，而且天然没有多线程隐患（懒汉式需要额外判断）。
 * 本项目里这个唯一实例无论如何都会被用到，没有「创建开销大到
 * 值得延迟」的理由，所以饿汉式是更合适的选择。
 *
 * @author tang
 * @version 1.0
 */
public class EmployeeService {

    /**
     * 唯一实例。
     * static 表示属于类而不是对象；final 表示这个引用一旦赋值就不能再指向别的对象。
     * 类加载时 JVM 执行这一行，实例就创建好了，所以叫「饿汉」。
     */
    private static final EmployeeService INSTANCE = new EmployeeService();

    /**
     * 私有构造器。
     * 构造器一旦被声明为 private，外部就无法 new EmployeeService()。
     * 这是单例模式的关键一步——注意光有 INSTANCE 字段是不够的，
     * 没有私有构造器的话外部照样能 new 出第二个实例。
     */
    private EmployeeService() {
    }

    /**
     * 获取全局唯一实例。
     *
     * @return 唯一的 EmployeeService 对象
     */
    public static EmployeeService getInstance() {
        return INSTANCE;
    }

    /**
     * 员工数据容器。
     * 泛型写 Employee（父类），实际存的是各种子类对象。
     * 注意这里没有用 static——它是「实例的」数据，每个实例各有一份；
     * 单例保证全局只有一个实例，所以实际上也只有这一份数据。
     */
    private final ArrayList<Employee> employees = new ArrayList<>();

    /**
     * 添加员工。
     * <p>
     * 工号是唯一标识，重复的工号会导致查找、修改、删除全部产生歧义，
     * 所以添加前先查重。这里用返回值表达失败，而不是抛异常——
     * 按本项目的约束不使用异常处理。
     *
     * @param e 待添加的员工对象
     * @return 添加成功返回 true；工号已存在返回 false
     */
    public boolean add(Employee e) {
        // 复用 findById 做查重，避免把遍历逻辑写两遍
        if (findById(e.getId()) != null) {
            return false;
        }
        employees.add(e);
        return true;
    }

    /**
     * 按工号查找员工。
     *
     * @param id 工号
     * @return 找到则返回该员工对象；找不到返回 null
     */
    public Employee findById(String id) {
        // 不用 for-each、不用 Iterator——本项目约束只用最基础的索引遍历
        for (int i = 0; i < employees.size(); i++) {
            // 字符串比较必须用 equals，不能用 ==。
            // == 比的是两个引用是否指向同一个对象，
            // equals 比的才是内容是否相同。
            if (employees.get(i).getId().equals(id)) {
                return employees.get(i);
            }
        }
        return null;
    }

    /**
     * 取得全部员工。
     * <p>
     * 注意返回的是「副本」而不是内部列表本身。
     * ArrayList 是引用类型，如果直接 return employees，
     * 等于把内部容器的钥匙交出去——调用方一个 remove 就能绕过本类的
     * 所有校验，直接改掉真实数据。复制一份出去，随便改都影响不到内部。
     * 代价是多一次复制，在这个数据量下可以忽略。
     *
     * @return 包含当前全部员工的新列表（修改它不影响内部数据）
     */
    public ArrayList<Employee> findAll() {
        return new ArrayList<>(employees);
    }

    /**
     * 当前员工总数。
     *
     * @return 员工人数
     */
    public int count() {
        return employees.size();
    }
}
```

- [ ] **Step 2: 编译验证**

```bash
javac -encoding UTF-8 -d employee-sys/out \
  employee-sys/src/tang/Gender.java \
  employee-sys/src/tang/Employee.java \
  employee-sys/src/tang/FullTimeEmployee.java \
  employee-sys/src/tang/SalesEmployee.java \
  employee-sys/src/tang/InternEmployee.java \
  employee-sys/src/tang/EmployeeService.java
```

预期：无输出，退出码 0。

- [ ] **Step 3: 写探针 `ServiceProbe1.java`**

写入 `/tmp/esys-probe/tang/ServiceProbe1.java`：

```java
package tang;

import java.util.ArrayList;

/**
 * 临时探针：验证单例、增删查与查重。
 */
public class ServiceProbe1 {

    public static void main(String[] args) {
        // 两次 getInstance() 拿到的必须是同一个对象
        EmployeeService s1 = EmployeeService.getInstance();
        EmployeeService s2 = EmployeeService.getInstance();
        System.out.println("两次取到同一实例？ " + (s1 == s2));

        System.out.println("初始人数：" + s1.count());

        boolean r1 = s1.add(new FullTimeEmployee("E001", "张三", Gender.MALE, 30, "研发部", 8000, 0.2));
        boolean r2 = s1.add(new SalesEmployee("E002", "李四", Gender.FEMALE, 28, "销售部", 3000, 50000, 0.05));
        boolean r3 = s1.add(new InternEmployee("E003", "王五", Gender.MALE, 22, "研发部", 150, 20));
        System.out.println("三次添加：" + r1 + " " + r2 + " " + r3);
        System.out.println("添加后人数：" + s1.count());

        // 重复工号必须被拒绝
        boolean dup = s1.add(new FullTimeEmployee("E001", "冒牌货", Gender.MALE, 99, "行政部", 1, 0));
        System.out.println("重复工号添加结果（应为 false）：" + dup);
        System.out.println("人数未变（应为 3）：" + s1.count());

        // 按工号查找
        Employee found = s1.findById("E002");
        System.out.println("查 E002：" + (found == null ? "没找到" : found.getName()));
        System.out.println("查 E999：" + (s1.findById("E999") == null ? "没找到（正确）" : "不该找到"));

        // findAll 返回副本：改副本不能影响内部数据
        ArrayList<Employee> copy = s1.findAll();
        copy.remove(0);
        System.out.println("副本删掉 1 个后，内部人数仍为（应为 3）：" + s1.count());
    }
}
```

- [ ] **Step 4: 运行探针**

```bash
javac -encoding UTF-8 -d /tmp/esys-probe/out \
  employee-sys/src/tang/*.java \
  /tmp/esys-probe/tang/ServiceProbe1.java \
  && java -Dstdout.encoding=UTF-8 -cp /tmp/esys-probe/out tang.ServiceProbe1
```

预期输出：

```
两次取到同一实例？ true
初始人数：0
三次添加：true true true
添加后人数：3
重复工号添加结果（应为 false）：false
人数未变（应为 3）：3
查 E002：李四
查 E999：没找到（正确）
副本删掉 1 个后，内部人数仍为（应为 3）：3
```

最后一行是 `findAll()` 返回副本的证明——如果直接返回内部列表，这里会打印 2。

- [ ] **Step 5: 提交**

```bash
git add employee-sys/src/tang/EmployeeService.java
git commit -m "feat(employee-sys): 添加员工服务单例与增查基础方法"
```

---

## Task 4: 员工服务——修改、删除、统计与排序

**Files:**
- Modify: `employee-sys/src/tang/EmployeeService.java`（在 `count()` 方法后追加）
- Create（临时，项目外）: `/tmp/esys-probe/tang/ServiceProbe2.java`

**Interfaces:**
- Consumes: Task 3 的 `EmployeeService`（单例、`add`、`findById`、`findAll`、`count`）
- Produces:
  - `boolean update(String id, Employee newEmp)`
  - `boolean delete(String id)`
  - `ArrayList<Employee> sortBySalary(boolean asc)`
  - `double averageSalary()`
  - `double maxSalary()`
  - `double minSalary()`
  - `void initSampleData()`

---

- [ ] **Step 1: 追加 `update` 与 `delete`**

在 `EmployeeService.java` 的 `count()` 方法之后、类的结束大括号之前，插入：

```java
    /**
     * 按工号修改员工——整体替换。
     * <p>
     * 为什么是「整体替换」而不是逐字段修改：员工的工资相关字段
     * （月薪 / 提成率 / 日薪）属于子类独有。要逐个修改就得先 instanceof
     * 判断真实类型、再向下转型，而这正是 day06 那个 ClassCastException
     * 演示想说明的麻烦事。改成「按原工号整体换一个新对象」，
     * 就完全绕开了类型判断，界面层也能用同一套输入流程同时服务
     * 「添加」和「修改」两种场景。
     * <p>
     * 调用方负责保证 newEmp 的工号就是 id（界面层会沿用原工号）。
     *
     * @param id     要被替换的员工工号
     * @param newEmp 新的员工对象
     * @return 替换成功返回 true；工号不存在返回 false
     */
    public boolean update(String id, Employee newEmp) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(id)) {
                // 用 set 替换指定位置的对象，列表长度不变、顺序不变
                employees.set(i, newEmp);
                return true;
            }
        }
        return false;
    }

    /**
     * 按工号删除员工。
     *
     * @param id 工号
     * @return 删除成功返回 true；工号不存在返回 false
     */
    public boolean delete(String id) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(id)) {
                // remove(int index) 按下标删除。
                // 注意 ArrayList 还有 remove(Object) 重载——
                // 传入 int 走的是按下标删，传入对象走的是按内容删，别搞混。
                employees.remove(i);
                // 删完必须立刻 return。若继续循环，后面的元素会前移，
                // 而且 i 已经指向下一个元素了，会导致漏查。
                return true;
            }
        }
        return false;
    }
```

- [ ] **Step 2: 追加排序与统计方法**

紧接上一步的 `delete` 之后插入：

```java
    /**
     * 按工资排序，返回排好序的副本。
     * <p>
     * 不直接排内部列表，理由和 findAll() 一样：排序会改变元素顺序，
     * 属于对内部数据结构的改动，不应该由外部触发。排副本则外部
     * 想怎么排都行，真实数据始终保持添加时的顺序。
     *
     * @param asc true 为升序（低到高），false 为降序
     * @return 排序后的新列表
     */
    public ArrayList<Employee> sortBySalary(boolean asc) {
        ArrayList<Employee> copy = new ArrayList<>(employees);

        if (asc) {
            // 写法一：方法引用 + Comparator.comparingDouble
            // Employee::calculateSalary 是「特定类型的方法引用」，
            // 等价于 e -> e.calculateSalary()，比 Lambda 更简洁。
            copy.sort(java.util.Comparator.comparingDouble(Employee::calculateSalary));
        } else {
            // 写法二：Lambda。降序就是把两个参数比较的顺序调过来，
            // 相当于把「e1 减 e2」换成「e2 减 e1」。
            // 用 Double.compare 而不是直接相减，是为了避免浮点误差
            // 和溢出问题，这也是标准写法。
            copy.sort((e1, e2) -> Double.compare(e2.calculateSalary(), e1.calculateSalary()));
        }

        return copy;
    }

    /**
     * 计算全部员工的平均工资。
     * <p>
     * 人数为 0 时直接返回 0，不抛异常——按本项目约束不使用异常处理，
     * 而且「没有员工时平均工资为 0」本身也是个合理答案。
     *
     * @return 平均工资，单位：元；无员工时返回 0
     */
    public double averageSalary() {
        if (employees.size() == 0) {
            return 0;
        }
        double sum = 0;
        for (int i = 0; i < employees.size(); i++) {
            // 这里又一次体现多态：同一个 calculateSalary()，
            // 每种员工按自己的规则算
            sum = sum + employees.get(i).calculateSalary();
        }
        return sum / employees.size();
    }

    /**
     * 最高工资。
     *
     * @return 最高工资；无员工时返回 0
     */
    public double maxSalary() {
        if (employees.size() == 0) {
            return 0;
        }
        double max = employees.get(0).calculateSalary();
        for (int i = 1; i < employees.size(); i++) {
            double s = employees.get(i).calculateSalary();
            if (s > max) {
                max = s;
            }
        }
        return max;
    }

    /**
     * 最低工资。
     *
     * @return 最低工资；无员工时返回 0
     */
    public double minSalary() {
        if (employees.size() == 0) {
            return 0;
        }
        double min = employees.get(0).calculateSalary();
        for (int i = 1; i < employees.size(); i++) {
            double s = employees.get(i).calculateSalary();
            if (s < min) {
                min = s;
            }
        }
        return min;
    }

    /**
     * 预置几条示例数据，方便程序一启动就能看到效果。
     * <p>
     * 用 add() 而不是直接往 employees 里塞，是为了复用查重逻辑；
     * 重复调用本方法时，重复的工号会被 add() 自动拒绝。
     * 返回值这里故意不接收——示例数据加不进去不是错误，
     * 不需要向用户报告。
     */
    public void initSampleData() {
        add(new FullTimeEmployee("E001", "张三", Gender.MALE, 30, "研发部", 8000, 0.2));
        add(new SalesEmployee("E002", "李四", Gender.FEMALE, 28, "销售部", 3000, 50000, 0.05));
        add(new InternEmployee("E003", "王五", Gender.MALE, 22, "研发部", 150, 20));
    }
```

- [ ] **Step 3: 编译验证**

```bash
javac -encoding UTF-8 -d employee-sys/out employee-sys/src/tang/*.java
```

预期：无输出，退出码 0。

- [ ] **Step 4: 写探针 `ServiceProbe2.java`**

写入 `/tmp/esys-probe/tang/ServiceProbe2.java`：

```java
package tang;

import java.util.ArrayList;

/**
 * 临时探针：验证修改、删除、排序、统计。
 */
public class ServiceProbe2 {

    static void printAll(EmployeeService s) {
        ArrayList<Employee> list = s.findAll();
        for (int i = 0; i < list.size(); i++) {
            System.out.println("   " + list.get(i));
        }
    }

    public static void main(String[] args) {
        EmployeeService s = EmployeeService.getInstance();
        s.initSampleData();
        System.out.println("预置后人数（应为 3）：" + s.count());

        System.out.println("---- 统计 ----");
        System.out.printf("平均 %.2f 最高 %.2f 最低 %.2f%n", s.averageSalary(), s.maxSalary(), s.minSalary());
        // 期望：平均 (9600+5500+3000)/3 = 6033.33，最高 9600，最低 3000

        System.out.println("---- 升序 ----");
        ArrayList<Employee> asc = s.sortBySalary(true);
        for (int i = 0; i < asc.size(); i++) {
            System.out.println("   " + asc.get(i).getName() + " " + asc.get(i).calculateSalary());
        }

        System.out.println("---- 降序 ----");
        ArrayList<Employee> desc = s.sortBySalary(false);
        for (int i = 0; i < desc.size(); i++) {
            System.out.println("   " + desc.get(i).getName() + " " + desc.get(i).calculateSalary());
        }

        System.out.println("---- 修改 E003 实习生 -> 正式工 ----");
        boolean u = s.update("E003", new FullTimeEmployee("E003", "王五", Gender.MALE, 23, "研发部", 9000, 0.1));
        System.out.println("修改结果：" + u);
        System.out.println("修改后 E003 的职位与工资：" + s.findById("E003").getPosition()
                + " / " + s.findById("E003").calculateSalary());
        System.out.println("人数仍为 3：" + s.count());

        System.out.println("修改不存在的工号（应为 false）：" + s.update("E999", null));

        System.out.println("---- 删除 E002 ----");
        System.out.println("删除结果：" + s.delete("E002"));
        System.out.println("删除后人数（应为 2）：" + s.count());
        System.out.println("再查 E002（应为没找到）：" + (s.findById("E002") == null ? "没找到" : "还在"));
        System.out.println("删除不存在的工号（应为 false）：" + s.delete("E999"));

        System.out.println("---- 剩余全部 ----");
        printAll(s);
    }
}
```

- [ ] **Step 5: 运行探针**

```bash
javac -encoding UTF-8 -d /tmp/esys-probe/out \
  employee-sys/src/tang/*.java \
  /tmp/esys-probe/tang/ServiceProbe2.java \
  && java -Dstdout.encoding=UTF-8 -cp /tmp/esys-probe/out tang.ServiceProbe2
```

预期输出：

```
预置后人数（应为 3）：3
---- 统计 ----
平均 6033.33 最高 9600.00 最低 3000.00
---- 升序 ----
   王五 3000.0
   李四 5500.0
   张三 9600.0
---- 降序 ----
   张三 9600.0
   李四 5500.0
   王五 3000.0
---- 修改 E003 实习生 -> 正式工 ----
修改结果：true
修改后 E003 的职位与工资：正式工 / 9900.0
人数仍为 3：3
修改不存在的工号（应为 false）：false
---- 删除 E002 ----
删除结果：true
删除后人数（应为 2）：2
再查 E002（应为没找到）：没找到
删除不存在的工号（应为 false）：false
---- 剩余全部 ----
   E001     张三     男    30   研发部      正式工         9600.00
   E003     王五     男    23   研发部      正式工         9900.00
```

**重点核对两处**：修改 E003 后职位从「实习生」变成「正式工」、工资从 3000 变成 9900（9000 + 9000×0.1）——这证明「整体替换」策略生效了，一个实习生对象被换成了正式工对象，而界面层完全不需要做类型判断。

- [ ] **Step 6: 提交**

```bash
git add employee-sys/src/tang/EmployeeService.java
git commit -m "feat(employee-sys): 添加修改/删除/排序/统计与示例数据"
```

---

## Task 5: 控制台菜单骨架——添加、列表、退出

**Files:**
- Create: `employee-sys/src/tang/EmployeeSystem.java`（本任务只写骨架 + 3 个分支，其余分支给提示占位）

**Interfaces:**
- Consumes: `EmployeeService`（Task 3 + 4）的全部公开方法；`Employee` 与三个子类
- Produces:
  - `public static void main(String[] args)`
  - `private static void printMenu()`
  - `private static void printTableHead()`
  - `private static void printList(ArrayList<Employee> list)`
  - `private static Employee inputEmployee(Scanner sc, String id)`
  - `private static void addEmployee(Scanner sc)`
  - `private static void listAll()`

**本任务先写 4 个菜单分支**：1 添加、2 列表、0 退出，以及 `default`。分支 3–7 暂时只打印「该功能将在下一步实现」，保证程序从这一步起就能编译运行。

---

- [ ] **Step 1: 创建 `EmployeeSystem.java`**

写入 `employee-sys/src/tang/EmployeeSystem.java`：

```java
package tang;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 人事管理系统入口：负责菜单显示、读取键盘输入、打印结果。
 * <p>
 * 分层说明：本类是「界面层」，只做输入输出，不含任何业务规则；
 * 所有数据操作都委托给 EmployeeService。这样将来换成图形界面，
 * 只需要重写本类，业务层一行都不用改。
 *
 * @author tang
 * @version 1.0
 */
public class EmployeeSystem {

    /**
     * 业务层实例。
     * 通过 getInstance() 取得全局唯一实例，而不是 new——
     * EmployeeService 的构造器是私有的，这里也 new 不了。
     */
    private static final EmployeeService service = EmployeeService.getInstance();

    public static void main(String[] args) {
        // 预置示例数据，这样一启动就能看到效果，不用先手动录入
        service.initSampleData();

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            printMenu();
            System.out.print("请输入操作编号：");

            // 【重要】nextInt() 只读走数字，不读走你按下的回车。
            // 如果不处理，紧接着调 nextLine() 会立刻读到一个空字符串，
            // 导致「姓名」等输入被跳过。补一句 nextLine() 把回车吃掉即可。
            // 这是初学者最高频的 bug。
            choice = sc.nextInt();
            sc.nextLine();

            // 【已知缺陷】若在这里输入非数字（如 abc），nextInt() 会抛出
            // InputMismatchException，程序直接中断退出。
            // 本项目按约束不使用异常处理，所以此处不做防护——
            // 要修它得先学异常处理章节。请在菜单处老老实实输入数字。

            switch (choice) {
                case 1:
                    addEmployee(sc);
                    break;
                case 2:
                    listAll();
                    break;
                case 3:
                    System.out.println("【提示】该功能将在下一步实现。");
                    break;
                case 4:
                    System.out.println("【提示】该功能将在下一步实现。");
                    break;
                case 5:
                    System.out.println("【提示】该功能将在下一步实现。");
                    break;
                case 6:
                    System.out.println("【提示】该功能将在下一步实现。");
                    break;
                case 7:
                    System.out.println("【提示】该功能将在下一步实现。");
                    break;
                case 0:
                    System.out.println("已退出人事管理系统，再见！");
                    break;
                default:
                    // 用 default 兜住所有非法编号，这就是不用异常时的兜底方式
                    System.out.println("无效的操作编号，请输入 0-7 之间的数字。");
                    break;
            }

            System.out.println();

        } while (choice != 0);
        // 选 0 时 choice 变成 0，条件为 false，循环结束，main 方法返回，程序退出
    }

    /**
     * 打印主菜单。
     */
    private static void printMenu() {
        System.out.println("========= 人事管理系统 =========");
        System.out.println("  1. 添加员工");
        System.out.println("  2. 查询所有员工");
        System.out.println("  3. 按工号查询");
        System.out.println("  4. 修改员工");
        System.out.println("  5. 删除员工");
        System.out.println("  6. 按工资排序");
        System.out.println("  7. 统计报表");
        System.out.println("  0. 退出系统");
        System.out.println("================================");
    }

    /**
     * 打印工资表的表头。
     * 各列的宽度必须与 Employee.toString() 里的格式串保持一致，
     * 否则表头和数据会对不上。
     */
    private static void printTableHead() {
        System.out.printf("%-8s %-6s %-4s %-4s %-8s %-8s %10s%n",
                "工号", "姓名", "性别", "年龄", "部门", "职位", "工资(元)");
    }

    /**
     * 逐行打印员工列表。
     *
     * @param list 要打印的列表
     */
    private static void printList(ArrayList<Employee> list) {
        if (list.size() == 0) {
            System.out.println("暂无员工数据。");
            return;
        }
        printTableHead();
        for (int i = 0; i < list.size(); i++) {
            // 直接打印对象，会自动调用它的 toString()——
            // 而 toString() 是父类写的，里面用多态取得职位和工资
            System.out.println(list.get(i));
        }
    }

    /**
     * 采集一名员工的完整信息，并创建对应的子类对象。
     * <p>
     * 「添加」和「修改」需要采集的字段完全一样，只差一个工号
     * （修改时沿用原工号且不可改），所以抽成同一个方法复用。
     * <p>
     * 返回类型声明为父类 Employee，实际返回的是子类对象——
     * 这就是向上转型。调用方拿到后不需要知道具体是哪个子类。
     *
     * @param sc 输入流
     * @param id 工号（添加时由调用方生成，修改时沿用原工号）
     * @return 创建好的员工对象；职位类型非法时返回 null
     */
    private static Employee inputEmployee(Scanner sc, String id) {
        System.out.println("请选择职位类型：1-正式工  2-销售  3-实习生");
        System.out.print("职位类型：");
        int type = sc.nextInt();
        sc.nextLine();   // 吃掉回车

        System.out.print("姓名：");
        String name = sc.nextLine();

        System.out.print("性别（1-男  2-女）：");
        int g = sc.nextInt();
        sc.nextLine();   // 吃掉回车
        // 用三目运算符把数字转成枚举。输入 2 以外的任何值都按「男」处理，
        // 属于简化处理——本项目不做严格的输入校验。
        Gender gender = (g == 2) ? Gender.FEMALE : Gender.MALE;

        System.out.print("年龄：");
        int age = sc.nextInt();
        sc.nextLine();   // 吃掉回车

        System.out.print("部门：");
        String dept = sc.nextLine();

        switch (type) {
            case 1:
                System.out.print("月薪（元）：");
                double monthlySalary = sc.nextDouble();
                sc.nextLine();
                System.out.print("绩效系数（0.2 表示奖金为月薪的 20%）：");
                double performanceFactor = sc.nextDouble();
                sc.nextLine();
                return new FullTimeEmployee(id, name, gender, age, dept,
                        monthlySalary, performanceFactor);

            case 2:
                System.out.print("底薪（元）：");
                double baseSalary = sc.nextDouble();
                sc.nextLine();
                System.out.print("本月销售额（元）：");
                double salesAmount = sc.nextDouble();
                sc.nextLine();
                System.out.print("提成率（0.05 表示 5%）：");
                double commissionRate = sc.nextDouble();
                sc.nextLine();
                return new SalesEmployee(id, name, gender, age, dept,
                        baseSalary, salesAmount, commissionRate);

            case 3:
                System.out.print("日薪（元）：");
                double dailyWage = sc.nextDouble();
                sc.nextLine();
                System.out.print("本月出勤天数：");
                int attendDays = sc.nextInt();
                sc.nextLine();
                return new InternEmployee(id, name, gender, age, dept,
                        dailyWage, attendDays);

            default:
                System.out.println("职位类型无效，操作已取消。");
                return null;
        }
    }

    /**
     * 菜单 1：添加员工。
     *
     * @param sc 输入流
     */
    private static void addEmployee(Scanner sc) {
        System.out.print("请输入工号：");
        String id = sc.nextLine();

        // 先查重。add() 内部也会查一次，但那时信息已经录完了，
        // 提前查可以避免用户白填一堆内容。
        if (service.findById(id) != null) {
            System.out.println("工号 " + id + " 已存在，添加失败。");
            return;
        }

        Employee e = inputEmployee(sc, id);
        if (e == null) {
            return;
        }

        if (service.add(e)) {
            System.out.println("添加成功！" + e.getName() + " 的工资为 "
                    + e.calculateSalary() + " 元。");
        } else {
            System.out.println("添加失败：工号已存在。");
        }
    }

    /**
     * 菜单 2：查询所有员工。
     */
    private static void listAll() {
        System.out.println("---- 全部员工，共 " + service.count() + " 人 ----");
        printList(service.findAll());
    }
}
```

- [ ] **Step 2: 编译验证**

```bash
javac -encoding UTF-8 -d employee-sys/out employee-sys/src/tang/*.java
```

预期：无输出，退出码 0。

- [ ] **Step 3: 管道验证——添加一名正式工并查看列表**

```bash
printf '1\nE100\n1\n赵六\n1\n35\n财务部\n12000\n0.15\n2\n0\n' | \
  java -Dstdout.encoding=UTF-8 -cp employee-sys/out tang.EmployeeSystem
```

输入的按键序列含义：`1`=菜单选添加 → `E100`=工号 → `1`=正式工 → `赵六`=姓名 → `1`=男 → `35`=年龄 → `财务部`=部门 → `12000`=月薪 → `0.15`=绩效系数 → `2`=菜单选列表 → `0`=退出。

预期输出（关键部分；因提示语用 `print` 不换行，管道测试时它们会连成一行，这是正常现象）：

```
请输入操作编号：请输入工号：请选择职位类型：1-正式工  2-销售  3-实习生
职位类型：姓名：性别（1-男  2-女）：年龄：部门：月薪（元）：绩效系数（0.2 表示奖金为月薪的 20%）：添加成功！赵六 的工资为 13800.0 元。

请输入操作编号：---- 全部员工，共 4 人 ----
工号       姓名     性别   年龄   部门       职位            工资(元)
E001     张三     男    30   研发部      正式工         9600.00
E002     李四     女    28   销售部      销售          5500.00
E003     王五     男    22   研发部      实习生         3000.00
E100     赵六     男    35   财务部      正式工        13800.00

请输入操作编号：已退出人事管理系统，再见！
```

**注意表头和数据行看起来没对齐**：`E001` 后面跟了 5 个空格，但「张三」后面也是 5 个空格，视觉上却错开了。原因见 `Employee.toString()` 的注释——`%-8s` 按字符个数补空格，而汉字占两个字符宽。这是已知限制，不是代码写错了。

**重点核对**：共 4 人（3 条预置 + 1 条新加），赵六工资 13800 = 12000 + 12000×0.15。

**关于提示文字挤在一起**：`System.out.print("请输入操作编号：")` 用的是 `print` 不带换行，而管道输入时用户敲的字符不会回显，所以提示语和下一句提示会连成一行。这是管道测试的正常现象，不是 bug——你在 IDEA 里手动运行时是分行输入，看起来是正常的。

- [ ] **Step 4: 管道验证——重复工号被拒绝**

```bash
printf '1\nE001\n0\n' | java -Dstdout.encoding=UTF-8 -cp employee-sys/out tang.EmployeeSystem
```

预期输出包含：

```
工号 E001 已存在，添加失败。
```

且没有进入「请选择职位类型」的提问——证明提前查重生效，用户不用白填信息。

- [ ] **Step 5: 管道验证——非法编号不崩溃**

```bash
printf '9\n0\n' | java -Dstdout.encoding=UTF-8 -cp employee-sys/out tang.EmployeeSystem
```

预期输出包含：

```
无效的操作编号，请输入 0-7 之间的数字。
```

然后回到菜单，读入 `0` 后正常退出。

- [ ] **Step 6: 提交**

```bash
git add employee-sys/src/tang/EmployeeSystem.java
git commit -m "feat(employee-sys): 添加控制台菜单骨架与添加/列表功能"
```

---

## Task 6: 补全剩余菜单分支

**Files:**
- Modify: `employee-sys/src/tang/EmployeeSystem.java`（替换 case 3–7 的占位提示，并追加 5 个方法）

**Interfaces:**
- Consumes: Task 5 的 `EmployeeSystem` 全部私有方法（`printMenu`、`printTableHead`、`printList`、`inputEmployee`）；`EmployeeService` 的 `findById` / `update` / `delete` / `sortBySalary` / `averageSalary` / `maxSalary` / `minSalary` / `count`
- Produces: `findByNo(Scanner)`、`updateEmployee(Scanner)`、`deleteEmployee(Scanner)`、`sortBySalary(Scanner)`、`statistics()`

---

- [ ] **Step 1: 替换 `switch` 中 case 3–7 的占位提示**

在 `EmployeeSystem.java` 中，把这 5 个 case：

```java
                case 3:
                    System.out.println("【提示】该功能将在下一步实现。");
                    break;
                case 4:
                    System.out.println("【提示】该功能将在下一步实现。");
                    break;
                case 5:
                    System.out.println("【提示】该功能将在下一步实现。");
                    break;
                case 6:
                    System.out.println("【提示】该功能将在下一步实现。");
                    break;
                case 7:
                    System.out.println("【提示】该功能将在下一步实现。");
                    break;
```

替换为：

```java
                case 3:
                    findByNo(sc);
                    break;
                case 4:
                    updateEmployee(sc);
                    break;
                case 5:
                    deleteEmployee(sc);
                    break;
                case 6:
                    sortBySalary(sc);
                    break;
                case 7:
                    statistics();
                    break;
```

- [ ] **Step 2: 追加 `findByNo` 与 `updateEmployee`**

在 `EmployeeSystem.java` 的 `listAll()` 方法之后、类的结束大括号之前，插入：

```java
    /**
     * 菜单 3：按工号查询。
     *
     * @param sc 输入流
     */
    private static void findByNo(Scanner sc) {
        System.out.print("请输入要查询的工号：");
        String id = sc.nextLine();

        Employee e = service.findById(id);
        if (e == null) {
            // findById 用返回 null 表示「不存在」，调用方必须判空，
            // 否则下一行 e.getName() 就会抛 NullPointerException
            System.out.println("工号 " + id + " 不存在。");
            return;
        }

        System.out.println("---- 查询结果 ----");
        printTableHead();
        System.out.println(e);
    }

    /**
     * 菜单 4：修改员工。
     * <p>
     * 先按工号确认员工存在，再用同一套 inputEmployee 流程重新采集全部信息，
     * 最后整体替换。工号沿用原值，不允许在修改时改工号——
     * 否则工号就不再是稳定的唯一标识了。
     *
     * @param sc 输入流
     */
    private static void updateEmployee(Scanner sc) {
        System.out.print("请输入要修改的员工工号：");
        String id = sc.nextLine();

        Employee old = service.findById(id);
        if (old == null) {
            System.out.println("工号 " + id + " 不存在，修改失败。");
            return;
        }

        System.out.println("当前信息：");
        printTableHead();
        System.out.println(old);

        System.out.println("请输入新的信息（工号保持不变）：");
        Employee newEmp = inputEmployee(sc, id);
        if (newEmp == null) {
            System.out.println("修改已取消。");
            return;
        }

        if (service.update(id, newEmp)) {
            System.out.println("修改成功！新工资为 " + newEmp.calculateSalary() + " 元。");
        } else {
            System.out.println("修改失败。");
        }
    }
```

- [ ] **Step 3: 追加 `deleteEmployee`、`sortBySalary` 与 `statistics`**

紧接上一步的 `updateEmployee` 之后插入：

```java
    /**
     * 菜单 5：删除员工。
     * <p>
     * 删除是不可撤销的操作，所以先显示要删的人是谁、再要求确认，
     * 避免手滑输错工号就删掉了别人的数据。
     *
     * @param sc 输入流
     */
    private static void deleteEmployee(Scanner sc) {
        System.out.print("请输入要删除的员工工号：");
        String id = sc.nextLine();

        Employee e = service.findById(id);
        if (e == null) {
            System.out.println("工号 " + id + " 不存在，删除失败。");
            return;
        }

        System.out.println("即将删除：" + e.getName() + "（" + e.getPosition() + "，"
                + e.getDept() + "）");
        System.out.print("确认删除？输入 y 确认，其他任意键取消：");
        String confirm = sc.nextLine();

        // 用 equals 比较字符串内容。equalsIgnoreCase 则忽略大小写，
        // 这样输入 Y 或 y 都算确认。
        if (confirm.equalsIgnoreCase("y")) {
            if (service.delete(id)) {
                System.out.println("已删除工号 " + id + " 的员工。");
            } else {
                System.out.println("删除失败。");
            }
        } else {
            System.out.println("已取消删除。");
        }
    }

    /**
     * 菜单 6：按工资排序。
     * <p>
     * 排序只影响显示顺序，不改动 service 里的真实数据——
     * sortBySalary 返回的是副本。
     *
     * @param sc 输入流
     */
    private static void sortBySalary(Scanner sc) {
        if (service.count() == 0) {
            System.out.println("暂无员工数据。");
            return;
        }

        System.out.print("请选择排序方式（1-升序  2-降序）：");
        int order = sc.nextInt();
        sc.nextLine();   // 吃掉回车

        boolean asc = (order != 2);   // 输入 1 或其他值都按升序处理

        System.out.println("---- 按工资" + (asc ? "升序" : "降序") + "排列 ----");
        printList(service.sortBySalary(asc));
    }

    /**
     * 菜单 7：统计报表。
     * <p>
     * 不需要读取输入，所以没有 Scanner 参数。
     */
    private static void statistics() {
        int n = service.count();
        if (n == 0) {
            System.out.println("暂无员工数据，无法统计。");
            return;
        }

        System.out.println("---- 统计报表 ----");
        System.out.println("员工总数：" + n + " 人");
        System.out.printf("平均工资：%.2f 元%n", service.averageSalary());
        System.out.printf("最高工资：%.2f 元%n", service.maxSalary());
        System.out.printf("最低工资：%.2f 元%n", service.minSalary());
    }
```

- [ ] **Step 4: 编译验证**

```bash
javac -encoding UTF-8 -d employee-sys/out employee-sys/src/tang/*.java
```

预期：无输出，退出码 0。

- [ ] **Step 5: 管道验证——按工号查询（含查不到的情况）**

```bash
printf '3\nE002\n3\nE999\n0\n' | java -Dstdout.encoding=UTF-8 -cp employee-sys/out tang.EmployeeSystem
```

预期输出包含：

```
---- 查询结果 ----
工号       姓名     性别   年龄   部门       职位            工资(元)
E002     李四     女    28   销售部      销售          5500.00
```

以及第二段：

```
工号 E999 不存在。
```

且程序继续回到菜单、正常退出，**没有崩溃**。

- [ ] **Step 6: 管道验证——删除（确认与取消两条路径）**

先测取消：

```bash
printf '5\nE003\nn\n2\n0\n' | java -Dstdout.encoding=UTF-8 -cp employee-sys/out tang.EmployeeSystem
```

预期包含 `已取消删除。`，且随后列表仍显示 3 人。

再测确认删除：

```bash
printf '5\nE003\ny\n2\n0\n' | java -Dstdout.encoding=UTF-8 -cp employee-sys/out tang.EmployeeSystem
```

预期包含 `已删除工号 E003 的员工。`，且随后列表显示「共 2 人」。

删除不存在的工号：

```bash
printf '5\nE999\n0\n' | java -Dstdout.encoding=UTF-8 -cp employee-sys/out tang.EmployeeSystem
```

预期包含 `工号 E999 不存在，删除失败。`

- [ ] **Step 7: 管道验证——排序与统计**

```bash
printf '6\n1\n6\n2\n7\n0\n' | java -Dstdout.encoding=UTF-8 -cp employee-sys/out tang.EmployeeSystem
```

预期：升序段为王五(3000) → 李四(5500) → 张三(9600)；降序段为张三 → 李四 → 王五。

统计段：

```
---- 统计报表 ----
员工总数：3 人
平均工资：6033.33 元
最高工资：9600.00 元
最低工资：3000.00 元
```

- [ ] **Step 8: 管道验证——修改员工，验证职位类型可以变**

```bash
printf '4\nE003\n1\n王五\n1\n23\n研发部\n9000\n0.1\n7\n0\n' | java -Dstdout.encoding=UTF-8 -cp employee-sys/out tang.EmployeeSystem
```

预期输出包含：

```
修改成功！新工资为 9900.0 元。
```

且随后统计段中「最高工资」变为 9900.00——证明实习生被成功替换成了正式工，且新工资参与了统计。这是「整体替换」策略的端到端证明。

- [ ] **Step 9: 提交**

```bash
git add employee-sys/src/tang/EmployeeSystem.java
git commit -m "feat(employee-sys): 补全查询/修改/删除/排序/统计菜单分支"
```

---

## 验收标准逐条对照

Task 6 完成后，逐条核对 spec 第七节：

| # | 验收标准 | 由哪一步验证 |
|---|---|---|
| 1 | 7 个文件在 `employee-sys/src/tang/`，`package tang;` | 各任务编译命令均以 `employee-sys/src/tang/*.java` 通配，文件数可 `ls` 核对 |
| 2 | 编译通过、能运行 `main` | Task 5 Step 2、Task 6 Step 4 |
| 3 | 能添加三类员工 | Task 5 Step 3（正式工）；Task 6 隐含（销售/实习生在 `inputEmployee` 的 case 2/3，由 Task 2 Step 6 与 Task 5 的列表验证覆盖） |
| 4 | 同一张表里三种职位工资互不相同 | Task 2 Step 6（探针）+ Task 5 Step 3（列表） |
| 5 | 按工号查询，查不到不崩溃 | Task 6 Step 5 |
| 6 | 修改后工号不变、字段更新 | Task 6 Step 8 |
| 7 | 删除后再查显示不存在 | Task 6 Step 6 |
| 8 | 重复工号被拒绝 | Task 5 Step 4 |
| 9 | 按工资升/降序正确 | Task 6 Step 7 |
| 10 | 统计总人数/平均/最高/最低 | Task 6 Step 7 |
| 11 | 输入 0 退出，非法编号不崩溃 | Task 5 Step 5 |
| 12 | 注释覆盖 | 代码已内嵌，编译后人工抽查 |

## 完成后建议手动跑一遍

管道测试只覆盖了脚本化的路径。Task 6 完成后，建议在 IDEA 里直接运行 `EmployeeSystem` 的 `main` 方法，手动把菜单每一项都点一遍，重点体验**交互式输入**的感觉——管道测试不会回显你敲的字符，实际使用时每行提示都是分行的，能更直观地看出菜单结构是否清晰。
