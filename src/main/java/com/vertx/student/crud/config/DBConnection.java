package com.vertx.student.crud.config;

import java.sql.Connection;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.vertx.core.Vertx;

public class DBConnection {

	private static HikariDataSource dataSource;

	private DBConnection() {
	}

	@Override
	protected void finalize() throws Throwable {
		if (null != dataSource) {
			dataSource.close();
		}
		super.finalize();
	}

	public static void setConnection(Vertx vertx) {

		if (dataSource == null) {
			String configFile = "src/main/resources/db.properties";
			HikariConfig cfg = new HikariConfig(configFile);
			dataSource = new HikariDataSource(cfg);
		}
	}

	public static HikariDataSource getConnection() {
		return dataSource;
	}

	public static DSLContext getDSLContext(Connection connection) {

		DSLContext dslContext = null;

		if (null != connection) {
			dslContext = DSL.using(connection, SQLDialect.POSTGRES);
		}

		return dslContext;
	}

}
