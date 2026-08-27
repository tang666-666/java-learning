package tang.enumdemo;

public class Test2 {
    public static void main(String[] args) {
        //第一种是常量做信息标志和分类：但参数值不受约束
        move(0);

        //第二种是枚举做信息标志和分类：参数值受枚举约束
        move2(Direction.LEFT);
    }

    public static void move2(Direction direction){
        switch (direction){
            case Direction.UP:
                System.out.println("向上移动");
                break;
            case Direction.DOWN:
                System.out.println("向下移动");
                break;
            case Direction.LEFT:
                System.out.println("向左移动");
                break;
            case Direction.RIGHT:
                System.out.println("向右移动");
                break;
            default:
                System.out.println("输入有误");
        }
    }

    public static void move(int direction){
        switch (direction){
            case Constant.UP:
                System.out.println("向上移动");
                break;
            case Constant.DOWN:
                System.out.println("向下移动");
                break;
            case Constant.LEFT:
                System.out.println("向左移动");
                break;
            case Constant.RIGHT:
                System.out.println("向右移动");
                break;
            default:
                System.out.println("输入有误");
        }
    }
}
