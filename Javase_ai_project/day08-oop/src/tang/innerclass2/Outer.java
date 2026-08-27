package tang.innerclass2;

public class Outer {
    public static String Name="汤宇轩";
    private int age;
    //静态内部类:属于外部类本身持有
    public static class Inner{
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
            System.out.println("show方法");
            System.out.println(Name);
        }
    }
}
