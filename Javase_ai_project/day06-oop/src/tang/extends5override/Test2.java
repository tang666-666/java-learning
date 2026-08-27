package tang.extends5override;

public class Test2 {
    public static void main(String[] args) {
        Student s=new Student("汤宇轩",'男',19);
        System.out.println(s);
        System.out.println(s.toString());
    }
}

class Student{
    private String name;
    private char sex;
    private int age;

    public Student(){

    }

    public Student(String name, char sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                '}';
    }
}
