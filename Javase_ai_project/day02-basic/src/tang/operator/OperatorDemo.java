package tang.operator;

/**
 * 运算符综合演示
 *
 * 本节内容：
 * 1. 算术运算符 + - * / %
 * 2. "+" 做字符串连接符
 * 3. 自增自减运算符 ++a a++ --a a--
 * 4. 赋值运算符与扩展赋值运算符 = += -= *= /= %=
 * 5. 关系运算符 > < >= <= == !=
 * 6. 逻辑运算符 & | ! ^ && ||
 * 7. 三目运算符 ? :
 *
 * @author tang
 * @version 1.0
 */
public class OperatorDemo {

    public static void main(String[] args) {
        // ========== 1. 算术运算符 ==========
        System.out.println("========== 1. 算术运算符 ==========");
        int a = 10, b = 3;

        System.out.println(a + " + " + b + " = " + (a + b));   // 13
        System.out.println(a + " - " + b + " = " + (a - b));   // 7
        System.out.println(a + " * " + b + " = " + (a * b));   // 30
        System.out.println(a + " / " + b + " = " + (a / b));   // 3（整数除法，舍去小数）
        System.out.println(a + " % " + b + " = " + (a % b));   // 1

        // 整数除法 vs 小数除法
        System.out.println("10 / 3 = " + (10 / 3));            // 3
        System.out.println("10.0 / 3 = " + (10.0 / 3));        // 3.33333...
        System.out.println("10 / 3.0 = " + (10 / 3.0));        // 3.33333...

        System.out.println();


        // ========== 2. "+" 做字符串连接符 ==========
        System.out.println("========== 2. \"+\" 字符串连接符 ==========");
        int num = 10;

        System.out.println("abc" + num);            // "abc10"（数字拼接成字符串）
        System.out.println(num + 5);                // 15（两边数值，做算术加法）
        System.out.println("itheima" + num + 'a');  // "itheima10a"（遇到字符串后全做拼接）
        System.out.println(num + 'a' + "itheima");  // "107itheima"（先做算术 10+97=107，再拼接）

        // 规律验证
        System.out.println("1 + 2 + \"abc\" = " + (1 + 2 + "abc"));     // "3abc"
        System.out.println("\"abc\" + 1 + 2 = " + ("abc" + 1 + 2));     // "abc12"
        System.out.println("1 + \"abc\" + 2 = " + (1 + "abc" + 2));     // "1abc2"

        System.out.println();


        // ========== 3. 自增自减运算符 ==========
        System.out.println("========== 3. 自增自减运算符 ==========");

        int x = 10;
        System.out.println("初始值 x = " + x);

        // 后置自增：先用后加
        int postInc = x++;      // postInc = 10, x 变为 11
        System.out.println("int postInc = x++ → postInc = " + postInc + ", x = " + x);

        // 前置自增：先加后用
        int preInc = ++x;       // x 先变成 12, preInc = 12
        System.out.println("int preInc = ++x → preInc = " + preInc + ", x = " + x);

        // 后置自减：先用后减
        int postDec = x--;      // postDec = 12, x 变为 11
        System.out.println("int postDec = x-- → postDec = " + postDec + ", x = " + x);

        // 前置自减：先减后用
        int preDec = --x;       // x 先变成 10, preDec = 10
        System.out.println("int preDec = --x → preDec = " + preDec + ", x = " + x);

        // 复杂表达式（不建议这样写，仅做理解）
        int p = 10;
        int q = p++ + ++p;      // p++ 用 10 → p=11，++p p=12 → 用 12 → q = 10 + 12 = 22
        System.out.println("p = " + p + ", q = p++ + ++p = " + q + "（p 最终为 " + p + "）");

        System.out.println();


        // ========== 4. 赋值运算符与扩展赋值运算符 ==========
        System.out.println("========== 4. 赋值运算符 ==========");

        int c = 20;                     // 基本赋值
        System.out.println("c = " + c);

        c += 5;     // c = c + 5
        System.out.println("c += 5 → c = " + c);    // 25

        c -= 3;     // c = c - 3
        System.out.println("c -= 3 → c = " + c);    // 22

        c *= 2;     // c = c * 2
        System.out.println("c *= 2 → c = " + c);    // 44

        c /= 4;     // c = c / 4
        System.out.println("c /= 4 → c = " + c);    // 11

        c %= 5;     // c = c % 5
        System.out.println("c %= 5 → c = " + c);    // 1

        // 扩展赋值运算符隐含强制类型转换
        byte b1 = 10;
        // b1 = b1 + 5;   // ❌ 编译错误：byte + int → int，不能直接赋给 byte
        b1 += 5;            // ✅ 等价于 b1 = (byte)(b1 + 5)
        System.out.println("byte b1 += 5 → b1 = " + b1 + "（隐含强转）");

        int i = 10;
        i += 3.5;           // 等价于 i = (int)(10 + 3.5) = 13
        System.out.println("int i += 3.5 → i = " + i + "（小数部分被截断）");

        System.out.println();


        // ========== 5. 关系运算符 ==========
        System.out.println("========== 5. 关系运算符 ==========");

        int m = 5, n = 3;
        System.out.println(m + " > " + n + " → " + (m > n));    // true
        System.out.println(m + " < " + n + " → " + (m < n));    // false
        System.out.println(m + " >= " + n + " → " + (m >= n));   // true
        System.out.println(m + " <= " + n + " → " + (m <= n));   // false
        System.out.println(m + " == " + n + " → " + (m == n));   // false
        System.out.println(m + " != " + n + " → " + (m != n));   // true

        // 关系运算符的结果是 boolean，可直接赋给 boolean 变量
        int score = 85;
        boolean isPass = score >= 60;
        System.out.println("score = " + score + "，是否及格：" + isPass);

        System.out.println();


        // ========== 6. 逻辑运算符 ==========
        System.out.println("========== 6. 逻辑运算符 ==========");

        boolean t = true, f = false;

        System.out.println("true  & false = " + (t & f));   // false（与）
        System.out.println("true  | false = " + (t | f));   // true（或）
        System.out.println("!true         = " + (!t));      // false（非）
        System.out.println("true  ^ false = " + (t ^ f));   // true（异或：不同为 true）
        System.out.println("true  ^ true  = " + (t ^ t));   // false（异或：相同为 false）

        // 短路与 &&
        boolean flag1 = (score >= 0) && (score <= 100);     // 判断 score 是否在 0~100
        System.out.println("score(" + score + ") 是否在 0~100 之间：" + flag1);

        // 短路或 ||
        boolean flag2 = (score < 60) || (score == 100);     // 不及格或满分
        System.out.println("score(" + score + ") 不及格或满分：" + flag2);

        // 短路特性演示
        int val = 10;
        boolean shortCircuit = (val > 20) && (++val > 10);  // 左边 false，右边不执行（&& 短路）
        System.out.println("短路 &&：(val > 20) && (++val > 10) → " + shortCircuit + "，val = " + val + "（++val 未执行）");

        val = 10;
        boolean nonShort = (val > 20) & (++val > 10);       // 左边 false，右边照常执行（& 非短路）
        System.out.println("非短路 &：(val > 20) & (++val > 10) → " + nonShort + "，val = " + val + "（++val 执行了）");

        System.out.println();


        // ========== 7. 三目运算符 ==========
        System.out.println("========== 7. 三目运算符 ==========");

        // 取较大值
        int a1 = 15, b2 = 20;
        int max = a1 > b2 ? a1 : b2;
        System.out.println("较大值：" + a1 + " 和 " + b2 + " 中较大的是 " + max);

        // 判断奇偶
        int num2 = 7;
        String parity = num2 % 2 == 0 ? "偶数" : "奇数";
        System.out.println(num2 + " 是" + parity);

        // 三目嵌套（不推荐嵌套太深）
        int x2 = 50, y2 = 80, z2 = 30;
        int max3 = x2 > y2 ? (x2 > z2 ? x2 : z2) : (y2 > z2 ? y2 : z2);
        System.out.println(x2 + ", " + y2 + ", " + z2 + " 中最大的是 " + max3);

        // 实际应用：根据分数输出等级
        int score2 = 75;
        String level = score2 >= 90 ? "优秀"
                     : score2 >= 80 ? "良好"
                     : score2 >= 70 ? "中等"
                     : score2 >= 60 ? "及格"
                     : "不及格";
        System.out.println("分数 " + score2 + " 的等级是：" + level);
    }
}
