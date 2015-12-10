package controllers;
import controllers.MovieRecommenderAPI;
import static org.junit.Assert.*;

import java.io.File;

import models.Rating;
import models.Movie;
import models.User;

import org.junit.Test;

import utils.Serializer;
import utils.XMLSerializer;
import static models.Fixtures.users;
import static models.Fixtures.ratings;
import static models.Fixtures.movies;

public class PersistanceTest
{
	MovieRecommenderAPI movierecommender;

	void populate (MovieRecommenderAPI movierecommender)
	{
		for (User user : users)
		{
			movierecommender.createUser(user.firstName, user.lastName, user.age, user.gender, user.occupation);
		}
		User user1 = movierecommender.getUser(users[0].id);
		Rating rating = movierecommender.addRating(ratings[0].userId, ratings[0].movieId, ratings[0].rating);
		movierecommender.addRating(ratings[1].userId, ratings[1].movieId, ratings[1].rating);
		User user2 = movierecommender.getUser(users[1].id);
		movierecommender.addRating(ratings[2].userId, ratings[2].movieId, ratings[2].rating);
		movierecommender.addRating(ratings[3].userId, ratings[3].movieId, ratings[3].rating);

		for (Movie movie : movies)
		{
			movierecommender.addRating(movie.id, rating.userId, rating.rating);
		}
	}

	@Test
	public void testPopulate()
	{ 
		movierecommender = new MovieRecommenderAPI(null);
		assertEquals(0, movierecommender.getUsers().size());
		populate (movierecommender);

		assertEquals(users.length, movierecommender.getUsers().size());
		assertEquals(2, movierecommender.getUser(users[0].id).ratings.size());
		assertEquals(2, movierecommender.getUser(users[1].id).ratings.size());     
	}

	void deleteFile(String fileName)
	{
		File datastore = new File ("testdatastore.xml");
		if (datastore.exists())
		{
			datastore.delete();
		}
	}

	@Test
	public void testXMLSerializer() throws Exception
	{ 
		String datastoreFile = "testdatastore.xml";
		deleteFile (datastoreFile);

		Serializer serializer = new XMLSerializer(new File (datastoreFile));

		movierecommender = new MovieRecommenderAPI(serializer); 
		populate(movierecommender);
		serializer.write();

		MovieRecommenderAPI movierecommender2 =  new MovieRecommenderAPI(serializer);
		movierecommender2.load();

		assertEquals (movierecommender.getUsers().size(), movierecommender2.getUsers().size());
		for (User user : movierecommender.getUsers())
		{
			assertTrue (movierecommender2.getUsers().contains(user));
		}
		deleteFile ("testdatastore.xml");
	}
} 
  
