package com.vertx.student.crud.vo;

import java.util.List;
import java.util.Map;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Result {

	JsonObject result;

	public Result(Object object) {

		result = new JsonObject();

		if (object instanceof List || object instanceof Map) {
			result.put("result", new JsonArray(Json.encode(object)));

		} else {
			result.put("result", new JsonObject(Json.encode(object)));
		}

	}

	public JsonObject getResult() {
		return result;
	}

	public void setResult(JsonObject result) {
		this.result = result;
	}
}
