package tang.demo7list;

import java.util.ArrayList;
import java.util.LinkedList;

public class ListDemo2 {
    public static void main(String[] args) {
        LinkedList<String> queue=new LinkedList<>();

        //入队
        queue.addLast("张三");
        queue.addLast("李四");
        queue.addLast("王五");
        queue.addLast("赵六");
        System.out.println(queue);

        //出队
        System.out.println(queue.removeFirst());
        System.out.println(queue.removeFirst());
        System.out.println(queue);

        System.out.println("=========================");

        LinkedList<String> stack=new LinkedList<>();

        //压栈
        stack.addFirst("第一颗子弹");
        stack.addFirst("第二颗子弹");
        stack.addFirst("第三颗子弹");
        stack.addFirst("第四颗子弹");
        System.out.println(stack);

        //出栈
        System.out.println(stack.removeFirst());
        System.out.println(stack.removeFirst());
        System.out.println(stack);
    }
}
