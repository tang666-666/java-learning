package tang.method1;

import java.util.Arrays;

public class Demo2 {
    public static void main(String[] args) {
        test();
    }
    public static void test(){
        Student[] students = new Student[6];
        students[0]= new Student("汤宇轩",18,170,'女');
        students[1]= new Student("张三",24,178,'男');
        students[2]= new Student("李四",38,170,'女');
        students[3]= new Student("王二",19,150,'女');
        students[4]= new Student("赵五",56,170,'男');
        students[5]= new Student("李六",20,179,'女');

        //Arrays.sort(students, (o1, o2)-> o1.getAge()-o2.getAge());

        //Arrays.sort(students, (o1, o2)-> Student.compareByAge(o1,o2));

        //Arrays.sort(students,Student::compareByAge);

        Student t=new Student();

        //Arrays.sort(students,((o1, o2) -> t.compareByHeight(o1,o2)));

        Arrays.sort(students,t::compareByHeight);

        for(int i=0;i< students.length;i++){
            Student s=students[i];
            System.out.println(s);
        }
    }
}
