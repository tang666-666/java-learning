package tang.innerclass;

public class Outer {
    public static String Name="汤宇轩";
    private int age=18;
    //成员内部类：无static修饰，属于外部类的对象持有的
    public class Inner{
        private String name;

        public Inner(){
        }

        public Inner(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void show(){
            System.out.println(Name);
            System.out.println(age);
            System.out.println(this);//自己对象
            System.out.println(Outer.this);//寄生的外部类对象
        }
    }
}
