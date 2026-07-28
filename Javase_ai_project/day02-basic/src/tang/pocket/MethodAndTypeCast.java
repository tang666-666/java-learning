package tang.pocket;

/**
 * 方法进阶与类型转换综合演示
 *
 * 本节内容：
 * 1. 方法的完整定义格式
 * 2. 方法重载（参数个数不同、类型不同、顺序不同）
 * 3. return 提前结束 void 方法
 * 4. 自动类型转换（小范围→大范围）
 * 5. 强制类型转换（大范围→小范围）及数据丢失风险
 * 6. 表达式的自动类型提升
 *
 * @author tang
 * @version 1.0
 */
public class MethodAndTypeCast {

    public static void main(String[] args) {
        // ========== 1. 方法重载演示 ==========
        System.out.println("========== 方法重载演示 ==========");

        // 调用 add(int, int) —— 两个整数相加
        int sum1 = add(10, 20);
        System.out.println("add(10, 20) = " + sum1);

        // 调用 add(int, int, int) —— 三个整数相加
        int sum2 = add(10, 20, 30);
        System.out.println("add(10, 20, 30) = " + sum2);

        // 调用 add(double, double) —— 两个小数相加
        double sum3 = add(2.5, 3.7);
        System.out.println("add(2.5, 3.7) = " + sum3);

        // 调用 add(int, double) —— 整数 + 小数（参数顺序：int, double）
        double sum4 = add(5, 2.5);
        System.out.println("add(5, 2.5) = " + sum4);

        // 调用 add(double, int) —— 小数 + 整数（参数顺序：double, int）
        double sum5 = add(5.5, 3);
        System.out.println("add(5.5, 3) = " + sum5);

        System.out.println();


        // ========== 2. return 提前结束方法 ==========
        System.out.println("========== return 提前结束方法演示 ==========");

        System.out.print("checkAge(20)：");
        checkAge(20);       // 合法的年龄，正常输出

        System.out.print("checkAge(200)：");
        checkAge(200);      // 非法的年龄，提前 return，不会输出"年龄是"

        System.out.println();


        // ========== 3. 自动类型转换演示 ==========
        System.out.println("========== 自动类型转换演示 ==========");

        int intVal = 100;
        long longVal = intVal;          // int → long，自动转换
        float floatVal = longVal;       // long → float，自动转换
        double doubleVal = floatVal;    // float → double，自动转换
        System.out.println("int " + intVal + " → long " + longVal);
        System.out.println("long " + longVal + " → float " + floatVal);
        System.out.println("float " + floatVal + " → double " + doubleVal);

        // char → int 自动转换（字符会转为对应的 Unicode 编码值）
        char ch = 'A';          // 'A' 的 Unicode 编码是 65
        int asciiCode = ch;     // char → int，自动转换
        System.out.println("char '" + ch + "' → int " + asciiCode);

        System.out.println();


        // ========== 4. 强制类型转换演示 ==========
        System.out.println("========== 强制类型转换演示 ==========");

        // double → int：小数部分直接丢弃
        double pi = 3.1415926;
        int intPi = (int) pi;           // 强制转换
        System.out.println("double " + pi + " → int " + intPi + "（精度丢失）");

        // 数据溢出风险：int → byte
        int bigNum = 300;
        byte byteNum = (byte) bigNum;   // 300 超出 byte 范围（-128~127），数据溢出
        System.out.println("int " + bigNum + " → byte " + byteNum + "（数据溢出）");

        // long → int
        long largeLong = 9999999999L;
        int castedInt = (int) largeLong;
        System.out.println("long " + largeLong + " → int " + castedInt + "（数据溢出）");

        System.out.println();


        // ========== 5. 表达式自动类型提升 ==========
        System.out.println("========== 表达式自动类型提升演示 ==========");

        // 示例一：int + double → double
        int a = 10;
        double b = 2.5;
        double resultAB = a + b;    // int 自动提升为 double
        System.out.println("int " + a + " + double " + b + " = " + resultAB + "（结果自动为 double）");

        // 示例二：byte + byte → int
        byte b1 = 10;
        byte b2 = 20;
        int sumBytes = b1 + b2;     // byte + byte 自动提升为 int
        System.out.println("byte " + b1 + " + byte " + b2 + " = " + sumBytes + "（byte 运算结果自动为 int）");

        // 示例三：多种类型混合运算，结果由最高类型决定
        byte byteVal = 2;
        short shortVal = 4;
        char charVal = 'A';         // 'A' = 65
        int intVal2 = 10;
        long longVal2 = 100L;
        float floatVal2 = 2.0F;
        double doubleVal2 = 3.0;

        // 整个表达式结果是 double（最高类型是 double）
        double mixedResult = byteVal + shortVal + charVal + intVal2 + longVal2 + floatVal2 + doubleVal2;
        System.out.println("混合类型表达式结果：" + mixedResult + "（由最高类型 double 决定）");

        // 每个值参与运算时的类型跟踪
        System.out.println("  byte(" + byteVal + ") → 参与运算时自动提升");
        System.out.println("  short(" + shortVal + ") → 参与运算时自动提升");
        System.out.println("  char('" + charVal + "'=" + (int) charVal + ") → 参与运算时自动提升为 int");
        System.out.println("  int(" + intVal2 + ") → 参与运算时自动提升");
        System.out.println("  long(" + longVal2 + ") → 参与运算时自动提升");
        System.out.println("  float(" + floatVal2 + ") → 参与运算时自动提升");
        System.out.println("  double(" + doubleVal2 + ") → 最高类型，无需提升");
    }

    // ========== 方法重载：同一个方法名 add，不同参数 ==========

    /**
     * 两个整数相加
     * @param a 第一个整数
     * @param b 第二个整数
     * @return 两数之和
     */
    public static int add(int a, int b) {
        return a + b;
    }

    /**
     * 三个整数相加（参数个数不同）
     * @param a 第一个整数
     * @param b 第二个整数
     * @param c 第三个整数
     * @return 三数之和
     */
    public static int add(int a, int b, int c) {
        return a + b + c;
    }

    /**
     * 两个小数相加（参数类型不同）
     * @param a 第一个小数
     * @param b 第二个小数
     * @return 两数之和
     */
    public static double add(double a, double b) {
        return a + b;
    }

    /**
     * 整数 + 小数（参数顺序不同：int, double）
     * @param a 整数
     * @param b 小数
     * @return 求和结果
     */
    public static double add(int a, double b) {
        return a + b;
    }

    /**
     * 小数 + 整数（参数顺序不同：double, int）
     * @param a 小数
     * @param b 整数
     * @return 求和结果
     */
    public static double add(double a, int b) {
        return a + b;
    }

    // ========== return 提前结束 void 方法 ==========

    /**
     * 检查年龄是否合法
     * 如果年龄不合法（<0 或 >150），输出提示后立即 return 结束方法
     * @param age 待检查的年龄
     */
    public static void checkAge(int age) {
        if (age < 0 || age > 150) {
            System.out.println("年龄不合法！");
            return;         // 提前结束方法，后面代码不再执行
        }
        System.out.println("年龄是：" + age);
    }
}
