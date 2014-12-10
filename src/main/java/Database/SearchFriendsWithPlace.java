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

	public void DownloadAllRelatedPhotos(String parentId) throws IOException {

		DownloadFiles dw = new DownloadFiles();
		dw.downloadParticularFile(filteredData, latitude, longitude, parentId);

	}

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

	public void GetFriendPlaceList(String parentId, DB db) {

		DBCollection dbcUser = db.getCollection("UserPlaces");

		BasicDBObject query = null;

		for (int i = 0; i < dataFriend.size(); i++) {
			// System.out.println(dataFriend.get(i));
			// System.out.println("displaying " + dataFriend.get(i));
			query = new BasicDBObject("parentId", dataFriend.get(i));

			DBCursor cursor = dbcUser.find(query);
			DBObject dbotemp = null;
			try {
				while (cursor.hasNext()) {

					// System.out.println("hi2");
					BasicDBObject object = (BasicDBObject) cursor.next();
					BasicDBObject place = (BasicDBObject) object.get("place");
					BasicDBObject location = (BasicDBObject) place
							.get("location");

					String city = "";
					double latitude = 0.0, longitude = 0.0;

					if (location.containsField("latitude"))
						latitude = location.getDouble("latitude");

					if (location.containsField("longitude"))
						longitude = location.getDouble("longitude");

					if (location.containsField("city"))
						city = location.get("city").toString();
					else
						city = place.get("name").toString();

					// System.out.println("it is: " + city + " " +
					// object.get("id").toString());

					if (Math.sqrt(Math.pow(this.longitude - longitude, 2)
							+ Math.pow(this.latitude - latitude, 2)) < 10) {
						dataFriendFiltered.add(dataFriend.get(i));
						dataFriendNameFiltered.add(dataFriendName.get(i));
						dataPlaceFiltered.add(place.get("id").toString());
						dataLocationFiltered.add(new JSONObject(object));
					}

					/*
					 * if(city.indexOf(cityName) >= 0) {
					 * //System.out.println("he: " + place.get("id").toString()
					 * + " " + object.get("id").toString());
					 * //dataUsierPlace.put
					 * (dataFriend.get(i),object.get("id").toString());
					 * dataFriendFiltered.add(dataFriend.get(i));
					 * dataPlaceFiltered.add(place.get("id").toString()); }
					 */

				}
			} finally {
				cursor.close();

			}
		}
	}

	public void GetFriendPhoto(String parentId, DB db) throws JSONException {

		DBCollection dbcUser = db.getCollection("UserPhotosPlaces");

		BasicDBObject query = null;
		// int counter = 0;
		for (int i = 0; i < dataPlaceFiltered.size(); i++) {
			// System.out.println(dataFriend.get(i));
			// System.out.println("displaying for " + dataFriendFiltered.get(i)
			// + "  " + dataPlaceFiltered.get(i));
			query = new BasicDBObject("placeId", dataPlaceFiltered.get(i));
			query.append("parentId", dataFriendFiltered.get(i));

			DBCursor cursor = dbcUser.find(query);
			DBObject dbotemp = null;
			FilteredUserPhotoPlace fTemp = null;
			JSONObject tempObj = null;
			try {
				while (cursor.hasNext()) {
					dbotemp = cursor.next();
					// System.out.println("pic is " +
					// dbotemp.get("photoId").toString());
					// dataPhotoFiltered.add(dataFriendFiltered.get(i));
					// dataFriendPhoto.put(dataFriendFiltered.get(i),
					// dbotemp.get("photoId").toString());
					fTemp = new FilteredUserPhotoPlace();
					fTemp.placeId = dataPlaceFiltered.get(i);
					fTemp.photoId = dbotemp.get("photoId").toString()
							.replace("[", "").replace("]", "")
							.replace("\"", "").trim();
					fTemp.parentId = dataFriendFiltered.get(i);
					fTemp.friendName = dataFriendNameFiltered.get(i);

					tempObj = dataLocationFiltered.get(i)
							.getJSONObject("place").getJSONObject("location");
					if (tempObj.has("zip"))
						fTemp.zip = tempObj.getString("zip");
					else
						fTemp.zip = "";

					if (tempObj.has("street"))
						fTemp.street = tempObj.getString("street");
					else
						fTemp.street = "";

					if (tempObj.has("state"))
						fTemp.state = tempObj.getString("state");
					else
						fTemp.state = "";

					if (tempObj.has("longitude"))
						fTemp.longitude = tempObj.getString("longitude");
					else
						fTemp.longitude = "";

					if (tempObj.has("latitude"))
						fTemp.latitude = tempObj.getString("latitude");
					else
						fTemp.latitude = "";

					if (tempObj.has("country"))
						fTemp.country = tempObj.getString("country");
					else
						fTemp.country = "";

					if (tempObj.has("city"))
						fTemp.city = tempObj.getString("city");
					else
						fTemp.city = "";

					if (dataLocationFiltered.get(i).getJSONObject("place")
							.has("name"))
						fTemp.name = dataLocationFiltered.get(i)
								.getJSONObject("place").getString("name");
					else
						fTemp.name = "";

					filteredData.add(fTemp);

				}
			} finally {
				cursor.close();

			}
		}
	}

}
