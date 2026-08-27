package tang.extends6constructor;

public class Text2 {
    public static void main(String[] args) {
        Teacher t= new Teacher("汤宇轩","java",'男');
        System.out.println(t.getName());
        System.out.println(t.getSkill());
        System.out.println(t.getSex());
    }
}
