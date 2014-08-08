package com.hazelcast.store;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hazelcast.config.CacheConfiguration;
import com.hazelcast.config.PersistenceConfiguration;
import com.hazelcast.config.PropertySourcesPlaceholderConfiguration;
import com.hazelcast.constant.StatusType;
import com.hazelcast.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
		PropertySourcesPlaceholderConfiguration.class,
		PersistenceConfiguration.class, CacheConfiguration.class })
public class UserJPAMapStoreTest {

	@Autowired
	private UserJPAMapStore userJPAMapStore;

	@Autowired
	private CacheManager cacheManager;

	@Test
	public void store() {
		userJPAMapStore.store("aa", getUser());
		User user = (User) userJPAMapStore.load(new Long(1l));
		Assert.assertNotNull(user);
		Assert.assertTrue(1 == user.getId().intValue());

	}

	public User getUser() {
		User user = new User();
		user.setFirstName("abid");
		user.setLastName("Khan");
		user.setStatus(StatusType.NEW);
		return user;

	}

}
