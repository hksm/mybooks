package io.github.hksm.dao;

import java.util.List;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.hksm.MybooksApplication;
import io.github.hksm.model.Author;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MybooksApplication.class)
public class AuthorDaoTests {

	@Inject
	AuthorDao dao;
	
	@Inject
	CountryDao countryDao;
	
	@Test
	public void evaluateInsert() {
		Author author1 = new Author(
					null,
					"George R. R.",
					"Martin",
					new DateTime(1948, 9, 20, 0, 0),
					countryDao.findByCode("US"),
					"georgerrmartin.com"
				);
		
		Author author2 = new Author();
		author2.setFirstName("J. K.");
		author2.setLastName("Rowling");
		
		dao.insert(author1);
		dao.insert(author2);
		
		Assert.assertNotNull(dao.findByFirstOrLastName("martin"));
		Assert.assertNotNull(dao.findByFirstOrLastName("rowling"));
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void evaluateInvalidInsert() {
		Author author = new Author();
		author.setLastName("Testing");
		
		dao.insert(author);
	}
	
	@Test
	public void evaluateRemove() {
		List<Author> list = dao.findAll();
		Long lastId = new Long(list.size());
		dao.remove(lastId);
		
		Assert.assertNull(dao.findOne(lastId));
	}
	
	@Test
	public void evaluateUpdate() {
		Author author = dao.findOne(4L);
		author.setCountryBorn(countryDao.findByName("United Kingdom"));
		
		dao.update(author);
		
		Assert.assertEquals("United Kingdom", dao.findOne(4L).getCountryBorn().getName());
	}
}
