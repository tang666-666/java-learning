package tang.cycle;

public class cycle_2 {
    public static void main(String[] args){
        double s=1;
        int i=2;
        double item=1;
        while(item>=Math.pow(10,-5)){
            item=1.0/i;
            s+=item;
            i++;
        }
        System.out.printf("s=%.2f",s);
    }
}
