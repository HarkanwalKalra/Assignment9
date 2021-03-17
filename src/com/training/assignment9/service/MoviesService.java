package com.training.assignment9.service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.training.assignment9.dao.MoviesDao;
import com.training.assignment9.model.Movie;

import java.util.Scanner;


public class MoviesService {
	MoviesDao obj = new MoviesDao();
	public List<Movie> populateMovies(File file)
	{
		List<Movie> movieList = new ArrayList<>();
		try {
			Scanner sc = new Scanner(file);
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/u");
			while(sc.hasNext()) {
				String data[] = sc.nextLine().split(",");
				Movie movie = new Movie(Integer.parseInt(data[0]),data[1],data[2],data[3],Date.valueOf(LocalDate.parse(data[4],dateTimeFormatter)),List.of(data[5].split("-")),Double.parseDouble(data[6]),Double.parseDouble(data[7]));
				movieList.add(movie);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return movieList;
	}
	
	public boolean addAllMoviesInDb(List<Movie> movies) {
		return obj.addAllMoviesInDb(movies);
	}
	
	public void addMovie(Movie movie,List<Movie> movies) {
		movies.add(movie);
		obj.addMovie(movie);
	}
	
	public void serializeMovies(List<Movie> movies, String fileName) {
		File file = new File(fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(movies);
			oos.close();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Movie> deserializeMovie(String filename){
		List <Movie> movies = new ArrayList<>();
		File file = new File(filename);
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			movies = (List<Movie>) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movies;
	}
	
	public List<Movie> getMoviesRealeasedInYear(List<Movie> movies,int year){
		List <Movie> moviesReleasedInYear = new ArrayList<>();
		for(Movie m:movies) {
			LocalDate date = m.getReleaseDate().toLocalDate();
			if(date.getYear() == year)
				moviesReleasedInYear.add(m);
		}
		return moviesReleasedInYear;
	}
	
	public List<Movie> getMoviesByActor(List<Movie> movies, String...actorNames){
		List<Movie> moviesByActor =  new ArrayList<>();
		for(Movie m:movies) {
			for(String actor:actorNames) {
				if(m.getCasting().contains(actor)) {
					moviesByActor.add(m);
					break;
				}
			}
		}
		return moviesByActor;
	}
	
	public void updateRatings(Movie movie,double rating,List<Movie> movies) {
		if(movies.contains(movie)) {
			movie.setRating(rating);
			obj.updateRatings(movie, rating);
			System.out.println("Rating Updated");
		}
		else {
			System.out.println("Movie does not exist in the list.");
		}
	}
	
	public void updateBusiness(Movie movie, double amount,List<Movie> movies) {
		if(movies.contains(movie)) {
			movie.setTotalBusinessDone(amount);
			obj.updateBusiness(movie, amount);
			System.out.println("Business Updated");
		}
		else {
			System.out.println("Movie does not exist in the list.");
		}
	}
	
	public Map<String,Set<Movie>> businessDone(List<Movie> movies,double amount){
		Set <Movie> movieSet = new TreeSet<>();
		Map <String,Set<Movie>> movieMap = new HashMap<>();
		for(Movie movie:movies) {
			if(movie.getTotalBusinessDone() > amount) {
				movieSet.add(movie);
				if(movieMap.containsKey(movie.getLanguage())) {
					movieMap.get(movie.getLanguage()).add(movie);
				}
				else {
					movieMap.put(movie.getLanguage(), movieSet);
				}
			}
		}
		
		return movieMap;
	}
	
}
