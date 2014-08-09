package com.hazelcast.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hazelcast.entity.User;
import com.hazelcast.repository.UserRepository;

@Transactional(readOnly = true)
@Service
public class UserService implements IUserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserRepository userRepository;

	@Transactional
	@Override
	public User save(User user) {
		return userRepository.saveAndFlush(user);
	}

	@Cacheable(value = "user", key = "#id")
	@Override
	public User findById(Long id) {
		System.out.println("User being fetched for id:" + id);
		return userRepository.findOne(id);
	}

	@Override
	public List<User> findByFirstName(String firstName) {
		return userRepository.findByFirstName(firstName);
	}

}
