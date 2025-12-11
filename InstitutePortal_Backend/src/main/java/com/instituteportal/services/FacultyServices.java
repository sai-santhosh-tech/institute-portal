package com.instituteportal.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instituteportal.DTO.FacultyDashboardDTO;
import com.instituteportal.models.Notices;
import com.instituteportal.models.User;
import com.instituteportal.repository.FacultyRepository;
import com.instituteportal.repository.UserRepository;

@Service
public class FacultyServices {
@Autowired
FacultyRepository facrepo;
@Autowired
UserRepository userreop;
@Autowired
MailService mailService;
	
	public FacultyDashboardDTO getDashDet(int i)
	{
		FacultyDashboardDTO facDac = new FacultyDashboardDTO();
		
		facDac.setCountNote(facrepo.getCountNotes(i));
		facDac.setCountNotice(facrepo.getCountNotices(i));
		facDac.setCountQuiz(facrepo.getCountQuiz(i));
		facDac.setCountRecording(facrepo.getCountRecording(i));
		return facDac;
	}
	
	public boolean uploadNotice(int id,String subject, String notice)
	{
		Notices newNotice= new Notices();
		newNotice.setNotice(notice);
		newNotice.setSubject(subject);
		User user = userreop.getUserById(id);
		newNotice.setUser(user);
		newNotice.setUploadDate(LocalDate.now());
		
		return facrepo.addNotice(newNotice);
	}
	
	public boolean uploadPrioNotice(int id, String subject, String notice) 
	{
		Notices newNotice= new Notices();
		newNotice.setNotice(notice);
		newNotice.setSubject(subject);
		User user = userreop.getUserById(id);
		newNotice.setUser(user);
		newNotice.setUploadDate(LocalDate.now());
		facrepo.addNotice(newNotice);
		List<String> mailList = facrepo.studentMailList();
		for(String email:mailList)
		{
		mailService.sendNotice(email, subject, notice);
		}
		return true;
	}
}
