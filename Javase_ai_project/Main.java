import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        double width;
        double height;
        Scanner sc=new Scanner(System.in);
        width=sc.nextDouble();
        height= sc.nextDouble();
        Rectangle s=new Rectangle(width,height);
        s.circumference();
        s.area();
    }
}

class Rectangle{
    private double width,height;

    public Rectangle(){
    }

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void circumference(){
        System.out.println("circumference:" + 2*(getHeight()+getWidth()));
    }

    public void area(){
        System.out.println("area:" + getHeight()*getWidth());
    }
}

