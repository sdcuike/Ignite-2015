package com.doctor.ignite.example;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;

public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@QuerySqlField(index = true)
	private UUID id;

	@QuerySqlField
	private String name;

	@QuerySqlField
	private BigDecimal salary;

	@QuerySqlField
	private String sex;

	@QueryTextField
	private String resume;

	public Person(UUID id, String name, BigDecimal salary, String sex, String resume) {
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.sex = sex;
		this.resume = resume;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public String getSex() {
		return sex;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public static void main(String[] args) {
		System.out.println(UUID.class);
	}

}