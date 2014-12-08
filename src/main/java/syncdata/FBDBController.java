package syncdata;

import Database.SearchFriendsWithPlace;
import Database.User;
import Database.UserFriends;
import Database.UserPlaces;
import DropBox.AddFiles;
import DropBox.DownloadFiles;
import Threading.RunThreads;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import java.util.Properties;

import facebook4j.Place;
import facebook4j.Checkin;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Location;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.conf.Configuration;
import facebook4j.conf.ConfigurationBuilder;
import facebook4j.internal.http.RequestMethod;
import facebook4j.internal.org.json.JSONObject;
import facebook4j.json.DataObjectFactory;
import facebook4j.ResponseList;
//import facebook4j.User;
import facebook4j.auth.AccessToken;
import facebook4j.Friend;
import facebook4j.Photo;
import facebook4j.BatchRequest;
import facebook4j.BatchRequests;
import facebook4j.BatchResponse;

;
//- See more at: http://www.devx.com/Java/how-to-integrate-facebook-and-twitter-with-java-applications.html#sthash.cC7FG9Sm.dpuf

public class FBDBController {
	public static void SyncFBDB(String receive) throws FacebookException {

		// Setting configuration details in ConfigurationBuilder Class
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setDebugEnabled(true);
		configurationBuilder.setOAuthAppId("364376597062363");
		configurationBuilder
				.setOAuthAppSecret("364376597062363|MOWZDjl5vZThkkkku7QeksiJiAI");
		configurationBuilder
				.setOAuthPermissions("email,user_friends,user_photos,user_tagged_places");
		configurationBuilder.setUseSSL(true);
		configurationBuilder.setJSONStoreEnabled(true);

		configurationBuilder.setOAuthAccessToken(receive);
		// CAAFLZAgmh4tsBAJAZAOwB1dPMaZAJi0zI1sLpZC8ZBmxBFweft65IndfP7GigRd9R0rCFaUIsZCowVT6IBz7sZATNVq82OhEFfpqL9IptCw8npffDQ0GzXGEJNLaLJraPG3IgaeYASmIxnMutz89HHecGtnAbfVqjuPoYcm98eDMZCWbebWZBtycISeuk0fTzR9FrQNz3GnGQTcs7lcxIoZBKeQPiH1DeFQG8ZD
		// Building Configuration
		Configuration configuration = configurationBuilder.build();

		// getting facebook instance
		FacebookFactory ff = new FacebookFactory(configuration);

		Facebook facebook = ff.getInstance();

		String parentID = "";
		try {

			BatchRequests<BatchRequest> batch = new BatchRequests<BatchRequest>();
			batch.add(new BatchRequest(RequestMethod.GET, "me"));
			List<BatchResponse> results = facebook.executeBatch(batch);
			BatchResponse resultUser = results.get(0);

			// Fetching User Profile info
			// String jsonStringUser = resultUser.asString();
			JSONObject jsonObjectUser = resultUser.asJSONObject();

			// /Calling User Database Class
			User uNew = new User();
			// ////////////////////
			String resp = uNew.AddUser(jsonObjectUser);

			parentID = jsonObjectUser.get("id").toString();

			// SearchFriendsWithPlace frdU = new SearchFriendsWithPlace();
			// frdU.GetFriendListNPhotos(parentID);

			BatchResponse resultUserFriends = null;
			BatchResponse resultUserPlaces = null;
			if (resp == "Add") {
				batch = new BatchRequests<BatchRequest>();
				batch.add(new BatchRequest(RequestMethod.GET,
						"me/friends?limit=1000"));
				// batch.add(new BatchRequest(RequestMethod.GET,
				// "me/tagged_places"));
				results = facebook.executeBatch(batch);

				resultUserFriends = results.get(0);
				// resultUserPlaces = results.get(1);

				ResponseList<JSONObject> responseListUserFriends = resultUserFriends
						.asResponseList();
				UserFriends usf = null;
				JSONObject jsonObjectUserFriends = resultUserFriends
						.asJSONObject();
				while (responseListUserFriends != null) {
					usf = new UserFriends(parentID, responseListUserFriends);

					if (jsonObjectUserFriends.has("Paging")) {
						if (jsonObjectUserFriends.getJSONObject("paging").has(
								"next")) {
							batch = new BatchRequests<BatchRequest>();
							batch.add(new BatchRequest(RequestMethod.GET,
									jsonObjectUserFriends.getJSONObject(
											"paging").getString("next")));
							results = facebook.executeBatch(batch);
							System.out.println("hello: "
									+ jsonObjectUserFriends.getJSONObject(
											"paging").getString("next"));

							resultUserFriends = results.get(0);
							responseListUserFriends = resultUserFriends
									.asResponseList();
							System.out
									.println("hi: " + responseListUserFriends);
							jsonObjectUserFriends = resultUserFriends
									.asJSONObject();
						} else
							responseListUserFriends = null;
					} else
						responseListUserFriends = null;
				}
				// JSONObject jsonObjectUserPlaces =
				// resultUserPlaces.asJSONObject();

				batch = new BatchRequests<BatchRequest>();

				batch.add(new BatchRequest(RequestMethod.GET,
						"me/tagged_places?limit=1000"));
				results = facebook.executeBatch(batch);

				resultUserPlaces = results.get(0);

				ResponseList<JSONObject> responseListUserPlaces = resultUserPlaces
						.asResponseList();

				UserPlaces up = null;

				JSONObject jsonObjectUserPlaces = resultUserPlaces
						.asJSONObject();
				while (responseListUserPlaces != null) {
					up = new UserPlaces(parentID, responseListUserPlaces);

					if (jsonObjectUserPlaces.has("Paging")) {
						if (jsonObjectUserPlaces.getJSONObject("paging").has(
								"next")) {
							batch = new BatchRequests<BatchRequest>();
							batch.add(new BatchRequest(RequestMethod.GET,
									jsonObjectUserPlaces
											.getJSONObject("paging").getString(
													"next")));
							results = facebook.executeBatch(batch);

							resultUserPlaces = results.get(0);
							responseListUserPlaces = resultUserPlaces
									.asResponseList();
							jsonObjectUserPlaces = resultUserPlaces
									.asJSONObject();
						}
					} else
						responseListUserPlaces = null;
				}

				// fetching user photos
				batch = new BatchRequests<BatchRequest>();
				batch.add(new BatchRequest(RequestMethod.GET,
						"me/photos?fields=source,place&limit=100"));

				// uploading images to Dropbox
				BatchResponse resultsPhotos = facebook.executeBatch(batch).get(
						0);
				JSONObject jsonObjectPhotos = resultsPhotos.asJSONObject();
				ResponseList<JSONObject> responseListUserPhotos = facebook
						.executeBatch(batch).get(0).asResponseList();
				if (jsonObjectPhotos.has("data")) {
					if (jsonObjectPhotos.getJSONArray("data").length() != 0) {
						RunThreads rt = new RunThreads(0, parentID,
								responseListUserPhotos);
						rt.start();

						String after = "";
						if (jsonObjectPhotos.has("paging")) {
							if (jsonObjectPhotos.getJSONObject("paging").has(
									"next")) {
								after = jsonObjectPhotos
										.getJSONObject("paging")
										.getJSONObject("cursors")
										.getString("after");
							}
						}
						////////////////////////////////


//////////////////////////////////////////////////////////////////
					}
				}
			} 

///////////////////////////

///////////////////////////////////////
			}

			// DownloadFiles df = new DownloadFiles();
			// df.downloadFile(uNew.userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}