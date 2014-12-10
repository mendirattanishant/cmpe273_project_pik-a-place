package Threading;

import java.io.IOException;

import com.dropbox.core.DbxException;

import DropBox.AddFiles;
import facebook4j.ResponseList;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

public class RunThreads implements Runnable {
	private Thread t;
	private String threadName;
	private String parentId;
	private ResponseList<JSONObject> jsonObjectPhotos;

	public RunThreads(int name, String parentId,
			ResponseList<JSONObject> objectPhotos) {
		threadName = parentId + "_Thread_" + name;
		jsonObjectPhotos = objectPhotos;
		this.parentId = parentId;
		System.out.println("Creating " + threadName);
	}

	public void run() {
		System.out.println("Running " + threadName);
		AddFiles adImg = new AddFiles();

		//
		
	
		try {

			adImg.uploadFile(threadName, parentId, jsonObjectPhotos);

		} catch (IOException | DbxException | JSONException e) {
			System.out.println("Thread " + threadName + " interrupted.");
		}

		System.out.println("Thread " + threadName + " exiting.");
		

	}
	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
	
}