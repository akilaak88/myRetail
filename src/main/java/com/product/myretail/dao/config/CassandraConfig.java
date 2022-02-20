package com.product.myretail.dao.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

@Configuration
public class CassandraConfig {
	
	@Value("${cassandra.contactpoint}")
	String contactPoint;
	
	Logger log = LoggerFactory.getLogger(CassandraConfig.class);

	@Bean
	Session getSession() {
		Session session = null;
		try {
			Cluster cluster = Cluster.builder().withoutJMXReporting().addContactPoint(contactPoint).build();
			session = cluster.connect();
		} catch (Exception e) {
			log.debug("Exception occurred while getting session to cassandra db"+e.getMessage());
		}
		return session;
		
	}
}
