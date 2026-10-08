package tang.demo8test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieService {
    //4.准备一个集合容器：存储全部上架的电影数据
    private static List<Movie> movies=new ArrayList<Movie>();

    private static Scanner sc=new Scanner(System.in);

    public void start() {
        //3.准备操作界面:GUI界面/cmd命令
        while (true) {
            System.out.println("====电影信息操作系统====");
            System.out.println("1、上架");
            System.out.println("2、下架某个电影");
            System.out.println("3、查询某个电影");
            System.out.println("4、封杀某个明星");
            System.out.println("5、退出");
            System.out.println("6、展示全部电影");
            System.out.println("7、修改某个电影");
            System.out.println("====电影信息操作系统====");
            System.out.println("请您输入操作命令");
            String command=sc.next();
            switch (command) {
                case "1":
                    //上架
                    addMovie();
                    break;

                case "2":
                    //下架某个电影
                    deleteMovie();
                    break;
                case "3":
                    //查询某个电影
                    queryMovie();
                    break;
                case "4":
                    //封杀某个明星
                    deleteStar();
                    break;

                case "5":
                    System.out.println("退出成功");
                    return;

                case "6":
                    //展示全部电影
                    queryAllMovies();
                    break;

                case "7":
                    //修改某个电影
                    updateMovies();
                    break;

                default:
                    System.out.println("您的命令有问题");
            }
        }
    }

    /**
     * 下架某个电影
     */
    private void deleteMovie() {
        System.out.println("==========下架电影==========");
        System.out.println("请您输入要下架的电影名称");
        String movieName=sc.next();
        for(int i=0;i<movies.size();i++){
            if(movieName.equals(movies.get(i).getName())){
                movies.remove(i);
                i--;
                System.out.println("电影下架成功！");
            }
        }
        queryAllMovies();
    }

    /**
     * 修改某个电影
     */
    private void updateMovies() {
        System.out.println("==========修改电影==========");
        System.out.println("请您输入要修改的电影名称");
        String movieName=sc.next();
        for (Movie movie:movies){
            if(movieName.equals(movie.getName())){
                while (true) {
                    System.out.println("请您输入需要进行的操作");
                    System.out.println("1、修改评分");
                    System.out.println("2、修改演员");
                    System.out.println("3、修改售价");
                    System.out.println("4、退出");
                    String command=sc.next();
                    switch (command) {
                        case "1":
                            //修改评分
                            updateMovieScore(movie);
                            break;

                        case "2":
                            //修改演员
                            updateMovieActor(movie);
                            break;

                        case "3":
                            //修改售价
                            updateMoviePrice(movie);
                            break;

                        case "4":
                            //退出
                            return;

                        default:
                            System.out.println("您的命令有问题");
                    }
                }
            }
            else{
                System.out.println("没有找到需要修改的电影");
            }
        }
    }

    //修改电影价格
    private void updateMoviePrice(Movie movie) {
        System.out.println("请输入你更改后的价格：");
        double moviePrice=sc.nextDouble();
        movie.setPrice(moviePrice);
        System.out.println("价格更改成功！");
        System.out.println(movie.toString());
    }

    //修改电影演员
    private void updateMovieActor(Movie movie) {
        System.out.println("请输入你更改后的演员：");
        String actorName=sc.next();
        movie.setActor(actorName);
        System.out.println("演员更改成功！");
        System.out.println(movie.toString());
    }

    //修改电影评分
    private void updateMovieScore(Movie movie) {
        System.out.println("请输入你更改后的评分：");
        double movieScore=sc.nextDouble();
        movie.setScore(movieScore);
        System.out.println("评分更改成功！");
        System.out.println(movie.toString());
    }

    /**
     *展示全部电影
     */
    private void queryAllMovies() {
        System.out.println("==========展示全部电影==========");
        for (Movie movie:movies) {
            System.out.println(movie.toString());
        }
    }

    /**
     * 封杀某个明星
     */
    private void deleteStar() {
        System.out.println("==========封杀明星==========");
        System.out.println("请您输入要封杀的明星：");
        String star=sc.next();
        for(int i=0;i<movies.size();i++){
            Movie movie=movies.get(i);
            if(movie.getActor().contains(star)){
                movies.remove(movies.get(i));
                i--;
            }
        }
        System.out.println("封杀成功！");
        queryAllMovies();
    }

    /**
     * 根据电影名称查询某部电影对象展示出来
     */
    private void queryMovie() {
        System.out.println("==========查询电影==========");
        System.out.println("请您输入电影名称：");
        String name=sc.next();
        //根据电影名称查询电影对象返回，展示这个对象数据
        Movie movie=queryMovieByName(name);
        if(movie!=null){
            System.out.println(movie.toString());
        }
        else {
            System.out.println("没有找到这个电影");
        }
    }

    //根据电影名称查询电影对象返回
    public Movie queryMovieByName(String name){
        for (Movie movie:movies){
            if(movie.getName().equals(name)){
                return movie;
            }
        }
        return null;
    }

    /**
     * 上架电影
     */
    private void addMovie() {
        System.out.println("==========上架电影==========");
        //每点击一次上架电影，其实就是新增一部电影，每部电影是一个电影对象封装数据的
        //1、创建电影对象，封装这部电影信息
        Movie movie=new Movie();
        //2、给电影对象注入数据

        System.out.println("请您输入电影名称：");
        movie.setName(sc.next());
        System.out.println("请您输入主演：");
        movie.setActor(sc.next());
        System.out.println("请您输入电影价格：");
        movie.setPrice(sc.nextDouble());
        System.out.println("请您输入电影的评分：");
        movie.setScore(sc.nextDouble());

        //3、把电影对象添加到集合中
        movies.add(movie);
        System.out.println("上架成功");
    }
}
