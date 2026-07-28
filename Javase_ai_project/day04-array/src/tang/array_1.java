package tang;

public class array_1 {
    public static void main(String[] args) {
        nameCall();
    }
    public static void nameCall(){
        String[] name ={"张三","李四","汤宇轩","王二"};
        int index=(int)(Math.random()*name.length);
        System.out.println(name[index]);
    }
}
