package com.hazelcast.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.repository.UserRepository;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import com.hazelcast.store.UserJPAMapStore;

/**
 * @author abidk
 * 
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

	@Autowired
	private Environment environment;

	@Autowired
	private UserRepository userRepository;

	@Bean
	public HazelcastInstance hazelcastInstance() {

		Config config = new Config();
		config.setGroupConfig(new GroupConfig(environment
				.getProperty("hazelcast.group"), environment
				.getProperty("hazelcast.password")));

		NetworkConfig networkConfig = new NetworkConfig();
		networkConfig.setPort(Integer.parseInt(environment
				.getProperty("hazelcast.port")));
		networkConfig.setPortAutoIncrement(false);

		JoinConfig joinConfig = new JoinConfig();
		MulticastConfig multicastConfig = new MulticastConfig();
		multicastConfig.setEnabled(false);
		joinConfig.setMulticastConfig(multicastConfig);

		TcpIpConfig tcpIpConfig = new TcpIpConfig();
		tcpIpConfig.setEnabled(true);
		String memberList = environment.getProperty("hazelcast.members");
		List<String> members = new ArrayList<String>();
		for (String member : memberList.split(",")) {
			members.add(member);
		}

		tcpIpConfig.setMembers(members);
		joinConfig.setTcpIpConfig(tcpIpConfig);

		networkConfig.setJoin(joinConfig);

		config.setNetworkConfig(networkConfig);

		HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
		return instance;
	}

	@Bean
	public CacheManager cacheManager() {
		HazelcastCacheManager cacheManager = new HazelcastCacheManager(
				hazelcastInstance());
		return cacheManager;
	}

	@Bean
	public UserJPAMapStore userJPAMapStore() {
		UserJPAMapStore userJPAMapStore = new UserJPAMapStore();
		userJPAMapStore.setCrudRepository(userRepository);
		return userJPAMapStore;
	}

}
