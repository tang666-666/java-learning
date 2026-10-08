package tang.demo1exception;

//自定义编译时异常
//1.继承RuntimeException做爸爸
//2.重写Exception的构造器
//3.哪里需要用这个异常返回，哪里就throw这个异常
public class AgeIllegalRuntimeException extends RuntimeException{
    public AgeIllegalRuntimeException() {
    }

    public AgeIllegalRuntimeException(String message) {
        super(message);
    }
}
