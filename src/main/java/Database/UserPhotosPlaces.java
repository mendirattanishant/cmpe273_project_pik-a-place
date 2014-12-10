package Database;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import java.net.UnknownHostException;

public class UserPhotosPlaces {

	public UserPhotosPlaces(OpenDatabase opDB, String parentId,
			JSONObject userPhotoPlace) throws UnknownHostException {

		BasicDBObject query = null;
		try {
			query = new BasicDBObject("parentId", parentId);
			query.append("photoId", userPhotoPlace.get("id").toString());

			if (userPhotoPlace.has("place"))
				query.append("placeId", userPhotoPlace.getJSONObject("place")
						.get("id").toString());

		} catch (JSONException e) {

			e.printStackTrace();
		}

		DBCursor cursor = opDB.dbCollection.find(query);

		try {
			if (cursor.hasNext()) {

				System.out
						.println("user place photo combination already noted");
			} else {
				JSONObject newUserPhotoPlaceObj = new JSONObject();
				newUserPhotoPlaceObj.append("parentId", parentId);
				newUserPhotoPlaceObj.append("photoId", userPhotoPlace.get("id")
						.toString());

				if (userPhotoPlace.has("place"))
					newUserPhotoPlaceObj.append("placeId", userPhotoPlace
							.getJSONObject("place").get("id").toString());

				Object o = com.mongodb.util.JSON.parse(newUserPhotoPlaceObj
						.toString());
				DBObject dbObj = (DBObject) o;
				opDB.dbCollection.insert(dbObj);
				System.out.println("New user place photo combination noted");

			}
		} catch (JSONException e) {

			e.printStackTrace();
		} finally {
			cursor.close();
		}

	}

}
