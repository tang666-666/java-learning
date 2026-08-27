package tang.polymophsmdemo;

/**
 * 多态综合演示
 *
 * 本节内容：
 * 1. 对象多态、行为多态
 * 2. 多态的三个前提（继承/实现 + 父类引用子类对象 + 方法重写）
 * 3. 成员变量不谈多态（编译看左，运行也看左）
 * 4. 多态的好处（解耦合、父类作形参接受一切子类）
 * 5. 多态的问题（不能使用子类独有功能）
 * 6. 类型转换（向上转型 / 向下转型）
 * 7. instanceof 关键字（强转前判断真实类型）
 *
 * @author tang
 * @version 1.0
 */
public class PolymorphismDemo {

    public static void main(String[] args) {
        // ========== 1. 对象多态与行为多态 ==========
        System.out.println("========== 1. 对象多态与行为多态 ==========");

        // 对象多态：父类引用指向不同子类对象
        Animal a1 = new Wolf();
        Animal a2 = new Tortoise();

        // 行为多态：同一个 run() 方法，不同表现
        System.out.print("a1.run() → ");
        a1.run();       // 狼跑的很快

        System.out.print("a2.run() → ");
        a2.run();       // 乌龟跑的很慢

        System.out.println();


        // ========== 2. 成员变量不谈多态 ==========
        System.out.println("========== 2. 成员变量不谈多态 ==========");

        // 成员变量：编译看左边，运行也看左边
        System.out.println("a1.name = " + a1.name);   // "动物"（父类的 name）
        System.out.println("a2.name = " + a2.name);   // "动物"（父类的 name）

        // 对比：方法看右边（多态），变量看左边（不多态）
        System.out.println("方法 run()：编译看左，运行看右（多态生效）");
        System.out.println("变量 name：编译看左，运行也看左（多态不生效）");

        System.out.println();


        // ========== 3. 多态的好处 1：解耦合 ==========
        System.out.println("========== 3. 多态好处：解耦合 ==========");

        // 右边对象解耦合：换子类对象，左边代码不用改
        Animal a3 = new Wolf();     // 想换成乌龟？只改右边
        System.out.print("a3.run() → ");
        a3.run();

        a3 = new Tortoise();        // 直接换右边对象
        System.out.print("a3 换对象后 run() → ");
        a3.run();

        System.out.println();


        // ========== 4. 多态的好处 2：父类作形参 ==========
        System.out.println("========== 4. 多态好处：父类作形参 ==========");

        // 一个方法接受一切子类对象
        System.out.println("传入 Wolf：");
        go(new Wolf());

        System.out.println("传入 Tortoise：");
        go(new Tortoise());

        // 新增子类不用改 go 方法（拓展性强）
        System.out.println("传入 Bird（未来新增的子类）：");
        go(new Bird());

        System.out.println();


        // ========== 5. 多态的问题：不能使用子类独有功能 ==========
        System.out.println("========== 5. 多态的问题 ==========");

        Animal a4 = new Tortoise();
        a4.run();              // ✅ 父类有 run()，可以调用
        System.out.println("  a4.run() ✅（父类也有此方法）");

        // a4.shrinkHead();    // ❌ 编译错误：父类 Animal 没有 shrinkHead()
        System.out.println("  a4.shrinkHead() ❌（编译报错：父类没有该方法）");
        System.out.println("  → 解决：强转回子类类型");

        System.out.println();


        // ========== 6. 类型转换：向上转型 / 向下转型 ==========
        System.out.println("========== 6. 类型转换 ==========");

        // 向上转型（自动）：子类 → 父类
        Animal a5 = new Wolf();         // 自动向上转型
        System.out.println("向上转型：Animal a5 = new Wolf() ✅ 自动");

        // 向下转型（强制）：父类 → 子类
        Wolf w = (Wolf) a5;             // 强制向下转型
        System.out.println("向下转型：Wolf w = (Wolf) a5 ✅ 强制");
        w.eatSheep();                   // 转回 Wolf 后可以调用独有功能

        System.out.println();


        // ========== 7. 强制类型转换的风险 ==========
        System.out.println("========== 7. 强制类型转换的风险 ==========");

        Animal a6 = new Wolf();         // 真实类型是 Wolf

        // 编译阶段：有继承关系就能强转，不报错
        // 运行阶段：真实类型 Wolf != Tortoise，报 ClassCastException
        System.out.println("Animal a6 = new Wolf()，真实类型是 Wolf");
        System.out.println("尝试强转成 Tortoise（编译通过，运行报错）：");

        try {
            Tortoise t = (Tortoise) a6;     // 编译通过，运行报错
            t.shrinkHead();
        } catch (ClassCastException e) {
            System.out.println("  ⚠️ ClassCastException：狼不能强转成乌龟！");
        }

        System.out.println();


        // ========== 8. instanceof 关键字（推荐做法） ==========
        System.out.println("========== 8. instanceof 关键字 ==========");

        // 强转前先判断真实类型
        System.out.println("调用 goSafe(new Wolf())：");
        goSafe(new Wolf());

        System.out.println("调用 goSafe(new Tortoise())：");
        goSafe(new Tortoise());

        System.out.println("调用 goSafe(new Bird())：");
        goSafe(new Bird());

        System.out.println();


        // ========== 9. 综合案例：多态数组 ==========
        System.out.println("========== 9. 综合案例：多态数组 ==========");

        // 父类数组可以存放各种子类对象
        Animal[] animals = {
                new Wolf(),
                new Tortoise(),
                new Bird(),
                new Wolf(),
                new Tortoise()
        };

        System.out.println("遍历动物数组（多态数组）：");
        for (Animal animal : animals) {
            animal.run();       // 多态：每个对象表现自己的行为
        }

        // 统计各种动物数量（instanceof 应用）
        int wolfCount = 0, tortoiseCount = 0, birdCount = 0;
        for (Animal animal : animals) {
            if (animal instanceof Wolf) {
                wolfCount++;
            } else if (animal instanceof Tortoise) {
                tortoiseCount++;
            } else if (animal instanceof Bird) {
                birdCount++;
            }
        }
        System.out.println("狼：" + wolfCount + " 只，乌龟：" + tortoiseCount + " 只，鸟：" + birdCount + " 只");
    }

    /**
     * 多态好处：父类类型作形参，接受一切子类对象
     * @param a 任意动物对象
     */
    public static void go(Animal a) {
        System.out.println("  开始");
        a.run();
        // a.shrinkHead();  // ❌ 多态下不能调用子类独有功能
    }

    /**
     * 使用 instanceof 安全地调用子类独有功能
     * @param a 任意动物对象
     */
    public static void goSafe(Animal a) {
        System.out.println("  开始");
        a.run();

        // 强转前先判断真实类型，避免 ClassCastException
        if (a instanceof Wolf) {
            Wolf w = (Wolf) a;
            w.eatSheep();           // 狼的独有功能
        } else if (a instanceof Tortoise) {
            Tortoise t = (Tortoise) a;
            t.shrinkHead();         // 乌龟的独有功能
        } else if (a instanceof Bird) {
            Bird b = (Bird) a;
            b.fly();                // 鸟的独有功能
        } else {
            System.out.println("  未知动物类型");
        }
    }
}


// ==================== 动物类体系 ====================

/**
 * 父类 Animal
 */
class Animal {
    String name = "动物";       // 父类成员变量

    public void run() {
        System.out.println("动物会跑");
    }
}

/**
 * 子类 Wolf
 */
class Wolf extends Animal {
    String name = "狼";         // 子类成员变量（不是重写）

    @Override
    public void run() {
        System.out.println("狼跑的很快");
    }

    // 狼的独有功能
    public void eatSheep() {
        System.out.println("  狼吃羊（独有功能）");
    }
}

/**
 * 子类 Tortoise
 */
class Tortoise extends Animal {
    String name = "乌龟";

    @Override
    public void run() {
        System.out.println("乌龟跑的很慢");
    }

    // 乌龟的独有功能
    public void shrinkHead() {
        System.out.println("  乌龟缩头（独有功能）");
    }
}

/**
 * 子类 Bird——演示"未来新增的子类"
 * 新增子类后，go() 方法不用改，体现多态的拓展性
 */
class Bird extends Animal {
    @Override
    public void run() {
        System.out.println("小鸟在地上跳");
    }

    // 鸟的独有功能
    public void fly() {
        System.out.println("  小鸟飞翔（独有功能）");
    }
}
