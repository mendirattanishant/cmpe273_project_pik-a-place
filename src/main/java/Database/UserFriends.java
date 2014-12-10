package Database;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;

import facebook4j.ResponseList;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Set;

public class UserFriends {

	public UserFriends(String parentUser, ResponseList<JSONObject> friendList)
			throws UnknownHostException {
		char[] password = "1234".toCharArray();

		MongoCredential credential = MongoCredential.createMongoCRCredential(
				"sjsuTeam16", "fbdata", password);
		MongoClient mongoClient = new MongoClient(new ServerAddress(
				"ds047720.mongolab.com", 47720), Arrays.asList(credential));
		DB db = mongoClient.getDB("fbdata");
		DBCollection dbcUserFriends = db.getCollection("UserFriends");

		if (dbcUserFriends.equals(null)) {
			dbcUserFriends = db.createCollection("UserFriends",
					new BasicDBObject("capped", true).append("size", 1048576));
		}

		/*
		 * Object o = com.mongodb.util.JSON.parse(userFrd); DBObject dbObj =
		 * (DBObject) o; dbcUser.insert(dbObj);
		 */
		BasicDBObject query = null;

		for (JSONObject frd : friendList) {

			try {
				query = new BasicDBObject("parentId", parentUser);
				query.append("id", frd.get("id").toString());

			} catch (JSONException e) {

				e.printStackTrace();
			}

			DBCursor cursor = dbcUserFriends.find(query);

			try {
				if (cursor.hasNext()) {
					// DBCollection dbExistingUser = cursor;
					// System.out.println(cursor.next());
					System.out.println("user friend combination already noted");
				} else {
					frd.append("parentId", parentUser);

					Object o = com.mongodb.util.JSON.parse(frd.toString());
					DBObject dbObj = (DBObject) o;
					dbcUserFriends.insert(dbObj);
					System.out.println("New user friend combination noted");

				}
			} catch (JSONException e) {

				e.printStackTrace();
			} finally {
				cursor.close();
			}
		}

	}

}
