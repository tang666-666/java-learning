package tang.demo;

import java.util.Scanner;

public class Movieopertor {
    private Movie[] movies;//记住一个电影对象的数组
    private int count;

    public Movieopertor(Movie[] movies){
        this.movies=movies;
    }

    public void printAllMovies(){
        for(int i=0;i< movies.length;i++){
            Movie m=movies[i];
            System.out.println(m.getId() + "\t" + m.getName() + "\t" + m.getPrice() + "\t" + m.getActor());
        }
    }
    public void searchMoviesById(){
        Scanner sc=new Scanner(System.in);
        int id=sc.nextInt();
        for(int i=0;i< movies.length;i++){
            Movie m=movies[i];
            if(m.getId()==id){
                System.out.println(m.getId() + "\t" + m.getName() + "\t" + m.getPrice() + "\t" + m.getActor());
                return;
            }
        }
        System.out.println("未找到电影");
    }
}
