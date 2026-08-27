package tang.interface3;

public class ClassDartaInterImpl2 implements ClassDataInter{

    private Student[] students;//记录数组

    public ClassDartaInterImpl2(){
    }

    public ClassDartaInterImpl2(Student[] students) {
        this.students = students;
    }

    @Override
    public void printAllStudentInfo() {
        System.out.println("全班信息如下：");
        int maleCount=0;
        int famaleCount=0;
        for(int i=0;i< students.length;i++){
            Student s=students[i];
            if(s.getSex()=='男'){
                maleCount++;
            }
            else{
                famaleCount++;
            }
            System.out.println(s.getName() + " " + s.getSex() + " " + s.getScore());
        }
        System.out.println("男生人数为：" + maleCount);
        System.out.println("女生人数为：" + famaleCount);
    }

    @Override
    public void printAverageScore() {
        double sum=0;
        double max=students[0].getScore();
        double min=students[0].getScore();
        for(int i=0;i<students.length;i++){
            Student s=students[i];
            if(s.getScore()>max){
                max=s.getScore();
            }
            if(s.getScore()<min){
                min=s.getScore();
            }
            sum+=s.getScore();
        }
        System.out.println("全班最高分为：" + max);
        System.out.println("全班最低分为：" + min);
        System.out.println("全班平均分为：" + (sum-max-min)/ (students.length-2));
    }

}
