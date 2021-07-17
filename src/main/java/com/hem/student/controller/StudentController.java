package com.hem.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hem.student.dto.StudentDto;
import com.hem.student.service.StudentService;

@RestController
@RequestMapping("/hem/student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@PostMapping("/insert")
	ApplicationResponse insert(@RequestBody @Validated StudentDto studentDto) {
		StudentDto idExist = studentService.getStudentById(studentDto.getStudentId());
		ApplicationResponse applicationResponse = new ApplicationResponse();
		if(idExist == null) {
			studentService.insertStudent(studentDto);
			applicationResponse.setCode(200);
			applicationResponse.setStatus("created");
			applicationResponse.setMessage("Student data inserted.");
			return applicationResponse;
		}
		applicationResponse.setCode(400);
		applicationResponse.setStatus("fail");
		applicationResponse.setMessage("Student id already exist.");
		return applicationResponse;
	}
	
	@GetMapping("/get")
	StudentDto getStudentById(@RequestParam int id) {	
		StudentDto dto = null;
		try {
			dto = studentService.getStudentById(id);
		}
		catch(Exception e) {
			
		}
		return dto;
	}
	
	@GetMapping("/getAll")
	List<StudentDto> findAll(){
		return studentService.fetchAll();
	}
	
	@DeleteMapping("/delete")
	ApplicationResponse deleteRecord(@RequestParam int id) {
		StudentDto idExist = studentService.getStudentById(id);
		ApplicationResponse applicationResponse = new ApplicationResponse();
		if(idExist == null) {
			applicationResponse.setCode(400);
 			applicationResponse.setStatus("fail");
 			applicationResponse.setMessage("Student Data does not exist.");
 			return applicationResponse;
		}
		try{
			studentService.deleteStudentById(id);
			applicationResponse.setCode(200);
 			applicationResponse.setStatus("success");
 			applicationResponse.setMessage("Student Deleted.");
			
		}
		catch(Exception e) {
			applicationResponse.setCode(400);
 			applicationResponse.setStatus("fail");
 			applicationResponse.setMessage(e.getMessage());
		}
		return applicationResponse;
	}
	

}
