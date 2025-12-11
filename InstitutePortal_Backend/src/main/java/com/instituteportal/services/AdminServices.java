package com.instituteportal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instituteportal.DTO.AdminDashboardDTO;
import com.instituteportal.models.User;
import com.instituteportal.repository.AdminRepository;
import com.instituteportal.repository.UserRepository;

@Service
public class AdminServices {

	@Autowired
	AdminRepository adminrepo;
	@Autowired
	UserRepository userRepo;
	public AdminDashboardDTO getDashDetails() {
		AdminDashboardDTO admindash = new AdminDashboardDTO();
		
		admindash.setCountfaculty(adminrepo.getFacCount());
		admindash.setCountstudent(adminrepo.getStudentCount());
    	admindash.setCountrecordings(adminrepo.getNotesCount());
		admindash.setCountstudymatrial(adminrepo.getRecordingCount());
		
		return admindash;
	}
	
	public List<User> getfacList() {
		
		return adminrepo.getFacList();
	}

	public List<User> getStudList() {
		
		return adminrepo.getStudList();
	}

	public void disableAcc(int id) {
		User u1 = userRepo.getUserById(id);
	
		u1.setActive(false);
		adminrepo.updateUser(u1);
	}

	public void enableAcc(int id) {
		User u1 = userRepo.getUserById(id);
		u1.setActive(true);
		adminrepo.updateUser(u1);
		
	}

}
