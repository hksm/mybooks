package io.github.hksm.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.github.hksm.model.Genre;

@Repository @Transactional
public class GenreDao extends GenericDao<Genre> {

	public List<Genre> findByName(String name) {
		TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.name = ?1", Genre.class);
		query.setParameter(1, name);
		return query.getResultList();
	}

}
