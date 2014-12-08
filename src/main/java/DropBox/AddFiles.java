package DropBox;

import Database.OpenDatabase;
import Database.UserPhotosPlaces;

import com.dropbox.core.*;

import facebook4j.ResponseList;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import facebook4j.internal.org.json.JSONArray;

import java.io.*;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

public class AddFiles {
	public void uploadFile(String threadName, String parentId,
			ResponseList<JSONObject> jsonObjectPhotos) throws IOException,
			DbxException, JSONException {

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

		// ////////////////////////

		final String APP_KEY = "2uguk7d5cb3n4xm";
		final String APP_SECRET = "wxib4jn5nym6m27";

		DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

		DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0",
				Locale.getDefault().toString());
		DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);

		String accessToken = "qMr5yR5h5qsAAAAAAAAABqjrFo-mJAnvweWEoimhnf3Hzq9ucCTMlcVMcdZqwoMK";

		DbxClient client = new DbxClient(config, accessToken);
		// DbxEntry.Folder folder= client.createFolder("/"+parentId);

		DbxEntry.WithChildren listing = client.getMetadataWithChildren("/");
		boolean create = true;
		for (DbxEntry child : listing.children) {
			if (child.name == parentId) {
				create = false;
				break;
			}
		}

		if (create == true)
			client.createFolder("/" + parentId);
		FileInputStream inputStream = null;

		(new File(downloadPath + threadName)).mkdirs();

		// Opening Database Connection
		OpenDatabase opDB = new OpenDatabase("UserPhotosPlaces");

		for (JSONObject data : jsonObjectPhotos) {
			create = false;
			try {
				create = saveImage(threadName, data.getString("source"),
						data.getString("id"), downloadPath);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			if (create == true) {
				try {

					File inputFile = new File(downloadPath + threadName + "/"
							+ data.getString("id") + ".jpg");

					inputStream = new FileInputStream(inputFile);

					DbxEntry.File uploadedFile = client
							.uploadFile(
									"/" + parentId + "/" + data.getString("id")
											+ ".jpg", DbxWriteMode.add(),
									inputFile.length(), inputStream);
					System.out.println("Uploaded: " + uploadedFile.toString());

					// //Database Entry///////////

					UserPhotosPlaces upp = new UserPhotosPlaces(opDB, parentId,
							data);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					inputStream.close();

				}
			}

		}

		for (JSONObject data : jsonObjectPhotos) {

			(new File(downloadPath + threadName + "/" + data.getString("id")
					+ ".jpg")).delete();
		}

		// Closing Database Connection
		opDB.CloseDatabase();

		File index = new File(downloadPath + threadName);
		index.delete();
		System.out.println("deleted folder from server.");

	}

	public boolean saveImage(String threadName, String imageUrl, String id,
			String downloadPath) throws IOException {
		try {
			URL url = new URL(imageUrl);
			String destName = downloadPath + threadName + "/" + id + ".jpg";
			// System.out.println(destName);

			InputStream is = url.openStream();

			OutputStream os = new FileOutputStream(destName);

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}

			is.close();
			os.close();
			return true;
		} catch (Exception e) {

			e.printStackTrace();

		}
		return false;
	}

}