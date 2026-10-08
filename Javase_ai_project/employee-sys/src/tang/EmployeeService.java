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
        // 入口防御：本类内部一律用 getId().equals(...) 比较工号，
        // 若传进来的对象本身是 null、或它的工号为 null，那么之后每一次
        // add 和 findById 都会抛 NullPointerException（null.equals 直接崩）。
        // 而本项目约定：错误一律用返回值表达，不抛异常，所以在这里就挡掉。
        if (e == null || e.getId() == null) {
            return false;
        }
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
     * 所有校验，直接改掉真实数据。复制一份出去，随便增删都影响不到内部。
     * 代价是多一次复制，在这个数据量下可以忽略。
     * <p>
     * 但要说清楚这个「副本」到底保护了什么：new ArrayList(...) 是浅拷贝——
     * 只有列表结构（那串存放元素的格子）是新的，格子里的元素仍然指向
     * 内部那一批员工对象本身。所以对返回值 remove 一个元素动不到内部，
     * 而对返回值 get(0) 拿到的对象 setName(...)，改的却是真实数据，
     * 因为拿到的就是内部那个对象。根源在于 ArrayList 是引用类型：
     * 复制列表只复制了引用，没复制引用所指向的对象。
     * 要做到完全隔离，得遍历一遍逐个 new 出新对象再放进副本，
     * 本项目这个规模下没有这个必要——知道边界在哪就够了。
     *
     * @return 包含当前全部员工的新列表。列表结构独立，增删元素不影响内部；
     *         但元素是内部对象本身，修改元素字段会影响真实数据
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
     * 本方法会拒绝工号与 id 不一致的新对象——工号唯一由本类自己守护，
     * 不依赖调用方。
     *
     * @param id     要被替换的员工工号
     * @param newEmp 新的员工对象
     * @return 替换成功返回 true；工号不存在或新对象工号与 id 不一致返回 false
     */
    public boolean update(String id, Employee newEmp) {
        // 入口防御：update 把新对象直接 set 进容器，一旦放进去容器当场就被污染，
        // 且两种坏数据症状恰好相反：newEmp 本身为 null 时，averageSalary /
        // maxSalary / minSalary / sortBySalary 立刻抛 NullPointerException，
        // findById / delete / update 要遍历到它才崩；若只是工号为 null，则反过来——
        // 4 个统计排序方法只调 calculateSalary()、从不调 getId()，于是不抛异常，
        // 而是把它静默计入统计；真正的危害是它既没有可用的工号，
        // 就无法被 findById 定位、也无法被 delete 删除，等于一颗只进不出的坏数据。
        // 崩溃会立刻暴露，而这种查不到删不掉的坏数据却会被一直当成真数据用下去，
        // 所以必须在写进容器前挡掉。
        if (newEmp == null || newEmp.getId() == null) {
            return false;
        }
        // 工号唯一是本类的核心不变量，而 update 是唯一不查重的写入口。
        // 若放任新对象的工号与 id 不一致，容器里就会出现两条同工号记录，
        // 其中一条再也查不到、删不掉。所以在这里拒绝，而不是推给调用方。
        if (!newEmp.getId().equals(id)) {
            return false;
        }
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

    /**
     * 按工资排序，返回排好序的副本。
     * <p>
     * 不直接排内部列表，理由和 findAll() 一样：排序会改变元素顺序，
     * 属于对内部数据结构的改动，不应该由外部触发。排副本则外部
     * 想怎么排都行，真实数据始终保持添加时的顺序。
     * <p>
     * 和 findAll() 一样，这里也要说清边界：副本是浅拷贝，
     * 它保证的是**顺序**不受影响，不是元素不可改。排序只重排格子里的引用，
     * 内部的顺序因此原封不动；但外部顺着副本里的引用去改员工字段，
     * 改的仍是真实数据。
     *
     * @param asc true 为升序（低到高），false 为降序
     * @return 排序后的新列表。顺序独立，不影响内部顺序；
     *         但元素是内部对象本身，修改元素字段会影响真实数据
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
}
