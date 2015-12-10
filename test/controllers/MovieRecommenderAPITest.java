package controllers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.MovieRecommenderAPI;
import models.User;
import models.Rating;


import static models.Fixtures.users;
import static models.Fixtures.ratings;


public class MovieRecommenderAPITest
{
	private MovieRecommenderAPI movierecommender;

	@Before
	public void setup()
	{
		movierecommender = new MovieRecommenderAPI();
		for (User user : users)
		{
			movierecommender.createUser(user.firstName, user.lastName, user.age, user.gender, user.occupation);
		}
	}

	@After
	public void tearDown()
	{
		movierecommender = null;
	}

	@Test
	public void testUser()
	{
		assertEquals (users.length, movierecommender.getUsers().size());
		movierecommender.createUser("Jenny", "Parker", 21, "female" , "Teacher");
		assertEquals (users.length+1, movierecommender.getUsers().size());
		assertEquals (users[0], movierecommender.getUser(users[0].id));
	}  

	@Test
	public void testUsers()
	{
		assertEquals (users.length, movierecommender.getUsers().size());
		for (User user: users)
		{
			User eachUser = movierecommender.getUser(user.id);
			assertEquals (user, eachUser);
			assertNotSame(user, eachUser);
		}
	}

	@Test
	public void testDeleteUsers()
	{
		assertEquals (users.length, movierecommender.getUsers().size());
		User user = movierecommender.getUser(0l);
		movierecommender.deleteUser(user.id);
		assertEquals (users.length-1, movierecommender.getUsers().size());    
	}  

	@Test
	public void testAddRating()
	{
		User user = movierecommender.getUser(0l);
		Rating rating = movierecommender.addRating(ratings[0].rating, ratings[0].movieId, ratings[0].rating);
		Rating returnedRating = movierecommender.getRating(rating.rating);
		assertEquals(ratings[0],  returnedRating);
		assertNotSame(ratings[0], returnedRating);
	}  


}

