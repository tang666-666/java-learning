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
