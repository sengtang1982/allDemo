package com.ebitg.commons.mongodb;

import java.util.Set;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class JongoTest {
	private static final String HOST = "10.99.99.227";
	private static final String COLL_NAME = "Employees";

	public static void main(String[] args) {
		MongoClient mongo = null;
		try {
			mongo = new MongoClient(HOST, 27017);
			DB db = mongo.getDB("jongo");
			Set<String> collectionNames = db.getCollectionNames();
			for (String collName : collectionNames) {
				System.out.println(collName);
			}
			Jongo jongo = new Jongo(db);
			MongoCollection coll = jongo.getCollection(COLL_NAME);
			coll.insert("{'name':'ÕÅÈý'}");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
	}
}
