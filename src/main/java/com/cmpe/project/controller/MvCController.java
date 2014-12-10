package com.cmpe.project.controller;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import Database.FilteredUserPhotoPlace;
import Database.SearchFriendsWithPlace;
import DropBox.DownloadFiles;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import gmail.Mail;

import javax.servlet.http.HttpSession;

import com.cmpe.project.constants.Constants;
/**
 * @author NISHANT
 *
 */
@Controller
@RequestMapping("/")
public class MvCController {

	@Autowired
	private HttpSession httpSession;
	
	public HttpSession getHttpSession() {
		return httpSession;
	}

	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	@RequestMapping(value = "/pik-a-place", method = RequestMethod.GET)
	public String index() {
		return "Login";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup() {
		return "index";
	}

	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public String successJsp() {
		return "Success";
	}

	@RequestMapping(value = "/Error", method = RequestMethod.GET)
	public String Errorjsp() {
		return "Error";
	}

	@RequestMapping(value = "/maps", method = RequestMethod.GET)
	public String MapsJsp() {
		return "Maps";
	}
	
	@RequestMapping(value = "/Recommendation", method = RequestMethod.GET)
	public String RecommendationJsp() {
		return "Recommendation";
	}
	
	
	@RequestMapping(value = "/Logout", method = RequestMethod.GET)
	public String LogoutJsp() {
		httpSession.invalidate();
		return "Logout";
	}
	
	@RequestMapping(value = "/syncPhotos", method = RequestMethod.POST)
	@ResponseBody
	public List<String> syncPhotos(@RequestBody String data) {

		DownloadFiles df = new DownloadFiles();
		List<String> picList = null;
		try {
			Database.User usTemp = new Database.User();
			String fbUserID = usTemp.getFBUserId(data.replace("=", ""));
			httpSession.setAttribute(Constants.FB_USER_ID, fbUserID);
			//System.out.println("fbUSerID: " + httpSession.getAttribute(Constants.FB_USER_ID)) ;
			if(fbUserID != "")
				picList = df.downloadFile(fbUserID);
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return picList;

	}

	
	@RequestMapping(value = "/fbUserId", method = RequestMethod.POST)
	@ResponseBody
	public String fbUserId(@RequestBody String data) throws UnknownHostException {
		String fbUserID = "";
		
		Database.User usTemp = new Database.User();
		fbUserID = usTemp.getFBUserId(httpSession.getAttribute(Constants.LOGGED_IN_USER).toString());
	
		
		
		return fbUserID;

	}
	
	@RequestMapping(value = "/mail", method = RequestMethod.POST)
	@ResponseBody
	public String sendMail(@RequestBody String data) {
		
		
		Gmail.Mail gmTemp = new Gmail.Mail();
		List<FilteredUserPhotoPlace> filteredUserPhotoPlace = (List<FilteredUserPhotoPlace>)httpSession.getAttribute(Constants.FilteredUserPhotoPlace);
		gmTemp.gmail(filteredUserPhotoPlace);
	
		
		
		return "sent";

	}
	@RequestMapping(value = "/placePhoto", method = RequestMethod.POST)
	@ResponseBody
	public List<FilteredUserPhotoPlace> placePhoto(@RequestBody String data) throws UnknownHostException {
		JSONObject jsonObj = null;
		List<FilteredUserPhotoPlace> filteredUserPhotoPlace = null;
		try {
			jsonObj = new JSONObject(data);
			System.out.println(jsonObj.getString("k").toString());
			System.out.println(jsonObj.getString("B").toString());
			filteredUserPhotoPlace = getAllData(jsonObj.getDouble("k"),
					jsonObj.getDouble("B"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpSession.setAttribute(Constants.FilteredUserPhotoPlace, filteredUserPhotoPlace);
		return filteredUserPhotoPlace;

	}

	public List<FilteredUserPhotoPlace> getAllData(double latitude,
			double longitude) throws UnknownHostException {
		String fbUserID = "";
		
		Database.User usTemp = new Database.User();
		fbUserID = usTemp.getFBUserId(httpSession.getAttribute(Constants.LOGGED_IN_USER).toString());
	
		
		SearchFriendsWithPlace frdU = new SearchFriendsWithPlace();
		String parentID = fbUserID;
		List<FilteredUserPhotoPlace> filteredData = null;
		try {
			filteredData = frdU.GetFriendListNPhotos(parentID, latitude,
					longitude);

		} catch (UnknownHostException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return filteredData;
	}

}