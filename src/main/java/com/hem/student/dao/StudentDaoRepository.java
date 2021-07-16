package com.hem.student.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentDaoRepository extends MongoRepository<Student, Integer> {
	

}
