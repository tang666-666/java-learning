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
