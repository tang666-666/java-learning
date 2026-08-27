package tang.capsulation;

public class Student {
    String name;
//    1.如何合理隐藏，使用private关键字修饰成员变量，就只能在本类中被直接访问，其他任何地方不能直接访问
    private int age;
    private double chinese;
    private double math;

//    2.如何暴露，使用public修饰的get和set方法合理暴露
//    成员变量的取值和赋值

    public void setAge(int age){
        if(age>0&&age<200){
            this.age=age;
        }
        else{
            System.out.println("数值不合理");
        }
    }

    public int getAge(){
        return age;
    }

    public void printAllScore(){
        System.out.println(name + "的总成绩是:" + (chinese+math));
    }

    public void printAverageScore(){
        System.out.println(name + "的平均成绩是:" + (chinese+math)/2);
    }
}
