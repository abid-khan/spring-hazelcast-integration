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

