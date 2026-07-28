package tang.pocket;

public class method_1 {

    public static void main(String[] args) {
//        掌握方法的定义和调用
        System.out.println(max(1,2));
        System.out.println(sum(1,2));
        printhelloworld();
        System.out.println(getCode(4));
    }
//    求两数字的最大值
    public static int max(int a,int b){
        int max=a>b?a:b;
        return max;
    }
//    求两数字的和
    public static int sum(int a,int b){
        return a+b;
    }
//    打印hello world
    public static void printhelloworld(){
        System.out.println("Hello world!");
    }
//    生成指定位数的验证码
    public static String getCode(int len){
        String code="";
        for(int i=0;i<len;i++){
            int num=(int)(Math.random()*10);
            code+=num;
        }
        return code;
    }
}
