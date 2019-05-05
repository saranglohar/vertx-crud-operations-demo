package com.vertx.student.crud.model;

import java.sql.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Student {

	Long id;

	String name;

	String className;

	String address;

	Long phoneNumber;

	Date creationDate;

	public Student() {
	}

	public Student(Long id, String name, String className, String address, Long phoneNumber, Date creationDate) {
		super();
		this.id = id;
		this.name = name;
		this.className = className;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.creationDate = creationDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Student(String jsonString) {

		super();

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			if (StringUtils.isNotBlank(jsonString)) {
				objectMapper.readerForUpdating(this).readValue(jsonString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return String.format("Student [id=%s, name=%s, className=%s, address=%s, phoneNumber=%s, creationDate=%s]", id,
				name, className, address, phoneNumber, creationDate);
	}

}
