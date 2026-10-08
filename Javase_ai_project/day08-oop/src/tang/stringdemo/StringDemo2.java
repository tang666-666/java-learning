package tang.stringdemo;

public class StringDemo2 {
    public static void main(String[] args) {
        System.out.println(getCode(4));
    }

    public static String getCode(int n){
        String str="ABCDEFGHIJKLMNOPQRSTUVWabcdefghigklmnopqrstuvwxyz0123456789";
        String code="";
        for(int i=0;i<n;i++){
            int index=(int)(Math.random()*str.length());
            //根据索引提取字符
            code+=str.charAt(index);
        }
        return code;
    }
}
