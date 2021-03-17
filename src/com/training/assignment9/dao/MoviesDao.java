package com.training.assignment9.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.training.assignment9.model.Movie;
import com.training.assignment9.util.DBConnectionUtil;

public class MoviesDao {
	
	Connection connection  = DBConnectionUtil.getConnection();
	public boolean addAllMoviesInDb(List<Movie> movies) {
		PreparedStatement preparedStatement = null;
		
		for(Movie movie : movies) {
			try {
				preparedStatement = connection.prepareStatement("insert into movies values (?,?,?,?,?,?,?)");
				preparedStatement.setInt(1, movie.getMovieId());
				preparedStatement.setString(2, movie.getMovieName());
				preparedStatement.setString(3, movie.getMovieType());
				preparedStatement.setString(4, movie.getLanguage());
				preparedStatement.setDate(5, movie.getReleaseDate());
				preparedStatement.setDouble(6, movie.getRating());
				preparedStatement.setDouble(7, movie.getTotalBusinessDone());
				preparedStatement.executeUpdate();
				
				for(String cast:movie.getCasting()) {
				     preparedStatement = connection.prepareStatement("insert into casting values (?,?)");
				     preparedStatement.setInt(1,movie.getMovieId());
				     preparedStatement.setString(2, cast);
				     preparedStatement.executeUpdate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public void addMovie(Movie movie) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("insert into movies values (?,?,?,?,?,?,?)");
			preparedStatement.setInt(1, movie.getMovieId());
			preparedStatement.setString(2, movie.getMovieName());
			preparedStatement.setString(3, movie.getMovieType());
			preparedStatement.setString(4, movie.getLanguage());
			preparedStatement.setDate(5, movie.getReleaseDate());
			preparedStatement.setDouble(6, movie.getRating());
			preparedStatement.setDouble(7, movie.getTotalBusinessDone());
			preparedStatement.executeUpdate();
			for(String cast:movie.getCasting()) {
			     preparedStatement = connection.prepareStatement("insert into casting values (?,?)");
			     preparedStatement.setInt(1,movie.getMovieId());
			     preparedStatement.setString(2, cast);
			     preparedStatement.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateRatings(Movie movie,double rating) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("update movies set rating = ? where movieId = ?");
			preparedStatement.setDouble(1, rating);
			preparedStatement.setInt(2, movie.getMovieId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateBusiness(Movie movie, double amount) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("update movies set totalBusinessdone = ? where movieId = ?");
			preparedStatement.setDouble(1, amount);
			preparedStatement.setInt(2, movie.getMovieId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
