package com.hem.student.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hem.student.dao.Student;
import com.hem.student.dao.StudentDaoRepository;
import com.hem.student.dto.StudentDto;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentDaoRepository studentRepository;

	@Override
	public void insertStudent(StudentDto studentDto) {
		Student student = new Student();
		BeanUtils.copyProperties(studentDto, student);
		studentRepository.save(student);
		
	}

	@Override
	public StudentDto getStudentById(int id) {
		StudentDto studentDto = null;
		Optional<Student> student = studentRepository.findById(id);
		if(student.isPresent()) {
			studentDto = new StudentDto();
			BeanUtils.copyProperties(student.get(), studentDto);
		}
		return studentDto;
	}

	@Override
	public List<StudentDto> fetchAll() {
		List<Student> students = studentRepository.findAll();
		List<StudentDto> studentDtos = new ArrayList<StudentDto>();
		
		students.forEach((student) -> {
			StudentDto studentDto = new StudentDto();
			BeanUtils.copyProperties(student, studentDto);
			studentDtos.add(studentDto);
		});
		
		return studentDtos;
	}

	@Override
	public void deleteStudentById(int id) {
		studentRepository.deleteById(id);

	}

	@Override
	public void updateStudent(StudentDto studentDto) {
		StudentDto dbStudentDto = this.getStudentById(studentDto.getStudentId());
		BeanUtils.copyProperties(studentDto, dbStudentDto, getNullPropertyNames(studentDto));
		Student student = new Student();
		BeanUtils.copyProperties(dbStudentDto, student);
		studentRepository.save(student);

	}
	
	
	// imported this from
	// https://stackoverflow.com/questions/19737626/how-to-ignore-null-values-using-springframework-beanutils-copyproperties
	public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
