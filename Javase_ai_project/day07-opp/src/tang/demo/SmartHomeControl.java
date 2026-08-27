package tang.demo;
//智能控制系统
public class SmartHomeControl {
    private static final SmartHomeControl smartHomeControl=new SmartHomeControl();

    private SmartHomeControl(){
    }

    public static SmartHomeControl getInstance(){
        return smartHomeControl;
    }

    public void control(JD jd){
        System.out.println(jd.getName() + "状态目前是:" + (jd.isStatus()?"开着":"关闭"));
        System.out.println("开始你的操作。。。。");
        jd.press();
        System.out.println(jd.getName() + "状态已经是:" + (jd.isStatus()?"开着":"关闭"));
    }

    public void printAllStatus(JD[] jds) {
        for(int i=0;i<jds.length;i++){
            JD jd=jds[i];
            System.out.println((i+1) + "," + jd.getName() + "状态目前是:" + (jd.isStatus()?"开着":"关闭"));
        }
    }
}
