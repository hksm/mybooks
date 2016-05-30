package io.github.hksm.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.github.hksm.model.Country;

@Repository @Transactional
public class CountryDao {

	@Inject
	private EntityManager em;
	
	public Country findOne(Long id) {
		return em.find(Country.class, id);
	}
	
	public List<Country> findAll() {
		TypedQuery<Country> query = em.createQuery("select c from Country c order by c.name", Country.class);
		return query.getResultList();
	}
	
	public Country findByCode(String code) {
		TypedQuery<Country> query = em.createQuery("select c from Country c where c.code = ?1 order by c.name", Country.class);
		query.setParameter(1, code);
		return query.getSingleResult();
	}
	
	public Country findByName(String name) {
		TypedQuery<Country> query = em.createQuery("select c from Country c where c.name = ?1 order by c.name", Country.class);
		query.setParameter(1, name);
		return query.getSingleResult();
	}
}