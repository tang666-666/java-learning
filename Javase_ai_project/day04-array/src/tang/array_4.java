package tang;

public class array_4 {
    public static void main(String[] args){
        printArray_1();
        printArray_2();
    }

    public static void printArray_1(){
        String[][] classroom={
                {"张无忌","赵敏","周正若"},
                {"张三丰","宋远桥","殷梨亭"},
                {"汤宇轩","贵芳"}
        };
//        访问行索引
        String[] name= classroom[2];
        for(int i=0;i<name.length;i++){
            System.out.println(name[i]);
        }
        System.out.println(classroom[2][1]);
        System.out.println(classroom.length);
        System.out.println(classroom[2].length);
    }

    public static void printArray_2(){
        String[][] classroom={
                {"张无忌","赵敏","周正若"},
                {"张三丰","宋远桥","殷梨亭"},
                {"汤宇轩","贵芳"}
        };
        for(int i=0;i< classroom.length;i++){
            String[] name=classroom[i];
            for(int j=0;j< name.length;j++){
                System.out.print(name[j] + "\t");
            }
            System.out.println();
        }
        for(int i=0;i< classroom.length;i++){
            for(int j=0;j< classroom[i].length;j++){
                System.out.print(classroom[i][j] + "\t");
            }
        }
    }
}
