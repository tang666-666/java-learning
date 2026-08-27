package tang.object;

public class student_text {
    public static void main(String[] args){
        student s1=new student();
        s1.name="波妞";
        s1.chinese=100;
        s1.math=100;
        s1.printAllScore();
        s1.printAverageScore();

        student s2=new student();
        s2.name="宗介";
        s2.chinese=50;
        s2.math=100;
        s2.printAllScore();
        s2.printAverageScore();
    }
}
