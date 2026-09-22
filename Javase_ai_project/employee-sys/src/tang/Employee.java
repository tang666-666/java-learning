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
