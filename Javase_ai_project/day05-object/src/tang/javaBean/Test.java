package tang.javaBean;

public class Test {
    public static void main(String[] args){
//        实体的基本作用：创建它的对象，存取数据（封装数据）
        Student s1=new Student();
        s1.setName("波妞");
        s1.setChinese(100);
        s1.setMath(100);
        System.out.println(s1.getName());
        System.out.println(s1.getChinese());
        System.out.println(s1.getMath());

        Student s2=new Student("宗杰",59,80);
        System.out.println(s2.getName());
        System.out.println(s2.getChinese());
        System.out.println(s2.getMath());

//        实体类在开发中的应用场景
//        创建一个学生的操作对象专门负责对学生对象的数据进行业务处理
        StudentOperator operator=new StudentOperator(s2);
        operator.printTotalScore();
        operator.printAverageScore();
    }
}
