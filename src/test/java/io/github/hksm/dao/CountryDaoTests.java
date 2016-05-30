package io.github.hksm.dao;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.hksm.MybooksApplication;
import io.github.hksm.model.Country;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MybooksApplication.class)
public class CountryDaoTests {

	@Inject
	CountryDao dao;
	
	@Test
	public void evaluateFindMethods() {
		List<Country> countries = dao.findAll();
		Country lowerCase = dao.findByName("brazil");
		Country exactCase = dao.findByName("Brazil");
		
		Assert.assertEquals("Brazil", lowerCase.getName());
		Assert.assertEquals("Brazil", lowerCase.getName());
		Assert.assertEquals(246, countries.size());
		Assert.assertEquals(false, countries.contains(exactCase));
	}
}
