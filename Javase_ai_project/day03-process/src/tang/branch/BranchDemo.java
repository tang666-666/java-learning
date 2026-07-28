package tang.branch;

/**
 * 分支结构综合演示
 *
 * 本节内容：
 * 1. 顺序结构——代码从上往下逐行执行
 * 2. if 的三种形式：单路 / 双路 / 多路
 * 3. switch 分支结构：byte/short/int/char，break，穿透，case 合并
 *
 * @author tang
 * @version 1.0
 */
public class BranchDemo {

    public static void main(String[] args) {
        // ========== 1. 顺序结构 ==========
        System.out.println("========== 1. 顺序结构 ==========");
        System.out.println("程序从上往下逐行执行");
        System.out.println("这是第一行");
        System.out.println("这是第二行");
        System.out.println("这是第三行");

        System.out.println();


        // ========== 2. if 的第一种形式：单路分支 ==========
        System.out.println("========== 2. if 第一种形式：单路分支 ==========");

        int age = 20;
        System.out.println("年龄：" + age);
        if (age >= 18) {
            System.out.println("已成年，可以上网");
        }
        // 条件不满足时，什么都不做
        int age2 = 15;
        System.out.print("年龄：" + age2 + " → ");
        if (age2 >= 18) {
            System.out.println("已成年");       // 不会执行
        } else {
            System.out.println("没有输出");     // 加个标记说明
        }

        System.out.println();


        // ========== 3. if 的第二种形式：双路分支 if-else ==========
        System.out.println("========== 3. if 第二种形式：双路分支 if-else ==========");

        int score = 55;
        System.out.print("分数：" + score + " → ");
        if (score >= 60) {
            System.out.println("及格");
        } else {
            System.out.println("不及格");
        }

        // 判断偶数还是奇数
        int num = 7;
        System.out.print(num + " 是：");
        if (num % 2 == 0) {
            System.out.println("偶数");
        } else {
            System.out.println("奇数");
        }

        System.out.println();


        // ========== 4. if 的第三种形式：多路分支 if-else if-else ==========
        System.out.println("========== 4. if 第三种形式：多路分支 ==========");

        int score2 = 85;
        System.out.print("分数 " + score2 + " 的等级是：");
        if (score2 >= 90) {
            System.out.println("优秀");
        } else if (score2 >= 80) {
            System.out.println("良好");
        } else if (score2 >= 70) {
            System.out.println("中等");
        } else if (score2 >= 60) {
            System.out.println("及格");
        } else {
            System.out.println("不及格");
        }

        // 多点判断：根据温度给出建议
        int temperature = 35;
        System.out.print("当前温度 " + temperature + "℃ → ");
        if (temperature >= 35) {
            System.out.println("高温预警，注意防暑");
        } else if (temperature >= 30) {
            System.out.println("天气炎热");
        } else if (temperature >= 20) {
            System.out.println("温度适宜");
        } else if (temperature >= 10) {
            System.out.println("有点凉");
        } else {
            System.out.println("很冷，注意保暖");
        }

        System.out.println();


        // ========== 5. switch 基础演示 ==========
        System.out.println("========== 5. switch 基础演示 ==========");

        // switch 表达式类型：int
        int weekday = 3;
        System.out.print("星期" + weekday + "：");
        switch (weekday) {
            case 1:
                System.out.println("星期一");
                break;
            case 2:
                System.out.println("星期二");
                break;
            case 3:
                System.out.println("星期三");
                break;
            case 4:
                System.out.println("星期四");
                break;
            case 5:
                System.out.println("星期五");
                break;
            default:
                System.out.println("周末");
                break;
        }

        // switch 表达式类型：char
        char grade = 'B';
        System.out.print("等级 " + grade + "：");
        switch (grade) {
            case 'A':
                System.out.println("90~100 分");
                break;
            case 'B':
                System.out.println("80~89 分");
                break;
            case 'C':
                System.out.println("70~79 分");
                break;
            case 'D':
                System.out.println("60~69 分");
                break;
            default:
                System.out.println("不及格");
                break;
        }

        System.out.println();


        // ========== 6. switch 穿透现象 ==========
        System.out.println("========== 6. switch 穿透现象 ==========");

        int n = 1;
        System.out.println("switch(" + n + ") 没有 break：");
        switch (n) {
            case 1:
                System.out.println("  case 1 执行");
                // 没写 break → 穿透到 case 2
            case 2:
                System.out.println("  case 2 执行（穿透过来的）");
                break;  // 这里遇到 break，停止
            case 3:
                System.out.println("  case 3 不会执行");
                break;
        }

        System.out.println();


        // ========== 7. case 合并（利用穿透实现） ==========
        System.out.println("========== 7. case 合并 ==========");

        int month = 3;
        String season = switchSeason(month);
        System.out.println(month + " 月是：" + season);

        // 批量测试
        int[] months = {1, 4, 7, 10, 13};
        for (int m : months) {
            System.out.println("  " + m + " 月 → " + switchSeason(m));
        }

        System.out.println();


        // ========== 8. 实际案例：判断一年中的天数 ==========
        System.out.println("========== 8. switch 实际案例 ==========");

        int month2 = 2;
        int year = 2024;    // 闰年
        int days = getDaysInMonth(year, month2);
        System.out.println(year + " 年 " + month2 + " 月有 " + days + " 天");
    }

    /**
     * 根据月份返回季节（演示 case 合并）
     * @param month 月份（1~12）
     * @return 季节名称
     */
    public static String switchSeason(int month) {
        String season;
        switch (month) {
            case 3:
            case 4:
            case 5:
                season = "春季";
                break;
            case 6:
            case 7:
            case 8:
                season = "夏季";
                break;
            case 9:
            case 10:
            case 11:
                season = "秋季";
                break;
            case 12:
            case 1:
            case 2:
                season = "冬季";
                break;
            default:
                season = "无效月份";
                break;
        }
        return season;
    }

    /**
     * 根据年份和月份返回天数（复杂 switch 应用）
     * @param year  年份
     * @param month 月份
     * @return 该月的天数
     */
    public static int getDaysInMonth(int year, int month) {
        int days;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                // 闰年判断：能被4整除但不能被100整除，或者能被400整除
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    days = 29;
                } else {
                    days = 28;
                }
                break;
            default:
                days = -1;  // 无效月份
                break;
        }
        return days;
    }
}
