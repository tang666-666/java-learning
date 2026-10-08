package tang.lambda;

import tang.innerclass3.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;

public class LambdaDemo2 {
    public static void main(String[] args) {
        Student[] students = new Student[6];
        students[0]= new Student("汤宇轩",18,170,'女');
        students[1]= new Student("张三",24,178,'男');
        students[2]= new Student("李四",38,170,'女');
        students[3]= new Student("王二",19,150,'女');
        students[4]= new Student("赵五",56,170,'男');
        students[5]= new Student("李六",20,179,'女');
        //public static void sort(T[] a, Comparator<T> c)
        //参数一：需要排序的数组。参数二：需要给sort方法声明一个比较器对象（指定排序的规则）
        //sort方法会调用匿名内部类对象的compare方法，对数组中的学生对象进行两两比较，从而实现排序
//        Arrays.sort(students, new Comparator<Student>() {
//            @Override
//            public int compare(Student o1, Student o2) {
//                if(o1.getAge()>o2.getAge()){
//                    return 1;
//                }
//                else if(o1.getAge()<o2.getAge()) {
//                    return -1;
//                }
//                return 0;
//            }
//        });

//        Arrays.sort(students, (Student o1, Student o2)-> {
//            if(o1.getAge()>o2.getAge()){
//                return 1;
//            }
//            else if(o1.getAge()<o2.getAge()) {
//                return -1;
//            }
//            return 0;
//        });

        Arrays.sort(students, (o1,o2)-> o1.getAge()-o2.getAge());

        for(int i=0;i< students.length;i++){
            Student s=students[i];
            System.out.println(s);
        }
    }
}
