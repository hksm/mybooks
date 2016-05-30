package io.github.hksm.dao;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.hksm.MybooksApplication;
import io.github.hksm.model.Author;
import io.github.hksm.model.Book;
import io.github.hksm.model.Genre;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MybooksApplication.class)
public class BookDaoTests {

	@Inject
	BookDao dao;
	
	@Inject
	AuthorDao authorDao;
	
	@Inject
	GenreDao genreDao;
	
	@Inject
	LanguageDao langDao;
	
	@Test
	public void evaluateInsert() {
		Set<Author> authors = new HashSet<Author>();
		authors.add(authorDao.findByFirstOrLastName("Rowling").get(0));
		Set<Genre> genres = new HashSet<Genre>();
		genres.addAll(genreDao.findAll());
		
		Book book = new Book(
				null,
				"Harry Potter and the Sorcerer's Stone",
				authors,
				genres,
				langDao.findByName("English"),
				new DateTime(1997, 6, 26, 0, 0),
				"Scholastic",
				new Integer(310),
				"https://upload.wikimedia.org/wikipedia/en/b/bf/Harry_Potter_and_the_Sorcerer's_Stone.jpg");
		
		dao.insert(book);
		
		Assert.assertNotNull(dao.findByAuthor(authorDao.findByFirstOrLastName("Rowling").get(0)));
	}
	
	@Test
	public void evaluateUpdate() {
		Book book = dao.findByTitle("harry potter").get(0);
		Set<Author> authors = book.getAuthors();
		Author newAuthor = authorDao.findByFirstOrLastName("martin").get(0);
		authors.add(newAuthor);
		book.setAuthors(authors);
		
		dao.update(book);
		
		Assert.assertEquals(2, dao.findByTitle("harry potter").get(0).getAuthors().size());
	}
}
