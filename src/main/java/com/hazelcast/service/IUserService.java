package com.hazelcast.service;

import java.util.List;

import com.hazelcast.entity.User;


/**
 * @author abidk
 * 
 */
public interface IUserService {

	User save(User user);
	
	User findById(Long id);
	
	List<User> findByFirstName(String firstName);
}
