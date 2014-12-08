package com;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * Spring MongoDB configuration file
 * 
 */
@Configuration
public class SpringConfig {
	static public DB db;

	public @Bean MongoTemplate mongoTemplate() throws Exception {

		MongoClient clientForMongo = new MongoClient(
				"ds053310.mongolab.com:53310");
		db = clientForMongo.getDB("project_273");
		boolean auth = db.authenticate("nishant", "nishant@1".toCharArray());
		MongoTemplate templateForMongo = new MongoTemplate(clientForMongo,
				"project_273");
		return templateForMongo;

		/*
		 * char[] password = "".toCharArray(); MongoCredential credential =
		 * MongoCredential.createMongoCRCredential("", "mydb",password);
		 * MongoClient mongoClient = new MongoClient(new
		 * ServerAddress("127.0.0.1",63224),Arrays.asList(credential));
		 * MongoTemplate mongoTemplate = new MongoTemplate(mongoClient,"mydb");
		 * db = mongoClient.getDB( "mydb" );
		 * 
		 * return mongoTemplate;
		 */

	}

}