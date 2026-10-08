package tang.demo1exception;

public class ExceptionDemo3 {
    public static void main(String[] args) {
        System.out.println("程序开始");
        try {
            save(300);
            System.out.println("成功了！");
        } catch (AgeIllegalException e) {
            e.printStackTrace();
            System.out.println("失败了！");
        }
        System.out.println("程序结束");
    }
    //需求：只要年龄小于1岁或大于200岁就是一个年龄非法异常
    public static void save(int age) throws AgeIllegalException {
        if(age<1||age>200){
            //年龄非法：抛出去一个异常返回
            throw new AgeIllegalException("年龄非法");
        }
        else{
            System.out.println("年龄合法");
            System.out.println("保存年龄：" +age);
        }
    }
}
