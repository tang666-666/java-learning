package tang.javaBean;

public class StudentOperator {
//    必须拿到要处理的学生对象
    private Student s;//用于记住将来要操作的学生对象

    public StudentOperator(Student s){
        this.s=s;
    }

    public void printTotalScore(){
        System.out.println(s.getName() + "的总成绩是：" + (s.getChinese()+s.getMath()));
    }

    public void printAverageScore(){
        System.out.println(s.getName() + "的平均成绩是：" + (s.getMath()+s.getChinese())/2);
    }
}
