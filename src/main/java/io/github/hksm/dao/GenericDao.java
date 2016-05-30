package io.github.hksm.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

public abstract class GenericDao<T> {

	@Inject
	protected EntityManager em;
	
	private Class<T> type;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GenericDao() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

	@Transactional
    public void insert(final T t) {
        this.em.persist(t);
        this.em.flush();
    }
    
	@Transactional
    public void update(final T t) {
    	this.em.merge(t);
    }

	@Transactional
    public void remove(final Long id) {
        this.em.remove(this.em.getReference(type, id));
    }

    public T findOne(final Long id) {
        return (T) this.em.find(type, id);
    }
    
    public List<T> findAll() {
		return em.createQuery("from " + type.getName(), type).getResultList();
    }
}
