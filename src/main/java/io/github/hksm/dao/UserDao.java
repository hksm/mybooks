package io.github.hksm.dao;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.github.hksm.model.UserData;

@Repository @Transactional
public class UserDao  extends GenericDao<UserData>{

	public UserData findByUsername(String username) {
		TypedQuery<UserData> query = em.createQuery("select u from UserData u where u.username = ?1", UserData.class);
		query.setParameter(1, username);
		return query.getSingleResult();
	}
}
