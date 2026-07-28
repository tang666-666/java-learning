package tang.cycle;

import java.util.Random;

/**
 * 循环结构综合演示
 *
 * 本节内容：
 * 1. for 循环
 * 2. while 循环
 * 3. do-while 循环（至少执行一次的特性）
 * 4. 死循环及退出方式
 * 5. 循环嵌套（打印图形、九九乘法表）
 * 6. break 与 continue 的区别
 * 7. Random 类的使用
 * 8. 验证码生成（综合应用）
 *
 * @author tang
 * @version 1.0
 */
public class CycleDemo {

    public static void main(String[] args) {
        // ========== 1. for 循环 ==========
        System.out.println("========== 1. for 循环 ==========");

        // 求 1~100 的和
        int sumFor = 0;
        for (int i = 1; i <= 100; i++) {
            sumFor += i;
        }
        System.out.println("1~100 的和（for）：" + sumFor);

        // 求 1~100 的偶数和
        int evenSum = 0;
        for (int i = 2; i <= 100; i += 2) {
            evenSum += i;
        }
        System.out.println("1~100 的偶数和（for）：" + evenSum);

        System.out.println();


        // ========== 2. while 循环 ==========
        System.out.println("========== 2. while 循环 ==========");

        // 求 1~100 的和（while 实现）
        int sumWhile = 0;
        int i = 1;
        while (i <= 100) {
            sumWhile += i;
            i++;
        }
        System.out.println("1~100 的和（while）：" + sumWhile);

        // 不断翻倍，直到超过 10000
        int num = 1;
        int count = 0;
        while (num <= 10000) {
            num *= 2;
            count++;
        }
        System.out.println("1 不断翻倍，经过 " + count + " 次后超过 10000，值为 " + num);

        System.out.println();


        // ========== 3. do-while 循环 ==========
        System.out.println("========== 3. do-while 循环 ==========");

        // 基本使用
        int j = 1;
        int sumDo = 0;
        do {
            sumDo += j;
            j++;
        } while (j <= 100);
        System.out.println("1~100 的和（do-while）：" + sumDo);

        // 验证"至少执行一次"
        int n = 10;
        System.out.print("条件不满足（n < 0 = false），但 do-while ");
        do {
            System.out.print("仍然执行了这一次 → ");
        } while (n < 0);
        System.out.println("然后退出");

        System.out.println();


        // ========== 4. 死循环及退出方式 ==========
        System.out.println("========== 4. 死循环与 break 退出 ==========");

        int loopCount = 0;
        while (true) {
            loopCount++;
            if (loopCount >= 5) {
                System.out.println("死循环已执行 " + loopCount + " 次，break 退出");
                break;
            }
            System.out.println("死循环第 " + loopCount + " 次执行");
        }

        // for 死循环
        int count2 = 0;
        for (;;) {
            count2++;
            System.out.println("for 死循环第 " + count2 + " 次");
            if (count2 >= 3) {
                System.out.println("  → break 退出");
                break;
            }
        }

        System.out.println();


        // ========== 5. 循环嵌套 ==========
        System.out.println("========== 5. 循环嵌套 ==========");

        // 打印直角三角形
        System.out.println("直角三角形：");
        for (int row = 1; row <= 5; row++) {         // 外层：行数
            for (int col = 1; col <= row; col++) {   // 内层：每行打印的个数
                System.out.print("*");
            }
            System.out.println();
        }

        System.out.println();

        // 打印九九乘法表
        System.out.println("九九乘法表：");
        for (int row2 = 1; row2 <= 9; row2++) {
            for (int col2 = 1; col2 <= row2; col2++) {
                System.out.print(col2 + "x" + row2 + "=" + (row2 * col2) + "\t");
            }
            System.out.println();
        }

        System.out.println();


        // ========== 6. break 与 continue 对比 ==========
        System.out.println("========== 6. break vs continue ==========");

        // break：结束整个循环
        System.out.print("break（i=3 时结束）：");
        for (int k = 1; k <= 5; k++) {
            if (k == 3) {
                break;
            }
            System.out.print(k + " ");
        }
        System.out.println();

        // continue：跳过本次，继续下次
        System.out.print("continue（i=3 时跳过）：");
        for (int k = 1; k <= 5; k++) {
            if (k == 3) {
                continue;
            }
            System.out.print(k + " ");
        }
        System.out.println();

        // 应用：打印 1~10 中不是 3 的倍数的数
        System.out.print("1~10 中不是 3 的倍数：");
        for (int k = 1; k <= 10; k++) {
            if (k % 3 == 0) {
                continue;
            }
            System.out.print(k + " ");
        }
        System.out.println();

        // 嵌套循环中的 break（只跳出内层）
        System.out.println("嵌套循环中 break 只跳出内层：");
        for (int out = 1; out <= 3; out++) {
            System.out.print("外层 " + out + " → ");
            for (int inner = 1; inner <= 5; inner++) {
                if (inner == 3) {
                    break;      // 只跳出内层循环
                }
                System.out.print(inner + " ");
            }
            System.out.println();   // 外层正常换行
        }

        System.out.println();


        // ========== 7. Random 类 ==========
        System.out.println("========== 7. Random 类 ==========");

        Random r = new Random();

        // 生成随机整数
        int randInt = r.nextInt(100);           // [0, 99]
        int randInt2 = r.nextInt(100) + 1;      // [1, 100]
        System.out.println("r.nextInt(100) = " + randInt + "     （范围 0~99）");
        System.out.println("r.nextInt(100)+1 = " + randInt2 + "   （范围 1~100）");

        // 生成随机小数
        double randDouble = r.nextDouble();     // [0.0, 1.0)
        System.out.println("r.nextDouble() = " + randDouble + " （范围 0.0~1.0）");

        // 生成随机布尔值
        boolean randBool = r.nextBoolean();
        System.out.println("r.nextBoolean() = " + randBool);

        // 生成随机小写字母
        char randLower = (char) ('a' + r.nextInt(26));
        System.out.println("随机小写字母：" + randLower);

        // 生成指定范围的随机数 [min, max]
        int min = 50, max = 80;
        int randRange = r.nextInt(max - min + 1) + min;
        System.out.println("随机数[" + min + ", " + max + "]：" + randRange);

        System.out.println();


        // ========== 8. 综合案例：验证码生成（Math.random + switch） ==========
        System.out.println("========== 8. 验证码生成 ==========");

        System.out.println("4 位验证码：" + generateCode(4));
        System.out.println("6 位验证码：" + generateCode(6));
        System.out.println("8 位验证码：" + generateCode(8));

        // 用 Random 类实现另一种验证码
        System.out.println("5 位验证码（Random 版）：" + generateCodeByRandom(5));

        System.out.println();


        // ========== 9. 综合案例：猜数字小游戏 ==========
        System.out.println("========== 9. 猜数字小游戏（演示循环综合运用） ==========");

        guessNumber();
    }

    /**
     * 生成随机验证码（Math.random + switch）
     * 每位可能是：数字 0~9、小写字母 a~z、大写字母 A~Z
     *
     * @param n 验证码长度
     * @return 生成的验证码字符串
     */
    public static String generateCode(int n) {
        String code = "";
        for (int i = 0; i < n; i++) {
            // 随机决定当前位是什么类型：0=数字，1=小写字母，2=大写字母
            int type = (int) (Math.random() * 3);
            switch (type) {
                case 0:     // 数字 0~9
                    int num = (int) (Math.random() * 10);
                    code += num;
                    break;
                case 1:     // 小写字母 a~z
                    char lower = (char) ('a' + (int) (Math.random() * 26));
                    code += lower;
                    break;
                case 2:     // 大写字母 A~Z
                    char upper = (char) ('A' + (int) (Math.random() * 26));
                    code += upper;
                    break;
            }
        }
        return code;
    }

    /**
     * 用 Random 类生成验证码（另一种实现方式）
     * @param n 验证码长度
     * @return 生成的验证码字符串
     */
    public static String generateCodeByRandom(int n) {
        Random r = new Random();
        String code = "";
        // 所有可选字符的集合
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < n; i++) {
            int index = r.nextInt(chars.length());
            code += chars.charAt(index);
        }
        return code;
    }

    /**
     * 猜数字小游戏
     * 系统随机生成 1~100 的整数，用户猜测，提示"大了""小了"直到猜中
     * 综合运用：Random、while 死循环、break、if-else
     */
    public static void guessNumber() {
        // 提示：这里不实际读取用户输入，仅演示逻辑框架
        // 用模拟值演示

        // 随机生成目标数字
        Random r = new Random();
        int target = r.nextInt(100) + 1;    // [1, 100]

        // 模拟过程（简化版，用预先设定的猜值来演示）
        int[] guesses = {50, 75, 63, 68, 66, 67};  // 模拟的猜测序列
        int attempts = 0;

        System.out.println("（系统已随机生成 1~100 的数字，模拟猜测过程）");

        for (int guess : guesses) {
            attempts++;
            if (guess > target) {
                System.out.println("  猜 " + guess + " → 大了");
            } else if (guess < target) {
                System.out.println("  猜 " + guess + " → 小了");
            } else {
                System.out.println("  猜 " + guess + " → 🎉 猜中了！共猜 " + attempts + " 次");
                return;
            }
        }

        // 如果模拟序列没猜中，补充循环
        System.out.println("  模拟序列未命中，继续随机猜测...");
        while (true) {
            attempts++;
            int guess = r.nextInt(100) + 1;
            if (guess == target) {
                System.out.println("  最终在第 " + attempts + " 次猜中 " + target);
                break;
            }
            if (attempts > 1000) {
                System.out.println("  超过 1000 次仍未猜中，不合理，退出");
                break;
            }
        }
    }
}
