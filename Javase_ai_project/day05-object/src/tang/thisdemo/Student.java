package tang.thisdemo;

public class Student {
    String name;

    public void print(){
        System.out.println(this);
        System.out.println(this.name);
    }

    public void printHobby(String name){
        System.out.println(this.name + "喜欢" + name);
    }
}
