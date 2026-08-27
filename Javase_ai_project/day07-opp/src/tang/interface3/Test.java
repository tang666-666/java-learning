package tang.interface3;

public class Test {
    public static void main(String[] args) {
        //1.定义学生类，创建学生对象，封装学生数据
        //2.准备学生数据，自己先造数据
        Student[] allStudent=new Student[10];
        allStudent[0]=new Student("张三",'男',84);
        allStudent[1]=new Student("李四",'女',100);
        allStudent[2]=new Student("王五",'男',59);
        allStudent[3]=new Student("赵六",'女',78);
        allStudent[4]=new Student("孙七",'男',78);
        allStudent[5]=new Student("周八",'男',54);
        allStudent[6]=new Student("吴九",'男',66);
        allStudent[7]=new Student("郑十",'男',91);
        allStudent[8]=new Student("汤宇轩",'男',90);
        allStudent[9]=new Student("刘梦婷",'女',100);

        //3.提供两套业务实现方案，支持灵活切换
        //定义一个接口（规范思想）
        //定义第一套实现类
        //定义第二套实现类
        ClassDataInter cdi = new ClassDartaInterImpl2(allStudent);
        cdi.printAllStudentInfo();;
        cdi.printAverageScore();
    }
}
