package tang.cycle;

public class cycle_4 {
//    验证码
    public static void main(String[] args){
        System.out.println(getCode(4));
        System.out.println(getCode(6));
    }

    public static String getCode(int n){
        String code="";
//        生成几位验证码
        for(int i=0;i<n;i++){
            int type=(int)(Math.random()*3);//0-数字，1-小写字母，2-大写字母
            switch (type){
                case 0:
                    int num=(int)(Math.random()*10);
                    code+=num;
                    break;
                case 1:
                    int num1=(int)(Math.random()*26);
                    char ch1=(char)('a'+num1);
                    code+=ch1;
                    break;
                case 2:
                    int num2=(int)(Math.random()*26);
                    char ch2=(char)('A'+num2);
                    code+=ch2;
                    break;
            }
        }
        return code;
    }
}
