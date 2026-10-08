package tang.demo6genericity;

import java.util.ArrayList;

/**
 * 泛型与包装类综合演示
 *
 * 本节内容：
 * 1. 泛型的作用——编译阶段约束数据类型，自动检查
 * 2. 泛型类——class 类名<E>
 * 3. 泛型接口——interface 接口名<T>
 * 4. 泛型方法——修饰符 <T> 返回值类型 方法名()
 * 5. 通配符 ?——使用泛型时代表一切类型
 * 6. 泛型上下限——? extends Car（上限）、? super Car（下限）
 * 7. 泛型擦除——编译后泛型消失，恢复成 Object
 * 8. 包装类——基本类型 → 对象类型
 * 9. 自动装箱与自动拆箱
 * 10. 类型转换——String ↔ 数值
 *
 * @author tang
 * @version 1.0
 */
public class GenericDemo7 {

    public static void main(String[] args) {
        // ========== 1. 泛型的作用 ==========
        System.out.println("========== 1. 泛型的作用 ==========");

        // 有泛型：编译阶段约束类型
        ArrayList<String> list = new ArrayList<>();
        list.add("Java");
        list.add("Python");
        // list.add(23);        // ❌ 编译阶段就报错
        // list.add(true);      // ❌ 编译阶段就报错

        // 取出来不用强转
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);     // 不需要 (String) 强转
            System.out.println("  " + s);
        }
        System.out.println("  → 泛型：编译阶段约束 + 自动检查 + 取出免强转");

        System.out.println();


        // ========== 2. 泛型类 ==========
        System.out.println("========== 2. 泛型类 ==========");

        // 自定义泛型类：E 被指定为 String
        MyList<String> strList = new MyList<>();
        strList.add("hello");
        strList.add("world");
        strList.add("Java");
        // strList.add(500);    // ❌ 编译报错
        strList.remove("hello");
        System.out.println("  MyList<String>：" + strList);
        System.out.println("  → 类型变量 E 由使用者指定");

        // 同一个泛型类，指定不同类型
        MyList<Integer> intList = new MyList<>();
        intList.add(100);
        intList.add(200);
        System.out.println("  MyList<Integer>：" + intList);

        System.out.println();


        // ========== 3. 泛型接口 ==========
        System.out.println("========== 3. 泛型接口 ==========");

        // 实现泛型接口时指定具体类型
        StudentData studentData = new StudentData();
        studentData.add(new Student("张三", 18));
        Student s = studentData.query(1);
        System.out.println("  泛型接口 Data<Student> 的实现：" + s);

        TeacherData teacherData = new TeacherData();
        Teacher t = teacherData.query(1);
        System.out.println("  泛型接口 Data<Teacher> 的实现：" + t);

        System.out.println();


        // ========== 4. 泛型方法 ==========
        System.out.println("========== 4. 泛型方法 ==========");

        // 泛型方法：类型变量写在返回值类型前
        String[] names = {"赵敏", "汤宇轩", "张三", "李四"};
        System.out.print("  打印 String 数组：");
        printArray(names);

        Integer[] nums = {10, 20, 30, 40};
        System.out.print("  打印 Integer 数组：");
        printArray(nums);

        Student[] students = {new Student("小明", 20), new Student("小红", 19)};
        System.out.print("  打印 Student 数组：");
        printArray(students);

        // 泛型方法有返回值
        String maxStr = getFirst(names);
        Integer maxNum = getFirst(nums);
        System.out.println("  getFirst(String[]) → " + maxStr);
        System.out.println("  getFirst(Integer[]) → " + maxNum);

        System.out.println();


        // ========== 5. 通配符 ? ==========
        System.out.println("========== 5. 通配符 ? ==========");

        // 通配符：使用泛型时代表一切类型
        ArrayList<String> listA = new ArrayList<>();
        listA.add("A");
        ArrayList<Integer> listB = new ArrayList<>();
        listB.add(1);

        System.out.print("  printAll(ArrayList<String>)：");
        printAll(listA);
        System.out.print("  printAll(ArrayList<Integer>)：");
        printAll(listB);
        System.out.println("  → ? 代表一切类型（使用泛型时用）");

        System.out.println();


        // ========== 6. 泛型上限 ==========
        System.out.println("========== 6. 泛型上限 ? extends Car ==========");

        // 虽然 Xiaomi、BYD 是 Car 的子类，
        // 但 ArrayList<Xiaomi> 和 ArrayList<Car> 没有任何关系！
        ArrayList<Xiaomi> xiaomis = new ArrayList<>();
        xiaomis.add(new Xiaomi());
        xiaomis.add(new Xiaomi());

        ArrayList<BYD> byds = new ArrayList<>();
        byds.add(new BYD());

        ArrayList<Car> cars = new ArrayList<>();
        cars.add(new Car());

        // ? extends Car：接受 Car 及其子类
        System.out.println("  goUpper(ArrayList<Xiaomi>)：");
        goUpper(xiaomis);
        System.out.println("  goUpper(ArrayList<BYD>)：");
        goUpper(byds);
        System.out.println("  goUpper(ArrayList<Car>)：");
        goUpper(cars);

        // ArrayList<Dog> dogs = new ArrayList<>();
        // goUpper(dogs);   // ❌ Dog 不是 Car 的子类，编译报错
        System.out.println("  Dog 不是 Car 子类 → 编译报错，不能传");

        System.out.println();


        // ========== 7. 泛型下限 ==========
        System.out.println("========== 7. 泛型下限 ? super Car ==========");

        ArrayList<Object> objects = new ArrayList<>();
        objects.add(new Object());

        // ? super Car：接受 Car 及其父类
        System.out.println("  goLower(ArrayList<Car>)：");
        goLower(cars);
        System.out.println("  goLower(ArrayList<Object>)：");
        goLower(objects);
        // goLower(xiaomis);   // ❌ Xiaomi 是 Car 的子类，不是父类
        System.out.println("  Xiaomi 是 Car 子类 → 不是父类，不能传");

        System.out.println();


        // ========== 8. 包装类 ==========
        System.out.println("========== 8. 包装类 ==========");

        // 泛型不支持基本类型，必须用包装类
        // ArrayList<int> list = new ArrayList<>();   // ❌ 编译错误
        ArrayList<Integer> intList2 = new ArrayList<>();    // ✅ 用包装类
        intList2.add(123);
        intList2.add(456);
        System.out.println("  泛型不支持基本类型，只能用包装类：ArrayList<Integer>");

        System.out.println("  基本类型 → 包装类 对照：");
        System.out.println("    int → Integer（特殊）、char → Character（特殊）");
        System.out.println("    其他都是首字母大写：byte→Byte, long→Long, double→Double...");

        System.out.println();


        // ========== 9. 自动装箱与自动拆箱 ==========
        System.out.println("========== 9. 自动装箱与自动拆箱 ==========");

        // 手动包装
        Integer it1 = Integer.valueOf(100);
        System.out.println("  手动包装：Integer.valueOf(100) = " + it1);

        // 自动装箱：基本类型 → 包装类
        Integer it2 = 100;
        System.out.println("  自动装箱：Integer it2 = 100 → " + it2);

        // 自动拆箱：包装类 → 基本类型
        int i = it2;
        System.out.println("  自动拆箱：int i = it2 → " + i);

        // 集合中的装箱拆箱
        ArrayList<Integer> nums2 = new ArrayList<>();
        nums2.add(123);             // 自动装箱
        nums2.add(120);
        int rs = nums2.get(1);      // 自动拆箱
        System.out.println("  集合中：list.add(123) 自动装箱，list.get(1) 自动拆箱 → " + rs);

        // ⚠️ 包装类的 == 陷阱
        System.out.println("  ⚠️ 包装类 == 陷阱：");
        Integer a1 = 100;
        Integer b1 = 100;
        System.out.println("    Integer a1=100, b1=100 → a1==b1 是 " + (a1 == b1) + "（缓存范围内 -128~127）");

        Integer a2 = 1000;
        Integer b2 = 1000;
        System.out.println("    Integer a2=1000, b2=1000 → a2==b2 是 " + (a2 == b2) + "（超出缓存范围）");
        System.out.println("    a2.equals(b2) 是 " + a2.equals(b2) + " ← 比较值要用 equals()");

        System.out.println();


        // ========== 10. 类型转换：基本类型 → 字符串 ==========
        System.out.println("========== 10. 类型转换：基本类型 → 字符串 ==========");

        int j = 23;

        // 方式一：静态方法 toString
        String rs1 = Integer.toString(j);
        System.out.println("  Integer.toString(23) → \"" + rs1 + "\"，rs1 + 1 = " + (rs1 + 1));

        // 方式二：对象方法 toString
        Integer i2 = j;
        String rs2 = i2.toString();
        System.out.println("  i2.toString() → \"" + rs2 + "\"，rs2 + 1 = " + (rs2 + 1));

        // 方式三：拼接空串（最简便）
        String rs3 = j + "";
        System.out.println("  j + \"\" → \"" + rs3 + "\"，rs3 + 1 = " + (rs3 + 1));

        System.out.println();


        // ========== 11. 类型转换：字符串 → 数值 ==========
        System.out.println("========== 11. 类型转换：字符串 → 数值 ==========");

        String str = "98";

        // 方式一：parseInt（返回基本类型 int）
        int i3 = Integer.parseInt(str);
        System.out.println("  Integer.parseInt(\"98\") → " + i3 + "，i3 + 2 = " + (i3 + 2));

        // 方式二：valueOf（返回包装类 Integer）
        int i4 = Integer.valueOf(str);
        System.out.println("  Integer.valueOf(\"98\") → " + i4 + "，i4 + 2 = " + (i4 + 2));

        // 小数
        String str2 = "98.8";
        double d1 = Double.parseDouble(str2);
        System.out.println("  Double.parseDouble(\"98.8\") → " + d1 + "，d1 + 2 = " + (d1 + 2));

        // 应用：把用户输入的字符串转成数值
        String userInput = "365";
        int days = Integer.parseInt(userInput);
        System.out.println("  应用：用户输入\"365\"→ " + days + " 天 = " + (days * 24) + " 小时");

        System.out.println();


        // ========== 12. 综合案例：泛型 + 包装类 ==========
        System.out.println("========== 12. 综合案例 ==========");

        // 泛型方法 + 包装类：求任意数值数组的最大值
        Integer[] scores = {85, 92, 78, 96, 88};
        Integer maxScore = getMax(scores);
        System.out.println("  成绩数组最大值：" + maxScore);

        Double[] prices = {9.9, 19.9, 5.5, 29.9};
        Double maxPrice = getMax(prices);
        System.out.println("  价格数组最大值：" + maxPrice);
    }

    /**
     * 泛型方法：打印任意类型数组
     * 类型变量 T 声明在返回值类型前
     *
     * @param arr 任意类型的数组
     * @param <T> 类型变量
     */
    public static <T> void printArray(T[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    /**
     * 泛型方法：返回数组第一个元素
     *
     * @param arr 任意类型数组
     * @param <T> 类型变量
     * @return 第一个元素
     */
    public static <T> T getFirst(T[] arr) {
        return arr[0];
    }

    /**
     * 通配符：接受任意类型的 ArrayList
     *
     * @param list 任意泛型 ArrayList
     */
    public static void printAll(ArrayList<?> list) {
        for (Object o : list) {
            System.out.print(o + " ");
        }
        System.out.println();
    }

    /**
     * 泛型上限：? extends Car
     * 只接受 Car 及其子类的 ArrayList
     *
     * @param cars Car 或其子类的列表
     */
    public static void goUpper(ArrayList<? extends Car> cars) {
        System.out.println("    接受 " + cars.size() + " 辆车（? extends Car）");
    }

    /**
     * 泛型下限：? super Car
     * 只接受 Car 及其父类的 ArrayList
     *
     * @param cars Car 或其父类的列表
     */
    public static void goLower(ArrayList<? super Car> cars) {
        System.out.println("    接受 " + cars.size() + " 个对象（? super Car）");
    }

    /**
     * 泛型方法 + 包装类：求数组最大值
     * 要求 T 必须实现 Comparable 接口才能比较
     *
     * @param arr 任意可比较类型的数组
     * @param <T> 类型变量
     * @return 最大值
     */
    public static <T extends Comparable<T>> T getMax(T[] arr) {
        T max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(max) > 0) {
                max = arr[i];
            }
        }
        return max;
    }
}


// ==================== 泛型类 ====================

/**
 * 自定义泛型类
 * 类型变量 E 由使用者指定
 */
class MyList<E> {
    private ArrayList<E> list = new ArrayList<>();

    public boolean add(E e) {
        return list.add(e);
    }

    public boolean remove(E e) {
        return list.remove(e);
    }

    public E get(int index) {
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}


// ==================== 泛型接口 ====================

/**
 * 自定义泛型接口
 * 类型变量 T 由实现类指定
 */
interface Data<T> {
    void add(T t);
    void delete(T t);
    void update(T t);
    T query(int id);
}

/**
 * 实现泛型接口——指定为 Student
 */
class StudentData implements Data<Student> {
    private ArrayList<Student> list = new ArrayList<>();

    @Override
    public void add(Student s) {
        list.add(s);
    }

    @Override
    public void delete(Student s) {
        list.remove(s);
    }

    @Override
    public void update(Student s) {
        // 简化实现
    }

    @Override
    public Student query(int id) {
        return new Student("张三", 18);
    }
}

/**
 * 实现泛型接口——指定为 Teacher
 */
class TeacherData implements Data<Teacher> {
    @Override
    public void add(Teacher t) { }

    @Override
    public void delete(Teacher t) { }

    @Override
    public void update(Teacher t) { }

    @Override
    public Teacher query(int id) {
        return new Teacher("李老师", "Java");
    }
}


// ==================== 实体类 ====================

class Student {
    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Student{" + name + ", " + age + "岁}";
    }
}

class Teacher {
    private String name;
    private String subject;

    public Teacher(String name, String subject) {
        this.name = name;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Teacher{" + name + ", " + subject + "}";
    }
}


// ==================== 继承体系（泛型上下限） ====================

/**
 * 父类 Car
 */
class Car {
}

/**
 * 子类 Xiaomi——继承 Car
 */
class Xiaomi extends Car {
}

/**
 * 子类 BYD——继承 Car
 */
class BYD extends Car {
}

/**
 * Dog——与 Car 无继承关系
 * 用于演示泛型上限的边界
 */
class Dog {
}
