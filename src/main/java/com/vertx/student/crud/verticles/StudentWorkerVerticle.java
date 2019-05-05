package com.vertx.student.crud.verticles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vertx.student.crud.dao.StudentDAO;
import com.vertx.student.crud.dao.impl.StudentDAOImpl;
import com.vertx.student.crud.enums.EventAddress;
import com.vertx.student.crud.resources.StudentResource;

import io.vertx.core.AbstractVerticle;

public class StudentWorkerVerticle extends AbstractVerticle {

	public static final Logger LOGGER = LoggerFactory.getLogger(StudentWorkerVerticle.class);

	@Override
	public void start() throws Exception {

		StudentDAO studentDAO = new StudentDAOImpl();

		StudentResource studentResource = new StudentResource(studentDAO);

		vertx.eventBus().consumer(EventAddress.GET_ALL_STUDENTS.name(), studentResource::getAll);

		vertx.eventBus().consumer(EventAddress.GET_STUDENT.name(), studentResource::get);

		vertx.eventBus().consumer(EventAddress.SAVE_STUDENT.name(), studentResource::save);

		vertx.eventBus().consumer(EventAddress.UPDATE_STUDENT.name(), studentResource::update);

		vertx.eventBus().consumer(EventAddress.DELETE_STUDENT.name(), studentResource::delete);

	}

}
