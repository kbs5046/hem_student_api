package com.hem.student.service;

import java.util.List;

import com.hem.student.dto.StudentDto;

public interface StudentService {
	
	void insertStudent(StudentDto studentDto);
	StudentDto getStudentById(int id);
	List<StudentDto> fetchAll();
	void deleteStudentById(int id);
	void updateStudent(StudentDto studentDto);
	
}
