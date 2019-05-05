package com.vertx.student.crud.starter;

import io.vertx.core.Launcher;

/**
 * Launch the vert.x application
 * 
 */
public class ApplicationLauncher {

	public static void main(String[] args) {

		Launcher.main(new String[] { "run", StudentCrudStarter.class.getName(),
				"-Dvertx.options.maxWorkerExecuteTime=30000000000000"});
	}

}
