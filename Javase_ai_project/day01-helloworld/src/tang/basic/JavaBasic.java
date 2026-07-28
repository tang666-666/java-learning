package tang.basic;

/**
 * Java 基础概念综合演示
 *
 * 本节课涵盖内容：
 * 1. 方法（Method）——程序的最小功能单位
 * 2. 三种注释的用法
 * 3. 不同字面量（Literal）
 * 4. 变量（Variable）的声明与使用
 * 5. 8 种基本数据类型
 * 6. 关键字与标识符的命名规则
 *
 * @author tang
 * @version 1.0
 */
public class JavaBasic {

    /*
     * 程序的入口 —— main 方法
     * JVM 会从这里开始执行代码
     * 这是一个多行注释，用于说明方法的作用
     */
    public static void main(String[] args) {
        // ========== 1. 字面量演示 ==========
        // 单行注释：字面量就是代码中直接写出来的固定值
        System.out.println("========== 不同字面量演示 ==========");

        // 整数字面量
        System.out.println("整数字面量：" + 100);
        System.out.println("负整数字面量：" + -5);

        // 小数字面量（浮点型）
        System.out.println("小数字面量：" + 3.14);
        System.out.println("负小数字面量：" + -0.5);

        // 字符字面量 —— 用单引号括起来的单个字符
        System.out.println("字符字面量：" + 'A');
        System.out.println("字符字面量（中文）：" + '中');

        // 字符串字面量 —— 用双引号括起来的任意文本
        System.out.println("字符串字面量：" + "Hello, Java!");
        System.out.println("字符串字面量（中文）：" + "你好，世界！");

        // 布尔字面量 —— 只有 true 和 false 两个值
        System.out.println("布尔字面量：" + true);
        System.out.println("布尔字面量：" + false);

        System.out.println(); // 空行分隔


        // ========== 2. 变量演示 ==========
        System.out.println("========== 变量演示 ==========");

        /*
         * 单行声明 + 赋值
         * 格式：数据类型 变量名 = 值;
         */
        int age = 25;   // 声明一个整数变量 age，赋值为 25
        // 先声明再赋值
        double price;   // 声明一个 double 变量
        price = 99.9;   // 赋值

        System.out.println("年龄（age）：" + age);
        System.out.println("价格（price）：" + price);

        // 变量可以重新赋值
        age = 26;       // 把 age 的值从 25 改为 26
        System.out.println("修改后的年龄：" + age);

        // 变量也可以参与运算
        int nextYearAge = age + 1;
        System.out.println("明年年龄：" + nextYearAge);

        System.out.println();


        // ========== 3. 8 种基本数据类型演示 ==========
        System.out.println("========== 8种基本数据类型演示 ==========");

        // byte —— 1 字节，范围：-128 ~ 127
        byte byteNum = 100;
        System.out.println("byte 类型：" + byteNum);

        // short —— 2 字节
        short shortNum = 30000;
        System.out.println("short 类型：" + shortNum);

        // int（最常用）—— 4 字节
        int intNum = 2000000000;
        System.out.println("int 类型：" + intNum);

        // long —— 8 字节，末尾要加 L 或 l
        long longNum = 9999999999L;   // 超出 int 范围，必须加 L
        System.out.println("long 类型：" + longNum);

        // float —— 4 字节，末尾要加 F 或 f
        float floatNum = 3.1415926F;   // float 精度约 7 位
        System.out.println("float 类型：" + floatNum);

        // double（最常用）—— 8 字节，精度更高
        double doubleNum = 3.141592653589793;
        System.out.println("double 类型：" + doubleNum);

        // char —— 2 字节，存单个字符（用单引号）
        char charA = 'A';
        char charChinese = '好';
        System.out.println("char 类型（英文）：" + charA);
        System.out.println("char 类型（中文）：" + charChinese);

        // boolean —— 只有 true 和 false
        boolean isJavaFun = true;
        boolean isHard = false;
        System.out.println("boolean 类型（isJavaFun）：" + isJavaFun);
        System.out.println("boolean 类型（isHard）：" + isHard);

        System.out.println();


        // ========== 4. 字符串类型（引用数据类型）演示 ==========
        System.out.println("========== 字符串（引用类型）演示 ==========");
        // String 是引用数据类型，不是基本类型
        String name = "小明";
        String greeting = "你好，" + name + "！";  // 字符串拼接用 +
        System.out.println(greeting);
        System.out.println("字符串长度：" + greeting.length());

        System.out.println();


        // ========== 5. 变量命名规范演示 ==========
        System.out.println("========== 命名规范演示 ==========");

        // ✅ 小驼峰命名法 —— 变量名首字母小写，后续单词首字母大写
        int studentAge = 18;
        String studentName = "张三";
        double finalScore = 95.5;

        System.out.println("学生姓名：" + studentName);
        System.out.println("学生年龄：" + studentAge);
        System.out.println("期末成绩：" + finalScore);
    }

    /**
     * 这是一个自定义方法，演示方法的定义
     * 文档注释可以有 @param、@return 等标签
     *
     * @param name 姓名
     * @return 拼接好的问候字符串
     */
    public static String buildGreeting(String name) {
        // 方法体：实现具体的功能
        return "你好，" + name + "！欢迎学习 Java！";
    }
}
