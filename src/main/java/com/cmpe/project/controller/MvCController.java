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
	
	
	
	@RequestMapping(value = "/Logout", method = RequestMethod.GET)
	public String LogoutJsp() {
		httpSession.invalidate();
		return "Logout";
	}
}