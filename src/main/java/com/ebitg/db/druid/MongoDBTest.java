package com.ebitg.db.druid;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBTest {
	@Test
	public void test01() {
		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("Sync");
			MongoCollection<Document> collection = mongoDatabase.getCollection("MyTBLName");
			Document document = new Document();
			document.put("k1", "v1");
			document.put("k2", "v2");

			collection.insertOne(document);
			System.out.println("Connect to database successfully");

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	@Test
	public void test02() {
		Builder builder = MongoClientOptions.builder();
	}
}
