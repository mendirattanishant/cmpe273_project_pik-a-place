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

	

}