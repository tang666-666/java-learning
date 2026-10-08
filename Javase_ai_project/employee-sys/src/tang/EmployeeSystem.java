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

    /**
     * 程序入口。启动时先预置示例数据，让菜单一打开就有内容可看；
     * 随后进入菜单循环：打印菜单、读取操作编号、按编号分发到各个菜单分支，
     * 直到用户输入 0 才结束循环、方法返回、程序退出。
     */
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
            // 用 printf 的 %.2f 而不是字符串拼接，是为了和表格的 %10.2f、
            // 统计报表的 %.2f 对齐——同一笔工资在三个地方显示成一样的两位小数，
            // 否则拼接会打印出 13800.0 这种一位小数的形式，看起来像两个数。
            System.out.printf("添加成功！%s 的工资为 %.2f 元。%n", e.getName(), e.calculateSalary());
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
            // 同样用 %.2f，与添加成功提示、表格、统计报表的小数位数保持一致
            System.out.printf("修改成功！新工资为 %.2f 元。%n", newEmp.calculateSalary());
        } else {
            System.out.println("修改失败。");
        }
    }

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
}
