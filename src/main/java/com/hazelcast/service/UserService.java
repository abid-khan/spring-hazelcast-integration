package com.hazelcast.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hazelcast.entity.User;
import com.hazelcast.repository.UserRepository;

@Transactional(readOnly = true)
@Service
public class UserService implements IUserService {

	@Autowired
	UserRepository userRepository;

	@Transactional
	@Override
	public User save(User user) {
		return userRepository.saveAndFlush(user);
	}
	
	
	@Cacheable(value = "user")
	@Override
	public User findById(Long id) {
		return userRepository.findOne(id);
	}



	@Override
	public List<User> findByFirstName(String firstName) {
		return userRepository.findByFirstName(firstName);
	}

}
