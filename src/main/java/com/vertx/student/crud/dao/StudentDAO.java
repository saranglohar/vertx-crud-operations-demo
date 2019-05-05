package com.vertx.student.crud.dao;

import java.sql.SQLException;
import java.util.List;

import com.vertx.student.crud.model.Student;

public interface StudentDAO {

	public Student get(Long studentID) throws SQLException;

	public List<Student> getAll();

	public Student save(Student student);

	public Student update(Student student);

	public Student delete(Long studentID);

}
