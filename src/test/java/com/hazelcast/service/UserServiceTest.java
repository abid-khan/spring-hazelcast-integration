package com.hazelcast.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hazelcast.config.CacheConfiguration;
import com.hazelcast.config.PersistenceConfiguration;
import com.hazelcast.constant.StatusType;
import com.hazelcast.entity.User;

/**
 * @author abidk
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfiguration.class,
		CacheConfiguration.class })
@TransactionConfiguration(defaultRollback = true)
public class UserServiceTest {

	@Autowired
	private IUserService userService;

	@Transactional
	@Test
	public void save() {
		User user = userService.save(getUser());
		Assert.assertNotNull(user);
	}

	@Transactional
	@Test
	public void findById() {
		User savedUser  = userService.save(getUser());
		Assert.assertNotNull(savedUser);
		User fetchedUser1  = userService.findById(savedUser.getId());
		Assert.assertTrue(savedUser.equals(fetchedUser1));
		
		User fetchedUser2  = userService.findById(savedUser.getId());
		Assert.assertTrue(savedUser.equals(fetchedUser2));
	}

	public User getUser() {
		User user = new User();
		user.setFirstName("abid");
		user.setLastName("Khan");
		user.setStatus(StatusType.NEW);
		return user;

	}

}
