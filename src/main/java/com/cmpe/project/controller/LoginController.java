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

}
