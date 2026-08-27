package tang.demo;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        //智能家居控制系统
        //1.定义设备类：创建设备对象代表设备
        //2.准备设备对象，放在数组中，代表整个家庭的设备
        JD[] jds=new JD[4];
        jds[0]=new TV("小米电视",true);
        jds[1]=new WashMachine("美的洗衣机",false);
        jds[2]=new Lamp("欧灯",true);
        jds[3]=new Air("美的空调",false);

        //3.为每个设备制定一个开和关的功能，定义一个接口，实现开关功能
        //4.创建智能控制系统对象，控制设备开和关
        SmartHomeControl smartHomeControl=SmartHomeControl.getInstance();
        //5.控制电视剧
        smartHomeControl.control(jds[0]);

        //6.提示用户操作，a.展示全部设备的当前情况。b.让用户选择哪一个操作
        //打印全部设备的开和关的现状
        while(true) {
            smartHomeControl.printAllStatus(jds);
            System.out.println("请您选择要控制的设备：");
            Scanner sc = new Scanner(System.in);
            String command = sc.next();
            switch (command) {
                case "1":
                    smartHomeControl.control(jds[0]);
                    break;
                case "2":
                    smartHomeControl.control(jds[1]);
                    break;
                case "3":
                    smartHomeControl.control(jds[2]);
                    break;
                case "4":
                    smartHomeControl.control(jds[3]);
                    break;
                case "exit":
                    System.out.println("退出APP!");
                    return;
                default:
                    System.out.println("输入有误，请重新输入：");
            }
        }
    }
}
