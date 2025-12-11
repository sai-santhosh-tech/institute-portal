package com.instituteportal.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.instituteportal.DTO.LoginStatusDto;
import com.instituteportal.DTO.ProfilePicDTO;
import com.instituteportal.models.User;
import com.instituteportal.services.OtpGenerator;
import com.instituteportal.services.UserServicesImpl;

@CrossOrigin
@RestController
public class UserController {

	@Autowired
	UserServicesImpl uservice;
	@Autowired
	OtpGenerator op1;

	@Autowired
	GridFsTemplate gridfs;
	@Autowired
	GridFsOperations gridop;

	
	@PostMapping("/register")
	public boolean registerUser(@RequestBody User user)
	{
		boolean status = uservice.registerService(user);
		return status;
	}
	
	@PostMapping("/login")
	public LoginStatusDto loginUser(@RequestParam String email,String password)
	{
		return uservice.loginService(email, password);
	}
	
	@PostMapping("/ForgetPasswordGenerateOtp")
	public boolean generateOtp(@RequestParam String email)
	{
		
		if(uservice.sendOtp(email))
		{
		return true;
		}
	
			return false;
	
	}
	
	@PostMapping("/verifyOtp")
	public boolean verifyOtp(@RequestParam String otp)
	{
		
		if(op1.verifyOtp(otp))
		{
		   return true;
		}
		return false;
	}
	
	@PostMapping("/updatePassword")
	public boolean updatePassword(@RequestParam String newPassword,@RequestParam String email )
	{
		
		uservice.passwordUpdateService(email, newPassword);
	    return true;
	}
	
	@PostMapping("/imageUpload")
	public boolean uplaodImage(@RequestBody MultipartFile profilePic,int id)
	{
		System.out.println(profilePic);
		System.out.println(id);
		ProfilePicDTO newPic = new ProfilePicDTO();
		newPic.setId(id);
		newPic.setProfilePic(profilePic);
		
	  return uservice.uploadImage(newPic);
    }
	
	@GetMapping( value = "/getpropic",produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getImageWithMediaType(@RequestParam int id) throws IOException {
		 return uservice.getImage(id);	
	}
	
}
