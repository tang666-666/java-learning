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

    /**
     * 底薪的 getter，刻意取名为 getBaseSalaryValue()。
     * <p>
     * 为什么不直接叫 getBaseSalary()：父类声明了
     * protected abstract double getBaseSalary()，本类必须实现它，
     * 于是这个名字已经被下面那份实现占住了。一个类里不允许有两个同签名的方法，
     * 所以 getter 若也叫 getBaseSalary()，两者只能合并成一个方法——
     * 它既当内部的算薪步骤，又成了对外的底薪读取接口；
     * 而为了能对外用就得声明成 public，等于把父类的 protected 放大成 public
     * （Java 允许放大可见性，这里编译不报错，所以很难被察觉）。
     * 后果是本该只是内部算薪步骤的方法，顺带变成真正的对外公开 API——
     * 用命名无意中扩大了接口。换个名字，这层重名就不存在了，
     * 字段名与父类方法名重名也不再产生歧义。
     *
     * @return 底薪，单位：元
     */
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
     * 方法名与本类的字段名 baseSalary 不冲突：Java 允许字段和方法重名，
     * 方法体里写 return baseSalary; 读的是字段而不是方法本身，不会递归调用。
     * <p>
     * 至于底薪的 getter 为什么不叫 getBaseSalary()，理由见上面
     * getBaseSalaryValue() 的说明——简单说，这个签名已经被本方法占住了。
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
