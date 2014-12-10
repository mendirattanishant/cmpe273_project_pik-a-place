package Database;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Set;

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

import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

public class User {

	

	public String lastSync = "";
	public String userId = "";

	public String AddUser(JSONObject newUserObj,String username) throws UnknownHostException {
		char[] password = "1234".toCharArray();

		MongoCredential credential = MongoCredential.createMongoCRCredential(
				"sjsuTeam16", "fbdata", password);
		MongoClient mongoClient = new MongoClient(new ServerAddress(
				"ds047720.mongolab.com", 47720), Arrays.asList(credential));
		DB db = mongoClient.getDB("fbdata");
		DBCollection dbcUser = db.getCollection("User");

		if (dbcUser.equals(null)) {
			dbcUser = db.createCollection("User", new BasicDBObject("capped",
					true).append("size", 1048576));
		}

		BasicDBObject query = null;
		try {
			userId = newUserObj.get("id").toString();
			query = new BasicDBObject("id", newUserObj.get("id").toString());
		} catch (JSONException e) {

			e.printStackTrace();
			return "Error";
		}

		DBCursor cursor = dbcUser.find(query);
		boolean flag = false;
		java.util.Date d = new java.util.Date();
		try {
			if (cursor.hasNext()) {
				DBObject dbotemp = cursor.next();
				lastSync = dbotemp.get("lastSync").toString().replace("[", "")
						.replace("]", "").replace("\"", "").trim();
				
				System.out.println("lastSync: " + lastSync);
				System.out.println("user already added");

				BasicDBObject obj = (BasicDBObject) dbotemp;

				obj.put("lastSync", d.toString());
				// dataColl.save(obj);
				dbcUser.save(obj);
			} else {

				lastSync = d.toString();
				newUserObj.append("lastSync", d.toString());
				newUserObj.append("username", username);
				Object o = com.mongodb.util.JSON.parse(newUserObj.toString());
				DBObject dbObj = (DBObject) o;
				dbcUser.insert(dbObj);
				flag = true;
				System.out.println(lastSync);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			cursor.close();

		}

		if (flag == true)
			return "Add";
		else
			return "Added";

	}
	
	
	

}
