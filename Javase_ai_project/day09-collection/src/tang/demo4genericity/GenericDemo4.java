package tang.demo4genericity;

import tang.demo3genericity.Student;

public class GenericDemo4 {
    public static void main(String[] args) {
        String[] names={"赵敏","汤宇轩","张三","李四","王二"};
        printArray(names);

        Student[] stus=new Student[3];
        printArray(stus);

       Student max = getMax(stus);
       String max2 = getMax(names);
    }

    public static <T> void printArray(T[] names){
    }

    public static <T> T getMax(T[] names){
        return null;
    }
}
