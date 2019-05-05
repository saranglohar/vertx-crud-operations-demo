package com.vertx.student.crud.starter;

import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vertx.student.crud.config.DBConnection;
import com.vertx.student.crud.enums.EventAddress;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class StudentCrudStarter extends AbstractVerticle {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentCrudStarter.class);

	@Override
	public void start() throws Exception {

		Router router = Router.router(vertx);

		DBConnection.setConnection(vertx);

		vertx.deployVerticle("com.vertx.student.crud.verticles.StudentWorkerVerticle",
				new DeploymentOptions().setWorker(true).setInstances(1));

		addRoutingPaths(router);
		startServer(router);
	}

	@Override
	public void stop() throws Exception {
		super.stop();
	}

	private void addRoutingPaths(Router router) throws URISyntaxException {

		LOGGER.info("Adding routing paths...");
		
		router.route().handler(BodyHandler.create());

		LOGGER.info("Get all students: /students");
		router.get("/students").handler(routingContext -> vertx.eventBus().send(EventAddress.GET_ALL_STUDENTS.name(),
				new JsonObject(), (AsyncResult<Message<JsonObject>> result) -> {

					JsonObject responseJson = result.result().body();

					HttpServerResponse response = routingContext.response();
					response.putHeader("Content-Type", "application/json");

					response.end(responseJson.encode());
				}));

		LOGGER.info("Get a student: /student/:studentId");
		router.get("/student/:studentId").handler(routingContext -> {

			vertx.eventBus().send(EventAddress.GET_STUDENT.name(), getRequestBody(routingContext),
					(AsyncResult<Message<JsonObject>> result) -> {

						HttpServerResponse response = routingContext.response();
						response.putHeader("Content-Type", "application/json");

						response.end(result.result().body().encode());
					});
		});

		LOGGER.info("Create a new student: /student");
		router.post("/student").handler(routingContext -> {

			JsonObject request = routingContext.getBodyAsJson();

			vertx.eventBus().send(EventAddress.SAVE_STUDENT.name(), request,
					(AsyncResult<Message<JsonObject>> result) -> {

						JsonObject responseJson = result.result().body();

						HttpServerResponse response = routingContext.response();
						response.putHeader("Content-Type", "application/json");

						response.end(responseJson.encode());
					});

		});

		LOGGER.info("Update a student: /student/:studentId");
		router.put("/student/:studentId")
				.handler(routingContext -> vertx.eventBus().send(EventAddress.UPDATE_STUDENT.name(), new JsonObject(),
						(AsyncResult<Message<JsonObject>> result) -> {

							JsonObject responseJson = result.result().body();

							HttpServerResponse response = routingContext.response();
							response.putHeader("Content-Type", "application/json");

							response.end(responseJson.encode());
						}));

		LOGGER.info("Delete a student: /student/:studentId");
		router.delete("/student/:studentId")
				.handler(routingContext -> vertx.eventBus().send(EventAddress.DELETE_STUDENT.name(), new JsonObject(),
						(AsyncResult<Message<JsonObject>> result) -> {

							JsonObject responseJson = result.result().body();

							HttpServerResponse response = routingContext.response();
							response.putHeader("Content-Type", "application/json");

							response.end(responseJson.encode());
						}));

	}

	private JsonObject getRequestBody(RoutingContext routingContext) {
		JsonObject requestJson = new JsonObject();

		HttpServerRequest request = routingContext.request();
		request.params().names().forEach(paramName -> {
			requestJson.put(paramName, request.getParam(paramName));
		});

		return requestJson;
	}

	@SuppressWarnings("deprecation")
	private void startServer(Router router) {

		vertx.createHttpServer().requestHandler(router::accept).listen(8085, result -> {

			if (result.succeeded()) {

				LOGGER.info("Application started on port : 8085");

			} else {
				LOGGER.error("Unable to start Application on port : 8085");
			}
		});

	}
}
