package tang.demo1exception;

public class ExceptionDemo2 {
    public static void main(String[] args) {
        System.out.println("程序开始执行");
        try {
            System.out.println(div(10,0));
            System.out.println("执行成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("执行失败");
        }
        System.out.println("程序结束");
    }

    public static int div(int a,int b) throws Exception {
        if(b==0){
            System.out.println("除数不能为零");
            //返回一个异常给上层调用者，返回的异常还能告知上层底层是执行成功还是失败
            throw new Exception("除数不能为零");
        }
        int result=a/b;
        return result;
    }
}
