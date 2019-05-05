package com.vertx.student.crud.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vertx.student.crud.config.DBConnection;
import com.vertx.student.crud.dao.StudentDAO;
import com.vertx.student.crud.model.Student;
import com.zaxxer.hikari.HikariDataSource;

/**
 * https://vertx.io/docs/vertx-sql-common/java/
 * 
 */
public class StudentDAOImpl implements StudentDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentDAOImpl.class);

	@Override
	public Student get(Long studentID) throws SQLException {

		LOGGER.info("Fetching record from the database for the id: {}", studentID);

		HikariDataSource dataSource = DBConnection.getConnection();

		Connection conn = null;
		Student student = null;

		try {

			conn = dataSource.getConnection();

			DSLContext dslContext = DBConnection.getDSLContext(conn);

			String sql = "SELECT * FROM \"Student\" where id = " + studentID;

			student = dslContext.fetchOne(sql).into(Student.class);

			LOGGER.info("Student record fetched successfully from db: {}", student);

		} catch (SQLException ex) {

			LOGGER.info(ex.getMessage() + ex);

		}

		return student;

	}

	@Override
	public List<Student> getAll() {
		LOGGER.info("Fetching all student from the database");

		List<Student> students = null;

		HikariDataSource dataSource = DBConnection.getConnection();

		try (Connection conn = dataSource.getConnection()) {

			DSLContext dslContext = DBConnection.getDSLContext(conn);

			String sql = "SELECT * FROM \"Student\"";

			students = dslContext.fetch(sql).into(Student.class);

			LOGGER.info("All students fetched successfully from db: {}", students);

		} catch (SQLException ex) {

			LOGGER.info(ex.getMessage() + ex);
		}
		return students;
	}

	@Override
	public Student save(Student student) {
		return new Student();
	}

	@Override
	public Student update(Student student) {
		return new Student();

	}

	@Override
	public Student delete(Long studentID) {
		return new Student();
	}

}
