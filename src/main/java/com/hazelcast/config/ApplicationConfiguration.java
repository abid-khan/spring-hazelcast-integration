package com.hazelcast.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@Configuration
@ComponentScan(basePackages = "com.hazelcast")
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
@Import({ PropertySourcesPlaceholderConfiguration.class })
public class ApplicationConfiguration {

}
