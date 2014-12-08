package DropBox;

import Database.FilteredUserPhotoPlace;
import Database.OpenDatabase;
import Database.UserPhotosPlaces;

import com.dropbox.core.*;

import facebook4j.ResponseList;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import facebook4j.internal.org.json.JSONArray;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

public class DownloadFiles {

	public List<String> downloadFile(String parentId) throws IOException {

		String downloadPath = "";

		// Accessing config file

		File configFile = new File("application.properties");

		try {
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);

			downloadPath = props.getProperty("downloadPath");

			reader.close();
		} catch (FileNotFoundException ex) {
			// file does not exist
		} catch (IOException ex) {
			// I/O error
		}

		// //////////////////////////

		List<String> picList = new ArrayList<String>();

		final String APP_KEY = "2uguk7d5cb3n4xm";
		final String APP_SECRET = "wxib4jn5nym6m27";

		DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

		DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0",
				Locale.getDefault().toString());
		DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);

		String accessToken = "qMr5yR5h5qsAAAAAAAAABqjrFo-mJAnvweWEoimhnf3Hzq9ucCTMlcVMcdZqwoMK";

		DbxClient client = new DbxClient(config, accessToken);
		// DbxEntry.Folder folder= client.createFolder("/"+parentId);
		System.out.println("INtoppppppp downloading " + parentId);
		DbxEntry.WithChildren listing = null;
		DbxEntry.File downloadedFile = null;
		try {
			listing = client.getMetadataWithChildren("/");
		} catch (DbxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean present = false;

		if (!(new File(downloadPath + parentId)).exists())
			(new File(downloadPath + parentId)).mkdirs();

		for (DbxEntry child : listing.children) {
			if (child.name == parentId) {
				present = true;
				break;
			}
		}
		listing = null;
		try {
			listing = client.getMetadataWithChildren("/" + parentId);
		} catch (DbxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (listing != null && !listing.children.isEmpty()) {
			for (DbxEntry childFiles : listing.children) {

				picList.add(childFiles.name);
				try {

					if (!(new File(downloadPath + parentId + "/"
							+ childFiles.name)).exists())
						downloadedFile = client.getFile("/" + parentId + "/"
								+ childFiles.name, null,
								new FileOutputStream(downloadPath + parentId
										+ "/" + childFiles.name));
					// File inputFile = new File((File)downloadedFile);

				} catch (DbxException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}

			}
		}

		return picList;

	}

	public void downloadParticularFile(
			List<FilteredUserPhotoPlace> filteredData, double latitude,
			double longitude, String parentId) throws IOException {

		String downloadPath = "";

		// Accessing config file

		File configFile = new File("application.properties");

		try {
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);

			downloadPath = props.getProperty("downloadPath");

			reader.close();
		} catch (FileNotFoundException ex) {
			// file does not exist
		} catch (IOException ex) {
			// I/O error
		}

		// //////////////////////////

		final String APP_KEY = "2uguk7d5cb3n4xm";
		final String APP_SECRET = "wxib4jn5nym6m27";

		DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

		DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0",
				Locale.getDefault().toString());
		DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);

		String accessToken = "qMr5yR5h5qsAAAAAAAAABqjrFo-mJAnvweWEoimhnf3Hzq9ucCTMlcVMcdZqwoMK";

		DbxClient client = new DbxClient(config, accessToken);
		// DbxEntry.Folder folder= client.createFolder("/"+parentId);

		DbxEntry.WithChildren listing = null, listingChild = null;

		try {
			listing = client.getMetadataWithChildren("/");
		} catch (DbxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		DbxEntry.File downloadedFile = null;
		for (int i = 0; i < filteredData.size(); i++) {

			// System.out.println("ALL details: " + filteredData.get(i).parentId
			// + "-- " + filteredData.get(i).photoId.replace("[",
			// "").replace("]", "").replace("\"", "") + "--" +
			// filteredData.get(i).placeId);

			boolean present = false;

			for (DbxEntry child : listing.children) {

				if (child.name.equalsIgnoreCase(filteredData.get(i).parentId)) {

					present = true;
					break;
				}
			}
			listingChild = null;
			if (present == true) {
				try {
					listingChild = client.getMetadataWithChildren("/"
							+ filteredData.get(i).parentId);
				} catch (DbxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (listingChild != null && !listingChild.children.isEmpty()) {

					try {

						if (!(new File(downloadPath + parentId + "/place-"
								+ latitude + "-" + longitude)).exists())
							(new File(downloadPath + parentId + "/place-"
									+ latitude + "-" + longitude)).mkdirs();

						if (!(new File(downloadPath + parentId + "/place-"
								+ latitude + "-" + longitude + "/"
								+ filteredData.get(i).photoId + ".jpg"))
								.exists()) {
							downloadedFile = client.getFile(
									"/" + filteredData.get(i).parentId + "/"
											+ filteredData.get(i).photoId
											+ ".jpg", null,
									new FileOutputStream(downloadPath
											+ parentId + "/place-" + latitude
											+ "-" + longitude + "/"
											+ filteredData.get(i).photoId
											+ ".jpg"));

							System.out.println("downloading particularly: "
									+ filteredData.get(i).photoId);
						}

					} catch (DbxException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
					}

				}
			}
		}

	}

}
