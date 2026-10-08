import java.util.ArrayList;
import java.util.Scanner;

// 实体层
class Student {
    private String id;
    private String name;
    private int age;

    public Student(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    public String getId() { return id; }
    public int getAge() { return age; }
    public String toString() {
        return "Student{id='" + id + "', name='" + name + "', age=" + age + "}";
    }
}

// 业务接口层
interface StudentService {
    void add(Student stu) throws Exception;
    Student query(String id) throws Exception;
    int total();
}

// 业务实现层
class StudentServiceImpl implements StudentService {
    private ArrayList<Student> stuList = new ArrayList<>();

    public void add(Student stu) throws Exception {
        stuList.add(stu);
    }

    public Student query(String id) throws Exception {
        Student stu=null;
        for (Student stud : stuList) {
            if (stud.getId().equals(id)) {
                stu = stud;
                break;
            }
        }
        return stu;
    }

    public int total() {
        return stuList.size();
    }
}

// 测试层
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentService service = new StudentServiceImpl();
        try {
            int n = sc.nextInt();
            for (int i = 0; i < n; i++) {
                String id = sc.next();
                String name = sc.next();
                int age = sc.nextInt();
                service.add(new Student(id, name, age));
            }
            String searchId = sc.next();
            Student res = service.query(searchId);
            System.out.println("查询学生信息：" + res);
            System.out.println("当前在册学生总人数：" + service.total());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        sc.close();
    }
}