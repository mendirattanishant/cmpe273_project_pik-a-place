package com.cmpe.project.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import syncdata.FBDBController;

import com.SpringConfig;
import com.cmpe.project.constants.Constants;
import com.cmpe.project.to.LoginTO;
import com.cmpe.project.to.RegistrationBean;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import facebook4j.FacebookException;

/**
 * @author NISHANT
 *
 */
@RestController 
public class LoginController {

	@Autowired
	private HttpSession httpSession;
	
	ApplicationContext applicationctx = new AnnotationConfigApplicationContext(
			SpringConfig.class);
	MongoOperations mongoOperation = (MongoOperations) applicationctx
			.getBean("mongoTemplate");

	@RequestMapping(value = "/LoginController1", method = RequestMethod.POST)
	@ResponseBody
	public RegistrationBean createUser(@RequestBody RegistrationBean RBean)
			throws NoSuchAlgorithmException {

		System.out.println("in controller");
		String password = RBean.getPassword();
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] bytes = md.digest();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		RBean.setPassword(sb.toString());
		System.out.println();
		mongoOperation.save(RBean);

		return RBean;
	}

	/**
	 * @param user
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	/**
	 * @param user
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@RequestMapping(value = "/userlogin", method = RequestMethod.POST)
	@ResponseBody
	public Object loginuser(@RequestBody LoginTO user)
			throws NoSuchAlgorithmException {
		RegistrationBean RBean = new RegistrationBean();
		String user_name = user.getUsername();
		String password = user.getPassword();
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] bytes = md.digest();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		System.out.println(sb.toString());

		DBCollection dbcUser = SpringConfig.db
				.getCollection("registrationBean");

		
		BasicDBObject query = null;
		
		query=new BasicDBObject("_id",user_name);
		DBCursor cursor = dbcUser.find(query);
		if(cursor.hasNext()) 
		{
			query = new BasicDBObject("_id", user_name).append("password",
					sb.toString());
			cursor=dbcUser.find(query);
			if(cursor.hasNext())
			{
				System.out.println("welcome user");
				httpSession.setAttribute(Constants.LOGGED_IN_USER, user_name);
				return user;
			}
			else
			{
				System.out.println("incorrect password");
				return "password failed";
			}	
		}
		else
		{
			System.out.println("user does not exist. Please Sign up");
			return "No User";
		}
		
		/*query = new BasicDBObject("username", user_name).append("password",
				sb.toString());
		*/
		//System.out.println("in post");
		
		
		/*DBCursor cursor = dbcUser.find(query);
		if (cursor.hasNext()) {
			httpSession.setAttribute(Constants.LOGGED_IN_USER, user_name);
			return user;
		} else {
			return "";
		}*/

	}


}
