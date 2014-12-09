package Database;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.util.List;
import java.util.Set;

import DropBox.DownloadFiles;

import com.mongodb.BasicDBList;
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

public class SearchFriendsWithPlace {

	List<String> dataFriend = new ArrayList<String>();
	List<String> dataFriendName = new ArrayList<String>();
	// List<String,String> dataUserPlace = new ArrayList<String,String>();

	double latitude = 0.0, longitude = 0.0;

	List<String> dataFriendFiltered = new ArrayList<String>();
	List<String> dataFriendNameFiltered = new ArrayList<String>();
	List<String> dataPlaceFiltered = new ArrayList<String>();
	List<String> dataPhotoFiltered = new ArrayList<String>();
	// /HashMap<String, String> dataFriendPhoto = new HashMap();
	List<FilteredUserPhotoPlace> filteredData = new ArrayList<FilteredUserPhotoPlace>();

	List<JSONObject> dataLocationFiltered = new ArrayList<JSONObject>();

	

	public List<FilteredUserPhotoPlace> GetFriendListNPhotos(String parentId,
			double latitude, double longitude) throws UnknownHostException,
			JSONException {
		this.latitude = latitude;
		this.longitude = longitude;

		char[] password = "1234".toCharArray();

		MongoCredential credential = MongoCredential.createMongoCRCredential(
				"sjsuTeam16", "fbdata", password);
		MongoClient mongoClient = new MongoClient(new ServerAddress(
				"ds047720.mongolab.com", 47720), Arrays.asList(credential));
		DB db = mongoClient.getDB("fbdata");
		DBCollection dbcUser = db.getCollection("User");

		BasicDBObject query = null;
		query = new BasicDBObject("id", parentId);

		DBCursor cursor = dbcUser.find(query);
		// boolean flag = false;

		try {
			if (cursor.hasNext()) {
				// System.out.println("hi0");
				GetFriendList(parentId, db);
				GetFriendPlaceList(parentId, db);
				GetFriendPhoto(parentId, db);
			}
		} finally {
			cursor.close();

		}
		System.out.println("final data");
		for (int i = 0; i < filteredData.size(); i++) {
			// filteredData.get(i).photoId.replace("[", "").replace("]",
			// "").replace("\"", "");
			System.out.println(filteredData.get(i).parentId + "-- "
					+ filteredData.get(i).photoId + "--"
					+ filteredData.get(i).placeId);
		}
		try {
			DownloadAllRelatedPhotos(parentId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

		return filteredData;
	}

	public void GetFriendList(String parentId, DB db) {

		DBCollection dbcUser = db.getCollection("UserFriends");

		// System.out.println("hi1");

		BasicDBObject query = null;
		query = new BasicDBObject("parentId", parentId);

		DBCursor cursor = dbcUser.find(query);
		DBObject dbotemp = null;
		try {
			while (cursor.hasNext()) {

				dbotemp = cursor.next();

				dataFriend.add(dbotemp.get("id").toString());
				dataFriendName.add(dbotemp.get("name").toString());

			}
		} finally {
			cursor.close();

		}
	}

	
	}

}