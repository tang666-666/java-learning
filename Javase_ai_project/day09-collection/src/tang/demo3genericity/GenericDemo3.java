package tang.demo3genericity;

public class GenericDemo3 {
    public static void main(String[] args) {
        StudentData studentData = new StudentData();
        studentData.add(new Student());
        studentData.delete(new Student());
        Student s=studentData.query(10);
    }
}
