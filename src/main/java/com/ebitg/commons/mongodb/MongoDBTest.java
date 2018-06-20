package com.ebitg.commons.mongodb;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class MongoDBTest {
	public static void main(String[] args) {
		try {
			// ���ӵ�MongoDB���� �����Զ�����ӿ����滻��localhost��Ϊ����������IP��ַ
			// ServerAddress()���������ֱ�Ϊ ��������ַ �� �˿�
			ServerAddress serverAddress = new ServerAddress("localhost", 27017);
			List<ServerAddress> addrs = new ArrayList<ServerAddress>();
			addrs.add(serverAddress);

			// MongoCredential.createScramSha1Credential()���������ֱ�Ϊ �û��� ���ݿ����� ����
			MongoCredential credential = MongoCredential.createScramSha1Credential("username", "databaseName",
					"password".toCharArray());
			List<MongoCredential> credentials = new ArrayList<MongoCredential>();
			credentials.add(credential);

			// ͨ��������֤��ȡMongoDB����
			MongoClient mongoClient = new MongoClient(addrs, credentials);

			// ���ӵ����ݿ�
			MongoDatabase mongoDB = mongoClient.getDatabase("databaseName");
			System.out.println(mongoDB.getName());
			System.out.println("Connect to database successfully");
			mongoDB.createCollection("myColl");
			System.out.println("���ϴ����ɹ�");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}
