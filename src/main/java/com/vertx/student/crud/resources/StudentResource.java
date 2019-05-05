package com.vertx.student.crud.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vertx.student.crud.dao.StudentDAO;
import com.vertx.student.crud.model.Student;
import com.vertx.student.crud.vo.Result;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

public class StudentResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentResource.class);

	StudentDAO studentDAO;

	public StudentResource(StudentDAO studentDAO) {
		this.studentDAO = studentDAO;
	}

	public void get(Message<JsonObject> message) {

		Result result = null;

		try {

			Student student = studentDAO.get(Long.parseLong(message.body().getString("studentId")));
			result = new Result(student);

		} catch (Exception e) {

			LOGGER.error("Error occured while fetching student:  {}", e);
		}

		message.reply(result.getResult());
	}

	public void getAll(Message<JsonObject> message) {

		Result result = null;

		try {

			List<Student> students = studentDAO.getAll();
			result = new Result(students);

		} catch (Exception e) {
			LOGGER.error("Error occured while fetching students:  {}", e);
		}

		message.reply(result.getResult());

	}

	public void save(Message<JsonObject> message) {

		LOGGER.error("Create student request body: {}", message.body().encode());

		Result result = null;

		try {

			Student newStudent = new Student(message.body().encode());

			Student student = studentDAO.save(newStudent);

			result = new Result(student);

		} catch (Exception e) {
			LOGGER.error("Error occured while saving students:  {}", e);
		}

		message.reply(result.getResult());
	}

	public void update(Message<JsonObject> message) {

		Student newStudent = new Student(message.body().encode());

		Student student = studentDAO.update(newStudent);

		message.reply(Json.encode(student));
	}

	public void delete(Message<JsonObject> message) {

		Student student = studentDAO.delete(message.body().getLong("studentId"));

		message.reply("Student deleted successfully: " + student);
	}
}
