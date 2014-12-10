package Database;

import java.net.UnknownHostException;
import java.util.Arrays;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class OpenDatabase {

	public DBCollection dbCollection;

	MongoClient mongoClient = null;

	public OpenDatabase(String collectionName) {
		char[] password = "1234".toCharArray();

		MongoCredential credential = MongoCredential.createMongoCRCredential(
				"sjsuTeam16", "fbdata", password);

		try {
			mongoClient = new MongoClient(new ServerAddress(
					"ds047720.mongolab.com", 47720), Arrays.asList(credential));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DB db = mongoClient.getDB("fbdata");
		dbCollection = db.getCollection(collectionName);

		if (dbCollection.equals(null)) {
			dbCollection = db.createCollection(collectionName,
					new BasicDBObject("capped", true).append("size", 1048576));
		}
	}

	public void CloseDatabase() {
		if (mongoClient != null)
			mongoClient.close();

	}
}
