package io.github.hksm.dao;

import java.util.List;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.hksm.MybooksApplication;
import io.github.hksm.model.Genre;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MybooksApplication.class)
public class GenreDaoTests {

	@Inject
	GenreDao dao;
	
	@Test
	public void evaluateFindByName() {
		List<Genre> genres = dao.findByName("Fantasy");
		
		Assert.assertEquals("Fantasy", genres.get(0).getName());
	}
	
	@Test
	public void evaluateInsert() {
		Genre genre = new Genre(null, "Mystery");
		
		dao.insert(genre);
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void evaluateInvalidInsert() {
		Genre genre = new Genre();
		
		dao.insert(genre);
	}
}
