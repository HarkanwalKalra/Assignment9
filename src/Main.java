import java.util.*;

import com.training.assignment9.model.Movie;
import com.training.assignment9.service.MoviesService;

import java.io.File;
import java.sql.Date;

enum Category {
	ACTION,
	COMEDY,
	DRAMA,
	FANTASY,
	HORROR,
	MYSTERY,
	ROMANTIC,
	THRILLER;
}

enum Language {
	HINDI,
	ENGLISH,
	PUNJABI,
	FRENCH,
	GERMAN,
	SPANISH;
}

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		MoviesService moviesService = new MoviesService();
		List <Movie> movieList=new ArrayList<Movie>();
		
		System.out.println("Adding  movies to List...");
		File file = new File("C:/Users/hkhar/eclipse-workspace/assignment9/src/MovieDetails.txt");
		movieList = moviesService.populateMovies(file);
		
		System.out.println("\nAdding all the movies to Database....");
		moviesService.addAllMoviesInDb(movieList);
		 
		System.out.println("\nAdding new  movie to List.....");
		Date date2=Date.valueOf("2012-02-02");
		List <String> cast=new ArrayList<String>();
		cast.add("Hritik Roshan");
		Movie m2=new Movie (5,"Zindagi Na Milegi Dobara",Category.DRAMA.toString(),Language.HINDI.toString(),date2,cast,4.9d,324244d);
		movieList.add(m2);
		moviesService.addMovie(m2,null);
		
		System.out.println("\nSerializing movies....");
		moviesService.serializeMovies(movieList,"movieListObject.txt");
		
		System.out.println("\nDeserializing movies.....");
		List <Movie> mlist = moviesService.deserializeMovie("movieListObject.txt");
		for (Movie m:mlist)
		{
			System.out.println(m.toString());
		}
		
		System.out.println("\n movies released in year 2020....");
		List <Movie> result =moviesService.getMoviesRealeasedInYear(movieList,2020);
		for (Movie m:result)
		{
			System.out.println(m.toString());
		}
		
		System.out.println("\n movies by Actor name...");
		result = moviesService.getMoviesByActor(movieList,"hritik roshan","ammy virk");
		for (Movie m:result)
		{
			System.out.println(m.toString());
		}
		
		System.out.println("\nUpdate Movie rating and Total business Done for movie.....");
		moviesService.updateRatings(m2,2.2,movieList);
		moviesService.updateBusiness(m2,200000,movieList);
		for (Movie mo: movieList)
		{
			if(mo.equals(m2))
			{
			System.out.println(mo.toString());
			}
		}
		
		System.out.println("\n movies by Language and sorted by total bussiness done in decending order......");
//		Map<String,Set<Movie>> map=new HashMap<String,Set<Movie>>();
		moviesService.businessDone(movieList, 47000d);
	}
	
}