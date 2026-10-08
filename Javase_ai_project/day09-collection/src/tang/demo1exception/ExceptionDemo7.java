package tang.demo1exception;

/**
 * 异常综合演示
 *
 * 本节内容：
 * 1. 异常体系——Error / Exception，运行时异常 vs 编译时异常
 * 2. 常见运行时异常（数组越界、除零、空指针）
 * 3. 异常处理——throws 抛出 vs try-catch 捕获
 * 4. 异常的作用——定位 bug、作为特殊返回值通知上层
 * 5. 自定义异常——继承 RuntimeException / Exception
 * 6. 异常处理方案——层层上抛、捕获后重新修复
 * 7. finally——无论是否发生异常都会执行
 *
 * @author tang
 * @version 1.0
 */
public class ExceptionDemo7 {

    public static void main(String[] args) {
        // ========== 1. 常见运行时异常演示 ==========
        System.out.println("========== 1. 常见运行时异常 ==========");

        // ① 数组索引越界异常
        try {
            int[] arr = {1, 2, 3};
            System.out.println(arr[3]);      // 越界！
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("捕获到数组越界异常：" + e.getMessage());
        }

        // ② 算术异常（除零）
        try {
            System.out.println(10 / 0);
        } catch (ArithmeticException e) {
            System.out.println("捕获到算术异常：" + e.getMessage());
        }

        // ③ 空指针异常
        try {
            String str = null;
            System.out.println(str.length());
        } catch (NullPointerException e) {
            System.out.println("捕获到空指针异常：" + e.getClass().getSimpleName());
        }

        System.out.println("  → 运行时异常编译阶段不报错，运行时才出现");

        System.out.println();


        // ========== 2. 异常处理：捕获后程序继续执行 ==========
        System.out.println("========== 2. 捕获后程序继续执行 ==========");

        System.out.println("程序开始");
        try {
            System.out.println("  div(10, 0) = " + div(10, 0));   // 抛异常
            System.out.println("  执行成功");                       // 不执行
        } catch (Exception e) {
            System.out.println("  执行失败：" + e.getMessage());     // 执行
        }
        System.out.println("程序结束（✅ 仍然执行）");

        // 正常情况
        try {
            System.out.println("  div(10, 2) = " + div(10, 2));
            System.out.println("  执行成功");
        } catch (Exception e) {
            System.out.println("  执行失败");
        }

        System.out.println();


        // ========== 3. 异常作为特殊返回值 ==========
        System.out.println("========== 3. 异常作为特殊返回值 ==========");

        // 方式一：返回 -1 表示失败（问题：调用者可能不知道 -1 是失败）
        System.out.println("  用返回值表示失败：divByReturn(10, 0) = " + divByReturn(10, 0) + "（含义不明确）");

        // 方式二：抛出异常（强制通知调用者）
        try {
            div(10, 0);
        } catch (Exception e) {
            System.out.println("  用异常表示失败：捕获到 \"" + e.getMessage() + "\"（含义明确）");
        }

        System.out.println();


        // ========== 4. 自定义运行时异常 ==========
        System.out.println("========== 4. 自定义运行时异常 ==========");

        // 继承 RuntimeException：编译不报错，运行时才出现
        try {
            saveAgeRuntime(300);      // 非法年龄
            System.out.println("  成功了");
        } catch (AgeIllegalRuntimeException e) {
            System.out.println("  失败了（运行时异常）：" + e.getMessage());
        }

        // 合法年龄
        saveAgeRuntime(18);
        System.out.println("  → 运行时异常：编译阶段不报错，提醒不激进");

        System.out.println();


        // ========== 5. 自定义编译时异常 ==========
        System.out.println("========== 5. 自定义编译时异常 ==========");

        // 继承 Exception：必须在方法上 throws 声明
        try {
            saveAgeCompile(300);      // 非法年龄
            System.out.println("  成功了");
        } catch (AgeIllegalException e) {
            System.out.println("  失败了（编译时异常）：" + e.getMessage());
        }

        // 注意：即使传的是合法年龄，编译器也不知道——所以调用处仍必须处理
        // 这正是编译时异常"激进"的体现
        try {
            saveAgeCompile(18);
        } catch (AgeIllegalException e) {
            System.out.println("  失败了：" + e.getMessage());
        }
        System.out.println("  → 编译时异常：编译阶段就报错，调用处必须处理，提醒较激进");

        System.out.println();


        // ========== 6. 异常处理方案一：层层上抛，最外层捕获 ==========
        System.out.println("========== 6. 方案一：层层上抛，最外层捕获 ==========");

        System.out.println("程序开始");
        try {
            layer3();                       // 最外层统一捕获
            System.out.println("操作成功");
        } catch (Exception e) {
            // 记录异常信息（给程序员看）
            System.out.println("  记录异常：" + e.getMessage());
            // 响应适合用户观看的信息（给用户看）
            System.out.println("操作失败，请稍后重试");
        }
        System.out.println("程序结束");

        System.out.println();


        // ========== 7. 异常处理方案二：捕获后重新修复 ==========
        System.out.println("========== 7. 方案二：捕获后重新修复 ==========");

        // 模拟：用户第一次输入非法，第二次输入合法
        String[] mockInputs = {"abc", "199.9"};     // 模拟用户输入序列
        int[] inputIndex = {0};

        double price = 0;
        while (true) {
            try {
                price = userInputPrice(mockInputs, inputIndex);
                break;                              // 成功则跳出
            } catch (Exception e) {
                System.out.println("  输入的数据有误，请重新输入");
            }
        }
        System.out.println("商品定价：" + price);
        System.out.println("  → 死循环 + try-catch 实现出错重试");

        System.out.println();


        // ========== 8. finally 代码块 ==========
        System.out.println("========== 8. finally 代码块 ==========");

        // 情况一：无异常
        System.out.println("情况一：try 中无异常");
        testFinally(false);

        // 情况二：有异常
        System.out.println("情况二：try 中有异常");
        testFinally(true);

        System.out.println("  → 无论是否发生异常，finally 都会执行");

        System.out.println();


        // ========== 9. 综合案例：安全的年龄录入 ==========
        System.out.println("========== 9. 综合案例：安全录入年龄 ==========");

        int[] testAges = {18, -5, 25, 300, 30};
        for (int age : testAges) {
            try {
                checkAndSaveAge(age);
                System.out.println("  年龄 " + age + " → 保存成功");
            } catch (AgeIllegalRuntimeException e) {
                System.out.println("  年龄 " + age + " → 保存失败：" + e.getMessage());
            } finally {
                System.out.println("    （finally：无论成败，这里都执行）");
            }
        }
    }

    // ==================== 异常处理相关方法 ====================

    /**
     * 除法运算——出错时抛异常（异常作为特殊返回值）
     * @param a 被除数
     * @param b 除数
     * @return 商
     * @throws Exception 除数为 0 时抛出
     */
    public static int div(int a, int b) throws Exception {
        if (b == 0) {
            // 返回一个异常给上层调用者
            // 返回的异常还能告知上层：底层是执行成功还是失败
            throw new Exception("除数不能为零");
        }
        return a / b;
    }

    /**
     * 除法运算——出错时返回 -1（对比方案，含义不明确）
     */
    public static int divByReturn(int a, int b) {
        if (b == 0) {
            return -1;      // 调用者可能不知道 -1 是"失败"
        }
        return a / b;
    }

    /**
     * 保存年龄——使用自定义运行时异常（无需 throws 声明）
     * @param age 年龄（1~200 合法）
     */
    public static void saveAgeRuntime(int age) {
        if (age < 1 || age > 200) {
            // 抛出自定义运行时异常
            throw new AgeIllegalRuntimeException("年龄非法（运行时）：" + age);
        }
        System.out.println("  年龄合法，保存年龄：" + age);
    }

    /**
     * 保存年龄——使用自定义编译时异常（必须 throws 声明）
     * @param age 年龄（1~200 合法）
     * @throws AgeIllegalException 年龄非法时抛出
     */
    public static void saveAgeCompile(int age) throws AgeIllegalException {
        if (age < 1 || age > 200) {
            // 抛出自定义编译时异常
            throw new AgeIllegalException("年龄非法（编译时）：" + age);
        }
        System.out.println("  年龄合法，保存年龄：" + age);
    }

    /**
     * 第三层：底层方法，异常层层往上抛
     */
    public static void layer3() throws Exception {
        System.out.println("  进入第三层（最底层）");
        layer2();
    }

    /**
     * 第二层：继续上抛
     */
    public static void layer2() throws Exception {
        System.out.println("  进入第二层");
        layer1();
    }

    /**
     * 第一层：抛出实际异常
     */
    public static void layer1() throws Exception {
        System.out.println("  进入第一层（抛出异常）");
        throw new Exception("底层数据读取失败");
    }

    /**
     * 模拟用户输入价格（用预设序列代替真实 Scanner 输入）
     * @param inputs 模拟的输入序列
     * @param index  当前输入位置
     * @return 解析出的价格
     */
    public static double userInputPrice(String[] inputs, int[] index) {
        if (index[0] >= inputs.length) {
            throw new RuntimeException("没有更多模拟输入了");
        }
        String input = inputs[index[0]++];
        System.out.println("  用户输入：" + input);
        return Double.parseDouble(input);   // 输入 "abc" 会抛 NumberFormatException
    }

    /**
     * 演示 finally 的执行
     * @param throwException 是否在 try 中抛出异常
     */
    public static void testFinally(boolean throwException) {
        try {
            System.out.println("  [try] 执行");
            if (throwException) {
                throw new RuntimeException("模拟异常");
            }
            System.out.println("  [try] 正常结束");
        } catch (Exception e) {
            System.out.println("  [catch] 捕获异常：" + e.getMessage());
        } finally {
            System.out.println("  [finally] 执行了（无论是否异常）");
        }
    }

    /**
     * 综合案例：检查并保存年龄（带完整异常处理）
     * @param age 年龄
     */
    public static void checkAndSaveAge(int age) {
        if (age < 1 || age > 200) {
            throw new AgeIllegalRuntimeException("年龄非法：" + age + "（应在 1~200 之间）");
        }
        // 保存逻辑（此处省略）
    }
}
