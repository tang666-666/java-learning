package tang;

/**
 * 性别枚举。
 * <p>
 * 为什么用枚举而不是 String：性别只有「男」「女」两个合法取值。
 * 若用 String，写成 "男"、"male"、"M" 都能编译通过，错误要到运行时才发现；
 * 用枚举则写错就是编译错误，把问题挡在编译期。
 * <p>
 * 为什么枚举项要带参数：把「枚举值」和「界面上显示的文字」绑在一起。
 * 如果只写 MALE/FEMALE，打印时就得在别处写 if 判断转成中文，
 * 那样一旦新增取值，所有 if 都要改。绑在枚举里则只需改这一个文件。
 *
 * @author tang
 * @version 1.0
 */
public enum Gender {

    /** 男性，界面显示为「男」 */
    MALE("男"),

    /** 女性，界面显示为「女」 */
    FEMALE("女");

    /** 该枚举项对应的中文显示文本。final 表示一旦创建就不能改。 */
    private final String label;

    /**
     * 枚举的构造器。
     * 注意：枚举构造器只能是 private，写不写 private 都一样，这里省略不写。
     * 它由 JVM 在加载枚举类时为每一行枚举项各调用一次，程序员无法手动 new。
     *
     * @param label 中文显示文本
     */
    Gender(String label) {
        this.label = label;
    }

    /**
     * 取得该性别的中文显示文本。
     *
     * @return 「男」或「女」
     */
    public String getLabel() {
        return label;
    }
}
