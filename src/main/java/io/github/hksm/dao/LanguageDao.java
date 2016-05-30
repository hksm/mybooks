package io.github.hksm.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.github.hksm.model.Language;

@Repository @Transactional
public class LanguageDao {

	@Inject
	private EntityManager em;
	
	public Language findOne(Long id) {
		return em.find(Language.class, id);
	}
	
	public List<Language> findAll() {
		TypedQuery<Language> query = em.createQuery("select l from Language l order by l.name", Language.class);
		return query.getResultList();
	}
	
	public Language findByCode(String code) {
		TypedQuery<Language> query = em.createQuery("select l from Language l where l.code = ?1 order by l.name", Language.class);
		query.setParameter(1, code);
		return query.getSingleResult();
	}
	
	public Language findByName(String name) {
		TypedQuery<Language> query = em.createQuery("select l from Language l where l.name = ?1 order by l.name", Language.class);
		query.setParameter(1, name);
		return query.getSingleResult();
	}
}