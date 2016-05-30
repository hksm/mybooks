package io.github.hksm.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.github.hksm.model.Author;
import io.github.hksm.model.Book;

@Repository @Transactional
public class BookDao extends GenericDao<Book> {

	public List<Book> findByTitle(String title) {
		TypedQuery<Book> query = em.createQuery("select b from Book b where b.title LIKE ?1", Book.class);
		query.setParameter(1, "%" + title + "%");
		return query.getResultList();
	}

	public List<Book> findByAuthor(Author author) {
		TypedQuery<Book> query = em.createQuery(
				"select b from Book b join fetch b.authors a where a.id = ?1", Book.class);
		query.setParameter(1, author.getId());
		return query.getResultList();
	}

}
