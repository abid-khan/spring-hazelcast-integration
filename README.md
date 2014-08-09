spring-hazelcast
================

Objective of spring-hazelcast project is to provide samples or examples of hazelcast integration with spring.
This project infact POC of hazelcast integration with spring.


POCs
================
* Example of java based hazelcast configuration
* Example of Hazelcast as cache in Spring
* Example of Hazelcast JPAMapStore


Quick Start
================
Add maven dependency in your project


```xml

  <properties>
		...
		<hazelcast.version>3.2.4</hazelcast.version>
		<hazelcast.spring.version>3.2.3</hazelcast.spring.version>
	</properties>
	
	
  <dependency>
		<groupId>com.hazelcast</groupId>
		<artifactId>hazelcast-all</artifactId>
		<version>${hazelcast.version}</version>
	</dependency>

	 <dependency>
		<groupId>com.hazelcast</groupId>
		<artifactId>hazelcast-spring</artifactId>
		<version>${hazelcast.spring.version}</version>
	</dependency>
```

Also configure port, members (IPs),group name and group password. 

```properties
hazelcast.group=dev
hazelcast.password=password
hazelcast.members=127.0.0.1
hazelcast.port=5700
```

Sample cache configuration with hazelcast looks like this.

```java
@Configuration
@EnableCaching
public class CacheConfiguration {

	@Autowired
	private Environment environment;


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


}
```

Basic configuration is done. Now we should use in an  application.
We will create a service. It has one method findById which will annotated with @Cacheable. 
For detail on Spring Cache please refer [Spring Cache Abstraction](http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/cache.html)

```java
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
		LOGGER.info("User being fetched for id:" + id);
		return userRepository.findOne(id);
	}

	@Override
	public List<User> findByFirstName(String firstName) {
		return userRepository.findByFirstName(firstName);
	}

}
```

Lets write a test to test it.
```java

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfiguration.class,
		CacheConfiguration.class })
@TransactionConfiguration(defaultRollback = true)
public class UserServiceTest {

	@Autowired
	private IUserService userService;

	...
	
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

```
Once we execute this, in console we will get below logs.

```console
log4j:WARN No appenders could be found for logger (org.springframework.test.context.junit4.SpringJUnit4ClassRunner).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
Aug 9, 2014 12:56:34 PM com.hazelcast.instance.DefaultAddressPicker
INFO: null [dev] [3.2.4] Interfaces is disabled, trying to pick one address from TCP-IP config addresses: [127.0.0.1]
Aug 9, 2014 12:56:34 PM com.hazelcast.instance.DefaultAddressPicker
INFO: null [dev] [3.2.4] Picked Address[127.0.0.1]:5700, using socket ServerSocket[addr=/0:0:0:0:0:0:0:0%0,localport=5700], bind any local is true
Aug 9, 2014 12:56:34 PM com.hazelcast.system
INFO: [127.0.0.1]:5700 [dev] [3.2.4] Hazelcast 3.2.4 (20140721) starting at Address[127.0.0.1]:5700
Aug 9, 2014 12:56:34 PM com.hazelcast.system
INFO: [127.0.0.1]:5700 [dev] [3.2.4] Copyright (C) 2008-2014 Hazelcast.com
Aug 9, 2014 12:56:34 PM com.hazelcast.instance.Node
INFO: [127.0.0.1]:5700 [dev] [3.2.4] Creating TcpIpJoiner
Aug 9, 2014 12:56:34 PM com.hazelcast.core.LifecycleService
INFO: [127.0.0.1]:5700 [dev] [3.2.4] Address[127.0.0.1]:5700 is STARTING
Aug 9, 2014 12:56:35 PM com.hazelcast.cluster.TcpIpJoiner
INFO: [127.0.0.1]:5700 [dev] [3.2.4] 


Members [1] {
	Member [127.0.0.1]:5700 this
}

Aug 9, 2014 12:56:35 PM com.hazelcast.core.LifecycleService
INFO: [127.0.0.1]:5700 [dev] [3.2.4] Address[127.0.0.1]:5700 is STARTED
Aug 9, 2014 12:56:35 PM com.hazelcast.partition.InternalPartitionService
INFO: [127.0.0.1]:5700 [dev] [3.2.4] Initializing cluster partition table first arrangement...
User being fetched for id:5
```

Note last line of the log. Though in test we have called same method twice though repository is called only once.
