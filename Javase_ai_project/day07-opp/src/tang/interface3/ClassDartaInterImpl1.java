package tang.interface3;

public class ClassDartaInterImpl1 implements ClassDataInter{

    private Student[] students;//记录数组

    public ClassDartaInterImpl1(){
    }

    public ClassDartaInterImpl1(Student[] students) {
        this.students = students;
    }

    @Override
    public void printAllStudentInfo() {
        System.out.println("全班信息如下 ：");
        for(int i=0;i<students.length;i++){
            Student s=students[i];
            System.out.println(s.getName() + " " + s.getSex() + " " + s.getScore());
        }
    }

    @Override
    public void printAverageScore() {
        double sum=0;
        for(int i=0;i<students.length;i++){
            Student s=students[i];
            sum+=s.getScore();
        }
        System.out.println("全班平均分为：" + sum/ students.length);
    }
}
