package io.github.hksm.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.github.hksm.model.Author;

@Repository @Transactional
public class AuthorDao extends GenericDao<Author> {

	public List<Author> findByFirstOrLastName(String firstOrLast) {
		TypedQuery<Author> query = em.createQuery("select a from Author a where concat(a.firstName, a.lastName) LIKE ?1", Author.class);
		query.setParameter(1, "%" + firstOrLast + "%");
		return query.getResultList();
	}
}
