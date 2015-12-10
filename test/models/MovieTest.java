package models;
import static models.Fixtures.movies;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import utils.Serializer;
import utils.XMLSerializer;
import controllers.Data;
import controllers.MovieRecommenderAPI;
public class MovieTest 
{

	Movie movie = new Movie ("GavsFilm", "2018","www.films.co.uk");
	MovieRecommenderAPI movieRecommender;
	@Test
	public void testCreate()
	{
		assertEquals ("GavsFilm",                movie.title);
		assertEquals ("2018",             		   movie.year);
		assertEquals ("www.films.co.uk",              movie.url);   
	}

	@Test
	public void testIds()
	{
		//test id size
		Set<Long> ids = new HashSet<>();
		for (Movie movie:movies)
		{
			ids.add(movie.id);
		}
		assertEquals (movies.length, ids.size());
		//test that each objects id with each other to ensure they are the same
		for(int i = 0; i<movies.length;i++)
		{
			assertEquals(movies[i].id,movies[i].id);
		}
	}

	@Test
	public void testToString()
	{
		assertEquals(movie.toString(),movie.toString());
	}



}



