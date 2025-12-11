package com.instituteportal.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instituteportal.DTO.OptionsDTO;
import com.instituteportal.DTO.QuestionListDTO;
import com.instituteportal.DTO.ShowSubjectsDTO;
import com.instituteportal.models.Notices;
import com.instituteportal.models.Options;
import com.instituteportal.models.Question;
import com.instituteportal.models.ScoreDetails;
import com.instituteportal.models.Subject;
import com.instituteportal.models.User;
import com.instituteportal.repository.StudentRepository;
import com.instituteportal.repository.UserRepository;

@Service
public class StudentServices {
	
	@Autowired
	StudentRepository stdrepo;
	@Autowired
	UserRepository userrepo;
	@Autowired

	public List <ShowSubjectsDTO> showSubjects()
	{
		List<Subject> subList = stdrepo.getSubjectList();
		List<ShowSubjectsDTO> subDtoList = new ArrayList<ShowSubjectsDTO>();
		for(Subject sub : subList)
		{
			ShowSubjectsDTO subdto= new ShowSubjectsDTO();
			subdto.setDate(sub.getDate());
			subdto.setFacname(sub.getUser().getName());
			subdto.setQuizId(sub.getQuizId());
			subdto.setSubject(sub.getSubject());
			subDtoList.add(subdto);
		}
		return subDtoList;
	}
	
	public List<QuestionListDTO> getQuestionList(int id)
	{
		List<Question> listQuestion = stdrepo.getQuiz(id);
		List<QuestionListDTO> questionList = new ArrayList<QuestionListDTO>();
		for(Question question : listQuestion)
		{
			QuestionListDTO quest = new QuestionListDTO();
			quest.setId(id);
			quest.setQuizId(question.getQuizId().getQuizId());
			quest.setQuestion(question.getQuestion());
			List<OptionsDTO> listOptDto = new ArrayList<OptionsDTO>();
			for(Options option : question.getOptions() )
			{
				OptionsDTO opt = new OptionsDTO();
				opt.setOptionString(option.getOptionString());
				opt.setQue_id(question.getId());
				opt.setCorrect(option.isCorrect());
				listOptDto.add(opt);
			}
			quest.setOptions(listOptDto);
		  questionList.add(quest);
		}
		return questionList;
	}

	public boolean addScore(int userid, int quizid, int score) {
		
		User u1 =  userrepo.getUserById(userid);
	    Subject sub = stdrepo.geSubject(quizid);
		ScoreDetails scoredet = new ScoreDetails();
		scoredet.setDate(LocalDate.now());
		scoredet.setQuizId(sub);
		scoredet.setScore(score);
		scoredet.setUserID(u1);
		return stdrepo.addScore(scoredet);
	}
	
	public List<Notices> displayNotice()
	{
		List<Notices> noticeList = stdrepo.getNotices();
		return noticeList;
	}

}
